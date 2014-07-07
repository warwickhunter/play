/**
 * Copyright Warwick Hunter 2014. All rights reserved.
 */
package threadpool;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * An experiment with ExecutorCompletionService to verify that jobs can be submitted, taken,
 * submitted then taken.
 * 
 * @author Warwick Hunter (w.hunter@computer.org)
 * @date 2014-07-03
 */
public class ExecutorExperiment {
    
    private final ExecutorService                   m_executor;
    private final ExecutorCompletionService<String> m_service;

    public static void main(String[] args) {
        new ExecutorExperiment().start();
    }

    public ExecutorExperiment() {
        m_executor = Executors.newCachedThreadPool();
        m_service = new ExecutorCompletionService<String>(m_executor);
    }

    public void start() {
        try {
            for (int i = 0; i < 5; ++i) {
                System.out.printf("submit %s%n", i);
                m_service.submit(new Job(i));
            }
            for (int i = 0; i < 5; ++i) {
                Future<String> result = m_service.take();
                System.out.println(result.get());
            }
            
            for (int i = 0; i < 5; ++i) {
                System.out.printf("submit %s%n", i);
                m_service.submit(new Job(i));
            }
            
            // Experiment with shutdown to see how it works
            Thread.sleep(3000);
            System.out.println("shutdown now");
            m_executor.shutdownNow();
            
            for (int i = 0; i < 5; ++i) {
                Future<String> result = m_service.take();
                System.out.println(result.get());
            }

            System.out.println("await termination");
            m_executor.awaitTermination(30, TimeUnit.SECONDS);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private static class Job implements Callable<String> {

        private final int m_number;

        public Job(int number) {
            m_number = number;
        }

        @Override
        public String call() {
            try {
                if (Thread.interrupted()) {
                    System.out.printf("interrupted before sleep %d%n", m_number);
                }
                Thread.sleep(500 * (10 - m_number));
            }
            catch (InterruptedException e) {
                return "interrupted during sleep " + m_number;
            }
            return "finished " + m_number;
        }
    }
}
