package com.idan.shapesDrawer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import com.idan.shapesDrawer.SelectedShape.DrawingType;

/**
 * This class represents a canvas for drawings.
 * 
 * @author Idan Perry
 * @version 03.05.2020
 */

@SuppressWarnings("serial")
public class Canvas extends JPanel implements MouseListener, MouseMotionListener {
	private static final int BASIC_THICK = 1;
	private static final int DRAW_OFFSET = 5;
	private static final int OVAL_THICK_FIX = 5;
	private static final int LINE_THICK_FIX = 2;
	private static final BasicStroke DASH = new BasicStroke(1.0f, BasicStroke.CAP_ROUND,
			BasicStroke.JOIN_ROUND, 10.0f, new float[] {10.0f}, 0.0f);
	
	private final ArrayList<Shape> shapes;
	private final Cursor crosshair;
	private final Cursor blank;
	private final BufferedImage cursorImg;
	private SelectedShape selectedShape;
	private DrawingType drawingType;	
	private FreeHand freeHand;
	private Point origin;
	private Point current;
	private Color color;
	private int originX;
	private int originY;
	private int currentX;
	private int currentY;
	private int thickness;	
	private boolean filled;

	/**
	 * Constructs a clean canvas for drawings.
	 */
	public Canvas() {
		shapes = new ArrayList<Shape>();
		crosshair = new Cursor(Cursor.CROSSHAIR_CURSOR);
		cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		blank = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0,0), "blank");

		// initialize tools for initiate drawing
		selectedShape = SelectedShape.FREE_HAND;
		drawingType = DrawingType.LINES;
		color = CustomColor.BLACK;
		thickness = BASIC_THICK;

		// customize properties of this canvas
		setBackground(CustomColor.WHITE);
		setBorder(new LineBorder(CustomColor.LIGHT_ORANGE));

		// add listeners
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	/**
	 * Changes the thickness of the drawing tool.
	 * doesn't effect a filled color shapes.
	 * 
	 * @param thickness the thickness of the drawing tool
	 */
	public void setThickness(int thickness) {
		this.thickness = thickness;
	}

	/**
	 * Changes the selected shape to be drawn.
	 * 
	 * @param selectedShape the shape to be drawn
	 */
	public void setSelectedShape(SelectedShape selectedShape) {
		this.selectedShape = selectedShape;
	}

	/**
	 * Changes the color of the drawing tool.
	 * 
	 * @param color the color of the drawing tool
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Changes the filled color attribute of the shape.
	 * doesn't effect lines and free hand drawings.
	 * 
	 * @param filled the filled color attribute of the shape
	 */
	public void setIsFilled(boolean filled) {
		this.filled = filled;
	}
	
	/**
	 * Changes the drawing tool for the free hand drawings.
	 * 
	 * @param drawingType the drawing tool for the free hand drawings
	 */
	public void setSelectedDrawingTool(DrawingType drawingType) {
		this.drawingType = drawingType;
	}

	/**
	 * Returns a list of the shapes drawn untill this point.
	 * 
	 * @return a list of the shapes drawn untill this point
	 */
	public ArrayList<Shape> getShapesList() {
		return shapes;
	}

	/*
	 * Creates a new rectangle on the canvas.
	 */
	private void createRect() {
		shapes.add(new Rectangle(originX, originY, currentX - originX, currentY - originY, thickness, color, filled));
	}

	/*
	 * Creates a new round cornered rectangle on the canvas.
	 */
	private void createRoundRect() {
		shapes.add(
				new RoundRectangle(originX, originY, currentX - originX, currentY - originY, thickness, color, filled));
	}

	/*
	 * Creates a new oval on the canvas.
	 */
	private void createOval() {
		shapes.add(new Oval(originX, originY, currentX - originX, currentY - originY, thickness, color, filled));
	}

	/*
	 * Creates a new isosceles triangle on the canvas.
	 */
	private void createIsoTriangle() {
		shapes.add(new Triangle(new int[] { originX + (currentX - originX) / 2, originX, currentX },
				new int[] { originY, currentY, currentY }, thickness, color, filled));
	}

	/*
	 * Creates a new right triangle on the canvas.
	 */
	private void createRightTriangle() {
		shapes.add(new Triangle(new int[] { originX, originX, currentX }, new int[] { originY, currentY, currentY },
				thickness, color, filled));
	}

	/*
	 * Creates a new hexagon on the canvas.
	 */
	private void createHexagon() {
		int[] points = initHexagonPoints();

		shapes.add(new Hexagon(new int[] { points[0], points[1], points[2], points[1], points[0], points[3] },
				new int[] { points[4], points[4], points[5], points[6], points[6], points[5] },
				thickness, color, filled));
	}
	
	/*
	 * Initializes the points of an hexagon as seen on x,y axis
	 */
	private int[] initHexagonPoints() {
		int x1 = originX + (currentX - originX) / 4; 	   // north west and south west x coordinate
		int x2 = originX + (3 * (currentX - originX) / 4); // north east and south east x
		int x3 = currentX; 								   // east x
		int x4 = originX; 								   // west x
		int y1 = originY; 								   // north west and north east y coordinate
		int y2 = originY + (currentY - originY) / 2; 	   // west and east y
		int y3 = currentY; 								   // south west and south east y
		
		return new int[] {x1, x2, x3, x4, y1, y2, y3};
	}

	/*
	 * Creates a new straight line on the canvas.
	 */
	private void createLine() {
		shapes.add(new Line(originX, originY, currentX, currentY, thickness, color));
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
			
		// keep redrawing all the shapes
		for (Shape shape : shapes) {
			if (shape != null)
				shape.draw(g2);
		}
		
		g2.setColor(color);
		g2.setStroke(DASH);

		// show the outline pulling as a dashed line
		switch (selectedShape) {
		case RECTENGLE:
			g2.drawRect(originX, originY, currentX - originX, currentY - originY);
			break;

		case ROUND_RECTANGLE:
			g2.drawRoundRect(originX, originY, currentX - originX, currentY - originY, RoundRectangle.ARC_WIDTH,
					RoundRectangle.ARC_HEIGHT);
			break;

		case OVAL:
			g2.drawOval(originX, originY, currentX - originX, currentY - originY);
			break;

		case ISO_TRIANGLE:
			g2.drawPolygon(new int[] { originX + (currentX - originX) / 2, originX, currentX },
					new int[] { originY, currentY, currentY }, 3);
			break;

		case RIGHT_TRIANGLE:
			g2.drawPolygon(new int[] { originX, originX, currentX }, new int[] { originY, currentY, currentY }, 3);
			break;

		case HEXAGON:
			int[] points = initHexagonPoints();

			g2.drawPolygon(new int[] { points[0], points[1], points[2], points[1], points[0], points[3] },
					new int[] { points[4], points[4], points[5], points[6], points[6], points[5] }, 6);
			break;

		case LINE:
			g2.drawLine(originX, originY, currentX, currentY);
			break;

		case FREE_HAND:
			// show a following dot to mark the place of the cursor
			if(drawingType == DrawingType.LINES)
				g2.fillOval(currentX, currentY, thickness * LINE_THICK_FIX, thickness * LINE_THICK_FIX);
			else
				g2.fillOval(currentX, currentY, thickness * OVAL_THICK_FIX, thickness * OVAL_THICK_FIX);
			
			// draw
			if(freeHand != null)
				freeHand.draw(g2);
			break;
		}	
	}

	@Override
	public void mousePressed(MouseEvent e) {
		originX = e.getX();
		originY = e.getY();
		currentX = e.getX();
		currentY = e.getY();

		// create new free hand drawing
		if(selectedShape == SelectedShape.FREE_HAND)
			freeHand = new FreeHand();

		repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		currentX = e.getX();
		currentY = e.getY();

		if (current == null)
			origin = e.getPoint();
		else
			origin = current;

		current = e.getPoint();

		// free hand drawing
		if (selectedShape == SelectedShape.FREE_HAND) {
			switch(drawingType) {
			case LINES:
				freeHand.setDrawing(new Line((int) origin.getX(), (int) origin.getY(),
						(int) current.getX(), (int) current.getY(), thickness, color));
				break;
				
			case OVALS:
				freeHand.setDrawing(new Oval((int) origin.getX() - DRAW_OFFSET, (int) origin.getY() - DRAW_OFFSET,
						thickness * OVAL_THICK_FIX, thickness * OVAL_THICK_FIX, thickness, color, true));
				break;
			}

		}


		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		currentX = e.getX();
		currentY = e.getY();

		// create shapes only if the mouse was dragged
		if (currentX != originX || currentY != originY) {
			switch (selectedShape) {
			case RECTENGLE:
				createRect();
				break;

			case ROUND_RECTANGLE:
				createRoundRect();
				break;

			case OVAL:
				createOval();
				break;

			case ISO_TRIANGLE:
				createIsoTriangle();
				break;

			case RIGHT_TRIANGLE:
				createRightTriangle();
				break;

			case HEXAGON:
				createHexagon();
				break;

			case LINE:
				createLine();
				break;

			case FREE_HAND:
				shapes.add(freeHand);
				freeHand = null;
				break;
			}
			
			current = null;
			repaint();
		}
		
		// prevent the dashed line from being redrawn
		originX = e.getX();
		originY = e.getY();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if(selectedShape == SelectedShape.FREE_HAND)
			setCursor(blank); // the cursor is set to be a dot
		else
			setCursor(crosshair);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// update cursor position to show a drawing dot
		if(selectedShape == SelectedShape.FREE_HAND) {
			currentX = e.getX();
			currentY = e.getY();
			repaint();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
}
