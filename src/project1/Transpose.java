package project1;

/**
 * Implement the static method tranpose() in Transpose.java that accepts a matrix x — represented
 * as a 2D array of doubles — and returns a new matrix that is the transpose of x.
 *
 * @author Lengqiang Lin
 * @date
 */
public class Transpose {
    public static void main(String[] args) {

    }

    /**
     * Create new transpose of x
     * @param x original transpose
     * @return new transpose
     */
    static double[][] transpose(double[][] x) {

        // Create a new 2D matrix t (for transpose) with dimensions n x m, where m x n are the
        // dimensions of x.
        int n = x.length;
        int m = x[0].length;
        double[][] t = new double[n][m];

        // For each 0 <= i < m and 0 <= j < n, set t[j][i] to x[i][j].
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                t[j][i] = x[i][j];
            }
        }
        return t;
    }
}
