package com.idan.drawables;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Random;

import com.idan.GUI.Canvas;
import com.idan.constants.DrawingTool;
import com.idan.interfaces.Drawable;

/**
 * This class represents a free hand drawing.
 * 
 * @author Idan Perry
 * @version 03.05.2020
 */

public class FreeHand extends Shape implements Drawable {
	private static final int DRAW_OFFSET = 5;
	private static final int BUFFER_SIZE = 1000;
	public static final int AIRBRUSH_SPREAD = 5;
	public static final int AIRBRUSH_AMOUNT = 4;
	public static final int ARTISTIC_DENSE = 3;
	public static final int HORIZONTAL_ARTISTIC_ANGLE = 7;
	public static final int VERTICAL_ARTISTIC_ANGLE = 4;

	private final Shape[] drawing;
	private final Random random;

	private int index;

	/**
	 * Constructs a clean free hand drawing object.
	 */
	public FreeHand() {
		drawing = new Shape[BUFFER_SIZE];
		random = new Random();
	}

	/**
	 * Returns the free hand drawing as a list of shapes.
	 * 
	 * @return the free hand drawing as a list of shapes
	 */
	public Shape[] getDrawing() {
		return drawing;
	}

	/**
	 * Sets a free hand drawing with the specified shape.
	 * 
	 * @param shape the shape to form the drawing
	 */
	public void setDrawing(Shape shape) {
		if (index < drawing.length)
			drawing[index++] = shape;
	}

	/**
	 * Sets a free hand drawing with the specified drawing tool, points, color and
	 * line thickness.
	 * 
	 * @param drawingTool the tool to draw with
	 * @param origin      the origin point of the shape that forms the drawing
	 * @param dest        the destination point (width/height if the shape is other
	 *                    than a line) of the shape
	 * @param color       the color of the drawing
	 * @param thickness   the thickness of the line of the shape (or just the line)
	 */
	public void setDrawing(DrawingTool drawingTool, Point origin, Point dest, Color color, int thickness) {
		switch (drawingTool) {
		case PENCIL:
			setDrawing(new Line((int) origin.getX(), (int) origin.getY(), (int) dest.getX(), (int) dest.getY(),
					thickness, color));
			break;

		case BRUSH:
			setDrawing(new Oval((int) origin.getX() - DRAW_OFFSET, (int) origin.getY() - DRAW_OFFSET,
					thickness * Canvas.OVAL_THICK_FIX, thickness * Canvas.OVAL_THICK_FIX, thickness, color, true));
			break;

		case AIRBRUSH:
			for (int i = 0; i < thickness * AIRBRUSH_AMOUNT; i++)
				setDrawing(new Oval((int) origin.getX() + random.nextInt(thickness * AIRBRUSH_SPREAD),
						(int) origin.getY() + random.nextInt(thickness * AIRBRUSH_SPREAD), 2, 2, 0, color, true));
			break;

		case ARTISTIC:
			for (int i = 0; i < ARTISTIC_DENSE; i++)
				setDrawing(new Line((int) origin.getX() + random.nextInt(thickness * HORIZONTAL_ARTISTIC_ANGLE),
						(int) origin.getY(), (int) dest.getX(),
						(int) dest.getY() + random.nextInt(thickness * VERTICAL_ARTISTIC_ANGLE), thickness, color));
			break;
		}
	}

	@Override
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(getColor());
		g2.setStroke(new BasicStroke(getThickness()));

		for (Shape shape : drawing) {
			if (shape != null)
				shape.draw(g2);
		}
	}

	@Override
	public void move(Point origin, Point dest, int currentX, int currentY) {

	}
}
