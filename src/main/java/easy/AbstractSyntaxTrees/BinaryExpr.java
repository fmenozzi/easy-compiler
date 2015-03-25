/**
 * Binary Expression
 * 
 * @author 	Federico Menozzi
 * @version Easy Compiler v1
 */

package easy.AbstractSyntaxTrees;

import easy.SyntacticAnalyzer.Line;

public class BinaryExpr extends Expression {

	/**
	 * Left expression in the binary expression
	 */
	public final Expression leftExpr;
	
	/**
	 * Binary operator (Any arithmetic, logical, or relational
	 * operator)
	 */
	public final Operator operator;
	
	/**
	 * Right expression in the binary expression
	 */
	public final Expression rightExpr;
	
	/**
	 * Construct a BinaryExpr of the form "Expr binop Expr", 
	 * where "binop" is any valid binary operator and "Expr"
	 * is any valid expression
	 * 
	 * @param leftExpr	the left expression in the binary expression	
	 * @param operator	the binary operator
	 * @param rightExpr	the right expression in the binary expression
	 * @param line		the line number of the expression in source
	 */
	public BinaryExpr(Expression leftExpr, Operator operator, Expression rightExpr, Line line) {
		super(leftExpr.spelling + operator.spelling + rightExpr.spelling, line);
		
		this.leftExpr  = leftExpr;
		this.operator  = operator;
		this.rightExpr = rightExpr;
	}

	/**
	 * Visit a BinaryExpr node
	 * 
	 * @param v		an object that implements the Visitor interface
	 * @param arg	the helper object received for the visit
	 * @return		the helper object synthesized during the visit
	 */
	@Override
	public <ArgType, ReturnType> ReturnType visit(Visitor<ArgType, ReturnType> v, ArgType arg) {
		return v.visitBinaryExpr(this, arg);
	}
}
