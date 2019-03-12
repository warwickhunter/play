import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;

/**
 * Experiments with RxJava from "Reactive Programming with RxJava"
 *
 * @author Warwick Hunter
 * @since  2018-02-19
 */
public class RxJavaChapter5 {

    public static void main(String[] args) throws InterruptedException {
        sampling();
    }

    private static void sampling() {
        long startTime = System.currentTimeMillis();
        Observable
            .interval(7, TimeUnit.MILLISECONDS)
            .timestamp()
            .sample(1, TimeUnit.SECONDS)
            .map(ts -> ts.time(TimeUnit.MILLISECONDS) - startTime + "ms: " + ts.value())
            .take(5)
            .blockingSubscribe(x -> System.out.printf("sampling1 %s%n", x));

        Observable<String> names = Observable.just("Mary", "Patricia", "Linda", "Barbara", "Elizabeth",
                                                   "Jennifer", "Maria", "Susan", "Margaret", "Dorothy");
        Observable<Long> absoluteDelayMillis = Observable.just(0.1, 0.6, 0.9, 1.1, 3.3, 3.4, 3.5, 3.6, 4.4, 4.8)
                .map(d -> (long)(d * 1_000));
        Observable<String> delayedNames = names.zipWith(absoluteDelayMillis,
            (n, d) -> Observable.just(n)
                        .delay(d, TimeUnit.MILLISECONDS))
            .flatMap(o -> o);
        delayedNames
            .sample(1, TimeUnit.SECONDS)
            .blockingSubscribe(x -> System.out.printf("sampling2 %s%n", x));

        Observable.range(1,  7)
                .buffer(3)
                .blockingSubscribe(x -> System.out.printf("buffer1 %s%n", x));
        delayedNames
        .buffer(1, TimeUnit.SECONDS)
        .blockingSubscribe(x -> System.out.printf("buffer2 %s%n", x));
    }
}