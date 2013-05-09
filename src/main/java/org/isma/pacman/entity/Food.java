package org.isma.pacman.entity;

import org.isma.pacman.context.PacmanGameContext;
import org.isma.slick2d.BitmapObject;
import org.newdawn.slick.Animation;

import static org.isma.slick2d.RenderableFactory.buildAnimation;


public abstract class Food extends BitmapObject<Animation, PacmanGameContext> {
    private static final Integer KEY_IMAGE = 1;
    private static final int FOOD_DIMENSION = 8;

    protected Food(PacmanGameContext context, float x, float y) {
        super(context, x, y);
    }

    @Override
    protected void buildRenderables() {
        renderables.put(getRenderableKey(), buildAnimation(sprite, getAnimationCount(), getAnimationRate(), 0));
    }

    @Override
    protected int getWidth() {
        return FOOD_DIMENSION;
    }

    @Override
    protected int getHeight() {
        return FOOD_DIMENSION;
    }


    protected Object getRenderableKey() {
        return KEY_IMAGE;
    }

    public abstract int getValue();

    public int getAnimationRate() {
        return 1000;
    }

    public int getAnimationCount() {
        return 1;
    }


}
