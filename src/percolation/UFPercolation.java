package percolation;

import dsa.WeightedQuickUnionUF;
import stdlib.In;
import stdlib.StdOut;

/**
 * Develop a data type called UFPercolation that implements the Percolation interface using
 * a WeightedQuickUnionUF object as the underlying data structure.
 *
 * @author Lengqiang Lin
 * @date 10/01/2022
 */

// An implementation of the Percolation API using the UF data structure.
public class UFPercolation implements Percolation {
    private int n;                          // size
    private boolean[][] open;               // percolation system
    private int openSites;                  // number of open sites
    private WeightedQuickUnionUF uf;        // Union-find representation of the percolation system

    // Constructs an n x n percolation system, with all sites blocked.
    public UFPercolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Illegal n");
        }
        this.n = n;
        this.open = new boolean[this.n][this.n];
        this.openSites = 0;
        this.uf = new WeightedQuickUnionUF(this.n * this.n + 2);
    }

    // Opens site (i, j) if it is not already open.
    public void open(int i, int j) {
        if (i < 0 || i >= this.n || j < 0 || j >= this.n) {
            throw new IndexOutOfBoundsException("Illegal i or j");
        }
        if (!isOpen(i, j)) {
            this.open[i][j] = true;
            this.openSites++;

            int q = encode(i, j);
            if (i == 0) {
                this.uf.union(0, q);
            }
            if (i == this.n - 1 && !percolates()) {
                this.uf.union(this.n * this.n + 1, q);
            }
            if (i - 1 >= 0 && isOpen(i - 1, j)) {
                this.uf.union(encode(i - 1, j), q);
            }
            if (i + 1 < this.n && isOpen(i + 1, j)) {
                this.uf.union(encode(i + 1, j), q);
            }
            if (j - 1 >= 0 && isOpen(i, j - 1)) {
                this.uf.union(encode(i, j - 1), q);
            }
            if (j + 1 < this.n && isOpen(i, j + 1)) {
                this.uf.union(encode(i, j + 1), q);
            }
        }
    }

    // Returns true if site (i, j) is open, and false otherwise.
    public boolean isOpen(int i, int j) {
        if (i < 0 || i >= this.n || j < 0 || j >= this.n) {
            throw new IndexOutOfBoundsException("Illegal i or j");
        }
        return this.open[i][j];
    }

    // Returns true if site (i, j) is full, and false otherwise.
    public boolean isFull(int i, int j) {
        if (i < 0 || i > this.n || j < 0 || j > this.n) {
            throw new IndexOutOfBoundsException("Illegal i or j");
        }
        return isOpen(i, j) && this.uf.connected(0, encode(i, j));
    }

    // Returns the number of open sites.
    public int numberOfOpenSites() {
        return this.openSites;
    }

    // Returns true if this system percolates, and false otherwise.
    public boolean percolates() {
        return this.uf.connected(0, this.n * this.n + 1);
    }

    // Returns an integer ID (1n) for site (i, j).
    private int encode(int i, int j) {
        return i * this.n + j + 1;
    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        int n = in.readInt();
        UFPercolation perc = new UFPercolation(n);
        while (!in.isEmpty()) {
            int i = in.readInt();
            int j = in.readInt();
            perc.open(i, j);
        }
        StdOut.printf("%d x %d system:\n", n, n);
        StdOut.printf("  Open sites = %d\n", perc.numberOfOpenSites());
        StdOut.printf("  Percolates = %b\n", perc.percolates());
        if (args.length == 3) {
            int i = Integer.parseInt(args[1]);
            int j = Integer.parseInt(args[2]);
            StdOut.printf("  isFull(%d, %d) = %b\n", i, j, perc.isFull(i, j));
        }
    }
}
