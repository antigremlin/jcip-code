package net.jcip.examples;

import java.util.concurrent.atomic.AtomicInteger;

class ExpensiveObject {
    private static AtomicInteger counter = new AtomicInteger();

    public static int getCounter() { return counter.intValue(); }

    public ExpensiveObject() {
        try {
            counter.incrementAndGet();
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void reset() {
        counter.set(0);
    }
}
