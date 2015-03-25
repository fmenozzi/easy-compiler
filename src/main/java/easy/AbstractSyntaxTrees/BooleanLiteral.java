/**
 * Boolean Literal
 * 
 * @author 	Federico Menozzi
 * @version Easy Compiler v1
 */

package easy.AbstractSyntaxTrees;

import easy.SyntacticAnalyzer.Line;

public class BooleanLiteral extends Literal {
	
	/**
	 * Construct a BooleanLiteral with specified spelling
	 * at the given line in source.
	 * 
	 * @param spelling	the spelling of the literal (either "true" or "false")
	 * @param line		the line number of the literal in source
	 */
	public BooleanLiteral(String spelling, Line line) {
		super(spelling, line);
	}

	/**
	 * Visit a BooleanLiteral node
	 * 
	 * @param v		an object that implements the Visitor interface
	 * @param arg	the helper object received for the visit
	 * @return		the helper object synthesized during the visit
	 */
	@Override
	public <ArgType, ReturnType> ReturnType visit(Visitor<ArgType, ReturnType> v, ArgType arg) {
		return v.visitBooleanLiteral(this, arg);
	}
}
