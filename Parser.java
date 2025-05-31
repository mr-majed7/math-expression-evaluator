import java.util.List;

/**
 * Parser class for mathematical expressions.
 * This class takes a list of scanned tokens and builds an expression tree
 * that represents the mathematical expression with proper operator precedence.
 * The tree can then be evaluated by traversing it in-order.
 */
public class Parser {
    private final List<ScannedToken> tokens;
    private int currentTokenIndex;

    /**
     * Creates a new Parser instance with the given tokenized expression.
     * @param tokens List of ScannedToken objects representing the tokenized mathematical expression
     */
    public Parser(List<ScannedToken> tokens) {
        this.tokens = tokens;
        this.currentTokenIndex = 0;
    }

    /**
     * Parses the tokenized expression into an expression tree.
     * @return The root node of the expression tree
     * @throws ExpressionException if the expression is invalid
     */
    public ExpressionNode parse() throws ExpressionException {
        if (tokens.isEmpty()) {
            throw new ExpressionException(ExpressionException.ErrorType.EMPTY_EXPRESSION, 0);
        }
        return parseExpression();
    }

    /**
     * Parses an expression, handling operator precedence.
     * Uses the shunting yard algorithm to build the expression tree.
     */
    private ExpressionNode parseExpression() throws ExpressionException {
        return parseBinaryExpression(0);
    }

    /**
     * Parses a binary expression with a given minimum precedence.
     * This implements the precedence climbing method for building the expression tree.
     */
    private ExpressionNode parseBinaryExpression(int minPrecedence) throws ExpressionException {
        ExpressionNode left = parseUnaryExpression();

        while (currentTokenIndex < tokens.size()) {
            ScannedToken token = tokens.get(currentTokenIndex);
            if (token.type() != TokenType.ADD && token.type() != TokenType.SUB &&
                token.type() != TokenType.MUL && token.type() != TokenType.DIV &&
                token.type() != TokenType.POW) {
                break;
            }

            int precedence = getOperatorPrecedence(token.type());
            if (precedence < minPrecedence) {
                break;
            }

            currentTokenIndex++;
            ExpressionNode right = parseBinaryExpression(precedence + 1);
            left = new ExpressionNode(token.type(), left, right);
        }

        return left;
    }

    /**
     * Parses a unary expression (e.g., negative numbers).
     */
    private ExpressionNode parseUnaryExpression() throws ExpressionException {
        if (currentTokenIndex >= tokens.size()) {
            throw new ExpressionException(ExpressionException.ErrorType.INVALID_EXPRESSION, currentTokenIndex);
        }

        ScannedToken token = tokens.get(currentTokenIndex);
        
        if (token.type() == TokenType.SUB) {
            currentTokenIndex++;
            ExpressionNode operand = parseUnaryExpression();
            return new ExpressionNode(TokenType.SUB, operand);
        }
        
        if (token.type() == TokenType.LPAR) {
            currentTokenIndex++;
            ExpressionNode expr = parseExpression();
            
            if (currentTokenIndex >= tokens.size() || tokens.get(currentTokenIndex).type() != TokenType.RPAR) {
                throw new ExpressionException(ExpressionException.ErrorType.UNMATCHED_PARENTHESES, currentTokenIndex);
            }
            currentTokenIndex++;
            return expr;
        }
        
        if (token.type() == TokenType.VALUE) {
            try {
                double value = Double.parseDouble(token.expression());
                currentTokenIndex++;
                return new ExpressionNode(value);
            } catch (NumberFormatException e) {
                throw new ExpressionException(ExpressionException.ErrorType.INVALID_NUMBER, currentTokenIndex, token.expression());
            }
        }

        throw new ExpressionException(ExpressionException.ErrorType.INVALID_EXPRESSION, currentTokenIndex);
    }

    /**
     * Gets the precedence of an operator.
     * Higher values indicate higher precedence.
     */
    private int getOperatorPrecedence(TokenType operator) {
        switch (operator) {
            case POW: return 3;
            case MUL:
            case DIV: return 2;
            case ADD:
            case SUB: return 1;
            default: return 0;
        }
    }
}
