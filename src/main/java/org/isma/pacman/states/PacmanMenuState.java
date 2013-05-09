package org.isma.pacman.states;

import org.isma.pacman.context.PacmanGameContext;
import org.isma.pacman.drawer.TitleDrawer;
import org.isma.pacman.entity.PacmanMenuOption;
import org.isma.pacman.resources.PacmanFontResources;
import org.isma.pacman.resources.PacmanImageResources;
import org.isma.slick2d.BasicResourcesGameState;
import org.isma.slick2d.drawer.ImageDrawer;
import org.isma.slick2d.drawer.TextDrawer;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

import static org.isma.pacman.PacmanGame.MENU_STATE;
import static org.newdawn.slick.Color.white;
import static org.newdawn.slick.Color.yellow;
import static org.newdawn.slick.Input.*;

public class PacmanMenuState extends BasicResourcesGameState<PacmanGameContext> {
    private static final String TITLE = "pacman";

    private PacmanImageResources imageResources;
    private PacmanFontResources fontResources;

    private Image background;
    private Font titleFont;
    private Font textFont;

    private ImageDrawer imageDrawer;
    private TitleDrawer titleDrawer;
    private TextDrawer textDrawer;

    @Override
    public int getID() {
        return MENU_STATE;
    }

    public PacmanMenuState(PacmanGameContext context) throws SlickException {
        super(context);
        imageResources = context.getResourcesManager().getImageResources();
        fontResources = context.getResourcesManager().getFontResources();
    }

    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        background = imageResources.getMenuBackgrond();
        titleFont = fontResources.getTitleFont();
        textFont = fontResources.getTextFont();

        imageDrawer = new ImageDrawer(getContext());
        titleDrawer = new TitleDrawer(getContext());
        textDrawer = new TextDrawer(getContext());
    }

    @Override
    protected void handleInputs(GameContainer container, StateBasedGame game) throws SlickException {
        super.handleInputs(container, game);

        Input input = container.getInput();
        if (input.isKeyPressed(KEY_ENTER)) {
            game.enterState(PacmanMenuOption.getSelectedState());
        } else if (input.isKeyPressed(KEY_UP)) {
            PacmanMenuOption.up();
        } else if (input.isKeyPressed(KEY_DOWN)) {
            PacmanMenuOption.down();
        } else if (input.isKeyPressed(KEY_ESCAPE)) {
            System.exit(0);
        }
    }


    protected void doUpdate(GameContainer gameContainer, StateBasedGame stateBasedGame, int delta) throws SlickException {
        //NOTHING
    }

    public void doRender(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        imageDrawer.draw(background, 0, 0, new Color(1f, 1f, 1f, 0.5f), null);
        titleDrawer.draw(titleFont, 50f, 50f, Color.yellow, TITLE);
        int yStart = 80;
        for (PacmanMenuOption pacmanMenuOption : PacmanMenuOption.values()) {
            Color color;
            if (pacmanMenuOption.isHighlighted()) {
                color = yellow;
            } else {
                color = white;
            }
            textDrawer.draw(textFont, 50, yStart, color, pacmanMenuOption.getLabel());
            yStart += 30;
        }
    }
}
