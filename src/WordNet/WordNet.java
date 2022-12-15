package WordNet;

import dsa.DiGraph;
import dsa.SeparateChainingHashST;
import dsa.Set;
import stdlib.In;
import stdlib.StdOut;

/**
 * Find the shortest common ancestor of a digraph in WordNet, a semantic lexicon for the English
 * language that computational linguists and cognitive scientists use extensively. For example,
 * WordNet was a key component in IBM’s Jeopardyplaying Watson computer system.
 *
 * @author Lengqiang Lin
 * @date 12/09/2022
 */
public class WordNet {
    private SeparateChainingHashST<String, Set<Integer>> st; // set
    private SeparateChainingHashST<Integer, String> rst;    // symbol
    private ShortestCommonAncestor sca;         // the ancestor for IDs

    /**
     * Constructs a WordNet.WordNet object given the names of the input (synset and hypernym) files.
     *
     * @param synsets   string of synsets
     * @param hypernyms string of hypernyms
     */
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null) {
            throw new NullPointerException("synsets is null");
        }
        if (hypernyms == null) {
            throw new NullPointerException("hypernyms is null");
        }
        this.st = new SeparateChainingHashST<>();
        this.rst = new SeparateChainingHashST<>();
        In in = new In(synsets);
        int id = 0;
        while (!in.isEmpty()) {
            String[] arr = in.readLine().split(",");
            String[] noun = arr[1].split("\\s");
            id = Integer.parseInt(arr[0]);
            for (String currNoun : noun) {
                if (!this.st.contains(currNoun)) {
                    this.st.put(currNoun, new Set<>());
                }
                this.st.get(currNoun).add(id);
            }
            this.rst.put(id, arr[1]);
        }
        DiGraph diGraph = new DiGraph(id + 1);
        In hyperIn = new In(hypernyms);
        while (!hyperIn.isEmpty()) {
            String[] arrHyper = hyperIn.readLine().split(",");
            int i = Integer.parseInt(arrHyper[0]);
            for (int j = 1; j < arrHyper.length; j++) {
                diGraph.addEdge(i, Integer.parseInt(arrHyper[j]));
            }
        }
        this.sca = new ShortestCommonAncestor(diGraph);
    }

    /**
     * Returns all WordNet and WordNet nouns.
     *
     * @return all WordNet
     */
    public Iterable<String> nouns() {
        return this.st.keys();
    }

    /**
     * Returns true if the given word is a WordNet, WordNet noun, and false otherwise.
     *
     * @param word string of word
     * @return true if the given word is a WordNet, WordNet noun, and false otherwise
     */
    public boolean isNoun(String word) {
        if (word == null) {
            throw new NullPointerException("word is null");
        }
        return this.st.contains(word);
    }

    /**
     * Returns a synset that is a shortest common ancestor of noun1 and noun2.
     *
     * @param noun1 string of noun 1
     * @param noun2 string of noun 2
     * @return synset that is a shortest common ancestor of noun1 and noun2.
     */
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
        int ancestor = this.sca.ancestor(this.st.get(noun1), this.st.get(noun2));
        return this.rst.get(ancestor);
    }

    /**
     * Returns the length of the shortest ancestral path between noun1 and noun2.
     *
     * @param noun1 string of noun 1
     * @param noun2 string of noun 2
     * @return length of the shortest ancestral path between noun1 and noun2
     */
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
        return this.sca.length(this.st.get(noun1), this.st.get(noun2));
    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        try {
            WordNet wordnet = new WordNet(args[0], args[1]);
            String word1 = args[2];
            String word2 = args[3];
            int nouns = 0;
            for (String noun : wordnet.nouns()) {
                nouns++;
            }
            StdOut.printf("# of nouns = %d\n", nouns);
            StdOut.printf("isNoun(%s)? %s\n", word1, wordnet.isNoun(word1));
            StdOut.printf("isNoun(%s)? %s\n", word2, wordnet.isNoun(word2));
            StdOut.printf("isNoun(%s %s)? %s\n", word1, word2, wordnet.isNoun(word1 + " " + word2));
            StdOut.printf("sca(%s, %s) = %s\n", word1, word2, wordnet.sca(word1, word2));
            StdOut.printf("distance(%s, %s) = %s\n", word1, word2, wordnet.distance(word1, word2));
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }
}
