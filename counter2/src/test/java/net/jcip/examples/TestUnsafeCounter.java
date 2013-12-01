package net.jcip.examples;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestUnsafeCounter extends ConcurrentHarness {

    private UnsafeCounter counter;
    private long startNanos;

    @Before
    public void initCounter() {
        counter = new UnsafeCounter();
        startNanos = System.nanoTime();
    }

    @Test
    public void testUnsafeCounter() {
        submitTasks();
    }

    @Override
    protected Runnable newTask() {
        return new Runnable() {
            @Override public void run() { counter.increment(); }
        };
    }

    @Override
    protected void checkResults() {
        long elapsedNanos = System.nanoTime() - startNanos;
        System.out.printf("Unsafe elapsed us: %d\n", elapsedNanos / 1000);
        assertEquals(CYCLES, counter.getCounter());
    }

}
