package org.dbc.numbers;

public class PositveNumberImpl implements PositiveNumber {

	private double d;

	public void setValue(double val) {
		this.d = val;
	}

	public double getValue() {
		return this.d;
	}

	public double getSqrt() {
		return Math.sqrt(this.d);
	}

	public double getSum(double e) {
		return (this.d + e);
	}

	public void add(double e) {
		this.d = this.d + e;
	}

	public double abs(double d) {
		return java.lang.Math.abs(d);
	}
}