package org.isma.pacman.drawer;

import org.isma.math.SinusVariation;
import org.isma.math.TrigoFunctionFactory;
import org.isma.pacman.PacmanGameContext;
import org.isma.pacman.entity.Food;
import org.isma.pacman.entity.Maze;
import org.isma.slick2d.drawer.TiledMapDrawer;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.ShapeFill;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public class MazeDrawer extends TiledMapDrawer<Maze, PacmanGameContext> {
    private boolean enableNightEffect = true;

    public MazeDrawer(PacmanGameContext context) throws SlickException {
        super(context);
    }

    private SinusVariation nightVariation = TrigoFunctionFactory.buildSinus(0.001f, 0.2f, true, 1);

    private SinusVariation colorVariation = TrigoFunctionFactory.buildSinus(0.005f, 0.3f, true, 0.5f);

    @Override
    public void draw(Graphics g, Maze maze, int layerIndex) throws SlickException {
        if (context.isDebugMode()) {
            layerIndex = maze.getPathTiledLayer().getIndex();
        } else {
            layerIndex = maze.getDisplayLayer().getIndex();
        }
        super.draw(g, maze, layerIndex);
        for (Food food : maze.getFoodMap().values()) {
            food.draw(maze.getRenderableDrawer());
        }
        if (enableNightEffect) {
//            nightEffect(g);
//            colorEffect(g);
        }
    }

    private void nightEffect(Graphics g) {
        g.fill(new Rectangle(0, 0, Maze.MAZE_WIDTH + 8 /*TODO c quoi cet offset ?*/, Maze.MAZE_HEIGHT), new ShapeFill() {
            public Color colorAt(Shape shape, float x, float y) {
                double v = nightVariation.nextValue();
                return new Color(0, 0, 0, (float) v);
            }

            public Vector2f getOffsetAt(Shape shape, float x, float y) {
                return new Vector2f(0, 0);
            }
        });
    }

    private void colorEffect(Graphics g) {
        g.fill(new Rectangle(0, 0, Maze.MAZE_WIDTH + 8 /*TODO c quoi cet offset ?*/, Maze.MAZE_HEIGHT), new ShapeFill() {
            public Color colorAt(Shape shape, float x, float y) {
                double v = colorVariation.nextValue();
                //return new Color((float)v, (float)v, 0f, 0.2f);
                return new Color((float) v / 3, (float) v / 3, (float) v, 0.5f);
            }

            public Vector2f getOffsetAt(Shape shape, float x, float y) {
                return new Vector2f(0, 0);
            }
        });
    }

}
