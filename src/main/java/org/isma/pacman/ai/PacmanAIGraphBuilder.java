package org.isma.pacman.ai;

import org.isma.graph.Vertex;
import org.isma.pacman.entity.Energizer;
import org.isma.pacman.entity.Food;
import org.isma.pacman.entity.Ghost;
import org.isma.pacman.entity.Pacgum;
import org.isma.pacman.tileset.PathTiledLayer;
import org.newdawn.slick.geom.Rectangle;

import java.awt.*;
import java.util.List;
import java.util.Map;

import static org.isma.slick2d.PositionHelper.getDistance;

public class PacmanAIGraphBuilder extends GraphBuilder {
    private static final int DOT_WEIGHT = -30;
    private static int GHOST_WEIGHT = 1000;

    // Mode IA : pacman VEUT bouffer du fantome
    private static final int GHOST_FOOD_WEIGHT = -GHOST_WEIGHT / 2;

    // Mode IA : pacman VEUT PAS bouffer du fantome (perte de temps
    //private static final int GHOST_FOOD_WEIGHT = ??;

    private static final int ENERGIZER_WEIGHT = -GHOST_WEIGHT;
    private static final int THREE_WAYS_INTERSECTION_WEIGHT = -1;
    private static final int FOUR_WAYS_INTERSECTION_WEIGHT = -2;

    private final int tileSize;

    private List<Ghost> ghosts;
    private Map<Point, Food> foodMap;

    public PacmanAIGraphBuilder(PathTiledLayer pathTiledLayer, Map<Point, Food> foodMap, List<Ghost> ghosts) {
        super(pathTiledLayer);
        tileSize = pathTiledLayer.getTiledMap().getTileWidth();
        this.foodMap = foodMap;
        this.ghosts = ghosts;
    }

    @Override
    protected int getWeight(Map<Point, Vertex> vertexes, Vertex<Point> source, Vertex<Point> destination) {
        Point dest = destination.getId();
        int weight = 1;

        int ghostWeight = addGhostsWeight(dest);
        weight += ghostWeight;
        if (foodMap.containsKey(new Point(dest))) {
            Food food = foodMap.get(new Point(dest));
            if (food instanceof Pacgum) {
                weight += DOT_WEIGHT;
            } else if (food instanceof Energizer) {
                weight += addEnergizerWeight(ghostWeight);
            }
        }
        weight += addIntersectionWeight(vertexes, dest);
        return weight;
    }

    private int addEnergizerWeight(int ghostWeight) {
        if (ghostWeight > GHOST_WEIGHT) {
            return ghostWeight + 1;
        } else {
            return DOT_WEIGHT;
        }
    }


    //TODO ça donne pas des supers résultats... bug ou pb de poids ?
    private int addIntersectionWeight(Map<Point, Vertex> vertexes, Point dest) {
        int height = pathTiledLayer.getTiledMap().getHeight();
        int width = pathTiledLayer.getTiledMap().getWidth();

        Point leftPosition = new Point(dest.x - 1, dest.y);
        Point rightPosition = new Point(dest.x + 1, dest.y);
        Point topPosition = new Point(dest.x, dest.y - 1);
        Point bottomPosition = new Point(dest.x, dest.y + 1);

        if (leftPosition.x == -1) {
            leftPosition.x = width - 1;
        }
        if (rightPosition.x == width) {
            rightPosition.x = 0;
        }
        if (topPosition.y == -1) {
            topPosition.y = height - 1;
        }
        if (bottomPosition.y == height) {
            bottomPosition.y = 0;
        }
        int countPossibleMoves = 0;

        if (vertexes.containsKey(leftPosition)) {
            countPossibleMoves++;
        }
        if (vertexes.containsKey(rightPosition)) {
            countPossibleMoves++;
        }
        if (vertexes.containsKey(topPosition)) {
            countPossibleMoves++;
        }
        if (vertexes.containsKey(bottomPosition)) {
            countPossibleMoves++;
        }
        if (countPossibleMoves == 3) {
            return THREE_WAYS_INTERSECTION_WEIGHT;
        }
        if (countPossibleMoves == 4) {
            return FOUR_WAYS_INTERSECTION_WEIGHT;
        }
        return 0;
    }

    private int addGhostsWeight(Point dest) {
        int weight = 0;
        final float dangerRatio = 0.7f;
        int radius = 5;//4 ca avait l air pas mal

        Rectangle destArea = pathTiledLayer.getRectangle(dest.x, dest.y);
        for (Ghost ghost : ghosts) {
            if (destArea.intersects(ghost.getCenter())) {
                weight += getDanger(ghost);
            } else {
                float[] destCenter = destArea.getCenter();
                float[] ghostCenter = ghost.getCenter().getCenter();
                float tileDistance = (float) (getDistance(destCenter[0], destCenter[1], ghostCenter[0], ghostCenter[1]) / tileSize);
                if (tileDistance < radius) {
                    float tmpWeight = (dangerRatio * getDanger(ghost)) / tileDistance;
                    weight += tmpWeight;
                }

            }
        }
        return weight;
    }

    //TODO trouver mieux
    private int getDanger(Ghost ghost) {
        final int fearEnoughValue = 40;
        if (ghost.isFrightened() && ghost.getFrightenedDuration() > fearEnoughValue) {
            return GHOST_FOOD_WEIGHT;
        } else if (ghost.isNaked()) {
            return GHOST_WEIGHT / 8;
        }
        return GHOST_WEIGHT;
    }
}
