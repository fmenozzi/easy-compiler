/**
 * Terminal
 * 
 * @author 	Federico Menozzi
 * @version Easy Compiler v1
 */

package easy.AbstractSyntaxTrees;

import easy.SyntacticAnalyzer.Line;

abstract public class Terminal extends AST {

	/**
	 * String representation of Terminal
	 */
	public final String spelling;

	/**
	 * Construct a Terminal with given spelling and line number
	 * 
	 * @param spelling	the String representation of the Terminal
	 * @param line		the line number of the Terminal in source
	 */
	public Terminal(String spelling, Line line) {
		super(line);
		this.spelling = spelling;
	}
}