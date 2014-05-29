/**
 * Reference Literal
 * 
 * @author  Federico Menozzi
 * @version Easy Compiler v1
 */

package easy.AbstractSyntaxTrees;

import easy.SyntacticAnalyzer.Line;

public class ReferenceLiteral extends Literal {
	
	/**
	 * Construct a ReferenceLiteral with specified spelling
	 * at the given line in source.
	 * 
	 * @param line		the line number of the literal in source
	 */
	public ReferenceLiteral(Line line) {
		super ("null", line);
	}

	/**
	 * Visit a ReferenceLiteral node
	 * 
	 * @param v		an object that implements the Visitor interface
	 * @param arg	the helper object received for the visit
	 * @return		the helper object synthesized during the visit
	 */
	@Override
	public <ArgType, ReturnType> ReturnType visit(Visitor<ArgType, ReturnType> v, ArgType arg) {
		return v.visitReferenceLiteral(this, arg);
	}
}
