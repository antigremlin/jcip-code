package net.jcip.examples;

import net.jcip.annotations.NotThreadSafe;

import java.util.concurrent.*;

@NotThreadSafe
public class LazyInitNoWait {
    private static ExecutorService initExecutor = Executors.newSingleThreadExecutor();

    private ExpensiveObject instance = null;
    private Future<ExpensiveObject> result;

    private LazyInitNoWait() {}

    public static LazyInitNoWait create() {
        LazyInitNoWait result = new LazyInitNoWait();
        result.init();
        return result;
    }

    private void init() {
        result = initExecutor.submit(new Callable<ExpensiveObject>() {
            @Override public ExpensiveObject call() throws Exception { return new ExpensiveObject(); }
        });
    }

    public ExpensiveObject getInstance() {
        try {
            return result.get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}

