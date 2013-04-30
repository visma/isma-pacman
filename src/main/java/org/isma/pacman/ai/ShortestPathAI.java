package org.isma.pacman.ai;

import org.isma.graph.DijkstraAlgorithm;
import org.isma.graph.Graph;
import org.isma.graph.Vertex;
import org.isma.pacman.entity.*;
import org.isma.slick2d.Direction;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.isma.slick2d.PositionHelper.findTilesAtPosition;
import static org.isma.slick2d.PositionHelper.getReverseDirection;
import static org.isma.slick2d.RandomUtils.getRandomElement;

public class ShortestPathAI extends GhostAI {
    private final GhostCorner corner1;
    private final GhostCorner corner2;
    private final GhostCorner corner3;

    private Target actualCorner;

    public ShortestPathAI(Ghost ghost, Maze maze) {
        super(ghost);
        corner1 = maze.getGhostCorner(ghost, 1);
        corner2 = maze.getGhostCorner(ghost, 2);
        corner3 = maze.getGhostCorner(ghost, 3);
        actualCorner = corner1;
    }

    @Override
    protected Direction chooseEscapeDirection(Target target, Maze maze, Graph pathGraph, List<Direction> possiblesMoves) {
        //Choose random direction only if at an intersection
        if (possiblesMoves.size() <= 2 && possiblesMoves.contains(currentDirection)) {
            return currentDirection;
        }
        List<Direction> clonePossibleMoves = new ArrayList<Direction>(possiblesMoves);
        //J'imagine qu'il n'y a qu'une direction possible si le fantome rentre dans la maison et va sur une extremité
        if (clonePossibleMoves.size() > 1) {
            clonePossibleMoves.remove(getReverseDirection(currentDirection));
        }
        return getRandomElement(clonePossibleMoves);
    }

    @Override
    protected Direction chooseScatterDirection(Target target, Maze maze, Graph pathGraph, List<Direction> possiblesMoves) {
        Direction direction = choseTargetDirection(actualCorner, maze, pathGraph);
        if (direction == null) {
            if (actualCorner == corner1) {
                actualCorner = corner2;
            } else if (actualCorner == corner2) {
                actualCorner = corner3;
            } else {
                actualCorner = corner1;
            }
            return choseTargetDirection(actualCorner, maze, pathGraph);
        }
        return direction;
    }

    @Override
    protected Direction chooseChaseDirection(Pacman pacman, Maze maze, Graph pathGraph, List<Direction> possiblesMoves) {
        return choseTargetDirection(pacman, maze, pathGraph);
    }

    @Override
    protected Direction chooseHomeDirection(Maze maze, Graph pathGraph, List<Direction> possiblesMoves) {
        Target house = maze.getGhostHouseLocation();
        return choseTargetDirection(house, maze, pathGraph);
    }


    private Direction choseTargetDirection(Target target, Maze maze, Graph pathGraph) {
        List<Point> tilesAtPosition = findTilesAtPosition(maze.getTiledMap(), character.getCenter());
        if (tilesAtPosition.size() != 1) {
            throw new RuntimeException("chooseDirection must be called when ghost is exactly on only one tile position");
        }
        Point currentGhostTile = tilesAtPosition.get(0);
        Point currentTargetTile = getTargetTilePosition(target, maze);


        DijkstraAlgorithm pathAlgo = new DijkstraAlgorithm(pathGraph);
        pathAlgo.execute(new Vertex<Point>(currentGhostTile));
        LinkedList<Vertex> path = pathAlgo.getPath(new Vertex<Point>(currentTargetTile));

        if (path == null) {
            return null;
        }
        Direction direction = PathFinding.nextDirection(path, maze.getTilesBounds());
        return direction;
    }
}
