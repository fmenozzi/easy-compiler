/**
 * For Statement
 * 
 * @author  Federico Menozzi
 * @version Easy Compiler v1
 */

package easy.AbstractSyntaxTrees;

import easy.SyntacticAnalyzer.Line;

public class ForStmt extends Statement {

	/**
	 * Integer variable used to iterate through the loop
	 */
	public final Identifier loopVar;
	
	/**
	 * Type of iterator variable
	 */
	
	public final Type loopVarType;
	
	/**
	 * Integer {@link Expression} for start of range
	 */
	public final Expression fromExpr;
	
	/**
	 * Integer {@code Expression} for end of range, inclusive
	 */
	public final Expression toExpr;
	
	/**
	 * Optional integer {@code Expression} for increment, defaults to one
	 */
	public final Expression byExpr;
	
	/**
	 * {@link BlockStmt} representing the body of the loop
	 */
	public final BlockStmt body;
	
	/**
	 * Construct a ForStmt that uses the integer {@link Identifier} loopVar
	 * to iterate through the values in the range [fromExpr, toExpr] in steps
	 * of byExpr. 
	 * 
	 * @param loopVar	the {@code Identifier} used to loop through the range of values
	 * @param fromExpr	the beginning of the range
	 * @param toExpr	the end of the range, inclusive
	 * @param byExpr	the increment (defaults to one if excluded from constructor)
	 * @param body		the statements to execute
	 * @param line		the line number of the for statement in source
	 */
	public ForStmt(Identifier loopVar, 
				   Type loopVarType,
				   Expression fromExpr, 
				   Expression toExpr, 
				   Expression byExpr,
				   BlockStmt body, 
				   Line line) {
		
		super(line);
		
		this.loopVar  	 = loopVar;
		this.loopVarType = loopVarType;
		this.fromExpr 	 = fromExpr;
		this.toExpr   	 = toExpr;
		this.byExpr   	 = byExpr;
		this.body 	  	 = body;
	}
	
	/**
	 * Construct a ForStmt that uses the integer {@link Identifier} loopVar
	 * to iterate through the values in the range [fromExpr, toExpr] in steps
	 * of byExpr. 
	 * 
	 * @param loopVar	the {@code Identifier} used to loop through the range of values
	 * @param fromExpr	the beginning of the range
	 * @param toExpr	the end of the range, inclusive
	 * @param body		the statements to execute
	 * @param line		the line number of the "for" keyword in source
	 */
	public ForStmt(Identifier loopVar, 
				   Type loopVarType,
				   Expression fromExpr, 
				   Expression toExpr, 
				   BlockStmt body, 
				   Line line) {
		
		super(line);
		
		this.loopVar  	 = loopVar;
		this.loopVarType = loopVarType;
		this.fromExpr 	 = fromExpr;
		this.toExpr   	 = toExpr;
		this.byExpr   	 = new LiteralExpr(new IntLiteral("1", line), line);
		this.body 	  	 = body;
	}
	
	/**
	 * Visit a ForStmt node
	 * 
	 * @param v		an object that implements the Visitor interface
	 * @param arg	the helper object received for the visit
	 * @return		the helper object synthesized during the visit
	 */
	@Override
	public <ArgType, ReturnType> ReturnType visit(Visitor<ArgType, ReturnType> v, ArgType arg) {
		return v.visitForStmt(this, arg);
	}
}
