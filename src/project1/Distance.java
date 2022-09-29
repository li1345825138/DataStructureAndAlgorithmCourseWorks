package project1;

/**
 * Implement the static method distance() in Distance.java that accepts position vectors x and y
 * each represented as a 1D array of doubles — and returns the Euclidean distance between the two
 * vectors, calculated as the square root of the sums of the squares of the differences between
 * the corresponding entries.
 *
 * @author Lengqiang Lin
 * @date
 */
public class Distance {
    public static void main(String[] args) {

    }

    /**
     * Accept vector x and y on 1D Array and calculate Euclidean distance
     * @param x x vector array
     * @param y y vectpr array
     * @return Euclidean distance
     */
    static double distance(double[] x, double[] y) {
        double sum = 0.0;

        // Sum up the squares of (x[i] - y[i]), where 0 <= i < x.length
        for (int i = 0; i < x.length; i++) {
            sum += Math.pow((x[i] - y[i]), 2);
        }

        // return the square root of the sum.
        return Math.sqrt(sum);
    }
}
