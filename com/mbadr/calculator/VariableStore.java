package com.mbadr.calculator;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VariableStore {
    private final Map<String, BigInteger> variables = new HashMap<>();
    private static final Pattern ASSIGNMENT_PATTERN = Pattern.compile("(\\w+) ?= ?(-?\\w+)");

    public void assign(String input) {
        Matcher matcher = ASSIGNMENT_PATTERN.matcher(input);
        if (!matcher.matches()) throw new CalculatorException("Invalid assignment");

        String name = matcher.group(1);
        String value = matcher.group(2);

        validateIdentifier(name);
        BigInteger number = parseValue(value);
        variables.put(name, number);
    }

    public BigInteger getValue(String name) {
        if (!variables.containsKey(name)) throw new CalculatorException("Unknown variable");
        return variables.get(name);
    }

    private void validateIdentifier(String name) {
        if (name.matches(".*\\d.*")) throw new CalculatorException("Invalid identifier");
    }

    private BigInteger parseValue(String value) {
        if (value.matches("[a-zA-Z]+")) {
            return variables.getOrDefault(value,
                    BigInteger.ZERO);
        }
        try {
            return new BigInteger(value);
        } catch (NumberFormatException e) {
            throw new CalculatorException("Invalid number format");
        }
    }
}
