package org.isma.pacman.drawer;

import org.isma.math.SinusVariation;
import org.isma.slick2d.context.GameContext;
import org.isma.slick2d.drawer.TextDrawer;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;

import static org.isma.math.TrigoFunctionFactory.buildSinus;

public class TitleDrawer extends TextDrawer {
    private final SinusVariation sinusVariation;

    public TitleDrawer(GameContext context) {
        super(context);
        sinusVariation = buildSinus(0.05f, 0.50f, true, 0.5f);
    }

    @Override
    public void draw(Font font, float x, float y, Color color, String text) {
        float rg = (float) sinusVariation.nextValue();
        Color newColor = new Color(rg, rg, 0f);
        super.draw(font, x, y, newColor, text);
    }
}
