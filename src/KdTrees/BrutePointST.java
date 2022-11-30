package KdTrees;

import dsa.LinkedQueue;
import dsa.MinPQ;
import dsa.Point2D;
import dsa.RectHV;
import dsa.RedBlackBinarySearchTreeST;
import stdlib.StdIn;
import stdlib.StdOut;

/**
 * Develop a data type called BrutePointST that implements the above API using a
 * red-black BST (RedBlackBST) as the underlying data structure.
 *
 * @author Lengqiang Lin
 * @date 11/30/2022
 */
public class BrutePointST<Value> implements PointST<Value> {

    // data structure to store 2d points
    private RedBlackBinarySearchTreeST<Point2D, Value> bst;

    /**
     * Constructs an empty symbol table.
     */
    public BrutePointST() {
        this.bst = new RedBlackBinarySearchTreeST<>();
    }

    /**
     * Returns true if this symbol table is empty, and false otherwise.
     *
     * @return true if this symbol table is empty, and false otherwise.
     */
    public boolean isEmpty() {
        return this.bst.isEmpty();
    }

    /**
     * Returns the number of key-value pairs in this symbol table.
     *
     * @return number of key-value pairs
     */
    public int size() {
        return this.bst.size();
    }

    /**
     * Inserts the given point and value into this symbol table.
     *
     * @param p     point
     * @param value value pair
     */
    public void put(Point2D p, Value value) {
        if (p == null) {
            throw new NullPointerException("p is null");
        } else if (value == null) {
            throw new NullPointerException("value is null");
        }
        this.bst.put(p, value);
    }

    /**
     * Returns the value associated with the given point in this symbol table, or null.
     *
     * @param p point
     * @return value associate with point
     */
    public Value get(Point2D p) {
        if (p == null) {
            throw new NullPointerException("p is null");
        }
        return this.bst.get(p);
    }

    /**
     * Returns true if this symbol table contains the given point, and false otherwise.
     *
     * @param p point
     * @return is exists on symbol table
     */
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new NullPointerException("p is null");
        }
        return this.bst.contains(p);
    }

    /**
     * Returns all the points in this symbol table.
     *
     * @return all the points(keys) in this symbol table.
     */
    public Iterable<Point2D> points() {
        return this.bst.keys();
    }

    /**
     * Returns all the points in this symbol table that are inside the given rectangle.
     *
     * @param rect rectangle
     * @return all the points in this symbol table that are inside the given rectangle.
     */
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new NullPointerException("rect is null");
        }
        LinkedQueue<Point2D> lq = new LinkedQueue<>();
        for (Point2D p2D : points()) {
            if (rect.contains(p2D)) {
                lq.enqueue(p2D);
            }
        }
        return lq;
    }

    /**
     * Returns the point in this symbol table that is different from and closest to the given
     * point or null.
     *
     * @param p point
     * @return all the point that is different from and closest to given point or null
     */
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new NullPointerException("p is null");
        }
        return nearest(p, 1).iterator().next();
    }

    /**
     * Returns up to k points from this symbol table that are different from and closest to the
     * given point.
     *
     * @param p point
     * @param k point counts
     * @return k points from this symbol table that are different from and closest to the
     * given point.
     */
    public Iterable<Point2D> nearest(Point2D p, int k) {
        if (p == null) {
            throw new NullPointerException("p is null");
        }
        MinPQ<Point2D> minQ = new MinPQ<>(size(), p.distanceToOrder());
        LinkedQueue<Point2D> lq = new LinkedQueue<>();
        for (Point2D p2D : points()) {
            if (p2D.equals(p)) {
                continue;
            }
            minQ.insert(p2D);
        }
        for (int i = 0; i < k; i++) {
            Point2D tempP = minQ.delMin();
            lq.enqueue(tempP);
        }
        return lq;
    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        BrutePointST<Integer> st = new BrutePointST<Integer>();
        double qx = Double.parseDouble(args[0]);
        double qy = Double.parseDouble(args[1]);
        int k = Integer.parseInt(args[2]);
        Point2D query = new Point2D(qx, qy);
        RectHV rect = new RectHV(-1, -1, 1, 1);
        int i = 0;
        while (!StdIn.isEmpty()) {
            double x = StdIn.readDouble();
            double y = StdIn.readDouble();
            Point2D p = new Point2D(x, y);
            st.put(p, i++);
        }
        StdOut.println("st.empty()? " + st.isEmpty());
        StdOut.println("st.size() = " + st.size());
        StdOut.printf("st.contains(%s)? %s\n", query, st.contains(query));
        StdOut.printf("st.range(%s):\n", rect);
        for (Point2D p : st.range(rect)) {
            StdOut.println("  " + p);
        }
        StdOut.printf("st.nearest(%s) = %s\n", query, st.nearest(query));
        StdOut.printf("st.nearest(%s, %d):\n", query, k);
        for (Point2D p : st.nearest(query, k)) {
            StdOut.println("  " + p);
        }
    }
}
