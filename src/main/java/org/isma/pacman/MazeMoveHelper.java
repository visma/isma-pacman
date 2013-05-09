package org.isma.pacman;

import org.isma.pacman.entity.Character;
import org.isma.pacman.entity.Maze;
import org.isma.slick2d.Direction;

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


}
