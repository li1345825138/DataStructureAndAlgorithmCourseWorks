package percolation;

import stdlib.StdOut;

/**
 * Write a program called Harmonic.java that accepts n (int) as command-line argument,
 * computes the nth harmonic number Hn as a rational number, and writes the value to standard
 * output.
 *
 * @date 09/29/2022
 */

public class Harmonic {
    // Entry point.
    public static void main(String[] args) {
        // Accept n (int) as command-line argument.
        int n = Integer.parseInt(args[0]);

        // Set total to the rational number 0.
        Rational total = new Rational(0, 1);

        // For each 1 <= i <= n, add the rational term 1 / i to total.
        for (int i = 1; i <= n; i++) {
            Rational term = new Rational(1, i);
            total = total.add(term);
        }

        // Write total to standard output.
        StdOut.println(total);
    }
}
