package KdTrees;

import dsa.LinkedQueue;
import dsa.MaxPQ;
import dsa.Point2D;
import dsa.RectHV;
import stdlib.StdIn;
import stdlib.StdOut;

/**
 * Develop a data type called KdTreePointST that uses a 2dTree to implement the above
 * symbol table API
 *
 * @author Lengqiang Lin
 * @date 12/02/2022
 */
public class KdTreePointST<Value> implements PointST<Value> {
    private Node root;          // root of 2dTree
    private int n;              // number of nodes in tree

    /**
     * Constructs an empty symbol table.
     */
    public KdTreePointST() {
        this.root = null;
        this.n = 0;
    }

    /**
     * Returns true if this symbol table is empty, and false otherwise.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return this.n == 0;
    }

    /**
     * Returns the number of key-value pairs in this symbol table.
     *
     * @return numbers of node pair on tree
     */
    public int size() {
        return this.n;
    }

    /**
     * Inserts the given point and value into this symbol table.
     *
     * @param p     p point
     * @param value value
     */
    public void put(Point2D p, Value value) {
        if (p == null) {
            throw new NullPointerException("p is null");
        } else if (value == null) {
            throw new NullPointerException("value is null");
        }
        RectHV newRect = new RectHV(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY,
                Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
        this.root = put(this.root, p, value, newRect, true);
    }

    /**
     * Returns the value associated with the given point in this symbol table, or null.
     *
     * @param p p point
     * @return value that associate with given point
     */
    public Value get(Point2D p) {
        if (p == null) {
            throw new NullPointerException("p is null");
        }
        return get(this.root, p, true);
    }

    /**
     * Returns true if this symbol table contains the given point, and false otherwise.
     *
     * @param p point
     * @return true if contain with given point, otherwise false
     */
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new NullPointerException("p is null");
        }
        return get(p) != null;
    }

    /**
     * Returns all the points in this symbol table.
     *
     * @return all the points
     */
    public Iterable<Point2D> points() {
        LinkedQueue<Node> q = new LinkedQueue<>();
        LinkedQueue<Point2D> q2 = new LinkedQueue<>();
        q.enqueue(this.root);
        while (!q.isEmpty()) {
            Node n = q.dequeue();
            if (n.lb != null) {
                q.enqueue(n.lb);
            }
            if (n.rt != null) {
                q.enqueue(n.rt);
            }
            q2.enqueue(n.p);
        }
        return q2;
    }

    /**
     * Returns all the points in this symbol table that are inside the given rectangle.
     *
     * @param rect rect
     * @return all the points in this symbol table that are inside the given rectangle.
     */
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new NullPointerException("rect is null");
        }
        LinkedQueue<Point2D> q = new LinkedQueue<>();
        range(this.root, rect, q);
        return q;
    }

    /**
     * Returns the point in this symbol table that is different from and closest to the given point,
     * or null.
     *
     * @param p point
     * @return all the point that are nearest with given point
     */
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new NullPointerException("p is null");
        }
        return nearest(this.root, p, null, true);
    }

    /**
     * Returns up to k points from this symbol table that are different from and closest to the
     * given point.
     *
     * @param p point
     * @param k up to k points count
     * @return iterable points
     */
    public Iterable<Point2D> nearest(Point2D p, int k) {
        if (p == null) {
            throw new NullPointerException("p is null");
        }
        MaxPQ<Point2D> pq = new MaxPQ<>(p.distanceToOrder());
        nearest(this.root, p, k, pq, true);
        return pq;
    }

    /**
     * Inserts the given point and value into the KdTree x having rect as its axis-aligned
     * rectangle, and returns a reference to the modified tree.
     * <p>
     * Note: In the helper methods that have lr as a parameter, its value specifies how to
     * compare the point p with the point x.p. If true, the points are compared by their
     * x-coordinates; otherwise, the points are compared by their y-coordinates. If the
     * comparison of the coordinates (x or y) is true, the recursive call is made on x.lb;
     * otherwise, the call is made on x.rt.
     *
     * @param x     node
     * @param p     point
     * @param value value
     * @param rect  rectangle
     * @param lr    specifies how campare with p with x.p
     * @return node
     */
    private Node put(Node x, Point2D p, Value value, RectHV rect, boolean lr) {
        if (x == null) {
            this.n++;
            return new Node(p, value, rect);
        }
        if (x.p.equals(p)) {
            return x;
        }

        boolean cmp;
        if (lr) {
            cmp = p.x() < x.p.x();
        } else {
            cmp = p.y() < x.p.y();
        }
        RectHV nextRect;
        if (cmp) {
            if (x.lb == null) {
                double x1 = rect.xMin();
                double y1 = rect.yMin();
                double x2;
                double y2;
                if (lr) {
                    x2 = x.p.x();
                    y2 = rect.yMax();
                } else {
                    x2 = rect.xMax();
                    y2 = x.p.y();
                }
                nextRect = new RectHV(x1, y1, x2, y2);
            } else {
                nextRect = x.lb.rect;
            }
            x.lb = put(x.lb, p, value, nextRect, !lr);
        } else {
            if (x.rt == null) {
                double x1;
                double y1;
                if (lr) {
                    x1 = x.p.x();
                    y1 = rect.yMin();
                } else {
                    x1 = rect.xMin();
                    y1 = x.p.y();
                }
                double x2 = rect.xMax();
                double y2 = rect.yMax();
                nextRect = new RectHV(x1, y1, x2, y2);
            } else {
                nextRect = x.rt.rect;
            }
            x.rt = put(x.rt, p, value, nextRect, !lr);
        }
        return x;
    }

    /**
     * Returns the value associated with the given point in the KdTree x, or null.
     *
     * @param x  given x node
     * @param p  given p point
     * @param lr compare
     * @return the value that in the KdTree x
     */
    private Value get(Node x, Point2D p, boolean lr) {
        if (x == null) {
            return null;
        }
        if (x.p.equals(p)) {
            return x.value;
        } else if (lr && p.x() < x.p.x() || !lr && p.y() < x.p.y()) {
            return get(x.lb, p, !lr);
        }
        return get(x.rt, p, !lr);
    }

    /**
     * Collects in the given queue all the points in the KdTree x that are inside rect.
     *
     * @param x    x node
     * @param rect rectangle
     * @param q    queue
     */
    private void range(Node x, RectHV rect, LinkedQueue<Point2D> q) {
        if (x == null || !x.rect.intersects(rect)) {
            return;
        }
        if (rect.contains(x.p)) {
            q.enqueue(x.p);
        }
        range(x.lb, rect, q);
        range(x.rt, rect, q);
    }

    /**
     * Returns the point in the KdTree x that is closest to p, or null; nearest is the closest
     * point discovered so far.
     *
     * @param x       x node
     * @param p       point
     * @param nearest the nearest points
     * @param lr      compare
     * @return all the nearest points
     */
    private Point2D nearest(Node x, Point2D p, Point2D nearest, boolean lr) {
        // If x = null, return nearest.
        if (x == null) {
            return nearest;
        }
        // If the point x.p is different from the given point p and the squared distance between
        // the two is smaller than the
        // squared distance between nearest and p, update nearest to x.p.
        Point2D nearestt = nearest;
        if (!x.p.equals(p) && (nearestt == null ||
                p.distanceSquaredTo(x.p) < p.distanceSquaredTo(nearestt))) {
            nearestt = x.p;
        }

        // Make a recursive call to nearest() on the left subtree x.lb.
        // If the rectangle x.lb.rect contains p, make a recursive call to nearest() on the
        // right subtree x.rt.
        // Otherwise, make a recursive call to nearest() on the left subtree x.lb.
        if (lr && p.x() < x.p.x() || !lr && p.y() < x.p.y()) {
            nearestt = nearest(x.lb, p, nearestt, !lr);
            if (x.rt != null && x.rt.rect.distanceSquaredTo(p) < p.distanceSquaredTo(nearestt)) {
                nearestt = nearest(x.rt, p, nearestt, !lr);
            }
        } else {
            nearestt = nearest(x.rt, p, nearestt, !lr);
            if (x.lb != null && x.lb.rect.distanceSquaredTo(p) < p.distanceSquaredTo(nearestt)) {
                nearestt = nearest(x.lb, p, nearestt, !lr);
            }
        }
        return nearestt;
    }

    /**
     * Collects in the given max-PQ up to k points from the KdTree x that are different from and
     * closest to p.
     *
     * @param x  x node
     * @param p  point
     * @param k  up to k count
     * @param pq max priority queue
     * @param lr compare
     */
    private void nearest(Node x, Point2D p, int k, MaxPQ<Point2D> pq, boolean lr) {
        if (x == null || pq.size() >= k &&
                pq.max().distanceSquaredTo(p) < x.rect.distanceSquaredTo(p)) {
            return;
        }
        if (!x.p.equals(p)) {
            pq.insert(x.p);
        }
        if (pq.size() > k) {
            pq.delMax();
        }
        boolean leftBottom;
        if (lr && p.x() < x.p.x() || !lr && p.y() < x.p.y()) {
            nearest(x.lb, p, k, pq, !lr);
            leftBottom = true;
        } else {
            nearest(x.rt, p, k, pq, !lr);
            leftBottom = false;
        }
        nearest(leftBottom ? x.rt : x.lb, p, k, pq, !lr);
    }

    // A representation of node in a KdTree in two dimensions (ie, a 2dTree). Each node stores a
    // 2d point (the key), a value, an axis-aligned rectangle, and references to the left/bottom
    // and right/top subtrees.
    private class Node {
        private Point2D p;   // the point (key)
        private Value value; // the value
        private RectHV rect; // the axis-aligned rectangle
        private Node lb;     // the left/bottom subtree
        private Node rt;     // the right/top subtree

        // Constructs a node given the point (key), the associated value, and the
        // corresponding axis-aligned rectangle.
        Node(Point2D p, Value value, RectHV rect) {
            this.p = p;
            this.value = value;
            this.rect = rect;
        }
    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        KdTreePointST<Integer> st = new KdTreePointST<>();
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
