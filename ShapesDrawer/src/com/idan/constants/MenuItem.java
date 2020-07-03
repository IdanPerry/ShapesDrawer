package com.idan.constants;

public enum MenuItem {
    ADJUST("Adjust", 0),
    CLONE("Clone", 1),
    REMOVE("Remove", 2);

    private final String name;
    private final int index;

    MenuItem(String name, int index) {
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
