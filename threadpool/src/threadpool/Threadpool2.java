/**
 * Copyright Warwick Hunter 2010. All rights reserved.
 */
package threadpool;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * An experiment with thread pools.
 * 
 * @author Warwick Hunter (w.hunter@computer.org)
 * @date 2012-04-23
 */
public class Threadpool2 {
    
    private final ExecutorService m_pool;

    public static void main(String[] args) {
        new Threadpool2().start();
    }

    public Threadpool2() {
        m_pool = Executors.newSingleThreadExecutor();
    }

    public void start() {
        try {
            for (int i = 0; i < 3; ++i) {
                System.out.printf("submit %s%n", i);
                Future<?> future = m_pool.submit(new Job("job " + i));
    
                System.out.println("get");
                Object result = future.get();
                System.out.printf("result=%s %n", result);
            }
            
            m_pool.shutdown();
            m_pool.awaitTermination(30, TimeUnit.SECONDS);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        catch (ExecutionException e) {
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
