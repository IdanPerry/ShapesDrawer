package com.idan.drawables;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import com.idan.utils.Geometry;

/**
 * This class represents a drawable straight line.
 * 
 * @author Idan Perry
 * @version 03.05.2020
 */

public class Line extends Shape {

	/**
	 * Constructs a line between two points - source point and destination point,
	 * with a color and thickness attributes. each point is made of the specified x
	 * and y coordinates.
	 * 
	 * @param sourceX   the x coordinate of the source point
	 * @param sourceY   the y coordinate of the source point
	 * @param destX     the x coordinate of the destination point
	 * @param destY     the y coordinate of the destination point
	 * @param thickness the thickness of this line
	 * @param color     the color attribute of this line
	 */
	public Line(int sourceX, int sourceY, int destX, int destY, int thickness, Color color) {
		super(sourceX, sourceY, destX, destY, thickness, color);
	}

	/**
	 * Returns the x coordinate of the source point
	 * 
	 * @return the x coordinate of the source point
	 */
	public int getSourceX() {
		return getX1();
	}

	/**
	 * Changes the x coordinate of the source point
	 * 
	 * @param sourceX the x coordinate of the source point
	 */
	public void setSourceX(int sourceX) {
		setX1(sourceX);
	}

	/**
	 * Returns the y coordinate of the source point
	 * 
	 * @return the y coordinate of the source point
	 */
	public int getSourceY() {
		return getY1();
	}

	/**
	 * Changes the y coordinate of the source point
	 * 
	 * @param sourceY the y coordinate of the source point
	 */
	public void setSourceY(int sourceY) {
		setY1(sourceY);
	}

	/**
	 * Returns the x coordinate of the destination point
	 * 
	 * @return the x coordinate of the destination point
	 */
	public int getDestX() {
		return getX2();
	}

	/**
	 * Changes the x coordinate of the destination point
	 * 
	 * @param destX the x coordinate of the destination point
	 */
	public void setDestX(int destX) {
		setX2(destX);
	}

	/**
	 * Returns the y coordinate of the destination point
	 * 
	 * @return the y coordinate of the destination point
	 */
	public int getDestY() {
		return getY2();
	}

	/**
	 * Changes the y coordinate of the destination point
	 * 
	 * @param destY the y coordinate of the destination point
	 */
	public void setDestY(int destY) {
		setY2(destY);
	}
	
	@Override
	public boolean hasPoint(int x, int y) {
		return Geometry.isBetween(new Point(getX1(), getY1()), new Point(getX2(), getY2()), new Point(x,y));
	}

	@Override
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(getColor());
		g2.setStroke(new BasicStroke(getThickness()));
		g2.drawLine(getX1(), getY1(), getX2(), getY2());
	}
}
