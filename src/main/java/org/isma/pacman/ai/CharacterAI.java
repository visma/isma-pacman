package org.isma.pacman.ai;

import org.isma.graph.Graph;
import org.isma.pacman.entity.Character;
import org.isma.pacman.entity.Maze;
import org.isma.pacman.entity.Pacman;
import org.isma.pacman.entity.Target;
import org.isma.slick2d.Direction;

import java.awt.*;
import java.util.List;

import static org.isma.slick2d.Direction.EAST;
import static org.isma.slick2d.PositionHelper.*;
import static org.isma.slick2d.RandomUtils.getRandomElement;

public abstract class CharacterAI<C extends Character> {
    protected Direction currentDirection;
    protected final C character;

    protected CharacterAI(C character) {
        this.character = character;
        reinitialize();
    }

    public void reinitialize() {
        //TODO valeur par défaut un peu bidon
        currentDirection = EAST;
    }

    protected abstract boolean authorizeTurningBack();

    public void move(Pacman pacman, Maze maze, Graph pathGraph) {
        if (isATile(maze.getTiledMap(), character.getCenter())) {
            onTileReached();

            if (mustRecomputeNewDirection(character, maze)) {
                List<Direction> possiblesMoves = maze.getPossibleMoves(character);
                Direction aiDirection = chooseDirection(pacman, maze, pathGraph, possiblesMoves);
                Direction reverseDirection = getReverseDirection(currentDirection);
                if (aiDirection == null || (aiDirection == reverseDirection && !authorizeTurningBack())) {
                    //Les persos ne retournent jamais en arrière (sauf ceux coincés dans la maison au début)...
                    if (possiblesMoves.contains(reverseDirection) && possiblesMoves.size() > 1) {
                        possiblesMoves.remove(reverseDirection);
                    }
                    currentDirection = getRandomElement(possiblesMoves);
                } else {
                    currentDirection = aiDirection;
                }
            }
        }
        character.move(currentDirection, maze);
    }

    protected void onTileReached() {
    }

    protected abstract Direction chooseDirection(Pacman pacman, Maze maze, Graph pathGraph, List<Direction> possiblesMoves);

    protected abstract boolean mustRecomputeNewDirection(C aCharacter, Maze maze);

    protected Point getTargetTilePosition(Target target, Maze maze) {
        return findTilesAtPosition(maze.getTiledMap(), target.getTarget()).get(0);
    }

}
