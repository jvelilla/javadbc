package org.javadbc.attributes;

/**
 * @author Jvelilla Typesafe enumeration listing possible check levels contracts .
 */

/**
 * PRECONDITION: Precondition method checking. POSTCONDITION: Postcondition
 * method checking, implies preconditions checking. Chequea las postcondiciones
 * de un metodo, implica chequear las PRECONDITION INVARIANT: Chequea los
 * invariantes, implica chequear INVARIANT DISABLE: The assertion are not
 * consider in runtime. las aserciones no son consideradas en run-time.
 */

public enum CheckLevel {

	PRECONDITION("PRECONDITION"), 
	POSTCONDITION("POSTCONDITION"), 
	INVARIANT("INVARIANT"), 
	DISABLE("DISABLE");

	private final String level;

	CheckLevel(String level) {
		this.level = level;
	}

	public String getLevel() {
		return level;
	}
}
