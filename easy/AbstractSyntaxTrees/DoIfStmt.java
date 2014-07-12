/**
 * Do-If Statement
 * 
 * @author  Federico Menozzi
 * @version Easy Compiler v1
 */

package easy.AbstractSyntaxTrees;

import easy.SyntacticAnalyzer.Line;

public class DoIfStmt extends Statement {
	
	/**
	 * {@link Expression} representing condition to 
	 * check for evaluation of thenExpr
	 */
	public final Expression condition;
	
	/**
	 * {@link Statement} to execute if "condition"
	 * evaluates to true
	 */
	public final Statement thenStmt;
	
	/**
	 * {@code Statement} to execute if "condition"
	 * evaluates to false
	 */
	public final Statement elseStmt;
	
	public DoIfStmt(Statement thenStmt, Expression condition, Statement elseStmt, Line line) {
		super(line);
		
		this.thenStmt  = thenStmt;
		this.condition = condition;
		this.elseStmt  = elseStmt;
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
		return v.visitDoIfStmt(this, arg);
	}
}
