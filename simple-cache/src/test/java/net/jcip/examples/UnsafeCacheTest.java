package net.jcip.examples;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class UnsafeCacheTest extends ConcurrentHarness {

    private final ValueCache<Integer, Integer> cache = new UnsafeCache<Integer, Integer>();

    private final ThreadLocal<Random> generator = new ThreadLocal<Random>() {
        @Override protected Random initialValue() {
            return new Random();
        }
    };

    private final AtomicInteger hitCount = new AtomicInteger();
    private final AtomicInteger missesCount = new AtomicInteger();
    private final AtomicInteger errorCount =  new AtomicInteger();

    @Test
    public void testCache() {
        try {
            for (int i = 0; i < CYCLES; i++) {
                executor.submit(new Task(i));
            }
        } catch (RejectedExecutionException e) {
            fail("Rejected execution");
        }
    }


    class Task implements Runnable {
        public Task(int i) { }

        @Override public void run() {
            Integer num = generator.get().nextInt(300);
            Integer value = cache.get(num);
            if (value == null) {
                value = num.hashCode();
                cache.put(num, value);
                missesCount.incrementAndGet();
            } else {
                hitCount.incrementAndGet();
                if (num.hashCode() != value) {
                    errorCount.incrementAndGet();
                }
            }
        }
    }

    @Override
    protected void afterAllTasks() {
        System.out.printf("Cache hits: %d\n", hitCount.get());
        assertTrue("There were cache misses", missesCount.get() > 0);
        assertTrue("There were cache hits", hitCount.get() > 0);
        assertEquals("Cache errors", 0, errorCount.get());
    }
}
