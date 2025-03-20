# SmartCalculator

A sophisticated command-line calculator built in Java that supports arithmetic operations, variables, and expression evaluation with parentheses.

## Features

- Basic arithmetic operations: addition (+), subtraction (-), multiplication (*), division (/)
- Support for parentheses to control operation precedence
- Variable assignment and usage
- Command system with help and exit functionality
- BigInteger support for arbitrary-precision arithmetic
- Robust error handling for invalid inputs

## Requirements

- Java 17 or later
- No external dependencies required

## Installation

1. Clone the repository:
```bash
git clone https://github.com/username/smartcalculator.git
```

Navigate to the project directory:
```bash

cd smartcalculator
```

Compile the code:

```bash

javac com/mbadr/calculator/*.java
```
Run the calculator:
```bash
java com.mbadr.calculator.SmartCalculator
```
Usage
The calculator accepts various types of input:

Arithmetic Expressions
```text

> 5 + 3 - 2
6
> (2 + 3) * 4
20
```
Variable Assignment
```text
> x = 5
> y = 10
> x + y
15
```
Variable Printing
```text
> x
5
```
Commands
```text
> /help
/** The program calculates arithmetic expressions with:
* Support for +, -, *, / operations
* Parentheses for precedence
* Variable assignment and usage
* Example:
> x = 5
> y = 4
> y + x - 3
*/
> /exit
Bye!
```
Examples
```text
> a = 10
> b = 5
> a + b * 2
20
> (a - b) / 5
1
> c = a + b
> c
15
```
Error Handling
The calculator provides meaningful error messages for:

Unknown variables
Invalid expressions
Division by zero
Unknown commands
Invalid assignments
Example:

```text
> x + y
Unknown variable
> 5 / 0
Division by zero
> /unknown
Unknown command
```
Architecture
The project follows SOLID principles and clean code practices:

SmartCalculator: Main class and program loop
InputClassifier: Determines input type
InputProcessor: Handles input formatting
VariableStore: Manages variable storage and retrieval
CommandProcessor: Handles command execution
ExpressionEvaluator: Evaluates mathematical expressions
InfixToPostfix: Converts infix expressions to postfix notation
CalculatorException: Custom exception class
Code Features
Modular design with single responsibility principle
Modern Java features (switch expressions, pattern matching)
Clear separation of concerns
Robust input validation
Extensible architecture
Contributing
Fork the repository
Create a feature branch
Submit a pull request with your changes
Please ensure your code follows the existing style and includes appropriate tests.

Author
Created by [Mahmoud Badr]

```text
This README provides:
- Project overview
- Installation instructions
- Usage examples
- Feature list
- Architecture overview
- Contribution guidelines
- Basic error handling information
```