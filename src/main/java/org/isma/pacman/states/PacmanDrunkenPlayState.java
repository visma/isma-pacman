package org.isma.pacman.states;

import org.isma.pacman.PacmanGame;
import org.isma.pacman.context.PacmanGameContext;
import org.isma.slick2d.context.GameContext;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import static org.isma.pacman.PacmanGame.DRUNKEN_PLAY_STATE;

public class PacmanDrunkenPlayState extends PacmanPlayState {
    private float angle;

    public PacmanDrunkenPlayState(PacmanGameContext context) throws SlickException {
        super(context);
    }


    @Override
    public int getID() {
        return DRUNKEN_PLAY_STATE;
    }

    @Override
    public void enter(GameContainer container, StateBasedGame stateBasedGame) throws SlickException {
        angle = 0;
        super.enter(container, stateBasedGame);
    }

    @Override
    protected void prepareGraphicsTransformation(Graphics g, GameContext context) {
        angle += 0.05f;
        g.rotate(context.getScreenSize().width / 2, context.getScreenSize().height / 2, angle);
        super.prepareGraphicsTransformation(g, context);
    }

    @Override
    protected void quit(GameContainer container, StateBasedGame game) {
        game.enterState(PacmanGame.MENU_STATE);
    }


}
