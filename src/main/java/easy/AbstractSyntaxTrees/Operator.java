/**
 * Operator
 * 
 * @author 	Federico Menozzi
 * @version Easy Compiler v1
 */

package easy.AbstractSyntaxTrees;

import easy.SyntacticAnalyzer.Token;
import easy.SyntacticAnalyzer.Line;

public class Operator extends Terminal {
	
	/**
	 * The underlying {@link Token}
	 */
	public final Token token;
	
	/**
	 * Construct an Operator from a Token
	 * 
	 * @param token	the underlying token
	 * @param line	the line number of the token in source
	 */
	public Operator(Token token, Line line) {
		super(token.spelling, line);
		this.token = token;
	}

	/**
	 * Visit an Operator node
	 * 
	 * @param v		an object that implements the Visitor interface
	 * @param arg	the helper object received for the visit
	 * @return		the helper object synthesized during the visit
	 */
	@Override
	public <ArgType, ReturnType> ReturnType visit(Visitor<ArgType, ReturnType> v, ArgType arg) {
		return v.visitOperator(this, arg);
	}
}
