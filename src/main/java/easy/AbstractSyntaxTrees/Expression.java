/**
 * Expression
 * 
 * @author 	Federico Menozzi
 * @version Easy Compiler v1
 */

package easy.AbstractSyntaxTrees;

import easy.SyntacticAnalyzer.Line;

public abstract class Expression extends AST {
	
	/**
	 * String representation of the expression (e.g. "2+3")
	 */
	public final String spelling;
	
	/**
	 * Construct an Expression with given spelling at 
	 * the given line number in source
	 * 
	 * @param spelling	the String representation of the expression (e.g. "2+3")
	 * @param line		the line number of the leftmost subexpression
	 */
	public Expression(String spelling, Line line) {
		super(line);
		this.spelling = spelling;
	}
}
