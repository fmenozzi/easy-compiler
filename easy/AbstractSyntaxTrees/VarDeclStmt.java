/**
 * Variable Declaration Statement
 * 
 * @author  Federico Menozzi
 * @version Easy Compiler v1
 */

package easy.AbstractSyntaxTrees;

import easy.SyntacticAnalyzer.Line;

public class VarDeclStmt extends Statement {

	/**
	 * Variable declaration portion of statement
	 */
	public final VarDecl varDecl;
	
	/**
	 * Initializing expression portion of statement
	 */
	public final Expression initExpr;
	
	/**
	 * Construct a VarDeclStmt consisting of the {@link VarDecl} varDecl
	 * and the initializing {@link Expression} initExpr.
	 * 
	 * @param varDecl	the {@code VarDecl}
	 * @param initExpr	the initializing {@code Expression}
	 * @param line		the line number of "varDecl" in source
	 */
	public VarDeclStmt(VarDecl varDecl, Expression initExpr, Line line) {
		super(line);
		
		this.varDecl  = varDecl;
		this.initExpr = initExpr;
	}
	
	/**
	 * Visit a VarDeclStmt node
	 * 
	 * @param v		an object that implements the Visitor interface
	 * @param arg	the helper object received for the visit
	 * @return		the helper object synthesized during the visit
	 */
	@Override
	public <ArgType, ReturnType> ReturnType visit(Visitor<ArgType, ReturnType> v, ArgType arg) {
		return v.visitVardeclStmt(this, arg);
	}
}
