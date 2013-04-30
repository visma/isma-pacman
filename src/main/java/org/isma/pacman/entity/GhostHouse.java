package org.isma.pacman.entity;

import org.isma.pacman.Collisionable;
import org.newdawn.slick.geom.Rectangle;

public class GhostHouse implements Target, Collisionable {
    private final Rectangle target;

    public GhostHouse(Rectangle target) {
        this.target = target;
    }

    public Rectangle getTarget() {
        return target;
    }

    public boolean hit(Collisionable other) {
        return getHitBox().intersects(other.getHitBox());
    }

    public Rectangle getHitBox() {
        return getTarget();
    }
}
