package org.isma.pacman.entity;

import org.isma.pacman.context.PacmanGameContext;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Energizer extends Food {

    Energizer(PacmanGameContext context, float x, float y) throws SlickException {
        super(context, x, y);
    }


    @Override
    protected SpriteSheet getSpriteSheet() {
        return getContext().getResourcesManager().getImageResources().getEnergizerSprites();
    }

    @Override
    public int getValue() {
        return 50;
    }

    @Override
    public int getAnimationRate() {
        return 200;
    }

    @Override
    public int getAnimationCount() {
        return 2;
    }
}
