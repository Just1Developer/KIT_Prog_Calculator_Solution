/*
 * Copyright (c) 2025, KASTEL. All rights reserved.
 */

package edu.kit.benjamin;

import java.util.Scanner;

/**
 * Main class of the calculator.
 * @author uuuuu
 */
public final class Calculator {
    private Calculator() {
        throw new UnsupportedOperationException();
    }

    /**
     * Main calculator lol.
     * @param args yes
     */
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        while (true) {
            String line = sc.nextLine();
            if (line.equals("quit")) {
                break;
            }

            int leftOperand = Integer.parseInt(line.substring(0, 1));
            int rightOperand = Integer.parseInt(line.substring(2, 3));

            switch (line.charAt(1)) {
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
                    System.out.println(leftOperand / rightOperand);
                    break;
                default:
                    break;
            }
        }

    }
}