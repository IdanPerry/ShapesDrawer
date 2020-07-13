package com.idan.constants;

/**
 * This enum class represents the state of the selected shape
 * from the user interface.
 * 
 * @author Idan Perry
 * @version 03.05.2020
 */

public enum SelectedShape {
	RECTANGLE("rect", 0),
	ROUND_RECTANGLE("square", 1),
	OVAL("oval", 2),
	ISO_TRIANGLE("iso_triangle", 3),
	RIGHT_TRIANGLE("triangle", 4),
	PENTAGON("pentagon", 5),
	HEXAGON("hexagon", 6),
	LINE("line", 7),
	FREE_DRAW("draw", 8);
	
	private final String name;
	private final int index;
	
	/**
	 * Constructs an enum selected shape object with the
	 * associated shape url name (for use with icon attachment).
	 * 
	 * @param name the shape url name
	 * @param index the index of the selected shape as a relative position
	 */
	SelectedShape(String name, int index) {
		this.name = name;
		this.index = index;
	}
	
	/**
	 * Returns the index of the enum field as a relative position.
	 * 
	 * @return the index of the enum field as a relative position
	 */
	public int getIndex() {
		return index;
	}

	@Override
	public String toString() {
		return name;
	}
}
