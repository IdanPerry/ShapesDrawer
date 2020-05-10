package com.idan.shapesDrawer;
import java.awt.Color;

/**
 * This abstract class represents a geometric bounded shape.
 * 
 * @author Idan Perry
 * @version 03.05.2020
 */

public abstract class BoundedShape extends Shape {
	private boolean filled;

	/**
	 * Constructs a geometric bounded shape with the specified boundaries,
	 * color, thickness and a boolean filled color attribute.
	 * 
	 * @param upperLeftX the upper left x coordinate of the bounded shape
	 * @param upperLeftY the upper left y coordinate of the bounded shape
	 * @param width      the width of the bounded shape
	 * @param height     the height of the bounded shape
	 * @param thickness  the thickness of the outline of this bounded shape
	 * @param color      the color of the bounded shape
	 * @param isFilled   whether the bounded shape is filled with color
	 */
	public BoundedShape(int upperLeftX, int upperLeftY, int width, int height, int thickness, Color color, boolean filled) {
		super(upperLeftX, upperLeftY, width, height, thickness, color);	
		this.filled = filled;
	}
	
	/**
	 * Constructs a geometric bounded shape with the specified
	 * color, thickness and a boolean filled color attribute.
	 * 
	 * @param thickness the thickness of the outline of this bounded shape
	 * @param color 	the color of the bounded shape
	 * @param filled 	whether the bounded shape is filled with color
	 */
	public BoundedShape(int thickness, Color color, boolean filled) {
		super(thickness, color);
		this.filled = filled;
	}
	
	/**
	 * Returns the upper left x coordinate
	 * 
	 * @return the upper left x coordinate
	 */
	public int getUpperLeftX() {
		return getX1();
	}
	
	/**
	 * Changes the upper left x coordinate
	 * 
	 * @param x the upper left x coordinate
	 */
	public void setUpperLeftX(int x) {
		setX1(x);
	}
	
	/**
	 * Returns the upper left y coordinate
	 * 
	 * @return the upper left y coordinate
	 */
	public int getUpperLeftY() {
		return getY1();
	}
	
	/**
	 * Changes the upper left y coordinate
	 * 
	 * @param y the upper left y coordinate
	 */
	public void setUpperLeftY(int y) {
		setY1(y);
	}
	
	/**
	 * Returns the width of the bounded shape
	 * 
	 * @return the width of the bounded shape
	 */
	public int getWidth() {
		return getX2();
	}
	
	/**
	 * Changes the width of the bounded shape
	 * 
	 * @param width the width of the bounded shape
	 */
	public void setWidth(int width) {
		setX2(width);
	}
	
	/**
	 * Returns the height of the bounded shape
	 * 
	 * @return the height of the bounded shape
	 */
	public int getHeight() {
		return getY2();
	}
	
	/**
	 * Changes the height of the bounded shape
	 * 
	 * @param width the height of the bounded shape
	 */
	public void setHeight(int height) {
		setY2(height);
	}

	/**
	 * Returns true if the bounded shape is filled with color
	 * 
	 * @return true if the bounded shape is filled with color
	 */
	public boolean isFilled() {
		return filled;
	}

	/**
	 * Changes the fill color attribute
	 * 
	 * @param filled the fill color attribute
	 */
	public void setFilled(boolean filled) {
		this.filled = filled;
	}
}
