package org.isma.pacman.tileset;

import org.isma.slick2d.tileset.TiledLayer;
import org.isma.slick2d.tileset.TiledLayerProperties;

public class FoodTiledLayerProperties extends TiledLayerProperties {

    private static final String PACGUM_PROPERTY_NAME = "pacgum";
    private static final String ENERGIZER_PROPERTY_NAME = "energizer";
    private static final String FRUIT_SPAWN_POINT_PROPERTY_NAME = "fruitSpawnPoint";

    public FoodTiledLayerProperties(final TiledLayer owner) {
        super(owner);
    }

    public boolean isFood(int x, int y) {
        return isPacgum(x, y) || isEnergizer(x, y);
    }

    public boolean isPacgum(int x, int y) {
        int tileId = owner.getTiledMap().getTileId(x, y, owner.getIndex());
        return owner.getTiledMap().getTileProperty(tileId, PACGUM_PROPERTY_NAME, "0").equals("1");
    }

    public boolean isEnergizer(int x, int y) {
        int tileId = owner.getTiledMap().getTileId(x, y, owner.getIndex());
        return owner.getTiledMap().getTileProperty(tileId, ENERGIZER_PROPERTY_NAME, "0").equals("1");
    }

    public boolean isFruitSpawnPoint(int x, int y) {
        int tileId = owner.getTiledMap().getTileId(x, y, owner.getIndex());
        return owner.getTiledMap().getTileProperty(tileId, FRUIT_SPAWN_POINT_PROPERTY_NAME, "0").equals("1");
    }
}
