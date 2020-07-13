package com.idan.constants;

/**
 * This enum class represents the state of the selected line thickness
 * from the thickness selection window.
 *
 * @author Idan Perry
 * @version 13.07.2020
 */

public enum SelectedThickness {
    ONE("one", 1),
    TWO("two", 2),
    THREE("three", 3),
    FOUR("four", 4),
    FIVE("five", 5),
    SIX("six", 6),
    SEVEN("seven", 7),
    EIGHT("eight", 8),
    NINE("nine", 9);

    private final String name;
    private final int index;

    /**
     * Constructs an enum selected thickness object with the
     * associated thickness url name (for use with icon attachment).
     *
     * @param name the thickness url name
     * @param index the index of the selected thickness as a relative position
     */
    SelectedThickness(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public String toString() {
        return name;
    }
}
