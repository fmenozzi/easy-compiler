/**
 * Function Declaration
 * 
 * @author  Federico Menozzi
 * @version Easy Compiler v1
 */

package easy.AbstractSyntaxTrees;

import easy.SyntacticAnalyzer.Line;

public class FunctionDecl extends Declaration {
	
	/**
	 * {@link ParameterDeclList} of formal parameters to function
	 */
	public final ParameterDeclList params;
	
	/**
	 * {@link BlockStmt} to execute in function call
	 */
	public final BlockStmt block;

	/**
	 * Construct a FunctionDecl 
	 * 
	 * @param name			the name of the function
	 * @param returnType	the type returned by the function, not null
	 * @param params		the list of formal parameters
	 * @param block			the statements to execute in function call
	 * @param line			the line number of the "function" keyword in source
	 */
	public FunctionDecl(String name, Type returnType, ParameterDeclList params, BlockStmt block, Line line) {
		super(returnType, name, line);
		
		this.params = params;
		this.block = block;
	}

	/**
	 * Visit a FunctionDecl node
	 * 
	 * @param v		an object that implements the Visitor interface
	 * @param arg	the helper object received for the visit
	 * @return		the helper object synthesized during the visit
	 */
	@Override
	public <ArgType, ReturnType> ReturnType visit(Visitor<ArgType, ReturnType> v, ArgType arg) {
		return v.visitFunctionDecl(this, arg);
	}
}
