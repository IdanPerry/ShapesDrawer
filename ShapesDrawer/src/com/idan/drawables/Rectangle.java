package com.idan.drawables;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import com.idan.utils.Geometry;

/**
 * This class represents a drawable rectangle.
 * 
 * @author Idan Perry
 * @version 03.05.2020
 */

public class Rectangle extends BoundedShape {

	/**
	 * Constructs a rectangle with the specified upper left point, width, height,
	 * line thickness, color and a boolean filled with color attribute.
	 * 
	 * @param upperLeftX the upper left x coordinate of this rectangle
	 * @param upperLeftY the upper left y coordinate of this rectangle
	 * @param width      the width of this rectangle
	 * @param height     the height of this rectangle
	 * @param thicness   the thickness of the outline of this rectangle
	 * @param color      the color of this rectangle
	 * @param filled     whether this rectangle is filled with color
	 */
	public Rectangle(int upperLeftX, int upperLeftY, int width, int height, int thickness, Color color,
			boolean filled) {
		super(upperLeftX, upperLeftY, width, height, thickness, color, filled);
	}

//	@Override
//	public boolean hasPoint(int x, int y) {
//		// upper and lower sides, x should be in the width range
//		if (x >= getX1() - getThickness() && x <= getX1() + getX2() + getThickness()) {
//			// y should be only in the thickness range either on the top or the bottom sides
//			if (Math.abs(y - getY1()) <= getThickness() || Math.abs(y - (getY1() + getY2())) <= getThickness())
//				return true;
//		}
//
//		// right and left sides, y should be in the height range
//		if (y >= getY1() - getThickness() && y <= getY1() + getY2() + getThickness()) {
//			// x should be only in the thickness range either on the left or the right sides
//			if(Math.abs(x - getX1()) <= getThickness() || Math.abs(x - (getX1() + getX2())) <= getThickness())
//				return true;
//		}
//			
//		return false;
//	}
	
	@Override
	public boolean hasPoint(int x, int y) {
		Point leftUpper = new Point(getX1(), getY1());
		Point rightUpper = new Point(getX1() + getX2(), getY1());
		Point leftLower = new Point(getX1(), getY1() + getY2());
		Point rightLower = new Point(getX1() + getX2(),getY1() + getY2());
		return Geometry.isOnRect(leftUpper, rightUpper, leftLower, rightLower, new Point(x, y));
	}

	@Override
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(getColor());
		g2.setStroke(new BasicStroke(getThickness()));

		if (isFilled())
			g2.fillRect(getX1(), getY1(), getX2(), getY2());

		else
			g2.drawRect(getX1(), getY1(), getX2(), getY2());
	}
}
