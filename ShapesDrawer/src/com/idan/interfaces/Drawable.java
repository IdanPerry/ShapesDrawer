package com.idan.interfaces;

import java.awt.Graphics;

/**
 * This interface gives the object of the implementing class to be able to draw itself.
 * 
 * @author Idan Perry
 * @version 22.06.2020
 */

@FunctionalInterface
public interface Drawable {
	/**
	 * Drawing using Graphics object
	 * 
	 * @param g the Graphics object
	 */
	public void draw(Graphics g);
}
