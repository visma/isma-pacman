package org.isma.pacman.drawer;

import org.isma.pacman.entity.Ghost;
import org.isma.pacman.entity.Pacman;
import org.isma.slick2d.drawer.ParticleDrawer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

import java.util.List;

class PacmanParticleDrawer extends ParticleDrawer<Pacman> {
    private final List<Ghost> ghosts;

    PacmanParticleDrawer(List<Ghost> ghosts) throws SlickException {
        super("pacman/particles/test_particle.png", "pacman/particles/pacman_emitter.xml");
        this.ghosts = ghosts;
    }

    @Override
    protected boolean render(Pacman obj) {
        for (Ghost ghost : ghosts) {
            if (ghost.isFrightened()) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected Vector2f getCenterOffSet(Pacman obj) {
        return new Vector2f(obj.getWidth() / 2, obj.getHeight() / 2);
    }
}
