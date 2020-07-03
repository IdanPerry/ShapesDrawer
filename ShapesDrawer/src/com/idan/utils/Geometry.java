package com.idan.utils;

import java.awt.Point;

/**
 * This class contains static methods which handles geometry related
 * calculations.
 *
 * @author Idan Perry
 * @version 22.06.2020
 */

public class Geometry {
    private static final double POINT_OFFSET = 0.1;
    private static final double INSIDE_ELLIPSE_OFFSET = 0.9;
    private static final double OUTSIDE_ELLIPSE_OFFSET = 1.1;

    /**
     * Returns the distance between two given points.
     *
     * @param a point a
     * @param b point b
     * @return the distance between two given points
     */
    public static double distance(Point a, Point b) {
        return Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2));
    }

    /**
     * Returns true if a given point c is between points a and b. Returns false
     * otherwise.
     *
     * @param a point a
     * @param b point b
     * @param c point c to determine if its between a and b
     * @return true if point c is between points a and b, false otherwise
     */
    public static boolean isBetween(Point a, Point b, Point c) {
        return Math.abs(distance(a, c) + distance(c, b) - distance(a, b)) <= POINT_OFFSET;
    }

    /**
     * Returns true if a given point is in the boundaries of a given rectangle
     * perimeter. Returns false otherwise.
     *
     * @param upperLeft  the upper left point of the rectangle
     * @param upperRight the upper right point of the rectangle
     * @param lowerLeft  the lower left point of the rectangle
     * @param lowerRight the lower right point of the rectangle
     * @param point      the point to determine if its in the rect boundaries
     * @return true if the given point is in the boundaries of the rectangle
     * perimeter, false other wise
     */
    public static boolean isOnRect(Point upperLeft, Point upperRight, Point lowerLeft, Point lowerRight, Point point) {
        return isBetween(upperLeft, upperRight, point) || isBetween(upperLeft, lowerLeft, point)
                || isBetween(lowerLeft, lowerRight, point) || isBetween(upperRight, lowerRight, point);
    }

    /**
     * Returns the center point of a given ellipse.
     *
     * @param x      the x coordinate of the upper left point of the rectangle
     *               blocking the given ellipse
     * @param y      the y coordinate of the upper left point of the rectangle
     *               blocking the given ellipse
     * @param width  the width of the rectangle blocking the given ellipse
     * @param height the height of the rectangle blocking the given ellipse
     * @return the center point of the ellipse
     */
    public static Point ellipseCenter(int x, int y, int width, int height) {
        return new Point(width / 2 + x, height / 2 + y);
    }

    /**
     * Returns true if a given point is in the boundaries of a given ellipse
     * circumference. Returns false otherwise.
     *
     * @param x      the x coordinate of the upper left point of the rectangle
     *               blocking the given ellipse
     * @param y      the y coordinate of the upper left point of the rectangle
     *               blocking the given ellipse
     * @param width  the width of the rectangle blocking the given ellipse
     * @param height the height of the rectangle blocking the given ellipse
     * @param p      the point to determine if its in the ellipse circumference
     *               boundaries
     * @return true if a given point is in the boundaries of a given ellipse
     * circumference. Returns false otherwise
     */
    public static boolean isOnEllipse(int x, int y, int width, int height, Point p) {
        Point center = ellipseCenter(x, y, width, height);
        double result = Math.pow(p.getX() - center.getX(), 2) / Math.pow(width / 2.0, 2)
                + Math.pow(p.getY() - center.getY(), 2) / Math.pow(height / 2.0, 2);

        return result >= INSIDE_ELLIPSE_OFFSET && result <= OUTSIDE_ELLIPSE_OFFSET;
    }

    public static boolean isOnPolygon(Point[] points, Point p) {
        for(int i = 0; i < points.length - 1; i++) {
            if(isBetween(points[i], points[i + 1], p))
                return true;
        }

        return false;
    }
}
