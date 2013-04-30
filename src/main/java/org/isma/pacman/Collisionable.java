package org.isma.pacman;

import org.newdawn.slick.geom.Rectangle;

public interface Collisionable {
    boolean hit(Collisionable other);

    Rectangle getHitBox();
}
