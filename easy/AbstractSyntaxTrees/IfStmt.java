/**
 * If Statement
 * 
 * @author  Federico Menozzi
 * @version Easy Compiler v1
 */

package easy.AbstractSyntaxTrees;

import java.util.List;

import easy.SyntacticAnalyzer.Line;

public class IfStmt extends Statement {
	
	/**
	 * {@link Expression} representing condition
	 */
	public final Expression condition;
	
	/**
	 * {@link Statement} representing body of code executed
	 * if the condition is true
	 */
	public final Statement body;
	
	/**
	 * List of {@link ElseIfStmt}s, can be null
	 */
	public final List<ElseIfStmt> elseIfStmts;
	
	/**
	 * {@link ElseStmt}, can be null
	 */
	public final ElseStmt elseStmt;
	
	/**
	 * Construct an IfStmt that executes a {@code Statement} if
	 * the {@code Expression} condition evaluates to true; otherwise, 
	 * control is transferred to the appropriate {@code ElseIfStmt} or
	 * {@code ElseStmt}.
	 * 
	 * @param condition		the condition used to test whether to execute the body
	 * @param body			the statement to execute if the condition is true
	 * @param elseIfStmts	the optional ArrayList of list of {@code ElseIfStmt}s (i.e. can be null)
	 * @param elseStmt		the optional {@code ElseStmt} (i.e. can be null)
	 * @param line			the line number of the "if" keyword in source
	 */
	public IfStmt(Expression condition, 
				  Statement body, 
				  List<ElseIfStmt> elseIfStmts, 
				  ElseStmt elseStmt, 
				  Line line) {
		
		super(line);
		
		this.condition 	 = condition;
		this.body 		 = body;
		this.elseIfStmts = elseIfStmts.isEmpty() ? null : elseIfStmts;
		this.elseStmt 	 = elseStmt;
	}

	/**
	 * Visit an IfStmt node
	 * 
	 * @param v		an object that implements the Visitor interface
	 * @param arg	the helper object received for the visit
	 * @return		the helper object synthesized during the visit
	 */
	@Override
	public <ArgType, ReturnType> ReturnType visit(Visitor<ArgType, ReturnType> v, ArgType arg) {
		return v.visitIfStmt(this, arg);
	}
}
