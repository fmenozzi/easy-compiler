/**
 * Unary Expression
 * 
 * @author 	Federico Menozzi
 * @version Easy Compiler v1
 */

package easy.AbstractSyntaxTrees;

import easy.SyntacticAnalyzer.Line;

public class UnaryExpr extends Expression {
	
	/**
	 * Unary operator (either - or !)
	 */
	public final Operator operator;
	
	/**
	 * Expression to which operator is applied
	 */
	public final Expression expr;
	
	/**
	 * Construct a UnaryExpr of form "unop Expr" where "unop"
	 * is any valid unary operator and "Expr" is any valid
	 * expression
	 * 
	 * @param operator	the unary operator  
	 * @param expr		the expression
	 * @param line		the line number of the expression in source
	 */
	public UnaryExpr(Operator operator, Expression expr, Line line) {
		super(operator.spelling + expr.spelling, line);
		this.operator = operator;
		this.expr = expr;
	}

	/**
	 * Visit a UnaryExpr node
	 * 
	 * @param v		an object that implements the Visitor interface
	 * @param arg	the helper object received for the visit
	 * @return		the helper object synthesized during the visit
	 */
	@Override
	public <ArgType, ReturnType> ReturnType visit(Visitor<ArgType, ReturnType> v, ArgType arg) {
		return v.visitUnaryExpr(this, arg);
	}
}
