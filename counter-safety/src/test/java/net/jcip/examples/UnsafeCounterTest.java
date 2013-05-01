package net.jcip.examples;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class UnsafeCounterTest {

    public static final int CYCLES = 10000;
    private ExecutorService executor;
    private UnsafeCounter counter;
    private SafeCounter safeCounter;

    @Before
    public void setUp() throws Exception {
        ThreadPoolExecutor svc = new ThreadPoolExecutor(16, 16, 5, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(CYCLES));
        executor = svc;
        counter = new UnsafeCounter();
        counter.reset();
        safeCounter = new SafeCounter();
        safeCounter.reset();
    }

    @Test
    public void testUnsafeIncrement() {
        try {
            for (int i = 0; i < CYCLES; i++) {
                executor.submit(new Runnable() {
                    public void run() { counter.increment(); }
                });
            }
        } catch (RejectedExecutionException e) {
            fail("Execution rejected");
        }
    }

    @Test
    public void testSafeIncrement() {
        try {
            for (int i = 0; i < CYCLES; i++) {
                executor.submit(new Runnable() {
                    public void run() { safeCounter.increment(); }
                });
            }
        } catch (RejectedExecutionException e) {
            fail("Execution rejected");
        }
    }


    @After
    public void tearDown() throws Exception {
        executor.shutdown();
        boolean success = executor.awaitTermination(10, TimeUnit.SECONDS);
        assertTrue("All tasks completed", success);
        assertEquals(CYCLES, counter.getCounter());
        assertEquals(CYCLES, safeCounter.getCounter());
    }
}
