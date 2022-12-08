package WordNet;

import dsa.Graph;
import dsa.LinkedQueue;
import dsa.RedBlackBinarySearchTreeST;
import stdlib.In;
import stdlib.StdOut;

/**
 * Consider an undirected graph G with V vertices and E edges
 *
 * @author Lengqiang Lin
 * @date 12/08/2022
 */
public class GraphProperties {
    private RedBlackBinarySearchTreeST<Integer, Integer> st; // degree -> frequency
    private double avgDegree;                                // average degree of the graph
    private double avgPathLength;                            // average path length of the graph
    private double clusteringCoefficient;                    // clustering coefficient of the graph

    /**
     * Computes graph properties for the undirected graph G.
     *
     * @param G graph
     */
    public GraphProperties(Graph G) {
        st = new RedBlackBinarySearchTreeST<>();
        int vertex = G.V();
        int edge = G.E();
        this.avgDegree = 2.0 * edge / vertex;
        int[] degree = new int[vertex];
        for (int v = 0; v < vertex; v++) {
            degree[v] = G.degree(v);
            if (!st.contains(degree[v])) {
                st.put(degree[v], 1);
            } else {
                st.put(degree[v], st.get(degree[v]) + 1);
            }
        }

        // find average path length by using findAllPath method
        int count = 0;
        double sum = 0;
        for (int v = 0; v < vertex; v++) {
            for (int w = v + 1; w < vertex; w++) {
                count++;
                sum += findAllPath(G, v, w);
            }
        }
        this.avgPathLength = sum / count;

        // calculate clustering coefficient of the graph
        this.clusteringCoefficient = calculateClusteringCoefficient(G) / vertex;
    }

    /**
     * Calculate Clustering Coefficient
     *
     * @param G graph
     * @return sum of total Clustering Coefficient
     */
    private double calculateClusteringCoefficient(Graph G) {
        double sumCC = 0;
        for (int v = 0; v < G.V(); v++) {
            int degreeV = G.degree(v);
            if (degreeV < 2) {
                continue;
            }
            int countEdge = 0;
            for (int w : G.adj(v)) {
                for (int x : G.adj(v)) {
                    if (w == x) {
                        continue;
                    }
                    if (hasEdge(G, w, x)) {
                        countEdge++;
                    }
                }
            }
            sumCC += (double) countEdge / (degreeV * (degreeV - 1));
        }
        return sumCC;
    }

    /**
     * Find all path by use BFS
     *
     * @param G graph
     * @param v vertex
     * @param w current visit
     * @return total path count
     */
    private int findAllPath(Graph G, int v, int w) {
        int[] edgeTo = new int[G.V()];
        boolean[] marked = new boolean[G.V()];
        LinkedQueue<Integer> queue = new LinkedQueue<>();
        queue.enqueue(v);
        marked[v] = true;
        while (!queue.isEmpty()) {
            int x = queue.dequeue();
            for (int y : G.adj(x)) {
                if (!marked[y]) {
                    edgeTo[y] = x;
                    marked[y] = true;
                    queue.enqueue(y);
                }
            }
        }
        int count = 0;
        for (int x = w; x != v; x = edgeTo[x]) {
            count++;
        }
        return count;
    }

    /**
     * Returns the degree distribution of the graph (a symbol table mapping each degree value to
     * the number of vertices with that value).
     *
     * @return degree distribution of the graph
     */
    public RedBlackBinarySearchTreeST<Integer, Integer> degreeDistribution() {
        return st;
    }

    /**
     * Returns the average degree of the graph.
     *
     * @return average degree of the graph
     */
    public double averageDegree() {
        return avgDegree;
    }

    /**
     * Returns the average path length of the graph.
     *
     * @return average path length of the graph
     */
    public double averagePathLength() {
        return avgPathLength;
    }

    /**
     * Returns the global clustering coefficient of the graph.
     *
     * @return global clustering coefficient of the graph
     */
    public double clusteringCoefficient() {
        return clusteringCoefficient;
    }

    // Returns true if G has an edge between vertices v and w, and false otherwise.
    private static boolean hasEdge(Graph G, int v, int w) {
        for (int u : G.adj(v)) {
            if (u == w) {
                return true;
            }
        }
        return false;
    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        In in = new In(args[0]);
        Graph G = new Graph(in);
        GraphProperties gp = new GraphProperties(G);
        RedBlackBinarySearchTreeST<Integer, Integer> st = gp.degreeDistribution();
        StdOut.println("Degree distribution:");
        for (int degree : st.keys()) {
            StdOut.println("  " + degree + ": " + st.get(degree));
        }
        StdOut.printf("Average degree         = %7.3f\n", gp.averageDegree());
        StdOut.printf("Average path length    = %7.3f\n", gp.averagePathLength());
        StdOut.printf("Clustering coefficient = %7.3f\n", gp.clusteringCoefficient());
    }
}
