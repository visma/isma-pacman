package org.isma.pacman.tileset;

import org.isma.slick2d.tileset.TiledLayer;
import org.isma.slick2d.tileset.TiledLayerProperties;

import java.awt.*;

import static java.lang.Integer.parseInt;

public class PathTiledLayerProperties extends TiledLayerProperties {
    private static final String PATH_PROPERTY_NAME = "path";
    private static final String GHOST_HOUSE_PROPERTY_NAME = "house";

    public PathTiledLayerProperties(final TiledLayer owner) {
        super(owner);
    }

    public boolean isPath(Point point) {
        return isPath(point.x, point.y);
    }

    public boolean isPath(int x, int y) {
        int tileId = owner.getTiledMap().getTileId(x, y, owner.getIndex());
        String tileProperty = owner.getTiledMap().getTileProperty(tileId, PATH_PROPERTY_NAME, "0");
        return parseInt(tileProperty) > 0;
    }

    public Integer getPath(int x, int y) {
        int tileId = owner.getTiledMap().getTileId(x, y, owner.getIndex());
        return parseInt(owner.getTiledMap().getTileProperty(tileId, PATH_PROPERTY_NAME, null));
    }

    public boolean isGhostHouse(int x, int y) {
        int tileId = owner.getTiledMap().getTileId(x, y, owner.getIndex());
        return parseInt(owner.getTiledMap().getTileProperty(tileId, GHOST_HOUSE_PROPERTY_NAME, "0")) == 1;
    }

    public boolean isGhostCorner(int x, int y, String ghostName, int cornerIndex) {
        int tileId = owner.getTiledMap().getTileId(x, y, owner.getIndex());
        return parseInt(owner.getTiledMap().getTileProperty(tileId, ghostName, "0")) == cornerIndex;
    }
}
