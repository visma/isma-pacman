package org.isma.pacman.ai;

import org.isma.graph.DijkstraAlgorithm;
import org.isma.graph.Graph;
import org.isma.graph.Vertex;
import org.isma.pacman.entity.Ghost;
import org.isma.pacman.entity.Maze;
import org.isma.pacman.entity.Pacman;
import org.isma.pacman.entity.Target;
import org.isma.pacman.tileset.FoodTiledLayer;
import org.isma.slick2d.Direction;
import org.newdawn.slick.geom.Rectangle;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.isma.slick2d.PositionHelper.findTilesAtPosition;
import static org.isma.slick2d.PositionHelper.getDistance;
import static org.isma.slick2d.RandomUtils.getRandomElement;

public class PacmanAI extends CharacterAI<Pacman> {
    private List<Ghost> ghosts;
    private FoodTiledLayer foodTiledLayer;

    public PacmanAI(Pacman character, List<Ghost> ghosts, FoodTiledLayer foodTiledLayer) {
        super(character);
        this.ghosts = ghosts;
        this.foodTiledLayer = foodTiledLayer;
    }

    @Override
    protected boolean mustRecomputeNewDirection(Pacman aCharacter, Maze maze) {
        return true;
    }

    @Override
    protected boolean authorizeTurningBack() {
        return true;
    }


    @Override
    protected Direction chooseDirection(Pacman pacman, Maze maze, Graph pathGraph, List<Direction> possiblesMoves) {

        List<Point> tilesAtPosition = findTilesAtPosition(maze.getTiledMap(), character.getCenter());
        if (tilesAtPosition.size() != 1) {
            throw new RuntimeException("chooseDirection must be called when ghost is exactly on only one tile position");
        }
        Point currentTile = tilesAtPosition.get(0);
        Target target = newTargetV1(maze);
        Point currentTargetTile = getTargetTilePosition(target, maze);


        DijkstraAlgorithm pathAlgo = new DijkstraAlgorithm(pathGraph);
        pathAlgo.execute(new Vertex<Point>(currentTile));
        LinkedList<Vertex> path = pathAlgo.getPath(new Vertex<Point>(currentTargetTile));

        if (path == null) {
            return null;
        }
        Direction aiDirection = PathFinding.nextDirection(path, maze.getTilesBounds());
        //TODO faire plus propre
        pacman.setStopped(false);
        return aiDirection;
    }

    private Target newTargetV1(Maze maze) {
        double farestFoodFromGhosts = 0;
        Target choosedFood = null;

        for (final Point foodTile : maze.getFoodMap().keySet()) {
            Target target = new Target() {
                public Rectangle getTarget() {
                    return foodTiledLayer.getRectangle(foodTile.x, foodTile.y);
                }
            };
            if (getGhostDistance(target) > farestFoodFromGhosts) {
                choosedFood = target;
            }
        }
        //Il ne reste plus que la nourriture sur les fantomes
        if (choosedFood == null) {
            final Point randomFoodLocationChoice = getRandomElement(new ArrayList<Point>(maze.getFoodMap().keySet()));
            return new Target() {
                public Rectangle getTarget() {
                    return foodTiledLayer.getRectangle(randomFoodLocationChoice.x, randomFoodLocationChoice.y);
                }
            };
        }
        return choosedFood;
    }

    private double getGhostDistance(Target food) {
        float[] foodCenter = food.getTarget().getCenter();
        double distance = 99999;
        for (Ghost ghost : ghosts) {
            float[] ghostCenter = ghost.getCenter().getCenter();
            double distanceGhost = getDistance(foodCenter[0], foodCenter[1], ghostCenter[0], foodCenter[1]);
            if (distanceGhost < distance) {
                distance = distanceGhost;
            }
        }
        return distance;
    }

}
