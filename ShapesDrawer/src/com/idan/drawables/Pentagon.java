package com.idan.drawables;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

public class Pentagon extends Polygon{
    private static final int N_POINTS = 5;
    private Point p1, p2, p3, p4, p5;

    /**
     * Constructs an hexagon with the specified x and y coordinates as respected
     * arrays, width, height, line thickness, color and a boolean filled with color
     * attribute.
     *
     * @param originX   the x coordinate of the source point
     * @param originY   the y coordinate of the source point
     * @param destX     the x coordinate of the destination point
     * @param destY     the y coordinate of the destination point
     * @param thickness the thickness of the outline of this hexagon
     * @param color     the color of this hexagon
     * @param filled    whether this hexagon is filled with color
     */
    public Pentagon(int originX, int originY, int destX, int destY, int thickness, Color color, boolean filled) {
        super(originX, originY, destX, destY, thickness, color, filled);
        initPentagonPoints(originX, originY, destX, destY);

        // initiate properties
        setOriginX(p1.x);
        setOriginY(originY);
        setDestX(destX);
        setDestY(destY);
        setWidth(destX - originX);    // width of a rectangle blocking this hexagon
        setHeight(destY - originY); // height of a rectangle blocking this hexagon

        setXPoints(new int[]{p1.x, p2.x, p3.x, p4.x, p5.x});
        setYPoints(new int[]{p1.y, p2.y, p3.y, p4.y, p5.y});
    }

    /*
     * Initializes the points of an hexagon as seen on x,y axis,
     * starting from top clockwise.
     */
    private void initPentagonPoints(int originX, int originY, int destX, int destY) {
        p1 = new Point(originX + (destX - originX) / 2, originY); // north
        p2 = new Point(destX, originY + 2 * (destY - originY) / 5); // east
        p3 = new Point(destX - (destX - originX) / 5, destY); // south east
        p4 = new Point(originX + (destX - originX) / 5, destY); // south west
        p5 = new Point(originX, originY + 2 * (destY - originY) / 5); // west
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(getColor());
        g2.setStroke(new BasicStroke(getThickness()));

        if (isFilled())
            g2.fillPolygon(getXPoints(), getYPoints(), N_POINTS);
        else
            g2.drawPolygon(getXPoints(), getYPoints(), N_POINTS);
    }

    @Override
    public void move(Point origin, Point dest, int currentX, int currentY) {
        // update new origin and destination points
        setOriginX(currentX + origin.x);
        setOriginY(currentY + origin.y);
        setDestX(currentX + origin.x + getWidth());
        setDestY(currentY + origin.y + getHeight());

        // set new position
        setXPoints(new int[]{ getOriginX(), getOriginX() + getWidth() / 2,
                getOriginX() + getWidth() / 2 - (getWidth() / 5), getOriginX() - getWidth() / 2 + (getWidth() / 5), getOriginX() - getWidth() / 2});
        setYPoints(new int[]{ getOriginY(), getOriginY() + 2 * (getHeight() / 5), getDestY(),
                getDestY(), getOriginY() + 2 * (getHeight() / 5)} );
    }
}
