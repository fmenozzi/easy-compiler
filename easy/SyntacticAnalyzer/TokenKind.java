/**
 * Used to differentiate between various kinds of tokens
 * 
 * @author 	Federico Menozzi
 * @version Easy Compiler v1
 */

package easy.SyntacticAnalyzer;

public enum TokenKind {
	KEYWORD, 
	ARITHOP, 
	LOGOP,
	RELOP,
	ASSIGN,
	LPAREN, 
	RPAREN, 
	LBRACE, 
	RBRACE, 
	LBRACKET,
	RBRACKET,
	INTLIT, 
	FLOATLIT,
	STRLIT,
	BOOLLIT,
	REFLIT,
	IDEN,
	SEMICOL,
	COMMA, 
	DOT,
	EOF, 
	ERROR
}
