package spike.pool;

public interface ConnectionSource<T> {
    T open();
    void close(T obj);
}
