package org.isma.pacman.entity;

import org.isma.pacman.context.PacmanGameContext;
import org.isma.pacman.resources.PacmanConfiguration;
import org.isma.pacman.resources.PacmanImageResources;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import static java.lang.String.format;
import static org.isma.slick2d.Direction.*;
import static org.isma.slick2d.RenderableFactory.buildAnimation;

public class Ghost extends Character<Ghost> {
    private static final String FRIGHTENED_BLINK_KEY = "frightened.blink.%s";
    private static final String NAKED_KEY_SUFFIX = "_naked";

    private final SpriteSheet frightenedSprite;
    private final SpriteSheet nakedSprite;

    private final int energizerDuration;
    private final int maxBlinkFrequency;
    private final int minBlinkFrequency;

    //State
    private int frightenedDuration;
    private int blinkFrequency;
    private int lastBlink;
    private int blinkKey;

    private boolean naked;

    Ghost(PacmanGameContext context, float x, float y, String name, float speed) throws SlickException {
        super(context, name, x, y);
        PacmanConfiguration configuration = context.getResourcesManager().getConfiguration();
        PacmanImageResources imageResources = context.getResourcesManager().getImageResources();

        setSpeed(speed);
        this.naked = false;
        this.energizerDuration = configuration.getEnergizerDuration();
        this.maxBlinkFrequency = configuration.getMaxBlinkFrequency();
        this.minBlinkFrequency = configuration.getMinBlinkFrequency();
        this.frightenedSprite = imageResources.getGhostFrightenedSprites();
        this.nakedSprite = imageResources.getGhostNakedSprites();
    }

    @Override
    protected SpriteSheet getSpriteSheet() {
        return getContext().getResourcesManager().getImageResources().getGhostSprites(getName());
    }

    @Override
    protected void buildRenderables() {
        renderables.put(EAST, buildAnimation(sprite, 2, ANIMATION_RATE, 0, 0, 0, false));
        renderables.put(WEST, buildAnimation(sprite, 2, ANIMATION_RATE, 0, 1, 0, false));
        renderables.put(NORTH, buildAnimation(sprite, 2, ANIMATION_RATE, 0, 2, 0, false));
        renderables.put(SOUTH, buildAnimation(sprite, 2, ANIMATION_RATE, 0, 3, 0, false));

        renderables.put(EAST + NAKED_KEY_SUFFIX, buildAnimation(nakedSprite, 1, ANIMATION_RATE, 0, 0, 0, true));
        renderables.put(WEST + NAKED_KEY_SUFFIX, buildAnimation(nakedSprite, 1, ANIMATION_RATE, 0, 1, 0, true));
        renderables.put(NORTH + NAKED_KEY_SUFFIX, buildAnimation(nakedSprite, 1, ANIMATION_RATE, 0, 2, 0, true));
        renderables.put(SOUTH + NAKED_KEY_SUFFIX, buildAnimation(nakedSprite, 1, ANIMATION_RATE, 0, 3, 0, true));

        renderables.put(format(FRIGHTENED_BLINK_KEY, 1), buildAnimation(frightenedSprite, 2, ANIMATION_RATE, 0, 0, 0, false));
        renderables.put(format(FRIGHTENED_BLINK_KEY, 2), buildAnimation(frightenedSprite, 2, ANIMATION_RATE, 0, 1, 0, false));
    }


    public void updateBlinkStatus() {
        if (isFrightened()) {
            if (lastBlink == blinkFrequency) {
                updateBlinkFrequency();
                lastBlink = 0;
                if (blinkKey == 1) {
                    blinkKey = 2;
                } else {
                    blinkKey = 1;
                }
            }
            lastBlink++;
        }
    }


    @Override
    protected Object getRenderableKey() {
        if (currentDirection == null) {
            return null;
        }
        if (isFrightened()) {
            return format(FRIGHTENED_BLINK_KEY, blinkKey);
        } else if (naked) {
            return super.getRenderableKey() + NAKED_KEY_SUFFIX;
        }
        return super.getRenderableKey();
    }

    @Override
    protected Object getDefaultRenderableKey() {
        if (isFrightened()) {
            return format(FRIGHTENED_BLINK_KEY, blinkKey);
        } else if (naked) {
            return super.getDefaultRenderableKey() + NAKED_KEY_SUFFIX;
        }
        return super.getDefaultRenderableKey();
    }

    @Override
    public void resetOriginalState() {
        super.resetOriginalState();
        naked = false;
        frightenedDuration = 0;
        lastBlink = 0;
    }


    @Override
    public int getMoveLevel() {
        return 2;
    }

    public boolean isFrightened() {
        return frightenedDuration != 0;
    }

    public void frightened() {
        frightenedDuration = energizerDuration;
        updateBlinkFrequency();
        blinkKey = 1;
    }

    public int getFrightenedDuration() {
        return frightenedDuration;
    }

    private void updateBlinkFrequency() {
        float rate = (float) frightenedDuration / (float) energizerDuration;
        blinkFrequency = (int) (maxBlinkFrequency * rate);
        if (blinkFrequency < minBlinkFrequency) {
            blinkFrequency = minBlinkFrequency;
        }
    }

    public void lowerFear() {
        frightenedDuration--;
        updateFrightenedStatus();
    }

    private void updateFrightenedStatus() {
        if (frightenedDuration == 0) {
            lastBlink = 0;
        }
    }

    @Override
    public void lose() {
        naked = true;
        frightenedDuration = 0;
        updateFrightenedStatus();
    }

    @Override
    public float getSpeed() {
        if (isFrightened()) {
            return super.getSpeed() / 2;
        }
        return super.getSpeed();
    }

    public boolean isNaked() {
        return naked;
    }

    public void resurrect() {
        naked = false;
    }

}
