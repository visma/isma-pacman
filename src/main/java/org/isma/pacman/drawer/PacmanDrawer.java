package org.isma.pacman.drawer;

import org.isma.pacman.PacmanGameContext;
import org.isma.pacman.entity.Ghost;
import org.isma.pacman.entity.Pacman;
import org.isma.slick2d.drawer.RenderableDrawer;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;

import java.util.List;

public class PacmanDrawer extends RenderableDrawer<Pacman, PacmanGameContext> {
    private PacmanParticleDrawer pacmanParticleDrawer;

    public PacmanDrawer(PacmanGameContext context, List<Ghost> ghosts) throws SlickException {
        super(context);
        if (context.enableParticles()) {
            pacmanParticleDrawer = new PacmanParticleDrawer(ghosts);
        }
    }

    @Override
    protected void drawAnimation(float xPos, float yPos, Color filter, Animation animation, Pacman pacman) {
        animation.draw(xPos, yPos, animation.getWidth(), animation.getHeight());
        if (context.enableParticles()) {
            pacmanParticleDrawer.renderParticles(pacman, xPos, yPos);
        }
    }


}
