/**
 * Used to represent tokens for use in parsing
 * 
 * @author	Federico Menozzi
 * @version Easy Compiler v1
 */

package easy.SyntacticAnalyzer;

public class Token {
	
	/**
	 * Instance of enumeration of types of tokens
	 */
	public final TokenKind kind;
	
	/**
	 * String representation of the token
	 */
	public final String spelling;
	
	/**
	 * Line number of the token in source
	 */
	public final Line line;
	
	/**
	 * Construct a Token of the specified type with the specified
	 * spelling at the given line number in source.
	 * 
	 * @param kind		the type of the token, selected from an enumeration
	 * @param spelling	the String representation of the Token in source
	 * @param line		the line number of the token is source
	 */
	public Token(TokenKind kind, String spelling, Line line) {
		this.kind = kind;
		this.spelling = spelling;
		this.line = line;
	}
	
	/**
	 * Print the spelling of the token
	 * 
	 * @return	the spelling of the token
	 */
	@Override
	public String toString() {
		return spelling;
	}
	
	/**
	 * A formatted String meant for debugging that displays type, 
	 * spelling, and line number of the token.
	 * 
	 * @return	the formatted String
	 */
	public String debugStr() {
		String f1 = String.format("%-20s", kind);
		String f2 = String.format("%-20s", spelling);
		return f1 + " " + f2 + " at line " + line;
	}
	
	/**
	 * Check whether operator is (or could be) a unary operator
	 * 
	 * @return	true if operator is (or could be) a unary operator, false otherwise
	 */
	public boolean isUnary() {
		return spelling.equals("!") || spelling.equals("not") || spelling.equals("-");
	}
}
