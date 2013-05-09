package org.isma.pacman.states;

import org.isma.pacman.PacmanGame;
import org.isma.pacman.context.PacmanGameContext;
import org.isma.pacman.resources.PacmanFontResources;
import org.isma.slick2d.BasicResourcesGameState;
import org.isma.slick2d.drawer.TextDrawer;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

import static org.isma.pacman.PacmanGame.CREDITS_STATE;
import static org.newdawn.slick.Color.*;

public class PacmanCreditsState extends BasicResourcesGameState<PacmanGameContext> {
    private final PacmanFontResources fontResources;

    private UnicodeFont textFont;
    private float yOrigin = 0;

    private TextDrawer drawer;
    protected float zoomRatio;

    @Override
    public int getID() {
        return CREDITS_STATE;
    }

    public PacmanCreditsState(final PacmanGameContext context) throws SlickException {
        super(context);
        fontResources = context.getResourcesManager().getFontResources();

    }

    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        textFont = fontResources.getTextFont();
        drawer = new TextDrawer(getContext());

        zoomRatio = 1f;
        //TODO corriger bug centrage lorsque zoom...
    }

    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException {
        yOrigin = getContext().getScreenSize().height;
    }



    @Override
    protected void handleInputs(GameContainer container, StateBasedGame game) throws SlickException {
        super.handleInputs(container, game);

        Input input = container.getInput();
        if (input.isKeyPressed(Input.KEY_ESCAPE)) {
            game.enterState(PacmanGame.MENU_STATE);
        }
    }

    protected void doUpdate(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
        if (yOrigin > 0) {
            //yOrigin -= 0.5f;
            yOrigin -= 2f;
        }
    }

    protected boolean enableZoom() {
        return false;
    }

//    @Override
//    protected void prepareGraphicsTransformation(Graphics g, GameContext context) {
//        super.prepareGraphicsTransformation(g, context);
//    }

    public void doRender(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        int y = 00;
        y = addCreditLine(y, "Main developper", "Ismaël", null);

        y += addSpace();
        drawCreditTopic(y, "TOOLKIT");

        y += addSpace();
        y = addCreditLine(y, "Language", "Java", null);
        y += addSpace();
        y = addCreditLine(y, "2D library", "slick2D", null);
        y += addSpace();
        y = addCreditLine(y, "Tile editor", "TILED", null);
        y += addSpace();
        y = addCreditLine(y, "Paint program", "Paintbrush", null);

        y += addSpace() + addSpace();
        drawCreditTopic(y, "SPECIAL THANKS TO");

        y += addSpace();
        y = addCreditLine(y, "Game design informations", "Jamey Pittman", "http://www.gamasutra.com/view/feature/132330/the_pacman_dossier.php");

    }

    private void drawCreditTopic(int y, String text) {
        drawer.drawCenter(textFont, y + yOrigin, red, zoomRatio, text);
    }

    private int addSpace() {
        return 30;
    }

    private int addCreditLine(int y, String category, String person, String additionalInformation) {
        drawer.drawCenter(textFont, y + yOrigin, red, zoomRatio, category);

        y += 20;
        drawer.drawCenter(textFont, y + yOrigin, yellow, zoomRatio, person);

        if (additionalInformation != null) {
            y += 10;
            drawer.drawCenter(textFont, y + yOrigin, white, zoomRatio, additionalInformation);
        }
        return y;
    }


}
