package edu.umb.cs210.p6;

import stdlib.In;
import stdlib.StdOut;

// An immutable data type for outcast detection.
public class Outcast {
    private WordNet wh;

    // Construct an Outcast object given a WordNet object.
    public Outcast(WordNet wordnet) {
        this.wh = wordnet;
    }

    // The outcast noun from nouns.
    public String outcast(String[] nouns) {
        String outP = "";
        int max = Integer.MIN_VALUE;
        for (String noun : nouns) {
            int sum = 0;
            for (String noun1 : nouns) {
                sum += wh.distance(noun, noun1);
            }
            if (sum > max) {
                max = sum;
                outP = noun;
            }
        }
        return outP;
    }

    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println("outcast(" + args[t] + ") = "
                           + outcast.outcast(nouns));
        }
    }
}
