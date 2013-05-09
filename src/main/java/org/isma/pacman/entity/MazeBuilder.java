package org.isma.pacman.entity;

import org.isma.pacman.context.PacmanGameContext;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

import java.awt.*;

import static org.isma.pacman.entity.PacmanEntitiesFactory.buildEnergizer;
import static org.isma.pacman.entity.PacmanEntitiesFactory.buildPacgum;

public class MazeBuilder {

    public static Pacgum addPacgum(Point foodPosition, TiledMap tiledMap, PacmanGameContext context) throws SlickException {
        int tileSize = tiledMap.getTileWidth();
        int x = foodPosition.x;
        int y = foodPosition.y;

        float xFood = (x * tileSize);
        float yFood = (y * tileSize);
        return buildPacgum(context, xFood, yFood);
    }

    //TODO copié collé a virer....
    public static Energizer addEnergizer(Point foodPosition, TiledMap tiledMap, PacmanGameContext context) throws SlickException {
        int tileSize = tiledMap.getTileWidth();
        int x = foodPosition.x;
        int y = foodPosition.y;

        float xFood = (x * tileSize);
        float yFood = (y * tileSize);
        return buildEnergizer(context, xFood, yFood);
    }

}
