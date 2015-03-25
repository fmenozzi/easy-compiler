/**
 * Assignment Statement
 * 
 * @author 	Federico Menozzi
 * @version Easy Compiler v1
 */

package easy.AbstractSyntaxTrees;

import easy.SyntacticAnalyzer.Line;

public class AssignStmt extends Statement {
	
	/**
	 * {@link Expression} that ref gets assigned
	 */
	public final Expression val;
	
	/**
	 * {@link Reference} that val gets assigned to
	 */
	public final Reference  ref;
	
	/**
	 * Construct an AssignStmt 
	 * 
	 * @param ref	the {@code Reference} that val gets assigned to
	 * @param val	the {@code Expression} that ref gets assigned	
	 * @param line	the line number of the reference in source
	 */
	public AssignStmt(Reference ref, Expression val, Line line){
        super(line);
        
        this.ref = ref;
        this.val = val;
    }
    
	/**
	 * Visit an AssignStmt node
	 * 
	 * @param v		an object that implements the Visitor interface
	 * @param arg	the helper object received for the visit
	 * @return		the helper object synthesized during the visit
	 */
	@Override
	public <ArgType, ReturnType> ReturnType visit(Visitor<ArgType, ReturnType> v, ArgType arg) {
		return v.visitAssignStmt(this, arg);
	}
}
