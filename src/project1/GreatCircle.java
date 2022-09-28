package project1;

/**
 * Exercise 1. (Great Circle Distance) Write a program called GreatCircle.java that
 * accepts x1 (double), y1 (double), x2 (double),
 * and y2 (double) as command-line arguments representing the latitude and longitude
 * (in degrees) of two points on earth, and
 * writes to standard output the great-circle distance (in km) between the two points,
 * given by the formula:
 * d = 6359.83 arccos(sin(x1) sin(x2) + cos(x1) cos(x2) cos(y1 − y2)).
 *
 * @author Lengqiang Lin
 * @date 09/27/2022
 */
public class GreatCircle {
    public static void main(String[] args) {
        try {
            double x1 = Double.parseDouble(args[0]);
            double y1 = Double.parseDouble(args[1]);
            double x2 = Double.parseDouble(args[2]);
            double y2 = Double.parseDouble(args[3]);
            double result = calculateGreateCircle(x1, y1, x2, y2);
            System.out.println(result);
        } catch (NumberFormatException e) {
            System.err.println("Error: The arguments should be numbers!");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Calculate Greate Circle distance
     * d = 6359.83 arccos(sin(x1) sin(x2) + cos(x1) cos(x2) cos(y1 − y2)).
     * @param x1 argument for x1
     * @param y1 argument for y1
     * @param x2 argument for x2
     * @param y2 argument for y2
     * @return result of greate circle distance
     */
    static double calculateGreateCircle(double x1, double y1, double x2, double y2) {
        // convert to radian value before calculation
        x1 = Math.toRadians(x1);
        x2 = Math.toRadians(x2);
        y1 = Math.toRadians(y1);
        y2 = Math.toRadians(y2);

        // return result by using formula
        return 6359.83 * Math.acos(Math.sin(x1) * Math.sin(x2) +
                Math.cos(x1) * Math.cos(x2) * Math.cos(y1 - y2));
    }
}
