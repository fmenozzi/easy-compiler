/**
 * Expression List
 * 
 * @author 	Federico Menozzi
 * @version Easy Compiler v1
 */

package easy.AbstractSyntaxTrees;

import java.util.ArrayList;
import java.util.Iterator;

public class ExprList implements Iterable<Expression> {

	/**
	 * ArrayList of {@link Expression}s
	 */
	private ArrayList<Expression> exprList;

	/**
	 * Construct an empty list of {@code Expression}s
	 */
	public ExprList() {
		exprList = new ArrayList<Expression>();
	}

	/**
	 * Add an {@code Expression} to the list. 
	 * 
	 * @param e	the Expression to add
	 */
	public void add(Expression e){
		exprList.add(e);
	}

	/**
     * Retrieve the i-th {@code Expression} from the list, 
     * using zero-based indexing.
     * 
     * @param i	the index
     * @return	the i-th Expression
     */
	public Expression get(int i){
		return exprList.get(i);
	}

	/**
     * Retrieve the size of the list.
     * 
     * @return	the number of elements in the list
     */
	public int size() {
		return exprList.size();
	}
	
	/**
     * Test whether the list is empty. Equivalent to testing
     * whether size() == 0.
     * 
     * @return	true if the list has zero elements, false otherwise
     */
	public boolean isEmpty() {
		return exprList.isEmpty();
	}

	/**
     * Retrieve an iterator for the list
     * 
     * @return an iterator for the list
     */
	public Iterator<Expression> iterator() {
		return exprList.iterator();
	}
}
