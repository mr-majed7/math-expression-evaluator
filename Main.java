import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Main class for the Mathematical Expression Evaluator.
 * This program provides an interactive command-line interface for evaluating
 * mathematical expressions. It supports:
 * - Basic arithmetic operations (+, -, *, /, ^)
 * - Parentheses for grouping
 * - Negative numbers
 * - Decimal numbers
 * 
 * The evaluation process follows these steps:
 * 1. Scanner: Tokenizes the input string into a list of tokens
 * 2. Parser: Builds an expression tree with proper operator precedence
 * 3. ExpressionNode: Evaluates the expression tree
 */
public class Main {
    /**
     * Main entry point of the program.
     * Provides an interactive loop that:
     * 1. Prompts for a mathematical expression
     * 2. Processes and evaluates the expression
     * 3. Displays the result or error message
     * 
     * The program continues running until terminated by the user (Ctrl+C)
     * 
     * @param args Command line arguments (not used)
     * @throws IOException If there's an error reading from standard input
     */
    public static void main(String[] args) throws IOException {
        System.out.println("Enter Your Mathematical Expression (or 'exit' to quit):");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        
        while (true) {
            try {
                // Read input from user
                String input = reader.readLine().trim();
                
                // Check for exit command
                if (input.equalsIgnoreCase("exit")) {
                    System.out.println("Goodbye!");
                    break;
                }
                
                // Skip empty input
                if (input.isEmpty()) {
                    System.out.println("Please enter an expression.");
                    continue;
                }
                
                // Create scanner and tokenize the expression
                Scanner sc = new Scanner(input);
                List<ScannedToken> tokens = sc.scan();
                
                // Print the tokenized expression for debugging
                System.out.println("Tokens: " + tokens);
                
                // Parse the tokens into an expression tree
                Parser parser = new Parser(tokens);
                ExpressionNode expressionTree = parser.parse();
                
                // Evaluate the expression tree and print the result
                double result = expressionTree.evaluate();
                System.out.println("Result: " + result);
                
            } catch (ExpressionException e) {
                // Handle expression-specific errors
                System.err.println("Error: " + e.getMessage());
                if (e.getErrorType() == ExpressionException.ErrorType.UNMATCHED_PARENTHESES) {
                    System.err.println("Hint: Check that all parentheses are properly matched.");
                } else if (e.getErrorType() == ExpressionException.ErrorType.INVALID_NUMBER) {
                    System.err.println("Hint: Make sure all numbers are valid.");
                } else if (e.getErrorType() == ExpressionException.ErrorType.DIVISION_BY_ZERO) {
                    System.err.println("Hint: Division by zero is not allowed.");
                }
            } catch (NumberFormatException e) {
                System.err.println("Error: Invalid number format in expression.");
            } catch (Exception e) {
                System.err.println("Unexpected error: " + e.getMessage());
            }
            
            System.out.println("\nEnter another expression (or 'exit' to quit):");
        }
    }
}
