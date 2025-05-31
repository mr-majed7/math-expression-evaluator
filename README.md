# Mathematical Expression Evaluator

A robust Java-based mathematical expression evaluator that parses and evaluates mathematical expressions with proper operator precedence and error handling. This project is an enhanced version of the implementation described in [Bruno Oliveira's article](https://dev.to/brunooliveira/writing-a-mathematical-expression-evaluator-in-java-1ka6).

## Features

- Evaluate mathematical expressions with support for:
  - Basic arithmetic operations (+, -, *, /)
  - Exponentiation (^)
  - Parentheses for grouping
  - Negative numbers
  - Decimal numbers
- Proper operator precedence (PEMDAS)
- Robust error handling
- Expression tree-based evaluation
- Interactive command-line interface

## Improvements Over Original Implementation

The original implementation was a great starting point, but this version includes several significant improvements:

1. **Expression Tree Structure**
   - Implemented a proper expression tree for evaluation
   - Clean separation of parsing and evaluation
   - Better handling of operator precedence
   - Support for unary operators (negative numbers)

2. **Error Handling**
   - Custom `ExpressionException` class with specific error types
   - Detailed error messages with position information
   - Graceful handling of invalid expressions
   - Specific handling for common errors:
     - Division by zero
     - Unmatched parentheses
     - Invalid number formats
     - Invalid operator usage

3. **Parser Improvements**
   - Implemented precedence climbing method for building the expression tree
   - More robust token processing
   - Better handling of parentheses and operator precedence
   - Proper handling of negative numbers

4. **User Interface**
   - Interactive command-line interface
   - Clear error messages with hints
   - Support for exiting the program
   - Better output formatting

## How to Use

1. Compile the project (either method works):

```bash
# Method 1: Compile all Java files at once
javac *.java

```

2. Run the program:

```bash
java Main
```

3. Enter mathematical expressions when prompted. Examples:

```
Enter Your Mathematical Expression (or 'exit' to quit):
(1+2)*3
Result: 9.0

Enter Your Mathematical Expression (or 'exit' to quit):
2^3
Result: 8.0

Enter Your Mathematical Expression (or 'exit' to quit):
-5+3
Result: -2.0
```

4. Type 'exit' to quit the program.

## Project Structure

- `Main.java`: Entry point and user interface
- `Scanner.java`: Tokenizes input expressions
- `Parser.java`: Builds expression tree from tokens
- `ExpressionNode.java`: Represents nodes in the expression tree
- `ExpressionException.java`: Custom exception handling
- `TokenType.java`: Enum for different token types
- `ScannedToken.java`: Represents a token with its type and value

## Implementation Details

The evaluation process follows these steps:

1. **Scanner**: Tokenizes the input string into a list of tokens
2. **Parser**: Builds an expression tree with proper operator precedence
3. **ExpressionNode**: Evaluates the expression tree recursively

The expression tree approach provides several benefits:

- Clear separation of parsing and evaluation
- Natural handling of operator precedence
- Easy to extend with new operators
- Better error handling capabilities

## Error Handling

The program handles various error cases gracefully:

- Invalid expressions
- Unmatched parentheses
- Division by zero
- Invalid number formats
- Invalid operator usage
- Empty expressions

Each error includes:

- Specific error type
- Position in the expression
- Helpful error message
- Additional hints where applicable

## Future Improvements

Potential areas for future enhancement:

- Support for more mathematical functions (sin, cos, etc.)
- Variable support
- Better handling of floating-point precision
- Unit tests
- GUI interface
- Support for more complex expressions

## Credits

This project is based on the implementation described in [Bruno Oliveira's article](https://dev.to/brunooliveira/writing-a-mathematical-expression-evaluator-in-java-1ka6), with significant improvements in error handling, expression tree implementation, and overall robustness.
