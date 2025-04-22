package synthesizer;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first; // index for the next dequeue or peek
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        this.capacity = capacity;
        first = 0;
        last = 0;
        fillCount = 0;
        rb = (T[]) new Object[capacity];
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    @Override
    public void enqueue(T x) {
        // Check overflow.
        if (fillCount == capacity) {
            throw new RuntimeException("Ring buffer overflow");
        }
        rb[last] = x;
        ++last;
        ++fillCount;
        if (last == capacity) {
            last = 0;
        }
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    @Override
    public T dequeue() {
        // Check underflow.
        if (fillCount == 0) {
            throw new RuntimeException("Ring buffer underflow");
        }
        T temp = rb[first];
        ++first;
        --fillCount;
        if (first == capacity) {
            first = 0;
        }
        return temp;
    }

    /**
     * Return oldest item, but don't remove it.
     */
    @Override
    public T peek() {
        // Check underflow.
        if (fillCount == 0) {
            throw new RuntimeException("Ring buffer underflow");
        }
        return rb[first];
    }

    private class ArrayRingIterator implements Iterator<T> {
        private int relIndex;
        private int absIndex;

        public ArrayRingIterator() {
            relIndex = 0; // Assume relative index 0 is null. Actual content starts at index 1.
            absIndex = first - 1; // Actual array index.
        }

        public boolean hasNext() {
            return relIndex != fillCount;
        }

        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Iterator range exceeded");
            }
            ++relIndex;
            ++absIndex;
            if (absIndex == capacity) {
                absIndex = 0;
            }

            return rb[absIndex];
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayRingIterator();
    }
}
