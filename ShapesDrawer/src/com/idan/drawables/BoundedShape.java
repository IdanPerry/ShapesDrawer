package com.idan.drawables;

import com.idan.utils.Geometry;
import java.awt.Color;
import java.awt.Point;

/**
 * This abstract class represents a geometric bounded shape.
 *
 * @author Idan Perry
 * @version 03.05.2020
 */

public abstract class BoundedShape extends Shape {
    private int width;
    private int height;
    private boolean filled;

    /**
     * Constructs a geometric bounded shape with the specified boundaries,
     * color, thickness and a boolean filled color attribute.
     *
     * @param upperLeftX the upper left x coordinate of the bounded shape
     * @param upperLeftY the upper left y coordinate of the bounded shape
     * @param width      the width of the bounded shape
     * @param height     the height of the bounded shape
     * @param thickness  the thickness of the outline of this bounded shape
     * @param color      the color of the bounded shape
     * @param filled     whether the bounded shape is filled with color
     */
    public BoundedShape(int upperLeftX, int upperLeftY, int width, int height, int thickness, Color color, boolean filled) {
        super(upperLeftX, upperLeftY, width, height, thickness, color);
        this.filled = filled;
        this.width = width;
        this.height = height;

    }

    /**
     * Constructs a geometric bounded shape with a default size,
     * color and thickness properties.
     */
    public BoundedShape() {
        super();
        width = 100;
        height = 100;
        filled = false;
    }

    /**
     * Returns the upper left x coordinate
     *
     * @return the upper left x coordinate
     */
    public int getUpperLeftX() {
        return super.getOriginX();
    }

    /**
     * Changes the upper left x coordinate
     *
     * @param x the upper left x coordinate
     */
    public void setUpperLeftX(int x) {
        super.setOriginX(x);
    }

    /**
     * Returns the upper left y coordinate
     *
     * @return the upper left y coordinate
     */
    public int getUpperLeftY() {
        return super.getOriginY();
    }

    /**
     * Changes the upper left y coordinate
     *
     * @param y the upper left y coordinate
     */
    public void setUpperLeftY(int y) {
        super.setOriginY(y);
    }

    /**
     * Returns the width of the bounded shape
     *
     * @return the width of the bounded shape
     */
    public int getWidth() {
        return width;
    }

    /**
     * Changes the width of the bounded shape
     *
     * @param width the width of the bounded shape
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Returns the height of the bounded shape
     *
     * @return the height of the bounded shape
     */
    public int getHeight() {
        return height;
    }

    /**
     * Changes the height of the bounded shape
     *
     * @param height the height of the bounded shape
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Returns true if the bounded shape is filled with color
     *
     * @return true if the bounded shape is filled with color
     */
    public boolean isFilled() {
        return filled;
    }

    /**
     * Changes the fill color attribute
     *
     * @param filled the fill color attribute
     */
    public void setFilled(boolean filled) {
        this.filled = filled;
    }

    @Override
    public boolean hasPoint(int x, int y) {
        Point leftUpper = new Point(getOriginX(), getOriginY());
        Point rightUpper = new Point(getOriginX() + getWidth(), getOriginY());
        Point leftLower = new Point(getOriginX(), getOriginY() + getHeight());
        Point rightLower = new Point(getOriginX() + getWidth(), getOriginY() + getHeight());
        return Geometry.isOnRect(leftUpper, rightUpper, leftLower, rightLower, new Point(x, y));
    }
}
