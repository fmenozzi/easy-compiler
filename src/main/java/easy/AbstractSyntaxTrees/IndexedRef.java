/**
 * Indexed Reference
 * 
 * @author 	Federico Menozzi
 * @version Easy Compiler v1
 */

package easy.AbstractSyntaxTrees;

import easy.SyntacticAnalyzer.Line;

public class IndexedRef extends Reference {

	/**
	 * Underlying {@link Reference}
	 */
	public final Reference ref;
	
	/**
	 * Index {@link Expression}
	 */
	public final Expression indexExpr;
	
	/**
	 * Construct an IndexedRef with underlying {@code Reference} "ref"
	 * and index {@code Expression} indexExpr. 
	 * 
	 * @param ref		the underlying reference
	 * @param indexExpr	the index expression
	 * @param line		the line number of the reference in source
	 */
	public IndexedRef(Reference ref, Expression indexExpr, Line line) {
		super(ref.spelling+"["+indexExpr.spelling+"]", line);
		
		this.ref = ref;
		this.indexExpr = indexExpr;
	}

	/**
	 * Visit an IndexedRef node
	 * 
	 * @param v		an object that implements the Visitor interface
	 * @param arg	the helper object received for the visit
	 * @return		the helper object synthesized during the visit
	 */
	@Override
	public <ArgType, ReturnType> ReturnType visit(Visitor<ArgType, ReturnType> v, ArgType arg) {
		return v.visitIndexedRef(this, arg);
	}
}
