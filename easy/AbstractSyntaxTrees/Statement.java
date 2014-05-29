/**
 * Statement
 * 
 * @author 	Federico Menozzi
 * @version Easy Compiler v1
 */

package easy.AbstractSyntaxTrees;

import easy.SyntacticAnalyzer.Line;

public abstract class Statement extends AST {

	/**
	 * Construct a Statement
	 * 
	 * @param line	the line number of the Statement in source
	 */
	public Statement(Line line) {
		super(line);
	}
}