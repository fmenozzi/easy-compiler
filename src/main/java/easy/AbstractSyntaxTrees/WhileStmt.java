/**
 * While Statement
 * 
 * @author  Federico Menozzi
 * @version Easy Compiler v1
 */

package easy.AbstractSyntaxTrees;

import easy.SyntacticAnalyzer.Line;

public class WhileStmt extends Statement {
	
	/**
	 * {@link Expression} representing condition to 
	 * check for execution of body
	 */
	public final Expression condition;
	
	/**
	 * {@link Statement} representing the body of the loop
	 */
	public final Statement body;
	
	/**
	 * Construct a WhileStmt with consisting of a condition
	 * and a loop body. 
	 * 
	 * @param condition	the condition for loop execution
	 * @param body		the statement to execute when the condition is true
	 * @param line		the line number of the "while" keyword in source
	 */
	public WhileStmt(Expression condition, Statement body, Line line) {
		super(line);
		
		this.condition = condition;
		this.body = body;
	}
	
	/**
	 * Visit a WhileStmt node
	 * 
	 * @param v		an object that implements the Visitor interface
	 * @param arg	the helper object received for the visit
	 * @return		the helper object synthesized during the visit
	 */
	@Override
	public <ArgType, ReturnType> ReturnType visit(Visitor<ArgType, ReturnType> v, ArgType arg) {
		return v.visitWhileStmt(this, arg);
	}
}
