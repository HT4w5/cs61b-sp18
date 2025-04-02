public class LinkedListDeque<T> {
    // Node class.
    private static class LLDNode<T> {
        // Instance variables.
        T _item;
        LLDNode<T> _prev;
        LLDNode<T> _next;

        // Constructor.
        public LLDNode() {
            _item = null;
            _prev = null;
            _next = null;
        }

        public LLDNode(T item, LLDNode<T> prev, LLDNode<T> next) {
            _item = item;
            _prev = prev;
            _next = next;
        }
    }

    // Instance variables.
    private int _size;
    private LLDNode<T> _sentinal;

    // Constructor.
    public LinkedListDeque() {
        // Create sentinal node.
        _sentinal = new LLDNode<>();
        _sentinal._next = _sentinal;
        _sentinal._prev = _sentinal;
        _size = 0;
    }

    // Methods.
    public void addFirst(T item) {
        _size += 1;
        LLDNode<T> next = _sentinal._next;
        _sentinal._next = new LLDNode<>(item, _sentinal, next);
        next._prev = _sentinal._next;
    }

    public void addLast(T item) {
        _size += 1;
        LLDNode<T> prev = _sentinal._prev;
        _sentinal._prev = new LLDNode<>(item, prev, _sentinal);
        prev._next = _sentinal._prev;
    }

    public boolean isEmpty() {
        return _size == 0;
    }

    public int size() {
        return _size;
    }

    public T removeFirst() {
        if (_size > 0) {
            _size -= 1;
        }
        T item = _sentinal._next._item;
        // Remove from ring.
        _sentinal._next = _sentinal._next._next;
        _sentinal._next._prev = _sentinal;
        return item;
    }

    public T removeLast() {
        if (_size > 0) {
            _size -= 1;
        }
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
            LLDNode<T> node = _sentinal._next;
            while (index != 0) {
                node = node._next;
                index--;
            }
            return node._item;
        }
    }

    public T getRecursive(int index) {
        // Bounds check.
        if (index >= _size) {
            return null;
        } else {
            LLDNode<T> node = _sentinal._next;
            return getRecursiveHelper(node, index);
        }
    }

    private T getRecursiveHelper(LLDNode<T> node, int index) {
        // Base case.
        if (index == 0) {
            return node._item;
        } else {
            return getRecursiveHelper(node._next, index - 1); // Recursion.
        }
    }

    public void printDeque() {
        LLDNode<T> node = _sentinal._next;
        for (int i = 0; i < _size; ++i) {
            System.out.print(node._item);
            System.out.print(" ");
            node = node._next;
        }
    }
}
