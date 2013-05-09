package org.isma.pacman.handlers;

import org.isma.pacman.ai.CharacterAI;
import org.isma.pacman.ai.PacmanAIGraphBuilder;
import org.isma.pacman.entity.Ghost;
import org.isma.pacman.entity.Maze;
import org.isma.pacman.entity.Pacman;

import java.util.List;

public class PacmanAIMoveHandler implements MoveHandler<Pacman> {
    private List<Ghost> ghosts;
    private CharacterAI<Pacman> ai;


    public PacmanAIMoveHandler(List<Ghost> ghosts, CharacterAI<Pacman> ai) {
        this.ghosts = ghosts;
        this.ai = ai;
    }


    public void handleCharacterMove(Pacman pacman, Maze maze) {
        PacmanAIGraphBuilder graphBuilder = new PacmanAIGraphBuilder(maze.getPathTiledLayer(), maze.getFoodMap(), ghosts);
        ai.move(pacman, maze, graphBuilder.buildGraph());
    }

    @Override
    public void reset() {
        ai.reinitialize();
    }

}
