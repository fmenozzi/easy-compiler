/**
 * For-Each Statement
 * 
 * @author  Federico Menozzi
 * @version Easy Compiler v1
 */

package easy.AbstractSyntaxTrees;

import easy.SyntacticAnalyzer.Line;

public class ForEachStmt extends Statement {
	
	/**
	 * The {@link Identifier} of the iterator
	 */
	public final Identifier loopVar;
	
	/**
	 * Type of iterator variable
	 */
	
	public final Type loopVarType;
	
	/**
	 * The collection to iterate through
	 */
	public final Expression collection;
	
	/**
	 * The statement to execute
	 */
	public final Statement body;
	
	/**
	 * Construct a ForEachStmt that uses the iterator iterDecl to
	 * iterate through collection, executing the body each time.
	 * 
	 * @param loopVar		the {@code Identifier} used as an iterator
	 * @param collection	the values through which to iterate
	 * @param body			the statement to execute in each iteration
	 * @param line			the line number of the "for" keyword in source
	 */
	public ForEachStmt(Identifier loopVar, Type loopVarType, Expression collection, Statement body, Line line) {
		super(line);
		
		this.loopVar 	 = loopVar;
		this.loopVarType = loopVarType;
		this.collection  = collection;
		this.body 		 = body;
	}
	
	/**
	 * Visit a ForEachStmt node
	 * 
	 * @param v		an object that implements the Visitor interface
	 * @param arg	the helper object received for the visit
	 * @return		the helper object synthesized during the visit
	 */
	@Override
	public <ArgType, ReturnType> ReturnType visit(Visitor<ArgType, ReturnType> v, ArgType arg) {
		return v.visitForEachStmt(this, arg);
	}
}
