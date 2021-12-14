package com.idan.GUI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.Stack;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.idan.constants.MenuItem;
import com.idan.constants.CustomColor;
import com.idan.constants.DrawingTool;
import com.idan.constants.Mode;
import com.idan.constants.SelectedShape;
import com.idan.drawables.FreeDraw;
import com.idan.drawables.Hexagon;
import com.idan.drawables.Line;
import com.idan.drawables.Oval;
import com.idan.drawables.Rectangle;
import com.idan.drawables.RoundRectangle;
import com.idan.drawables.Shape;
import com.idan.drawables.RightTriangle;
import com.idan.drawables.IsoTriangle;
import com.idan.drawables.Pentagon;

/**
 * This class represents a canvas for drawings.
 *
 * @author Idan Perry
 * @version 03.05.2020
 */

public class Canvas extends JPanel implements MouseListener, MouseMotionListener, ActionListener {
    private static final int BASIC_THICK = 1;
    public static final int OVAL_THICK_FIX = 3;
    public static final int LINE_THICK_FIX = 2;
    private static final int MOUSE_POS_X = 15;
    private static final int MOUSE_POS_Y = 15;
    private static final BufferedImage CURSOR_IMG = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
    private static final Cursor CROSSHAIR = new Cursor(Cursor.CROSSHAIR_CURSOR);
    private static final Cursor BLANK = Toolkit.getDefaultToolkit().createCustomCursor(CURSOR_IMG, new Point(0, 0), "blank");
    private static final Cursor MOVING = new Cursor(Cursor.MOVE_CURSOR);
    private static final BasicStroke DASH = new BasicStroke(1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 10.0f, new float[]{10.0f}, 0.0f);

    private JFileChooser file;

    private final DrawingEditor editor;
    private final Stack<Shape> drawings;
    private final Stack<Shape> restored;
    private JPopupMenu menu;
    private JMenuItem[] items;
    private BufferedImage board;
    private SelectedShape selectedShape;
    private DrawingTool drawingTool;
    private FreeDraw freeDraw;
    private Point originPoint;
    private Point currentPoint;
    private Color color;
    private int originX;
    private int originY;
    private int currentX;
    private int currentY;
    private int changeOriginX;      // x difference of current mouse position and shape origin
    private int changeOriginY;      // y difference of current mouse position and shape origin
    private int changeDestX;
    private int changeDestY;
    private int thickness;
    private boolean filled;
    private boolean pressed;        // mouse pressed state
    private Mode mode;
    private Shape selected;
    private Shape detected;

    /**
     * Constructs a canvas with optional custom background for drawings.
     */
    public Canvas() {
        drawings = new Stack<Shape>();
        restored = new Stack<Shape>();
        editor = DrawingEditor.getInstance();
        editor.initEditor(this);
        mode = Mode.DRAW;

        // initialize tools for initiate drawing
        selectedShape = SelectedShape.FREE_DRAW;
        drawingTool = DrawingTool.PENCIL;
        color = CustomColor.BLACK;
        thickness = BASIC_THICK;

        // customize properties of this canvas
        setBackground(CustomColor.LIGHTER_GRAY);
        initPopupMenu();
        setBoard("chalkboard2");

        // add listeners
        addMouseListener(this);
        addMouseMotionListener(this);

        setImageSaving();
    }

    /**
     *
     */
    public void save() {
        BufferedImage img  = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        Graphics g = img.createGraphics();
        paint(g);

        int returnVal = file.showSaveDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                ImageIO.write(img, "PNG", file.getSelectedFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     */
    public void setBoard(String board) {
        try {
            // canvas background
            this.board = ImageIO.read(getClass().getResourceAsStream("/" + board + ".jpg"));
            repaint();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
     * Returns the current color set on this canvas.
     *
     * @return the current color set on this canvas
     */
    public Color getColor() {
        return color;
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
        if (!drawings.isEmpty()) {
            restored.push(drawings.pop());
            repaint();
        }
    }

    /**
     * Removes all the drawings from this canvas.
     */
    public void clear() {
        drawings.clear();
        repaint();
    }

    /**
     * Restores last deleted drawing.
     */
    public void restore() {
        if (!restored.isEmpty()) {
            drawings.push(restored.pop());
            repaint();
        }
    }

    /**
     * Switches between selected mode and drawing mode.
     *
     * @param mode selected or drawing mode
     */
    public void setMode(Mode mode) {
        this.mode = mode;
    }

    /*
     *
     */
    private void setImageSaving() {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG Images", "png");
        file = new JFileChooser();
        file.setSelectedFile(new File("./drawing.png"));
        file.setFileFilter(filter);
    }

    /*
     * Initializes a popup menu out of a selected drawing with
     * drawing related options.
     */
    private void initPopupMenu() {
        menu = new JPopupMenu();
        items = new JMenuItem[MenuItem.values().length];

        int i = 0;
        for (MenuItem item : MenuItem.values()) {
            items[i] = new JMenuItem();
            items[i].setOpaque(true);
            items[i].setBackground(CustomColor.LIGHT_BLACK);
            items[i].setForeground(CustomColor.LIGHT_GRAY);
            items[i].setText(item.toString());
            items[i].addActionListener(this);
            menu.add(items[i++]);
        }

        menu.setBorder(BorderFactory.createLineBorder(CustomColor.GRAY));
    }

    /*
     * Creates a new rectangle on the canvas.
     */
    private void createRect() {
        drawings.push(new Rectangle(originX, originY, currentX - originX, currentY - originY, thickness, color, filled));
    }

    /*
     * Creates a new round cornered rectangle on the canvas.
     */
    private void createRoundRect() {
        drawings.push(
                new RoundRectangle(originX, originY, currentX - originX, currentY - originY, thickness, color, filled));
    }

    /*
     * Creates a new oval on the canvas.
     */
    private void createOval() {
        drawings.push(new Oval(originX, originY, currentX - originX, currentY - originY, thickness, color, filled));
    }

    /*
     * Creates a new isosceles triangle on the canvas.
     */
    private void createIsoTriangle() {
        drawings.push(new IsoTriangle(originX, originY, currentX, currentY, thickness, color, filled));
    }

    /*
     * Creates a new right triangle on the canvas.
     */
    private void createRightTriangle() {
        drawings.push(new RightTriangle(originX, originY, currentX, currentY, thickness, color, filled));
    }

    private void createPentagon() {
        drawings.push(new Pentagon(originX, originY, currentX, currentY, thickness, color, filled));
    }

    /*
     * Initializes the points of an hexagon as seen on x,y axis
     */
    private int[] initPentagonPoints() {
        int x1 = originX + (currentX - originX) / 2;        // north x coordinate
        int x2 = currentX;                                    // east x
        int x3 = currentX - (currentX - originX) / 5;        // south east x
        int x4 = originX + (currentX - originX) / 5;        // south west x
        int x5 = originX;                                    // west
        int y1 = originY;                                    // north y coordinate
        int y2 = originY + 2 * (currentY - originY) / 5;    // west and east y
        int y3 = currentY;                                    // south west and south east y

        return new int[]{x1, x2, x3, x4, x5, y1, y2, y3};
    }

    /*
     * Creates a new hexagon on the canvas.
     */
    private void createHexagon() {
        drawings.push(new Hexagon(originX, originY, currentX, currentY, thickness, color, filled));
    }

    /*
     * Initializes the points of an hexagon as seen on x,y axis
     */
    private int[] initHexagonPoints() {
        int x1 = originX + (currentX - originX) / 4;        // north west and south west x coordinate
        int x2 = originX + (3 * (currentX - originX) / 4);    // north east and south east x
        int x3 = currentX;                                    // east x
        int x4 = originX;                                    // west x
        int y1 = originY;                                    // north west and north east y coordinate
        int y2 = originY + (currentY - originY) / 2;        // west and east y
        int y3 = currentY;                                    // south west and south east y

        return new int[]{x1, x2, x3, x4, y1, y2, y3};
    }

    /*
     * Creates a new straight line on the canvas.
     */
    private void createLine() {
        drawings.push(new Line(originX, originY, currentX, currentY, thickness, color));
    }

    /*
     * Keeps redrawing all the shapes.
     */
    private void redraw(Graphics2D g2) {
        for (Shape drawing : drawings) {
            if (drawing != null)
                drawing.draw(g2);
        }
    }

    /*
     * Shows a following dot to mark the place of the cursor.
     */
    private void showDotCursor(Graphics2D g2) {
        int diameter;

        if (selectedShape != SelectedShape.FREE_DRAW || drawingTool == DrawingTool.PENCIL)
            diameter = thickness * LINE_THICK_FIX;
        else
            diameter = thickness * OVAL_THICK_FIX;

        g2.fillOval(currentX - diameter/2, currentY - diameter/2, diameter, diameter);
    }

    /*
     * Shows the outline pulling as a dashed line.
     */
    private void drawPullingOutline(Graphics2D g2) {
        g2.setStroke(DASH);

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
                g2.drawPolygon(new int[]{originX + (currentX - originX) / 2, originX, currentX},
                        new int[]{originY, currentY, currentY}, 3);
                break;

            case RIGHT_TRIANGLE:
                g2.drawPolygon(new int[]{originX, originX, currentX}, new int[]{originY, currentY, currentY}, 3);
                break;

            case PENTAGON:
                int[] pPoints = initPentagonPoints();
                g2.drawPolygon(new int[]{pPoints[0], pPoints[1], pPoints[2], pPoints[3], pPoints[4]},
                        new int[]{pPoints[5], pPoints[6], pPoints[7], pPoints[7], pPoints[6]}, 5);
                break;

            case HEXAGON:
                int[] hPoints = initHexagonPoints();
                g2.drawPolygon(new int[]{hPoints[0], hPoints[1], hPoints[2], hPoints[1], hPoints[0], hPoints[3]},
                        new int[]{hPoints[4], hPoints[4], hPoints[5], hPoints[6], hPoints[6], hPoints[5]}, 6);
                break;

            case LINE:
                g2.drawLine(originX, originY, currentX, currentY);
                break;

            case FREE_DRAW:
                if (freeDraw != null)
                    freeDraw.draw(g2);
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
        freeDraw.setDrawing(drawingTool, originPoint, currentPoint, color, thickness);
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

            case PENTAGON:
                createPentagon();
                break;

            case HEXAGON:
                createHexagon();
                break;

            case LINE:
                createLine();
                break;

            case FREE_DRAW:
                drawings.add(freeDraw);
                freeDraw = null;
                break;
        }

        repaint();
    }

    /*
     * Searches a drawing to match the coordinates of the pressed area.
     */
    private void selectDrawing(MouseEvent e) {
        if (detected != null) {
            selected = detected;
            if (SwingUtilities.isRightMouseButton(e))
                menu.show(this, e.getX(), e.getY());

            else {
                changeOriginX = detected.getOriginX() - currentX;
                changeOriginY = detected.getOriginY() - currentY;
                changeDestX = detected.getDestX() - currentX;
                changeDestY = detected.getDestY() - currentY;
            }
        }
    }

    /*
     * Returns the drawing the cursor is pointing at. otherwise
     * if the cursor doesn't point at any drawing - returns null.
     */
    private Shape detectDrawing() {
        for (Shape drawing : drawings) {
            if (drawing.hasPoint(currentX, currentY)) {
                setCursor(MOVING);
                return drawing;
            } else
                setCursor(Cursor.getDefaultCursor());
        }

        return null;
    }

    /*
     * Makes a copy of the selected drawing.
     */
    private void cloneDrawing() {
        try {
            drawings.push((Shape) detected.clone());
            repaint();
        } catch (CloneNotSupportedException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(board, 0, 0, null);
        g2.setFont(new Font("SansSerif", Font.PLAIN, 13));
        g2.setColor(CustomColor.WHITE);
        // enable antialiasing
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // draw current mouse position coordinates
        g2.drawString(currentX + "x" + currentY + "px", MOUSE_POS_X, getHeight() - MOUSE_POS_Y);
        redraw(g2);

        if (mode == Mode.DRAW) {
            g2.setColor(color);
            showDotCursor(g2);
            if (pressed)
                drawPullingOutline(g2);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        pressed = true;
        // prevent drawing the path from last recorded point to the current
        currentPoint = null;

        // collect initial coordinates
        currentX = originX = e.getX();
        currentY = originY = e.getY();

        switch (mode) {
            case SELECT:
                selectDrawing(e);
                break;

            case DRAW:
                // create new free hand drawing
                if (selectedShape == SelectedShape.FREE_DRAW)
                    freeDraw = new FreeDraw();
                break;
        }

        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        currentX = e.getX();
        currentY = e.getY();

        switch (mode) {
            case SELECT:
                if (selected != null && SwingUtilities.isLeftMouseButton(e))
                    selected.move(new Point(changeOriginX, changeOriginY), new Point(changeDestX, changeDestY), currentX, currentY);
                break;

            case DRAW:
                if (selectedShape == SelectedShape.FREE_DRAW)
                    draw(e.getPoint());
                break;
        }

        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        pressed = false;

        switch (mode) {
            case SELECT:
                selected = null;
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
                if (selectedShape == SelectedShape.FREE_DRAW)
                    // the cursor is set to be a dot
                    setCursor(BLANK);
                else
                    setCursor(CROSSHAIR);
                break;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        currentX = e.getX();
        currentY = e.getY();
        repaint();

        if (mode == Mode.SELECT)
            detected = detectDrawing();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // open adjust drawing editor
        if (e.getSource() == items[MenuItem.ADJUST.getIndex()]) {
            editor.setDrawing(detected);
            editor.setSize();
            editor.setColor();
            editor.setVisible(true);
        } else if (e.getSource() == items[MenuItem.CLONE.getIndex()])
            cloneDrawing();

            // delete selected drawing
        else if (e.getSource() == items[MenuItem.REMOVE.getIndex()]) {
            drawings.remove(detected);
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
