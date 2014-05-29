/**
 * Type Declaration
 * 
 * @author 	Federico Menozzi
 * @version Easy Compiler v1
 */

package easy.AbstractSyntaxTrees;

import easy.SyntacticAnalyzer.Line;

public abstract class TypeDecl extends Declaration {
	
	/**
	 * The name of the type
	 */
	public final String name;
	
	/**
	 * The list of fields (structs have fields only, 
	 * classes also have methods)
	 */
	public final FieldDeclList fieldDeclList;
	
	/**
	 * Construct a new TypeDecl with given type name
	 * and list of fields.
	 * 
	 * @param name			the type name
	 * @param fieldDeclList	the list of fields
	 * @param line			the line number of the name in source
	 */
	public TypeDecl(String name, FieldDeclList fieldDeclList, Line line) {
		super(null, name, line);
		
		this.name = name;
		this.fieldDeclList = fieldDeclList;
	}
}