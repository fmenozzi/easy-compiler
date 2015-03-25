/**
 * Return Statement
 * 
 * @author  Federico Menozzi
 * @version Easy Compiler v1
 */

package easy.AbstractSyntaxTrees;

import easy.SyntacticAnalyzer.Line;

public class ReturnStmt extends Statement {
	
	/**
	 * {@link Expression} to return, may be null
	 */
	public final Expression returnExpr;
	
	/**
	 * Construct a ReturnStmt that returns the {@code Expression} 
	 * returnExpr, which may be null to signal the return from a
	 * void function or method.
	 * 
	 * @param returnExpr	the Expression to return (may be null)
	 * @param line			the line number of the "return" keyword in source
	 */
	public ReturnStmt(Expression returnExpr, Line line) {
		super(line);
		this.returnExpr = returnExpr;
	}
	
	/**
	 * Visit a ReturnStmt node
	 * 
	 * @param v		an object that implements the Visitor interface
	 * @param arg	the helper object received for the visit
	 * @return		the helper object synthesized during the visit
	 */
	@Override
	public <ArgType, ReturnType> ReturnType visit(Visitor<ArgType, ReturnType> v, ArgType arg) {
		return v.visitReturnStmt(this, arg);
	}
}
