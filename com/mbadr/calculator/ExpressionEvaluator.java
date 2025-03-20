package com.mbadr.calculator;
import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.Deque;

public class ExpressionEvaluator {
    private final VariableStore variables;

    public ExpressionEvaluator(VariableStore variables) {
        this.variables = variables;
    }

    public String evaluate(String expression) {
        String postfix = InfixToPostfix.convert(expression);
        return evaluatePostfix(postfix.split("\\s+"));
    }

    private String evaluatePostfix(String[] tokens) {
        Deque<BigInteger> stack = new ArrayDeque<>();

        for (String token : tokens) {
            if (isOperator(token)) {
                if (stack.size() < 2) throw new CalculatorException("Invalid expression");
                BigInteger b = stack.pop();
                BigInteger a = stack.pop();
                stack.push(calculate(a, b, token));
            } else {
                stack.push(parseOperand(token));
            }
        }
        if (stack.size() != 1) throw new CalculatorException("Invalid expression");
        return stack.pop().toString();
    }

    private BigInteger parseOperand(String token) {
        String name = token.matches("[a-zA-Z]+") ? token : null;
        return name != null ? variables.getValue(name) : new BigInteger(token);
    }

    private BigInteger calculate(BigInteger a, BigInteger b, String operator) {
        return switch (operator) {
            case "+" -> a.add(b);
            case "-" -> a.subtract(b);
            case "*" -> a.multiply(b);
            case "/" -> {
                if (b.equals(BigInteger.ZERO)) throw new CalculatorException("Division by zero");
                yield a.divide(b);
            }
            default -> throw new CalculatorException("Unknown operator");
        };
    }

    private boolean isOperator(String token) {
        return "+-*/".contains(token);
    }
}
