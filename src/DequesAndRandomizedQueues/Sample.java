package DequesAndRandomizedQueues;

import stdlib.StdOut;

/**
 *  Implement a program called Sample.java that accepts lo (int), hi (int), k (int), and mode
 * (String) as command-line arguments, uses a random queue to sample k integers from the
 * interval [lo, hi], and writes the samples to standard output.
 *
 * @author Lengqiang Lin
 * @date 10/20/2022
 */
public class Sample {
    // Entry point.
    public static void main(String[] args) {
        String mode = args[3];

        // if mode is not + or -, then nothing need to be done
        if (!mode.equals("+") && !mode.equals("-")) {
            throw new IllegalArgumentException("Illegal mode");
        }

        // accept other command arguments
        int lo = Integer.parseInt(args[0]);
        int hi = Integer.parseInt(args[1]);
        int k = Integer.parseInt(args[2]);

        // random queue, contain integer from [lo, hi] interval
        ResizingArrayRandomQueue<Integer> q = new ResizingArrayRandomQueue<>();
        for (int i = lo; i <= hi; i++) {
            q.enqueue(i);
        }

        // do things base on + or - mode
        switch (mode) {
            case "+":
                for (int i = 0; i < k; i++) {
                    StdOut.println(q.sample());
                }
                break;
            case "-":
                for (int i = 0; i < k; i++) {
                    StdOut.println(q.dequeue());
                }
        }
    }
}
