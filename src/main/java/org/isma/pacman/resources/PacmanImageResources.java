package org.isma.pacman.resources;

import org.isma.slick2d.GameResourcesLoader;
import org.isma.slick2d.resources.ImageResources;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import java.awt.*;

import static java.lang.String.format;

public class PacmanImageResources extends ImageResources {
    private static final Dimension CHARACTER_SIZE = new Dimension(16, 16);
    private static final Dimension DOT_SIZE = new Dimension(8, 8);
    private static final Dimension FRUIT_SIZE = new Dimension(16, 16);
    private static final Dimension FRUIT_POINTS_SIZE = new Dimension(20, 16);


    private static final String SPRITE_PACMAN = "sprite.pacman";
    private static final String SPRITE_PACMAN_DEATH = "sprite.pacman.death";

    private static final String SPRITE_GHOST = "sprite.ghost.%s";
    private static final String SPRITE_GHOST_FRIGHTENED = "sprite.ghost.frightened";
    private static final String SPRITE_GHOST_NAKED = "sprite.ghost.naked";
    private static final String SPRITE_GHOST_POINTS = "sprite.ghost.points";

    private static final String SPRITE_ENERGIZER = "sprite.energizer";
    private static final String SPRITE_PACGUM = "sprite.pacgum";
    private static final String SPRITE_FRUITS = "sprite.fruits";

    private static final String BACKGROUND_MENU = "image.background.menu";
    private static final String SPRITE_FRUITS_POINTS = "sprite.fruits.points";


    public PacmanImageResources(GameResourcesLoader resourcesManager) {
        super(resourcesManager);
    }

    public void loadImages() throws SlickException {
        loadSprite(SPRITE_PACMAN, CHARACTER_SIZE);
        loadSprite(SPRITE_PACMAN_DEATH, CHARACTER_SIZE);

        for (GhostEnum ghost : GhostEnum.values()) {
            loadSprite(format(SPRITE_GHOST, ghost.getName()), CHARACTER_SIZE);
        }
        loadSprite(SPRITE_GHOST_FRIGHTENED, CHARACTER_SIZE);
        loadSprite(SPRITE_GHOST_NAKED, CHARACTER_SIZE);
        loadSprite(SPRITE_GHOST_POINTS, CHARACTER_SIZE);
        loadSprite(SPRITE_ENERGIZER, DOT_SIZE);
        loadSprite(SPRITE_PACGUM, DOT_SIZE);
        loadSprite(SPRITE_FRUITS, FRUIT_SIZE);
        loadSprite(SPRITE_FRUITS_POINTS, FRUIT_POINTS_SIZE);

        loadImage(BACKGROUND_MENU);
    }

    public SpriteSheet getPacmanSprites() {
        return sprites.get(SPRITE_PACMAN);
    }

    public SpriteSheet getPacmanDeathSprites() {
        return sprites.get(SPRITE_PACMAN_DEATH);
    }

    public Image getFruitPointsImage(int index) {
        return sprites.get(SPRITE_FRUITS_POINTS).getSprite(0, index);
    }


    public Image getGhostPointsImage(int index) {
        return sprites.get(SPRITE_GHOST_POINTS).getSprite(index, 0);
    }

    public SpriteSheet getGhostSprites(String name) {
        return sprites.get(String.format(SPRITE_GHOST, name));
    }

    public SpriteSheet getGhostFrightenedSprites() {
        return sprites.get(SPRITE_GHOST_FRIGHTENED);
    }

    public SpriteSheet getGhostNakedSprites() {
        return sprites.get(SPRITE_GHOST_NAKED);
    }

    public SpriteSheet getEnergizerSprites() {
        return sprites.get(SPRITE_ENERGIZER);
    }

    public SpriteSheet getPacgumSprites() {
        return sprites.get(SPRITE_PACGUM);
    }

    public SpriteSheet getFruitsSprites() {
        return sprites.get(SPRITE_FRUITS);
    }

    public Image getMenuBackgrond() {
        return images.get(BACKGROUND_MENU);
    }


}
