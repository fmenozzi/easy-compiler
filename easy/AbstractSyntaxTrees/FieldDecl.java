/**
 * Field Declaration
 * 
 * @author 	Federico Menozzi
 * @version Easy Compiler v1
 */

package easy.AbstractSyntaxTrees;

import easy.SyntacticAnalyzer.Line;

public class FieldDecl extends MemberDecl {

	/**
	 * Construct a FieldDecl for use in either classes or structs. If used
	 * in structs, isPrivate and isStatic will always both be false. 
	 * 
	 * @param isPrivate	the boolean flag to indicate whether the field is public or private
	 * @param isStatic	the boolean flag to indicate whether the field is static or not
	 * @param type		the type of the field
	 * @param name		the name of the field
	 * @param line		the line number of the name in source
	 */
	public FieldDecl(boolean isPrivate, boolean isStatic, Type type, String name, Line line){
		super(isPrivate, isStatic, type, name, line);
	}

	/**
	 * Visit a FieldDecl node
	 * 
	 * @param v		an object that implements the Visitor interface
	 * @param arg	the helper object received for the visit
	 * @return		the helper object synthesized during the visit
	 */
	@Override
	public <ArgType, ReturnType> ReturnType visit(Visitor<ArgType, ReturnType> v, ArgType arg) {
		return v.visitFieldDecl(this, arg);
	}
}
