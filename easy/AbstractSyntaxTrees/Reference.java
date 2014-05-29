/**
 * Reference
 * 
 * @author 	Federico Menozzi
 * @version Easy Compiler v1
 */

package easy.AbstractSyntaxTrees;

import easy.SyntacticAnalyzer.Line;

public abstract class Reference extends AST {
	/**
	 * String representation of reference (e.g. "nums[3].toString")
	 */
	public final String spelling;
	
	/**
	 * Construct a Reference with given spelling at given line
	 * number in source.
	 * 
	 * @param spelling	the String representation of the reference (e.g. "nums[3].toString")
	 * @param line		the line number of the reference in source
	 */
	public Reference(String spelling, Line line) {
		super(line);
		this.spelling = spelling;
	}
}