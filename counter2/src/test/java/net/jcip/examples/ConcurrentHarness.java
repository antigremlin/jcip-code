package net.jcip.examples;

import org.junit.After;
import org.junit.Before;

import java.util.concurrent.*;

import static org.junit.Assert.assertTrue;

public abstract class ConcurrentHarness {
    protected static final int CYCLES = 10000000;
    protected ExecutorService executor;

    @Before
    public void initExecutor() {
        executor = Executors.newFixedThreadPool(32);
    }

    protected void submitTasks() {
        for (int i = 0; i < CYCLES; i++) {
            executor.submit(newTask());
        }
    }

    @After
    public void collectResults() throws InterruptedException {
        waitAllTasks();
        checkResults();
    }

    private void waitAllTasks() throws InterruptedException {
        executor.shutdown();
        boolean terminated = executor.awaitTermination(5, TimeUnit.SECONDS);
        assertTrue("Finished all tasks", terminated);
    }

    protected abstract Runnable newTask();

    protected abstract void checkResults();
}
