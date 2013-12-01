package net.jcip.examples;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.RejectedExecutionException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class LazyInitRaceTest extends ConcurrentHarness {

    private LazyInitRace subject = new LazyInitRace();

    @Test
    public void testLazyInit() {
        try {
            for (int i = 0; i < CYCLES; i++) {
                executor.submit(new Runnable() {
                    @Override public void run() { subject.getInstance(); }
                });
            }
        } catch (RejectedExecutionException e) {
            fail("Execution rejected");
        }
    }

    @Override
    protected void afterAllTasks() {
        assertEquals(1, ExpensiveObject.getCounter());
        ExpensiveObject.reset();
    }
}
