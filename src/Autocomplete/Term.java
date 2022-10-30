package Autocomplete;

import java.util.Arrays;
import java.util.Comparator;

import stdlib.In;
import stdlib.StdOut;

/**
 * Implement an immutable comparable data type called Term that represents an autocomplete
 * term: a string query and an associated real-valued weight. You must implement the following API,
 * which supports comparing terms by three different orders: lexicographic order by query; in
 * descending order by weight; and lexicographic order by query but using only the first r
 * characters. The last order may seem a bit odd, but you will use it in Problem 3 to find all
 * terms that start with a given prefix (of length r).
 *
 * @author Lengqiang Lin
 * @date 10/30/2022
 */
public class Term implements Comparable<Term> {
    private String query;       // Query String
    private long weight;         // Query Weight

    /**
     * Constructs a term given the associated query string, having weight 0.
     *
     * @param query string query
     * @throws throw NullPointerException if query is null
     */
    public Term(String query) {
        if (query == null) {
            throw new NullPointerException("query is null");
        }
        this.query = query;
        this.weight = 0L;
    }

    /**
     * Constructs a term given the associated query string and weight.
     *
     * @param query  string query
     * @param weight query weight
     * @throws throw NullPointerException if query is null
     * @throws throw IllegalArgumentException if weight is < 0
     */
    public Term(String query, long weight) {
        if (query == null) {
            throw new NullPointerException("query is null");
        }
        if (weight < 0) {
            throw new IllegalArgumentException("Illegal weight");
        }
        this.query = query;
        this.weight = weight;
    }

    /**
     * Returns a string representation of this term.
     *
     * @return term string
     */
    public String toString() {
        return String.format("%d\t%s", this.weight, this.query);
    }

    /**
     * Returns a comparison of this term and other by query.
     *
     * @param other the object to be compared.
     * @return Result of compare
     */
    public int compareTo(Term other) {
        return this.query.compareTo(other.query);
    }

    /**
     * Returns a comparator for comparing two terms in reverse order of their weights.
     *
     * @return an object of type ReverseWeightOrder.
     */
    public static Comparator<Term> byReverseWeightOrder() {
        return new ReverseWeightOrder();
    }

    /**
     * Returns a comparator for comparing two terms by their prefixes of length r.
     *
     * @param r length
     * @return an object of type PrefixOrder
     */
    public static Comparator<Term> byPrefixOrder(int r) {
        if (r < 0) {
            throw new IllegalArgumentException("Illegal r");
        }
        return new PrefixOrder(r);
    }

    /**
     * Reverse-weight comparator, inherit Comparator interface
     */
    private static class ReverseWeightOrder implements Comparator<Term> {
        // Returns a comparison of terms v and w by their weights in reverse order.
        public int compare(Term v, Term w) {
            return Long.compare(w.weight, v.weight);
        }
    }

    /**
     * Prefix-order comparator.
     */
    private static class PrefixOrder implements Comparator<Term> {
        private int r;          // Prefix length

        /**
         * Constructs a new prefix order given the prefix length.
         *
         * @param r the prefix length
         */
        PrefixOrder(int r) {
            this.r = r;
        }

        /**
         * Returns a comparison of terms v and w by their prefixes of length r.
         *
         * @param v the first object to be compared.
         * @param w the second object to be compared.
         * @return
         */
        public int compare(Term v, Term w) {
            int vLength = Math.min(v.query.length(), this.r);
            int wLength = Math.min(w.query.length(), this.r);
            String vQuery = v.query.substring(0, vLength);
            String wQuery = w.query.substring(0, wLength);
            return vQuery.compareTo(wQuery);
        }
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
        StdOut.printf("Top %d by lexicographic order:\n", k);
        Arrays.sort(terms);
        for (int i = 0; i < k; i++) {
            StdOut.println(terms[i]);
        }
        StdOut.printf("Top %d by reverse-weight order:\n", k);
        Arrays.sort(terms, Term.byReverseWeightOrder());
        for (int i = 0; i < k; i++) {
            StdOut.println(terms[i]);
        }
    }
}
