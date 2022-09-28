package project1;

/**
 * Implement the static method isPrime() in PrimeCounter.java that accepts an integer x and returns
 * true if x is prime and false otherwise. Also implement the static method primes() that accepts
 * an integer n and returns the number of primes less than or equal to n — a number x is prime if
 * it is not divisible by any number i ∈ [2, √x.
 *
 * @author Lengqiang Lin
 * @date 09/27/2022
 */
public class PrimeCounter {
    public static void main(String[] args) {
        try {
            int n = Integer.parseInt(args[0]);

            // get how many prime number on n range
            int primeCount = primes(n);

            // print out result
            System.out.println(primeCount);
        } catch (NumberFormatException e) {

            // if number is not a integer number
            System.err.println("Error: argument must be Integer number!");
        } catch (IllegalArgumentException e){

            // if number is less than 0
            System.err.println(e.getMessage());
        } catch (Exception e) {

            // any other exception
            System.err.println(e.getMessage());
        }
    }

    /**
     * Count how many prime in range of [0, n]
     * @param n The max range
     * @return how many prime number are in that range
     * @throws IllegalArgumentException if n is less than 0
     */
    static int primes(int n) throws IllegalArgumentException {
        if (n < 0) {
            throw new IllegalArgumentException("Error: number couldn't less than 0!");
        }
        int count = 0;
        for (int i = 0; i <= n; i++) {
            if (isPrime(i)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Check current number is prime or not
     * @param x number to check
     * @return true if is prime, false otherwise
     */
    static boolean isPrime(int x) {
        if (x <= 1) {
            return false;
        } else if (x == 2) {
            return true;
        }
        for (int i = 2; i < x; i++) {
            if (x % i == 0) {
                return false;
            }
        }
        return true;
    }
}
