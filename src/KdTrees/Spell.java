package KdTrees;

import stdlib.In;
import stdlib.StdIn;
import stdlib.StdOut;

/**
 * Implement a program called Spell that accepts f ilename (String) as command-line argument,
 * which is the name of a file containing common misspellings (a line-oriented file with each
 * comma-separated line containing a misspelled word and the correct spelling); reads text from
 * standard input; and writes to standard output the misspelled words in the text, the line numbers
 * where they occurred, and their corrections.
 *
 * @author Lengqiang Lin
 * @date 11/29/2022
 */
public class Spell {
    // Entry point.
    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] lines = in.readAllLines();
        in.close();

        // Create an ArrayST<String, String> object called st.
        ArrayST<String, String> st = new ArrayST<>();

        // For each line in lines, split it into two tokens using "," as delimiter; insert into
        // st the key-value pair (token 1, token 2).
        String[] words = null;
        for (String word : lines) {
            words = word.split(",");
            st.put(words[0], words[1]);
        }

        // Read from standard input one line at a time; increment a line number counter; split
        // the line into words using "\\b" as the delimiter; for each word in words, if it
        // exists in st, write the (misspelled) word, its line number, and corresponding value
        // (correct spelling) from st.
        String line = null;
        int counter = 0;

        while (!StdIn.isEmpty()) {
            line = StdIn.readLine();
            counter++;
            words = line.split("\\b");
            for (String word : words) {
                if (st.contains(word)) {
                    StdOut.printf("%s:%d -> %s\n", word, counter, st.get(word));
                }
            }
        }
    }
}
