/**
 * Call Expression
 * 
 * @author 	Federico Menozzi
 * @version Easy Compiler v1
 */

package easy.AbstractSyntaxTrees;

import easy.SyntacticAnalyzer.Line;

public class CallExpr extends Expression {
	
	/**
	 * {@link Reference} to the underlying function
	 */
	public final Reference functionRef;
	
	/**
	 * {@link ExprList} of the function arguments
	 */
	public final ExprList argList;
	
	/**
	 * Construct a CallExpr of underlying {@code Reference}
	 * functionRef and {@code ExprList} argList
	 * 
	 * @param functionRef	the reference to the underlying function
	 * @param argList		the list of arguments
	 * @param line			the line number of the call in source
	 */
	public CallExpr(Reference functionRef, ExprList argList, Line line) {
		super(functionRef.spelling, line);
		
		this.functionRef = functionRef;
		this.argList	 = argList;
	}
	
	/**
	 * Visit a CallExpr node
	 * 
	 * @param v		an object that implements the Visitor interface
	 * @param arg	the helper object received for the visit
	 * @return		the helper object synthesized during the visit
	 */
	@Override
	public <ArgType, ReturnType> ReturnType visit(Visitor<ArgType, ReturnType> v, ArgType arg) {
		return v.visitCallExpr(this, arg);
	}
}
