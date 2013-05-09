package org.isma.pacman.states;

import org.isma.pacman.PacmanGame;
import org.isma.pacman.PacmanGameContext;
import org.isma.pacman.ai.ShortestPathAI;
import org.isma.pacman.entity.Ghost;
import org.isma.pacman.entity.Pacman;
import org.isma.pacman.handlers.CharacterInputMoveHandler;
import org.isma.pacman.handlers.GhostAIMoveHandler;
import org.isma.pacman.handlers.MoveHandler;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import static org.isma.pacman.PacmanGame.PLAY_STATE;

public class PacmanPlayState extends AbstractPacmanPlayState {
    public PacmanPlayState(PacmanGameContext context) throws SlickException {
        super(context);
    }

    @Override
    public int getID() {
        return PLAY_STATE;
    }

    @Override
    protected boolean captureScreen(Input input) {
        return input.isKeyPressed(Input.KEY_P);
    }

    @Override
    protected void quit(GameContainer container, StateBasedGame stateBasedGame) {
        long scored = game.getScore();
        if (getContext().getHighScoreManager().isRanked(scored)){
            getContext().getHighScoreManager().push(scored);
            stateBasedGame.enterState(PacmanGame.HIGHSCORES_STATE);
        }else {
            stateBasedGame.enterState(PacmanGame.MENU_STATE);
        }
    }

    @Override
    protected void configurePacmanMoveHandlerAndAI(GameContainer container) {
        gameMovesManager.put(pacman, new CharacterInputMoveHandler<Pacman>(container.getInput()));
    }

    @Override
    protected void configureGhostsMoveHandlerAndAI(GameContainer container) {
        for (Ghost ghost : ghosts) {
            ShortestPathAI ai = new ShortestPathAI(ghost, maze);
            MoveHandler<Ghost> ghostMoveHandler = new GhostAIMoveHandler(pacman, maze, ai);
            gameMovesManager.put(ghost, ghostMoveHandler);
        }
    }
}
