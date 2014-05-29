/**
 * Qualified Reference
 * 
 * @author 	Federico Menozzi
 * @version Easy Compiler v1
 */

package easy.AbstractSyntaxTrees;

import easy.SyntacticAnalyzer.Line;

public class QualifiedRef extends Reference {
	
	/**
	 * The {@link Reference} to the <i>left</i> of the dot
	 */
	public final Reference ref;
	
	/**
	 * The {@link Identifier} to the <i>right</i> of the dot
	 */
	public final Identifier id;
	
	/**
	 * Construct a QualifiedRef with "ref" on the <i>left</i> of
	 * the dot and "id" on the <i>right</i> of the dot.
	 * 
	 * @param ref	the {@code Reference} on the left of the dot
	 * @param id	the {@code Identifier} on the right of the dot
	 * @param line	the line number of "ref" in source
	 */
	public QualifiedRef(Reference ref, Identifier id, Line line) {
		super(ref.spelling+"."+id.spelling, line);

		this.ref = ref;
		this.id  = id;
	}

	/**
	 * Visit a QualifiedRef node
	 * 
	 * @param v		an object that implements the Visitor interface
	 * @param arg	the helper object received for the visit
	 * @return		the helper object synthesized during the visit
	 */
	@Override
	public <ArgType, ReturnType> ReturnType visit(Visitor<ArgType, ReturnType> v, ArgType arg) {
		return v.visitQualifiedRef(this, arg);
	}
}
