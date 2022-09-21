package PrimeFinder;

import java.util.ArrayList;

/**
 * takes two whole numbers on the command line that are greater than 0 and smaller
 * than 2,000,000,001 and finds all prime numbers between the two numbers and then prints them
 * on the screen once all those numbers have been found. Note that you should not print the primes
 * as you find them. First, find all of them and then print them out.
 * @author Lengqiang Lin
 * @date 09/20/2022
 */
public class PrimeFinder {
    public static void main(String[] args) {
        try{
            int beg = Integer.parseInt(args[0]);
            int end = Integer.parseInt(args[1]);

            // check arguments to see if it valid
            checkArguments(beg, end);

            // get all prime number array list from method
            ArrayList<Integer> primeNums = findAllPrime(beg, end);

            // print out result
            System.out.println(primeNums);
        } catch (ArrayIndexOutOfBoundsException e){
            // if user didn't finish input, such as arguments is less than 2 or no arguments
            System.err.println("Error: Incomplete arguments input!\nArguments length is" +
                    " less than 2!");
        } catch (NumberFormatException e) {
            // if user input arguments is other than whole number
            System.err.println("Error: Input arguments must be whole number!");
        } catch (IllegalArgumentException e) {
            // some illegal arguments that throw by checkValidArguments method
            System.err.println(e.getMessage());
        } catch (Exception e) {
            // any other unknown exception
            e.printStackTrace();
        }
    }

    /**
     * Check input arguments
     * Throw Exception if there has Illegal argument
     * @param num1 first number to check
     * @param num2 second number to check
     * @throws IllegalArgumentException throw exception if cause by method
     */
    static void checkArguments(int num1, int num2) throws IllegalArgumentException {
        if (num2 < num1) {
            throw new IllegalArgumentException("Error: The Second number couldn't smaller" +
                    " than First number!");
        } else if (num1 <= 0 || num2 <= 0) {
            throw new IllegalArgumentException("Error: The input arguments couldn't" +
                    " be less equal than 0!");
        } else if (num1 >= 2000000001 || num2 >= 2000000001) {
            throw new IllegalArgumentException("Error: The input arguments is too large!\n" +
                    "Accept range is [1, 2000000001)");
        }
    }

    /**
     * Find all numbers from [beg, end]
     * @param beg number to begin with
     * @param end number to end with
     * @return all the numbers in array that are prime between [beg, end]
     */
    static ArrayList<Integer> findAllPrime(int beg, int end) {
        ArrayList<Integer> primeNums = new ArrayList<>();
        int tmp = beg;
        while (tmp <= end) {
            if (isPrime(tmp)) {
                primeNums.add(tmp);
            }
            tmp++;
        }
        return primeNums;
    }

    /**
     * Check if num is a prime number
     * @param num the number that will be check
     * @return true if is prime, false is not
     */
    static boolean isPrime(int num) {
        if (num <= 1) {
            return false;
        } else if (num == 2) {
            return true;
        } else if (num % 2 == 0) {
            return false;
        }
        num = (int)Math.sqrt(num);
        for (int i = 3; i <= num; i += 2) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }
}
