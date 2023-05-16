public class LinkedListDeque<T> implements Deque<T> {
    private int size;
    private StuffNode sentinel;
    /*The list now has 2 links for every node.*/
    private class StuffNode {
        private StuffNode prev;
        private T item;
        private StuffNode next;

        /*Constructor for StuffNode*/
        public StuffNode(StuffNode p, T x, StuffNode n) {
            this.prev = p;
            this.item = x;
            this.next = n;
        }

        /*This constructor is especially for the sentinel.*/
        public StuffNode(StuffNode pp, StuffNode nn) {
            this.prev = pp;
            this.next = pp;
        }
    }

    /*Creates an empty linked list deque.*/
    public LinkedListDeque() {
        this.size = 0;
        sentinel = new StuffNode(null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
    }

    /*Adds an item of type T to the front of the deque.*/
    @Override
    public void addFirst(T x) {
        this.size += 1;
        StuffNode n = new StuffNode(sentinel, x, sentinel.next);
        sentinel.next.prev = n;
        sentinel.next = n;
    }

    /*Adds an item of type T to the back of the deque.*/
    @Override
    public void addLast(T x) {
        this.size += 1;
        StuffNode n = new StuffNode(sentinel.prev, x, sentinel);
        sentinel.prev.next = n;
        sentinel.prev = n;
    }

    /*Removes the first item of the list.*/
    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        } else {
            T rst = this.sentinel.next.item;
            this.size -= 1;
            sentinel.next.next.prev = sentinel;
            sentinel.next = sentinel.next.next;
            return rst;
        }
    }

    /*Removes the last item of the list.*/
    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        } else {
            T rst = this.sentinel.prev.item;
            this.size -= 1;
            sentinel.prev.prev.next = sentinel;
            sentinel.prev = sentinel.prev.prev;
            return rst;
        }
    }

    /*Gets the item at the given index.*/
    @Override
    public T get(int index) {
        if (index < 0 || index > this.size) {
            return null;
        } else {
            StuffNode ptr = this.sentinel;
            for (int i = 0; i <= index; i += 1) {
                ptr = ptr.next;
            }
            return ptr.item;
        }
    }

    /*Same as get but recursively.*/
    private T getRecursiveHelp(StuffNode p, int i) {
        if (i == 0) {
            return p.item;
        }
        return getRecursiveHelp(p.next, i - 1);
    }
    public T getRecursive(int index) {
        if (index < 0 || index >= this.size) {
            return null;
        }
        return getRecursiveHelp(sentinel.next, index);
    }

    /*Returns true if deque is empty, false otherwise.*/
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    /*Prints the items in the deque from first to last*/
    @Override
    public void printDeque() {
        StuffNode ptr = this.sentinel;
        while (ptr.next.next != this.sentinel) {
            ptr = ptr.next;
            System.out.print(ptr.item + " ");
        }
        ptr = ptr.next;
        System.out.println(ptr.item);
    }
}
