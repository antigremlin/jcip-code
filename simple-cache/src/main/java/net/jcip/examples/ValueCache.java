package net.jcip.examples;

public interface ValueCache<K, V> {
    V get(K key);
    void put(K key, V value);
}
