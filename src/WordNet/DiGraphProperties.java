package WordNet;

import dsa.DiGraph;
import dsa.LinkedBag;
import dsa.Topological;
import stdlib.In;
import stdlib.StdOut;

/**
 * Consider a digraph G with V vertices.
 *
 * @author Lengqiang Lin
 * @date 12/09/2022
 */
public class DiGraphProperties {
    private boolean isDAG;              // is the digraph a DAG?
    private boolean isMap;              // is the digraph a map?
    private LinkedBag<Integer> sources; // the sources in the digraph
    private LinkedBag<Integer> sinks;   // the sinks in the digraph

    /**
     * Computes graph properties for the digraph G.
     *
     * @param G a DiGraph
     */
    public DiGraphProperties(DiGraph G) {
        // find is DiGraph is DAG
        Topological topological = new Topological(G);
        this.isDAG = topological.hasOrder();

        // check if the DiGraph is a map
        this.isMap = true;
        for (int v = 0; v < G.V(); v++) {
            if (G.outDegree(v) != 1) {
                this.isMap = false;
                break;
            }
        }

        // find the sources and sinks
        this.sources = new LinkedBag<>();
        this.sinks = new LinkedBag<>();
        for (int v = 0; v < G.V(); v++) {
            if (G.inDegree(v) == 0) {
                this.sources.add(v);
            }
            if (G.outDegree(v) == 0) {
                this.sinks.add(v);
            }
        }
    }

    /**
     * Returns true if the digraph is a directed acyclic graph (DAG), and false otherwise.
     *
     * @return true if the digraph is a directed acyclic graph, false otherwise
     */
    public boolean isDAG() {
        return this.isDAG;
    }

    /**
     * Returns true if the digraph is a map, and false otherwise.
     *
     * @return true if the digraph is a map, and false otherwise
     */
    public boolean isMap() {
        return this.isMap;
    }

    /**
     * Returns all the sources (ie, vertices without any incoming edges) in the digraph.
     *
     * @return all the sources
     */
    public Iterable<Integer> sources() {
        return this.sources;
    }

    /**
     * Returns all the sinks (ie, vertices without any outgoing edges) in the digraph.
     *
     * @return all the sinks
     */
    public Iterable<Integer> sinks() {
        return this.sinks;
    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        In in = new In(args[0]);
        DiGraph G = new DiGraph(in);
        DiGraphProperties gp = new DiGraphProperties(G);
        StdOut.print("Sources: ");
        for (int v : gp.sources()) {
            StdOut.print(v + " ");
        }
        StdOut.println();
        StdOut.print("Sinks: ");
        for (int v : gp.sinks()) {
            StdOut.print(v + " ");
        }
        StdOut.println();
        StdOut.println("Is DAG? " + gp.isDAG());
        StdOut.println("Is Map? " + gp.isMap());
    }
}
