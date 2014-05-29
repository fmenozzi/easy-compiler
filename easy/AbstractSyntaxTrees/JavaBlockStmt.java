/**
 * Java Block Statement
 * 
 * @author  Federico Menozzi
 * @version Easy Compiler v1
 */

package easy.AbstractSyntaxTrees;

import java.util.List;

import easy.SyntacticAnalyzer.Line;

public class JavaBlockStmt extends Statement {

	/**
	 * List of Java statements 
	 */
	public final List<StringLiteral> statements;
	
	/**
	 * Construct a JavaBlockStmt
	 * 
	 * @param statements	the list of Java statements
	 * @param line			the line number of the "java" keyword in source
	 */
	public JavaBlockStmt(List<StringLiteral> statements, Line line) {
		super(line);
		this.statements = statements;
	}
	
	/**
	 * Visit a JavaBlockStmt node
	 * 
	 * @param v		an object that implements the Visitor interface
	 * @param arg	the helper object received for the visit
	 * @return		the helper object synthesized during the visit
	 */
	@Override
	public <ArgType, ReturnType> ReturnType visit(Visitor<ArgType, ReturnType> v, ArgType arg) {
		return v.visitJavaBlockStmt(this, arg);
	}
}
