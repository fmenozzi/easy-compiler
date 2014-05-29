/**
 * Parameter Declaration
 * 
 * @author  Federico Menozzi
 * @version Easy Compiler v1
 */

package easy.AbstractSyntaxTrees;

import easy.SyntacticAnalyzer.Line;

public class ParameterDecl extends Declaration {

	/**
	 * Construct a ParameterDecl of a variable called "name"
	 * with underlying {@link Type} "type".
	 * 
	 * @param type	the type of the variable
	 * @param name	the name of the variable
	 * @param line	the line number of the declaration in source
	 */
	public ParameterDecl(Type type, String name, Line line) {
		super(type, name, line);
	}
	
	/**
	 * Visit a ParameterDecl node
	 * 
	 * @param v		an object that implements the Visitor interface
	 * @param arg	the helper object received for the visit
	 * @return		the helper object synthesized during the visit
	 */
	@Override
	public <ArgType, ReturnType> ReturnType visit(Visitor<ArgType, ReturnType> v, ArgType arg) {
		return v.visitParameterDecl(this, arg);
	}
}
