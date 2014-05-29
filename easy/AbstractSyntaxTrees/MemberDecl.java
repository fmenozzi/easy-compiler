package easy.AbstractSyntaxTrees;

import easy.SyntacticAnalyzer.Line;

abstract public class MemberDecl extends Declaration {
	
	/**
	 * Boolean flag for indicating whether the field is public or private
	 */
	public final boolean isPrivate;
	
	/**
	 * Boolean flag for indicating whether the field is static or not
	 */
    public final boolean isStatic;
    
    /**
     * Underlying {@link TypeDecl} of the field (distinguishes between class and struct)
     */
    public final TypeDecl typeDecl;
    
    /**
     * Construct a Member Decl
     * 
     * @param isPrivate	the boolean flag to indicate whether the member is public or private
     * @param isStatic	the boolean flag to indicate whether the mmeber is static or not
     * @param type		the type of the member
     * @param name		the name of the member
     * @param line		the line number of the name in source
     */
    public MemberDecl(boolean isPrivate, boolean isStatic, Type type, String name, Line line) {
    	super(type, name, line);
    	
    	this.isPrivate = isPrivate;
    	this.isStatic  = isStatic;
    	
    	typeDecl = null;	// TODO Is this field even needed?
    }
}
