public class LinkedListDeque<T> {
    public int size;
    public StuffNode sentinel;
    /*The list now has 2 links for every node.*/
    public class StuffNode {
        public StuffNode prev;
        public T item;
        public StuffNode next;

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

    /*Creates a deque with a given value*/
    public LinkedListDeque(T x) {
        sentinel = new StuffNode(null,  null);
        sentinel.prev = new StuffNode(sentinel, x, sentinel);
        sentinel.next = sentinel.prev;
        this.size = 1;
    }

    /*Creates an empty linked list deque.*/
    public LinkedListDeque(){
        this.size = 0;
        sentinel = new StuffNode(null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
    }

    /*Adds an item of type T to the front of the deque.*/
    public void addFirst(T x) {
        this.size += 1;
        StuffNode S = sentinel.next;
        StuffNode TMP = new StuffNode(null, x, null);
        TMP.prev = sentinel;
        sentinel.next = TMP;
        S.prev = TMP;
        TMP.next = S;
    }

    /*Adds an item of type T to the back of the deque.*/
    public void addLast(T x) {
        this.size += 1;
        StuffNode S = sentinel.prev;
        StuffNode TMP = new StuffNode(null, x, null);
        TMP.next = sentinel;
        sentinel.prev = TMP;
        S.next = TMP;
        TMP.prev = S;
    }

    /*Removes the first item of the list.*/
    public T removeFirst() {
        if (size == 0) {
            return null;
        } else {
            T rst = this.sentinel.next.item;
            this.size -= 1;
            sentinel.next = sentinel.next.next;
            sentinel.next.next.prev = sentinel;
            return rst;
        }
    }

    /*Removes the last item of the list.*/
    public T removeLast() {
        if (size == 0) {
            return null;
        } else {
            T rst = this.sentinel.prev.item;
            this.size -= 1;
            sentinel.prev = sentinel.prev.prev;
            sentinel.prev.prev.next = sentinel;
            return rst;
        }
    }

    /*Gets the item at the given index.*/
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
    private T getRecursive(StuffNode p, int i) {
        if (i == 0) {
            return p.next.item;
        }
        return getRecursive(p.next, i - 1);
    }
    public T getRecursive(int index) {
        if (index < 0 || index >= this.size){
            return null;
        }
        return getRecursive(sentinel, index);
    }

    /*Returns true if deque is empty, false otherwise.*/
    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    /*Prints the items in the deque from first to last*/
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