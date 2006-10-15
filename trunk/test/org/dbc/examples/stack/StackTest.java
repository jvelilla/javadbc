package org.dbc.examples.stack;


import junit.framework.TestCase;

public class StackTest extends TestCase {

    IStack s;
    
	public void testPut(){
		 assertNotNull("one");
		 s.put("one");
	     assertEquals("one", s.getItem());
	     assertTrue(!s.isEmpty());
	 }
    
	public void testItem(){
		 s.put("one");
		 s.put("two");
		 assertTrue(!s.isEmpty());
		 assertEquals("two",s.getItem());
    } 
	
	public void testStack(){
		 s.put("one");
		 s.put("two");
		 s.remove();
		 s.remove();
		 try{
		 s.remove();
		 }catch(AssertionError e){
	          e.printStackTrace();
		 }	 
		 }
		 
 	
	@Override
	protected void setUp() throws Exception {
		s = new StackImpl(); 
	}
}
