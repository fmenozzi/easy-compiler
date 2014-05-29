/**
 * Structure Declaration
 * 
 * @author 	Federico Menozzi
 * @version Easy Compiler v1
 */

package easy.AbstractSyntaxTrees;

import easy.SyntacticAnalyzer.Line;

public class StructDecl extends TypeDecl {
	
	/**
	 * Construct a StructDecl with the given name and
	 * list of fields
	 * 
	 * @param structName	the name of the new type the struct defines
	 * @param fieldDeclList	the list of fields in the struct
	 * @param line			the line number of the "struct" keyword in source
	 */
	public StructDecl(String structName, FieldDeclList fieldDeclList, Line line) {
		super(structName, fieldDeclList, line);
	}
	
	/**
	 * Visit a StructDecl node
	 * 
	 * @param v		an object that implements the Visitor interface
	 * @param arg	the helper object received for the visit
	 * @return		the helper object synthesized during the visit
	 */
	@Override
	public <ArgType, ReturnType> ReturnType visit(Visitor<ArgType, ReturnType> v, ArgType arg) {
		return v.visitStructDecl(this, arg);
	}
}
