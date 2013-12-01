package net.jcip.examples;

import java.util.concurrent.atomic.AtomicReference;

public class UnsafeCache<K, V> implements ValueCache<K, V> {
    private final AtomicReference<K> key = new AtomicReference<K>();
    private final AtomicReference<V> value = new AtomicReference<V>();

    @Override
    public V get(K k) {
        if (k.equals(key.get())) {
            return value.get();
        }
        return null;
    }

    @Override
    public void put(K k, V v) {
        key.set(k);
        value.set(v);
    }
}
