public enum TokenType {
    ADD,
    SUB,
    MUL,
    DIV,
    POW,
    LPAR,
    RPAR,
    VALUE;

    /**
     * Converts a string operator or parenthesis to its corresponding TokenType.
     * For any other string input, returns VALUE type.
     */
    public static TokenType fromString(String s) {
        switch (s) {
            case "+":
                return TokenType.ADD;
            case "-":
                return TokenType.SUB;
            case "*":
                return TokenType.MUL;
            case "/":
                return TokenType.DIV;
            case "^":
                return TokenType.POW;
            case "(":
                return TokenType.LPAR;
            case ")":
                return TokenType.RPAR;
            default:
                return TokenType.VALUE;
        }
    }

    /**
     * Converts a TokenType back to its string representation.
     * For operators and parentheses, returns their symbol.
     * For VALUE type, returns the enum name.
     */
    @Override
    public String toString() {
        switch (this.ordinal()) {
            case 0:
                return "+";
            case 1:
                return "-";
            case 2:
                return "*";
            case 3:
                return "/";
            case 4:
                return "^";
            case 5:
                return "(";
            case 6:
                return ")";
            case 7:
                return this.name();
            default:
                return "null";
        }
    }
}
