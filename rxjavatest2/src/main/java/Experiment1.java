import java.util.List;

import com.google.common.collect.Lists;

import io.reactivex.Completable;
import io.reactivex.Observable;

/**
 * Experiments with RxJava to understand some possibly odd behaviour I see.
 *
 * @author Warwick Hunter
 * @since  2018-07-20
 */
public class Experiment1 {

	public static void main(String[] args) {
		wtf();
	}
	
	private static void wtf() {
		List<String> resourcesForEntity = Lists.newArrayList("a", "b");
		
		deleteEntity()
			.andThen(Observable.fromIterable(resourcesForEntity))
			.flatMapCompletable(resource -> transform(resource))
			.doOnError(e -> e(e))
			.doFinally(() -> markSuccessful())
			.blockingAwait();
	}
	
	private static Completable deleteEntity() {
		System.out.printf("deleteEntity on thread %s%n", Thread.currentThread().getName());
		return Completable.complete();
	}
	
	private static Completable transform(String resource) {
		System.out.printf("transform %s on thread %s%n", resource, Thread.currentThread().getName());
		return Completable.complete();
	}
	
	private static void markSuccessful() {
		System.out.printf("markSuccesul on thread %s%n", Thread.currentThread().getName());
	}

	private static void e(Throwable e) {
		e.printStackTrace();
	}
}
