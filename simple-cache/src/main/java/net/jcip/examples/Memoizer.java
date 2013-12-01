package net.jcip.examples;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class Memoizer<A,V> implements Computable<A, V> {
    private final Computable<A,V> computer;
    private final ConcurrentMap<A,Future<V>> cache = new ConcurrentHashMap<A, Future<V>>();

    public Memoizer(Computable<A, V> computer) {
        this.computer = computer;
    }

    @Override
    public V compute(final A arg) throws InterruptedException {
        while (true) {
            Future<V> f = cache.get(arg);
            if (f == null) {
                Callable<V> eval = new Callable<V>() {
                    @Override public V call() throws Exception {
                        return computer.compute(arg);
                    } };
                FutureTask<V> ft = new FutureTask<V>(eval);
                f = cache.putIfAbsent(arg, ft);
                if (f == null) {
                    f = ft;
                    ft.run();
                }
            }
            try {
                return f.get();
            } catch (CancellationException e) {
                cache.remove(arg);
            } catch (ExecutionException e) {
                cache.remove(arg);
                throw new RuntimeException(e);
            }
        }
    }
}

class ExpensiveComputation implements Computable<String, BigInteger> {
    @Override
    public BigInteger compute(String arg) throws InterruptedException {
        return new BigInteger(arg);
    }
}