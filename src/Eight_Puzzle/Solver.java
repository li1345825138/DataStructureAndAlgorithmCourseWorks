package Eight_Puzzle;

import dsa.LinkedStack;
import dsa.MinPQ;
import stdlib.In;
import stdlib.StdOut;

/**
 * Implement an immutable data type called Solver that uses the A* algorithm to solve the
 * 8-puzzle and its generalizations
 *
 * @author Lengqiang Lin
 * @date 11/14/2022
 */
public class Solver {
    // Minimum number of moves needed to solve the initial board
    private int moves;

    // Sequence of boards in a shortest solution of the initial board
    private LinkedStack<Board> solution;

    /**
     * Finds a solution to the initial board using the A* algorithm.
     * @param board board object
     */
    public Solver(Board board) {
        if (board == null) {
            throw new NullPointerException("board is null");
        } else if (!board.isSolvable()) {
            throw new IllegalArgumentException("board is\n" +
                    "unsolvable");
        }
        this.moves = 0;
        this.solution = new LinkedStack<>();

        // create search node and insert to priority queue
        MinPQ<SearchNode> pq = new MinPQ<>();
        pq.insert(new SearchNode(board, 0, null));

        // As long as pq is not empty
        SearchNode node = null;
        while (!pq.isEmpty()) {
            // Remove the smallest node (call it node) from pq
            node = pq.delMin();

            // If the board in node is the goal board, extract from the node the number of moves
            // in the solution and the solution and store the values in the instance variables
            // moves and solution respectively, and break.
            if (node.board.isGoal()) {
                for (SearchNode x = node; x.previous != null;x = x.previous) {
                    this.solution.push(x.board);
                }
                break;
            } else {
                // Otherwise, iterate over the neighboring boards of node.board, and for each
                // neighbor that is different from node.previous.board, insert a new SearchNode
                // object into pq, constructed using appropriate values.
                for (Board x : node.board.neighbors()) {
                    pq.insert(new SearchNode(x, this.moves, node));
                }
                this.moves++;
            }
        }
    }

    /**
     * Returns the minimum number of moves needed to solve the initial board.
     * @return minimum number of moves needed to solve the initial board
     */
    public int moves() {
        return this.moves;
    }

    /**
     * Returns a sequence of boards in a shortest solution of the initial board.
     * @return sequence of boards in a shortest solution of the initial board
     */
    public Iterable<Board> solution() {
        return this.solution;
    }

    /**
     * A data type that represents a search node in the grame tree. Each node includes a
     * reference to a board, the number of moves to the node from the initial node, and a
     * reference to the previous node.
     */
    private class SearchNode implements Comparable<SearchNode> {
        private Board board;    // The board represented by this node
        private int moves;      // number of move from initial node
        private SearchNode previous;    // previous node

        /**
         * Constructs a new search node.
         * @param board board object
         * @param moves moves needed
         * @param previous previouse search
         */
        public SearchNode(Board board, int moves, SearchNode previous) {
            this.board = board;
            this.moves = moves;
            this.previous = previous;
        }

        /**
         * Returns a comparison of this node and other based on the following sum:
         * Manhattan distance of the board in the node + the # of moves to the node
         * @param other the object to be compared.
         * @return comparison of this node and other
         */
        public int compareTo(SearchNode other) {
            int sum1 = this.board.manhattan() + this.moves;
            int sum2 = other.board.manhattan() + other.moves;
            return sum1 - sum2;
        }
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
        Board initial = new Board(tiles);
        if (initial.isSolvable()) {
            Solver solver = new Solver(initial);
            StdOut.printf("Solution (%d moves):\n", solver.moves());
            StdOut.println(initial);
            StdOut.println("----------");
            for (Board board : solver.solution()) {
                StdOut.println(board);
                StdOut.println("----------");
            }
        } else {
            StdOut.println("Unsolvable puzzle");
        }
    }
}
