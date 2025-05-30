public class ScannedToken {
    private String expressionPiece;
    private TokenType type;

    public ScannedToken(String expr, TokenType type) {
        this.expressionPiece = expr;
        this.type = type;
    }

    @Override
    public String toString() {
        return "(Expr:" + this.expressionPiece + ", Token:" + type + ")";
    }

    public TokenType type() {
        return type;
    }

    public String expression() {
        return expressionPiece;
    }
}
