package percolation;

import stdlib.StdOut;

/**
 * Implement the static method isPrime() in PrimeCounter.java that accepts an integer x and returns
 * true if x is prime and false otherwise. Also implement the static method primes() that accepts
 * an integer n and returns the number of primes less than or equal to n — a number x is prime if
 * it is not divisible by any number i ∈ [2, √x.
 *
 * @date 09/27/2022
 */
public class PrimeCounter {
    // Entry point. [DO NOT EDIT]
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        StdOut.println(primes(n));
    }

    // Returns true if x is prime; and false otherwise.
    private static boolean isPrime(int x) {
        // For each 2 <= i <= x / i, if x is divisible by i, then x is not a prime. If no such i
        // exists, then x is a prime.
        if (x <= 1) {
            return false;
        } else if (x == 2) {
            return true;
        }
        for (int i = 2; i <= x / i; i++) {
            if (x % i == 0) {
                return false;
            }
        }
        return true;
    }

    // Returns the number of primes <= n.
    private static int primes(int n) {
        // For each 2 <= i <= n, use isPrime() to test if i is prime, and if so increment a count.
        // At the end return the count.
        int count = 0;
        for (int i = 2; i <= n; i++) {

            // if current i is prime, increment a count
            if (isPrime(i)) {
                count++;
            }
        }
        return count;
    }
}
