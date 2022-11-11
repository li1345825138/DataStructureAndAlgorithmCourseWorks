package Eight_Puzzle;

import stdlib.StdOut;

/**
 * Srinivasa Ramanujan was an Indian mathematician who became famous for his intuition for numbers.
 * When the English mathematician G. H. Hardy came to visit him one day, Hardy remarked that the
 * number of his taxi was 1729, a rather dull number. To which Ramanujan replied, “No, Hardy! It
 * is a very interesting number. It is the smallest number expressible as the sum of two cubes in
 * two different ways.” Verify this claim by writing a program Ramanujan1.java that accepts n (int)
 * as command-line argument and writes to standard output all integers less than or equal to n that
 * can be expressed as the sum of two cubes in two different ways. In other words, find distinct
 * positive integers a, b, c, and d such that a
 * 3 + b
 * 3 = c
 * 3 + d
 * 3 ≤ n.
 */
public class Ramanujan1 {
    // Entry point.
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        // for each a, b, c, d, check if a^3 + b^3 = c^3 + d^3
        for (int a = 1; a <= n; a++) {
            int cubicA = a * a * a;

            // check if cubicA is greater than n, if does mean no solution is possible,
            // break
            if (cubicA > n) {
                break;
            }

            // avoid duplicates B
            for (int b = a; b <= n; b++) {
                int cubicB = b * b * b;
                if (cubicA + cubicB > n) {
                    break;
                }

                // avoid duplicates C
                for (int c = a + 1; c <= n; c++) {
                    int cubicC = c * c * c;
                    if (cubicC > cubicA + cubicB) {
                        break;
                    }

                    // avoid duplicates D
                    for (int d = c; d <= n; d++) {
                        int cubicD = d * d * d;
                        if (cubicC + cubicD > cubicA + cubicB) {
                            break;
                        }

                        // if a^3 + b^3 = c^3 + d^3, print out result
                        if (cubicA + cubicB == cubicC + cubicD) {
                            StdOut.printf("%d = %d^3 + %d^3 = %d^3 + %d^3\n", (cubicA + cubicB), a,
                                    b, c, d);
                        }
                    }
                }
            }
        }
    }
}
