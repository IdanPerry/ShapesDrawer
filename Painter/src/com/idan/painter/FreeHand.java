package com.idan.painter;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 * This class represents a free hand drawing.
 * 
 * @author Idan Perry
 * @version 03.05.2020
 */

public class FreeHand extends Shape {
	private ArrayList<Shape> drawing;

	/**
	 * Constructs a clean free hand drawing object.
	 */
	public FreeHand() {
		drawing = new ArrayList<Shape>();		
	}
	
	/**
	 * Returns the free hand drawing as a list of shapes.
	 * 
	 * @return the free hand drawing as a list of shapes
	 */
	public ArrayList<Shape> getDrawing() {
		return drawing;
	}
	
	/**
	 * Sets the free hand drawing by adding shapes to the shapes list.
	 * 
	 * @param shape the shape to add to the list that forms the drawing.
	 */
	public void setDrawing(Shape shape) {
		drawing.add(shape);
	}
	
	@Override
	protected void draw(Graphics g) {	
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(getColor());
		g2.setStroke(new BasicStroke(getThickness()));
		
		for (Shape shape : drawing) {
			shape.draw(g2);
		}
	}
}
