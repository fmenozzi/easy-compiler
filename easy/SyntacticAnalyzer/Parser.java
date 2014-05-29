package easy.SyntacticAnalyzer;

import java.util.ArrayList;

import easy.AbstractSyntaxTrees.*;

import easy.SyntacticAnalyzer.Scanner;
import easy.SyntacticAnalyzer.Token;

import easy.ErrorReporter;

public class Parser {

	/**
	 * The Scanner from whose token stream we synthesize the AST
	 */
	private Scanner scanner;
	
	/**
	 * Used in reporting errors in scanning and parsing
	 */
	private ErrorReporter reporter;
	
	/**
	 * The current token in the token stream
	 */
	private Token token;
	
	/**
	 * Used to unwind parse stack when parse fails
	 */
    class SyntaxError extends Error {
		private static final long serialVersionUID = 1L;	
    }
	
	/**
	 * Constructor that sets scanner and reporter
	 * 
	 * @param scanner	the Scanner from whose token stream we synthesize the AST
	 * @param reporter	the ErrorReporter used to report errors in scanning and parsing
	 */
	public Parser(Scanner scanner, ErrorReporter reporter) {
		this.scanner  = scanner;
		this.reporter = reporter;
	}
	
	/**
	 * Verify that current input token matches expected token in type,
	 * advancing to the next token if it does and throwing a SyntaxError
	 * if it does not.
	 * 
	 * @param expectedType	the expected token {@link TokenKind} 
	 * @throws SyntaxError	if expected token mismatches actual token
	 */
	private void accept(TokenKind expectedKind) throws SyntaxError {
		if (token.kind == expectedKind)
			token = scanner.scan();
		else 
			parseError("At line " + token.line + ": Expecting " + expectedKind + " but found " + token.kind);
	}
	
	/**
	 * Verify that current input token matches expected token in type
	 * AND spelling, advancing to the next token if it does and throwing
	 * a SyntaxError if it does not.
	 * 
	 * @param expectedType		the expected token {@code TokenKind}
	 * @param expectedSpelling	the expected token spelling
	 * @throws SyntaxError		if expected token mismatches actual token
	 */
	private void accept(TokenKind expectedKind, String expectedSpelling) throws SyntaxError {
		if (token.kind == expectedKind && token.spelling.equals(expectedSpelling)) {
			token = scanner.scan();
		} else {
			Token t = new Token(expectedKind, expectedSpelling, new Line(scanner.lineNumber()));
			parseError("At line " + token.line + ": Expecting \"" + t + "\" but found \"" + token + "\"");
		}
	}
	
	/**
	 * Accept current token
	 */
	private void acceptIt() {
    	accept(token.kind, token.spelling);
    }
	
	/**
	 * Report parse error and unwind parse stack to start of parse
	 * 
	 * @param errorMessage	the error message
	 * @throws SyntaxError
	 */
	private void parseError(String errorMessage) throws SyntaxError {
        reporter.addParseError(errorMessage);
        throw new SyntaxError();
    }
	
	/**
	 * Begin parse
	 * 
	 * @return				the AST of the source program
	 * @throws SyntaxError	if expected token mismatches actual token
	 */
	public AST parse() {
    	token = scanner.scan();
    	try {
    		return parseProgram();
    	} catch (SyntaxError e) { 
    		return null;
    	}
    }
	
	/**
	 * Program ::= (functionDecl | structDecl)* mainBlock (functionDecl | structDecl)* EOF
	 * 
	 * @return				the AST of the program
	 * @throws SyntaxError	if expected token mismatches actual token
	 */
	private Program parseProgram() throws SyntaxError {
		FunctionDeclList fdl = new FunctionDeclList();
		StructDeclList sdl   = new StructDeclList();
		BlockStmt mainBlock  = null;
		
		while (! token.spelling.equals("main")) {
			if (token.spelling.equals("function")) {
				fdl.add(parseFunctionDeclaration());
			} else if (token.spelling.equals("struct")) {
				sdl.add(parseStructDeclaration());
			} else {
				parseError("At line "+token.line+": Unrecognized token");
			}
			
			if (token.kind == TokenKind.EOF)
				parseError("At line "+token.line+": Missing main block");
		}
		
		Line mainLine = token.line;
		
		mainBlock = parseMainBlock();
		
		while (token.kind != TokenKind.EOF) {
			if (token.spelling.equals("function")) {
				fdl.add(parseFunctionDeclaration());
			} else if (token.spelling.equals("struct")) {
				sdl.add(parseStructDeclaration());
			} else {
				parseError("At line "+token.line+": Unrecognized token");
			}
		}
		
		accept(TokenKind.EOF);
		
		return new Program(mainBlock, fdl, sdl, mainLine);
	}
	
	/**
	 * MainBlock ::= main Statement end
	 * 
	 * @return				the AST of the Main Block
	 * @throws SyntaxError	if expected token mismatches actual token
	 */
	private BlockStmt parseMainBlock() throws SyntaxError {
		accept(TokenKind.KEYWORD, "main");
		
		StatementList mainBlockStatements = new StatementList();
		while (! token.spelling.equals("end"))
			mainBlockStatements.add(parseStatement());
		
		accept(TokenKind.KEYWORD, "end");
		
		return new BlockStmt(mainBlockStatements, mainBlockStatements.get(0).line);
	}
	
	/**
	 * FunctionDecl ::= function (Type =)? id( ParamList? ) Statement end
	 * <p>
	 * FunctionDecl ::= function ((int | float | boolean | id) =)? id( ParamList? ) Statement end
	 * 
	 * @return				the AST of the Function Declarations
	 * @throws SyntaxError	if expected token mismatches actual token
	 */
	
	private FunctionDecl parseFunctionDeclaration() throws SyntaxError {
		
		// Default values for function
		String functionName		 = "";
		Type returnType 		 = new BaseType(TypeKind.VOID, new Line(scanner.lineNumber()));
		ParameterDeclList params = new ParameterDeclList();
		StatementList body 		 = new StatementList();
		
		Line functionLine = new Line(scanner.lineNumber());
		accept(TokenKind.KEYWORD, "function");			// function
		
		if (token.spelling.equals("int")   ||
			token.spelling.equals("float") ||
			token.spelling.equals("boolean")) {			// int | float | boolean
			
			String spelling = token.spelling;
			if (spelling.equals("int"))
				returnType = new BaseType(TypeKind.INT, returnType.line);
			else if (spelling.equals("float"))
				returnType = new BaseType(TypeKind.FLOAT, returnType.line);
			else
				returnType = new BaseType(TypeKind.BOOLEAN, returnType.line);
			
			acceptIt();
			accept(TokenKind.ASSIGN);					// =
			
			functionName = token.spelling;
			
			accept(TokenKind.IDEN);						// id
		} else {	// id
			Identifier id = new Identifier(token.spelling, token.line);
			
			acceptIt();									// id
			
			if (token.kind == TokenKind.ASSIGN) {
				acceptIt();								// =
				returnType = new RefType(id, id.line);
				
				functionName = token.spelling;
				accept(TokenKind.IDEN);
			} else {
				functionName = id.spelling;
			}
		}
		
		accept(TokenKind.LPAREN);						// (
		if (token.kind != TokenKind.RPAREN)
			params = parseParameterList();				// ParamList?
		accept(TokenKind.RPAREN);						// )
		
		Line blockLine = token.line;
		while (! token.spelling.equals("end"))
			body.add(parseStatement());					// Statement
		
		accept(TokenKind.KEYWORD, "end");				// end
		
		return new FunctionDecl(functionName, returnType, params, new BlockStmt(body, blockLine), functionLine);
	}
	
	/**
	 * StructDecl ::= struct id FieldDecl FieldDecl* end
	 * 
	 * @return				the AST of the Structure Declaration
	 * @throws SyntaxError	if expected token mismatches actual token
	 */
	private StructDecl parseStructDeclaration() throws SyntaxError {
		Line structLine = new Line(scanner.lineNumber());
		accept(TokenKind.KEYWORD, "struct");			// struct
		
		String structName = token.spelling;
		accept(TokenKind.IDEN);							// id
		
		FieldDeclList fields = new FieldDeclList();
		fields.add(parseFieldDecl());					// FieldDecl
		while (! token.spelling.equals("end"))
			fields.add(parseFieldDecl());				// FieldDecl*
		
		accept(TokenKind.KEYWORD, "end");				// end
						
		return new StructDecl(structName, fields, structLine);
	}
	
	/**
	 * FieldDecl ::= Type id;
	 * 
	 * @return				the AST of the Field Declaration
	 * @throws SyntaxError	if expected token mismatches actual token
	 */
	private FieldDecl parseFieldDecl() throws SyntaxError {
		Type fieldType = parseType();		// Type
			
		String fieldName = token.spelling;	
		Line fieldLine   = token.line;
		
		accept(TokenKind.IDEN);				// id
		
		accept(TokenKind.SEMICOL);			// ;
		
		boolean isPrivate = false;
		boolean isStatic  = false;
		
		return new FieldDecl(isPrivate, isStatic, fieldType, fieldName, fieldLine);
	}
	
	/**
	 * Type ::= PrimType | RefType
	 * <p>
	 * Type ::= int | float | boolean | void | id
	 * 
	 * @return				the AST of the Type
	 * @throws SyntaxError	if expected token mismatches actual token
	 */
	private Type parseType() throws SyntaxError {
		String typeSpelling = token.spelling;
		Line typeLine       = token.line;
		
		TypeKind typeKind   = null;
		
		if      (typeSpelling.equals("int")) 		typeKind = TypeKind.INT;
		else if (typeSpelling.equals("float"))		typeKind = TypeKind.FLOAT;
		else if (typeSpelling.equals("boolean"))	typeKind = TypeKind.BOOLEAN;
		else if (typeSpelling.equals("void"))		typeKind = TypeKind.VOID;
		else /* id */								typeKind = TypeKind.REF;
		
		acceptIt();	
		
		if (typeKind == TypeKind.REF)
			return new RefType(new Identifier(typeSpelling, typeLine), typeLine);
		else
			return new BaseType(typeKind, typeLine);
	}
	
	/**
	 * ParameterList ::= Type id (, Type id)*
	 * 
	 * @return				the AST of the Parameter List
	 * @throws SyntaxError	if expected token mismatches actual token
	 */
    private ParameterDeclList parseParameterList() throws SyntaxError {
    	ParameterDeclList pdl = new ParameterDeclList();
    	
    	Type varType 	= parseType();		// Type
    	String varName 	= token.spelling;
    	
    	accept(TokenKind.IDEN);				// id
    	
    	pdl.add(new ParameterDecl(varType, varName, new Line(scanner.lineNumber())));
    	
    	while (token.kind == TokenKind.COMMA) {
    		accept(TokenKind.COMMA);		// ,
    		
    		varType = parseType();			// Type
        	varName = token.spelling;	
        	accept(TokenKind.IDEN);			// id
    		
    		pdl.add(new ParameterDecl(varType, varName, new Line(scanner.lineNumber())));
    	}
    	
    	return pdl;
    }
    
    /**
	 * ArgumentList ::= Expression (, Expression)*
	 * 
	 * @return				the AST of the Argument List
	 * @throws SyntaxError	if expected token mismatches actual token
	 */
    private ExprList parseArgumentList() throws SyntaxError {
    	ExprList args = new ExprList();
    	args.add(parseExpression());				// Expression
    	while (token.kind == TokenKind.COMMA) {
    		accept(TokenKind.COMMA);				// ,
    		args.add(parseExpression());			// Expression
    	}
    	return args;
    }
    
    /**
	 * Reference ::= BaseRef (.BaseRef)*
	 * 
	 * @return				the AST of the Reference
	 * @throws SyntaxError	if expected token mismatches actual token
	 */
    
    // TODO May need to check back and adjust the creation of Qualified vs. Indexed references
    
    private Reference parseReference() throws SyntaxError {
    	Reference ref = parseBaseRef();
    	
    	while (token.kind == TokenKind.DOT) {
    		acceptIt();
    		
    		Identifier id = new Identifier(token.spelling, token.line);
    		accept(TokenKind.IDEN);
    		
      		ref = new QualifiedRef(ref, id, id.line);
    	}
    	
    	return ref;
    }
    
    /**
	 * BaseRef ::= id | id[Expression]
	 * 
	 * @return				the AST of the Reference
	 * @throws SyntaxError	if expected token mismatches actual token
	 */
    
    // TODO Should this include id( ArgList? ) also?
    
    private Reference parseBaseRef() throws SyntaxError {
    	IdRef idRef = new IdRef(new Identifier(token.spelling, token.line), token.line);
    	
    	accept(TokenKind.IDEN);							// id
    	
    	if (token.kind == TokenKind.LBRACKET) {
    		acceptIt();									// [
    		Expression indexExpr = parseExpression();	// Expression
    		accept(TokenKind.RBRACKET);					// ]
    		
    		return new IndexedRef(idRef, indexExpr, idRef.line);
    	} else {
    		return idRef;
    	}
    }
    
    /**
	 * Statement ::= <p> 
	 * 				Type id = Expression; <p> 
	 * 				Reference = Expression; <p> 
	 * 				Reference( ArgList? ); <p> 
	 * 				if Expr Statement (else if cond Statement)* (else Statement)? end <p> 
	 * 				while Expression Statement end <p> 
	 * 				return Expression; <p>
	 * 				java JavaStmt JavaStmt* end
	 * 
	 * @return				the AST of the Reference
	 * @throws SyntaxError	if expected token mismatches actual token
	 */
    
    private Statement parseStatement() throws SyntaxError {
    	if (token.kind == TokenKind.KEYWORD) {
    		if (token.spelling.equals("if")) {	
    			Line ifLine = token.line;
    			
    			acceptIt();
    			Expression ifCondition = parseExpression();		

    			StatementList ifBody = new StatementList();
    			Line blockLine = token.line;
    			while (! token.spelling.equals("end") && 
    				   ! token.spelling.equals("else"))
    				ifBody.add(parseStatement());					

    			ArrayList<ElseIfStmt> elseIfStmts = new ArrayList<ElseIfStmt>();
    			ElseStmt elseStmt				  = null;
    			
    			// TODO FINISH!
    			while (token.spelling.equals("else")) {
    				Line elseLine = token.line;
    				acceptIt();
    				
    				if (token.spelling.equals("if")) {
    					acceptIt();
    					
    					Expression elseIfCondition = parseExpression();
    					
    					StatementList elseIfBody = new StatementList();
    	    			Line elseIfBlockLine = token.line;
    	    			while (! token.spelling.equals("end") && 
    	    				   ! token.spelling.equals("else"))
    	    				elseIfBody.add(parseStatement());	
    	    			
    	    			elseIfStmts.add(new ElseIfStmt(elseIfCondition, 
    	    										   new BlockStmt(elseIfBody, elseIfBlockLine), 
    	    										   elseLine));
    				} else {
    					StatementList elseBody = new StatementList();
    	    			Line elseBlockLine = token.line;
    	    			while (! token.spelling.equals("end") && 
    	    				   ! token.spelling.equals("else"))
    	    				elseBody.add(parseStatement());
    	    			
    	    			elseStmt = new ElseStmt(new BlockStmt(elseBody, elseBlockLine), elseLine);
    	    			break;
    				}
    			}
    			
    			accept(TokenKind.KEYWORD, "end");
    			
    			return new IfStmt(ifCondition, new BlockStmt(ifBody, blockLine), elseIfStmts, elseStmt, ifLine);
    		} else if (token.spelling.equals("while")) {
    			Line whileLine = token.line;

    			acceptIt();									// while
    			Expression condition = parseExpression();	// Expression
    			StatementList body = new StatementList();

    			Line blockLine = token.line;
    			while (! token.spelling.equals("end"))
    				body.add(parseStatement());				// Statement
    			accept(TokenKind.KEYWORD, "end");			// end

    			return new WhileStmt(condition, new BlockStmt(body, blockLine), whileLine);

    		} else if (token.spelling.equals("int")  ||
    				token.spelling.equals("float")   ||
    				token.spelling.equals("boolean") ||
    				token.spelling.equals("void")) {

    			VarDecl decl = new VarDecl(parseType(), token.spelling, token.line);

    			accept(TokenKind.IDEN);
    			accept(TokenKind.ASSIGN);

    			Expression expr = parseExpression();

    			accept(TokenKind.SEMICOL);

    			return new VarDeclStmt(decl, expr, decl.line);
    		} else if (token.spelling.equals("return")) {
    			Line returnLine = token.line;
    			acceptIt();
    			if (token.kind == TokenKind.SEMICOL) {
    				acceptIt();
    				return new ReturnStmt(null, returnLine);
    			} else {
    				Expression returnExpr = parseExpression();
    				accept(TokenKind.SEMICOL);
    				return new ReturnStmt(returnExpr, returnLine);
    			}
    		} else if (token.spelling.equals("for")) {
    			Line forLine = token.line;
    			acceptIt();
    			
    			Type iterType 	  = parseType();
				Identifier iterId = new Identifier(token.spelling, token.line);
				
				accept(TokenKind.IDEN);
    			
    			if (token.spelling.equals("from")) {	// ForStmt    				
    				accept(TokenKind.KEYWORD, "from");
    				Expression fromExpr = parseExpression();
    				
    				accept(TokenKind.KEYWORD, "to");
    				Expression toExpr = parseExpression();
    				
    				Expression byExpr = new LiteralExpr(new IntLiteral("1", token.line), token.line);
    				if (token.spelling.equals("by")) {
    					acceptIt();
    					byExpr = parseExpression();
    				}
    				
    				StatementList body = new StatementList();
        			Line blockLine = token.line;
        			while (! token.spelling.equals("end"))
        				body.add(parseStatement());				
        			accept(TokenKind.KEYWORD, "end");
        			
        			return new ForStmt(iterId, iterType, fromExpr, toExpr, 
        							   byExpr, new BlockStmt(body, blockLine), forLine);	
    			} else {								// ForEachStmt
    				accept(TokenKind.KEYWORD, "in");
    				Expression collection = parseExpression();
    				
    				StatementList body = new StatementList();
        			Line blockLine = token.line;
        			while (! token.spelling.equals("end"))
        				body.add(parseStatement());				
        			accept(TokenKind.KEYWORD, "end");
    				
    				return new ForEachStmt(iterId, iterType, collection, new BlockStmt(body, blockLine), forLine);
    			}
    		} else if (token.spelling.equals("break")) {
    			Line breakLine = token.line;
    			acceptIt();
    			accept(TokenKind.SEMICOL);
    			return new BreakStmt(breakLine);
    		} else {	// "java"
    			Line javaLine = token.line;

    			acceptIt();												// java

    			ArrayList<StringLiteral> javaStmts = new ArrayList<StringLiteral>();

    			javaStmts.add(new StringLiteral(token.spelling, token.line));
    			accept(TokenKind.STRLIT);								// JavaStmt

    			while (! token.spelling.equals("end")) {
    				javaStmts.add(new StringLiteral(token.spelling, token.line));
    				accept(TokenKind.STRLIT);							// JavaStmt*
    			}

    			accept(TokenKind.KEYWORD, "end");						// end

    			return new JavaBlockStmt(javaStmts, javaLine);
    		}
    	} else {	// id
    		Identifier typeId = new Identifier(token.spelling, new Line(scanner.lineNumber()));

    		accept(TokenKind.IDEN);
    		if (token.kind == TokenKind.IDEN) {				// id id = Expression;
    			String varName 	= token.spelling;
    			Type varType 	= new RefType(typeId, new Line(scanner.lineNumber()));
    			VarDecl decl	= new VarDecl(varType, varName, new Line(scanner.lineNumber()));

    			accept(TokenKind.IDEN);
    			accept(TokenKind.ASSIGN);

    			Expression expr = parseExpression();

    			accept(TokenKind.SEMICOL);

    			return new VarDeclStmt(decl, expr, new Line(scanner.lineNumber()));
    		} else if (token.kind == TokenKind.LBRACKET) { // id[Expr] (.id([Expr])?)* (= Expr; | (ArgList?);)
    			accept(TokenKind.LBRACKET);

    			IdRef idRef 	= new IdRef(typeId, new Line(scanner.lineNumber()));
    			Reference ref 	= new IndexedRef(idRef, parseExpression(), new Line(scanner.lineNumber()));

    			accept(TokenKind.RBRACKET);
    			while (token.kind == TokenKind.DOT) {
    				acceptIt();

    				Identifier id = new Identifier(token.spelling, new Line(scanner.lineNumber()));
    				accept(TokenKind.IDEN);
    				if (token.kind == TokenKind.LBRACKET) {
    					acceptIt();
    					Expression expr = parseExpression();
    					accept(TokenKind.RBRACKET);

    					ref = new QualifiedRef(ref, id, new Line(scanner.lineNumber()));
    					ref = new IndexedRef(ref, expr, new Line(scanner.lineNumber()));
    				} else {
    					ref = new QualifiedRef(ref, id, new Line(scanner.lineNumber()));
    				}
    			}

    			if (token.kind == TokenKind.ASSIGN) {
    				acceptIt();
    				Expression expr = parseExpression();
    				accept(TokenKind.SEMICOL);

    				return new AssignStmt(ref, expr, new Line(scanner.lineNumber()));
    			} else {
    				ExprList args = new ExprList();

    				accept(TokenKind.LPAREN);
    				if (token.kind != TokenKind.RPAREN)
    					args = parseArgumentList();
    				accept(TokenKind.RPAREN);

    				accept(TokenKind.SEMICOL);

    				return new CallStmt(ref, args, new Line(scanner.lineNumber()));
    			}
    		} else if (token.kind == TokenKind.DOT) { // id . (...I think) 
    			Reference ref = new IdRef(typeId, new Line(scanner.lineNumber()));

    			while (token.kind == TokenKind.DOT) {
    				accept(TokenKind.DOT);

    				Identifier id = new Identifier(token.spelling, new Line(scanner.lineNumber()));
    				accept(TokenKind.IDEN);

    				if (token.kind == TokenKind.LBRACKET) {
    					accept(TokenKind.LBRACKET);
    					Expression expr = parseExpression();
    					accept(TokenKind.RBRACKET);

    					ref = new QualifiedRef(ref, id, new Line(scanner.lineNumber()));
    					ref = new IndexedRef(ref, expr, new Line(scanner.lineNumber()));
    				} else {
    					ref = new QualifiedRef(ref, id, new Line(scanner.lineNumber()));
    				}
    			}

    			if (token.kind == TokenKind.ASSIGN) { // AssignStmt
    				accept(TokenKind.ASSIGN);
    				Expression expr = parseExpression();
    				accept(TokenKind.SEMICOL);

    				return new AssignStmt(ref, expr, new Line(scanner.lineNumber()));
    			} else {								// CallStmt
    				ExprList args = new ExprList();

    				accept(TokenKind.LPAREN);
    				if (token.kind != TokenKind.RPAREN)
    					args = parseArgumentList();
    				accept(TokenKind.RPAREN);

    				accept(TokenKind.SEMICOL);

    				return new CallStmt(ref, args, new Line(scanner.lineNumber()));
    			}
    		} else if (token.kind == TokenKind.ASSIGN) {
    			accept(TokenKind.ASSIGN);
    			Expression expr = parseExpression();
    			accept(TokenKind.SEMICOL);

    			Reference ref = new IdRef(typeId, new Line(scanner.lineNumber()));
    			return new AssignStmt(ref, expr, new Line(scanner.lineNumber()));
    		} else {
    			ExprList args = new ExprList();

    			accept(TokenKind.LPAREN);
    			if (token.kind != TokenKind.RPAREN)
    				args = parseArgumentList();
    			accept(TokenKind.RPAREN);

    			accept(TokenKind.SEMICOL);

    			Reference ref = new IdRef(typeId, new Line(scanner.lineNumber()));
    			return new CallStmt(ref, args, new Line(scanner.lineNumber()));
    		}
    	}
    }
    
    /*
     * Expression ::= A (|| A)*
     * A ::= B (&& B)*
     * B ::= C (== C | != C)*
     * C ::= D (< D | > D | <= D | >= D)*
     * D ::= E (+ E | - E)*
     * E ::= F (* F | / F | % F)*
     * F ::= num | string | true | false | null | (Expr) | -F | !F | new id( ArgList? )
     */
    
    private Expression parseExpression() {
    	Expression expr = parseA();
    	while (token.spelling.equals("or") || token.spelling.equals("||")) {
    		Operator op = new Operator(token, new Line(scanner.lineNumber()));
    		acceptIt();
    		expr = new BinaryExpr(expr, op, parseA(), new Line(scanner.lineNumber()));
    	}
    	return expr;
    }
    
    private Expression parseA() {
    	Expression expr = parseB();
    	while (token.spelling.equals("and") || token.spelling.equals("&&")) {
    		Operator op = new Operator(token, new Line(scanner.lineNumber()));
    		acceptIt();
    		expr = new BinaryExpr(expr, op, parseB(), new Line(scanner.lineNumber()));
    	}
    	return expr;
    }
    
    private Expression parseB() {
    	Expression expr = parseC();
    	while (token.spelling.equals("==") || token.spelling.equals("equals") ||
    		   token.spelling.equals("!=") || token.spelling.equals("notequals")) {
    		Operator op = new Operator(token, new Line(scanner.lineNumber()));
    		acceptIt();
    		expr = new BinaryExpr(expr, op, parseC(), new Line(scanner.lineNumber()));
    	}
    	return expr;
    }
    
    private Expression parseC() {
    	Expression expr = parseD();
    	while (token.spelling.equals("<") || token.spelling.equals("<=") ||
    		   token.spelling.equals(">") || token.spelling.equals(">=")) {
    		Operator op = new Operator(token, new Line(scanner.lineNumber()));
    		acceptIt();
    		expr = new BinaryExpr(expr, op, parseD(), new Line(scanner.lineNumber()));
    	}
    	return expr;
    }
    
    private Expression parseD() {
    	Expression expr = parseE();
    	while (token.spelling.equals("+") || token.spelling.equals("-")) {
    		Operator op = new Operator(token, new Line(scanner.lineNumber()));
    		acceptIt();
    		expr = new BinaryExpr(expr, op, parseE(), new Line(scanner.lineNumber()));
    	}
    	return expr;
    }
    
    private Expression parseE() {
    	Expression expr = parseF();
    	while (token.spelling.equals("*") || token.spelling.equals("/") || 
    		   token.spelling.equals("mod") || token.spelling.equals("%")) {
    		Operator op = new Operator(token, new Line(scanner.lineNumber()));
    		acceptIt();
    		expr = new BinaryExpr(expr, op, parseF(), new Line(scanner.lineNumber()));
    	}
    	return expr;
    }
    
    private Expression parseF() {
    	if (token.isUnary()) {
    		Operator op = new Operator(token, new Line(scanner.lineNumber()));
    		acceptIt();
    		return new UnaryExpr(op, parseF(), new Line(scanner.lineNumber()));
    	} else if (token.kind == TokenKind.LPAREN) {
    		acceptIt();
    		Expression expr = parseExpression();
    		accept(TokenKind.RPAREN);
    		return expr;
    	} else if (token.kind == TokenKind.INTLIT) {
    		IntLiteral lit = new IntLiteral(token.spelling, new Line(scanner.lineNumber()));
    		acceptIt();
    		return new LiteralExpr(lit, new Line(scanner.lineNumber()));
    	} else if (token.kind == TokenKind.FLOATLIT) {
    		FloatLiteral lit = new FloatLiteral(token.spelling, new Line(scanner.lineNumber()));
    		acceptIt();
    		return new LiteralExpr(lit, new Line(scanner.lineNumber()));
    	} else if (token.kind == TokenKind.STRLIT) {
    		StringLiteral lit = new StringLiteral(token.spelling, new Line(scanner.lineNumber()));
    		acceptIt();
    		return new LiteralExpr(lit, new Line(scanner.lineNumber()));
    	} else if (token.kind == TokenKind.BOOLLIT) {
    		BooleanLiteral lit = new BooleanLiteral(token.spelling, new Line(scanner.lineNumber()));
    		acceptIt();
    		return new LiteralExpr(lit, new Line(scanner.lineNumber()));
    	} else if (token.kind == TokenKind.REFLIT) {
    		ReferenceLiteral reflit = new ReferenceLiteral(new Line(scanner.lineNumber()));
    		acceptIt();
    		return new LiteralExpr(reflit, new Line(scanner.lineNumber()));
    	} else if (token.spelling.equals("new")) {
    		Line newLine = token.line;
    		
    		accept(TokenKind.KEYWORD, "new");
    		
    		Identifier id = new Identifier(token.spelling, token.line);
    		
    		accept(TokenKind.IDEN);
    		accept(TokenKind.LPAREN);
    		
    		ExprList args = new ExprList();
    		if (token.kind != TokenKind.RPAREN)
    			args = parseArgumentList();
    		accept(TokenKind.RPAREN);
    		
    		return new NewObjectExpr(new RefType(id, id.line), args, newLine);
    	} else { // Reference (ArgList?)? 
    		Reference ref = parseReference();    		
    		if (token.kind == TokenKind.LPAREN) {
    			ExprList exprList = new ExprList();
    			accept(TokenKind.LPAREN);
				if (token.kind != TokenKind.RPAREN)
					exprList = parseArgumentList();
    			accept(TokenKind.RPAREN);
    			return new CallExpr(ref, exprList, new Line(scanner.lineNumber()));
    		} else {
    			return new RefExpr(ref, new Line(scanner.lineNumber()));
    		}
    	}
    }
}
