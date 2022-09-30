package percolation;

import stdlib.StdArrayIO;
import stdlib.StdOut;

/**
 * Implement the static method distance() in Distance.java that accepts position vectors x and y
 * each represented as a 1D array of doubles — and returns the Euclidean distance between the two
 * vectors, calculated as the square root of the sums of the squares of the differences between
 * the corresponding entries.
 *
 * @author Lengqiang Lin
 * @date 09/28/2022
 */
public class Distance {
    // Entry point. [DO NOT EDIT]
    public static void main(String[] args) {
        double[] x = StdArrayIO.readDouble1D();
        double[] y = StdArrayIO.readDouble1D();
        StdOut.println(distance(x, y));
    }

    // Returns the Euclidean distance between the position vectors x and y.
    private static double distance(double[] x, double[] y) {
        // Sum up the squares of (x[i] - y[i]), where 0 <= i < x.length, and return the square
        // root of the sum.
        double sum = 0.0;

        // Sum up the squares of (x[i] - y[i]), where 0 <= i < x.length
        for (int i = 0; i < x.length; i++) {
            sum += Math.pow((x[i] - y[i]), 2);
        }

        // return the square root of the sum.
        return Math.sqrt(sum);
    }
}
