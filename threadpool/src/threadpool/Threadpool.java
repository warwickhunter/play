/**
 * Copyright Warwick Hunter 2010. All rights reserved.
 */
package threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * An experiment with thread pools.
 * 
 * @author Warwick Hunter (w.hunter@computer.org)
 * @date   Sep 12, 2010
 *
 */
public class Threadpool 
{
    private static final int NUM_THREADS = 8;

    private final ExecutorService m_pool;
    
    public static void main(String[] args)
    {
        new Threadpool().start();
    }

    public Threadpool()
    {
        m_pool = Executors.newFixedThreadPool(NUM_THREADS);
    }
    
    public void start()
    {
        for (int i = 0; i < 100; ++i)
        {
            m_pool.execute(new Job(Integer.toString(i)));
        }
    }

    private static class Job implements Runnable
    {
        private final String m_id;

        public Job(String id)
        {
            m_id = id;
        }

        @Override
        public void run()
        {
            System.out.println(m_id + " start");
            try
            {
                Thread.sleep(2000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            System.out.println(m_id + " end");
        }
        
    }
}
