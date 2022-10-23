package DequesAndRandomizedQueues;

import dsa.LinkedStack;
import stdlib.StdIn;
import stdlib.StdOut;

/**
 * Implement a program called Sort.java that accepts strings from standard input, stores them
 * in a LinkedDeque data structure, sorts the deque, and writes the sorted strings to standard
 * output.
 *
 * @author Lengqiang Lin
 * @date 10/19/2022
 */
public class Sort {
    // Entry point.
    public static void main(String[] args) {
        LinkedDeque<String> d = new LinkedDeque<>();
        LinkedStack<String> s = new LinkedStack<>();
        String w = null;
        do {
            w = StdIn.readString();


            if (d.isEmpty()) { // if d container is empty
                d.addFirst(w);
            } else if (less(w, d.peekFirst())) { // if w is smaller than the first element in d
                d.addFirst(w);
            } else if (!less(w, d.peekLast())) { // if w is larger than the last element in d
                d.addLast(w);
            } else { // if w is between the first and last element in d
                while (w.compareTo(d.peekFirst()) != 0 && !less(w, d.peekFirst())) {
                    s.push(d.removeFirst());
                }
                d.addFirst(w);
                while (!s.isEmpty()) {
                    d.addFirst(s.pop());
                }
            }
        } while (!StdIn.isEmpty());

        // print the sorted deque
        for (String tmp : d) {
            StdOut.println(tmp);
        }
    }

    // Returns true if v is less than w according to their lexicographic order, and false otherwise.
    private static boolean less(String v, String w) {
        return v.compareTo(w) < 0;
    }
}
