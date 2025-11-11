/*
 * Copyright (c) 2025, KASTEL. All rights reserved.
 */

package edu.kit.capybara;

import java.util.Scanner;

/**
 * Main class of the calculator.
 * @author Programmieren-Team
 */
public final class Calculator {

    // First, we define some constants and give them names. The names should focus on the meaning,
    // so what it's used for, and not what the current value is. The value can change, the meaning
    // doesn't.

    private static final String EXIT_COMMAND_NAME = "quit";

    private static final String ERROR_NO_OPERATORS = "Error, du musst einen gültigen Rechenoperator benutzen";
    private static final String ERROR_PARSE_INPUT = "Error, die Eingabe enthält nicht das korrekte Format. Das korrekte "
            + "Format ist: a <operator> b, bzw: \" *\\d+ *[+-*/^] *\\d+ *\"%n";
    private static final String ERROR_DIVIDE_BY_ZERO = "Error, du darfst nicht durch 0 teilen";
    private static final int INVALID_INDEX_VALUE = -1;

    // This is an array of characters. It's a sort of list with constant size.
    private static final char[] VALID_OPERATORS = { '+', '-', '*', '/', '^' };

    private Calculator() {
        throw new UnsupportedOperationException();
    }

    /**
     * The main calculator program.
     * @param args the command line arguments. Not used.
     */
    public static void main(String[] args) {

        // Create a new Scanner and have it listen to the System Input Stream.
        // This is the thing that where console input can be read from, like System.out
        // is where the console output goes.

        Scanner scanner = new Scanner(System.in);

        // while (condition) does something as long as:
        //   - the condition is true
        //   - we don't break out of the loop using break
        // So, while(true) means "do this forever unless we manually break out somewhere"

        while (true) {
            // Get the next line entered from the command line. The program will wait here until enter is pressed.
            String line = scanner.nextLine();
            // If the line is the quit command, we break out of the loop. When we then exit the main method,
            // our program is finished.
            if (line.equals(EXIT_COMMAND_NAME)) {
                break;
            }

            // Next, we want to find out what operator we entered, if any. -1 represents an invalid value.
            // We loop through all the values. This may look weird, what this is doing is called a "for-each"
            // loop, and we use it if we are not interested in the index of the element but rather just the
            // element. This loop is equivalent:
            //
            // for (int i = 0; i < VALID_OPERATORS.length; i++) {
            //     char op = VALID_OPERATORS[i];
            //     <Rest of the code>
            // }
            //
            // Which is, of course, a bit bulkier if we don't care about the i.
            // Next, line.indexOf(op) gives us the position where in the line the character op appears, or
            // -1 if it doesn't appear. If this index happens to not be -1, we know that this is the operator
            // we are using and we know where it is. We use a break; to exit the loop, since we don't need to
            // check more characters.

            int operatorIndex = INVALID_INDEX_VALUE;
            for (char op : VALID_OPERATORS) {
                int index = line.indexOf(op);
                if (index != INVALID_INDEX_VALUE) {
                    operatorIndex = index;
                    break;
                }
            }

            // This is a thing we did not have in the code I wrote in the last 20 minutes of the tutorial.
            // If, after checking all characters, the operatorIndex is still invalid, it means there were
            // no (valid) operators in the line. So, we print an error and do "continue", which puts us
            // immediately at the start of the while loop again, where we wait for the next input.

            if (operatorIndex == INVALID_INDEX_VALUE) {
                System.out.println(ERROR_NO_OPERATORS);
                continue;
            }

            // Now comes the calculator part. First, we get the character that is at the operator position.
            // Note that an index always starts at 0, so the first character has index 0, the second has index
            // 1, and so forth.
            // Then, we parse the two numbers. We use the substring method that basically works like this:
            //
            //     someString.substring( Index of the first character we wish to include,
            //       index of the last character we wish to include );
            //
            // That method returns a part of the String, so "abcde".substring(0, 2) would return "ab",
            // and "abcdef".substring(1, 3) would return "bc".
            // What we do is get the part from 0 to (but excluding) the operator symbol, and then from
            // the character after operator symbol to the end. This could theoretically crash if the
            // operator was the last character, but we ignored that here.
            // After we have the part before and after the operator, we "trim" it, meaning to cut off
            // leading and trailing spaces. This is not necessary, but it's a nice touch since "2 + 2"
            // also works with this.
            //
            // To convert the string to an integer in code, we "parse" it using Integer.parse("...").
            // A problem is that when we just do this, an error (or: "Exception") can occur if the given
            // String is not actually a valid number. In this case, an Exception is thrown, and usually,
            // this would cause our beloved program to crash. To prevent this, we need to "catch" the
            // thrown exception and need to tell the program what to do.
            //
            // The syntax works like this:
            //
            // try {
            //     <Some stuff that could throw an exception>
            // } catch (The Exception) {
            //     <Do whatever, for example print an error>
            // }
            //
            // In our case, we print out an error message and skip to the top of the loop, just like we did
            // above when we didn't find an operator.

            char operator = line.charAt(operatorIndex);

            int leftOperand;
            int rightOperand;
            try {
                leftOperand = Integer.parseInt(line.substring(0, operatorIndex).trim());
                rightOperand = Integer.parseInt(line.substring(operatorIndex + 1).trim());
            } catch (NumberFormatException e) {
                System.out.printf(ERROR_PARSE_INPUT);
                continue;
            }

            // At this point, everything is probably good and we just need to perform the operation.
            // For clarity, and so this method doesn't get too long, we put that logic somewhere else.

            performOperation(leftOperand, rightOperand, operator);
        }

        // A scanner should usually be closed. This is mainly important for when you're reading files,
        // since files can become corrupted if you don't close the scanner before exiting, but for
        // the System input stream, it doesn't really matter.
        scanner.close();
    }

    /**
     * Actually performs the operation. If the operator is not a valid one, does nothing,
     * otherwise, immediately prints the solution. Divide by zero exception is accounted
     * for and handled appropriately.
     * @param leftOperand The left operand of the calculation.
     * @param rightOperand The right operand of the calculation.
     * @param operator The operator to perform.
     */
    private static void performOperation(int leftOperand, int rightOperand, char operator) {
        // A switch is basically a more specific variant of if-else blocks. It works like this:
        // switch( some variable ) {
        //   in case it's this:
        //     do this
        //     break;
        //   in case it's some other thing:
        //     do this
        //     break;
        //
        // The break is used to exit the switch statement, otherwise, we go into
        // the next case block, until we're done or we reach a break.
        // Assume we have a variable number that is some number.
        // Below are English, Java's if-else and Java's switch:
        //
        // if the number is 3:
        //     print "three"
        // otherwise, if the number is 5 or the number is 6:
        //     print that the number is five or six
        // if none of the above:
        //     print that you don't know the number
        //
        //
        // if (number == 3) {
        //     System.out.println("three");
        // } else if (number == 5 || number == 6) {
        //     System.out.println("The number is five or six");
        // } else {
        //     System.out.println("I don't know the number");
        // }
        //
        //
        // switch (number) {
        //     case 3:
        //         System.out.println("three");
        //         break;
        //     case 5:    // <-- no break, so we execute the next block if it's 5 or 6. That's why we need break
        //     case 6:
        //         System.out.println("The number is five or six");
        //         break;
        //     default:
        //         System.out.println("I don't know the number");
        //         break;
        // }
        //
        //
        //  Using a switch is, of course, completely optional. When compiling, Java does what it thinks is best,
        //  so you will probably not really notice a difference in performance or anything. It's just more readable
        //  if you're always doing "if (variable == <value>) { ... }"

        switch (operator) {
            case '+':
                System.out.println(leftOperand + rightOperand);
                break;
            case '-':
                System.out.println(leftOperand - rightOperand);
                break;
            case '*':
                System.out.println(leftOperand * rightOperand);
                break;
            case '/':

                // Relevant case: If we divide by zero, it can cause an exception.
                // We could do this with a try-catch again, but it's better to not do a try-catch
                // where we don't need to, and in this case, a simple if-else is sufficient:

                if (rightOperand == 0) {
                    System.out.println(ERROR_DIVIDE_BY_ZERO);
                } else {
                    System.out.println(leftOperand / rightOperand);
                }
                break;
            case '^':

                // What's special here: We use Math.pow because Java has no operator for power.
                // Math.pow gives us a "double", meaning a decimal value, and we tell it to drop
                // the decimal places and simply convert to an Integer with (int).
                // Usually, outside of these situations, we generally want to avoid casting (which
                // is what this is called) in our own classes, since there are better ways of solving
                // the underlying problem. For now, don't worry about it.

                System.out.println((int) Math.pow(leftOperand, rightOperand));
                break;
            default:
                break;
        }
    }
}