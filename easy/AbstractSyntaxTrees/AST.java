/**
 * Abstract Syntax Tree (AST)
 */

package easy.AbstractSyntaxTrees;

import easy.SyntacticAnalyzer.Line;

public abstract class AST {
	/**
	 * The line number of the AST node in source
	 */
	public final Line line;

	/**
	 * Construct an AST node at the given line
	 * 
	 * @param line	the line number of the AST node in source
	 */
	public AST (Line line) {
		this.line = line;
	}

	/**
	 * Print class name of AST node
	 */
	@Override
	public String toString() {
		String fullClassName = this.getClass().getName();
		String className = fullClassName.substring(fullClassName.lastIndexOf('.') + 1);
		if (ASTDisplay.showPosition)
			className += " " + line.toString();
		return className;
	}

	/**
	 * Visit an AST node
	 * 
	 * @param v		an object that implements the Visitor interface
	 * @param arg	the helper object received for the visit
	 * @return		the helper object synthesized during the visit
	 */
	public abstract <ArgType, ReturnType> ReturnType visit(Visitor<ArgType, ReturnType> v, ArgType arg);
}
