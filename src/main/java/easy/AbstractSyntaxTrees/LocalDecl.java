/**
 * Local Declaration
 * 
 * @author 	Federico Menozzi
 * @version Easy Compiler v1
 */

package easy.AbstractSyntaxTrees;

import easy.SyntacticAnalyzer.Line;

public abstract class LocalDecl extends Declaration {
	
	/**
	 * Construct a LocalDecl of a variable called "name" 
	 * with underlying {@link Type} "type".
	 * 
	 * @param type	the type of the variable 
	 * @param name	the name of the variable
	 * @param line	the line number of the declaration in source
	 */
	public LocalDecl(Type type, String name, Line line){
		super(type, name, line);
	}
}
