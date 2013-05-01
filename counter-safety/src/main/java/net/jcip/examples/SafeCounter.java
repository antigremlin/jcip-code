package net.jcip.examples;

import java.util.concurrent.atomic.AtomicLong;

public class SafeCounter {

    private AtomicLong counter = new AtomicLong();

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
