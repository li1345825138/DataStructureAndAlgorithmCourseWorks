package Autocomplete;

import stdlib.StdOut;
import stdlib.StdRandom;

/**
 * Implement a comparable data type called Die that represents a six-sided die and
 * supports the following API
 *
 * @author Lengqiang Lin
 * @date 10/27/2022
 */
public class Die implements Comparable<Die> {
    private int value; // the face value

    /**
     * Contructure, initial value into 0
     */
    public Die() {
        this.value = 0;
    }

    /**
     * Roll the Die.
     * The Die total have 6 face, and value will be [1, 6]
     */
    public void roll() {
        // get random value from [1, 6]
        this.value = StdRandom.uniform(1, 7);
    }

    /**
     * Return die face value
     *
     * @return current die face value
     */
    public int value() {
        return this.value;
    }

    /**
     * Returns true if this die is the same as other, and false otherwise.
     *
     * @param other other Die object
     * @return same return true, otherwise false
     */
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (other.getClass() != this.getClass()) {
            return false;
        }
        Die otherDie = (Die) other;
        return this.value == otherDie.value;
    }

    /**
     * Returns a comparison of this die with other, by their face values.
     *
     * @param that the object to be compared.
     * @return greater return > 0, same return 0, smaller return < 0
     */
    public int compareTo(Die that) {
        return this.value - that.value;
    }

    /**
     * Returns a string representation of this die.
     *
     * @return String of Die face
     */
    public String toString() {
        String dieString = null;
        switch (this.value) {
            case 1:
                dieString = "     \n  *  \n     ";
                break;
            case 2:
                dieString = "*    \n     \n    *";
                break;
            case 3:
                dieString = "*    \n  *  \n    *";
                break;
            case 4:
                dieString = "*   *\n     \n*   *";
                break;
            case 5:
                dieString = "*   *\n  *  \n*   *";
                break;
            case 6:
                dieString = "* * *\n     \n* * *";
                break;
            default:
                dieString = "Not rolled yet";
        }
        return dieString;
    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        int x = Integer.parseInt(args[0]);
        int y = Integer.parseInt(args[1]);
        int z = Integer.parseInt(args[2]);
        Die a = new Die();
        a.roll();
        while (a.value() != x) {
            a.roll();
        }
        Die b = new Die();
        b.roll();
        while (b.value() != y) {
            b.roll();
        }
        Die c = new Die();
        c.roll();
        while (c.value() != z) {
            c.roll();
        }
        StdOut.println("Dice a, b, and c:");
        StdOut.println(a);
        StdOut.println(b);
        StdOut.println(c);
        StdOut.println("a.equals(b)    = " + a.equals(b));
        StdOut.println("b.equals(c)    = " + b.equals(c));
        StdOut.println("a.compareTo(b) = " + a.compareTo(b));
        StdOut.println("b.compareTo(c) = " + b.compareTo(c));
    }
}
