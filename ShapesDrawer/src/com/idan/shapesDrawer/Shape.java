package com.idan.shapesDrawer;

import java.awt.Color;
import java.awt.Graphics;

/**
 * This abstract class represents a geometric shape.
 * 
 * @author Idan Perry
 * @version 03.05.2020
 */

public abstract class Shape implements Cloneable {
	private int x1;
	private int x2;
	private int y1;
	private int y2;
	private int thickness;
	private Color color;

	/**
	 * Constructs a geometric shape using four integers and a color attribute. It's
	 * up to the inheriting class to decide how to use the four integers to
	 * construct the wanted shape.
	 * 
	 * @param x1        first integer - usualy representing the first x coordinate
	 * @param y1        second integer - usualy representing the first y coordinate
	 * @param x2        third integer - usualy representing the second x coordinate
	 *                  or the width attribute of a two dimensional shape
	 * @param y2        fourth integer - usualy representing the second y coordinate
	 *                  or the height attribute of a two dimensional shape
	 * @param thickness the thickness of the outline of the shape
	 * @param color     the color attribute of the shape
	 */
	public Shape(int x1, int y1, int x2, int y2, int thickness, Color color) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.thickness = thickness;
		this.color = color;
	}

	/**
	 * Constructs a geometric shape using a color attribute.
	 * 
	 * @param thickness the thickness of the outline of the shape
	 * @param color		the color attribute of the shape
	 */
	public Shape(int thickness, Color color) {
		this.color = color;
		this.thickness = thickness;
	}

	/**
	 * Constructs a geometric shape.
	 */
	public Shape() {
	}

	/**
	 * Returns the first x coordinate
	 * 
	 * @return the first x coordinate
	 */
	protected int getX1() {
		return x1;
	}

	/**
	 * Sets the first x coordinate
	 * 
	 * @param x1 the first x coordinate
	 */
	protected void setX1(int x1) {
		this.x1 = x1;
	}

	/**
	 * Returns the second x coordinate
	 * 
	 * @return the second x coordinate
	 */
	protected int getX2() {
		return x2;
	}

	/**
	 * Sets the second x coordinate
	 * 
	 * @param x2 the second x coordinate
	 */
	protected void setX2(int x2) {
		this.x2 = x2;
	}

	/**
	 * Returns the first y coordinate
	 * 
	 * @return the first y coordinate
	 */
	protected int getY1() {
		return y1;
	}

	/**
	 * Sets the first y coordinate
	 * 
	 * @param y1 the first y coordinate
	 */
	protected void setY1(int y1) {
		this.y1 = y1;
	}

	/**
	 * Returns the second y coordinate
	 * 
	 * @return the second y coordinate
	 */
	protected int getY2() {
		return y2;
	}

	/**
	 * Sets the second y coordinate
	 * 
	 * @param y2 the second y coordinate
	 */
	protected void setY2(int y2) {
		this.y2 = y2;
	}

	/**
	 * Returns the color attribute
	 * 
	 * @return the color attribute
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Returns the thickness of the outline of the shape.
	 * 
	 * @return the thickness of the outline of the shape
	 */
	public int getThickness() {
		return thickness;
	}

	/**
	 * Changes the thickness of the outline of the shape.
	 * 
	 * @param thickness the thickness of the outline of the shape
	 */
	public void setThickness(int thickness) {
		this.thickness = thickness;
	}

	/**
	 * Changes the color attribute of the shape
	 * 
	 * @param color the color attribute of the shape
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Drawing the shape using Graphics object
	 * 
	 * @param g the Graphics object
	 */
	protected abstract void draw(Graphics g);
}
