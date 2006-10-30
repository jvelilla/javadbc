package org.dbc.library;

import org.javadbc.attributes.DBC;
import org.javadbc.attributes.Ensure;
import org.javadbc.attributes.Invariant;
import org.javadbc.attributes.Require;

@DBC
@Invariant("getTitle() != null ")
public class Book {

	private String title;

	private String author;

	private boolean isBorrowed;
	

	//~Constructor
	public Book(String title, String author) {
		this.title=title;
		this.author=author;
	}

	//~ Methods
	public String getAuthor() {
		return author;
	}

	public String getTitle() {
		return title;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * Is book currently borrowed (i.e not in the library)?
	 * @return
	 */
	public boolean isBorrowed() {
		return isBorrowed;
	}

	public void setBoolean(boolean isBorrowed) {
		this.isBorrowed = isBorrowed;
	}
    /**
     * Borrow book
     *  
     */
	@Require("! isBorrowed()")
	@Ensure("isBorrowed()")
    public void borrow(){
    	isBorrowed=true;
    }
	
	/**
	 * return book
	 *
	 */
	@Require("isBorrowed()")
	@Ensure("!isBorrowed()")
	public void _return(){
		isBorrowed=false;
	}
}
