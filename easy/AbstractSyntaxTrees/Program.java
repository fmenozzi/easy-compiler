/**
 * Program
 * 
 * @author 	Federico Menozzi
 * @version Easy Compiler v1
 */

package easy.AbstractSyntaxTrees;

import easy.SyntacticAnalyzer.Line;

public class Program extends AST {
	
	/**
	 * Statements that are executed when the program is run
	 */
	public final BlockStmt mainBlock;
	
	/**
	 * Function declared in the program
	 */
	public final FunctionDeclList functionDeclList;

	/**
	 * Construct a Program 
	 * 
	 * @param mainBlock			the statements executed when the program is run
	 * @param functionDeclList	the list of function declarations
	 * @param structDeclList	the list of struct declarations
	 * @param line				the line number of the "main" keyword in source
	 */
	public Program(BlockStmt mainBlock, 
				   FunctionDeclList functionDeclList, 
				   Line line) {
		
		super(line);
		
		this.mainBlock = mainBlock;
		this.functionDeclList = functionDeclList;
	}

	/**
	 * Visit a Program node
	 * 
	 * @param v		an object that implements the Visitor interface
	 * @param arg	the helper object received for the visit
	 * @return		the helper object synthesized during the visit
	 */
	@Override
	public <ArgType, ReturnType> ReturnType visit(Visitor<ArgType, ReturnType> v, ArgType arg) {
		return v.visitProgram(this, arg);
	}
}