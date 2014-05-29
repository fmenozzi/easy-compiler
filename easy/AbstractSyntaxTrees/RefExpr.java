/**
 * Reference Expression
 * 
 * @author 	Federico Menozzi
 * @version Easy Compiler v1
 */

package easy.AbstractSyntaxTrees;

import easy.SyntacticAnalyzer.Line;

public class RefExpr extends Expression {

	/**
	 * Underlying reference
	 */
	public final Reference ref;
	
	/**
	 * Construct a RefExpr with underlying Reference ref
	 * 
	 * @param ref	the underlying reference
	 * @param line	the line number of the reference in source
	 */
	public RefExpr(Reference ref, Line line) {
		super(ref.spelling, line);
		this.ref = ref;
	}
	
	/**
	 * Visit a RefExpr node
	 * 
	 * @param v		an object that implements the Visitor interface
	 * @param arg	the helper object received for the visit
	 * @return		the helper object synthesized during the visit
	 */
	@Override
	public <ArgType, ReturnType> ReturnType visit(Visitor<ArgType, ReturnType> v, ArgType arg) {
		return v.visitRefExpr(this, arg);
	}
}
