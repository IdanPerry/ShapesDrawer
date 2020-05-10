package com.idan.shapesDrawer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

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
	 * @param thicness   the thickness of the outline of this rectangle
	 * @param color      the color of this rectangle
	 * @param filled     whether this rectangle is filled with color
	 */
	public RoundRectangle(int upperLeftX, int upperLeftY, int width, int height, int thickness, Color color,
			boolean isFilled) {
		super(upperLeftX, upperLeftY, width, height, thickness, color, isFilled);
	}

	@Override
	protected void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(getColor());
		g2.setStroke(new BasicStroke(getThickness()));

		if (isFilled())
			g2.fillRoundRect(getX1(), getY1(), getX2(), getY2(), ARC_WIDTH, ARC_HEIGHT);

		else
			g2.drawRoundRect(getX1(), getY1(), getX2(), getY2(), ARC_WIDTH, ARC_HEIGHT);
	}

}
