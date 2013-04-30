package org.isma.pacman.tileset;

import org.isma.slick2d.tileset.NoneTiledLayerProperties;
import org.isma.slick2d.tileset.TiledLayer;
import org.newdawn.slick.tiled.TiledMap;

public class DisplayTiledLayer extends TiledLayer<NoneTiledLayerProperties> {

    public DisplayTiledLayer(TiledMap map) {
        super(0, map);
    }

    @Override
    protected NoneTiledLayerProperties buildProperties() {
        return new NoneTiledLayerProperties(this);
    }
}
