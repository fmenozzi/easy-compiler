/**
 * Reference Type
 * 
 * @author 	Federico Menozzi
 * @version Easy Compiler v1
 */

package easy.AbstractSyntaxTrees;

import easy.SyntacticAnalyzer.Line;

public class RefType extends Type {
	
	/**
	 * {@link Identifier} representing spelling of type's name
	 */
	public final Identifier typeName;
	
	/**
	 * Construct a RefType 
	 * 
	 * @param typeName	the {@code Identifier} that encapsulates the spelling of the type name
	 * @param line		the line number of the Identifier in source
	 */
	public RefType(Identifier typeName, Line line) {
		super(TypeKind.REF, line);
		
		this.typeName = typeName;
		this.spelling = typeName.spelling;
	}

	/**
	 * Visit a RefType node
	 * 
	 * @param v		an object that implements the Visitor interface
	 * @param arg	the helper object received for the visit
	 * @return		the helper object synthesized during the visit
	 */
	@Override
	public <ArgType, ReturnType> ReturnType visit(Visitor<ArgType, ReturnType> v, ArgType arg) {
		return v.visitRefType(this, arg);
	}
}
