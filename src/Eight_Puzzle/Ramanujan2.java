package Eight_Puzzle;

import dsa.MinPQ;
import stdlib.StdOut;

/**
 * Write a program Ramanujan2.java that uses a minimum-oriented priority queue to solve
 * the problem from Exercise 2.
 *
 * @author Lengqiang Lin
 * @date 11/12/2022
 */
public class Ramanujan2 {
    // Entry point.
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        MinPQ<Pair> pq = new MinPQ<>();

        // Initialize a min-PQ pq with pairs (1, 2),(2, 3),(3, 4),...,(i, i + 1), where i < √3 n
        for (int i = 1; i * i * i < n; i++) {
            pq.insert(new Pair(i, i + 1));
        }
        Pair previous = null;       // previous pair
        Pair current = null;        // current pair

        // while pq Queue is not empty
        while (!pq.isEmpty()) {

            // Remove the smallest pair (call it current) from pq.
            current = pq.delMin();

            // Print the previous pair (k, l) and current pair (i, j)
            // if k^3 + l^3 = i^3 + j^3 <= n
            if (previous != null && current.sumOfCubes == previous.sumOfCubes
                    && current.sumOfCubes <= n) {
                StdOut.printf("%d = %d^3 + %d^3 = %d^3 + %d^3\n", current.sumOfCubes, previous.i,
                        previous.j, current.i, current.j);
            }

            // If i < √3 n, insert the pair (i, j + 1) into pq
            if (current.j * current.j * current.j < n) {
                pq.insert(new Pair(current.i, current.j + 1));
            }
            previous = current;
        }
    }

    // A data type that encapsulates a pair of numbers (i, j) and the sum of their cubes.
    private static class Pair implements Comparable<Pair> {
        private int i;          // first number in the pair
        private int j;          // second number in the pair
        private int sumOfCubes; // i^3 + j^3

        // Constructs a pair (i, j).
        public Pair(int i, int j) {
            this.i = i;
            this.j = j;
            sumOfCubes = i * i * i + j * j * j;
        }

        // Returns a comparison of pairs this and other based on their sum-of-cubes values.
        public int compareTo(Pair other) {
            return sumOfCubes - other.sumOfCubes;
        }
    }
}
