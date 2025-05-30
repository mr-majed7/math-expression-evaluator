import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Scanner class for parsing and evaluating mathematical expressions.
 * Supports basic arithmetic operations (+, -, *, /, ^) and parentheses.
 */
public class Scanner {
    private final String expression;

    /**
     * Creates a new Scanner instance with the given mathematical expression.
     * @param expr The expression string to be parsed and evaluated
     */
    public Scanner(String expr) {
        this.expression = expr;
    }

    /**
     * Scans the input expression and converts it into a list of tokens.
     * Each token represents either a number (VALUE) or an operator/parenthesis.
     * @return List of ScannedToken objects representing the tokenized expression
     */
    public List<ScannedToken> scan() {
        StringBuilder value = new StringBuilder();
        List<ScannedToken> scannedExpr = new ArrayList<>();

        for (char c : expression.toCharArray()) {
            TokenType type = TokenType.fromString(new String(new char[]{c}));
            if (!type.equals(TokenType.VALUE)) {
                if (value.length() > 0) {
                    ScannedToken st = new ScannedToken(value.toString(), TokenType.VALUE);
                    scannedExpr.add(st);
                    value = new StringBuilder();
                } else {
                    value.append(new String(new char[]{c}));
                }
            }
            if (value.length() > 0) {
                ScannedToken st = new ScannedToken(value.toString(), TokenType.VALUE);
                scannedExpr.add(st);
                value = new StringBuilder();
            }
        }
        return scannedExpr;
    }

    /**
     * Recursively evaluates a tokenized expression, handling parentheses first.
     * The method processes expressions from innermost parentheses outward.
     * @param tokenizedExpr List of tokens representing the expression
     * @return The numerical result of the expression
     */
    public double evaluate(List<ScannedToken> tokenizedExpr) {
        if (tokenizedExpr.size() == 1) {
            return Double.parseDouble(tokenizedExpr.get(0).expression());
        }
        List<ScannedToken> simpleExpr = new ArrayList<>();
        int idx = tokenizedExpr.stream().
                map(ScannedToken::type).
                collect(Collectors.toList()).
                lastIndexOf(TokenType.LPAR);
        int matchingPar = -1;
        if (idx > 0) {
            for (int i = idx + 1; i < tokenizedExpr.size(); i++) {
                ScannedToken curr = tokenizedExpr.get(i);
                if (curr.type() == TokenType.RPAR) {
                    matchingPar = i;
                    break;
                } else {
                    simpleExpr.add(tokenizedExpr.get(i));
                }
            }
        } else {
            simpleExpr.addAll(tokenizedExpr);
            return evaluateSimpleExpression(tokenizedExpr);
        }
        double value = evaluateSimpleExpression(simpleExpr);
        List<ScannedToken> partiallyEvalExper = new ArrayList<>();

        for (int i = 0; i < idx; i++) {
            partiallyEvalExper.add(tokenizedExpr.get(i));
        }
        partiallyEvalExper.add(new ScannedToken(Double.toString(value), TokenType.VALUE));
        System.out.println(partiallyEvalExper);
        return evaluate(partiallyEvalExper);
    }

    /**
     * Evaluates a simple expression (without parentheses) following operator precedence:
     * 1. Exponentiation (^)
     * 2. Multiplication (*) and Division (/)
     * 3. Addition (+) and Subtraction (-)
     * 
     * @param expression List of tokens representing a simple expression
     * @return The numerical result of the expression
     * @return -1.0 if the expression is invalid
     */
    public double evaluateSimpleExpression(List<ScannedToken> expression) {
        // Base case: single number
        if (expression.size() == 1) {
            return Double.parseDouble(expression.get(0).expression());
        } else {
            List<ScannedToken> newExpression = new ArrayList<>();
            
            // Step 1: Handle exponentiation (highest precedence)
            int idx = expression.stream()
                    .map(ScannedToken::expression)
                    .collect(Collectors.toList())
                    .indexOf(TokenType.POW);
            if (idx != -1) {
                // Calculate power and create new expression with result
                double base = Double.parseDouble(expression.get(idx - 1).expression());
                double exp = Double.parseDouble(expression.get(idx + 1).expression());
                double ans = Math.pow(base, exp);
                // Add remaining tokens after the power operation
                for (int i = idx + 2; i < expression.size(); i++) {
                    newExpression.add(expression.get(i));
                }
                return evaluateSimpleExpression(newExpression);
            } else {
                // Step 2: Handle multiplication and division
                int mulIdx = expression.stream()
                        .map(ScannedToken::type)
                        .collect(Collectors.toList())
                        .indexOf(TokenType.MUL);
                int divIdx = expression.stream()
                        .map(ScannedToken::type)
                        .collect(Collectors.toList())
                        .indexOf(TokenType.DIV);
                // Find the leftmost * or / operator
                int computationIdx = (mulIdx >= 0 && divIdx >= 0) ? Math.min(mulIdx, divIdx) : Math.max(mulIdx, divIdx);
                
                if (computationIdx != -1) {
                    // Calculate multiplication or division
                    double left = Double.parseDouble(expression.get(computationIdx - 1).expression());
                    double right = Double.parseDouble(expression.get(computationIdx + 1).expression());
                    double ans = computationIdx == mulIdx ? left * right : left / right * 1.0;
                    
                    // Build new expression with the result
                    for (int i = 0; i < computationIdx - 1; i++) {
                        newExpression.add(expression.get(i));
                    }
                    newExpression.add(new ScannedToken(ans + "", TokenType.VALUE));
                    for (int i = computationIdx + 2; i < expression.size(); i++) {
                        newExpression.add(expression.get(i));
                    }
                    return evaluateSimpleExpression(newExpression);
                } else {
                    // Step 3: Handle addition and subtraction (lowest precedence)
                    int addIdx = expression.stream()
                            .map(e -> e.type())
                            .collect(Collectors.toList())
                            .indexOf(TokenType.ADD);
                    int subIdx = expression.stream()
                            .map(e -> e.type())
                            .collect(Collectors.toList())
                            .indexOf(TokenType.SUB);
                    // Find the leftmost + or - operator
                    int computationIdx2 = (addIdx >= 0 && subIdx >= 0) ?
                            Math.min(addIdx, subIdx) :
                            Math.max(addIdx, subIdx);
                            
                    if (computationIdx2 != -1) {
                        // Calculate addition or subtraction
                        double left = Double.parseDouble(expression.get(computationIdx2 - 1).expression());
                        double right = Double.parseDouble(expression.get(computationIdx2 + 1).expression());
                        double ans = computationIdx2 == addIdx ? left + right : (left - right) * 1.0;
                        
                        // Build new expression with the result
                        for (int i = 0; i < computationIdx2 - 1; i++) {
                            newExpression.add(expression.get(i));
                        }
                        newExpression.add(new ScannedToken(ans + "", TokenType.VALUE));
                        for (int i = computationIdx2 + 2; i < expression.size(); i++) {
                            newExpression.add(expression.get(i));
                        }
                        return evaluateSimpleExpression(newExpression);
                    }
                }
            }
        }
        return -1.0; // Return -1.0 for invalid expressions
    }
}


