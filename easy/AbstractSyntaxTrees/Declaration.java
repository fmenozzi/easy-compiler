/**
 * Declaration
 * 
 * @author 	Federico Menozzi
 * @version Easy Compiler v1
 */

package easy.AbstractSyntaxTrees;

import easy.SyntacticAnalyzer.Line;

public abstract class Declaration extends AST {
	
	/**
	 * {@link Type} of declared variable
	 */
	public final Type type;
	
	/**
	 * Name of declared variable
	 */
	public final String name;
	
	/**
	 * Construct a Declaration of the variable "name" with
	 * underlying {@code Type} "type". 
	 * 
	 * @param name	the name of the variable
	 * @param type	the type of the variable 
	 * @param line	the line number of the type in source
	 */
	public Declaration(Type type, String name, Line line) {
		super(line);
		
		this.type 	  = type;
		this.name 	  = name;
	}
}
