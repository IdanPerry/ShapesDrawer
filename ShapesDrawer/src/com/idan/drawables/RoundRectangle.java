package com.idan.drawables;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import com.idan.utils.Geometry;

/**
 * This class represents a drawable round cornered rectangle.
 * 
 * @author Idan Perry
 * @version 03.05.2020
 */

public class RoundRectangle extends BoundedShape {
	public static final int ARC_WIDTH = 20;
	public static final int ARC_HEIGHT = 20;

	/**
	 * Constructs a round cornered rectangle with the specified upper left point,
	 * width, height, line thickness, color and a boolean filled with color
	 * attribute.
	 * 
	 * @param upperLeftX the upper left x coordinate of this rectangle
	 * @param upperLeftY the upper left y coordinate of this rectangle
	 * @param width      the width of this rectangle
	 * @param height     the height of this rectangle
	 * @param thickness   the thickness of the outline of this rectangle
	 * @param color      the color of this rectangle
	 * @param filled     whether this rectangle is filled with color
	 */
	public RoundRectangle(int upperLeftX, int upperLeftY, int width, int height, int thickness, Color color,
			boolean isFilled) {
		super(upperLeftX, upperLeftY, width, height, thickness, color, isFilled);
	}
	
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

		int arc = Math.max(getX2(), getY2()) / 4;
		
		if (isFilled())
			g2.fillRoundRect(getX1(), getY1(), getX2(), getY2(), arc, arc);

		else
			g2.drawRoundRect(getX1(), getY1(), getX2(), getY2(), arc, arc);
	}

}
