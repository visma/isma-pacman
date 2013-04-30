package org.isma.pacman.drawer;

import org.isma.pacman.entity.Ghost;
import org.isma.slick2d.drawer.ParticleDrawer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

class GhostParticleDrawer extends ParticleDrawer<Ghost> {

    GhostParticleDrawer() throws SlickException {
        super("pacman/particles/test_particle.png", "pacman/particles/ghost_emitter.xml");
    }

    @Override
    protected boolean render(Ghost obj) {
        return !obj.isFrightened() && !obj.isNaked();
    }

    @Override
    protected Vector2f getCenterOffSet(Ghost obj) {
        return new Vector2f(obj.getWidth() / 2, obj.getHeight() / 2);
    }
}
