 package org.dbc.punto;

import org.javadbc.attributes.DBC;
import org.javadbc.attributes.Ensure;

@DBC
public class Punto {
	private int x = 0;

	private int y = 0;

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Ensure("(getX() == {old getX()} + #arg0) && (getY() == {old getY()})")
	public int moveX(int dx) {
		x += dx;
		return x;
	}

	@Ensure("getY() == {old getY()} + $0 && getX() == {old getX()}")
	public int moveY(int dy) {
		y += dy;
		return y;
	}
}