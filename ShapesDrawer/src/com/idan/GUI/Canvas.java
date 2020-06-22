package com.idan.GUI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Stack;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.idan.constants.CustomColor;
import com.idan.constants.DrawingTool;
import com.idan.constants.Mode;
import com.idan.constants.SelectedShape;
import com.idan.drawables.FreeHand;
import com.idan.drawables.Hexagon;
import com.idan.drawables.Line;
import com.idan.drawables.Oval;
import com.idan.drawables.Rectangle;
import com.idan.drawables.RoundRectangle;
import com.idan.drawables.Shape;
import com.idan.drawables.Triangle;

/**
 * This class represents a canvas for drawings.
 * 
 * @author Idan Perry
 * @version 03.05.2020
 */

@SuppressWarnings("serial")
public class Canvas extends JPanel implements MouseListener, MouseMotionListener {
	private static final int BASIC_THICK = 1;
	public static final int OVAL_THICK_FIX = 5;
	public static final int LINE_THICK_FIX = 2;
	private static final BufferedImage CURSOR_IMG = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
	private static final Cursor CROSSHAIR = new Cursor(Cursor.CROSSHAIR_CURSOR);;
	private static final Cursor BLANK = Toolkit.getDefaultToolkit().createCustomCursor(CURSOR_IMG, new Point(0, 0), "blank");
	private static final Cursor MOVING = new Cursor(Cursor.MOVE_CURSOR);
	private static final BasicStroke DASH = new BasicStroke(1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 10.0f, new float[] { 10.0f }, 0.0f);

	private final Stack<Shape> shapes;
	private final Stack<Shape> restored;
	private BufferedImage chalkboard;
	private SelectedShape selectedShape;
	private DrawingTool drawingTool;
	private FreeHand freeHand;
	private Point originPoint;
	private Point currentPoint;
	private Color color;
	private int originX;
	private int originY;
	private int currentX;
	private int currentY;
	private int changeOriginX;
	private int changeOriginY;
	private int changeDestX;
	private int changeDestY;
	private int thickness;
	private boolean filled;
	private Mode mode;
	private Shape dragShape;

	/**
	 * Constructs a canvas with optional custom background for drawings.
	 */
	public Canvas() {
		shapes = new Stack<Shape>();
		restored = new Stack<Shape>();
		mode = Mode.DRAW;

		// initialize tools for initiate drawing
		selectedShape = SelectedShape.FREE_HAND;
		drawingTool = DrawingTool.PENCIL;
		color = CustomColor.BLACK;
		thickness = BASIC_THICK;

		// customize properties of this canvas
		setBackground(CustomColor.LIGHTER_GRAY);

		try {
			// canvas background
			chalkboard = ImageIO.read(getClass().getResourceAsStream("/chalkboard2.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// add listeners
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	/**
	 * Changes the thickness of the drawing tool. doesn't effect a filled color
	 * shapes.
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
	 * Changes the filled color attribute of the shape. doesn't effect lines and
	 * free hand drawings.
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
	public void setSelectedDrawingTool(DrawingTool drawingType) {
		this.drawingTool = drawingType;
	}

	/**
	 * Removes the last added drawing.
	 */
	public void remove() {
		if (!shapes.isEmpty()) {
			restored.push(shapes.pop());
			repaint();
		}
	}

	/**
	 * Removes all the drawings from this canvas.
	 */
	public void clear() {
		shapes.clear();
		repaint();
	}

	/**
	 * Restors last deleted drawing.
	 */
	public void restore() {
		if (!restored.isEmpty()) {
			shapes.push(restored.pop());
			repaint();
		}
	}

	/**
	 * Switches between seleced mode and drawing mode.
	 * 
	 * @param mode selected or drawing mode
	 */
	public void setMode(Mode mode) {
		this.mode = mode;
	}

	/*
	 * Creates a new rectangle on the canvas.
	 */
	private void createRect() {
		shapes.push(new Rectangle(originX, originY, currentX - originX, currentY - originY, thickness, color, filled));
	}

	/*
	 * Creates a new round cornered rectangle on the canvas.
	 */
	private void createRoundRect() {
		shapes.push(
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
		shapes.push(new Triangle(new int[] { originX + (currentX - originX) / 2, originX, currentX },
				new int[] { originY, currentY, currentY }, thickness, color, filled));
	}

	/*
	 * Creates a new right triangle on the canvas.
	 */
	private void createRightTriangle() {
		shapes.push(new Triangle(new int[] { originX, originX, currentX }, new int[] { originY, currentY, currentY },
				thickness, color, filled));
	}

	/*
	 * Creates a new hexagon on the canvas.
	 */
	private void createHexagon() {
		int[] points = initHexagonPoints();

		shapes.push(new Hexagon(new int[] { points[0], points[1], points[2], points[1], points[0], points[3] },
				new int[] { points[4], points[4], points[5], points[6], points[6], points[5] }, thickness, color,
				filled));
	}

	/*
	 * Initializes the points of an hexagon as seen on x,y axis
	 */
	private int[] initHexagonPoints() {
		int x1 = originX + (currentX - originX) / 4; // north west and south west x coordinate
		int x2 = originX + (3 * (currentX - originX) / 4); // north east and south east x
		int x3 = currentX; // east x
		int x4 = originX; // west x
		int y1 = originY; // north west and north east y coordinate
		int y2 = originY + (currentY - originY) / 2; // west and east y
		int y3 = currentY; // south west and south east y

		return new int[] { x1, x2, x3, x4, y1, y2, y3 };
	}

	/*
	 * Creates a new straight line on the canvas.
	 */
	private void createLine() {
		shapes.push(new Line(originX, originY, currentX, currentY, thickness, color));
	}

	/*
	 * keeps redrawing all the shapes.
	 */
	private void redraw(Graphics2D g2) {
		for (Shape shape : shapes) {
			if (shape != null)
				shape.draw(g2);
		}
	}

	/*
	 * shows a following dot to mark the place of the cursor.
	 */
	private void showDotCursor(Graphics2D g2) {
		if (drawingTool == DrawingTool.PENCIL)
			g2.fillOval(currentX, currentY, thickness * LINE_THICK_FIX, thickness * LINE_THICK_FIX);
		else
			g2.fillOval(currentX, currentY, thickness * OVAL_THICK_FIX, thickness * OVAL_THICK_FIX);
	}

	/*
	 * Shows the outline pulling as a dashed line.
	 */
	private void drawPullingOutline(Graphics2D g2) {
		switch (selectedShape) {
		case RECTANGLE:
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
			showDotCursor(g2);
			// draw
			if (freeHand != null)
				freeHand.draw(g2);
			break;
		}
	}

	/*
	 * Free hand drawing.
	 */
	private void draw(Point point) {
		if (currentPoint == null)
			originPoint = point;
		else
			originPoint = currentPoint;

		currentPoint = point;
		freeHand.setDrawing(drawingTool, originPoint, currentPoint, color, thickness);
	}

	/*
	 * Creates the desired shape or free hand drawing and shows it on the canvas
	 * surface.
	 */
	private void putDrawingOnCanvas() {
		switch (selectedShape) {
		case RECTANGLE:
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

		repaint();
	}

	/*
	 * Searches a drawing to match the coordinates of the pressed area.
	 */
	private void find(MouseEvent e) {
		for (Shape shape : shapes) {
			if (shape.hasPoint(originX, originY)) {				
				if(SwingUtilities.isRightMouseButton(e))
					new DrawingEditor(this, shape);
				
				else {
					dragShape = shape;
					changeOriginX = shape.getX1() - currentX;
					changeOriginY = shape.getY1() - currentY;
					changeDestX = shape.getX2() - currentX;
					changeDestY = shape.getY2() - currentY;
					
					setCursor(MOVING);
				}
				
				break;
			}
		}
	}

	/*
	 * Drags the drawing to the current mouse cursor position.
	 */
	private void drag() {
		if (dragShape != null) {
			dragShape.setX1(currentX + changeOriginX);
			dragShape.setY1(currentY + changeOriginY);
			
			if(dragShape instanceof Line) {
				dragShape.setX2(currentX + changeDestX);
				dragShape.setY2(currentY + changeDestY);
			}
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(chalkboard, 0, 0, null);

		// enable antialiasing
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		redraw(g2);

		if (mode == Mode.DRAW) {
			g2.setColor(color);
			g2.setStroke(DASH);
			drawPullingOutline(g2);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// prevent drawing the path from last recorded point to the current
		currentPoint = null;

		// collect initail coordinates
		currentX = originX = e.getX();
		currentY = originY = e.getY();

		switch (mode) {
		case SELECT:
			find(e);
			break;

		case DRAW:
			// create new free hand drawing
			if (selectedShape == SelectedShape.FREE_HAND)
				freeHand = new FreeHand();
			break;
		}

		repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// shapes coordinates
		currentX = e.getX();
		currentY = e.getY();

		switch (mode) {
		case SELECT:
			drag();
			break;

		case DRAW:
			if (selectedShape == SelectedShape.FREE_HAND)
				draw(e.getPoint());
			break;
		}

		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		switch (mode) {
		case SELECT:
			dragShape = null;
			setCursor(Cursor.getDefaultCursor());
			break;

		case DRAW:
			currentX = e.getX();
			currentY = e.getY();

			// create shapes only if the mouse was dragged
			if (currentX != originX || currentY != originY)
				putDrawingOnCanvas();

			// prevent the dashed line from being redrawn
			originX = e.getX();
			originY = e.getY();
			break;
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		switch (mode) {
		case SELECT:
			setCursor(Cursor.getDefaultCursor());
			break;

		case DRAW:
			if (selectedShape == SelectedShape.FREE_HAND)
				// the cursor is set to be a dot
				setCursor(BLANK);
			else
				setCursor(CROSSHAIR);
			break;
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (mode == Mode.SELECT)
			return;

		// update cursor position to show a drawing dot
		if (selectedShape == SelectedShape.FREE_HAND) {
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
