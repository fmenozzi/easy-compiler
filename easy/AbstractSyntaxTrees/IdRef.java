/**
 * Identifier Reference
 * 
 * @author 	Federico Menozzi
 * @version Easy Compiler v1
 */

package easy.AbstractSyntaxTrees;

import easy.SyntacticAnalyzer.Line;

public class IdRef extends Reference {
	
	/**
	 * Underlying {@link Identifier}
	 */
	public final Identifier id;
	
	/**
	 * Construct an IdRef with underlying {@code Identifier}
	 * id at the given line in source.
	 * 
	 * @param id	the underlying Identifier
	 * @param line	the line number of the underlying Identifier in source
	 */
	public IdRef(Identifier id, Line line){
		super(id.spelling, line);
		this.id = id;
	}
		
	/**
	 * Visit an IdRef node
	 * 
	 * @param v		an object that implements the Visitor interface
	 * @param arg	the helper object received for the visit
	 * @return		the helper object synthesized during the visit
	 */
	@Override
	public <ArgType, ReturnType> ReturnType visit(Visitor<ArgType, ReturnType> v, ArgType arg) {
		return v.visitIdRef(this, arg);
	}
}
