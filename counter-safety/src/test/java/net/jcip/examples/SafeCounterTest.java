package net.jcip.examples;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.RejectedExecutionException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class SafeCounterTest extends ConcurrentHarness {

    private SafeCounter counter;

    @Before
    public void initCounters() {
        counter = new SafeCounter();
        counter.reset();
    }

    @Test
    public void testSafeIncrement() {
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

    @Override
    protected void afterAllTasks() {
        assertEquals(CYCLES, counter.getCounter());
    }
}
