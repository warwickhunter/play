import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.subjects.AsyncSubject;
import io.reactivex.subjects.PublishSubject;

/**
 * Experiments with RxJava from "Reactive Programming with RxJava"
 *
 * @author Warwick Hunter
 * @since  2018-02-01
 */
public class RxJavaChapter2 {

    private static Integer[] numbers = {1, 2, 3};

    public static void main(String[] args) {
        System.out.println("RxJava");
        System.out.printf("original %s%n", Arrays.asList(numbers));
        simple();
        intervalAndTimer();
        flatMap();
        subject();
        connectable();
    }

    private static void simple() {
        Observable.fromArray(numbers)
            .subscribe(v -> System.out.printf("unmodified %d%n", v));

        Observable.fromArray(numbers)
            .map(v -> v * 2)
            .subscribe(v -> System.out.printf("map * 2 = %d%n", v));

        List<Integer> collector = new ArrayList<>();
        Observable.just(1,2,3)
            .map(v -> v+1)
            .subscribe(v -> collector.add(v));
        collector.forEach(v -> System.out.printf("map + 1 & collected %d%n", v));

        Observable<Integer> observable = Observable.fromArray(numbers)
                .map(v -> v * 2)
                .cache();
        observable.subscribe(v -> System.out.printf("a:map * 2 = %d%n", v));
        observable.subscribe(v -> System.out.printf("b:map * 2 = %d%n", v));
    }

    private static void flatMap() {
        Observable.range(1, 3)
            .delay(2, TimeUnit.SECONDS)
            .flatMap(v -> Observable.just(v * 10))
            .subscribe(value -> System.out.printf("flatMapped1 %d%n", value),
                       error -> System.out.printf("flatMapped1: error %s%n", error),
                       ()    -> System.out.printf("flatMapped1: complete%n"));

        Observable.range(1, 3)
        .delay(2, TimeUnit.SECONDS)
        .flatMap(v -> Observable.just(v * 10))
        .doOnEach(v -> {
            if (v.isOnNext()) {
                System.out.printf("flatMapped2: %d %s %n", v.getValue(), v);
            } else if (v.isOnComplete()) {
                System.out.println("flatMapped2: complete");
            }
        })
        .blockingLast();
    }

    private static void intervalAndTimer() {
        Observable.interval(250, TimeUnit.MILLISECONDS)
            .take(5)
            .subscribe(v -> System.out.printf("interval %d%n", v));

        Observable.timer(500, TimeUnit.MILLISECONDS)
            .subscribe(v -> System.out.printf("timer %d%n", v));
    }

    private static void subject() {
        PublishSubject<Object> subject = PublishSubject.create();
        // observer1 will receive all onNext and onComplete events
        subject.subscribe(value -> System.out.printf("observer1: subject %s%n", value));
        subject.subscribe(value -> System.out.printf("observer1a: subject %s%n", value),
                          error -> System.out.printf("observer1a: error %s%n", error),
                          () -> System.out.printf("observer1a: complete%n"));
        subject.onNext("one");
        subject.onNext("two");
        // observer2 will only receive "three" and onComplete
        subject.subscribe(v -> System.out.printf("observer2: subject %s%n", v));
        subject.onNext("three");
        subject.onComplete();

        AsyncSubject<Object> asyncSubject = AsyncSubject.create();
        asyncSubject.subscribe(value -> System.out.printf("observer3: subject %s%n", value),
                               error -> System.out.printf("observer3: error %s%n", error),
                               ()    -> System.out.printf("observer3: complete%n"));
        asyncSubject.onNext("one");  // will be dropped
        asyncSubject.onNext("two");  // will be dropped
        asyncSubject.onNext("three");
        asyncSubject.onComplete();
    }

    private static void connectable() {
        System.out.println("connectable: setup");
        ConnectableObservable<Integer> connectable = Observable.fromArray(numbers).publish();
        connectable.subscribe(value -> System.out.printf("connectable: value %s%n", value),
                              error -> System.out.printf("connectable: error %s%n", error),
                              ()    -> System.out.printf("connectable: complete%n"));
        System.out.println("connectable: connect");
        connectable.connect();
    }
}