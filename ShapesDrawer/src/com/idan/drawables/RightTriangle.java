package com.idan.drawables;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 * This class represents a drawable triangle.
 *
 * @author Idan Perry
 * @version 03.05.2020
 */

public class RightTriangle extends Polygon {
    private static final int N_POINTS = 3;

    /**
     * Constructs a triangle with the specified upper left point, width, height,
     * line thickness, color and a boolean filled with color attribute.
     *
     * @param originX   the x coordinate of the source point
     * @param originY   the y coordinate of the source point
     * @param destX     the x coordinate of the destination point
     * @param destY     the y coordinate of the destination point
     * @param thickness the thickness of the outline of this triangle
     * @param color     the color of this triangle
     * @param filled    whether this triangle is filled with color
     */
    public RightTriangle(int originX, int originY, int destX, int destY, int thickness, Color color, boolean filled) {
        super(originX, originY, destX, destY, thickness, color, filled);

        // initiate properties
        setOriginX(originX);
        setOriginY(originY);
        setDestX(destX);
        setDestY(destY);
        setWidth(destX - originX);
        setHeight(destY - originY);

        // set position and size
        setXPoints(new int[]{originX, originX, destX});
        setYPoints(new int[]{originY, destY, destY});
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
        setXPoints(new int[]{ getOriginX(), getOriginX(), getDestX()});
        setYPoints(new int[]{ getOriginY(), getDestY(), getDestY()});
    }
}
