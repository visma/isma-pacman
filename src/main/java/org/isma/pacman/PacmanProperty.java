package org.isma.pacman;

//TODO a degager a terme...
public enum PacmanProperty {
    LEVEL_TILES("level.tiles");

    private String propertyName;

    PacmanProperty(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyName() {
        return propertyName;
    }
}
