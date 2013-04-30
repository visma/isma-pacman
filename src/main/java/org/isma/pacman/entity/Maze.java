package org.isma.pacman.entity;

import org.isma.pacman.PacmanGameContext;
import org.isma.pacman.drawer.MazeDrawer;
import org.isma.pacman.tileset.DisplayTiledLayer;
import org.isma.pacman.tileset.FoodTiledLayer;
import org.isma.pacman.tileset.PathTiledLayer;
import org.isma.slick2d.Direction;
import org.isma.slick2d.PositionHelper;
import org.isma.slick2d.drawer.RenderableDrawer;
import org.isma.slick2d.tileset.TiledObject;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.isma.pacman.PacmanProperty.LEVEL_TILES;
import static org.isma.slick2d.Direction.*;

public class Maze extends TiledObject<PacmanGameContext> {
    public static final int MAZE_WIDTH = 224;
    public static final int MAZE_HEIGHT = 288;

    private final DisplayTiledLayer displayLayer;
    private final FoodTiledLayer foodLayer;
    private final PathTiledLayer pathLayer;
    private final Map<Point, Food> foodMap = new HashMap<Point, Food>();
    private final GhostHouse ghostHouse;
    private final Rectangle fruitSpawnPoint;

    private RenderableDrawer renderableDrawer;
    private MazeDrawer tiledMapDrawer;

    public Maze(PacmanGameContext context) throws SlickException {
        super(context);
        loadTiledMap();

        displayLayer = new DisplayTiledLayer(getTiledMap());
        foodLayer = new FoodTiledLayer(getTiledMap());
        pathLayer = new PathTiledLayer(getTiledMap());

        ghostHouse = new GhostHouse(pathLayer.getGhostHouse());
        fruitSpawnPoint = foodLayer.getFruitSpawnPoint();
        loadFood();
    }

    public void loadFood() throws SlickException {
        List<Point> foodPositions = foodLayer.getFoodPositionList();
        for (Point foodPosition : foodPositions) {
            if (foodLayer.properties().isPacgum(foodPosition.x, foodPosition.y)) {
                foodMap.put(foodPosition, MazeBuilder.addPacgum(foodPosition, getTiledMap(), context));
            } else if (foodLayer.properties().isEnergizer(foodPosition.x, foodPosition.y)) {
                foodMap.put(foodPosition, MazeBuilder.addEnergizer(foodPosition, getTiledMap(), context));
            } else {
                throw new RuntimeException("wtf ??");
            }
        }
    }


    protected String getTiledMapPropertyName() {
        return LEVEL_TILES.getPropertyName();
    }


    public void draw(Graphics g) throws SlickException {
        int layerIndex;
        if (context.isDebugMode()) {
            layerIndex = pathLayer.getIndex();
        } else {
            layerIndex = displayLayer.getIndex();
        }
        tiledMapDrawer.draw(g, getTiledMap(), layerIndex);
        for (Food food : foodMap.values()) {
            food.draw(renderableDrawer);
        }
    }

    public List<Direction> getPossibleMoves(org.isma.pacman.entity.Character character) {
        List<Direction> directions = new ArrayList<Direction>();
        if (canMoveLeft(character)) {
            directions.add(WEST);
        }
        if (canMoveRight(character)) {
            directions.add(EAST);
        }
        if (canMoveTop(character)) {
            directions.add(NORTH);
        }
        if (canMoveBottom(character)) {
            directions.add(SOUTH);
        }
        return directions;
    }

    public boolean canMoveLeft(Character character) {
        return pathLayer.existPath(character.getLeftLine(), character.getMoveLevel());
    }

    public boolean canMoveRight(Character character) {
        return pathLayer.existPath(character.getRightLine(), character.getMoveLevel());
    }

    public boolean canMoveTop(Character character) {
        return pathLayer.existPath(character.getTopLine(), character.getMoveLevel());
    }

    public boolean canMoveBottom(Character character) {
        return pathLayer.existPath(character.getBottomLine(), character.getMoveLevel());
    }


    public Food feed(Pacman pacman) {
        Rectangle pacmanCenter = pacman.getCenter();
        List<Point> tiles = PositionHelper.findTilesAtPosition(getTiledMap(), pacmanCenter);
        for (Point tile : tiles) {
            Food food = foodMap.get(tile);
            if (food != null) {
                return foodMap.remove(tile);
            }
        }
        return null;
    }

    public boolean hasRemainingFood() {
        return !foodMap.isEmpty();
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < getTiledMap().getHeight(); y++) {
            for (int x = 0; x < getTiledMap().getWidth(); x++) {
                if (!pathLayer.properties().isPath(x, y)) {
                    sb.append("#");
                } else {
                    if (foodLayer.properties().isPacgum(x, y)) {
                        sb.append(".");
                    } else if (foodLayer.properties().isEnergizer(x, y)) {
                        sb.append("@");
                    } else {
                        sb.append(" ");
                    }
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public Rectangle getFruitSpawnPoint() {
        return fruitSpawnPoint;
    }

    public GhostHouse getGhostHouseLocation() {
        return ghostHouse;
    }


    public GhostCorner getGhostCorner(Ghost ghost, int cornerIndex) {
        return new GhostCorner(pathLayer.getGhostCorner(ghost.getName(), cornerIndex));
    }

    public PathTiledLayer getPathTiledLayer() {
        return pathLayer;
    }

    public FoodTiledLayer getFoodTiledLayer() {
        return foodLayer;
    }

    public Map<Point, Food> getFoodMap() {
        return foodMap;
    }

    public void setRenderableDrawer(RenderableDrawer renderableDrawer) {
        this.renderableDrawer = renderableDrawer;
    }

    public void setTiledMapDrawer(MazeDrawer tiledMapDrawer) {
        this.tiledMapDrawer = tiledMapDrawer;
    }
}
