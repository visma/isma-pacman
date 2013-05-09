package org.isma.pacman.entity;

import org.newdawn.slick.geom.Rectangle;

public interface Collisionable {
    boolean hit(Collisionable other);

    Rectangle getHitBox();
}
