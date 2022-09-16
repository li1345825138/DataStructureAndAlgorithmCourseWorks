package Calculator;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * You will be writing a program that implements a simple calculator that is capable of a few
 * simple arithmetic operations Your program can run in two different modes:
 * 1) Non-interactive
 * 2) interactive.
 * @author Lengqiang Lin
 * @date 09/12/2022
 */
public class Calculator {
    public static void main(String[] args) {
        try{
            // A correct non-interactive equation should have 3 arguments
            if (args.length == 3) {
                nonInteractiveMode(args);
            } else {
                // if arguments length is smaller than 3 or larger than 3
                // which mean is illegal input run in interactive mode
                interactiveMode();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage()); // any unknown exception
        }
    }

    /**
     * Calculator run in non-interactive mode
     * @param args command line input arguments
     * @throws ArithmeticException throw exception if divide number is zero
     * @throws IllegalArgumentException throw exception if arguments is not legal
     * @throws ArrayIndexOutOfBoundsException throw exception if args index is out of bounce
     */
    static void nonInteractiveMode(String[] args) throws ArithmeticException,
            IllegalArgumentException, ArrayIndexOutOfBoundsException {
        double result;
        try {
            char operator = getValidateOperator(args[1]);
            double num1 = Double.parseDouble(args[0]);
            double num2 = Double.parseDouble(args[2]);

            // if operator is divide, check divisor number
            if (operator == '/' && num2 == 0f) {
                throw new ArithmeticException("Error: Invalid value for the second operand." +
                        " Divisor cannot be zero.");
            }

            // if operator is modulu, check if number is whole number
            if (operator == '%' && num1 % 1 != 0 || operator == '%' && num2 % 1 != 0) {
                throw new IllegalArgumentException("Error: Wrong value! Operands of" +
                        " modulu operations must be integer. Please try again.");
            }
            result = getResult(operator, num1, num2);
            if (num1 % 1 == 0 && num2 % 1 == 0) { // if both numbers is whole number
                System.out.printf("%.0f\n", result);
            } else {
                System.out.printf("%.1f\n", result);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ArrayIndexOutOfBoundsException("Error: Args index is out of bounce!");
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Error: Invalid Value. Operands must be numbers.");
        }
    }

    /**
     * In interactive mode, your program runs without any command line arguments and then asks
     * the users to enter the operation he/she wants to perform and the first operand and the
     * second operand in order.
     */
    static void interactiveMode() {
        Scanner input = new Scanner(System.in);
        char operator;
        while (true) {
            System.out.print("Operation? ");
            try {
                operator = getValidateOperator(input.nextLine());
            } catch (IllegalArgumentException e) {
                System.err.println(e.getMessage());
                continue;
            }
            break;
        }
        double[] numbers = getValidateInputValue(input, operator);
        input.close(); // close scanner input resource

        double result = getResult(operator, numbers[0], numbers[1]);

        // if both numbers is whole number
        if (numbers[0] % 1 == 0 && numbers[1] % 1 == 0) {
            System.out.printf("%.0f\n", result);
        } else {
            System.out.printf("%.1f\n", result);
        }
    }

    /**
     * Get Valid operator as char from received string operator
     * @param operator String operator
     * @return Valid calculate operator
     */
    static char getValidateOperator(String operator) {
        char validOperator;
        if (operator.equals("x")) {
            validOperator = '*';
        } else if (operator.equals("xx")) {
            validOperator = '^';
        } else {
            validOperator = operator.charAt(0);
        }

        // check is valid operator
        if (validOperator == '+' || validOperator == '-' || validOperator == '*'
                || validOperator == '/' || validOperator == '^' || validOperator == '%') {
            return validOperator;
        }
        throw new IllegalArgumentException("Error: Wrong operation! Valid operations" +
                "are +, -, *(x), /, ^(xx) and %. Please try again.");
    }

    /**
     * Doing calculation
     * @param operator calculate operator
     * @param num1  first number recieved
     * @param num2  second number received
     * @return  result of calculation
     * @throws IllegalArgumentException throw exception if illegal number argument(like mod float
     * number or invalid operator)
     */
    static double getResult(char operator, double num1, double num2)
            throws IllegalArgumentException {
        double result = 0.0;
        switch (operator) {
            case '+':
                result = num1 + num2;
                break;
            case '-':
                result = num1 - num2;
                break;
            case '*':
                result = num1 * num2;
                break;
            case '/':
                result = num1 / num2;
                break;
            case '^':
                result = Math.pow(num1, num2);
                break;
            case '%':
                result = num1 % num2;
        }
        return result;
    }

    /**
     * Get validate input value for interative mode
     * @param input input scanner
     * @param operator  calculate operator
     * @return the numbers from scanner input
     */
    static double[] getValidateInputValue(Scanner input, char operator) {
        double num1;
        double num2;
        // validate first value input
        while (true) {
            System.out.print("First number? ");
            try {
                num1 = input.nextDouble();
                // if operator is % and number is not whole number
                if (operator == '%' && num1 % 1 != 0) {
                    System.out.println("Error: Wrong value! Operands of modulu operations must" +
                            " be integer. Please try again.");
                    continue;
                }
            } catch (InputMismatchException e) {
                // if input number is not number, such like english letters
                System.out.println("Error: Invalid Value. Operands must be numbers.");
                input.nextLine(); // clear input cache
                continue;
            }
            break;
        }

        // validate second value input
        while (true) {
            System.out.print("Second number? ");
            try {
                num2 = input.nextDouble();
                // if operator is % and number is not whole number
                if (operator == '%' && num2 % 1 != 0) {
                    System.out.println("Error: Wrong value! Operands of modulu operations must" +
                            " be integer. Please try again.");
                    continue;
                } else if (operator == '/' && num2 == 0f) {
                    System.err.println("Wrong value!. The divisor cannot be zero. Please" +
                            " try again.");
                    continue;
                }
            } catch (InputMismatchException e) {
                // if input number is not number, such like english letters
                System.out.println("Error: Invalid Value. Operands must be numbers.");
                input.nextLine(); // clear input cache
                continue;
            }
            break;
        }
        return new double[]{num1, num2};
    }
}
