package org.isma.pacman.resources;

import org.isma.slick2d.resources.GameResourcesLoader;
import org.isma.slick2d.resources.FontResources;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;

import java.awt.*;

import static org.isma.slick2d.FontFactory.buildFont;

public class PacmanFontResources implements FontResources {
    private GameResourcesLoader resourcesManager;

    private static final String FONT_PACMAN = "font.pacman";
    private static final String FONT_MENU = "font.text";

    private UnicodeFont titleFont;
    private UnicodeFont textFont;

    public PacmanFontResources(GameResourcesLoader resourcesManager) {
        this.resourcesManager = resourcesManager;
    }

    public void loadFonts() throws SlickException {
        titleFont = loadFont(FONT_PACMAN, Color.YELLOW);
        textFont = loadFont(FONT_MENU, Color.WHITE);

    }

    private UnicodeFont loadFont(String propertyName, Color color) throws SlickException {
        String ttfFile = resourcesManager.get(propertyName);
        System.out.printf("loading font %s ...\n", ttfFile);
        return buildFont(ttfFile, color);
    }

    public UnicodeFont getTitleFont() {
        return titleFont;
    }

    public UnicodeFont getTextFont() {
        return textFont;
    }

}
