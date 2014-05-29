/**
 * Literal
 * 
 * @author 	Federico Menozzi
 * @version Easy Compiler v1
 */

package easy.AbstractSyntaxTrees;

import easy.SyntacticAnalyzer.Line;

public abstract class Literal extends Terminal {

	/**
	 * Construct a Literal
	 * 
	 * @param spelling	the String representation of the literal
	 * @param line		the line number of the literal in source
	 */
	public Literal(String spelling, Line line) {
		super(spelling, line);
	}
}