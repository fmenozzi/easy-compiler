/**
 * If Expression
 * 
 * @author  Federico Menozzi
 * @version Easy Compiler v1
 */

package easy.AbstractSyntaxTrees;

import easy.SyntacticAnalyzer.Line;

public class IfExpr extends Expression {
	
	/**
	 * {@link Expression} representing condition to 
	 * check for evaluation of thenExpr
	 */
	public final Expression condition;
	
	/**
	 * {@code Expression} representing value of overall expression
	 * if "condition" evaluates to true
	 */
	public final Expression thenExpr;
	
	/**
	 * {@code Expression} representing value of overall expression
	 * if "condition" evaluates to false
	 */
	public final Expression elseExpr;
	
	public IfExpr(Expression thenExpr, Expression condition, Expression elseExpr, Line line) {
		super(thenExpr.spelling+" if "+condition.spelling+" else "+elseExpr.spelling, line);
		
		this.thenExpr  = thenExpr;
		this.condition = condition;
		this.elseExpr  = elseExpr;
	}
	
	/**
	 * Visit an IfExpr node
	 * 
	 * @param v		an object that implements the Visitor interface
	 * @param arg	the helper object received for the visit
	 * @return		the helper object synthesized during the visit
	 */
	@Override
	public <ArgType, ReturnType> ReturnType visit(Visitor<ArgType, ReturnType> v, ArgType arg) {
		return v.visitIfExpr(this, arg);
	}
}
