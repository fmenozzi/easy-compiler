/**
 * Infinite Loop Statement
 * 
 * @author  Federico Menozzi
 * @version Easy Compiler v1
 */

package easy.AbstractSyntaxTrees;

import easy.SyntacticAnalyzer.Line;

public class InfiniteLoopStmt extends Statement {
	
	/**
	 * {@link Statement} representing the body of the loop
	 */
	public final Statement body;
	
	public InfiniteLoopStmt(Statement body, Line line) {
		super(line);
		this.body = body;
	}

	/**
	 * Visit an InfiniteLoopStmt node
	 * 
	 * @param v		an object that implements the Visitor interface
	 * @param arg	the helper object received for the visit
	 * @return		the helper object synthesized during the visit
	 */
	@Override
	public <ArgType, ReturnType> ReturnType visit(Visitor<ArgType, ReturnType> v, ArgType arg) {
		return v.visitInfiniteLoopStmt(this, arg);
	}
}
