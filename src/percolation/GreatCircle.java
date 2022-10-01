package percolation;

import stdlib.StdOut;

/**
 * Exercise 1. (Great Circle Distance) Write a program called GreatCircle.java that
 * accepts x1 (double), y1 (double), x2 (double),and y2 (double) as command-line
 * arguments representing the latitude and longitude (in degrees) of two points on earth, and
 * writes to standard output the great-circle distance (in km) between the two points,
 * given by the formula: d = 6359.83 arccos(sin(x1) sin(x2) + cos(x1) cos(x2) cos(y1 − y2)).
 *
 * @date 09/27/2022
 */
public class GreatCircle {
    // Entry point.
    public static void main(String[] args) {
        // Accept x1 (double), y1 (double), x2 (double), and y2 (double) as command-line arguments.
        double x1 = Double.parseDouble(args[0]);
        double y1 = Double.parseDouble(args[1]);
        double x2 = Double.parseDouble(args[2]);
        double y2 = Double.parseDouble(args[3]);

        // Convert the angles to radians.
        x1 = Math.toRadians(x1);
        y1 = Math.toRadians(y1);
        x2 = Math.toRadians(x2);
        y2 = Math.toRadians(y2);

        // Calculate great-circle distance d.
        double d = 6359.83 * Math.acos(Math.sin(x1) * Math.sin(x2) +
                Math.cos(x1) * Math.cos(x2) * Math.cos(y1 - y2));

        // Write d to standard output.
        StdOut.println(d);
    }
}
