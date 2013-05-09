package org.isma.pacman.entity;

import org.isma.pacman.context.PacmanGameContext;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Pacgum extends Food {

    Pacgum(PacmanGameContext context, float x, float y) throws SlickException {
        super(context, x, y);
    }

    @Override
    protected SpriteSheet getSpriteSheet() {
        return getContext().getResourcesManager().getImageResources().getPacgumSprites();
    }

    @Override
    public int getValue() {
        return 10;
    }
}
