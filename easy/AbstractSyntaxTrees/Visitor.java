/**
 * The Visitor interface for Abstract Syntax Tree classes.
 * <p>
 * An implementation of the Visitor interface provides a method visitX
 * for each <i>non-abstract</i> AST class X. 
 * 
 * @author  Federico Menozzi
 * @version Easy Compiler v1
 */

package easy.AbstractSyntaxTrees;

public interface Visitor<ArgType, ResultType> {

	// Program
	public ResultType visitProgram(Program prog, ArgType arg);

	// Declarations
	public ResultType visitStructDecl(StructDecl sd, ArgType arg);
	public ResultType visitFieldDecl(FieldDecl fd, ArgType arg);
	public ResultType visitFunctionDecl(FunctionDecl fd, ArgType arg);
	public ResultType visitParameterDecl(ParameterDecl pd, ArgType arg);
	public ResultType visitVarDecl(VarDecl decl, ArgType arg);

	// Types
	public ResultType visitBaseType(BaseType type, ArgType arg);
	public ResultType visitRefType(RefType type, ArgType arg);

	// Statements
	public ResultType visitBlockStmt(BlockStmt stmt, ArgType arg);
	public ResultType visitVardeclStmt(VarDeclStmt stmt, ArgType arg);
	public ResultType visitAssignStmt(AssignStmt stmt, ArgType arg);
	public ResultType visitCallStmt(CallStmt stmt, ArgType arg);
	public ResultType visitIfStmt(IfStmt stmt, ArgType arg);
	public ResultType visitElseIfStmt(ElseIfStmt stmt, ArgType arg);
	public ResultType visitElseStmt(ElseStmt stmt, ArgType arg);
	public ResultType visitWhileStmt(WhileStmt stmt, ArgType arg);
	public ResultType visitForStmt(ForStmt stmt, ArgType arg);
	public ResultType visitForEachStmt(ForEachStmt stmt, ArgType arg);
	public ResultType visitBreakStmt(BreakStmt stmt, ArgType arg);
	public ResultType visitReturnStmt(ReturnStmt stmt, ArgType arg);
	public ResultType visitJavaBlockStmt(JavaBlockStmt stmt, ArgType arg);

	// Expressions
	public ResultType visitUnaryExpr(UnaryExpr expr, ArgType arg);
	public ResultType visitBinaryExpr(BinaryExpr expr, ArgType arg);
	public ResultType visitRefExpr(RefExpr expr, ArgType arg);
	public ResultType visitCallExpr(CallExpr expr, ArgType arg);
	public ResultType visitLiteralExpr(LiteralExpr expr, ArgType arg);
	public ResultType visitNewObjectExpr(NewObjectExpr expr, ArgType arg);

	// References
	public ResultType visitQualifiedRef(QualifiedRef ref, ArgType arg);
	public ResultType visitIndexedRef(IndexedRef ref, ArgType arg);
	public ResultType visitIdRef(IdRef ref, ArgType arg);

	// Terminals
	public ResultType visitIdentifier(Identifier id, ArgType arg);
	public ResultType visitOperator(Operator op, ArgType arg);
	public ResultType visitIntLiteral(IntLiteral num, ArgType arg);
	public ResultType visitFloatLiteral(FloatLiteral num, ArgType arg);
	public ResultType visitBooleanLiteral(BooleanLiteral bool, ArgType arg);
	public ResultType visitReferenceLiteral(ReferenceLiteral reflit, ArgType arg);
	public ResultType visitStringLiteral(StringLiteral str, ArgType arg);
}
