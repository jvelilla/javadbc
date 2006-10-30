package org.dbc.library;

import junit.framework.TestCase;

public class LibraryTest extends TestCase {
	Book book;
	
	@Override
	protected void setUp() throws Exception {
	   book = new Book("Object Oriented Software Construction","Bertrand Meyer");
	}
	
	public void testBorrowed(){
		assertFalse(book.isBorrowed());
		book.borrow();
		assertTrue(book.isBorrowed());
	}

}
