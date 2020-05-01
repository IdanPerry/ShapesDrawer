package com.idan.painter;

import java.awt.Color;

/**
 * This abstract class represents a polygon.
 * 
 * @author Idan Perry
 * @version 03.05.2020
 */

public abstract class Polygon extends BoundedShape {
	private int[] xPoints;
	private int[] yPoints;

	/**
	 * Constructs a polygon with the specified boundaries, color
	 * and a boolean filled color attribute.
	 * 
	 * @param upperLeftX the upper left x coordinate of this polygon
	 * @param upperLeftY the upper left y coordinate of this polygon
	 * @param width      the width of this polygon
	 * @param height     the height of this polygon
	 * @param color      the color of the polygon
	 * @param thickness  the thickness of the outline of this polygon
	 * @param isFilled   whether this polygon is filled with color
	 */
	public Polygon(int[] xPoints, int[] yPoints, int thickness, Color color, boolean filled) {
		super(thickness, color, filled);
		this.xPoints = xPoints;
		this.yPoints = yPoints;
	}

	/**
	 * Changes the array of x coordinates of this polygon.
	 * 
	 * @param xPoints the array of x coordinates of this polygon
	 */
	public void setXPoints(int[] xPoints) {
		this.xPoints = xPoints;
	}

	/**
	 * Returns an array of the x coordinates of this polygon.
	 * 
	 * @return an array of the x coordinates of this polygon
	 */
	public int[] getXPoints() {
		return xPoints;
	}

	/**
	 * Changes the array of y coordinates of this polygon.
	 * 
	 * @param xPoints the array of y coordinates of this polygon
	 */
	public void setYPoints(int[] yPoints) {
		this.yPoints = yPoints;
	}

	/**
	 * Returns an array of the y coordinates of this polygon.
	 * 
	 * @return an array of the y coordinates of this polygon
	 */
	public int[] getYPoints() {
		return yPoints;
	}
}
