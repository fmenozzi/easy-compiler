/**
 * Else Statement
 * 
 * @author  Federico Menozzi
 * @version Easy Compiler v1
 */

package easy.AbstractSyntaxTrees;

import easy.SyntacticAnalyzer.Line;

public class ElseStmt extends Statement {
	
	/**
	 * {@link Statement} representing body of code to execute
	 */
	public final Statement body;
	
	/**
	 * Construct an ElseStmt that executes a {@link BlockStmt} 
	 * 
	 * @param block	the {@code Statement} to execute
	 * @param line	the line number of the "else" keyword in source
	 */
	public ElseStmt(Statement body, Line line) {
		super(line);
		this.body = body;
	}
	
	/**
	 * Visit an ElseStmt node
	 * 
	 * @param v		an object that implements the Visitor interface
	 * @param arg	the helper object received for the visit
	 * @return		the helper object synthesized during the visit
	 */
	@Override
	public <ArgType, ReturnType> ReturnType visit(Visitor<ArgType, ReturnType> v, ArgType arg) {
		return v.visitElseStmt(this, arg);
	}
}
