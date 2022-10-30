package Autocomplete;

import java.util.Arrays;
import java.util.Comparator;

import stdlib.StdIn;
import stdlib.StdOut;

/**
 * Implement an immutable data type called Point3D that represents a point in 3D and
 * supports the following API
 *
 * @author Lengqiang Lin
 * @date 10/29/2022
 */
public class Point3D implements Comparable<Point3D> {
    private double x; // x coordinate
    private double y; // y coordinate
    private double z; // z coordinate

    /**
     * Constructs a point in 3D given its x, y, and z coordinates.
     *
     * @param x coordinate
     * @param y coordinate
     * @param z coordinate
     */
    public Point3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Returns the Euclidean distance between this point and other.
     *
     * @param other other point3D object
     * @return Euclidean distance between current point and other
     */
    public double distance(Point3D other) {
        double diffX = this.x - other.x;
        double diffY = this.y - other.y;
        double diffZ = this.z - other.z;
        double sumOfSquare = (diffX * diffX) + (diffY * diffY) + (diffZ * diffZ);
        return Math.sqrt(sumOfSquare);
    }

    /**
     * Returns a string representation of this point.
     *
     * @return string of 3D point
     */
    public String toString() {
        return "(" + this.x + ", " + this.y + ", " + this.z + ")";
    }

    /**
     * Returns a comparison of this point with other based on their respective distances to the
     * origin (0, 0, 0).
     *
     * @param other the object to be compared.
     * @return result of two Point3D object
     */
    public int compareTo(Point3D other) {
        Point3D origin = new Point3D(0, 0, 0);
        double thisDistance = distance(origin);
        double otherDistance = origin.distance(other);
        return Double.compare(thisDistance, otherDistance);
    }

    /**
     * Returns a comparator to compare two points by their x-coordinate.
     *
     * @return
     */
    public static Comparator<Point3D> xOrder() {
        return new XOrder();
    }

    /**
     * Returns a comparator to compare two points by their y-coordinate.
     *
     * @return
     */
    public static Comparator<Point3D> yOrder() {
        return new YOrder();
    }

    /**
     * Returns a comparator to compare two points by their z-coordinate.
     *
     * @return
     */
    public static Comparator<Point3D> zOrder() {
        return new ZOrder();
    }

    /**
     * A comparator for comparing two points by their x-coordinate.
     */
    private static class XOrder implements Comparator<Point3D> {
        // Returns a comparison of p1 and p2 by their x-coordinate.
        public int compare(Point3D p1, Point3D p2) {
            return Double.compare(p1.x, p2.x);
        }
    }

    /**
     * A comparator for comparing two points by their y-coordinate.
     */
    private static class YOrder implements Comparator<Point3D> {
        // Returns a comparison of p1 and p2 by their y-coordinate.
        public int compare(Point3D p1, Point3D p2) {
            return Double.compare(p1.y, p2.y);
        }
    }

    /**
     * A comparator for comparing two points by their z-coordinate.
     */
    private static class ZOrder implements Comparator<Point3D> {
        // Returns a comparison of p1 and p2 by their z-coordinate.
        public int compare(Point3D p1, Point3D p2) {
            return Double.compare(p1.z, p2.z);
        }
    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        StdOut.print("How many points? ");
        int n = StdIn.readInt();
        Point3D[] points = new Point3D[n];
        StdOut.printf("Enter %d doubles, separated by whitespace: ", n * 3);
        for (int i = 0; i < n; i++) {
            double x = StdIn.readDouble();
            double y = StdIn.readDouble();
            double z = StdIn.readDouble();
            points[i] = new Point3D(x, y, z);
        }
        StdOut.println("Here are the points in the order entered:");
        for (Point3D point : points) {
            StdOut.println("  " + point);
        }
        Arrays.sort(points);
        StdOut.println("Sorted by their natural ordering (compareTo)");
        for (Point3D point : points) {
            StdOut.println("  " + point);
        }
        Arrays.sort(points, Point3D.xOrder());
        StdOut.println("Sorted by their x coordinate (xOrder)");
        for (Point3D point : points) {
            StdOut.println("  " + point);
        }
        Arrays.sort(points, Point3D.yOrder());
        StdOut.println("Sorted by their y coordinate (yOrder)");
        for (Point3D point : points) {
            StdOut.println("  " + point);
        }
        Arrays.sort(points, Point3D.zOrder());
        StdOut.println("Sorted by their z coordinate (zOrder)");
        for (Point3D point : points) {
            StdOut.println("  " + point);
        }
    }
}
