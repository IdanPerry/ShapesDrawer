package com.idan.painter;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * This class represents a drawable hexagon.
 * 
 * @author Idan Perry
 * @version 03.05.2020
 *
 */

public class Hexagon extends Polygon {
	private static final int N_POINTS = 6;

	/**
	 * Constructs an hexagon with the specified x and y coordinates as respected
	 * arrays, width, height, line thickness, color and a boolean filled with color
	 * attribute.
	 * 
	 * @param xPoints   an array of the x coordinates
	 * @param yPoints   an array of the y coordinates
	 * @param thickness the thickness of the outline of this hexagon
	 * @param color     the color of this hexagon
	 * @param thickness the thickness of the outline of this hexagon
	 * @param filled    whether this hexagon is filled with color
	 */
	public Hexagon(int[] xPoints, int[] yPoints, int thickness, Color color, boolean filled) {
		super(xPoints, yPoints, thickness, color, filled);
	}

	@Override
	protected void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(getColor());
		g2.setStroke(new BasicStroke(getThickness()));

		if (isFilled())
			g2.fillPolygon(getXPoints(), getYPoints(), N_POINTS);

		else
			g2.drawPolygon(getXPoints(), getYPoints(), N_POINTS);
	}
}
