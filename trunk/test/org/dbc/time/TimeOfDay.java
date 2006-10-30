package org.dbc.time;

import org.javadbc.attributes.DBC;
import org.javadbc.attributes.Ensure;
import org.javadbc.attributes.Invariant;
import org.javadbc.attributes.Require;

@DBC
@Invariant(" getHour() >= 0 && getHour() <= 23 &&"
		+ " getMinutes() >= 0 &&  getMinutes() <= 59 && "
		+ "getSeconds() >= 0 &&  getSeconds() <= 59")
public class TimeOfDay {

	private int hour;

	private int minutes;

	private int seconds;

	@Require("#arg0 >= 0 && #arg0 <= 23 &&  " + "#arg1 >= 0 && #arg1 <= 59 && "
			+ "#arg2 >= 0 && #arg2 <= 59 ")
	@Ensure("getHour() == #arg0 && " + "getMinutes() == #arg1 && "
			+ "getSeconds() == #arg2 ")
	public TimeOfDay(int hour, int minutes, int seconds) {
		super();
		this.hour = hour;
		this.minutes = minutes;
		this.seconds = seconds;
	}

	public TimeOfDay() {
	}

	public int getHour() {
		return hour;
	}

	public int getMinutes() {
		return minutes;
	}

	public int getSeconds() {
		return seconds;
	}

	@Require("#arg0 >= 0 && #arg0 <= 23")
	@Ensure("getHour() ==  #arg0 && "
			+ "getMinutes() == {old getMinutes()} && "
			+ "getSeconds() == {old getSeconds()}")
	public void setHour(int hour) {
		this.hour = hour;
	}

	@Require("#arg0 >= 0 && #arg0 <= 59")
	@Ensure("getHour() == {old getHour()} && "
			+ "getMinutes() == #arg0 && "
			+ "getSeconds() == {old getSeconds()}")
	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	@Require("#arg0 >= 0 && #arg0 <= 59")
	@Ensure("getHour() == {old getHour()} && "
			+ "getMinutes() == {old getMinutes()} && "
			+ "getSeconds() == #arg0 ")
	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}

}
