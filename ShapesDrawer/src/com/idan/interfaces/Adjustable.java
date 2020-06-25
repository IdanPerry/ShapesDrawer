package com.idan.interfaces;

import java.awt.Point;

/**
 * This interface gives the object of the implementing class to be able to adjust itself
 * by moving, resizing or reshaping.
 *
 * @author Idan Perry
 * @version 22.06.2020
 */

public interface Adjustable {
    public abstract void move(Point origin, Point dest,  int currentX, int currentY);

//    public abstract void resize();

//    public abstract void reshape();
}
