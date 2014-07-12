/**
 * Display the Abstract Syntax Tree (AST) in textual form. 
 * <p>
 * Performs an in-order traversal of the AST, visiting each node with
 * a method of the form:
 * <p>
 * public Object visitNODE(NODE astnode, String arg)
 * <p>
 * where "arg" is a prefix String (indentation) to precede display of "astnode".
 * <p>
 * Implements Visitor<String, Object>
 * 
 * @author  Federico Menozzi
 * @version Easy Compiler v1
 */

// TODO Re-write all documentation to use correct names (and include "to stdout")

package easy.AbstractSyntaxTrees;

public class ASTDisplay implements Visitor<String, Object> {
	
	public static boolean showPosition = false;
	
	/**
     * Display arbitrary text for a node.
     * 
     * @param prefix  the spacing to indicate depth in AST
     * @param text    the pre-formatted node display
     */
    private void show(String prefix, String text) {
        System.out.println(prefix + text);
    }
    
    /**
     * Display AST node by name.
     * 
     * @param prefix  the spacing to indicate depth in AST
     * @param node    the AST node, will be shown by name
     */
    private void show(String prefix, AST node) {
    	System.out.println(prefix + node.toString());
    }
    
    /**
     * Quote a string
     * 
     * @param text    the string to quote
     */
    private String quote(String text) {
    	return ("\"" + text + "\"");
    }
    
    /**
     * Increase depth in AST
     * 
     * @param prefix	the current spacing to indicate depth in AST
     * @return  		the new spacing 
     */
    private String indent(String prefix) {
        return prefix + "  ";
    }
	
	/**
     * Print textual representation of AST to stdout
     * 
     * @param ast	the root node of AST 
     */
    public void showTree(AST ast) {
        System.out.println("==================== AST Display ====================");
        ast.visit(this, "");
        System.out.println("======================================================");
    }

    /**
     * Print textual representation of entire program to stdout
     * 
     * @param prog	the program
     * @param arg	the prefix String 
     */
	@Override
	public Object visitProgram(Program prog, String arg) {
		show(arg, prog);
		
        FunctionDeclList functions = prog.functionDeclList;
        BlockStmt mainBlock		   = prog.mainBlock;
        
        String prefix = arg + "  . ";
        
        show(arg, "  FunctionDeclList [" + functions.size() + "]");
        for (FunctionDecl function : functions)
        	function.visit(this, prefix);
        
        show(arg, "  Main Block [" + mainBlock.statementList.size() + "]");
        for (Statement statement : mainBlock.statementList)
        	statement.visit(this, prefix);
        
        return null;
	}

	/**
     * Print textual representation of a Function Declaration
     * 
     * @param prog	the Function Declaration
     * @param arg	the prefix String 
     */
	@Override
	public Object visitFunctionDecl(FunctionDecl fd, String arg) {		
    	fd.type.visit(this, indent(arg));
    	show(indent(arg), quote(fd.name) + " function name");
    	
        ParameterDeclList params = fd.params;
        show(arg, "  ParameterDeclList [" + params.size() + "]");
        
        String prefix = arg + "  . ";
        
        for (ParameterDecl param: params)
            param.visit(this, prefix);
        
        StatementList statements = fd.block.statementList;
        
        show(arg, "  StmtList [" + statements.size() + "]");
        for (Statement statement: statements)
            statement.visit(this, prefix);
      
        return null;
	}

	/**
     * Print textual representation of a Parameter Declaration
     * 
     * @param prog	the Function Declaration
     * @param arg	the prefix String 
     */
	@Override
	public Object visitParameterDecl(ParameterDecl pd, String arg) {
		show(arg, pd);
        pd.type.visit(this, indent(arg));
        show(indent(arg), quote(pd.name) + " parameter name ");
        return null;
	}

	/**
     * Print textual representation of a Variable Declaration
     * 
     * @param prog	the Variable Declaration
     * @param arg	the prefix String 
     */
	@Override
	public Object visitVarDecl(VarDecl decl, String arg) {
		show(arg, decl);
        decl.type.visit(this, indent(arg));
        show(indent(arg), quote(decl.name) + " variable name");
        return null;
	}

	/**
     * Print textual representation of a Base Type
     * 
     * @param prog	the Base Type
     * @param arg	the prefix String 
     */
	@Override
	public Object visitBaseType(BaseType type, String arg) {
		show(arg, type.typeKind + " " + type.toString());
        return null;
	}

	/**
     * Print textual representation of a Block Statement
     * 
     * @param prog	the Block Statement
     * @param arg	the prefix String 
     */
	@Override
	public Object visitBlockStmt(BlockStmt stmt, String arg) {
		show(arg, stmt);
        StatementList statements = stmt.statementList;
        show(arg,"  StatementList [" + statements.size() + "]");
        String pfx = arg + "  . ";
        for (Statement statement: statements)
        	statement.visit(this, pfx);
        return null;
	}

	/**
     * Print textual representation of a Variable Declaration Statement
     * 
     * @param prog	the Variable Declaration Statement
     * @param arg	the prefix String 
     */
	@Override
	public Object visitVardeclStmt(VarDeclStmt stmt, String arg) {
		show(arg, stmt);
        stmt.varDecl.visit(this, indent(arg));	
        stmt.initExpr.visit(this, indent(arg));
        return null;
	}

	/**
     * Print textual representation of an Assignment Statement
     * 
     * @param prog	the Assignment Statement
     * @param arg	the prefix String 
     */
	@Override
	public Object visitAssignStmt(AssignStmt stmt, String arg) {
		show(arg,stmt);
        stmt.ref.visit(this, indent(arg));
        stmt.val.visit(this, indent(arg));
        return null;
	}

	/**
     * Print textual representation of a Call Statement
     * 
     * @param prog	the Call Statement
     * @param arg	the prefix String 
     */
	@Override
	public Object visitCallStmt(CallStmt stmt, String arg) {
		show(arg, stmt);
        stmt.functionRef.visit(this, indent(arg));
        ExprList args = stmt.argList;
        show(arg, "  ExprList [" + args.size() + "]");
        String prefix = arg + "  . ";
        for (Expression argument: args)
            argument.visit(this, prefix);
        return null;
	}

	@Override
	public Object visitIfStmt(IfStmt stmt, String arg) {
		show(arg, stmt);
		
		stmt.condition.visit(this, indent(arg));
		stmt.body.visit(this, indent(arg));
		
		if (stmt.elseIfStmts != null)
			for (ElseIfStmt elseIfStmt : stmt.elseIfStmts)
				elseIfStmt.visit(this, indent(arg));
		
		if (stmt.elseStmt != null)
			stmt.elseStmt.visit(this, indent(arg));
		
		return null;
	}

	@Override
	public Object visitElseIfStmt(ElseIfStmt stmt, String arg) {
		show(arg, stmt);
	
		stmt.condition.visit(this, indent(arg));
		stmt.body.visit(this, indent(arg));
		
		return null;
	}

	@Override
	public Object visitElseStmt(ElseStmt stmt, String arg) {
		show(arg, stmt);
		
		stmt.body.visit(this, indent(arg));
		
		return null;
	}

	/**
     * Print textual representation of a While Statement
     * 
     * @param prog	the While Statement
     * @param arg	the prefix String 
     */
	@Override
	public Object visitWhileStmt(WhileStmt stmt, String arg) {
		show(arg, stmt);
        stmt.condition.visit(this, indent(arg));
        stmt.body.visit(this, indent(arg));
        return null;
	}

	/**
     * Print textual representation of a For Statement
     * 
     * @param prog	the For Statement
     * @param arg	the prefix String 
     */
	@Override
	public Object visitForStmt(ForStmt stmt, String arg) {
		show(arg, stmt);
		
		stmt.loopVar.visit(this, indent(arg));
		
		stmt.fromExpr.visit(this, indent(arg));
		stmt.toExpr.visit(this, indent(arg));
		stmt.byExpr.visit(this, indent(arg));
		
		stmt.body.visit(this, indent(arg));
		
		return null;
	}

	/**
     * Print textual representation of a For-Each Statement
     * 
     * @param prog	the For-Each Statement
     * @param arg	the prefix String 
     */
	@Override
	public Object visitForEachStmt(ForEachStmt stmt, String arg) {
		show(arg, stmt);
		
		stmt.loopVar.visit(this, indent(arg));
		stmt.collection.visit(this, indent(arg));
		stmt.body.visit(this, indent(arg));
		
		return null;
	}
	
	/**
     * Print textual representation of an Infinite Loop Statement
     * 
     * @param prog	the Infinite Loop Statement
     * @param arg	the prefix String 
     */
	@Override
	public Object visitInfiniteLoopStmt(InfiniteLoopStmt stmt, String arg) {
		show(arg, stmt);
		stmt.body.visit(this, indent(arg));
		return null;
	}
	
	/**
     * Print textual representation of an Until Statement
     * 
     * @param prog	the Until Statement
     * @param arg	the prefix String 
     */
	@Override
	public Object visitUntilStmt(UntilStmt stmt, String arg) {
		show(arg, stmt);
        stmt.condition.visit(this, indent(arg));
        stmt.body.visit(this, indent(arg));
        return null;
	}
	
	/**
     * Print textual representation of a Do-If Statement
     * 
     * @param prog	the Do-If Statement
     * @param arg	the prefix String 
     */
	@Override
	public Object visitDoIfStmt(DoIfStmt stmt, String arg) {
		show(arg, stmt);
		stmt.thenStmt.visit(this, indent(arg));
		stmt.condition.visit(this, indent(arg));
		stmt.elseStmt.visit(this, indent(arg));
		return null;
	}

	/**
     * Print textual representation of a Break Statement
     * 
     * @param prog	the Break Statement
     * @param arg	the prefix String 
     */
	@Override
	public Object visitBreakStmt(BreakStmt stmt, String arg) {
		show(arg, stmt);
		return null;
	}

	/**
     * Print textual representation of a Return Statement
     * 
     * @param prog	the Return Statement
     * @param arg	the prefix String 
     */
	@Override
	public Object visitReturnStmt(ReturnStmt stmt, String arg) {
		show(arg, stmt);
		if (stmt.returnExpr != null)
			stmt.returnExpr.visit(this, indent(arg));
		return null;
	}

	/**
     * Print textual representation of a Unary Expression
     * 
     * @param prog	the Unary Expression
     * @param arg	the prefix String 
     */
	@Override
	public Object visitUnaryExpr(UnaryExpr expr, String arg) {
		show(arg, expr);
        expr.operator.visit(this, indent(arg));
        expr.expr.visit(this, indent(indent(arg)));
        return null;
	}

	/**
     * Print textual representation of a Binary Expression
     * 
     * @param prog	the Binary Expression
     * @param arg	the prefix String 
     */
	@Override
	public Object visitBinaryExpr(BinaryExpr expr, String arg) {
		show(arg, expr);
        expr.operator.visit(this, indent(arg));
        expr.leftExpr.visit(this, indent(indent(arg)));
        expr.rightExpr.visit(this, indent(indent(arg)));
        return null;
	}

	/**
     * Print textual representation of a Reference Expression
     * 
     * @param prog	the Reference Expression
     * @param arg	the prefix String 
     */
	@Override
	public Object visitRefExpr(RefExpr expr, String arg) {
		show(arg, expr);
		expr.ref.visit(this, indent(arg));
		return null;
	}

	/**
     * Print textual representation of a Call Expression
     * 
     * @param prog	the Call Expression
     * @param arg	the prefix String 
     */
	@Override
	public Object visitCallExpr(CallExpr expr, String arg) {
		show(arg, expr);
		
        expr.functionRef.visit(this, indent(arg));
        
        ExprList args = expr.argList;
        show(arg, "  ExprList + [" + args.size() + "]");
        
        String prefix = arg + "  . ";
        
        for (Expression argument: args)
            argument.visit(this, prefix);
        
        return null;
	}

	/**
     * Print textual representation of a Literal Expression
     * 
     * @param prog	the Literal Expression
     * @param arg	the prefix String 
     */
	@Override
	public Object visitLiteralExpr(LiteralExpr expr, String arg) {
		show(arg, expr);
        expr.literal.visit(this, indent(arg));
        return null;
	}
	
	/**
     * Print textual representation of an IfExpr
     * 
     * @param prog	the If Expression
     * @param arg	the prefix String 
     */
	@Override
	public Object visitIfExpr(IfExpr expr, String arg) {
		show(arg, expr);
		
		expr.thenExpr.visit(this, indent(arg));
		expr.condition.visit(this, indent(arg));
		expr.elseExpr.visit(this, indent(arg));
		
		return null;
	}

	/**
     * Print textual representation of an Indexed Reference
     * 
     * @param prog	the Indexed Reference
     * @param arg	the prefix String 
     */
	@Override
	public Object visitIndexedRef(IndexedRef ref, String arg) {
		show(arg, ref);
    	ref.indexExpr.visit(this, indent(arg));
    	ref.ref.visit(this, indent(arg));
    	return null;
	}

	/**
     * Print textual representation of an Identifier Reference
     * 
     * @param prog	the Identifier Reference
     * @param arg	the prefix String 
     */
	@Override
	public Object visitIdRef(IdRef ref, String arg) {
		show(arg,ref);
    	ref.id.visit(this, indent(arg));
    	return null;
	}

	/**
     * Print textual representation of an Identifier
     * 
     * @param prog	the Identifier
     * @param arg	the prefix String 
     */
	@Override
	public Object visitIdentifier(Identifier id, String arg) {
		show(arg, quote(id.spelling) + " " + id.toString());
        return null;
	}

	/**
     * Print textual representation of an Operator
     * 
     * @param prog	the Operator
     * @param arg	the prefix String 
     */
	@Override
	public Object visitOperator(Operator op, String arg) {
		show(arg, quote(op.spelling) + " " + op.toString());
        return null;
	}

	/**
     * Print textual representation of an Integer Literal
     * 
     * @param prog	the Integer Literal
     * @param arg	the prefix String 
     */
	@Override
	public Object visitIntLiteral(IntLiteral num, String arg) {
		show(arg, quote(num.spelling) + " " + num.toString());
        return null;
	}

	/**
     * Print textual representation of a Boolean Literal
     * 
     * @param prog	the Boolean Literal
     * @param arg	the prefix String 
     */
	@Override
	public Object visitBooleanLiteral(BooleanLiteral bool, String arg) {
		show(arg, quote(bool.spelling) + " " + bool.toString());
        return null;  
	}

	/**
     * Print textual representation of a String Literal
     * 
     * @param prog	the String Literal
     * @param arg	the prefix String 
     */
	@Override
	public Object visitStringLiteral(StringLiteral str, String arg) {
		show(arg, quote(str.spelling) + " " + str.toString());
		return null;
	}
}
