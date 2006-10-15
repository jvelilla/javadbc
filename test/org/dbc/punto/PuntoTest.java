package org.dbc.punto;

import junit.framework.TestCase;

public class PuntoTest extends TestCase {

	Punto p;
	
	public void testCordenadas(){
	  p.setX(10);
	  p.setY(5);
	  assertEquals(10, p.getX());
	  assertEquals(5, p.getY());
	  p.moveX(5);
	  assertEquals(15, p.getX());
	}
	
	protected void setUp() throws Exception {
	 p=new Punto();
	}
}
