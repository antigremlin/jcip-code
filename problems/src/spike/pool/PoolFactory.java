package spike.pool;

import java.util.concurrent.TimeUnit;

public interface PoolFactory<T> {

    /**
     * Create a new connection pool with a fixed capacity.
     * @param capacity the number of connections to create on start
     * @param src the actual interface for opening/closing connections
     * @return the newly created connection pool
     */
    ConnectionPool<T> newConnectionPool(int capacity, ConnectionSource<T> src);

    /**
     * Create a new variable capacity pool. Some (init) connections are pre-opened.
     * Additional connections are opened dynamically. Idle connections are closed
     * after a keepAlive timeout. The init number of connections are left open
     * even after the keepAlive timeout.
     * @param init initial number of connections
     * @param max maximum number of connections
     * @param keepAliveTime the idle time after which a connection is closed
     * @param unit the time unit for keepAliveTime
     * @param src the actual interface for opening/closing connections
     * @return the newly created connection pool
     */
    ConnectionPool<T> newConnectionPool(int init, int max,
                                        long keepAliveTime, TimeUnit unit,
                                        ConnectionSource<T> src);

}
