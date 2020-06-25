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
	 * @param originX   the x coordinate of the source point
	 * @param originY   the y coordinate of the source point
	 * @param destX     the x coordinate of the destination point
	 * @param destY     the y coordinate of the destination point
	 * @param thickness the thickness of this line
	 * @param color     the color attribute of this line
	 */
	public Line(int originX, int originY, int destX, int destY, int thickness, Color color) {
		super(originX, originY, destX, destY, thickness, color);
	}

	/**
	 * Returns the x coordinate of the source point
	 * 
	 * @return the x coordinate of the source point
	 */
	public int getOriginX() {
		return super.getOriginX();
	}

	/**
	 * Changes the x coordinate of the source point
	 * 
	 * @param sourceX the x coordinate of the source point
	 */
	public void setOriginX(int sourceX) {
		super.setOriginX(sourceX);
	}

	/**
	 * Returns the y coordinate of the source point
	 * 
	 * @return the y coordinate of the source point
	 */
	public int getOriginY() {
		return super.getOriginY();
	}

	/**
	 * Changes the y coordinate of the source point
	 * 
	 * @param sourceY the y coordinate of the source point
	 */
	public void setOriginY(int sourceY) {
		super.setOriginY(sourceY);
	}

	/**
	 * Returns the x coordinate of the destination point
	 * 
	 * @return the x coordinate of the destination point
	 */
	public int getDestX() {
		return super.getDestX();
	}

	/**
	 * Changes the x coordinate of the destination point
	 * 
	 * @param destX the x coordinate of the destination point
	 */
	public void setDestX(int destX) {
		super.setDestX(destX);
	}

	/**
	 * Returns the y coordinate of the destination point
	 * 
	 * @return the y coordinate of the destination point
	 */
	public int getDestY() {
		return super.getDestY();
	}

	/**
	 * Changes the y coordinate of the destination point
	 * 
	 * @param destY the y coordinate of the destination point
	 */
	public void setDestY(int destY) {
		super.setDestY(destY);
	}
	
	@Override
	public boolean hasPoint(int x, int y) {
		return Geometry.isBetween(new Point(getOriginX(), getOriginY()), new Point(this.getDestX(), this.getDestY()), new Point(x,y));
	}

	@Override
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(getColor());
		g2.setStroke(new BasicStroke(getThickness()));
		g2.drawLine(getOriginX(), getOriginY(), getDestX(), getDestY());
	}

	@Override
	public void move(Point origin, Point dest, int currentX, int currentY) {
		setOriginX(currentX + origin.x);
		setOriginY(currentY + origin.y);
		setDestX(currentX + dest.x);
		setDestY(currentY + dest.y);
	}
}
