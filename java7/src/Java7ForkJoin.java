/**
 * Copyright Warwick Hunter 2011. All rights reserved.
 */
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * Experiments with Java 7 features.
 *
 * @author Warwick Hunter
 * @since  2011-07-30
 */
public class Java7ForkJoin {

    private static class Stringifier extends RecursiveTask<String> {

        private final int m_number;
        
        private Stringifier(int number) {
            m_number = number;
        }
        
        @Override
        protected String compute() {
            if (m_number > 10) {
                Stringifier large = new Stringifier(m_number / 10);
                Stringifier small = new Stringifier(m_number % 10);
                large.fork();
                small.fork();
                return large.join() + small.join();
            } else {
                return "'" + Integer.toString(m_number) + "'";
            }
        }
    }
    
    /** Use fork join to split a task into smaller tasks */
    public static void forkJoin() {
        ForkJoinPool pool = new ForkJoinPool();
        Stringifier s = new Stringifier(13);
        pool.submit(s);
        while (!s.isDone()) {
            try {
                System.out.println("zzz....");
                Thread.sleep(100);
                
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        try {
            System.out.printf("Result %s%n", s.get());
        } catch (InterruptedException|ExecutionException e) {
            e.printStackTrace();
        }
    }
}