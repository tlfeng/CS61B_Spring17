/** Array based list.*/

//         0 1  2 3 4 5 6 7
// items: [6 9 -1 2 0 0 0 0 ...]
// size: 5

public class ArrayDeque {
    private int[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    /** Creates an empty list. */
    public ArrayDeque() {
        int originalCapacity = 8;
        items = new int[originalCapacity];
        size = 0;
        nextFirst=0; nextLast=0;
    }

    public int minusOne(int index) {
        int before = index-1;
        if (before < 0)
            before += items.length;
        return before;
    }

    public int plusOne(int index) {
        int after = index+1;
        if (after > items.length-1)
            after -= items.length;
        return after;
    }

    /** Inserts X into the front of the list. */
    public void addFirst(int x) {
        // when first position already has item
        if(size == 1 && nextFirst != 0)
            nextFirst = minusOne(nextFirst);

        if(isFull())
            items = resize("expand");

        items[nextFirst] = x;
        size ++;
        nextFirst = minusOne(nextFirst);
    }

    /** Inserts X into the back of the list. */
    public void addLast(int x) {
        // when first position already has item
        if(size == 1 && nextFirst != 0)
            nextLast = plusOne(nextLast);

        if(isFull())
            items = resize("expand");

        items[nextLast] = x;
        size ++;
        nextLast = plusOne(nextLast);
    }

    /** Gets the ith item in the list (0 is the front). */
    public int get(int index) {
        index += plusOne(nextFirst);
        if (index>=items.length)
            index -= items.length;
        return items[index];
    }

    public boolean isEmpty() {
        if (size ==0)
            return true;
        else
            return false;
    }

    /** Returns the number of items in the list. */
    public int size() {
        return size;
    }

    public void printDeque() {
        int index = plusOne(nextFirst);
        for (int i=0; i<size(); i++) {
            System.out.print(items[index]+" ");
            index = plusOne(index);
        }
    }

    public int removeFirst() {
        nextFirst = plusOne(nextFirst);
        size --;

        // optimize memory
        double usageFactor = 0.25;
        if (items.length>=16 &&
                (double)size()/items.length < usageFactor)
            items = resize("shrink");

        return items[plusOne(nextFirst)];
    }

    /** Deletes item from back of the list and
      * returns deleted item. */
    public int removeLast() {
        nextLast = minusOne(nextFirst);
        size --;
        return items[minusOne(nextLast)];
    }

    public boolean isFull() {
        if (size == items.length)
            return true;
        else
            return false;
    }

    /** Create a bigger or smaller array,
     * and copy original elements in the new array*/
    public int[] resize(String mode) {
        int[] newitems = new int[1];

        if (mode.equals("expand")) {
            int refactor = 2;
            newitems = new int[size() * refactor];
        }
        if (mode.equals("shrink")) {
            double usageFactor = 0.25;
            int newSize = (int) Math.ceil(size() / usageFactor);
            newitems = new int[newSize];
        }

        //take original array in order as a line
        for (int i=0; i<this.size(); i++) {
            newitems[i] = this.get(i);
        }
        nextFirst = newitems.length-1;
        nextLast = size();

        return newitems;
    }

    public static void main(String[] args) {
        ArrayDeque x = new ArrayDeque();
        x.addFirst(3);
        x.addFirst(6);
        x.addLast(2);
        x.addLast(4);
        x.addLast(5);
        x.addLast(6);
        x.addLast(7);
        x.addLast(8);
        x.addFirst(9);
        x.removeFirst();x.removeFirst();x.removeFirst();
        x.removeFirst();x.removeFirst();x.removeFirst();
        x.removeFirst();
        //x.get(2);
        System.out.println(x.isFull());

    }
} 