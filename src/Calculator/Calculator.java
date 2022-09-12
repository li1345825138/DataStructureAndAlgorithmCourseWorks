package Calculator;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * You will be writing a program that implements a simple calculator that is capable of a few
 * simple arithmetic operations Your program can run in two different modes: 1) Non-interactive
 * and 2) interactive.
 * @author Lengqiang Lin
 * @date 09/12/2022
 */
public class Calculator {
    public static void main(String[] args) {
        try{
            if (args.length > 3) {
                System.err.println("Parse Index out of bounce!");
                System.exit(-1);
            }
            if (args.length > 0) {
                nonInteractiveMode(args);
            } else {
                interactiveMode();
            }
        }catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Index out of Bounce! if you use '^' as operator, please write " +
                    "as '^^' if you on CMD");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * You will be writing a program that implements a simple calculator that is capable of a
     * few simple arithmetic operations, as described below.
     * @param args command line input arguments
     * @throws ArithmeticException throw exception if divide number is zero
     * @throws IllegalArgumentException throw exception if arguments is not legal
     * @throws ArrayIndexOutOfBoundsException throw exception if args index is out of bounce
     */
    static void nonInteractiveMode(String[] args) throws ArithmeticException,
            IllegalArgumentException, ArrayIndexOutOfBoundsException {
        double result;

        try {
            double num1 = Double.parseDouble(args[0]);
            double num2 = Double.parseDouble(args[2]);
            char operator = args[1].charAt(0);
            result = getResult(operator, num1, num2);
            if (num1 % 1 == 0 && num2 % 1 == 0) { // if both numbers is whole number
                System.out.printf("%.0f\n", result);
            } else {
                System.out.printf("%.1f\n", result);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ArrayIndexOutOfBoundsException();
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Error: Invalid Value. Operands must be numbers.");
        }
    }

    /**
     * In interactive mode, your program runs without any command line arguments and then asks
     * the users to enter the operation he/she wants to perform and the first operand and the
     * second operand in order. The followings are some exampes. Note that the regular font
     * indicate the program's output and bold font indicates users' input.
     */
    static void interactiveMode() {
        try (
                // Auto close input resource
                Scanner input = new Scanner(System.in)
                ) {
            char operator;
            while (true) {
                System.out.print("Operation? ");
                operator = input.next().charAt(0);
                if (operator != '+' && operator != '-' && operator != '*' && operator != '/'
                        && operator != '^' && operator != '%') {
                    System.out.println("Error: Wrong operation! Valid operations are " +
                            "+, -, *, /, ^ and %. Please try again.");
                    continue;
                }
                break;
            }
            double[] numbers = getValidateInputValue(input, operator);
            double result = getResult(operator, numbers[0], numbers[1]);

            // if both numbers is whole number
            if (numbers[0] % 1 == 0 && numbers[1] % 1 == 0) {
                System.out.printf("%.0f\n", result);
            } else {
                System.out.printf("%.1f\n", result);
            }
        } catch (Exception e) {
            // any unknow exception
            e.printStackTrace();
        }
    }

    /**
     * Doing calculation
     * @param operator calculate operator
     * @param num1  first number recieved
     * @param num2  second number received
     * @return  result of calculation
     * @throws ArithmeticException throw exception if divide by zero
     * @throws IllegalArgumentException throw exception if illegal number argument(like mod float
     * number or invalid operator)
     */
    static double getResult(char operator, double num1, double num2) throws ArithmeticException,
            IllegalArgumentException {
        double result;
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
                if (num2 == 0f) {
                    throw new ArithmeticException("Error: Invalid value for the second operand." +
                            " Divisor cannot be zero.");
                }
                result = num1 / num2;
                break;
            case '^':
                result = Math.pow(num1, num2);
                break;
            case '%':
                if (num1 % 1 != 0 || num2 % 1 != 0) { // if is not whole number
                    throw new IllegalArgumentException("Error: Wrong value! Operands of" +
                            "modulu operations must be integer. Please try again.");
                }
                result = num1 % num2;
                break;
            default:
                throw new IllegalArgumentException("Error: Wrong operation! Valid operations" +
                        "are +, -, *, /, ^ and %. Please try again.");
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
