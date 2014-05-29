/**
 * Block Statement
 * 
 * @author  Federico Menozzi
 * @version Easy Compiler v1
 */

package easy.AbstractSyntaxTrees;

import easy.SyntacticAnalyzer.Line;

public class BlockStmt extends Statement {

	/**
	 * List of statements to execute
	 */
	public final StatementList statementList;
	
	/**
	 * Construct a BlockStmt containing the {@link StatementList} statementList
	 * 
	 * @param statementList	the list of statements to execute
	 * @param line			the line of the first statement of the block in source
	 */
	public BlockStmt(StatementList statementList, Line line) {
		super(line);
		this.statementList = statementList;
	}
	
	/**
	 * Visit a BlockStmt node
	 * 
	 * @param v		an object that implements the Visitor interface
	 * @param arg	the helper object received for the visit
	 * @return		the helper object synthesized during the visit
	 */
	@Override
	public <ArgType, ReturnType> ReturnType visit(Visitor<ArgType, ReturnType> v, ArgType arg) {
		return v.visitBlockStmt(this, arg);
	}
}
