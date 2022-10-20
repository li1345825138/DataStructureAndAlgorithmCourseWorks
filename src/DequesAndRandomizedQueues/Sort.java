package DequesAndRandomizedQueues;

import dsa.LinkedStack;

import stdlib.StdIn;
import stdlib.StdOut;

public class Sort {
    // Entry point.
    public static void main(String[] args) {
        LinkedDeque<String> d = new LinkedDeque<>();
        LinkedStack<String> s = new LinkedStack<>();
        String w = null;
        do {
            w = StdIn.readString();
            if (d.isEmpty()) {
                d.addFirst(w);
            } else if (less(w, d.peekFirst())) {
                d.addFirst(w);
            } else if (!less(w, d.peekLast())) {
                d.addLast(w);
            } else {
                while (w.compareTo(d.peekFirst()) != 0 && !less(w, d.peekFirst())) {
                    s.push(d.removeFirst());
                }
                d.addFirst(w);
                while (!s.isEmpty()) {
                    d.addFirst(s.pop());
                }
            }
        } while (!StdIn.isEmpty());

        for (String tmp : d) {
            StdOut.println(tmp);
        }
    }

    // Returns true if v is less than w according to their lexicographic order, and false otherwise.
    private static boolean less(String v, String w) {
        return v.compareTo(w) < 0;
    }
}
