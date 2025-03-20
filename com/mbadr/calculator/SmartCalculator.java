package com.mbadr.calculator;

import java.util.Scanner;

public class SmartCalculator {
    private final Scanner scanner;
    private final VariableStore variables;
    private final ExpressionEvaluator evaluator;
    private final CommandProcessor commandProcessor;

    public SmartCalculator() {
        this.scanner = new Scanner(System.in);
        this.variables = new VariableStore();
        this.evaluator = new ExpressionEvaluator(variables);
        this.commandProcessor = new CommandProcessor();
    }

    public static void main(String[] args) {
        new SmartCalculator().run();
    }

    public void run() {
        while (true) {
            try {
                String input = InputProcessor.removeSpaces(scanner.nextLine());
                if (input.isEmpty()) continue;

                InputType type = InputClassifier.classify(input);
                String output = processInput(input, type);

                if (output != null) {
                    if (output.equals("Bye!")) break;
                    if (!output.isEmpty()) System.out.println(output);
                }
            } catch (CalculatorException e) {
                System.out.println(e.getMessage());
            }
        }
        scanner.close();
        System.out.println("Bye!");
    }

    private String processInput(String input, InputType type) {
        return switch (type) {
            case EXPRESSION -> evaluator.evaluate(InputProcessor.format(input));
            case COMMAND -> commandProcessor.execute(input);
            case ASSIGNMENT -> {
                variables.assign(input);
                yield null;
            }
            case PRINT -> variables.getValue(input).toString();
            case UNKNOWN -> null;
        };
    }
}

