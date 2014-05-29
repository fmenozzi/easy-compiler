/**
 * Function Declaration List
 * 
 * @author  Federico Menozzi
 * @version Easy Compiler v1
 */

package easy.AbstractSyntaxTrees;

import java.util.ArrayList;
import java.util.Iterator;

public class FunctionDeclList implements Iterable<FunctionDecl>{
	
	/**
	 * ArrayList of {@link FunctionDecl}s
	 */
	private ArrayList<FunctionDecl> functionDeclList;

	/**
	 * Construct an empty list of {@code FunctionDecl}s
	 */
	public FunctionDeclList() {
		functionDeclList = new ArrayList<FunctionDecl>();
	}   

	/**
	 * Add an {@code FunctionDecl} to the list. 
	 * 
	 * @param fd	the FunctionDecl to add
	 */
	public void add(FunctionDecl fd){
		functionDeclList.add(fd);
	}

	/**
     * Retrieve the i-th {@code FunctionDecl} from the list, 
     * using zero-based indexing.
     * 
     * @param i	the index
     * @return	the i-th FunctionDecl
     */
	public FunctionDecl get(int i){
		return functionDeclList.get(i);
	}

	/**
     * Retrieve the size of the list.
     * 
     * @return	the number of elements in the list
     */
	public int size() {
		return functionDeclList.size();
	}

	/**
     * Test whether the list is empty. Equivalent to testing
     * whether size() == 0.
     * 
     * @return	true if the list has zero elements, false otherwise
     */
	public boolean isEmpty() {
		return functionDeclList.isEmpty();
	}

	/**
     * Retrieve an iterator for the list
     * 
     * @return an iterator for the list
     */
	public Iterator<FunctionDecl> iterator() {
		return functionDeclList.iterator();
	}
}
