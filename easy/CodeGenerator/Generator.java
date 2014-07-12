/**
 * Generate a .java file that is semantically equivalent to 
 * the .easy source file
 */

package easy.CodeGenerator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import easy.AbstractSyntaxTrees.*;

// TODO Add documentation!

public class Generator implements Visitor<String, Object> {
	
	/**
	 * Writes to output .java file
	 */
	private BufferedWriter writer;
	
	/**
	 * Name of source file up to the first dot
	 */
	private String filename;
	
	/**
	 * Create file for writing
	 * 
	 * @param file	the output .java file
	 */
	public Generator(File file) {
		try {
			writer = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
			filename = file.getName().substring(0, file.getName().indexOf("."));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Write the content to the file
	 * 
	 * @param content	the String to write to the file
	 */
	private void write(String content) {
		try {
			writer.write(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Write the content to the file and add
	 * a newline.
	 * 
	 * @param content	the String to write to the file
	 */
	private void writeln(String content) {
		try {
			writer.write(content);
			writer.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Tab over
	 * 
	 * @param prefix	the String preceding the tab
	 * @return			the prefix with an added tab
	 */
	private String tab(String prefix) {
        return prefix + "\t";
    }
	
	/**
	 * Begin writing to file
	 * 
	 * @param ast	the root AST node
	 */
	public void generate(AST ast) {
		try {
			ast.visit(this, "");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Write entire program to file
	 * 
	 * @param prog	the 
	 */
	@Override
	public Object visitProgram(Program prog, String arg) {
		writeln("class " + filename + " {\n");
		
		writeln(tab(arg) + "public static void main(String[] args) {");
		prog.mainBlock.visit(this, tab(tab(arg)));
		writeln(tab(arg) + "}\n");
		
		for (FunctionDecl function : prog.functionDeclList)
			function.visit(this, tab(arg));

		writeln("}");
		
		return null;
	}

	@Override
	public Object visitFunctionDecl(FunctionDecl fd, String arg) {
		write(arg + "public static " + fd.type.spelling + " " + fd.name + "(");
		for (int i = 0; i < fd.params.size(); i++) {
			write(((String) fd.params.get(i).visit(this, "")));
			if (i < fd.params.size() - 1)
				write(", ");
		}
		writeln(") {");
		
		for (Statement statement : fd.block.statementList)
			statement.visit(this, tab(arg));
		
		writeln(arg + "}\n");
		
		return null;
	}

	@Override
	public Object visitParameterDecl(ParameterDecl pd, String arg) {
		return new String(pd.type.spelling + " " + pd.name);
	}

	@Override
	public Object visitVarDecl(VarDecl decl, String arg) {
		return new String(decl.type.spelling + " " + decl.name);
	}

	@Override
	public Object visitBaseType(BaseType type, String arg) {
		write(type.spelling);
		return null;
	}

	@Override
	public Object visitBlockStmt(BlockStmt stmt, String arg) {
		for (Statement statement : stmt.statementList)
			statement.visit(this, arg);
		return null;
	}

	@Override
	public Object visitVardeclStmt(VarDeclStmt stmt, String arg) {
		write(arg + ((String) stmt.varDecl.visit(this, arg)));
		write(" = ");
		stmt.initExpr.visit(this, "");
		writeln(";");
		return null;
	}

	@Override
	public Object visitAssignStmt(AssignStmt stmt, String arg) {
		write(arg);
		stmt.ref.visit(this, "");
		write(" = ");
		stmt.val.visit(this, "");
		writeln(";");
		
		return null;
	}

	@Override
	public Object visitCallStmt(CallStmt stmt, String arg) {
		if (stmt.functionRef.spelling.equals("println")) {
			write(arg + "System.out.println(");
			for (Expression argument : stmt.argList)
				argument.visit(this, "");
			writeln(");");
		} else if (stmt.functionRef.spelling.equals("print")) {
			write(arg + "System.out.print(");
			for (Expression argument : stmt.argList)
				argument.visit(this, "");
			writeln(");");
		} else {
			write(arg + stmt.functionRef.spelling + "(");
			for (int i = 0; i < stmt.argList.size(); i++) {
				stmt.argList.get(i).visit(this, "");
				if (i < stmt.argList.size() - 1)
					write(", ");
			}
			writeln(");");
		}
		
		return null;
	}

	@Override
	public Object visitIfStmt(IfStmt stmt, String arg) {
		write(arg + "if (");
		stmt.condition.visit(this, "");
		writeln(") {");
		stmt.body.visit(this, tab(arg));
		write(arg + "} ");
		
		if (stmt.elseIfStmts != null)
			for (ElseIfStmt elseIf : stmt.elseIfStmts)
				elseIf.visit(this, arg);
		
		if (stmt.elseStmt != null)
			stmt.elseStmt.visit(this, arg);
		else
			writeln("");
				
		return null;
	}

	@Override
	public Object visitElseIfStmt(ElseIfStmt stmt, String arg) {
		write("else if (");
		stmt.condition.visit(this, "");
		writeln(") {");
		stmt.body.visit(this, tab(arg));
		write(arg + "} ");
		
		return null;
	}

	@Override
	public Object visitElseStmt(ElseStmt stmt, String arg) {
		writeln("else {");
		stmt.body.visit(this, tab(arg));
		writeln(arg + "}");
		
		return null;
	}

	@Override
	public Object visitWhileStmt(WhileStmt stmt, String arg) {
		write(arg + "while (");
		stmt.condition.visit(this, "");
		writeln(") {");
		
		stmt.body.visit(this, tab(arg));
		
		writeln(arg + "}");
		
		return null;
	}

	@Override
	public Object visitForStmt(ForStmt stmt, String arg) {
		write(arg + "for (" + stmt.loopVarType.spelling + " " + stmt.loopVar.spelling + " = ");
		stmt.fromExpr.visit(this, "");
		write("; ");
		
		write(stmt.loopVar.spelling + " <= ");	// TODO Does not allow for counting DOWN!
		stmt.toExpr.visit(this, "");
		write("; ");
		
		write(stmt.loopVar.spelling + " += ");
		stmt.byExpr.visit(this, "");
		
		writeln(") {");
		
		stmt.body.visit(this, tab(arg));
		
		writeln(arg + "}");
		
		return null;
	}

	@Override
	public Object visitForEachStmt(ForEachStmt stmt, String arg) {
		// TODO Implement me!
		return null;
	}
	
	@Override
	public Object visitInfiniteLoopStmt(InfiniteLoopStmt stmt, String arg) {
		writeln(arg + "while (true) {");
		
		stmt.body.visit(this, tab(arg));
		
		writeln(arg + "}");
		
		return null;
	}
	
	@Override
	public Object visitUntilStmt(UntilStmt stmt, String arg) {
		write(arg + "while (!(");
		stmt.condition.visit(this, "");
		writeln(")) {");
		
		stmt.body.visit(this, tab(arg));
		
		writeln(arg + "}");
		
		return null;
	}
	
	@Override
	public Object visitDoIfStmt(DoIfStmt stmt, String arg) {
		write(arg + "if (");
		stmt.condition.visit(this, "");
		writeln(") { ");
		stmt.thenStmt.visit(this, tab(arg));
		writeln(arg + "} else { ");
		stmt.elseStmt.visit(this, tab(arg));
		writeln(arg + "}");
		return null;
	}

	@Override
	public Object visitBreakStmt(BreakStmt stmt, String arg) {
		writeln(arg + "break;");
		return null;
	}

	@Override
	public Object visitReturnStmt(ReturnStmt stmt, String arg) {
		write(arg + "return ");
		stmt.returnExpr.visit(this, "");
		writeln(";");
		return null;
	}

	@Override
	public Object visitUnaryExpr(UnaryExpr expr, String arg) {
		write(arg + "(");
		expr.operator.visit(this, "");
		expr.expr.visit(this, "");
		write(")");
		
		return null;
	}

	@Override
	public Object visitBinaryExpr(BinaryExpr expr, String arg) {
		write(arg + "(");
		expr.leftExpr.visit(this, "");
		expr.operator.visit(this, "");
		expr.rightExpr.visit(this, "");
		write(")");
		
		return null;
	}

	@Override
	public Object visitRefExpr(RefExpr expr, String arg) {
		expr.ref.visit(this, arg);
		return null;
	}

	@Override
	public Object visitCallExpr(CallExpr expr, String arg) {
		if (expr.functionRef.spelling.equals("sqrt"))
			write(arg + "Math.sqrt(");
		else
			write(arg + expr.functionRef.spelling + "(");
		
		for (int i = 0; i < expr.argList.size(); i++) {
			expr.argList.get(i).visit(this, "");
			if (i < expr.argList.size() - 1)
				write(", ");
		}
		write(")");
		return null;
	}

	@Override
	public Object visitLiteralExpr(LiteralExpr expr, String arg) {
		expr.literal.visit(this, arg);
		return null;
	}
	
	@Override
	public Object visitIfExpr(IfExpr expr, String arg) {
		write(arg + "(");
		expr.condition.visit(this, "");
		write(" ? ");
		expr.thenExpr.visit(this, "");
		write(" : ");
		expr.elseExpr.visit(this, "");
		write(")");
		return null;
	}

	@Override
	public Object visitIndexedRef(IndexedRef ref, String arg) {
		write(arg + ref.spelling);
		return null;
	}

	@Override
	public Object visitIdRef(IdRef ref, String arg) {
		ref.id.visit(this, arg);
		return null;
	}

	@Override
	public Object visitIdentifier(Identifier id, String arg) {
		write(arg + id.spelling);
		return null;
	}

	@Override
	public Object visitOperator(Operator op, String arg) {
		String spelling = op.spelling;
		if (spelling.equals("and"))
			spelling = "&&";
		else if (spelling.equals("or"))
			spelling = "||";
		else if (spelling.equals("not"))
			spelling = "!";
		else if (spelling.equals("equals"))
			spelling = "==";
		else if (spelling.equals("notequals"))
			spelling = "!=";
		else if (spelling.equals("mod"))
			spelling = "%";
		
		write(arg + spelling);
		
		return null;
	}

	@Override
	public Object visitIntLiteral(IntLiteral num, String arg) {
		write(arg + num.spelling);
		return null;
	}

	@Override
	public Object visitBooleanLiteral(BooleanLiteral bool, String arg) {
		write(arg + bool.spelling);
		return null;
	}

	@Override
	public Object visitStringLiteral(StringLiteral str, String arg) {
		write(arg + "\""+str.spelling+"\"");
		return null;
	}
}
