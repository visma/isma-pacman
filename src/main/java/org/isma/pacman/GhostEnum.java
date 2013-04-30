package org.isma.pacman;

public enum GhostEnum {
    BLINKY("blinky"),
    PINKY("pinky"),
    INKY("inky"),
    CLYDE("clyde");
    private final String name;

    GhostEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
