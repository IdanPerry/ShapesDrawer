package com.idan.painter;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * This class represents a drawable triangle.
 * 
 * @author Idan Perry
 * @version 03.05.2020
 */

public class Triangle extends Polygon {
	private static final int N_POINTS = 3;
	
	/**
	 * Constructs a triangle with the specified upper left point, width, height,
	 * line thickness, color and a boolean filled with color attribute.
	 * 
	 * @param upperLeftX the upper left x coordinate of this triangle
	 * @param upperLeftY the upper left y coordinate of this triangle
	 * @param width      the width of this triangle
	 * @param height     the height of this triangle
	 * @param thicness	 the thickness of the outline of this triangle 
	 * @param color      the color of this triangle
	 * @param filled     whether this triangle is filled with color
	 */
	public Triangle(int[] xPoints, int[] yPoints, int thickness, Color color, boolean filled) {
		super(xPoints, yPoints, thickness, color, filled);	
	}
	
	@Override
	protected void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(getColor());
		g2.setStroke(new BasicStroke(getThickness()));
		
		if(isFilled())
			g2.fillPolygon(getXPoints(), getYPoints(), N_POINTS);

		else
			g2.drawPolygon(getXPoints(), getYPoints(), N_POINTS);
	}
}
