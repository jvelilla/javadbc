package org.dbc.examples.genericstack;

import java.util.LinkedList;

public class StackImpl<E> implements IStack<E> {

	LinkedList<E> stackImpl= new LinkedList<E>();
	
	public StackImpl(){
		stackImpl.clear();
	}
	public int getCount() {
		return stackImpl.size();
	}

	public E getItem() {
		return stackImpl.getLast();
	}

	public void initialize() {
	    
	}

	public boolean isEmpty() {
		return getCount()==0;
	}

	
	public void put(E g) {
		stackImpl.add(g); 
	}

	public void remove() {
		stackImpl.removeLast();
	}

}
