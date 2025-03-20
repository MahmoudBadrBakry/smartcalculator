package com.mbadr.calculator;
import java.util.ArrayDeque;
import java.util.Deque;

public class InfixToPostfix {

    public static final String OPERATOR = "+-*/()";

    public static String convert(String expression) {
        StringBuilder result = new StringBuilder();
        Deque<String> stack = new ArrayDeque<>();

        for (int i = 0; i < expression.length(); i++) {
            String token = nextToken(expression, i);
            i += token.length() - 1;

            if (isOperand(token)) {
                result.append(token).append(" ");
            } else if (token.equals("(")) {
                stack.push(token);
            } else if (token.equals(")")) {
                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    result.append(stack.pop()).append(" ");
                }
                if (stack.isEmpty()) throw new CalculatorException("Invalid expression");
                stack.pop();
            } else {
                while (!stack.isEmpty() && getPrecedence(token) <= getPrecedence(stack.peek())) {
                    result.append(stack.pop()).append(" ");
                }
                stack.push(token);
            }
        }

        while (!stack.isEmpty()) {
            if (stack.peek().equals("(")) throw new CalculatorException("Invalid expression");
            result.append(stack.pop()).append(" ");
        }
        return result.toString().trim();
    }

    private static String nextToken(String expression, int start) {
        if (start >= expression.length()) return "";
        char c = expression.charAt(start);
        if (OPERATOR.indexOf(c) != -1) return String.valueOf(c);

        int end = start;
        while (end < expression.length() && OPERATOR.indexOf(expression.charAt(end)) == -1) {
            end++;
        }
        return expression.substring(start, end);
    }

    private static int getPrecedence(String operator) {
        return switch (operator) {
            case "+", "-" -> 1;
            case "*", "/" -> 2;
            default -> -1;
        };
    }

    private static boolean isOperand(String token) {
        return !OPERATOR.contains(token);
    }
}
