package org.isma.pacman.handlers;

import org.isma.pacman.ai.PacmanAIGraphBuilder;
import org.isma.pacman.entity.Ghost;
import org.isma.pacman.entity.Maze;
import org.isma.pacman.entity.Pacman;

import java.util.List;

public class PacmanAIMoveHandler implements MoveHandler<Pacman> {
    private List<Ghost> ghosts;


    public PacmanAIMoveHandler(List<Ghost> ghosts) {
        this.ghosts = ghosts;
    }


    public void handleCharacterMove(Pacman pacman, Maze maze) {
        PacmanAIGraphBuilder graphBuilder = new PacmanAIGraphBuilder(maze.getPathTiledLayer(), maze.getFoodMap(), ghosts);
        pacman.getAi().move(pacman, maze, graphBuilder.buildGraph());
    }

}
