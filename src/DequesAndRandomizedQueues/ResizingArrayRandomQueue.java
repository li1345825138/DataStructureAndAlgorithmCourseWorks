package DequesAndRandomizedQueues;

import java.util.Iterator;
import java.util.NoSuchElementException;

import stdlib.StdOut;
import stdlib.StdRandom;

// A data type to represent a random queue, implemented using a resizing array as the underlying
// data structure.
public class ResizingArrayRandomQueue<Item> implements Iterable<Item> {
    private Item[] q;       // item to store on this queue
    private int n;          // number of element store on this queue

    // Constructs an empty random queue.
    public ResizingArrayRandomQueue() {
        this.q = (Item[]) new Object[2];
        this.n = 0;
    }

    // Returns true if this queue is empty, and false otherwise.
    public boolean isEmpty() {
        return this.n == 0;
    }

    // Returns the number of items in this queue.
    public int size() {
        return this.n;
    }

    // Adds item to the end of this queue.
    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException("item is null");
        }

        // if current queue is already full, then resize
        if (this.n == this.q.length) {
            resize(this.n * 2);
        }
        this.q[this.n++] = item;
    }

    // Returns a random item from this queue.
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("Random queue is empty");
        }
        int r = StdRandom.uniform(this.n);
        return this.q[r];
    }

    // Removes and returns a random item from this queue.
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Random queue is empty");
        }
        int r = StdRandom.uniform(this.n);
        Item item = this.q[r];
        this.q[r] = this.q[this.n - 1];
        this.q[this.n - 1] = null;

        // if current queue size is quarter capacity of n
        if (this.q.length / 4 == this.n) {
            resize(this.q.length / 2);
        }
        this.n--;
        return item;
    }

    // Returns an independent iterator to iterate over the items in this queue in random order.
    public Iterator<Item> iterator() {
        return new RandomQueueIterator();
    }

    // Returns a string representation of this queue.
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Item item : this) {
            sb.append(item);
            sb.append(", ");
        }
        return n > 0 ? "[" + sb.substring(0, sb.length() - 2) + "]" : "[]";
    }

    // An iterator, doesn't implement remove() since it's optional.
    private class RandomQueueIterator implements Iterator<Item> {
        private Item[] items;           // iterator items queue
        private int current;            // current item index

        // Constructs an iterator.
        public RandomQueueIterator() {
            this.items = (Item[]) new Object[n];
            for (int i = 0; i < n; i++) {
                this.items[i] = q[i];
            }
            StdRandom.shuffle(items);
            this.current = 0;
        }

        // Returns true if there are more items to iterate, and false otherwise.
        public boolean hasNext() {
            return this.current < n;
        }

        // Returns the next item.
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Iterator is empty");
            }
            return this.items[this.current++];
        }
    }

    // Resizes the underlying array.
    private void resize(int max) {
        Item[] temp = (Item[]) new Object[max];
        for (int i = 0; i < n; i++) {
            if (q[i] != null) {
                temp[i] = q[i];
            }
        }
        q = temp;
    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        ResizingArrayRandomQueue<Integer> q = new ResizingArrayRandomQueue<Integer>();
        int sum = 0;
        for (int i = 0; i < 1000; i++) {
            int r = StdRandom.uniform(10000);
            q.enqueue(r);
            sum += r;
        }
        int iterSumQ = 0;
        for (int x : q) {
            iterSumQ += x;
        }
        int dequeSumQ = 0;
        while (q.size() > 0) {
            dequeSumQ += q.dequeue();
        }
        StdOut.println("sum       = " + sum);
        StdOut.println("iterSumQ  = " + iterSumQ);
        StdOut.println("dequeSumQ = " + dequeSumQ);
        StdOut.println("iterSumQ + dequeSumQ == 2 * sum? " + (iterSumQ + dequeSumQ == 2 * sum));
    }
}
