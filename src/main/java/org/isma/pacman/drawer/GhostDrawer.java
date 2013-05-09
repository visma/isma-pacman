package org.isma.pacman.drawer;

import org.isma.math.SinusVariation;
import org.isma.pacman.context.PacmanGameContext;
import org.isma.pacman.entity.Ghost;
import org.isma.slick2d.drawer.RenderableDrawer;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;

import static org.isma.math.TrigoFunctionFactory.buildSinus;

public class GhostDrawer extends RenderableDrawer<Ghost, PacmanGameContext> {
    private GhostParticleDrawer particleDrawer;
    private final SinusVariation alphaVariation;


    public GhostDrawer(PacmanGameContext context) throws SlickException {
        super(context);
        if (context.enableParticles()) {
            particleDrawer = new GhostParticleDrawer();
        }
        alphaVariation = buildSinus(0.005f, 0.40f, true, 0.25f);
    }


    @Override
    protected void drawAnimation(float xPos, float yPos, Color filter, Animation animation, Ghost ghost) {
        float alpha = (float) alphaVariation.nextValue();

        filter = new Color(1, 1, 1, alpha);
        animation.draw(xPos, yPos, animation.getWidth(), animation.getHeight(), filter);
        if (context.enableParticles()) {
            particleDrawer.renderParticles(ghost, xPos, yPos);
        }
    }


}
