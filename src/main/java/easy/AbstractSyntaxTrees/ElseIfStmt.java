/**
 * Else-If Statement
 * 
 * @author  Federico Menozzi
 * @version Easy Compiler v1
 */

package easy.AbstractSyntaxTrees;

import easy.SyntacticAnalyzer.Line;

public class ElseIfStmt extends Statement {
	
	/**
	 * {@link Expression} used to test whether to execute the body
	 */
	public final Expression condition;
	
	/**
	 * {@link Statement} the execute if the condition is true
	 */
	public final Statement body;
	
	/**
	 * Construct an ElseIfStmt that executes a {@code BlockStmt} if
	 * the {@code Expression} condition is true.
	 * 
	 * @param condition	the {@code Expression} used to test whether to execute the body
	 * @param body		the {@code Statement} to execute if the condition is true
	 * @param line		the line number of the "else" keyword in source
	 */
	public ElseIfStmt(Expression condition, Statement body, Line line) {
		super(line);
		
		this.condition = condition;
		this.body      = body;
	}
	
	/**
	 * Visit an ElseIfStmt node
	 * 
	 * @param v		an object that implements the Visitor interface
	 * @param arg	the helper object received for the visit
	 * @return		the helper object synthesized during the visit
	 */
	@Override
	public <ArgType, ReturnType> ReturnType visit(Visitor<ArgType, ReturnType> v, ArgType arg) {
		return v.visitElseIfStmt(this, arg);
	}
}
