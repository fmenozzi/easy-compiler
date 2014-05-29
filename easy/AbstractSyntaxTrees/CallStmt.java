/**
 * Call Statement
 * 
 * @author  Federico Menozzi
 * @version Easy Compiler v1
 */

package easy.AbstractSyntaxTrees;

import easy.SyntacticAnalyzer.Line;

public class CallStmt extends Statement {

	/**
	 * {@link Reference} to the underlying function
	 */
	public final Reference functionRef;
	
	/**
	 * {@link ExprList} of the function arguments
	 */
	public final ExprList argList;
	
	/**
	 * Construct a CallStmt of underlying {@code Reference}
	 * functionRef and {@code ExprList} argList
	 * 
	 * @param functionRef	the reference to the underlying function
	 * @param argList		the list of arguments
	 * @param line			the line number of the call in source
	 */
	public CallStmt(Reference functionRef, ExprList argList, Line line) {
		super(line);
		
		this.functionRef = functionRef;
		this.argList     = argList;
	}
	
	/**
	 * Visit a CallStmt node
	 * 
	 * @param v		an object that implements the Visitor interface
	 * @param arg	the helper object received for the visit
	 * @return		the helper object synthesized during the visit
	 */
	@Override
	public <ArgType, ReturnType> ReturnType visit(Visitor<ArgType, ReturnType> v, ArgType arg) {
		return v.visitCallStmt(this, arg);
	}
}
