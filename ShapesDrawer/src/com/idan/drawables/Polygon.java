package com.idan.drawables;

import com.idan.utils.Geometry;
import java.awt.Color;
import java.awt.Point;

/**
 * This abstract class represents a polygon.
 *
 * @author Idan Perry
 * @version 03.05.2020
 */

public abstract class Polygon extends BoundedShape {
    private int[] xPoints;
    private int[] yPoints;

    /**
     * Constructs a polygon with the specified boundaries, color
     * and a boolean filled color attribute.
     *
     * @param originX   the x coordinate of the source point
     * @param originY   the y coordinate of the source point
     * @param destX     the x coordinate of the destination point
     * @param destY     the y coordinate of the destination point
     * @param color     the color of the polygon
     * @param thickness the thickness of the outline of this polygon
     * @param filled    whether this polygon is filled with color
     */
    public Polygon(int originX, int originY, int destX, int destY, int thickness, Color color, boolean filled) {
        super(originX, originY, destX, destY, thickness, color, filled);
    }

    /**
     * Constructs a polygon with a default size,
     * color and thickness properties.
     */
    public Polygon() {
        super();
    }

    /**
     * Changes the array of x coordinates of this polygon.
     *
     * @param xPoints the array of x coordinates of this polygon
     */
    public void setXPoints(int[] xPoints) {
        this.xPoints = xPoints;
    }

    /**
     * Returns an array of the x coordinates of this polygon.
     *
     * @return an array of the x coordinates of this polygon
     */
    public int[] getXPoints() {
        return xPoints;
    }

    /**
     * Changes the array of y coordinates of this polygon.
     *
     * @param yPoints the array of y coordinates of this polygon
     */
    public void setYPoints(int[] yPoints) {
        this.yPoints = yPoints;
    }

    /**
     * Returns an array of the y coordinates of this polygon.
     *
     * @return an array of the y coordinates of this polygon
     */
    public int[] getYPoints() {
        return yPoints;
    }

    @Override
    public boolean hasPoint(int x, int y) {
        // init n+1 points. the last point equals the 1st and closes
        // the shape's perimeter.
        Point[] points = new Point[xPoints.length + 1];
        for (int i = 0; i < xPoints.length; i++)
            points[i] = new Point(xPoints[i], yPoints[i]);

        // the n-th+1 point
        points[xPoints.length] = points[0];
        return Geometry.isOnPolygon(points, new Point(x, y));
    }
}
