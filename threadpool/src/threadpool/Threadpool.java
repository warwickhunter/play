/**
 * Copyright Warwick Hunter 2010. All rights reserved.
 */
package threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * An experiment with thread pools.
 * 
 * @author Warwick Hunter (w.hunter@computer.org)
 * @date 2010-09-12
 */
public class Threadpool {
    
    private static final int      NUM_THREADS = 4;

    private final ExecutorService m_pool;

    public static void main(String[] args) {
        new Threadpool().start();
    }

    public Threadpool() {
        m_pool = Executors.newFixedThreadPool(NUM_THREADS);
    }

    public void start() {
        try {
            for (int i = 0; i < 50; ++i) {
                m_pool.execute(new Job(Integer.toString(i)));
            }

            System.out.println("shutdown");
            m_pool.shutdown();

            System.out.println("waiting for all jobs to complete");
            m_pool.awaitTermination(30, TimeUnit.SECONDS);
            System.out.println("waiting complete");
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class Job implements Runnable {

        private final String m_id;

        public Job(String id) {
            m_id = id;
        }

        @Override
        public void run() {
            System.out.printf("%s start on %s %n", m_id, Thread.currentThread().getName());
            try {
                Thread.sleep(500);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.printf("%s end on %s %n", m_id, Thread.currentThread().getName());
        }
    }
}
