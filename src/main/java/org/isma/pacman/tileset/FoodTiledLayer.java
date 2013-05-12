package org.isma.pacman.tileset;

import org.isma.slick2d.tileset.TiledLayer;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FoodTiledLayer extends TiledLayer {
    private static final String PACGUM_PROPERTY_NAME = "pacgum";
    private static final String ENERGIZER_PROPERTY_NAME = "energizer";
    private static final String FRUIT_SPAWN_POINT_PROPERTY_NAME = "fruitSpawnPoint";

    public FoodTiledLayer(TiledMap tiledMap) {
        super(1, tiledMap);
    }

    public List<Point> getFoodPositionList() {
        List<Point> foodPositionList = new ArrayList<Point>();

        for (int i = 0; i < getTiledMap().getWidth(); i++) {
            for (int j = 0; j < getTiledMap().getHeight(); j++) {
                if (isFood(i, j)) {
                    foodPositionList.add(new Point(i, j));
                }
            }
        }
        return foodPositionList;
    }


    public Rectangle getFruitSpawnPoint() {
        for (int i = 0; i < getTiledMap().getWidth(); i++) {
            for (int j = 0; j < getTiledMap().getHeight(); j++) {
                if (isFruitSpawnPoint(i, j)) {
                    return getRectangle(i, j);
                }
            }
        }
        throw new RuntimeException("no ghost house defined");
    }

    public boolean isFood(int x, int y) {
        return isPacgum(x, y) || isEnergizer(x, y);
    }

    public boolean isPacgum(int x, int y) {
        int tileId = getTiledMap().getTileId(x, y, getIndex());
        return getTiledMap().getTileProperty(tileId, PACGUM_PROPERTY_NAME, "0").equals("1");
    }

    public boolean isEnergizer(int x, int y) {
        int tileId = getTiledMap().getTileId(x, y, getIndex());
        return getTiledMap().getTileProperty(tileId, ENERGIZER_PROPERTY_NAME, "0").equals("1");
    }

    public boolean isFruitSpawnPoint(int x, int y) {
        int tileId = getTiledMap().getTileId(x, y, getIndex());
        return getTiledMap().getTileProperty(tileId, FRUIT_SPAWN_POINT_PROPERTY_NAME, "0").equals("1");
    }

}
