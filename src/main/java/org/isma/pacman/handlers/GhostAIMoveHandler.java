package org.isma.pacman.handlers;

import org.isma.graph.Graph;
import org.isma.pacman.ai.CharacterAI;
import org.isma.pacman.ai.GraphBuilder;
import org.isma.pacman.entity.Ghost;
import org.isma.pacman.entity.Maze;
import org.isma.pacman.entity.Pacman;

public class GhostAIMoveHandler implements MoveHandler<Ghost> {
    private final Pacman pacman;
    private final CharacterAI<Ghost> ai;
    private final Graph pathGraph;

    public GhostAIMoveHandler(Pacman pacman, Maze maze, CharacterAI<Ghost> ai) {
        this.pacman = pacman;
        this.pathGraph = new GraphBuilder(maze.getPathTiledLayer()).buildGraph();
        this.ai = ai;
    }

    public void handleCharacterMove(Ghost ghost, Maze maze) {
        ai.move(pacman, maze, pathGraph);
    }

    @Override
    public void reset() {
        ai.reinitialize();
    }
}
