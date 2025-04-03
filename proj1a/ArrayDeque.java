public class ArrayDeque<T> {
    private static final int INIT_SIZE = 8;
    // Instance variables.
    private T[] _data;
    private int _logicalSize;
    private int _allocatedSize;
    private int _frontIndex;
    private int _backIndex;

    // Constructor.
    public ArrayDeque() {
        _data = (T[]) new Object[INIT_SIZE];
        _logicalSize = 0;
        _allocatedSize = INIT_SIZE;
        _frontIndex = INIT_SIZE - 1;
        _backIndex = 0;
    }

    // Methods.
    private int nextIndex(int index) {
        if (index == _allocatedSize - 1) {
            return 0;
        } else {
            return index + 1;
        }
    }

    private int prevIndex(int index) {
        if (index == 0) {
            return _allocatedSize - 1;
        } else {
            return index - 1;
        }
    }

    private void resize(int newSize) {
        T[] newData = (T[]) new Object[newSize];
        // dequeue all items from old data.
        int index = _frontIndex;
        for (int i = 0; i < _logicalSize; ++i) {
            index = nextIndex(index);
            newData[i] = _data[index];
        }
        _data = newData;
        // Reset front and back indexes.
        _frontIndex = newSize - 1;
        _backIndex = _logicalSize;
        _allocatedSize = newSize;
    }

    public void addFirst(T item) {
        if (_logicalSize == _allocatedSize) {
            resize(_allocatedSize * 2);
        }

        _logicalSize++;
        _data[_frontIndex] = item;
        _frontIndex = prevIndex(_frontIndex);
    }

    public void addLast(T item) {
        if (_logicalSize == _allocatedSize) {
            resize(_allocatedSize * 2);
        }

        _logicalSize++;
        _data[_backIndex] = item;
        _backIndex = nextIndex(_backIndex);
    }

    public boolean isEmpty() {
        return _logicalSize == 0;
    }

    public int size() {
        return _logicalSize;
    }

    public T removeFirst() {
        // Bounds check.
        if (_logicalSize == 0) {
            return null;
        } else if (_allocatedSize / _logicalSize >= 4) {
            // Resize if usage factor is lower than 25%.
            if (_allocatedSize > 8) {
                resize(_allocatedSize / 2);
            }
        }
        _logicalSize--;
        _frontIndex = nextIndex(_frontIndex);
        T item = _data[_frontIndex];
        _data[_frontIndex] = null;
        return item;
    }

    public T removeLast() {
        // Bounds check.
        if (_logicalSize == 0) {
            return null;
        } else if (_allocatedSize / _logicalSize >= 4) {
            // Resize if usage factor is lower than 25%.
            if (_allocatedSize > 8) {
                resize(_allocatedSize / 2);
            }
        }
        _logicalSize--;
        _backIndex = prevIndex(_backIndex);
        T item = _data[_backIndex];
        _data[_backIndex] = null;
        return item;
    }

    public T get(int index) {
        int realIndex = _frontIndex + index + 1;
        if (realIndex >= _allocatedSize) {
            realIndex -= _allocatedSize;
        }
        return _data[realIndex];
    }

    public void printDeque() {
        int index = _frontIndex;
        for (int i = 0; i < _logicalSize; ++i) {
            index = nextIndex(index);
            System.out.print(_data[index]);
            System.out.print(" ");
        }
    }
}
