import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.commons.lang3.tuple.Pair;

import com.google.common.base.Stopwatch;

import io.reactivex.Observable;

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
    }

    private static void nonRxToRx() {
        rxMethod()
            .filter(line -> line.contains("time_utc"))
            .subscribe(x -> System.out.printf("nonRxToRx %s%n", x));
    }

    /** Invoke some random web service to make things more realistic */
    private static List<String> nonRxMethod() throws IOException {
        URL url = new URL("http://scooterlabs.com/echo?message=hello_world");
        return IOUtils.readLines(url.openStream(), "UTF-8");
    }
    
    private static Observable<String> rxMethod() {
        return Observable.defer(() -> 
                    Observable.fromIterable(nonRxMethod()));
    }

    private static void joinTwoObservables() {
        Stopwatch sw = Stopwatch.createStarted();
        fast()
            .zipWith(slow(), Pair::of)
            .subscribe(x -> System.out.printf("joinTwo %s%n", x));
        sw.elapsed(TimeUnit.MILLISECONDS);
        System.out.printf("joinTwo took %s%n", sw);
    }
    
    private static Observable<String> slow() {
        try {
            TimeUnit.MILLISECONDS.sleep(500);
            return Observable.just("slow");
        } catch (InterruptedException e) {
            return Observable.error(e);
        }
    }

    private static Observable<String> fast() {
        try {
            TimeUnit.MILLISECONDS.sleep(50);
            return Observable.just("fast");
        } catch (InterruptedException e) {
            return Observable.error(e);
        }
    }
}