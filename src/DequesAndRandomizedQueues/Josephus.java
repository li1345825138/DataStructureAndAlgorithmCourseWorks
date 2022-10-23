package DequesAndRandomizedQueues;

import dsa.LinkedQueue;
import stdlib.StdOut;

/**
 * In the Josephus problem from antiquity, n people are in dire straits and agree to the
 * following strategy to reduce the population. They arrange themselves in a circle
 * (at positions numbered from 1 to n) and proceed around the circle, eliminating every mth person
 * until only one person is left. Legend has it that Josephus figured out
 * where to sit to avoid being eliminated. Implement a program Josephus.java
 * that accepts n (int) and m (int) as command-line
 * arguments, and writes to standard output the order in which people are eliminated
 * (and thus would show Josephus where to sit in the circle).
 *
 * @author Lengqiang Lin
 * @date 10/17/2022
 */
public class Josephus {
    // Entry point.
    public static void main(String[] args) {
        // Accept n (int) and m (int) as command-line arguments.
        int n = Integer.parseInt(args[0]);
        int m = Integer.parseInt(args[1]);

        // Create a queue q and enqueue integers 1, 2, ..., n.
        LinkedQueue<Integer> q = new LinkedQueue<>();
        for (int i = 1; i <= n; i++) {
            q.enqueue(i);
        }

        // Set i to 0. As long as q is not empty: increment i; dequeue an element (say pos); if m
        // divides i, write pos to standard output, otherwise enqueue pos to q.
        int i = 0;
        int pos = 0;
        while (!q.isEmpty()) {
            i++;
            pos = q.dequeue();
            if (i % m == 0) {
                StdOut.println(pos);
            } else {
                q.enqueue(pos);
            }
        }
    }
}
