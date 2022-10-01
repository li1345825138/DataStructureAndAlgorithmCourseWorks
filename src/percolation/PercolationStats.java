package percolation;

import stdlib.StdOut;
import stdlib.StdRandom;
import stdlib.StdStats;

/**
 * To estimate the percolation threshold, consider the following computational
 * (Monte Carlo simulation) experiment
 *
 * @date 10/01/2022
 */
public class PercolationStats {
    private UFPercolation uf;           // percolation
    private int experiments;            // experiments count
    private double[] fractions;         // fractions

    // Performs m independent experiments on an n x n percolation system.
    public PercolationStats(int n, int m) {
        if (n <= 0 || m <= 0) {
            throw new IllegalArgumentException("Illegal n or m");
        }
        this.experiments = m;
        this.fractions = new double[experiments];
        for (int i = 0; i < this.experiments; i++) {
            this.uf = new UFPercolation(n);
            int openSites = 0;
            while (!this.uf.percolates()) {
                int row = StdRandom.uniform(n);
                int col = StdRandom.uniform(n);
                if (!this.uf.isOpen(row, col)) {
                    this.uf.open(row, col);
                    openSites++;
                }
            }
            fractions[i] = (double) openSites / (n * n);
        }
    }

    // Returns sample mean of percolation threshold.
    public double mean() {
        return StdStats.mean(this.fractions);
    }

    // Returns sample standard deviation of percolation threshold.
    public double stddev() {
        return StdStats.stddev(this.fractions);
    }

    // Returns low endpoint of the 95% confidence interval.
    public double confidenceLow() {
        return mean() - ((1.96 * stddev()) / Math.sqrt(this.experiments));
    }

    // Returns high endpoint of the 95% confidence interval.
    public double confidenceHigh() {
        return mean() + ((1.96 * stddev()) / Math.sqrt(this.experiments));
    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int m = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, m);
        StdOut.printf("Percolation threshold for a %d x %d system:\n", n, n);
        StdOut.printf("  Mean                = %.3f\n", stats.mean());
        StdOut.printf("  Standard deviation  = %.3f\n", stats.stddev());
        StdOut.printf("  Confidence interval = [%.3f, %.3f]\n", stats.confidenceLow(),
                stats.confidenceHigh());
    }
}