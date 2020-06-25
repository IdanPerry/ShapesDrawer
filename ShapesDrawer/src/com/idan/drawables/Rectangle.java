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
	 * @param thickness   the thickness of the outline of this rectangle
	 * @param color      the color of this rectangle
	 * @param filled     whether this rectangle is filled with color
	 */
	public Rectangle(int upperLeftX, int upperLeftY, int width, int height, int thickness, Color color,
			boolean filled) {
		super(upperLeftX, upperLeftY, width, height, thickness, color, filled);
	}

	@Override
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(getColor());
		g2.setStroke(new BasicStroke(getThickness()));

		if (isFilled())
			g2.fillRect(getOriginX(), getOriginY(), getDestX(), getDestY());
		else
			g2.drawRect(getOriginX(), getOriginY(), getDestX(), getDestY());
	}

	@Override
	public void move(Point origin, Point dest, int currentX, int currentY) {
		setOriginX(currentX + origin.x);
		setOriginY(currentY + origin.y);
	}
}
