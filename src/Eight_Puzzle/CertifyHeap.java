package Eight_Puzzle;

import stdlib.StdIn;
import stdlib.StdOut;

/**
 *  Implement the static method isMaxHeap() in CertifyHeap.java that takes an array a of
 *  Comparable objects (excluding a[0] = *) and returns true if a represents a max-heap,
 *  and false otherwise.
 *
 * @author Lengqiang Lin
 * @date 11/10/2022
 */
public class CertifyHeap {

    /**
     * Returns true if a[] represents a max-heap, and false otherwise.
     * @param a comparable array
     * @return
     */
    public static boolean isMaxHeap(Comparable[] a) {
        // Set n to the number of elements in a.
        int n = a.length;

        // For each node 1 <= i <= n / 2, if a[i] is less than either of its children, return
        // false, meaning a[] does not represent a max-heap. If no such i exists, return true.
        for (int i = 1; i <= n / 2; i++) {
            if (2 * i < n && less(a[i], a[2 * i])) {
                return false;
            }
            if (2 * i + 1 < n && less(a[i], a[2 * i + 1])) {
                return false;
            }
        }
        return true;
    }

    // Returns true of v is less than w, and false otherwise.
    private static boolean less(Comparable v, Comparable w) {
        return (v.compareTo(w) < 0);
    }

    // Unit tests the library. [DO NOT EDIT]
    public static void main(String[] args) {
        String[] a = StdIn.readAllStrings();
        StdOut.println(isMaxHeap(a));
    }
}
