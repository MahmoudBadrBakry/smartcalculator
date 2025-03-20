package com.mbadr.calculator;
public class InputProcessor {
    public static String removeSpaces(String input) {
        return input.replaceAll("\\s+", "");
    }

    public static String format(String input) {
        return input.replaceAll("--+", "+")
                .replaceAll("\\++", "+")
                .replaceAll("\\+-\\+*", "-")
                .replace("-\\+-", "+");
    }
}
