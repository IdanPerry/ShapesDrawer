package com.idan.constants;

/**
 * This enum class represents the state of the selected drawing tool
 * from the user interface.
 * 
 * @author Idan Perry
 * @version 22.06.2020
 */

public enum DrawingTool {
	PENCIL("pencil", 0),
	BRUSH("brush", 1),
	AIRBRUSH("airbrush", 2),
	ARTISTIC("bigbrush", 3);
	
	private final String name;
	private final int index;
	
	/**
	 * Constructs an enum selected drawing tool object with the
	 * associated tool url name (for use with icon attachment).
	 * 
	 * @param symbol the shape url name
	 */
	DrawingTool(String name, int index) {
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
