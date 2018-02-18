import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.tuple.Pair;

import com.google.common.base.Stopwatch;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * Experiments with RxJava from "Reactive Programming with RxJava"
 *
 * @author Warwick Hunter
 * @since  2018-02-17
 */
public class RxJavaChapter4 {

    public static void main(String[] args) throws InterruptedException {
        nonRxToRx();
        joinTwoObservables();
        parallel();
    }

    private static void nonRxToRx() {
        rxMethod()
            .filter(line -> line.contains("time_utc"))
            .subscribe(x -> System.out.printf("nonRxToRx %s%n", x));
    }

    /** Invoke some random web service to make things more realistic */
    private static List<String> nonRxMethod() throws IOException {
        URL url = new URL("http://scooterlabs.com/echo?message=" + UUID.randomUUID().toString());
        List<String> lines = IOUtils.readLines(url.openStream(), "UTF-8");
        lines.add("thread=" + thread());
        return lines;
    }

    private static Observable<String> rxMethod() {
        return Observable.defer(() ->
                    Observable.fromIterable(nonRxMethod()));
    }

    private static String thread() {
        return Thread.currentThread().getName();
    }

    private static void joinTwoObservables() {
        Stopwatch sw = Stopwatch.createStarted();
        fast()
            .zipWith(slow(), Pair::of) // can be String::concat
            .blockingSubscribe(x -> System.out.printf("joinTwo1 %s on %s %n", x, thread()));
        sw.elapsed(TimeUnit.MILLISECONDS);
        System.out.printf("joinTwo1 took %s thread=%s %n", sw, thread());
    }

    private static Observable<String> slow() {
        return Observable.just("slow thread=" + thread())
                    .delay(1500, TimeUnit.MILLISECONDS);
    }

    private static Observable<String> fast() {
        return Observable.just("fast thread=" + thread())
                    .delay(500, TimeUnit.MILLISECONDS);
    }

    private static void parallel() throws InterruptedException {
        Observable.range(1, 3)
            .flatMap(x -> rxMethod()
                            .subscribeOn(Schedulers.io()))
            .filter(line -> line.contains("thread") || line.contains("utc"))
            .blockingSubscribe(x -> System.out.printf("parallel1 %s on %s%n", x, thread()));
    }
}