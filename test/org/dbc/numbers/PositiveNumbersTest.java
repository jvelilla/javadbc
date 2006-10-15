package org.dbc.numbers;

import junit.framework.TestCase;

public class PositiveNumbersTest extends TestCase {

	PositiveNumber pn;
	
	protected void setUp() throws Exception {
		    pn = new PositveNumberImpl();
	}
	  
	 public void testSetValue(){
		  pn.setValue(10);
	      assertEquals(10.0,pn.getValue()); 
	 }
	 
	 public void testAddValue(){
		 pn.setValue(3);
	     assertEquals(3.0,pn.getValue());
	     pn.add(5);
	     assertEquals(8.0,pn.getValue());
	     
	 }
	 
	 public void testGetSum(){
		 pn.setValue(3);
	     assertEquals(3.0,pn.getValue());
	     assertEquals(10.0,pn.getSum(7));
	     
	 }

}
