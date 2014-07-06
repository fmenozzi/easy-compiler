/**
 * Used to scan character streams and return tokens
 * 
 * @author 	Federico Menozzi
 * @version Easy Compiler v1
 */

package easy.SyntacticAnalyzer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.util.HashSet;

import easy.SyntacticAnalyzer.Line;
import easy.SyntacticAnalyzer.Token;
import easy.SyntacticAnalyzer.TokenKind;

import easy.SyntacticAnalyzer.Scanner;
import easy.ErrorReporter;

public class Scanner {
	
	/**
	 * Current character in input stream
	 */
	private char currentChar;
	
	/**
	 * Newline character for Windows systems
	 */
	private final static char eolWindows = '\r';
	
	/**
	 * Newline character for Unix systems
	 */
	private final static char eolUnix = '\n';
	
	/**
	 * Current line number in source code
	 */
	private int lineNumber;	
	
	/**
	 * Flag for end-of-file character
	 */
	private boolean isEOF;
	
	/**
	 * Stream from which we read input characters
	 */
	private InputStream inputStream;
	
	/**
	 * Used in reporting errors
	 */
	public final ErrorReporter reporter;
	
	/**
	 * Set of keywords
	 */
	private HashSet<String> keywords;
	
	/**
	 * Set of logical operators
	 */
	private HashSet<String> logops;
	
	/**
	 * Set of relational operators
	 */
	private HashSet<String> relops;
	
	/**
	 * Main method used for debugging 
	 * 
	 * @param args	the list of command-line args, the first being the source path
	 */
	public static void main(String[] args) {
		InputStream inputStream = null;
		if (args.length == 0) {
			System.out.println("Enter Program:");
			inputStream = System.in;
		} else {
			try {
				inputStream = new FileInputStream(args[0]);
			} catch (FileNotFoundException e) {
				System.out.println("Input file " + args[0] + " not found");
				System.exit(1);
			}		
		}
		
		Scanner scanner = new Scanner(inputStream, new ErrorReporter());
		
		System.out.println("Starting...\n");
		
		Token token = scanner.scan();
		if (scanner.reporter.hasErrors()) {
			scanner.reporter.reportErrors();
			return;
		}
		System.out.println(token.debugStr());
		
		while (token.kind != TokenKind.EOF) {
			token = scanner.scan();
			if (scanner.reporter.hasErrors()) {
				scanner.reporter.reportErrors();
				return;
			}
			System.out.println(token.debugStr());
		}
		System.out.println("\nDone");
	}
	
	/**
	 * Constructor that sets up keywords and operator
	 * names and then proceeds to read first character of input
	 * 
	 * @param inputStream	the stream from which characters are read
	 * @param reporter		the ErrorReporter instance
	 */
	public Scanner(InputStream inputStream, ErrorReporter reporter) {
		this.inputStream = inputStream;
		this.reporter 	 = reporter;
				
		keywords = new HashSet<String>();
		keywords.add("function");
		keywords.add("main");
		keywords.add("end");
		keywords.add("return");
		
		//keywords.add("returns");
		
		keywords.add("int");
		keywords.add("boolean");
		keywords.add("void");
		keywords.add("mod");
		keywords.add("if");
		keywords.add("else");
		keywords.add("while");
		keywords.add("for");
		keywords.add("loop");
		keywords.add("until");
		keywords.add("in");
		keywords.add("from");
		keywords.add("by");
		keywords.add("to");
		keywords.add("break");
		keywords.add("new");
		
		logops = new HashSet<String>();
		logops.add("and");
		logops.add("or");
		logops.add("not");
		
		relops = new HashSet<String>();
		relops.add("equals");
		relops.add("notequals");

		isEOF = false;

		lineNumber = 1;

		nextChar();
	}
	
	/**
	 * Scans a series of characters from the character stream 
	 * and constructs the appropriate token
	 *  
	 * @return the synthesized token
	 */
	public Token scan() {
				
		// Skip whitespace (\n, \t, etc.) and comments
		passThroughWhitespace();
		
		// Check for EOF flag
		if (isEOF)
			return new Token(TokenKind.EOF, "EOF", new Line(lineNumber));

		// Identify keywords and identifiers
		if (isAlphaChar(currentChar)) {
			// Consume entire "word"
			String word = "";
			while (isAlphaChar(currentChar) || isDigit(currentChar) || isUnderscore(currentChar)) {
				word += currentChar;
				takeIt();
			}

			// Perform lookup to disambiguate "word"
			if (keywords.contains(word))
				return new Token(TokenKind.KEYWORD, word, new Line(lineNumber));
			else if (logops.contains(word))
				return new Token(TokenKind.LOGOP, word, new Line(lineNumber));
			else if (relops.contains(word))
				return new Token(TokenKind.RELOP, word, new Line(lineNumber));
			else if (word.equals("true") || word.equals("false"))
				return new Token(TokenKind.BOOLLIT, word, new Line(lineNumber));
			else if (word.equals("mod"))
				return new Token(TokenKind.ARITHOP, word, new Line(lineNumber));
			else 
				return new Token(TokenKind.IDEN, word, new Line(lineNumber));
		}

		// Scan token
		char   temp;
		String errorStr;
		switch (currentChar) {
		case '"':
		case '\'':
			// Handle string literals (can begin with single or double quotes)
			String strlit = "";
			temp = currentChar;
			takeIt();
			while (currentChar != temp) {
				if (isEOF) {
					errorStr = "Unterminated string";
					scanError(lineNumber, errorStr);
					return new Token(TokenKind.ERROR, errorStr, new Line(lineNumber));
				}
				strlit += Character.toString(currentChar);
				takeIt();
			}
			takeIt();
			return new Token(TokenKind.STRLIT, strlit, new Line(lineNumber));
			
		case '+': 
		case '*':
		case '/':
		case '-':
		case '%':
			// Handle single-character arithmetic operators
			temp = currentChar;
			takeIt();
			if ((temp == '+' && currentChar == '+') || 
				(temp == '-' && currentChar == '-')) {
				// Deny ++ and --
				errorStr = "++ not allowed in Easy!";
				scanError(lineNumber, errorStr);
				return new Token(TokenKind.ERROR, Character.toString(temp), new Line(lineNumber));
			} else {
				return new Token(TokenKind.ARITHOP, Character.toString(temp), new Line(lineNumber));
			}
			
		case '>':
		case '<':
			// Handle single-character relational operators
			temp = currentChar;
			takeIt();
			if (currentChar == '=') {
				takeIt();
				return new Token(TokenKind.RELOP, Character.toString(temp) + "=", new Line(lineNumber));
			}
			else {
				return new Token(TokenKind.RELOP, Character.toString(temp), new Line(lineNumber));
			}
			
		case '=':
			temp = currentChar;
			takeIt();
			if (currentChar == '=') {
				// Allow == as well
				takeIt();
				return new Token(TokenKind.RELOP, "==", new Line(lineNumber));
			} else {
				return new Token(TokenKind.ASSIGN, "=", new Line(lineNumber));
			}
			
		case '!':
			// Allow ! and != as well
			takeIt();
			if (currentChar == '=') {
				takeIt();
				return new Token(TokenKind.RELOP, "!=", new Line(lineNumber));
			}
			else
				return new Token(TokenKind.LOGOP, "!", new Line(lineNumber));
			
		case '&':
			takeIt();
			if (currentChar == '&') {
				// Allow && as well
				takeIt();
				return new Token(TokenKind.LOGOP, "&&", new Line(lineNumber));
			} else {
				errorStr = "Single & not allowed in Easy!";
				scanError(lineNumber, errorStr);
				return new Token(TokenKind.ERROR, errorStr, new Line(lineNumber));
			}
			
		case '|':
			takeIt();
			if (currentChar == '|') {
				// Allow || as well
				takeIt();
				return new Token(TokenKind.LOGOP, "||", new Line(lineNumber));
			} else {
				errorStr = "Single | not allowed in Easy!";
				scanError(lineNumber, errorStr);
				return new Token(TokenKind.ERROR, errorStr, new Line(lineNumber));
			}

		case '(': 
			takeIt();
			return new Token(TokenKind.LPAREN, "(", new Line(lineNumber));

		case ')':
			takeIt();
			return new Token(TokenKind.RPAREN, ")", new Line(lineNumber));

		case '{':
			takeIt();
			return new Token(TokenKind.LBRACE, "{", new Line(lineNumber));

		case '}':
			takeIt();
			return new Token(TokenKind.RBRACE, "}", new Line(lineNumber));

		case '[':
			takeIt();
			return new Token(TokenKind.LBRACKET, "[", new Line(lineNumber));

		case ']':
			takeIt();
			return new Token(TokenKind.RBRACKET, "]", new Line(lineNumber));

		case '0': 
		case '1': 
		case '2': 
		case '3': 
		case '4':
		case '5': 
		case '6': 
		case '7': 
		case '8': 
		case '9':
			String numstr = "";
			while (isDigit(currentChar)) {
				numstr += Character.toString(currentChar);
				takeIt();
			}
			return new Token(TokenKind.INTLIT, numstr, new Line(lineNumber));
			
			/*
			// Distinguish between ints and floats
			if (currentChar == '.') {
				takeIt();
				if (isDigit(currentChar)) {
					String floatstr = "";
					while (isDigit(currentChar)) {
						floatstr += Character.toString(currentChar);
						takeIt();
					}
					return new Token(TokenKind.FLOATLIT, numstr+"."+floatstr, new Line(lineNumber));
				} else {
					errorStr = "Floats must have numbers on either side of the point!";
					scanError(lineNumber, errorStr);
					return new Token(TokenKind.ERROR, errorStr, new Line(lineNumber));
				}
			} else {
				return new Token(TokenKind.INTLIT, numstr, new Line(lineNumber));
			}
			*/
		case ';':
			takeIt();
			return new Token(TokenKind.SEMICOL, ";", new Line(lineNumber));
			
		case ',':
			takeIt();
			return new Token(TokenKind.COMMA, ",", new Line(lineNumber));
			
		case '.':
			takeIt();
			if (isDigit(currentChar)) {
				errorStr = "Floats must have numbers on either side of the point!";
				scanError(lineNumber, errorStr);
				return new Token(TokenKind.ERROR, errorStr, new Line(lineNumber));
			} else {
				return new Token(TokenKind.DOT, ".", new Line(lineNumber));
			}
			
		case '_':
			takeIt();
			errorStr = "Identifiers cannot start with an underscore!";
			scanError(lineNumber, errorStr);
			return new Token(TokenKind.ERROR, errorStr, new Line(lineNumber));
			
		default:
			scanError(lineNumber, "Unrecognized character in input");
			errorStr = "ASCII: " + Integer.toString((int)currentChar);
			return new Token(TokenKind.ERROR, errorStr, new Line(lineNumber));
		}
	}
	
	/**
	 * Returns the current line number in source code
	 * 
	 * @return the current line number in source code
	 */
	public int lineNumber() {
		return lineNumber;
	}
	
	private void takeIt() {
		nextChar();
	}
	
	private void nextChar() {
		try {
            int c = inputStream.read();
            if (c == -1) {
            	currentChar = '\0';
            	isEOF = true;
            } else {
            	currentChar = (char) c;
            }
        } catch (IOException e) {
            scanError(lineNumber, "I/O Exception!");
            currentChar = '\0';
            isEOF = true;
        }
	}
	
	private void passThroughWhitespace() {
		// Skip whitespace and comments
		do {
			// Consume initial whitespace
			while (isWhitespace() || isNewline()) {
				if (isNewline())
					lineNumber++;
				takeIt();
			}

			// Consume comments
			if (currentChar == '#') {
				takeIt();
				if (currentChar == '#') {
					// Multi-line comments
					takeIt();    
					while (true) {
						if (currentChar == '#') {
							takeIt();
							if (currentChar == '#') {
								takeIt();
								break;
							}
						} else {
							if (isNewline())
								lineNumber++;
							takeIt();
						}

						if (isEOF) {
							scanError(lineNumber, "Unterminated block comment");
							break;
						}
					}
				} else {
					// Single-line comments
					while (!isNewline() && !isEOF)
						takeIt();
				}
			}	
		} while (isWhitespace() || isNewline() || isStartOfComment());
	}
	
	private boolean isDigit(char c) {
        return (c >= '0') && (c <= '9');
    }
	
	private boolean isAlphaChar(char c) {
		return ('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z');
	}
	
	private boolean isUnderscore(char c) {
		return c == '_';
	}
	
	private boolean isWhitespace() {
		return 	currentChar == ' ' || currentChar == '\t';
	}
	
	private boolean isNewline() {
		return currentChar == eolWindows || currentChar == eolUnix;
	}
	
	private boolean isStartOfComment() {
		return currentChar == '#';
	}
	
	private void scanError(int lineNumber, String errorMessage) {
    	reporter.addScanError(lineNumber, errorMessage);
    }
}
