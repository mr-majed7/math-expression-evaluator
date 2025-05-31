import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Parser class for mathematical expressions.
 * This class takes a list of scanned tokens and processes them to handle special cases
 * like negative numbers and ensures proper token ordering for evaluation.
 */
public class Parser {
    private final List<ScannedToken> expression;

    /**
     * Creates a new Parser instance with the given tokenized expression.
     * @param expression List of ScannedToken objects representing the tokenized mathematical expression
     */
    public Parser(List<ScannedToken> expression) {
        this.expression = expression;
    }

    /**
     * Parses the tokenized expression to handle special cases and prepare for evaluation.
     * Main responsibilities:
     * 1. Identifies and processes negative numbers (e.g., "-5" or "(-3)")
     * 2. Creates new tokens for negative values
     * 3. Maintains proper token ordering for evaluation
     * 
     * @return List of ScannedToken objects with processed negative numbers and proper ordering
     */
    public List<ScannedToken> parse() {
        // Track token types for context-aware processing
        TokenType prev = null;
        TokenType curr = null;
        TokenType next = null;

        // Lists to store processed tokens and track modifications
        List<ScannedToken> properlyParsedExpr = new ArrayList<>();
        List<TokenType> types = expression.stream().map(ScannedToken::type).collect(Collectors.toList());
        List<Integer> indexes = new ArrayList<>();  // Tracks positions of negative numbers
        List<ScannedToken> negativeValues = new ArrayList<>();  // Stores processed negative values

        // First pass: Identify and process negative numbers
        for (int i = 0; i < types.size() - 1; i++) {
            prev = i == 0 ? null : types.get(i - 1);
            curr = types.get(i);
            next = i < types.size() - 1 ? types.get(i + 1) : null;

            // Case 1: Negative number at start of expression (e.g., "-5")
            if (prev == null && curr == TokenType.SUB && next == TokenType.VALUE) {
                ScannedToken negativeValue = new ScannedToken("" + (-1 * Double.parseDouble(expression.get(i + 1).
                        expression())), TokenType.VALUE);
                System.out.println("new token at index " + i);
                indexes.add(i);
                negativeValues.add(negativeValue);
            } 
            // Case 2: Negative number after opening parenthesis (e.g., "(-3)")
            else if (prev == TokenType.LPAR && curr == TokenType.SUB && next == TokenType.VALUE) {
                ScannedToken negativeValue =
                        new ScannedToken("" + (-1 * Double.parseDouble(expression.get(i + 1).expression())),
                                TokenType.VALUE);
                System.out.println("new token at index " + i);
                indexes.add(i);
                negativeValues.add(negativeValue);
            }
        }

        // Second pass: Build final expression with processed negative numbers
        int maxIter = expression.size();
        int i = 0;
        int j = 0;
        while (i < maxIter) {
            if (indexes.contains(i) && j < negativeValues.size()) {
                // Insert processed negative value
                properlyParsedExpr.add(negativeValues.get(j));
                i++;
                j++;
            } else {
                // Keep original token
                properlyParsedExpr.add(expression.get(i));
            }
            i++;
        }
        System.out.println(properlyParsedExpr);
        return properlyParsedExpr;
    }
}
