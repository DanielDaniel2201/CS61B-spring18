public class ArrayDeque<T> {
    private int size;
    private T[] array;
    private int nextFirst;
    private int nextLast;

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private int nextFirstChange(int f) {
        if (f == 0) {
            return array.length - 1;
        }
        return f - 1;
    }

    private int nextLastChange(int l) {
        if (l == array.length - 1) {
            return 0;
        }
        return l + 1;
    }
    private void enlarge() {
        T[] newArray = (T []) new Object[size * 2];
        System.arraycopy(array, 0, newArray, 0, nextFirst + 1);
        System.arraycopy(array, nextLast, newArray, nextLast + size, size - nextLast);
        array = newArray;
        nextFirst = nextFirst + size;
    }

    private void shrink() {
        T[] newArray = (T []) new Object[size * 4];
        System.arraycopy(array, 0, newArray, 0, nextLast);
        System.arraycopy(array, nextFirst + 1,
                newArray, array.length - 1 - nextFirst, nextFirst + 1);
        array = newArray;
        nextFirst = nextFirst - (array.length - 4 * size - 1);
    }
    public void addFirst(T x) {
        if (size == array.length - 1) {
            enlarge();
        }
        array[nextFirst] = x;
        nextFirst = nextFirstChange(nextFirst);
        size += 1;
    }

    public void addLast(T x) {
        if (size == array.length - 1) {
            enlarge();
        }
        array[nextLast] = x;
        nextLast = nextLastChange(nextLast);
        size += 1;
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        size -= 1;
        nextFirst = nextLastChange(nextFirst);
        T rst = array[nextFirst];
        if (array.length > 16 && array.length / size >= 4) {
            shrink();
        }
        return rst;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        size -= 1;
        nextLast = nextFirstChange(nextLast);
        T rst = array[nextLast];
        if (array.length > 16 && array.length / size >= 4) {
            shrink();
        }
        return rst;
    }

    public T get(int index) {
        if (index >= size) {
            return null;
        }
        int ptr = nextFirst;
        for (int i = 0; i <= index; i += 1) {
            ptr = nextFirstChange(ptr);
        }
        return array[ptr];
    }

    public void printDeque() {
        int ptr = nextFirst;
        while (ptr != nextLast) {
            System.out.println(array[ptr]);
            ptr = nextFirstChange(ptr);
        }
    }
}
