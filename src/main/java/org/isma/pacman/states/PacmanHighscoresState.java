package org.isma.pacman.states;

import org.isma.pacman.HighScoreHelper;
import org.isma.pacman.HighScoreInput;
import org.isma.pacman.PacmanGame;
import org.isma.pacman.PacmanGameContext;
import org.isma.slick2d.drawer.TextDrawer;
import org.isma.pacman.entity.RankedPlayer;
import org.isma.pacman.manager.HighScoreManager;
import org.isma.pacman.resources.PacmanFontResources;
import org.isma.slick2d.BasicResourcesGameState;
import org.isma.slick2d.font.ColoredText;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

import java.util.List;

import static org.isma.pacman.PacmanGame.HIGHSCORES_STATE;
import static org.newdawn.slick.Color.*;
import static org.newdawn.slick.Input.KEY_ENTER;
import static org.newdawn.slick.Input.KEY_ESCAPE;

public class PacmanHighscoresState extends BasicResourcesGameState<PacmanGameContext> {
    private static final Color DEFAULT_INPUT_COLOR = green;
    private static final Color CURRENT_INPUT_COLOR = white;

    private final HighScoreManager highScoreManager;
    private final PacmanFontResources fontResources;

    private HighScoreInput highScoreInput;

    private UnicodeFont textFont;
    private RankedPlayer currentRankedPlayer;

    private TextDrawer textDrawer;

    @Override
    public int getID() {
        return HIGHSCORES_STATE;
    }

    public PacmanHighscoresState(PacmanGameContext context) throws SlickException {
        super(context);
        highScoreManager = getContext().getHighScoreManager();
        fontResources = context.getResourcesManager().getFontResources();
    }

    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        textDrawer = new TextDrawer(getContext());
        textFont = fontResources.getTextFont();
    }

    @Override
    public void enter(GameContainer container, StateBasedGame game) throws SlickException {
        currentRankedPlayer = highScoreManager.getCurrentRankedPlayer();
        highScoreInput = new HighScoreInput(highScoreManager);
    }

    @Override
    public void leave(GameContainer container, StateBasedGame game) throws SlickException {
        highScoreManager.clearCurrentRankedPlayer();
    }


    @Override
    protected void handleInputs(GameContainer container, StateBasedGame game) throws SlickException {
        super.handleInputs(container, game);

        Input input = container.getInput();

        if (currentRankedPlayer == null) {
            if (input.isKeyPressed(KEY_ESCAPE) || input.isKeyPressed(KEY_ENTER)) {
                game.enterState(PacmanGame.MENU_STATE);
            }
            return;
        }
        highScoreInput.handleInput(container.getInput());
        if (input.isKeyPressed(KEY_ENTER)) {
            currentRankedPlayer.name = highScoreInput.getName();
            highScoreManager.save();
            currentRankedPlayer = null;
        }
    }


    protected void doUpdate(GameContainer gameContainer, StateBasedGame game, int delta) throws SlickException {
        if (currentRankedPlayer == null) {
            return;
        }
        currentRankedPlayer.name = highScoreInput.getName();
    }

    public void doRender(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        List<RankedPlayer> rankedPlayers = highScoreManager.getRankedPlayers();

        int maxNameLen = highScoreManager.getNameSize();
        int maxScoreLen = getMaxScoreLenght(rankedPlayers);

        int y = 0;

        float zoomRatio = 1f;

        textDrawer.drawCenter(textFont, y, Color.white, zoomRatio, "RANKING");
        y += 20;

        for (RankedPlayer rankedPlayer : rankedPlayers) {
            int rank = rankedPlayers.indexOf(rankedPlayer) + 1;

            String text = HighScoreHelper.toString(rankedPlayer, rank, maxNameLen, maxScoreLen);

            Color defaultColor;
            if (rank == 1) {
                defaultColor = yellow;
            } else {
                defaultColor = white;
            }
            if (rankedPlayer == currentRankedPlayer) {
                ColoredText coloredName = buildColoredName();
                ColoredText coloredText = HighScoreHelper.toColoredText(rankedPlayer, rank, maxNameLen, maxScoreLen, coloredName, DEFAULT_INPUT_COLOR);
                textDrawer.drawCenter(textFont, y, zoomRatio, coloredText);
            } else {
                textDrawer.drawCenter(textFont, y, defaultColor, zoomRatio, text);
            }
            y += 20;
        }
    }

    private ColoredText buildColoredName() {
        ColoredText coloredText = new ColoredText();
        for (int i = 0; i < highScoreInput.getCharCount(); i++) {
            int charIndex = highScoreInput.getCharIndex();
            Color color;
            if (i == charIndex) {
                color = CURRENT_INPUT_COLOR;
            } else {
                color = DEFAULT_INPUT_COLOR;
            }
            coloredText.add(highScoreInput.getCharAt(i), color);
        }
        return coloredText;
    }

    private int getMaxScoreLenght(List<RankedPlayer> rankedPlayers) {
        int maxScoreLen = 0;
        for (RankedPlayer rankedPlayer : rankedPlayers) {
            if (Long.toString(rankedPlayer.score).length() > maxScoreLen) {
                maxScoreLen = Long.toString(rankedPlayer.score).length();
            }
        }
        return maxScoreLen;
    }

}
