package edu.umb.cs210.p6;

import dsa.DiGraph;
import dsa.LinkedQueue;
import dsa.SeparateChainingHashST;
import stdlib.In;
import stdlib.StdIn;
import stdlib.StdOut;

// An immutable data type for computing shortest common ancestors.
public class ShortestCommonAncestor {
    private DiGraph G; // DiGraph G

    // Construct a WordNet.ShortestCommonAncestor object given a rooted DAG.
    public ShortestCommonAncestor(DiGraph G) {
        if (G == null) {
            throw new NullPointerException("G is null");
        }
        this.G = G;
    }

    // Length of the shortest ancestral path between v and w.
    public int length(int v, int w) {
        if (!((v >= 0 || v <= G.V() - 1))) {
            throw new IllegalArgumentException("v is out of bounds");
        }
        if (!((w >= 0 || w <= G.V() - 1))) {
            throw new IllegalArgumentException("w is out of bounds");
        }
        int ancestor = ancestor(v, w);
        SeparateChainingHashST<Integer, Integer> hstV = distFrom(v);
        SeparateChainingHashST<Integer, Integer> hstW = distFrom(w);
        if (ancestor == -1) {
            return -1;
        }
        return hstV.get(ancestor) + hstW.get(ancestor);
    }

    // Shortest common ancestor of vertices v and w.
    public int ancestor(int v, int w) {
        if (!((v >= 0 && v <= G.V() - 1))) {
            throw new IndexOutOfBoundsException("v is out of bounds");
        }
        if (!((w >= 0 && w <= G.V() - 1))) {
            throw new IndexOutOfBoundsException("w is out of bounds");
        }
        SeparateChainingHashST<Integer, Integer> hstV = distFrom(v);
        SeparateChainingHashST<Integer, Integer> hstW = distFrom(w);
        int min = Integer.MAX_VALUE;
        int ancestor = -1;
        for (int i : hstV.keys()) {
            if (hstW.contains(i)) {
                int dist = hstV.get(i) + hstW.get(i);
                if (dist < min) {
                    min = dist;
                    ancestor = i;
                }
            }
        }
        return ancestor;
    }

    // Length of the shortest ancestral path of vertex subsets A and B.
    public int length(Iterable<Integer> A, Iterable<Integer> B) {
        if (A == null) {
            throw new NullPointerException("A is null");
        }
        if (B == null) {
            throw new NullPointerException("B is null");
        }
        if (!A.iterator().hasNext()) {
            throw new IllegalArgumentException("A is empty");
        }
        if (!B.iterator().hasNext()) {
            throw new IllegalArgumentException("B is empty");
        }
        int[] tr = triad(A, B);
        return distFrom(tr[1]).get(tr[0]) +
               distFrom(tr[2]).get(tr[0]);
    }

    // A shortest common ancestor of vertex subsets A and B.
    public int ancestor(Iterable<Integer> A, Iterable<Integer> B) {
        if (A == null) {
            throw new NullPointerException("A is null");
        }
        if (B == null) {
            throw new NullPointerException("B is null");
        }
        if (!A.iterator().hasNext()) {
            throw new IllegalArgumentException("A is empty");
        }
        if (!B.iterator().hasNext()) {
            throw new IllegalArgumentException("B is empty");
        }
        int[] arr = triad(A, B);
        return arr[0];
    }

    // Helper: Return a map of vertices reachable from v and their 
    // respective shortest distances from v.
    private SeparateChainingHashST<Integer, Integer> distFrom(int v) {
        SeparateChainingHashST<Integer, Integer> hst =
                new SeparateChainingHashST<>();
        hst.put(v, 0);
        LinkedQueue<Integer> pq = new LinkedQueue<>();
        pq.enqueue(v);
        while (!pq.isEmpty()) {
            int i = pq.dequeue();
            for (int w : G.adj(i)) {
                if (!hst.contains(w)) {
                    hst.put(w, hst.get(i) + 1);
                    pq.enqueue(w);
                }
            }
        }
        return hst;
    }

    // Helper: Return an array consisting of a shortest common ancestor a 
    // of vertex subsets A and B, and vertex v from A and vertex w from B 
    // such that the path v-a-w is the shortest ancestral path of A and B.
    private int[] triad(Iterable<Integer> A, Iterable<Integer> B) {
        if (A == null) {
            throw new NullPointerException("A is null");
        }
        if (B == null) {
            throw new NullPointerException("B is null");
        }
        int min = Integer.MAX_VALUE;
        int v = -1, w = -1;
        for (int minV : A) {
            for (int minW : B) {
                if (length(minV, minW) < min) {
                    min = length(minV, minW);
                    v = minV;
                    w = minW;
                }
            }
        }
        int[] arr = {ancestor(v, w), v, w};
        return arr;
    }

    // helper method that produces a defensive copy of G
    private DiGraph defensiveCopy(DiGraph g) {
        DiGraph copy = new DiGraph(g.V());
        for (int from = 0; from < g.V(); from++) {
            for (int to : g.adj(from)) {
                copy.addEdge(from, to);
            }
        }
        return copy;
    }

    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        In in = new In(args[0]);
        DiGraph G = new DiGraph(in);
        in.close();
        ShortestCommonAncestor sca = new ShortestCommonAncestor(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sca.length(v, w);
            int ancestor = sca.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
