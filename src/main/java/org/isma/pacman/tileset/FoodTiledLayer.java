package org.isma.pacman.tileset;

import org.isma.slick2d.tileset.TiledLayer;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.tiled.TiledMap;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FoodTiledLayer extends TiledLayer<FoodTiledLayerProperties> {

    public FoodTiledLayer(TiledMap tiledMap) {
        super(1, tiledMap);
    }

    public List<Point> getFoodPositionList() {
        List<Point> foodPositionList = new ArrayList<Point>();

        for (int i = 0; i < getTiledMap().getWidth(); i++) {
            for (int j = 0; j < getTiledMap().getHeight(); j++) {
                if (properties().isFood(i, j)) {
                    foodPositionList.add(new Point(i, j));
                }
            }
        }
        return foodPositionList;
    }


    @Override
    protected FoodTiledLayerProperties buildProperties() {
        return new FoodTiledLayerProperties(this);
    }


    public Rectangle getFruitSpawnPoint() {
        for (int i = 0; i < getTiledMap().getWidth(); i++) {
            for (int j = 0; j < getTiledMap().getHeight(); j++) {
                if (properties().isFruitSpawnPoint(i, j)) {
                    return getRectangle(i, j);
                }
            }
        }
        throw new RuntimeException("no ghost house defined");
    }


}
