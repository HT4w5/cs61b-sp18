public class ArrayDeque<T> {
    private static final int _INIT_SIZE = 8;
    // Instance variables.
    private T[] _data;
    private int _logicalSize;
    private int _allocatedSize;

    // Constructor.
    public ArrayDeque() {
        _data = (T[]) new Object[_INIT_SIZE];
        _logicalSize = 0;
        _allocatedSize = _INIT_SIZE;
    }

    // Methods.
    public addFirst

}