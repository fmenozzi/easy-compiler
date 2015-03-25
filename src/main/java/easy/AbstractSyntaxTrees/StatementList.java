/**
 * Statement List
 * 
 * @author  Federico Menozzi
 * @version Easy Compiler v1
 */

package easy.AbstractSyntaxTrees;

import java.util.ArrayList;
import java.util.Iterator;

public class StatementList implements Iterable<Statement> {

	/**
	 * ArrayList of {@link Statement}s
	 */
	private ArrayList<Statement> statementList;

	/**
	 * Construct an empty list of {@code Statement}s
	 */
	public StatementList() {
		statementList = new ArrayList<Statement>();
	}

	/**
	 * Add an {@code Statement} to the list. 
	 * 
	 * @param e	the Statement to add
	 */
	public void add(Statement s){
		statementList.add(s);
	}

	/**
     * Retrieve the i-th {@code Statement} from the list, 
     * using zero-based indexing.
     * 
     * @param i	the index
     * @return	the i-th Statement
     */
	public Statement get(int i){
		return statementList.get(i);
	}

	/**
     * Retrieve the size of the list.
     * 
     * @return	the number of elements in the list
     */
	public int size() {
		return statementList.size();
	}
	
	/**
     * Test whether the list is empty. Equivalent to testing
     * whether size() == 0.
     * 
     * @return	true if the list has zero elements, false otherwise
     */
	public boolean isEmpty() {
		return statementList.isEmpty();
	}

	/**
     * Retrieve an iterator for the list
     * 
     * @return an iterator for the list
     */
	public Iterator<Statement> iterator() {
		return statementList.iterator();
	}
}