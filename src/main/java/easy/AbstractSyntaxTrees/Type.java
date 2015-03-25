/**
 * Type
 * 
 * @author 	Federico Menozzi
 * @version Easy Compiler v1
 */

package easy.AbstractSyntaxTrees;

import easy.SyntacticAnalyzer.Line;

public abstract class Type extends AST {
	
	/**
	 * An instance of an enumeration of possible types
	 */
	public final TypeKind typeKind;
	
	/**
	 * The spelling of the type (e.g. "int")
	 */
	public String spelling;
	
	/**
	 * Construct a Type with the specified {@link TypeKind}
	 * 
	 * @param typeKind	an instance of an enumeration of possible types
	 * @param line		the line number of the type in source
	 */
	public Type(TypeKind typeKind, Line line){
        super(line);
        this.typeKind = typeKind;
        this.spelling = "";
    }
}
