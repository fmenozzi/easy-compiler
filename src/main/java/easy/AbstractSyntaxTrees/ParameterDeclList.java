/**
 * Parameter Declaration List
 * 
 * @author  Federico Menozzi
 * @version Easy Compiler v1
 */

package easy.AbstractSyntaxTrees;

import java.util.ArrayList;
import java.util.Iterator;

public class ParameterDeclList implements Iterable<ParameterDecl> {
	
	/**
	 * ArrayList of {@link ParameterDecl}s
	 */
	private ArrayList<ParameterDecl> parameterDeclList;
	
	/**
	 * Construct an empty list of {@code ParameterDecl}s
	 */
    public ParameterDeclList() {
    	parameterDeclList = new ArrayList<ParameterDecl>();
    }
    
    /**
     * Add a {@code ParameterDecl} to the list
     * 
     * @param pd	the ParameterDecl to add
     */
    public void add(ParameterDecl pd){
    	parameterDeclList.add(pd);
    }
    
    /**
     * Retrieve the i-th {@code ParameterDecl} from the list, 
     * using zero-based indexing.
     * 
     * @param i	the index
     * @return	the i-th {@code ParameterDecl}
     */
    public ParameterDecl get(int i){
        return parameterDeclList.get(i);
    }
    
    /**
     * Retrieve the size of the list.
     * 
     * @return	the number of elements in the list
     */
    public int size() {
        return parameterDeclList.size();
    }
    
    /**
     * Test whether the list is empty. Equivalent to testing
     * whether size() == 0.
     * 
     * @return	true if the list has zero elements, false otherwise
     */
    public boolean isEmpty() {
    	return parameterDeclList.isEmpty();
    }
    
    /**
     * Retrieve an iterator for the list
     * 
     * @return an iterator for the list
     */
    public Iterator<ParameterDecl> iterator() {
    	return parameterDeclList.iterator();
    }
}
