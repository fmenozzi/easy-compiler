/**
 * Literal Expression
 * 
 * @author 	Federico Menozzi
 * @version Easy Compiler v1
 */

package easy.AbstractSyntaxTrees;

import easy.SyntacticAnalyzer.Line;

public class LiteralExpr extends Expression {

	/**
	 * Underlying literal
	 */
	public final Literal literal;
	
	/**
	 * Construct a LiteralExpr using the underlying {@link Literal} literal
	 * 
	 * @param literal	the underlying {@code Literal}
	 * @param line		the line number of the literal in source
	 */
	public LiteralExpr(Literal literal, Line line) {
		super(literal.spelling, line);
		this.literal = literal;
	}
	
	/**
	 * Visit a LiteralExpr node
	 * 
	 * @param v		an object that implements the Visitor interface
	 * @param arg	the helper object received for the visit
	 * @return		the helper object synthesized during the visit
	 */
	@Override
	public <ArgType, ReturnType> ReturnType visit(Visitor<ArgType, ReturnType> v, ArgType arg) {
		return v.visitLiteralExpr(this, arg);
	}
}
