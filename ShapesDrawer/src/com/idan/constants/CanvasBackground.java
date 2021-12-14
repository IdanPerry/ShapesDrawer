package com.idan.constants;

public enum CanvasBackground {
    CHALK("chalkboard2", 0),
    BLACK_GRID("blackgrid", 1),
    BLUE_GRID("bluegrid", 2);

    private final String name;
    private final int index;

    CanvasBackground(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public String toString() {
        return name;
    }
}
