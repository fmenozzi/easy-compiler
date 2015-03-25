/**
 * Integer Literal
 * 
 * @author 	Federico Menozzi
 * @version Easy Compiler v1
 */

package easy.AbstractSyntaxTrees;

import easy.SyntacticAnalyzer.Line;

public class IntLiteral extends Literal {

	/**
	 * Construct an IntLiteral with the given spelling
	 * at the given line. 
	 * 
	 * @param spelling	the String representation of the literal (e.g. "1")
	 * @param line		the line number of the literal in source
	 */
	public IntLiteral(String spelling, Line line) {
		super(spelling, line);
	}

	/**
	 * Visit an IntLiteral node
	 * 
	 * @param v		an object that implements the Visitor interface
	 * @param arg	the helper object received for the visit
	 * @return		the helper object synthesized during the visit
	 */
	@Override
	public <ArgType, ReturnType> ReturnType visit(Visitor<ArgType, ReturnType> v, ArgType arg) {
		return v.visitIntLiteral(this, arg);
	}
}
