/**
 * Break Statement
 * 
 * @author  Federico Menozzi
 * @version Easy Compiler v1
 */

package easy.AbstractSyntaxTrees;

import easy.SyntacticAnalyzer.Line;

public class BreakStmt extends Statement {
	/**
	 * Construct a break statement
	 * 
	 * @param line	the line number of the break statement in source
	 */
	public BreakStmt(Line line) {
		super(line);
	}
	
	/**
	 * Visit a BreakStmt node
	 * 
	 * @param v		an object that implements the Visitor interface
	 * @param arg	the helper object received for the visit
	 * @return		the helper object synthesized during the visit
	 */
	@Override
	public <ArgType, ReturnType> ReturnType visit(Visitor<ArgType, ReturnType> v, ArgType arg) {
		return v.visitBreakStmt(this, arg);
	}
}
