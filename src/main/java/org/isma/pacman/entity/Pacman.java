package org.isma.pacman.entity;

import org.isma.pacman.context.PacmanGameContext;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Rectangle;

import static org.isma.slick2d.Direction.*;
import static org.isma.slick2d.RenderableFactory.buildAnimation;

public class Pacman extends Character<Pacman> implements Target {
    private static final String STOPPED_KEY_SUFFIX = "_stopped";
    private static final String DEATH_KEY = "death";

    private int lives;
    private boolean dead;


    Pacman(PacmanGameContext context, float x, float y, int lives) throws SlickException {
        super(context, "pacman", x, y);
        this.lives = lives;
    }

    @Override
    protected SpriteSheet getSpriteSheet() {
        return getContext().getResourcesManager().getImageResources().getPacmanSprites();
    }

    protected void buildRenderables() {
        renderables.put(EAST, buildAnimation(sprite, 2, ANIMATION_RATE, 0));
        renderables.put(SOUTH, buildAnimation(sprite, 2, ANIMATION_RATE, 90));
        renderables.put(WEST, buildAnimation(sprite, 2, ANIMATION_RATE, 180));
        renderables.put(NORTH, buildAnimation(sprite, 2, ANIMATION_RATE, 270));

        renderables.put(EAST + STOPPED_KEY_SUFFIX, buildAnimation(sprite, 1, ANIMATION_RATE, 0));
        renderables.put(SOUTH + STOPPED_KEY_SUFFIX, buildAnimation(sprite, 1, ANIMATION_RATE, 90));
        renderables.put(WEST + STOPPED_KEY_SUFFIX, buildAnimation(sprite, 1, ANIMATION_RATE, 180));
        renderables.put(NORTH + STOPPED_KEY_SUFFIX, buildAnimation(sprite, 1, ANIMATION_RATE, 270));

        SpriteSheet deathSprites = getContext().getResourcesManager().getImageResources().getPacmanDeathSprites();
        Animation deathAnim = buildAnimation(deathSprites, 12, ANIMATION_RATE, 0);
        deathAnim.setLooping(false);
        renderables.put(DEATH_KEY, deathAnim);
    }


    @Override
    protected Object getRenderableKey() {
        if (currentDirection == null) {
            return null;
        }
        if (dead) {
            return DEATH_KEY;
        }
        if (stopped) {
            return currentDirection + STOPPED_KEY_SUFFIX;
        }
        return currentDirection;
    }

    @Override
    protected Object getDefaultRenderableKey() {
        if (dead) {
            return DEATH_KEY;
        }
        if (stopped) {
            return super.getDefaultRenderableKey() + STOPPED_KEY_SUFFIX;
        }
        return super.getDefaultRenderableKey();
    }


    public Rectangle getTarget() {
        return getCenter();
    }

    @Override
    public int getMoveLevel() {
        return 1;
    }

    @Override
    public void lose() {
        lives--;
        dead = true;
    }

    public int getLives() {
        return lives;
    }


    @Override
    public void resetOriginalState() {
        super.resetOriginalState();
        stopped = true;
    }

    public boolean isDead() {
        return dead;
    }

    public void revive() {
        dead = false;
        renderables.get(DEATH_KEY).restart();
    }

    public void addExtraLife() {
        lives++;
    }
}
