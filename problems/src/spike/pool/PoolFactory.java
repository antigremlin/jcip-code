package spike.pool;

import java.util.concurrent.TimeUnit;

public interface PoolFactory<T> {
    ConnectionPool<T> newConnectionPool(int capacity, ConnectionSource<T> src);
    ConnectionPool<T> newConnectionPool(int min, int max,
                                        long keepAliveTime, TimeUnit utit,
                                        ConnectionSource<T> src);
}
