package org.isma.pacman.states;

import org.isma.pacman.PacmanGameContext;
import org.isma.pacman.ai.PacmanAI;
import org.isma.pacman.ai.ShortestPathAI;
import org.isma.pacman.entity.Ghost;
import org.isma.pacman.handlers.GhostAIMoveHandler;
import org.isma.pacman.handlers.MoveHandler;
import org.isma.pacman.handlers.PacmanAIMoveHandler;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import static org.isma.pacman.PacmanGame.DEMO_STATE;

public class PacmanDemoState extends AbstractPacmanPlayState {
    public PacmanDemoState(PacmanGameContext context) throws SlickException {
        super(context);
    }

    @Override
    public void enter(GameContainer container, StateBasedGame stateBasedGame) throws SlickException {
        container.setTargetFrameRate(getContext().getResourcesManager().getConfiguration().getDemoTargetFrameRate());
        super.enter(container, stateBasedGame);
    }

    @Override
    public void leave(GameContainer container, StateBasedGame stateBasedGame) throws SlickException {
        int fps = getContext().getResourcesManager().getConfiguration().getTargetFrameRate();
        container.setTargetFrameRate(fps);
        super.leave(container, stateBasedGame);
    }


    @Override
    public void doUpdate(GameContainer container, StateBasedGame stateBasedGame, int delta) throws SlickException {
        super.doUpdate(container, stateBasedGame, delta);
    }


    @Override
    public int getID() {
        return DEMO_STATE;
    }

    @Override
    protected void configurePacmanMoveHandlerAndAI(GameContainer container) {
        PacmanAI ai = new PacmanAI(pacman, ghosts, maze.getFoodTiledLayer());
        gameMovesManager.put(pacman, new PacmanAIMoveHandler(ghosts, ai));
    }

    @Override
    protected void configureGhostsMoveHandlerAndAI(GameContainer container) {
        for (Ghost ghost : ghosts) {
            ShortestPathAI ai = new ShortestPathAI(ghost, maze);
            MoveHandler ghostMoveHandler = new GhostAIMoveHandler(pacman, maze, ai);
            gameMovesManager.put(ghost, ghostMoveHandler);
        }
    }
}
