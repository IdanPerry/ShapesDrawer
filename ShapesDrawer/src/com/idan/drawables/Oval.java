package com.idan.drawables;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import com.idan.utils.Geometry;

/**
 * This class represent a drawable oval.
 * 
 * @author Idan Perry
 * @version 03.05.2020
 */

public class Oval extends BoundedShape {

	/**
	 * Constructs an oval with the specified upper left point of the blocking
	 * rectangle, width, height color, line thickness and a boolean filled with color
	 * attribute.
	 * 
	 * @param upperLeftX the upper left x coordinate of the blocking rectangle
	 * @param upperLeftY the upper left y coordinate of the blocking rectangle
	 * @param width      the width of the blocking rectangle
	 * @param height     the height of the blocking rectangle
	 * @param thickness  the thickness of the outline of this oval 
	 * @param color      the color of this oval
	 * @param filled     whether this oval is filled with color
	 */
	public Oval(int upperLeftX, int upperLeftY, int width, int height, int thickness, Color color, boolean filled) {
		super(upperLeftX, upperLeftY, width, height, thickness, color, filled);
	}
	
	public boolean hasPoint(int x, int y) {
		return Geometry.isOnEllipse(getOriginX(), getOriginY(), getDestX(), getDestY(), new Point(x, y));
	}
	
	@Override
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(getColor());
		g2.setStroke(new BasicStroke(getThickness()));
		
		if(isFilled())
			g2.fillOval(getOriginX(), getOriginY(), getDestX(), getDestY());
		else
			g2.drawOval(getOriginX(), getOriginY(), getDestX(), getDestY());
	}

	@Override
	public void move(Point origin, Point dest, int currentX, int currentY) {
		setOriginX(currentX + origin.x);
		setOriginY(currentY + origin.y);
	}
}
