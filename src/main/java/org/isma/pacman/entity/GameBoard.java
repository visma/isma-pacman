package org.isma.pacman.entity;

import org.isma.pacman.context.PacmanGameContext;
import org.isma.pacman.resources.PacmanFontResources;
import org.isma.pacman.resources.PacmanResourcesManager;
import org.isma.slick2d.entity.GameObject;
import org.isma.slick2d.drawer.RenderableDrawer;
import org.isma.slick2d.drawer.TextDrawer;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.geom.Rectangle;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static org.isma.pacman.entity.Fruit.FruitEnum;
import static org.newdawn.slick.Color.red;


public class GameBoard extends GameObject<PacmanGameContext> {
    private static final int FRUIT_X_POSITION = 210;

    private RenderableDrawer renderableDrawer;
    private TextDrawer textDrawer;

    private final UnicodeFont textFont;
    private final Image pacmanLiveImage;
    private final Point position;

    private final Map<Rectangle, Integer> ghostPointsMap = new HashMap<Rectangle, Integer>();

    public GameBoard(PacmanGameContext context) {
        super(context);
        PacmanResourcesManager resourcesHandler = context.getResourcesManager();
        PacmanFontResources fontResources = resourcesHandler.getFontResources();
        SpriteSheet pacmanSprites = resourcesHandler.getImageResources().getPacmanSprites();

        pacmanLiveImage = pacmanSprites.getSprite(0, 0);
        textFont = fontResources.getTextFont();

        position = new Point(0, 280);
    }


    public void draw(int pacmanLives, Game game, Round round, Fruit fruit) {
        renderScore(game.getScore());
        renderLives(pacmanLives);
        renderFruit(fruit);
        renderGhostPoints();
        renderFruitPoints(round.showPointsFruit, fruit);

        if (game.starting) {
            drawTextUnderGhostHouse("READY!");
        } else if (round.starting) {
            drawTextUnderGhostHouse("ROUND " + round.index);
        } else if (game.over) {
            drawTextUnderGhostHouse("GAME OVER");
        } else if (game.pause) {
            drawTextUnderGhostHouse("PAUSE");
        }
    }

    private void renderFruitPoints(boolean showPointsFruit, Fruit fruit) {
        if (showPointsFruit) {
            FruitEnum eated = fruit.getPreviousFruit();
            Image image = getContext().getResourcesManager().getImageResources().getFruitPointsImage(eated.ordinal());
            renderableDrawer.draw(image, fruit.getX(), fruit.getY(), null, null);
        }
    }


    private void renderGhostPoints() {
        for (Rectangle ghostPosition : ghostPointsMap.keySet()) {
            Integer spriteIndex = ghostPointsMap.get(ghostPosition);
            Image image = getContext().getResourcesManager().getImageResources().getGhostPointsImage(spriteIndex);

            renderableDrawer.draw(image, ghostPosition.getX(), ghostPosition.getY(), null, null);
        }
    }

    private void drawTextUnderGhostHouse(String text) {
        int xCenter = (Maze.MAZE_WIDTH - textFont.getWidth(text)) / 2;
        int yCenter = (Maze.MAZE_HEIGHT - textFont.getHeight(text)) / 2;
        yCenter += 15;

        textDrawer.draw(textFont, xCenter, yCenter, red, text);
    }

    private void renderFruit(Fruit fruit) {
        int x = position.x + FRUIT_X_POSITION;
        int y = position.y;
        fruit.draw(renderableDrawer, x, y);
    }

    private void renderLives(int pacmanLives) {
        int x = position.x + FRUIT_X_POSITION;
        int y = position.y;
        int spread = 0;

        for (int i = 0; i < pacmanLives; i++) {
            x -= pacmanLiveImage.getWidth() + spread;
            renderableDrawer.draw(pacmanLiveImage, x, y, null, null);
        }
    }

    private void renderScore(long score) {
        String text = "SCORE :" + score;
        float x = position.x;
        float y = position.y;
        textDrawer.draw(textFont, x, y, Color.white, text);
    }

    public void addGhostEatedEvent(Ghost ghost, int ghostEatedIndex, long duration) {
        final Rectangle position = ghost.getCenter();
        ghostPointsMap.put(position, ghostEatedIndex);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                ghostPointsMap.remove(position);
            }
        }, duration);
    }

    public void setRenderableDrawer(RenderableDrawer renderableDrawer) {
        this.renderableDrawer = renderableDrawer;
    }

    public void setTextDrawer(TextDrawer textDrawer) {
        this.textDrawer = textDrawer;
    }
}
