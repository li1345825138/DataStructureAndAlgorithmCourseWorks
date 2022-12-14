package DequesAndRandomizedQueues;

import stdlib.StdOut;

import java.util.Iterator;

/**
 * Implement an immutable, iterable data type called Primes to systematically iterate over the
 * first n primes.
 * An immutable data type to systematically iterate over the first n primes.
 *
 * @author Lengqiang Lin
 * @date 10/14/2022
 */
public class Primes implements Iterable<Integer> {
    private int n; // need first n primes

    /**
     * Constructs a Primes object given the number of primes needed.
     * @param n the number of primes needed
     */
    public Primes(int n) {
        this.n = n;
    }

    /**
     * Returns an iterator to iterate over the first n primes.
     * @return the PrimeIterator
     */
    public Iterator<Integer> iterator() {
        return new PrimesIterator();
    }

    /**
     * Primes iterator.
     */
    private class PrimesIterator implements Iterator<Integer> {
        private int count; // number of primes returned so far
        private int p;     // current prime

        /**
         * Constructs a PrimesIterator object.
         */
        public PrimesIterator() {
            this.count = 0;
            this.p = 2;
        }

        /**
         * Returns true if there are anymore primes to be iterated
         * @return true if there are anymore primes to be iterated, and false otherwise
         */
        public boolean hasNext() {
            return this.count < n;
        }

        /**
         * Returns the next prime.
         * @return the next prime
         */
        public Integer next() {
            // Increment count by 1.
            this.count++;

            // As long as p is not prime, increment p by 1.
            while (!isPrime(this.p)) {
                this.p++;
            }

            // Return current value of p and increment it by 1.
            return this.p++;
        }

        // Returns true if x is a prime, and false otherwise.
        private boolean isPrime(int x) {
            for (int i = 2; i <= x / i; i++) {
                if (x % i == 0) {
                    return false;
                }
            }
            return true;
        }
    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        for (int i : new Primes(n)) {
            StdOut.println(i);
        }
    }
}
