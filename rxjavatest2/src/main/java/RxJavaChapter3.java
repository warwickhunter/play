    import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.IOUtils;

import io.reactivex.Observable;

/**
 * Experiments with RxJava from "Reactive Programming with RxJava"
 *
 * @author Warwick Hunter
 * @since  2018-02-03
 */
public class RxJavaChapter3 {

    public static void main(String[] args) throws InterruptedException {
        map();
        filter();
        flatMap();
        delay();
        merge();
        zip();
        combineLatest();
        TimeUnit.SECONDS.sleep(15);
    }

    private static void map() {
        Observable.just(8, 9, 10)
            .filter(i -> i % 3 > 0)
            .map(i -> "#" + i * 10)
            .filter(s -> s.length() < 4)
            .subscribe(v -> System.out.printf("map1 %s%n", v));
    }

    private static void filter() {
        int min = 1;
        int max = 100;
        Observable.range(min, max)
            .filter(i -> (i & 1) != 0) // odd/even test
            .filter(i -> {             // factor test
                for (int j = 3; j <= Math.sqrt(max); ++j) {
                    if (i != j && (i % j) == 0) {
                        return false;
                    }
                }
                return true;
            })
            .subscribe(i -> System.out.printf("prime %s%n", i));

        Observable.just(8,  9,  10)
            .doOnNext(i -> System.out.printf("A: %d%n",  i))
            .filter(i -> i % 3 > 0)
            .doOnNext(i -> System.out.printf("B: %d%n",  i))
            .map(i -> "#" + i * 10)
            .doOnNext(s -> System.out.printf("C: %s%n",  s))
            .filter(s -> s.length() < 4)
            .subscribe(s -> System.out.printf("D: %s%n", s));
    }

    private static Observable<String> getTokens() {
        return Observable.just("5", "1");
    }

    private static void flatMap() {
        getTokens()
            .map(s -> s + "!")
            .subscribe(s -> System.out.printf("map %s%n", s));

        // equivalent to above
        getTokens()
            .flatMap(s -> Observable.just(s + "!"))
            .subscribe(s -> System.out.printf("flatMap1 %s%n", s));

        // Now for something more complex
        getTokens()
            .flatMap(token -> invokeWebService(token))
            .filter(line -> line.contains("time_utc"))
            .subscribe(line -> System.out.printf("flatMap2 %s%n", line.trim()));
    }

    /** Invoke some random web service to make things more realistic */
    private static Observable<String> invokeWebService(String token) throws IOException {
        URL url = new URL("http://scooterlabs.com/echo?" + token);
        return Observable.fromIterable(IOUtils.readLines(url.openStream(), "UTF-8"));
    }

    private static void delay() throws InterruptedException {
        getTokens()
            .flatMap(token -> invokeWebService(token))
            .filter(line -> line.contains("time_utc"))
            .subscribe(line -> System.out.printf("delay1 %s%n", line.trim()));

        Observable.just(10L,  1L)
            .flatMap(x -> Observable.just(x).delay(x, TimeUnit.SECONDS))
            .subscribe(x -> System.out.printf("delay2 %s%n", x));
    }

    private static void merge() {
        Observable.merge(
                getTokens(),
                Observable.just("a", "b", "c"))
            .subscribe(x -> System.out.printf("merge1 %s%n", x));
    }

    private static void zip() {
        Observable<String> letters = Observable.just("a", "b", "c");
        Observable.zip(getTokens(), letters, (t, l) -> t + "/" + l)
            .subscribe(x -> System.out.printf("zip1 %s%n", x));

        getTokens().zipWith(letters, (t, l) -> t + "|" + l)
            .subscribe(x -> System.out.printf("zip2 %s%n", x));
    }

    private static void combineLatest() {
        Observable<Long> slow = Observable.interval(17,  TimeUnit.MILLISECONDS);
        Observable<Long> fast = Observable.interval(10,  TimeUnit.MILLISECONDS);
        Observable.combineLatest(
                        slow,
                        fast,
                        (s, f) -> f + ":" + s)
            .take(10)
            .forEach(x -> System.out.printf("combineLatest1 %s%n", x));

        slow.take(10)
            .withLatestFrom(fast, (s, f) -> f + ":" + s)
            .forEach(x -> System.out.printf("withLatestFrom %s%n", x));
    }
}