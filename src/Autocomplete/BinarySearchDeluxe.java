package Autocomplete;

import java.util.Arrays;
import java.util.Comparator;

import stdlib.In;
import stdlib.StdOut;

/**
 * When binary searching a sorted array that contains more than one key equal to the
 * search key, the client may want to know the index of either the first or the last such key.
 * Accordingly, implement a library called BinarySearchDeluxe with the following API
 *
 * @author Lengqiang Lin
 * @date 10/31/2022
 */
public class BinarySearchDeluxe {

    /**
     * Returns the index of the first key in a that equals the search key, or -1, according to
     * the order induced by the comparator c.
     *
     * @param a   key array
     * @param key search key
     * @param c   search key comparator
     * @return index of first key
     */
    public static <Key> int firstIndexOf(Key[] a, Key key, Comparator<Key> c) {
        if (a == null || key == null || c == null) {
            throw new NullPointerException("a, key, or c is null");
        }

        // if key array length is less equal to 0
        if (a.length == 0) {
            return -1;
        }

        // searching key by binary search
        int lo = 0;
        int hi = a.length - 1;
        int mid = 0;
        int index = -1;

        if (c.compare(key, a[lo]) == 0) {
            index = lo;
            return index;
        }

        while (lo <= hi) {
            mid = (hi + lo) >> 1;           // same as (hi + lo) / 2
            if (c.compare(key, a[mid]) < 0) {
                hi = mid - 1;
            } else if (c.compare(key, a[mid]) > 0) {
                lo = mid + 1;
            } else if (c.compare(key, a[mid - 1]) == 0) {
                hi = mid - 1;
            } else {
                index = mid;
                break;
            }
        }
        return index;
    }

    /**
     * Returns the index of the first key in a that equals the search key, or -1, according to
     * the order induced by the comparator c.
     *
     * @param a   key array
     * @param key search key
     * @param c   search key comparator
     * @return index of first key
     */
    public static <Key> int lastIndexOf(Key[] a, Key key, Comparator<Key> c) {
        if (a == null || key == null || c == null) {
            throw new NullPointerException("a, key, or c is null");
        }

        // if key array length is less equal to 0
        if (a.length == 0) {
            return -1;
        }

        // searching key by binary search
        int lo = 0;
        int hi = a.length - 1;
        int mid = 0;
        int index = -1;

        if (c.compare(key, a[hi]) == 0) {
            index = hi;
            return index;
        }

        while (lo <= hi) {
            mid = (hi + lo) >> 1;               // same as (hi + lo) / 2
            if (c.compare(key, a[mid]) < 0) {
                hi = mid - 1;
            } else if (c.compare(key, a[mid]) > 0) {
                lo = mid + 1;
            } else if (c.compare(key, a[mid + 1]) == 0) {
                lo = mid + 1;
            } else {
                index = mid;
                break;
            }
        }
        return index;
    }

    // Unit tests the library. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        String prefix = args[1];
        In in = new In(filename);
        int N = in.readInt();
        Term[] terms = new Term[N];
        for (int i = 0; i < N; i++) {
            long weight = in.readLong();
            in.readChar();
            String query = in.readLine();
            terms[i] = new Term(query.trim(), weight);
        }
        Arrays.sort(terms);
        Term term = new Term(prefix);
        Comparator<Term> prefixOrder = Term.byPrefixOrder(prefix.length());
        int i = BinarySearchDeluxe.firstIndexOf(terms, term, prefixOrder);
        int j = BinarySearchDeluxe.lastIndexOf(terms, term, prefixOrder);
        int count = i == -1 && j == -1 ? 0 : j - i + 1;
        StdOut.println("firstIndexOf(" + prefix + ") = " + i);
        StdOut.println("lastIndexOf(" + prefix + ")  = " + j);
        StdOut.println("frequency(" + prefix + ")    = " + count);
    }
}
