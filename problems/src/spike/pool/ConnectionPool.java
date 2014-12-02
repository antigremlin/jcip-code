package spike.pool;

import java.util.concurrent.TimeUnit;

public interface ConnectionPool<T> {
    T get() throws InterruptedException;
    T tryGet();
    T tryGet(long time, TimeUnit unit) throws InterruptedException;
    void release(T object);
}
