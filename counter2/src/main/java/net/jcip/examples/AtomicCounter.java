package net.jcip.examples;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicCounter {
    private AtomicInteger counter = new AtomicInteger();

    public void reset() {
        counter.set(0);
    }

    public void increment() {
        counter.getAndIncrement();
    }

    public long getCounter() {
        return counter.get();
    }

}
