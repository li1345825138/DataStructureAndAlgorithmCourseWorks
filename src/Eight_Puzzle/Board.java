package Eight_Puzzle;

import dsa.Inversions;
import dsa.LinkedQueue;
import stdlib.In;
import stdlib.StdOut;

/**
 * Implement an immutable data type called Board to represent a board in an n-puzzle,
 * supporting the following API
 *
 * @author Lengqiang Lin
 * @date 11/13/2022
 */
public class Board {
    private int[][] tiles;         // Titles in the board
    private int n;                  // board size
    private int hamming;            // hamming distance
    private int manhattan;          // Manhatten distance
    private int blankPos;           // Position of blank tile in row-major order


    /**
     * Constructs a board from an n x n array; tiles[i][j] is the tile at row i and column j, with 0
     * denoting the blank tile.
     *
     * @param tiles n x n array
     */
    public Board(int[][] tiles) {
        this.n = tiles.length;
        this.tiles = new int[this.n][this.n];
        int length2D = this.n * this.n;
        for (int i = 0; i < length2D; i++) {
            this.tiles[i / this.n][i % this.n] = tiles[i / this.n][i % this.n];
        }

        // calculate hamming
        for (int i = 0; i < length2D; i++) {
            if (tileAt(i / this.n, i % this.n) != this.n * (i / this.n) + (i % this.n) + 1
                    && tileAt(i / this.n, i % this.n) != 0) {
                this.hamming++;
            }
        }

        // calculate Mahattan
        for (int i = 0; i < length2D; i++) {
            if (tileAt(i / this.n, i % this.n) == 0) {
                continue;
            } else {
                int row = (tileAt(i / this.n, i % this.n) - 1) / this.n;
                int col = (tileAt(i / this.n, i % this.n) - 1) % this.n;
                this.manhattan += (Math.abs((i / this.n) - row) + Math.abs((i % this.n) - col));
            }
        }

        // calculate blank position
        for (int i = 0; i < length2D; i++) {
            if (tileAt(i / this.n, i % this.n) == 0) {
                this.blankPos = 1 + (i / this.n) * this.n + (i % this.n);
            }
        }
    }

    /**
     * Returns the size of this board.
     *
     * @return size of this board
     */
    public int size() {
        return this.n;
    }

    /**
     * Returns the tile at row i and column j of this board.
     *
     * @param i index of row
     * @param j index of column
     * @return tile at row i and column j of this board
     */
    public int tileAt(int i, int j) {
        return this.tiles[i][j];
    }

    /**
     * Returns Hamming distance between this board and the goal board.
     *
     * @return Hamming distance between this board and the goal board
     */
    public int hamming() {
        return this.hamming;
    }

    /**
     * Returns the Manhattan distance between this board and the goal board.
     *
     * @return Manhattan distance between this board and the goal board
     */
    public int manhattan() {
        return this.manhattan;
    }

    /**
     * Returns true if this board is the goal board, and false otherwise.
     *
     * @return true if this board is the goal board, and false otherwise.
     */
    public boolean isGoal() {
        return this.manhattan == 0;
    }

    /**
     * Returns true if this board is solvable, and false otherwise.
     *
     * @return true if this board is solvable, and false otherwise
     */
    public boolean isSolvable() {
        Integer[] tilesArray = new Integer[this.n * this.n - 1];
        int index = 0;
        for (int i = 0; i < tilesArray.length + 1; i++) {
            if (tileAt(i / this.n, i % this.n) <= 0) {
                continue;
            }
            tilesArray[index++] = tileAt(i / this.n, i % this.n);
        }

        int inversion = (int) Inversions.count(tilesArray);
        if (this.n % 2 != 0) {
            return inversion % 2 == 0;
        } else {
            int sum = ((blankPos - 1) / this.n) + inversion;
            return sum % 2 != 0;
        }
    }

    /**
     * Returns an iterable object containing the neighboring boards of this board.
     *
     * @return iterable object containing the neighboring boards of this board
     */
    public Iterable<Board> neighbors() {
        LinkedQueue<Board> q = new LinkedQueue<>(); // board object
        int pos = blankPos - 1;
        int i = pos / n;
        int j = pos % n;
        int[][] temp = null;

        if (i < n - 1) {
            temp = cloneTiles();
            int t = temp[i][j];
            temp[i][j] = temp[i + 1][j];
            temp[i + 1][j] = t;
            q.enqueue(new Board(temp));
        }

        if (i > 0) {
            temp = cloneTiles();
            int t = temp[i][j];
            temp[i][j] = temp[i - 1][j];
            temp[i - 1][j] = t;
            q.enqueue(new Board(temp));
        }

        if (j < n - 1) {
            temp = cloneTiles();
            int t = temp[i][j];
            temp[i][j] = temp[i][j + 1];
            temp[i][j + 1] = t;
            q.enqueue(new Board(temp));
        }

        if (j > 0) {
            temp = cloneTiles();
            int t = temp[i][j];
            temp[i][j] = temp[i][j - 1];
            temp[i][j - 1] = t;
            q.enqueue(new Board(temp));
        }
        return q;
    }

    /**
     * Returns true if this board is the same as other, and false otherwise.
     *
     * @param other other Board Object
     * @return true if this board is the same as other, and false otherwise
     */
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other == this) {
            return true;
        }
        if (other.getClass() != this.getClass()) {
            return false;
        }
        Board tmpBoard = (Board) other;
        if (tmpBoard.n != this.n) {
            return false;
        }
        int length2D = this.n * this.n;
        for (int i = 0; i < length2D; i++) {
            if (tileAt(i / this.n, i % this.n) != tmpBoard.tileAt(i / this.n, i % this.n)) {
                return false;
            }
        }
        return true;
    }

    // Returns a string representation of this board.
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2s", tiles[i][j] == 0 ? " " : tiles[i][j]));
                if (j < n - 1) {
                    s.append(" ");
                }
            }
            if (i < n - 1) {
                s.append("\n");
            }
        }
        return s.toString();
    }

    // Returns a defensive copy of tiles[][].
    private int[][] cloneTiles() {
        int[][] clone = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                clone[i][j] = tiles[i][j];
            }
        }
        return clone;
    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = in.readInt();
            }
        }
        Board board = new Board(tiles);
        StdOut.printf("The board (%d-puzzle):\n%s\n", n, board);
        String f = "Hamming = %d, Manhattan = %d, Goal? %s, Solvable? %s\n";
        StdOut.printf(f, board.hamming(), board.manhattan(), board.isGoal(), board.isSolvable());
        StdOut.println("Neighboring boards:");
        for (Board neighbor : board.neighbors()) {
            StdOut.println(neighbor);
            StdOut.println("----------");
        }
    }
}
