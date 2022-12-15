package WordNet;

import dsa.Graph;
import dsa.LinkedQueue;
import dsa.RedBlackBinarySearchTreeST;
import stdlib.In;
import stdlib.StdOut;


import java.util.Arrays;

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
        this.st = new RedBlackBinarySearchTreeST<>();
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

        // Calculate avgPathLength
        this.avgPathLength = findAllPaths(G);

        // Calculate clusteringCoefficient
        this.clusteringCoefficient = calculateClusteringCoefficient(G, vertex);
    }

    /**
     * Helper method: Calculate out clustering Coefficient
     *
     * @param G graph
     * @return clustering Coefficient
     */
    private double calculateClusteringCoefficient(Graph G, int n) {
        double sum = 0;
        for (int v = 0; v < n; v++) {
            Iterable<Integer> adj = G.adj(v);
            int countNeibors = 0;
            for (int w1 : adj) {
                for (int w2 : adj) {
                    if (hasEdge(G, w1, w2)) {
                        countNeibors++;
                    }
                }
            }
            int N = G.degree(v);
            if (N > 1) {
                int maxNumEdgesInNeighborhood = N * (N - 1);
                double localCoefficient = (double) countNeibors / maxNumEdgesInNeighborhood;
                sum += localCoefficient;
            }
        }
        return sum / n;
    }

    /**
     * Helper Method: To find all path and return avgPathLength
     *
     * @param G graph
     * @return Average Path Length
     */
    private double findAllPaths(Graph G) {
        double totalPathLength = 0;
        for (int v = 0; v < G.V(); v++) {
            for (int w = v + 1; w < G.V(); w++) {
                int distance = findShortestPath(G, v, w);
                if (distance != Integer.MAX_VALUE) {
                    totalPathLength += distance;
                }
            }
        }
        int numPairs = G.V() * (G.V() - 1) / 2;
        return totalPathLength / numPairs;
    }

    /**
     * Helper method: finds the shortest path from v to w using BreathFirstSearch
     *
     * @param G graph
     * @param v v vertex
     * @param w target
     * @return distant to
     */
    private int findShortestPath(Graph G, int v, int w) {
        LinkedQueue<Integer> queue = new LinkedQueue<>();
        queue.enqueue(v);
        int[] disTo = new int[G.V()];
        Arrays.fill(disTo, Integer.MAX_VALUE);
        disTo[v] = 0;
        while (!queue.isEmpty()) {
            int curr = queue.dequeue();
            for (int neibors : G.adj(curr)) {
                if (disTo[neibors] == Integer.MAX_VALUE) {
                    disTo[neibors] = disTo[curr] + 1;
                    queue.enqueue(neibors);
                }
            }
        }
        return disTo[w];
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
