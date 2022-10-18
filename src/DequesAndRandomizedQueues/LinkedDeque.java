package DequesAndRandomizedQueues;

import java.util.Iterator;
import java.util.NoSuchElementException;

import stdlib.StdOut;
import stdlib.StdRandom;

// A data type to represent a double-ended queue (aka deque), implemented using a doubly-linked
// list as the underlying data structure.
public class LinkedDeque<Item> implements Iterable<Item> {
    private Node first;             // Reference to the front of the deque,
    private Node last;              // Reference to the last of the deque,
    private int n;                  // Size of the deque

    // Constructs an empty deque.
    public LinkedDeque() {
        this.first = null;
        this.last = null;
        this.n = 0;
    }

    // Returns true if this deque is empty, and false otherwise.
    public boolean isEmpty() {
        return this.n == 0;
    }

    // Returns the number of items in this deque.
    public int size() {
        return this.n;
    }

    // Adds item to the front of this deque.
    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException("item is null");
        }

        Node newNode = new Node();
        newNode.item = item;
        newNode.prev = null;
        newNode.next = null;
        if (this.first == null) {
            this.last = newNode;
        } else {
            newNode.next = this.first;
            this.first.prev = newNode;
        }
        this.first = newNode;
        this.n++;
    }

    // Adds item to the back of this deque.
    public void addLast(Item item) {
        if (item == null) {
            throw new NullPointerException("item is null");
        }

        Node newNode = new Node();
        newNode.item = item;
        newNode.prev = null;
        newNode.next = null;
        if (this.last == null) {
            this.first = newNode;
        } else {
            this.last.next = newNode;
            newNode.prev = this.last;
        }
        this.last = newNode;
        this.n++;
    }

    // Returns the item at the front of this deque.
    public Item peekFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }
        return this.first.item;
    }

    // Removes and returns the item at the front of this deque.
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }
        Node tmp = this.first;
        this.first = this.first.next;
        if (this.first == null) {
            this.last = null;
        } else {
            this.first.prev = null;
        }
        tmp.next = null;
        this.n--;
        return tmp.item;
    }

    // Returns the item at the back of this deque.
    public Item peekLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }
        return this.last.item;
    }

    // Removes and returns the item at the back of this deque.
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }
        Node tmp = this.last;
        this.last = this.last.prev;
        if (this.last == null) {
            this.first = null;
        } else {
            this.last.next = null;
        }
        tmp.prev = null;
        this.n--;
        return tmp.item;
    }

    // Returns an iterator to iterate over the items in this deque from front to back.
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    // Returns a string representation of this deque.
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Item item : this) {
            sb.append(item);
            sb.append(", ");
        }
        return n > 0 ? "[" + sb.substring(0, sb.length() - 2) + "]" : "[]";
    }

    // A deque iterator.
    private class DequeIterator implements Iterator<Item> {
        private Node current;       // current iterator node

        // Constructs an iterator.
        public DequeIterator() {
            this.current = first;
        }

        // Returns true if there are more items to iterate, and false otherwise.
        public boolean hasNext() {
            return this.current != null;
        }

        // Returns the next item.
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Iterator is empty");
            }
            Node tmp = this.current;
            this.current = this.current.next;
            return tmp.item;
        }
    }

    // A data type to represent a doubly-linked list. Each node in the list stores a generic item
    // and references to the next and previous nodes in the list.
    private class Node {
        private Item item;  // the item
        private Node next;  // the next node
        private Node prev;  // the previous node
    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        LinkedDeque<Character> deque = new LinkedDeque<Character>();
        String quote = "There is grandeur in this view of life, with its several powers, having " +
                "been originally breathed into a few forms or into one; and that, whilst this " +
                "planet has gone cycling on according to the fixed law of gravity, from so simple" +
                " a beginning endless forms most beautiful and most wonderful have been, and are " +
                "being, evolved. ~ Charles Darwin, The Origin of Species";
        int r = StdRandom.uniform(0, quote.length());
        StdOut.println("Filling the deque...");
        for (int i = quote.substring(0, r).length() - 1; i >= 0; i--) {
            deque.addFirst(quote.charAt(i));
        }
        for (int i = 0; i < quote.substring(r).length(); i++) {
            deque.addLast(quote.charAt(r + i));
        }
        StdOut.printf("The deque (%d characters): ", deque.size());
        for (char c : deque) {
            StdOut.print(c);
        }
        StdOut.println();
        StdOut.println("Emptying the deque...");
        double s = StdRandom.uniform();
        for (int i = 0; i < quote.length(); i++) {
            if (StdRandom.bernoulli(s)) {
                deque.removeFirst();
            } else {
                deque.removeLast();
            }
        }
        StdOut.println("deque.isEmpty()? " + deque.isEmpty());
    }
}
