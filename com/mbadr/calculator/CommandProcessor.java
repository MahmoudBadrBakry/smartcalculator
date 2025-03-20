package com.mbadr.calculator;
public class CommandProcessor {
    public String execute(String command) {
        return switch (command) {
            case "/exit" -> "Bye!";
            case "/help" -> """
                /** The program calculates arithmetic expressions with:
                * Support for +, -, *, / operations
                * Parentheses for precedence
                * Variable assignment and usage
                * Example:
                > x = 5
                > y = 4
                > y + x - 3
                */""";
            default -> throw new CalculatorException("Unknown command");
        };
    }
}
