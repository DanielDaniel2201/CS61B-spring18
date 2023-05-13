public class ArrayDeque<T> {
    private int size;
    private T[] array;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque() {
        size = 0;
        array = (T []) new Object[8];
        nextFirst = 0;
        nextLast = 1;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private int minusOne(int f) {
        return (f - 1 + array.length) % array.length;
    }

    private int plusOne(int l) {
        return (l + 1) % array.length;
    }

    public void resize(int capacity) {
        T[] newArray = (T []) new Object[capacity];
        int oldindex = plusOne(nextFirst);
        for (int i = 0; i < size; i += 1) {
            newArray[i] = array[nextFirst];
            oldindex = plusOne(oldindex);
        }
        array = newArray;
        nextFirst = array.length - 1;
        nextLast = size;
    }
    private void upsize() {
        resize(size * 2);
    }

    private void downsize() {
        resize(size * 4);
    }
    public void addFirst(T x) {
        if (size == array.length - 1) {
            upsize();
        }
        array[nextFirst] = x;
        nextFirst = minusOne(nextFirst);
        size += 1;
    }

    public void addLast(T x) {
        if (size == array.length - 1) {
            upsize();
        }
        array[nextLast] = x;
        nextLast = plusOne(nextLast);
        size += 1;
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        if (array.length > 16 && array.length / size >= 4) {
            downsize();
        }
        size -= 1;
        nextFirst = plusOne(nextFirst);
        return array[nextFirst];
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        if (array.length > 16 && array.length / size >= 4) {
            downsize();
        }
        size -= 1;
        nextLast = minusOne(nextLast);
        return array[nextLast];
    }

    public T get(int index) {
        if (index >= size) {
            return null;
        }
        int ptr = plusOne(nextFirst);
        for (int i = 0; i < index; i += 1) {
            ptr = minusOne(ptr);
        }
        return array[ptr];
    }

    public void printDeque() {
        int ptr = nextFirst;
        while (ptr != nextLast) {
            System.out.println(array[ptr]);
            ptr = minusOne(ptr);
        }
    }
}
