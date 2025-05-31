/**
 * Custom exception class for mathematical expression evaluation errors.
 * This class provides detailed error messages for different types of expression errors.
 */
public class ExpressionException extends RuntimeException {
    public enum ErrorType {
        INVALID_EXPRESSION("Invalid expression format"),
        UNMATCHED_PARENTHESES("Unmatched parentheses"),
        INVALID_OPERATOR("Invalid operator usage"),
        INVALID_NUMBER("Invalid number format"),
        DIVISION_BY_ZERO("Division by zero"),
        INVALID_POWER("Invalid power operation"),
        EMPTY_EXPRESSION("Empty expression");

        private final String message;

        ErrorType(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    private final ErrorType errorType;
    private final int position;

    public ExpressionException(ErrorType errorType, int position) {
        super(String.format("%s at position %d", errorType.getMessage(), position));
        this.errorType = errorType;
        this.position = position;
    }

    public ExpressionException(ErrorType errorType, int position, String details) {
        super(String.format("%s at position %d: %s", errorType.getMessage(), position, details));
        this.errorType = errorType;
        this.position = position;
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    public int getPosition() {
        return position;
    }
} 