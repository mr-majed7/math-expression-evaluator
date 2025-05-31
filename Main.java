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
 * 2. Parser: Processes the tokens to handle special cases (like negative numbers)
 * 3. Scanner: Evaluates the processed tokens following operator precedence
 */
public class Main {
    /**
     * Main entry point of the program.
     * Provides an interactive loop that:
     * 1. Prompts for a mathematical expression
     * 2. Processes and evaluates the expression
     * 3. Displays the result
     * 
     * The program continues running until terminated by the user (Ctrl+C)
     * 
     * @param args Command line arguments (not used)
     * @throws IOException If there's an error reading from standard input
     */
    public static void main(String[] args) throws IOException {
        System.out.println("Enter Your Mathematical Expression:");
        while (true) {
            // Read input from user
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String name = reader.readLine();
            
            // Create scanner and tokenize the expression
            Scanner sc = new Scanner(name);
            List<ScannedToken> scanExp = sc.scan();
            
            // Parse the tokens to handle special cases
            Parser parser = new Parser(scanExp);
            List<ScannedToken> parsed = parser.parse();
            
            // Print the tokenized expression for debugging
            scanExp.forEach(e -> System.out.println(e));
            
            // Evaluate and print the result
            System.out.println(sc.evaluate(parsed));
        }
    }
}
