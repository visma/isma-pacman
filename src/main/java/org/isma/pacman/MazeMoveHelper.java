package org.isma.pacman;

import org.isma.pacman.entity.Character;
import org.isma.pacman.entity.Maze;
import org.isma.slick2d.Direction;
import org.isma.slick2d.PositionHelper;
import org.newdawn.slick.geom.Rectangle;

import java.util.ArrayList;
import java.util.List;

import static org.isma.slick2d.Direction.*;

public class MazeMoveHelper {

    private MazeMoveHelper() {
    }


    public static List<Direction> getPossibleMoves(Maze maze, org.isma.pacman.entity.Character character) {
        List<Direction> directions = new ArrayList<Direction>();
        if (canMoveLeft(maze, character)) {
            directions.add(WEST);
        }
        if (canMoveRight(maze, character)) {
            directions.add(EAST);
        }
        if (canMoveTop(maze, character)) {
            directions.add(NORTH);
        }
        if (canMoveBottom(maze, character)) {
            directions.add(SOUTH);
        }
        return directions;
    }

    public static boolean canMoveLeft(Maze maze, Character character) {
        return maze.getPathTiledLayer().existPath(character.getLeftLine(), character.getMoveLevel());
    }

    public static boolean canMoveRight(Maze maze, Character character) {
        return maze.getPathTiledLayer().existPath(character.getRightLine(), character.getMoveLevel());
    }

    public static boolean canMoveTop(Maze maze, Character character) {
        return maze.getPathTiledLayer().existPath(character.getTopLine(), character.getMoveLevel());
    }

    public static boolean canMoveBottom(Maze maze, Character character) {
        return maze.getPathTiledLayer().existPath(character.getBottomLine(), character.getMoveLevel());
    }

    public static void move(Character character, Direction direction, Maze level) {
        if (direction == WEST) {
            character.setX(character.getX() - character.getSpeed());
        } else if (direction == EAST) {
            character.setX(character.getX() + character.getSpeed());
        } else if (direction == NORTH) {
            character.setY(character.getY() - character.getSpeed());
        } else if (direction == SOUTH) {
            character.setY(character.getY() + character.getSpeed());
        } else {
            throw new RuntimeException("unexpected input move");
        }
        Rectangle center = character.getCenter();
        if (PositionHelper.isOutOfBounds(level.getBounds(), center)) {
            Direction boundOut = PositionHelper.getBoundOut(level.getBounds(), center);
            if (boundOut == EAST) {
                character.setX(level.getBounds().getMinX());
            }
            if (boundOut == Direction.WEST) {
                character.setX(level.getBounds().getMaxX() - character.getWidth());
            }
            if (boundOut == Direction.NORTH) {
                character.setY(level.getBounds().getMaxY() - character.getHeight());
            }
            if (boundOut == Direction.SOUTH) {
                character.setY(level.getBounds().getMinY());
            }
        }
        character.setCurrentDirection(direction);
    }

}
