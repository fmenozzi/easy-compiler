/**
 * Used to report errors in various stages of compilation
 * 
 * @author 	Federico Menozzi
 * @version Easy Compiler v1
 */

package easy;

import java.util.ArrayList;

public class ErrorReporter {
	/**
	 * Error lists
	 */
	private ArrayList<String> scanErrors;
	private ArrayList<String> parseErrors;
	
	/**
	 * Default constructor that initializes error lists
	 */
	public ErrorReporter() {
		scanErrors	= new ArrayList<String>();
		parseErrors	= new ArrayList<String>();
	}
	
	/**
	 * Add a scan error to the list of scan errors
	 * 
	 * @param message
	 */
	public void addScanError(int lineNumber, String message) {
		scanErrors.add("At line " + lineNumber + ": " + message);
	}	

	/**
	 * Add a parse error to the list of parse errors
	 * 
	 * @param message
	 */
	public void addParseError(int lineNumber, String message) {
		parseErrors.add("At line " + lineNumber + ": " + message);
	}
	
	/**
	 * Determine whether the reporter currently has any errors
	 * 
	 * @return	whether the reporter currently has any errors
	 */
	public boolean hasErrors() {
		return	hasScanErrors() || hasParseErrors();
	}
	
	private boolean hasScanErrors() {
		return scanErrors.size() > 0;
	}

	private boolean hasParseErrors() {
		return parseErrors.size() > 0;
	}
	
	/**
	 * Report errors to console
	 */
	public void reportErrors() {	
		if (!scanErrors.isEmpty()) {
			System.out.println("SCAN ERROR(S):");
			for (String error : scanErrors)
				System.out.println("\t-" + error);
		} 

		if (!parseErrors.isEmpty()) {
			System.out.println("PARSE ERROR(S):");
			for (String error : parseErrors)
				System.out.println("\t-" + error);
		}
	}
}
