package org.dbc.time;

import junit.framework.TestCase;

public class TimeOfDayTest extends TestCase {

	TimeOfDay time; 
	@Override
	protected void setUp() throws Exception {
		//time =new TimeOfDay();
	}
	public void testTimeOfDay() {
		time =new TimeOfDay(12,34,44);
	    assertEquals(12,time.getHour());
	    assertEquals(34,time.getMinutes());
	    assertEquals(44,time.getSeconds());
	    try {
	    time = new TimeOfDay(123,23,231);
	    } catch(AssertionError e ){
	    	e.printStackTrace();
	}
	}

	public void testGetHour() {
		fail("Not yet implemented");
	}

	public void testGetMinutes() {
		fail("Not yet implemented");
	}

	public void testGetSeconds() {
		fail("Not yet implemented");
	}

	public void testSetHour() {
		fail("Not yet implemented");
	}

	public void testSetMinutes() {
		fail("Not yet implemented");
	}

	public void testSetSeconds() {
		fail("Not yet implemented");
	}

}
