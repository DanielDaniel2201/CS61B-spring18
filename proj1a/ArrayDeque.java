public class ArrayDeque<T> {
    private int size;
    private T[] items;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque() {
        items = (T []) new Object[8];
        size = 0;
        nextFirst = 0;
        nextLast = 1;
    }

    private void resize(int factor) {
        T[] a = (T []) new Object[factor];
        System.arraycopy(items, 0, a, 0, size);
        items = a;
    }
    public void addLast(T x) {
        if (size == items.length) {
            resize(size * 2);
            nextLast = size;
            nextFirst = items.length;
        }
        items[nextLast] = x;
        size += 1;
        if (nextLast + 1 == items.length) {
            nextLast = 0;
        } else {
            nextLast += 1;
        }
    }

    public void addFirst(T x) {
        if (size == items.length) {
            resize(size * 2);
            nextLast = size;
            nextFirst = items.length;
        }
        items[nextFirst] = x;
        size += 1;
        if (nextFirst == 0) {
            nextFirst = items.length - 1;
        } else {
            nextFirst -= 1;
        }
    }

    private void usage() {
        T[] a = (T []) new Object[4 * size];
        System.arraycopy(items, 0, a, 0, nextLast);
        System.arraycopy(items, nextFirst + 1,
                a, 4 * size - items.length + nextFirst, items.length);
        items = a;
    }
    public T removeLast() {
        T rst = items[nextLast - 1];
        items[nextLast - 1] = null;
        size -= 1;
        if ((double) size / items.length < 0.25) {
            usage();
        }
        return rst;
    }

    public T removeFirst() {
        T rst;
        int i;
        if (nextFirst == items.length - 1) {
            rst = items[0];
            i = 0;
        } else {
            rst = items[nextFirst + 1];
            i = nextFirst + 1;
        }
        size -= 1;
        items[i] = null;
        if ((double) size / items.length < 0.25) {
            usage();
        }
        return rst;
    }
    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void printDeque() {
        for (int i = 0; i <= size; i += 1) {
            if (items[i] != null) {
                System.out.println(items[i]);
            }
        }
    }

    public T get(int index) {
        if (index > 0 && index <= size && items[index] != null) {
            return items[index];
        }
        return null;
    }
}
