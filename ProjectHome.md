The notion of contracts as interfaces annotated with preconditions,postconditions and invariants , is simple and practical. The interface defines everything the clients need to know, but no more than that. This Project focus is on developing a library of Desing by Contract for Java. JavaDbC uses Java 5 annotations, to define assertions based on AOP(http://www.eclipse.org/aspectj/) and OGNL(http://www.ognl.org/).

  * Quick start with Design by Contract in Java (Comming soon!)


You can see the code and examples here: http://javadbc.googlecode.com/svn/trunk/




**Example**

```

package org.dbc.examples.genericstack;

import org.javadbc.attributes.DBC;
import org.javadbc.attributes.Ensure;
import org.javadbc.attributes.Invariant;
import org.javadbc.attributes.Require;

@DBC
@Invariant("getCount() >= 0")
public interface IStack<E> {
	
	/**
	 * Cantidad de items que contiene actualmente el Stack
	 * 
	 * @return
	 */
	int getCount();

	/**
	 * Retorna el item que esta en el tope de la pila
	 * 
	 * @return
	 */
	@Require("getCount() > 0")
	public E getItem();

	/**
	 * Determina si el Stack esta vacio
	 * 
	 * @return
	 */
	@Ensure("{result}==(getCount()==0)")
	public boolean isEmpty();

	/**
	 * Cada nuevo Stack se inicializa en vacio o para re-inicializar una Stack
	 * existente
	 */
	void initialize();

	/**
	 * Pone un item g sobre el tope del Stack
	 * 
	 * @param g
	 */
	@Ensure("#arg0.equals(getItem()) && ( getCount() == {old getCount()} + 1)")
	void put(E g);

	/**
	 * Saca el elemento que esta en el tope del Stack
	 * 
	 * @return
	 */
	@Require("getCount() > 0 ")
	@Ensure("getCount() == {old getCount()} - 1 ")
	void remove();

}

```


