package com.idan.drawables;

import com.idan.interfaces.Adjustable;
import java.awt.Color;

import com.idan.interfaces.Drawable;
import java.awt.Graphics;

/**
 * This abstract class represents a geometric shape.
 *
 * @author Idan Perry
 * @version 03.05.2020
 */

public abstract class Shape implements Drawable, Adjustable, Cloneable {
    private int originX;
    private int destX;
    private int originY;
    private int destY;
    private int thickness;
    private Color color;

    /**
     * Constructs a geometric shape using four integers and a color attribute. It's
     * up to the inheriting class to decide how to use the four integers to
     * construct the wanted shape.
     *
     * @param originX   first integer - usually representing the first x coordinate
     * @param originY   second integer - usually representing the first y coordinate
     * @param destX     third integer - usually representing the second x coordinate
     *                  or the width attribute of a two dimensional shape
     * @param destY     fourth integer - usually representing the second y coordinate
     *                  or the height attribute of a two dimensional shape
     * @param thickness the thickness of the outline of the shape
     * @param color     the color attribute of the shape
     */
    public Shape(int originX, int originY, int destX, int destY, int thickness, Color color) {
        this.originX = originX;
        this.originY = originY;
        this.destX = destX;
        this.destY = destY;
        this.thickness = thickness;
        this.color = color;
    }

    /**
     * Constructs a copy of the specified shape object.
     *
     * @param shape the shape object to copy
     */
    public Shape(Shape shape) {
        this.originX = shape.originX;
        this.originY = shape.originY;
        this.destX = shape.destX;
        this.destY = shape.destY;
        this.thickness = shape.thickness;
        this.color = shape.color;
    }

    /**
     * Constructs a geometric shape with a default size,
     * color and thickness properties.
     */
    public Shape() {
        this(20, 20, 120, 120, 1, Color.BLACK);
    }

    /**
     * Returns the first x coordinate
     *
     * @return the first x coordinate
     */
    public int getOriginX() {
        return originX;
    }

    /**
     * Sets the first x coordinate
     *
     * @param originX the first x coordinate
     */
    public void setOriginX(int originX) {
        this.originX = originX;
    }

    /**
     * Returns the second x coordinate
     *
     * @return the second x coordinate
     */
    public int getDestX() {
        return destX;
    }

    /**
     * Sets the second x coordinate
     *
     * @param destX the second x coordinate
     */
    public void setDestX(int destX) {
        this.destX = destX;
    }

    /**
     * Returns the first y coordinate
     *
     * @return the first y coordinate
     */
    public int getOriginY() {
        return originY;
    }

    /**
     * Sets the first y coordinate
     *
     * @param originY the first y coordinate
     */
    public void setOriginY(int originY) {
        this.originY = originY;
    }

    /**
     * Returns the second y coordinate
     *
     * @return the second y coordinate
     */
    public int getDestY() {
        return destY;
    }

    /**
     * Sets the second y coordinate
     *
     * @param destY the second y coordinate
     */
    public void setDestY(int destY) {
        this.destY = destY;
    }

    /**
     * Returns the color attribute
     *
     * @return the color attribute
     */
    public Color getColor() {
        return color;
    }

    /**
     * Returns the thickness of the outline of the shape.
     *
     * @return the thickness of the outline of the shape
     */
    public int getThickness() {
        return thickness;
    }

    /**
     * Changes the thickness of the outline of the shape.
     *
     * @param thickness the thickness of the outline of the shape
     */
    public void setThickness(int thickness) {
        this.thickness = thickness;
    }

    /**
     * Changes the color attribute of the shape
     *
     * @param color the color attribute of the shape
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Returns true if this shape contains the specified x and y coordinates
     * on one of its sides. otherwise returns false.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @return true if this shape contains the specified x and y coordinates
     * on one of its sides. otherwise returns false
     */
    public boolean hasPoint(int x, int y) {
        return false;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
