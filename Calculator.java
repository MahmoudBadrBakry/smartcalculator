package smartcalculator;

import java.util.*;
import java.util.regex.*;
import java.math.BigInteger;

public class Calculator {
    public static void main(String[] args) {

        Calculator calculator = new Calculator();
        calculator.run();
    }

    public void run(){
        Scanner scanner = new Scanner(System.in);
        String inputString = "";
        String classifier ;
        String out;
        Map<String, BigInteger> variables = new HashMap<>();

        l1:
        while (true) {
            
            inputString = scanner.nextLine();
            inputString = removeSpaces(inputString);
            classifier = classify(inputString);
            out = "";

            try {
                switch (classifier) {
                    case "EXPRESSION": {
                        inputString = format(inputString);
                        out = calculateExpression(inputString, variables);
                        break;
                    }
                    case "COMMAND": {
                        out = command(inputString);
                        if (out.equals("Bye!")) break l1;
                        break;
                    }
                    case "ASSINGMENT": {
                        assign(inputString, variables);
                        break;
                    }
                    case "PRINT":{
                        out = (isVariable(inputString) ? getVariable(inputString,variables) : inputString);
                        break;
                    }
                    
                    case "" : continue l1;
                }
                if (!out.equals(""))
                    System.out.println(out);
                
            } catch (RuntimeException e){
                System.out.println(e.getMessage());
            }
        }
        scanner.close();
        System.out.println("Bye!");
    }

    private static String classify(String input){
        if (input.matches("/\\w+"))
            return "COMMAND";
        else if (input.matches("\\w+(=\\-?\\w*)+"))
            return "ASSINGMENT";
        else if (input.matches("\\-?\\w+"))
            return "PRINT";
        else if (input.matches("(\\(?[\\+\\-]?(\\w+)?(\\-|\\+|\\*|\\/)?[\\+\\-]?(\\w+)?\\)?)+"))
            return "EXPRESSION";
        return "";
    }
    
    private static String command (String commandString) throws RuntimeException{
        String message = "";
        switch(commandString){
            case "/exit":{
                message = "Bye!";
                break;
            }
            case "/help":{
                message = "/** The program calculates the sum, sub, div and mul of numbers with parentheses and " +
                                "\n* it can take multiple operations and provides variables" +
                                "\n* example :- \n> x = 5\n> y = 4\n> y + x - 3\n*/" ;
                break;
            }
            default: {
                throw new RuntimeException("Unknown command");
            }
        }
        return message;
    }
    
    private static void assign(String inpuString, Map<String, BigInteger> variables){
        Pattern pattern = Pattern.compile("(\\w+) ?= ?(\\-?\\w+)");
        Matcher assignmentMatcher = pattern.matcher(inpuString);
        
        // handling errors
        if(!assignmentMatcher.matches())
            throw new RuntimeException("Invalid assignment");
            
        if(assignmentMatcher.group(2).matches("\\w*\\d\\w*") && assignmentMatcher.group(2).matches("\\w*[a-zA-Z]\\w*"))
            throw new RuntimeException("Invalid assignment");
        
        if(assignmentMatcher.group(1).matches("([a-zA-Z]*\\d+[a-zA-Z]*)+"))
            throw new RuntimeException("Invalid identifier");

        if(isVariable(assignmentMatcher.group(2))){
            if(!variables.containsKey(assignmentMatcher.group(2))){
                throw new RuntimeException("Unknown variable");
            }
            // assigning the value
            else {
                variables.put(assignmentMatcher.group(1), variables.get(assignmentMatcher.group(2)));
            }
        }else {
            variables.put(assignmentMatcher.group(1), new BigInteger(assignmentMatcher.group(2)));
        }
        
    }
    
    private static String getVariable(String inpuString, Map<String, BigInteger> variables){
        if(!variables.containsKey(inpuString))
        throw new RuntimeException("Unknown variable");
        return variables.get(inpuString).toString();
    }
    
    
    private static boolean isVariable(String group){
        return group.matches("[a-zA-Z]+");
    }

    private static String removeSpaces(String input){
        return input.replaceAll(" +","");
    } 
    
    private static String format(String inputString){
        inputString = inputString.replaceAll("(\\-\\-)+", "+");
        inputString = inputString.replaceAll("(\\+)+", "+");
        inputString = inputString.replaceAll("(\\+*\\-\\+*)", "-");
        inputString = inputString.replaceAll("(\\-\\+*\\-)", "+");
        return inputString;
    }

    static int getPrecedence(String ch) { 
		switch (ch) { 
		case "+": 
		case "-": 
			return 1; 
	
		case "*": 
		case "/": 
            return 2; 
            
		// case '^': 
		// 	return 3; 
		} 
		return -1; 
	} 

    static int getNextStop(String expression,int lastIndex){
        for (int j = lastIndex; j < expression.length(); j++) 
            if(!Character.isLetterOrDigit(expression.charAt(j))) return j;
        return -1;
    }

	static String infixToPostfix(String expression) { 
        
        String result = new String(""); 
		
		Stack<String> stack = new Stack<>(); 
		
		for (int i = 0; i<expression.length(); ++i) { 
            
            String operand;
            if(Character.isLetterOrDigit(expression.charAt(i))){
                if(getNextStop(expression,i) == -1){
                    operand = expression.substring(i);
                    i += operand.length()-1;
                }
                else {
                    operand = expression.substring(i, getNextStop(expression,i)); 
                    i += operand.length()-1;
                }
            }else {
                operand = Character.toString(expression.charAt(i)); 
            }
			
			if (Character.isLetterOrDigit(operand.charAt(0))) 
				result += operand+" "; 
			
			else if (operand.equals("(")) 
                stack.push(operand); 
                
			else if (operand.equals(")")) { 
				while (!stack.isEmpty() && !stack.peek().equals("(") ) 
					result += (stack.pop()+" "); 
				
				if (!stack.isEmpty() && !stack.peek().equals("(")) {
					throw new RuntimeException("Invalid expression"); 			 
                }
				else{
                    if(stack.isEmpty()) 
                        throw new RuntimeException("Invalid expression");
					stack.pop(); 
                }
			} 
			else { 
				while (!stack.isEmpty() && getPrecedence(operand) <= getPrecedence(stack.peek())){ 
					if(stack.peek().equals("(")) 
                        throw new RuntimeException("Invalid expression");			 
                    result += (stack.pop()+" "); 
                    } 
                    stack.push(operand); 
                }   
            } 
            while (!stack.isEmpty()){ 
                if(stack.peek().equals("(")) 
                    throw new RuntimeException("Invalid expression"); 			  
			    result += (stack.pop()+" "); 
		} 
		return result; 
	}

    private static String calculateExpression(String expression, Map<String, BigInteger> variables){
        // to postfix
        String newExpression = infixToPostfix(expression);
        
        // postfix to stack

        String splitedExpression[] = newExpression.split(" ");
        Stack<String>  stack = new Stack<>();
         
        // perform operations
        BigInteger resultOfExpression = BigInteger.ZERO, firstNum, secondNum;
        String firstOperand, secondOperand;
        for(int i=0 ;i<splitedExpression.length ; ++i){
            if(Character.isLetterOrDigit(splitedExpression[i].charAt(0))){
                stack.push(splitedExpression[i]);
            }else {
                    if(stack.isEmpty()) 
                        throw new RuntimeException("Invalid expression");
                    
                    secondOperand = stack.pop();
                    
                    if(stack.isEmpty())
                        throw new RuntimeException("Invalid expression");
                    
                    firstOperand = stack.pop();

                    firstNum = (isVariable(firstOperand)) ? variables.get(firstOperand) : new BigInteger(firstOperand);
                    secondNum = (isVariable(secondOperand)) ? variables.get(secondOperand) : new BigInteger(secondOperand);

                    switch(splitedExpression[i].charAt(0)) {
                        case '+': {
                            resultOfExpression = firstNum.add(secondNum);
                            break;
                        }
		                case '-':{
                            resultOfExpression = firstNum.subtract(secondNum);
                            break;
                        } 
		                case '*':{
                            resultOfExpression = firstNum.multiply(secondNum);
                            break;
                        } 
                        case '/':{
                            resultOfExpression = firstNum.divide(secondNum);
                            break;
                        } 
                }
                stack.push(resultOfExpression.toString());
            }
        }

        return resultOfExpression.toString();
    }
}