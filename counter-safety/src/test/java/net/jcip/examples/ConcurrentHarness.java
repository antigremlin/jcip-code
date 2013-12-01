package net.jcip.examples;

import org.junit.After;
import org.junit.Before;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

public abstract class ConcurrentHarness {
    public static final int CYCLES = 10000;
    protected ExecutorService executor;

    @Before
    public void initExecutor() {
        executor = new ThreadPoolExecutor(4, 4, 5, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(CYCLES));
    }

    @After
    public void tearDown() throws Exception {
        executor.shutdown();
        boolean success = executor.awaitTermination(10, TimeUnit.SECONDS);
        assertTrue("All tasks completed", success);
        afterAllTasks();
    }

    abstract protected void afterAllTasks();

}
