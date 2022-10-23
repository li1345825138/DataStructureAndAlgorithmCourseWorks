package DequesAndRandomizedQueues;

import stdlib.StdOut;

import java.util.Iterator;

/**
 * An immutable data type to systematically iterate over binary strings of length n.
 * Implement an immutable, iterable data type called BinaryStrings to systematically iterate
 * over binary strings of length n
 *
 * @author Lengqiang Lin
 * @date 10/13/2022
 */
public class BinaryStrings implements Iterable<String> {
    private int n; // need all binary strings of length n

    /**
     * Constructs a BinaryStrings object given the length of binary strings needed.
     * @param n binary string width
     */
    public BinaryStrings(int n) {
        this.n = n;
    }

    /**
     * Returns an iterator to iterate over binary strings of length n.
     * @return the iterator of binary string
     */
    public Iterator<String> iterator() {
        return new BinaryStringsIterator();
    }

    /**
     * Binary strings iterator class implement Iterator interface
     */
    private class BinaryStringsIterator implements Iterator<String> {
        private int count; // number of binary strings returned so far
        private int p;     // current number in decimal

        /**
         * Constructs an iterator.
         */
        public BinaryStringsIterator() {
            this.count = 0;
            this.p = 0;
        }

        /**
         * Returns true if there are anymore binary strings to be iterated, and false otherwise.
         * @return is still have anymore binary strings
         */
        public boolean hasNext() {
            return this.count < (1 << n);
        }

        /**
         * Returns the next binary string.
         * @return next binary string
         */
        public String next() {
            this.count++;
            return binary(this.p++);
        }

        /**
         * Returns the n-bit binary representation of x.
         * @param x current turn into binary integer
         * @return binary string of current integer
         */
        private String binary(int x) {
            String s = Integer.toBinaryString(x);
            int padding = n - s.length();
            for (int i = 1; i <= padding; i++) {
                s = "0" + s;
            }
            return s;
        }
    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        for (String s : new BinaryStrings(n)) {
            StdOut.println(s);
        }
    }
}
