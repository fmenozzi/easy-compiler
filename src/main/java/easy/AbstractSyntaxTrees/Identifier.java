/**
 * Identifier
 * 
 * @author  Federico Menozzi
 * @version Easy Compiler v1
 */

package easy.AbstractSyntaxTrees;

import easy.SyntacticAnalyzer.Line;

public class Identifier extends Terminal {
	
	/**
	 * Construct an Identifier with String representation
	 * s at the given line. 
	 * 
	 * @param s		the String representation of the Identifier (e.g. "foo")
	 * @param line	the line number of the identifier in source
	 */
	public Identifier (String s, Line line) {
		super (s, line);
	}

	/**
	 * Visit an Identifier node
	 * 
	 * @param v		an object that implements the Visitor interface
	 * @param arg	the helper object received for the visit
	 * @return		the helper object synthesized during the visit
	 */
	@Override
	public <ArgType, ReturnType> ReturnType visit(Visitor<ArgType, ReturnType> v, ArgType arg) {
		return v.visitIdentifier(this, arg);
	}
}
