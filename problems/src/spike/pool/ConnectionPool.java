package spike.pool;

import java.util.concurrent.TimeUnit;

public interface ConnectionPool<T> {

    /**
     * Retrieve an open connection from the pool. If a new connection is being created, block until it is ready.
     * @return a connection, open and ready to use
     * @throws InterruptedException as the blocking policy
     */
    T get() throws InterruptedException;

    /**
     * For fixed capacity pools: try to get a free connection if it is available.
     * @return an open connection or null if no free connections are available
     */
    T tryGet();

    /**
     * For fixed capacity pools: try to get a free connection if it is available.
     * Block for a specified time period if it is not available immediately.
     * @param time time to wait, as usual for timed calls
     * @param unit time unit
     * @return an open connection or null if no free connections were available after the timeout
     * @throws InterruptedException as the blocking policy
     */
    T tryGet(long time, TimeUnit unit) throws InterruptedException;

    /**
     * Return the connection to the pool. Don't use the object after this call.
     * @param object the connection to return to the pool
     */
    void release(T object);

}
