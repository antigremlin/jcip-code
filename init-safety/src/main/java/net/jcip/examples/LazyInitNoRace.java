package net.jcip.examples;

import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class LazyInitNoRace {
    private ExpensiveObject instance = null;

    public synchronized ExpensiveObject getInstance() {
        if (instance == null)
            instance = new ExpensiveObject();
        return instance;
    }
}

