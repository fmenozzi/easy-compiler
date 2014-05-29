/**
 * Field Declaration List
 * 
 * @author 	Federico Menozzi
 * @version Easy Compiler v1
 */

package easy.AbstractSyntaxTrees;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FieldDeclList implements Iterable<FieldDecl> {

	/**
	 * ArrayList of {@link FieldDecl}s
	 */
	private List<FieldDecl> fieldDeclList;

	/**
	 * Construct an empty list of {@code FieldDecl}s
	 */
	public FieldDeclList() {
		fieldDeclList = new ArrayList<FieldDecl>();
	}   

	/**
	 * Add an {@code FieldDecl} to the list. 
	 * 
	 * @param fd	the FieldDecl to add
	 */
	public void add(FieldDecl fd){
		fieldDeclList.add(fd);
	}

	/**
     * Retrieve the i-th {@code FieldDecl} from the list, 
     * using zero-based indexing.
     * 
     * @param i	the index
     * @return	the i-th FunctionDecl
     */
	public FieldDecl get(int i){
		return fieldDeclList.get(i);
	}

	/**
     * Retrieve the size of the list.
     * 
     * @return	the number of elements in the list
     */
	public int size() {
		return fieldDeclList.size();
	}

	/**
     * Test whether the list is empty. Equivalent to testing
     * whether size() == 0.
     * 
     * @return	true if the list has zero elements, false otherwise
     */
	public boolean isEmpty() {
		return fieldDeclList.isEmpty();
	}

	/**
     * Retrieve an iterator for the list
     * 
     * @return an iterator for the list
     */
	public Iterator<FieldDecl> iterator() {
		return fieldDeclList.iterator();
	}
}
