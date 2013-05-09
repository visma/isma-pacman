package org.isma.pacman.entity;

import org.isma.pacman.context.PacmanGameContext;
import org.isma.pacman.resources.GhostEnum;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import static org.isma.pacman.entity.Fruit.FruitEnum;
import static org.isma.pacman.resources.GhostEnum.*;

public class PacmanEntitiesFactory {
    private static final int PACMAN_X_ORIGIN = 4 + (4 * 25);
    private static final int PACMAN_Y_ORIGIN = 28 + (8 * 22);

    private static final float BLINKY_X_ORIGIN = 4 + (4 * 25);
    private static final float BLINKY_Y_ORIGIN = 28 + (8 * 10);

    private static final float INKY_X_ORIGIN = BLINKY_X_ORIGIN - 16f;
    private static final float INKY_Y_ORIGIN = BLINKY_Y_ORIGIN + 24f;

    private static final float PINKY_X_ORIGIN = BLINKY_X_ORIGIN + 0f;
    private static final float PINKY_Y_ORIGIN = BLINKY_Y_ORIGIN + 24f;

    private static final float CLYDE_X_ORIGIN = BLINKY_X_ORIGIN + 16f;
    private static final float CLYDE_Y_ORIGIN = BLINKY_Y_ORIGIN + 24f;


    public static Pacman buildPacman(PacmanGameContext gameContext) throws SlickException {
        int lives = gameContext.getResourcesManager().getConfiguration().getLives();

        Pacman pacman = new Pacman(gameContext, PACMAN_X_ORIGIN, PACMAN_Y_ORIGIN, lives);
        pacman.setSpeed(gameContext.getResourcesManager().getConfiguration().getSpeed(pacman.getName()));
        pacman.init();
        return pacman;
    }

    public static Ghost buildBlinky(PacmanGameContext gameContext) throws SlickException {
        return buildGhost(gameContext, BLINKY_X_ORIGIN, BLINKY_Y_ORIGIN, BLINKY);
    }

    public static Ghost buildPinky(PacmanGameContext gameContext) throws SlickException {
        return buildGhost(gameContext, PINKY_X_ORIGIN, PINKY_Y_ORIGIN, PINKY);
    }

    public static Ghost buildInky(PacmanGameContext gameContext) throws SlickException {
        return buildGhost(gameContext, INKY_X_ORIGIN, INKY_Y_ORIGIN, INKY);
    }

    public static Ghost buildClyde(PacmanGameContext gameContext) throws SlickException {
        return buildGhost(gameContext, CLYDE_X_ORIGIN, CLYDE_Y_ORIGIN, CLYDE);
    }

    private static Ghost buildGhost(PacmanGameContext gameContext, float x, float y, GhostEnum ghostEnum) throws SlickException {
        float speed = gameContext.getResourcesManager().getConfiguration().getSpeed(ghostEnum.getName());
        Ghost ghost = new Ghost(gameContext, x, y, ghostEnum.getName(), speed);
        ghost.init();
        return ghost;
    }

    public static Pacgum buildPacgum(PacmanGameContext gameContext, float x, float y) throws SlickException {
        Pacgum obj = new Pacgum(gameContext, x, y);
        obj.init();
        return obj;
    }

    public static Energizer buildEnergizer(PacmanGameContext gameContext, float x, float y) throws SlickException {
        Energizer obj = new Energizer(gameContext, x, y);
        obj.init();
        return obj;
    }

    public static Fruit buildFruit(PacmanGameContext context, Rectangle center, FruitEnum fruitEnum) throws SlickException {
        int centerFruitOffset = 4;
        float x = center.getX() - centerFruitOffset;
        float y = center.getY() - centerFruitOffset;
        Fruit fruit = new Fruit(context, x, y, fruitEnum);
        fruit.init();
        return fruit;
    }
}
