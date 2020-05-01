package com.idan.painter;

/**
 * This enum class represents the state of the selected shape
 * from the user interface.
 * 
 * @author Idan Perry
 * @version 03.05.2020
 */

public enum SelectedShape {
	RECTENGLE("\u25A1", 0),
	ROUND_RECTANGLE("\u25A2", 1),
	OVAL("\u25CB", 2),
	ISO_TRIANGLE("\u25B3", 3),
	RIGHT_TRIANGLE("\u25FA", 4),
	HEXAGON("\u2B21", 5),
	LINE("\u29F8", 6),
	FREE_HAND("\u270D", 7);
	
	// free hand drawing constants
	public enum DrawingType {LINES, OVALS};
	
	private final String symbol;
	private final int index;
	
	/**
	 * Constructs an enum selected shape object with the
	 * associated shape drawing represented by a unicode string.
	 * 
	 * @param symbol the shape symbol in a unicode
	 */
	SelectedShape(String symbol, int index) {
		this.symbol = symbol;
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
		return symbol;
	}
}
