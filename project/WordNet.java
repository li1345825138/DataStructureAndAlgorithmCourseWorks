package edu.umb.cs210.p6;

import dsa.DiGraph;
import dsa.RedBlackBinarySearchTreeST;
import stdlib.In;
import stdlib.StdOut;
import dsa.Set;

// An immutable WordNet data type.
public class WordNet {
    private RedBlackBinarySearchTreeST<String, Set<Integer>> st; // set
    // of synset IDs
    private RedBlackBinarySearchTreeST<Integer, String> rst; // symbol
    // table of synset IDs
    private ShortestCommonAncestor sca; // the ancestor for IDs

    // Construct a WordNet object given the names of the input (synset and
    // hypernym) files.
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null) {
            throw new NullPointerException("synsets is null");
        }
        if (hypernyms == null) {
            throw new NullPointerException("hypernyms is null");
        }
        st = new RedBlackBinarySearchTreeST<>();
        rst = new RedBlackBinarySearchTreeST<>();
        In in = new In(synsets);
        int ID = 0;
        while (!in.isEmpty()) {
            String[] arr = in.readLine().split(",");
            String[] noun = arr[1].split("\\s");
            ID = Integer.parseInt(arr[0]);
            for (String nou : noun) {
                if (!st.contains(nou)) {
                    st.put(nou, new Set<>());
                }
                st.get(nou).add(ID);
            }
            rst.put(ID, arr[1]);
        }
        DiGraph G = new DiGraph(ID + 1);
        In hyperIn = new In(hypernyms);
        while (!hyperIn.isEmpty()) {
            String[] arrH = hyperIn.readLine().split(",");
            int i = Integer.parseInt(arrH[0]);
            for (int j = 1; j < arrH.length; j++) {
                G.addEdge(i, Integer.parseInt(arrH[j]));
            }
        }
        sca = new ShortestCommonAncestor(G);
    }

    // All WordNet nouns.
    public Iterable<String> nouns() {
        return st.keys();
    }

    // Is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) {
            throw new NullPointerException("word is null");
        }
        return st.contains(word);
    }

    // A synset that is a shortest common ancestor of noun1 and noun2.
    public String sca(String noun1, String noun2) {
        if (noun1 == null) {
            throw new NullPointerException("noun1 is null");
        }
        if (noun2 == null) {
            throw new NullPointerException("noun2 is null");
        }
        if (!isNoun(noun1)) {
            throw new IllegalArgumentException("noun1 is not a noun");
        }
        if (!isNoun(noun2)) {
            throw new IllegalArgumentException("noun2 is not a noun");
        }
        return rst.get(sca.ancestor(st.get(noun1), st.get(noun2)));
    }

    // Distance between noun1 and noun2.
    public int distance(String noun1, String noun2) {
        if (noun1 == null) {
            throw new NullPointerException("noun1 is null");
        }
        if (noun2 == null) {
            throw new NullPointerException("noun2 is null");
        }
        if (!isNoun(noun1)) {
            throw new IllegalArgumentException("noun1 is not a noun");
        }
        if (!isNoun(noun2)) {
            throw new IllegalArgumentException("noun2 is not a noun");
        }
        return sca.length(st.get(noun1), st.get(noun2));
    }

    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        String word1 = args[2];
        String word2 = args[3];        
        int nouns = 0;
        for (String noun : wordnet.nouns()) {
            nouns++;
        }
        StdOut.println("# of nouns = " + nouns);
        StdOut.println("isNoun(" + word1 + ") = " + wordnet.isNoun(word1));
        StdOut.println("isNoun(" + word2 + ") = " + wordnet.isNoun(word2));
        StdOut.println("isNoun(" + (word1 + " " + word2) + ") = "
                       + wordnet.isNoun(word1 + " " + word2));
        StdOut.println("sca(" + word1 + ", " + word2 + ") = "
                       + wordnet.sca(word1, word2));
        StdOut.println("distance(" + word1 + ", " + word2 + ") = "
                       + wordnet.distance(word1, word2));
    }
}
