/**
 * Variable Declaration
 * 
 * @author  Federico Menozzi
 * @version Easy Compiler v1
 */

package easy.AbstractSyntaxTrees;

import easy.SyntacticAnalyzer.Line;

public class VarDecl extends Declaration {

	/**
	 * Construct a VarDecl of a variable called "name" 
	 * with underlying {@link Type} "type".
	 * 
	 * @param type	the type of the variable 
	 * @param name	the name of the variable
	 * @param line	the line number of the name in source
	 */
	public VarDecl(Type type, String name, Line line) {
		super(type, name, line);
	}
	
	/**
	 * Visit a VarDecl node
	 * 
	 * @param v		an object that implements the Visitor interface
	 * @param arg	the helper object received for the visit
	 * @return		the helper object synthesized during the visit
	 */
	@Override
	public <ArgType, ReturnType> ReturnType visit(Visitor<ArgType, ReturnType> v, ArgType arg) {
		return v.visitVarDecl(this, arg);
	}
}
