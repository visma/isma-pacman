package org.isma.pacman.ai;

import org.isma.graph.Graph;
import org.isma.pacman.entity.Ghost;
import org.isma.pacman.entity.Maze;
import org.isma.pacman.entity.Pacman;
import org.isma.pacman.entity.Target;
import org.isma.pacman.entity.helper.MazeMoveHelper;
import org.isma.slick2d.Direction;

import java.util.List;

import static org.isma.pacman.ai.GhostAI.GhostBehavior.*;

public abstract class GhostAI extends CharacterAI<Ghost> {
    protected enum GhostBehavior {
        CHASE,
        SCATTER,
        FRIGHTENED,
        NAKED
    }

    private static final int MAX_CHASE_DURATION = 120;
    private static final int MAX_SCATTER_DURATION = 40;

    private int scatterDuration;
    private int chaseDuration;

    private GhostBehavior behavior;

    public GhostAI(Ghost ghost) {
        super(ghost);
        this.behavior = CHASE;
    }

    @Override
    protected boolean authorizeTurningBack() {
        return false;
    }

    protected abstract Direction chooseHomeDirection(Maze maze, Graph pathGraph, List<Direction> possiblesMoves);

    protected abstract Direction chooseEscapeDirection(Target target, Maze maze, Graph pathGraph, List<Direction> possiblesMoves);

    protected abstract Direction chooseScatterDirection(Target target, Maze maze, Graph pathGraph, List<Direction> possiblesMoves);

    protected abstract Direction chooseChaseDirection(Pacman pacman, Maze maze, Graph pathGraph, List<Direction> possiblesMoves);

    @Override
    protected boolean mustRecomputeNewDirection(Ghost aCharacter, Maze maze) {
        List<Direction> possibleMoves = MazeMoveHelper.getPossibleMoves(maze, aCharacter);
        return possibleMoves.size() > 2 || !possibleMoves.contains(aCharacter.getCurrentDirection());
    }

    @Override
    protected Direction chooseDirection(Pacman pacman, Maze maze, Graph pathGraph, List<Direction> possiblesMoves) {

        if (character.isFrightened()) {
            behavior = FRIGHTENED;
        } else if (character.isNaked()) {
            behavior = NAKED;
        } else {
            if (chaseDuration > 0) {
                behavior = CHASE;
            } else {
                behavior = SCATTER;
            }
        }

        if (behavior == CHASE) {
            chaseDuration++;
            if (chaseDuration > MAX_CHASE_DURATION) {
                chaseDuration = 0;
                scatterDuration = 1;
                behavior = SCATTER;
            }
        }
        if (behavior == SCATTER) {
            scatterDuration++;
            if (scatterDuration > MAX_SCATTER_DURATION) {
                scatterDuration = 0;
                chaseDuration = 1;
                behavior = CHASE;
            }
        }

        if (behavior == CHASE) {
            return chooseChaseDirection(pacman, maze, pathGraph, possiblesMoves);
        } else if (behavior == SCATTER) {
            return chooseScatterDirection(pacman, maze, pathGraph, possiblesMoves);
        } else if (behavior == FRIGHTENED) {
            return chooseEscapeDirection(pacman, maze, pathGraph, possiblesMoves);
        } else if (behavior == NAKED) {
            return chooseHomeDirection(maze, pathGraph, possiblesMoves);
        }
        throw new RuntimeException("??");
    }

    @Override
    protected void onTileReached() {
        if (character.isFrightened()) {
            behavior = FRIGHTENED;
        } else if (character.isNaked()) {
            behavior = NAKED;
        } else {
            if (chaseDuration > 0) {
                behavior = CHASE;
            } else {
                behavior = SCATTER;
            }
        }

        if (behavior == CHASE) {
            chaseDuration++;
            if (chaseDuration > MAX_CHASE_DURATION) {
                chaseDuration = 0;
                scatterDuration = 1;
                behavior = SCATTER;
            }
        }
        if (behavior == SCATTER) {
            scatterDuration++;
            if (scatterDuration > MAX_SCATTER_DURATION) {
                scatterDuration = 0;
                chaseDuration = 1;
                behavior = CHASE;
            }
        }
    }

}
