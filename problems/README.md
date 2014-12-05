# Homework Assignements

## A Simple Connection Pool

Initial code is located in `spike.pool` package.

Create a connection pool service with the following characteristics:

- The service is startable and stoppable:
-- pre-initialize minimum number of connections on start
-- stop giving out connections on stop
- The pool is configurable:
-- minimum and maximum number of connections
-- keep-alive time
-- the actual connection source (`ConnectionSource` interface)

When connection is requested, the pool does one of the following:

1. Returns one of existing free connections
2. Creates a new connection and returns it
3. Refuses to return a connection when maximum number of connections is reached

When connection is released, the pool marks it as free. The pool also checks periodically for idle connections and closes those which are idle for more than the keep-alive time. The min number of connections is always kept open.

Implementation approach:

- you can use Semaphores or BlockingQueues to manage free connections
- you can use FutureTask for representing a connection being open (this is not the only option)
- rely on object identity for the pooled connections: the objects passed to `release()` 
  should be exactly the same objects returned from `get()`, etc.

You should implement 2 interfaces: `PoolFactory` and `ConnectionPool`. Please also provide 
a good number of unit tests covering the `ConnectionPool` implementation.
