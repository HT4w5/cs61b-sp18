public class LinkedListDeque<T> {
    // Node class.
    private static class _LLDNode<T> {
        // Instance variables.
        T _item;
        _LLDNode<T> _prev;
        _LLDNode<T> _next;

        // Constructor.
        public _LLDNode() {
            _item = null;
            _prev = null;
            _next = null;
        }

        public _LLDNode(T item, _LLDNode<T> prev, _LLDNode<T> next) {
            _item = item;
            _prev = prev;
            _next = next;
        }
    }

    // Instance variables.
    int _size;
    _LLDNode<T> _sentinal;

    // Constructor.
    public LinkedListDeque() {
        // Create sentinal node.
        _sentinal = new _LLDNode<>();
        _sentinal._next = _sentinal;
        _sentinal._prev = _sentinal;
        _size = 0;
    }

    // Methods.
    public void addFirst(T item) {
        _size += 1;
        _LLDNode<T> next = _sentinal._next;
        _sentinal._next = new _LLDNode<>(item, _sentinal, next);
        next._prev = _sentinal._next;
    }

    public void addLast(T item) {
        _size += 1;
        _LLDNode<T> prev = _sentinal._prev;
        _sentinal._prev = new _LLDNode<>(item, prev, _sentinal);
        prev._next = _sentinal._prev;
    }

    public boolean isEmpty() {
        return _size == 0;
    }

    public int size() {
        return _size;
    }

    public T removeFirst() {
        _size -= 1;
        T item = _sentinal._next._item;
        // Remove from ring.
        _sentinal._next = _sentinal._next._next;
        _sentinal._next._prev = _sentinal;
        return item;
    }

    public T removeLast() {
        _size -= 1;
        T item = _sentinal._prev._item;
        // Remove from ring.
        _sentinal._prev = _sentinal._prev._prev;
        _sentinal._prev._next = _sentinal;
        return item;
    }

    public T get(int index) {
        // Bounds check.
        if (index >= _size) {
            return null;
        } else {
            _LLDNode<T> node = _sentinal._next;
            while (index != 0) {
                node = node._next;
            }
            return node._item;
        }
    }

    public T getRecursive(int index) {
        // Bounds check.
        if (index >= _size) {
            return null;
        } else {
            return getRecursiveHelper(index);
        }
    }

    private T getRecursiveHelper(int index) {
        // Base case.
        if (index == 0) {
            return _sentinal._next._item;
        } else {
            return getRecursiveHelper(index - 1); // Recursion.
        }
    }

    public void printDeque() {
        _LLDNode<T> node = _sentinal._next;
        for (int i = 0; i < _size; ++i) {
            System.out.print(node._item);
            System.out.print(" ");
            node = node._next;
        }
    }
}
