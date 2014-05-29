/**
 * Representation of line numbers in source code
 * 
 * @author  Federico Menozzi
 * @version Easy Compiler v1
 */
package easy.SyntacticAnalyzer;

public class Line {
	
	/**
	 * Line number in source code
	 */
	public final int line;

	/**
	 * Construct a Line at the given line in source
	 * 
	 * @param line	the given line number in source
	 */
	public Line(int line) {
		this.line = line;
	}

	/**
	 * Print the String representation of the integer "line"
	 * 
	 * @return	the String form of the integer "line"
	 */
	@Override
	public String toString() {
		return Integer.toString(line);
	}
}
