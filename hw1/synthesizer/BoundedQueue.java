package synthesizer;

import java.util.Iterator;

// Interface for bounded queue.
public interface BoundedQueue<T> extends Iterable<T> {
    int capacity();

    int fillCount();

    void enqueue(T x);

    T dequeue();

    T peek();

    Iterator<T> iterator();

    // Default methods.
    default boolean isEmpty() {
        return fillCount() == 0;
    }

    default boolean isFull() {
        return fillCount() == capacity();
    }
}
