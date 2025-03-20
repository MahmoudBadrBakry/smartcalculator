package com.mbadr.calculator;
import java.util.regex.Pattern;

public class InputClassifier {
    private static final Pattern COMMAND_PATTERN = Pattern.compile("^/\\w+$");
    private static final Pattern ASSIGNMENT_PATTERN = Pattern.compile("^\\w+=-?\\w+$");
    private static final Pattern PRINT_PATTERN = Pattern.compile("^-?\\w+$");
    private static final Pattern EXPRESSION_PATTERN = Pattern.compile("^(\\(?[+\\-]?(\\w+)?(-|\\+|\\*|/)?[+\\-]?(\\w+)?\\)?)+$");

    public static InputType classify(String input) {
        if (COMMAND_PATTERN.matcher(input).matches()) return InputType.COMMAND;
        if (ASSIGNMENT_PATTERN.matcher(input).matches()) return InputType.ASSIGNMENT;
        if (PRINT_PATTERN.matcher(input).matches()) {
            return Character.isLetter(input.charAt(0)) ? InputType.PRINT : InputType.EXPRESSION;
        }
        if (EXPRESSION_PATTERN.matcher(input).matches()) return InputType.EXPRESSION;
        return InputType.UNKNOWN;
    }
}
