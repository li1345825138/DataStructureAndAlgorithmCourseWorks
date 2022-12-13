package WordNet;

import dsa.DiGraph;
import dsa.LinkedQueue;
import dsa.SeparateChainingHashST;
import stdlib.In;
import stdlib.StdIn;
import stdlib.StdOut;

/**
 * An ancestral path between two vertices v and w in a rooted DAG is a directed path fromv to a
 * common ancestor x, together with a directed path from w to the same ancestor x. A shortest
 * ancestral path is anancestral path of minimum total length. We refer to the common ancestor in
 * a shortest ancestral path as a shortest commonancestor. Note that a shortest common ancestor
 * always exists because the root is an ancestor of every vertex. Note also thatan ancestral path
 * is a path, but not a directed path.
 *
 * @author Lengqiang Lin
 * @date 12/09/2022
 */
public class ShortestCommonAncestor {
    private DiGraph G;      // DiGraph G

    /**
     * Constructs a WordNet.ShortestCommonAncestor object given a rooted DAG.
     *
     * @param G DiGraph
     */
    public ShortestCommonAncestor(DiGraph G) {
        if (G == null) {
            throw new NullPointerException("G is null");
        }
        this.G = G;
    }

    /**
     * Returns length of the shortest ancestral path between vertices v and w.
     *
     * @param v vertices v
     * @param w vertices w
     * @return length of the shortest ancestral path
     */
    public int length(int v, int w) {
        if (v < 0 || v > G.V() - 1) {
            throw new IndexOutOfBoundsException("v is invalid");
        }
        if (w < 0 || w > G.V() - 1) {
            throw new IndexOutOfBoundsException("w is invalid");
        }
        int ancestor = ancestor(v, w);
        SeparateChainingHashST<Integer, Integer> hstV = distFrom(v);
        SeparateChainingHashST<Integer, Integer> hstW = distFrom(w);
        if (ancestor == -1) {
            return -1;
        }
        return hstV.get(ancestor) + hstW.get(ancestor);
    }

    /**
     * Returns a shortest common ancestor of vertices v and w.
     *
     * @param v vertices v
     * @param w vertices w
     * @return a shortest common ancestor of vertices v and w
     */
    public int ancestor(int v, int w) {
        if (v < 0 || v > G.V() - 1) {
            throw new IndexOutOfBoundsException("v is invalid");
        }
        if (w < 0 || w > G.V() - 1) {
            throw new IndexOutOfBoundsException("w is invalid");
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

    /**
     * Returns length of the shortest ancestral path of vertex subsets A and B.
     *
     * @param A subsets A
     * @param B subsets B
     * @return length of the shortest ancestral path
     */
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
        int[] triad = triad(A, B);
        return distFrom(triad[1]).get(triad[0]) + distFrom(triad[2]).get(triad[0]);
    }

    /**
     * Returns a shortest common ancestor of vertex subsets A and B.
     *
     * @param A subsets A
     * @param B subsets B
     * @return a shortest common ancestor
     */
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

    /**
     * Returns a map of vertices reachable from v and their respective shortest distances from v.
     *
     * @param v vertices v
     * @return a map of vertices reachable from v
     */
    private SeparateChainingHashST<Integer, Integer> distFrom(int v) {
        SeparateChainingHashST<Integer, Integer> hst = new SeparateChainingHashST<>();
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

    /**
     * Returns an array consisting of a shortest common ancestor a of vertex subsets A and B,
     * vertex v from A, and vertex w from B such that the path v-a-w is the shortest
     * ancestralpath of A and B.
     *
     * @param A subsets A
     * @param B subsets B
     * @return an array consisting of a shortest common ancestor
     */
    private int[] triad(Iterable<Integer> A, Iterable<Integer> B) {
        if (A == null) {
            throw new NullPointerException("A is null");
        }
        if (B == null) {
            throw new NullPointerException("B is null");
        }
        int min = Integer.MAX_VALUE;
        int v = -1;
        int w = -1;
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

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        In in = new In(args[0]);
        DiGraph G = new DiGraph(in);
        in.close();
        ShortestCommonAncestor sca = new ShortestCommonAncestor(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sca.length(v, w);
            int ancestor = sca.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
