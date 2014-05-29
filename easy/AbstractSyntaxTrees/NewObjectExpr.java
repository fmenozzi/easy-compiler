/**
 * New Object Expression
 * 
 * @author  Federico Menozzi
 * @version Easy Compiler v1
 */

package easy.AbstractSyntaxTrees;

import easy.SyntacticAnalyzer.Line;

public class NewObjectExpr extends Expression {

	/**
	 * Underlying type of the expression
	 */
	public final RefType refType;
	
	/**
	 * Argument list of constructor
	 */
	public final ExprList argList;
	
	/**
	 * Construct a NewObjectExpr of type refType. If the refType
	 * is a struct, then the "constructor" will simply instantiate
	 * each field to the corresponding argument. If the refType is
	 * a class, then the corresponding constructor will be called.
	 * 
	 * @param refType	the underlying type of the expression
	 * @param argList	the list of arguments
	 * @param line		the line number of the "new" keyword in source
	 */
	public NewObjectExpr(RefType refType, ExprList argList, Line line) {
		super("new "+refType.typeName.spelling, line);
		
		this.refType = refType;
		this.argList = argList;
	}
	
	/**
	 * Visit a NewObjectExpr node
	 * 
	 * @param v		an object that implements the Visitor interface
	 * @param arg	the helper object received for the visit
	 * @return		the helper object synthesized during the visit
	 */
	@Override
	public <ArgType, ReturnType> ReturnType visit(Visitor<ArgType, ReturnType> v, ArgType arg) {
		return v.visitNewObjectExpr(this, arg);
	}
}
