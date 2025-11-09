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
    private static final String EXIT_COMMAND_NAME = "quit";

    private static final String ERROR_NO_OPERATORS = "Error, du musst einen gültigen Rechenoperator benutzen";
    private static final String ERROR_PARSE_INPUT = "Error, die Eingabe enthält nicht das korrekte Format. Das korrekte "
            + "Format ist: a <operator> b, bzw: \" *\\d+ *[+-*/^] *\\d+ *\"%n";
    private static final String ERROR_DIVIDE_BY_ZERO = "Error, du darfst nicht durch 0 teilen";

    private static final char[] VALID_OPERATORS = { '+', '-', '*', '/', '^' };

    private Calculator() {
        throw new UnsupportedOperationException();
    }

    /**
     * The main calculator program.
     * @param args the command line arguments. Not used.
     */
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        while (true) {
            String line = scanner.nextLine();
            if (line.equals(EXIT_COMMAND_NAME)) {
                break;
            }

            int operatorIndex = -1;
            for (char op : VALID_OPERATORS) {
                int index = line.indexOf(op);
                if (index != -1) {
                    operatorIndex = index;
                    break;
                }
            }
            if (operatorIndex == -1) {
                System.out.println(ERROR_NO_OPERATORS);
                continue;
            }
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

            performOperation(leftOperand, rightOperand, operator);
        }

        // should be closed, though it's not relevant for System.in
        scanner.close();
    }

    private static void performOperation(int leftOperand, int rightOperand, char operator) {
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
                if (rightOperand == 0) {
                    System.out.println(ERROR_DIVIDE_BY_ZERO);
                } else {
                    System.out.println(leftOperand / rightOperand);
                }
                break;
            case '^':
                System.out.println((int) Math.pow(leftOperand, rightOperand));
                break;
            default:
                break;
        }
    }
}