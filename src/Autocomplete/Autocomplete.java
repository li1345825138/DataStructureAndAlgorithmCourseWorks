package Autocomplete;

import stdlib.In;
import stdlib.StdIn;
import stdlib.StdOut;

import java.util.Arrays;

/**
 * In this part, you will implement a data type that provides autocomplete functionality for a
 * given set of string and weights, using Term and BinarySearchDeluxe. To do so, sort the terms in
 * lexicographic order; use binary search to find the set of terms that start with a given prefix;
 * and sort the matching terms in descending order by weight. Organize your program by creating an
 * immutable data type called Autocomplete with the following API
 *
 * @author Lengqiang Lin
 * @date 11/01/2022
 */
public class Autocomplete {
    private Term[] terms;           // Array of terms

    /**
     * Constructs an autocomplete data structure from an array of terms.
     *
     * @param terms terms to construct
     */
    public Autocomplete(Term[] terms) {
        if (terms == null) {
            throw new NullPointerException("terms is null");
        }
        int length = terms.length;
        this.terms = new Term[length];
        for (int i = 0; i < length; i++) {
            this.terms[i] = terms[i];
        }
        Arrays.sort(this.terms);
    }

    /**
     * Returns all terms that start with prefix, in descending order of their weights.
     *
     * @param prefix prefix search string
     * @return all the term matches
     */
    public Term[] allMatches(String prefix) {
        if (prefix == null) {
            throw new NullPointerException("prefix is null");
        }
        // check the term length
        if (this.terms.length == 0) {
            return new Term[0];
        }

        Term prefixTerm = new Term(prefix);

        // Find the index i of the first term in terms that starts with prefix
        int i = BinarySearchDeluxe.firstIndexOf(this.terms, prefixTerm,
                Term.byPrefixOrder(prefix.length()));

        // Find the number of terms (say n) in terms that start with prefix
        int n = BinarySearchDeluxe.lastIndexOf(this.terms, prefixTerm,
                Term.byPrefixOrder(prefix.length()));

        // Construct an array matches containing n elements from terms, starting at. index i
        Term[] matches = new Term[n - i + 1];
        for (int j = 0; j < matches.length; j++) {
            matches[j] = this.terms[i++];
        }

        // Sort matches in reverse order of weight and return the sorted array.
        Arrays.sort(matches, Term.byReverseWeightOrder());
        return matches;
    }

    /**
     * Returns the number of terms that start with prefix.
     *
     * @param prefix prefix search string
     * @return the number of matches result
     */
    public int numberOfMatches(String prefix) {
        if (prefix == null) {
            throw new NullPointerException("prefix is null");
        }
        // check terms length
        if (this.terms.length == 0) {
            return 0;
        }

        Term prefixTerm = new Term(prefix);

        // Find the indices i and j of the first and last term in terms that start with prefix.
        int i = BinarySearchDeluxe.firstIndexOf(this.terms, prefixTerm,
                Term.byPrefixOrder(prefix.length()));
        int j = BinarySearchDeluxe.lastIndexOf(this.terms, prefixTerm,
                Term.byPrefixOrder(prefix.length()));
        return j - i + 1;
    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        int k = Integer.parseInt(args[1]);
        In in = new In(filename);
        int N = in.readInt();
        Term[] terms = new Term[N];
        for (int i = 0; i < N; i++) {
            long weight = in.readLong();
            in.readChar();
            String query = in.readLine();
            terms[i] = new Term(query.trim(), weight);
        }
        Autocomplete autocomplete = new Autocomplete(terms);
        StdOut.print("Enter a prefix (or ctrl-d to quit): ");
        while (StdIn.hasNextLine()) {
            String prefix = StdIn.readLine();
            Term[] results = autocomplete.allMatches(prefix);
            String msg = " matches for \"" + prefix + "\", in descending order by weight:";
            if (results.length == 0) {
                msg = "No matches";
            } else if (results.length > k) {
                msg = "First " + k + msg;
            } else {
                msg = "All" + msg;
            }
            StdOut.printf("%s\n", msg);
            for (int i = 0; i < Math.min(k, results.length); i++) {
                StdOut.println("  " + results[i]);
            }
            StdOut.print("Enter a prefix (or ctrl-d to quit): ");
        }
    }
}
