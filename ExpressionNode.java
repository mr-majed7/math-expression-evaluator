/**
 * Represents a node in the expression tree.
 * Each node can be either:
 * - A leaf node containing a value
 * - An operator node with left and right children
 * - A unary operator node (for negative numbers) with a single child
 */
public class ExpressionNode {
    public enum NodeType {
        VALUE,      // Leaf node containing a number
        OPERATOR,   // Binary operator node (+, -, *, /, ^)
        UNARY       // Unary operator node (for negative numbers)
    }

    private final NodeType type;
    private final TokenType operator;  // For operator nodes
    private final double value;        // For value nodes
    private ExpressionNode left;       // Left child
    private ExpressionNode right;      // Right child

    // Constructor for value nodes
    public ExpressionNode(double value) {
        this.type = NodeType.VALUE;
        this.value = value;
        this.operator = null;
    }

    // Constructor for operator nodes
    public ExpressionNode(TokenType operator, ExpressionNode left, ExpressionNode right) {
        this.type = NodeType.OPERATOR;
        this.operator = operator;
        this.left = left;
        this.right = right;
        this.value = 0.0;
    }

    // Constructor for unary operator nodes (e.g., negative numbers)
    public ExpressionNode(TokenType operator, ExpressionNode child) {
        this.type = NodeType.UNARY;
        this.operator = operator;
        this.left = child;
        this.right = null;
        this.value = 0.0;
    }

    /**
     * Evaluates the expression tree starting from this node.
     * @return The numerical result of the expression
     * @throws ExpressionException if the expression is invalid
     */
    public double evaluate() throws ExpressionException {
        switch (type) {
            case VALUE:
                return value;
            case UNARY:
                if (operator == TokenType.SUB) {
                    return -left.evaluate();
                }
                throw new ExpressionException(ExpressionException.ErrorType.INVALID_OPERATOR, 0);
            case OPERATOR:
                double leftVal = left.evaluate();
                double rightVal = right.evaluate();
                
                switch (operator) {
                    case ADD: return leftVal + rightVal;
                    case SUB: return leftVal - rightVal;
                    case MUL: return leftVal * rightVal;
                    case DIV:
                        if (rightVal == 0) {
                            throw new ExpressionException(ExpressionException.ErrorType.DIVISION_BY_ZERO, 0);
                        }
                        return leftVal / rightVal;
                    case POW:
                        if (leftVal == 0 && rightVal < 0) {
                            throw new ExpressionException(ExpressionException.ErrorType.INVALID_POWER, 0);
                        }
                        return Math.pow(leftVal, rightVal);
                    default:
                        throw new ExpressionException(ExpressionException.ErrorType.INVALID_OPERATOR, 0);
                }
            default:
                throw new ExpressionException(ExpressionException.ErrorType.INVALID_EXPRESSION, 0);
        }
    }

    /**
     * Gets the precedence of this operator node.
     * Higher values indicate higher precedence.
     */
    public int getPrecedence() {
        if (type != NodeType.OPERATOR && type != NodeType.UNARY) {
            return Integer.MAX_VALUE;  // Values have highest precedence
        }
        
        switch (operator) {
            case POW: return 3;
            case MUL:
            case DIV: return 2;
            case ADD:
            case SUB: return 1;
            default: return 0;
        }
    }

    // Getters
    public NodeType getType() { return type; }
    public TokenType getOperator() { return operator; }
    public double getValue() { return value; }
    public ExpressionNode getLeft() { return left; }
    public ExpressionNode getRight() { return right; }
} 