public class LinkedListDeque<LoahNess> {
    public class StuffNode {
        private LoahNess item;
        private StuffNode next, prev;
        private StuffNode(LoahNess i, StuffNode n, StuffNode p) {
            item = i;
            next = n;
            prev = p;
        }
    }

    /** The first item (if exists) is at sentinel.next */
    private StuffNode sentinel;
    private int size;

    public LinkedListDeque() {
        sentinel = new StuffNode(null, null,null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    public LinkedListDeque(LoahNess x) {
        sentinel = new StuffNode(null, null, null);
        sentinel.next = new StuffNode(x, sentinel, sentinel);
        sentinel.prev = sentinel.next;
        size = 1;
    }

    /** Returns the number of items in the list. */
    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return sentinel.next == sentinel;
    }

    /** Adds an item to the front of the list. */
    public void addFirst(LoahNess x) {
        StuffNode p = new StuffNode(x, sentinel.next, sentinel);
        sentinel.next.prev = p;
        sentinel.next = p;
        if (isEmpty())
            sentinel.prev = p;
        size += 1;
    }

    /** Adds an item to the end of the list. */
    public void addLast(LoahNess x) {
        StuffNode p = new StuffNode(x, sentinel, sentinel.prev);
        sentinel.prev.next = p;
        sentinel.prev = p;
        if (isEmpty())
            sentinel.next = p;
        size += 1;
    }

    public LoahNess removeFirst() {
        if (isEmpty())
            return null;
        else {
            sentinel.next = sentinel.next.next;
            sentinel.next.prev = sentinel;
            size -= 1;
            return sentinel.next.item;
        }
    }

    public LoahNess removeLast() {
        if (isEmpty())
            return null;
        else {
            sentinel.prev = sentinel.prev.prev;
            sentinel.prev.next = sentinel;
            size -= 1;
            return sentinel.prev.item;
        }
    }

    /** Retrieves the front item from the list. */
    public LoahNess get(int index) {
        StuffNode p = sentinel.next;
        for (int i=0; i<index; i++) {
            p = p.next;
        }
        if (isEmpty())
            return null;
        else
            return p.item;
    }

    public LoahNess getRecursiveHelper(int index, StuffNode p) {
        if (index == 0)
            return p.item;
        else
            return getRecursiveHelper (index-1, p.next);
    }

    public LoahNess getRecursive(int index) {
        if (index > this.size)
            return null;
        else
            return getRecursiveHelper (index, this.sentinel.next);
    }

    /** Crashes when you call addLast on an empty SLList. Fix it. */
    public static void main(String[] args) {
        LinkedListDeque<Integer> x = new LinkedListDeque<>();
        x.addFirst(5);
        x.addLast(10);
        x.addLast(15);
        //x.removeFirst();
        //x.removeLast();
        System.out.println(x.get(1));
        System.out.println(x.getRecursive(1));
        //System.out.println(x.isEmpty());
    }
}
