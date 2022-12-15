package WordNet;

import stdlib.StdIn;
import stdlib.StdOut;

/**
 * Implement an immutable data type called Outcast with the following API
 *
 * @author Lengqiang Lin
 * @date 12/11/2022
 */
public class Outcast {
    private WordNet wordnet;        // semantic lexicon

    /**
     * Constructs an Outcast object given the WordNet.WordNet semantic lexicon.
     *
     * @param wordnet The wordnet object
     */
    public Outcast(WordNet wordnet) {
        this.wordnet = wordnet;
    }

    /**
     * Returns the outcast noun from nouns.
     *
     * @param nouns array of nouns
     * @return the nouns with the largest distance
     */
    public String outcast(String[] nouns) {
        String out = nouns[0];
        int maxDistance = Integer.MIN_VALUE;
        for (String noun : nouns) {
            int sumDistance = 0;
            for (String noun1 : nouns) {
                sumDistance += this.wordnet.distance(noun, noun1);
            }
            if (sumDistance > maxDistance) {
                maxDistance = sumDistance;
                out = noun;
            }
        }
        return out;
    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        String[] nouns = StdIn.readAllStrings();
        String outcastNoun = outcast.outcast(nouns);
        for (String noun : nouns) {
            StdOut.print(noun.equals(outcastNoun) ? "*" + noun + "* " : noun + " ");
        }
        StdOut.println();
    }
}
