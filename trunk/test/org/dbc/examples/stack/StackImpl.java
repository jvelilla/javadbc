package org.dbc.examples.stack;

import java.util.LinkedList;


public class StackImpl implements IStack {

	LinkedList stackImpl= new LinkedList();
	
	public StackImpl(){
		stackImpl.clear();
	}
	public int getCount() {
		return stackImpl.size();
	}

	public String getItem() {
		return (String)stackImpl.getLast();
	}

	public void initialize() {
	    
	}

	public boolean isEmpty() {
		return getCount()==0;
	}

	
	public void put(Object g) {
		stackImpl.add(g); 
	}

	public void remove() {
		stackImpl.removeLast();
	}

}
