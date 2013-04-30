package org.isma.pacman.entity;

import org.newdawn.slick.geom.Rectangle;

public class GhostCorner implements Target {
    private final Rectangle area;

    public GhostCorner(Rectangle area) {
        this.area = area;
    }

    public Rectangle getTarget() {
        return area;
    }
}
