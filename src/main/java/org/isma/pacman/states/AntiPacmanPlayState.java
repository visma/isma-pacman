package org.isma.pacman.states;

import org.isma.pacman.PacmanGameContext;
import org.isma.pacman.ai.PacmanAI;
import org.isma.pacman.ai.ShortestPathAI;
import org.isma.pacman.entity.Ghost;
import org.isma.pacman.handlers.CharacterInputMoveHandler;
import org.isma.pacman.handlers.GhostAIMoveHandler;
import org.isma.pacman.handlers.MoveHandler;
import org.isma.pacman.handlers.PacmanAIMoveHandler;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import static org.isma.pacman.GhostEnum.BLINKY;
import static org.isma.pacman.PacmanGame.ANTIPACMANPLAY_STATE;

public class AntiPacmanPlayState extends AbstractPacmanPlayState {
    public AntiPacmanPlayState(PacmanGameContext context) throws SlickException {
        super(context);
    }

    @Override
    public int getID() {
        return ANTIPACMANPLAY_STATE;
    }

    @Override
    protected void configurePacmanMoveHandlerAndAI(GameContainer container) {
        pacman.setMoveHandler(new PacmanAIMoveHandler(ghosts));
        pacman.setAi(new PacmanAI(pacman, ghosts, maze.getFoodTiledLayer()));

    }

    @Override
    protected void configureGhostsMoveHandlerAndAI(GameContainer container) {
        MoveHandler<Ghost> aiMoveHandler = new GhostAIMoveHandler(pacman, maze);
        MoveHandler<Ghost> inputMoveHandler = new CharacterInputMoveHandler<Ghost>(container.getInput());
        for (Ghost ghost : ghosts) {
            if (ghost.getName().equals(BLINKY.getName())) {
                ghost.setMoveHandler(inputMoveHandler);
                ghost.setAi(null);
                //TODO faire ça via de la conf
                ghost.setSpeed(ghost.getSpeed() - 0.2f);
            } else {
                ghost.setMoveHandler(aiMoveHandler);
                ghost.setAi(new ShortestPathAI(ghost, maze));
                //TODO faire ça via de la conf
                ghost.setSpeed(ghost.getSpeed() - 0.2f);
            }
        }
    }
}
