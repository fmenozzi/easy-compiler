/**
 * Struct Declaration List
 * 
 * @author  Federico Menozzi
 * @version Easy Compiler v1
 */

package easy.AbstractSyntaxTrees;

import java.util.ArrayList;
import java.util.Iterator;

public class StructDeclList implements Iterable<StructDecl> {
	
	/**
	 * ArrayList of {@link StructDecl}s
	 */
	private ArrayList<StructDecl> structDeclList;

	/**
	 * Construct an empty list of {@code StructDecl}s
	 */
	public StructDeclList() {
		structDeclList = new ArrayList<StructDecl>();
	}

	/**
	 * Add a {@code StructDecl} to the list. 
	 * 
	 * @param sd	the StructDcel to add
	 */
	public void add(StructDecl sd){
		structDeclList.add(sd);
	}

	/**
     * Retrieve the i-th {@code StructDecl} from the list, 
     * using zero-based indexing.
     * 
     * @param i	the index
     * @return	the i-th StructDecl
     */
	public StructDecl get(int i){
		return structDeclList.get(i);
	}

	/**
     * Retrieve the size of the list.
     * 
     * @return	the number of elements in the list
     */
	public int size() {
		return structDeclList.size();
	}
	
	/**
     * Test whether the list is empty. Equivalent to testing
     * whether size() == 0.
     * 
     * @return	true if the list has zero elements, false otherwise
     */
	public boolean isEmpty() {
		return structDeclList.isEmpty();
	}

	/**
     * Retrieve an iterator for the list
     * 
     * @return an iterator for the list
     */
	public Iterator<StructDecl> iterator() {
		return structDeclList.iterator();
	}
}
