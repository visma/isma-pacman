package org.isma.pacman;

import org.isma.pacman.context.PacmanGameContext;
import org.isma.pacman.entity.Maze;
import org.isma.pacman.manager.HighScoreManager;
import org.isma.pacman.repository.FileHighScoreRepository;
import org.isma.pacman.resources.PacmanResourcesManager;
import org.isma.pacman.states.*;
import org.isma.slick2d.resources.GameResourcesLoader;
import org.isma.slick2d.ResourcesGame;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

import java.awt.*;

/**
 * TODO - IA : différences plus marqués entre les fantomes
 * TODO - réglages : gérer des vitesses > 1 (et non modulo 2)
 * TODO - gestion de la difficulté (attendre les réglages d'IA et de vitesse)
 * TODO - acceleration sur le clignotement des fantomes marche pas (ou mal) (faire ça avec de la trigo ?)
 * TODO - logs
 * TODO - mavenisation
 * TODO - github
 */
public class PacmanGame extends ResourcesGame<PacmanResourcesManager> implements PacmanGameContext {
    private static final Dimension RESOLUTION_320_240 = new Dimension(320, 240);
    private static final Dimension RESOLUTION_640_480 = new Dimension(640, 480);
    private static final Dimension RESOLUTION_800_600 = new Dimension(800, 600);
    private static final Dimension RESOLUTION_1024_768 = new Dimension(1024, 768);

    private static final Dimension RESOLUTION = RESOLUTION_800_600;

    //States
    public static final int DRUNKEN_PLAY_STATE = 7;
    public static final int PLAY_STATE = 1;
    public static final int MENU_STATE = 2;
    public static final int HIGHSCORES_STATE = 3;
    public static final int CREDITS_STATE = 4;
    public static final int DEMO_STATE = 5;
    public static final int ANTIPACMANPLAY_STATE = 6;

    private static final String NAMESPACE = "pacman";
    private static final String TITLE = "pacman";

    private static final boolean FULLSCREEN = false;
    protected HighScoreManager highScoreManager;


    public PacmanGame() throws Exception {
        super(TITLE, NAMESPACE);

        highScoreManager = new HighScoreManager(new FileHighScoreRepository());
    }

    @Override
    public void initStatesList(GameContainer container) throws SlickException {
        super.initStatesList(container);

        addState(new PacmanMenuState(this));
        addState(new PacmanHighscoresState(this));
        addState(new PacmanPlayState(this));
        addState(new AntiPacmanPlayState(this));
        addState(new PacmanDemoState(this));
        addState(new PacmanCreditsState(this));
        addState(new PacmanDrunkenPlayState(this));
        enterState(MENU_STATE);
    }

    public float getGameScreenXPosition(float zoomRatio) {
        return (RESOLUTION.width - (Maze.MAZE_WIDTH * zoomRatio)) / 2;
    }

    public float getGameScreenYPosition(float zoomRatio) {
        return (RESOLUTION.height - (Maze.MAZE_HEIGHT * zoomRatio)) / 2;
    }

    public Dimension getGameScreenSize(float zoomRatio) {
        float width = Maze.MAZE_WIDTH * zoomRatio;
        float height = Maze.MAZE_HEIGHT * zoomRatio;
        return new Dimension((int) width, (int) height);
    }

    public Dimension getScreenSize() {
        return RESOLUTION;
    }


    @Override
    protected PacmanResourcesManager buildResourcesHandler(GameResourcesLoader resourcesManager) {
        return new PacmanResourcesManager(resourcesManager);
    }

    public PacmanResourcesManager getResourcesManager() {
        return resourcesHanlder;
    }

    public boolean isDebugMode() {
        return getResourcesManager().isDebugMode();
    }

    public boolean enableParticles() {
        return getResourcesManager().enableParticles();
    }

    public HighScoreManager getHighScoreManager() {
        return highScoreManager;
    }

    public float getZoomRatio() {
        return getResourcesManager().getConfiguration().getZoomRatio();
    }

    public static void main(String[] argv) throws Exception {
        new NativeLoader().load();
        PacmanGame game = new PacmanGame();
        AppGameContainer appContainer = new AppGameContainer(game,
                RESOLUTION.width,
                RESOLUTION.height, FULLSCREEN);
        appContainer.start();
    }


}