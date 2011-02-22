package org.dbc.component.composite_library;

import java.util.Iterator;
import java.util.LinkedList;

import org.javadbc.attributes.DBC;
import org.javadbc.attributes.Ensure;
import org.javadbc.attributes.Invariant;
import org.javadbc.attributes.Require;

/**
 * Composite Library
 * @author Jvelilla
 * @based_on Karine Arnout "From Patterns to Components"
 */
//invariant
//parts_consistent:
//is_composite implies (parts /= Void and then not parts.has (Void))
@DBC
//@Invariant("isComposite()")
public abstract class Component<E> {
      //Component parts (which are themselves components)
	  protected LinkedList<Component<E>> parts=new LinkedList<Component<E>>();

	  /**
	   * Do some operation
	   */
	  public void operation(){};
	  
	  /**
	   * Is a component a composite
	   * @return
	   */
	  public boolean isComposite(){
		  return false;
	  }

	  /**
	   * Current part of composite
	   */
	  @Require("isComposite()")
	  @Ensure("{result} != null  && {result}.equals(parts.element())")
	  public Component<E> item(){
		return parts.element();  
	  }
	 
	 /**
	  * 
	  * @param <E>
	  * @param i
	  */
	 @Require("isComposite() && ( #arg0 > 0  && #arg0 < count()) ") 
	 @Ensure("{result} != null && ({result}.equals(parts.get(i))) ")		
	 public Component<E> ith(int i){
		 return parts.get(i);
	 }
	 
	 /**
	  * First Component part
	  * @param <E>
	  */
	 @Require("isComposite() && !isEmpty()")
	 @Ensure("{result} != null")
	 public Component<E> first() {
		return parts.getFirst();
	 } 
	 
	 /**
	  * Last component part
	  * @param <E>
	  */
	 @Require("isComposite() && !isEmpty()")
     @Ensure("{result} != null && {result}.equals(parts.getLast())")
     public Component<E> last(){
         return parts.getLast();
 		 
	 }

	/**
	 * Does composite contain `a_part'?
	 * @param aPart
	 * @return
	 */
	 @Require("#arg0 != null && isComposite()")
	 @Ensure("{result} == parts.has(#arg0)")
	 public boolean has(Component<E> aPart){
	   return parts.contains(aPart);
	 }
	/**
	 * Does component contain no part?
	 */
     @Require("isComposite()")
     @Ensure("{result}==(count() == 0)")
	 public boolean isEmpty(){
	 	return parts.isEmpty();
	 }
	

	/**
	 * Number of component parts
	 * @return
	 */
     @Require("isComposite() && count()== parts.size()")
	 public int count(){
		return parts.size();
	 }
	
	/**
	 * Add `aPart' to component `parts'.
	 * @param aPart
	 */
     @Require("isComposite() && #arg0 != null && !has(#arg0)")
	 @Ensure("( count() == {old count()} + 1 && has(#arg0))")
	 public void add(Component<E> aPart) {
		System.out.println("Agregamos una nueva parte");
           parts.add(aPart);
	 }
	
	/**
	 * Remove `a_part' from component `parts'.
	 * @param aPart
	 */
	@Require("isComposite() && #arg0 != null && has(#arg0)")
    @Ensure(" (count() == {old count()} - 1) && !has(#arg0)")
	public void remove (E aPart) {
		parts.remove(aPart);
	   
	}

	public Iterator<Component<E>> iterator(){
		return parts.iterator();
	}



}
