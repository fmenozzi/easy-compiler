/**
 * Base Type
 * 
 * @author 	Federico Menozzi
 * @version Easy Compiler v1
 */

package easy.AbstractSyntaxTrees;

import easy.SyntacticAnalyzer.Line;

public class BaseType extends Type {

	/**
	 * Construct a BaseType with given {@link TypeKind} and {@link Line}
	 * 
	 * @param typeKind	an instance of the enum {@code TypeKind}
	 * @param line		the line number of the BaseType in source
	 */
	public BaseType(TypeKind typeKind, Line line) {
		super(typeKind, line);
		this.spelling = typeKind.name().toLowerCase();
	}

	/**
	 * Visit an BaseType node
	 * 
	 * @param v		an object that implements the Visitor interface
	 * @param arg	the helper object received for the visit
	 * @return		the helper object synthesized during the visit
	 */
	@Override
	public <ArgType, ReturnType> ReturnType visit(Visitor<ArgType, ReturnType> v, ArgType arg) {
		return v.visitBaseType(this, arg);
	}
}
