package net.jcip.examples;

public interface Computable<A, V> {
    V compute(A arg) throws InterruptedException;
}
