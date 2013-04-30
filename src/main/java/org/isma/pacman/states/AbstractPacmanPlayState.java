package org.isma.pacman.states;

import org.isma.pacman.*;
import org.isma.pacman.entity.*;
import org.isma.pacman.manager.SimpleDrawerManager;
import org.isma.pacman.resources.PacmanSoundResources;
import org.isma.slick2d.BasicResourcesGameState;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static org.isma.pacman.entity.Fruit.FruitEnum;
import static org.isma.pacman.entity.PacmanEntitiesFactory.*;
import static org.isma.pacman.rules.PacmanRules.getGhostEatedPoints;
import static org.isma.pacman.rules.PacmanRules.showFruit;
import static org.newdawn.slick.Input.KEY_ENTER;
import static org.newdawn.slick.Input.KEY_ESCAPE;

public abstract class AbstractPacmanPlayState extends BasicResourcesGameState<PacmanGameContext> implements ScoreListener {

    private static final long BIG_FREEZE = 5000l;
    private static final long SMALL_FREEZE = 2000l;
    private static final long FRUIT_TIME_DURATION = 10000l;
    private static final long FRUIT_POINTS_DISPLAY_DURATION = 3000l;

    private final SimpleDrawerManager drawerManager;

    //Resources
    private final PacmanSoundResources soundResources;

    //Entities
    private GameBoard gameBoard;
    protected Maze maze;
    protected Pacman pacman;
    protected final List<Ghost> ghosts = new ArrayList<Ghost>();
    private Fruit fruit;

    //GameState
    private Round round;
    protected Game game;

    private boolean freezeGameEvent;

    private final Timer timer;


    public AbstractPacmanPlayState(PacmanGameContext context) throws SlickException {
        super(context);
        timer = new Timer();
        soundResources = context.getResourcesManager().getSoundResources();
        drawerManager = new SimpleDrawerManager(context);
    }


    protected abstract void configurePacmanMoveHandlerAndAI(GameContainer container);

    protected abstract void configureGhostsMoveHandlerAndAI(GameContainer container);


    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        container.setTargetFrameRate(getContext().getResourcesManager().getConfiguration().getTargetFrameRate());
    }

    @Override
    public void leave(GameContainer container, StateBasedGame game) throws SlickException {
        soundResources.stopMusic();
    }

    protected void quit(GameContainer container, StateBasedGame game) {
        game.enterState(PacmanGame.MENU_STATE);
    }

    @Override
    public void enter(GameContainer container, StateBasedGame stateBasedGame) throws SlickException {
        //State
        game = new Game(this);
        round = new Round();

        //Entities
        gameBoard = new GameBoard(getContext());
        maze = new Maze(getContext());
        pacman = buildPacman(getContext());

        ghosts.clear();
        ghosts.add(buildBlinky(getContext()));
        ghosts.add(buildInky(getContext()));
        ghosts.add(buildPinky(getContext()));
        ghosts.add(buildClyde(getContext()));

        fruit = buildFruit(getContext(), maze.getFruitSpawnPoint(), FruitEnum.values()[0]);

        //Handlers
        configurePacmanMoveHandlerAndAI(container);
        configureGhostsMoveHandlerAndAI(container);

        //Drawers
        maze.setRenderableDrawer(drawerManager.getRenderableDrawer());
        maze.setTiledMapDrawer(drawerManager.getMazeDrawer());

        gameBoard.setRenderableDrawer(drawerManager.getRenderableDrawer());
        gameBoard.setTextDrawer(drawerManager.getTextDrawer());

        pacman.setDefaultDrawer(drawerManager.getPacmanDrawer(ghosts));
        for (Ghost ghost : ghosts) {
            ghost.setDefaultDrawer(drawerManager.getGhostDrawer());
        }
        fruit.setDefaultDrawer(drawerManager.getRenderableDrawer());
    }


    public void doUpdate(GameContainer container, StateBasedGame stateBasedGame, int delta) throws SlickException {
        long start = System.currentTimeMillis();

        handleFruitSpawn();

        handleMoves();

        handlePacmanDotVoracity();

        handleMusic();

        int livesBefore = pacman.getLives();
        handleCollisions();

        handleGameStatus(container, stateBasedGame, livesBefore, pacman.getLives());

        long end = System.currentTimeMillis();
        long duration = end - start;
        if (duration > 8) {
            System.out.println("update cost :" + duration);
        }
    }

    protected boolean isGameUpdateSuspended() {
        if (game.over || game.pause || pacman.isDead() || freezeGameEvent) {
            return true;
        }
        if (game.starting) {
            startGame();
            return true;
        } else if (round.starting) {
            startRound();
            return true;
        }
        return false;
    }

    private void handleGameStatus(GameContainer container, StateBasedGame stateBasedGame, int livesBefore, int livesAfter) throws SlickException {
        if (pacman.getLives() == 0) {
            endGame(container, stateBasedGame);
        } else if (livesAfter < livesBefore) {
            retryRound();
        } else if (!maze.hasRemainingFood()) {
            nextRound();
        }
    }

    protected void handleInputs(GameContainer container, StateBasedGame stateBasedGame) throws SlickException {
        super.handleInputs(container, stateBasedGame);

        Input input = container.getInput();
        if (input.isKeyPressed(KEY_ENTER)) {
            game.pause = !game.pause;
        } else if (input.isKeyPressed(KEY_ESCAPE)) {
            stateBasedGame.enterState(PacmanGame.MENU_STATE);
        }
    }


    private void handleFruitSpawn() {
        //Condition permettant de vérifier quand on a mangé pile le nombre de dots qu'on n'affiche pas des fruits en boucle
        if (showFruit(round.dotEatedCount) && !round.showFruit && !round.alreadyShowedFruit) {
            round.showFruit = true;
            round.alreadyShowedFruit = true;
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    round.showFruit = false;
                    round.alreadyShowedFruit = false;
                }
            }, FRUIT_TIME_DURATION);
        }
    }

    private void handleMusic() {
        for (Ghost ghost : ghosts) {
            if (ghost.isNaked()) {
                soundResources.playMusicNaked();
                return;
            }
        }
        for (Ghost ghost : ghosts) {
            if (ghost.isFrightened()) {
                soundResources.playMusicFrightened();
                return;
            }
        }
        soundResources.playMusicSiren();
    }

    private void startGame() {
        //On lance la musique d'intro et on attend un peu
        if (!game.playingIntro) {
            soundResources.playMusicIntro();
            game.playingIntro = true;
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    game.starting = false;
                    game.playingIntro = false;
                }
            }, BIG_FREEZE - SMALL_FREEZE);
        }
    }

    private void startRound() {
        //On affiche le n° du round et on attend un peu...
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                round.starting = false;
            }
        }, SMALL_FREEZE);
    }

    private void endGame(final GameContainer gameContainer, final StateBasedGame stateBasedGame) {
        //On affiche le game over et on attend un peu...
        soundResources.stopMusic();
        game.over = true;

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                quit(gameContainer, stateBasedGame);
            }
        }, BIG_FREEZE);
    }


    private void nextRound() throws SlickException {
        maze.loadFood();
        round.nextRound();
        resetRound();
    }

    private void retryRound() {
        soundResources.stopMusic();
        soundResources.death();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                pacman.revive();
                resetRound();
            }
        }, SMALL_FREEZE);
    }

    private void resetRound() {
        soundResources.stopMusic();
        pacman.resetOriginalState();
        for (Ghost ghost : ghosts) {
            ghost.resetOriginalState();
        }
        round.reset();
    }

    private void handleCollisions() {
        handleFruitCollision();
        handleGhostsCollisions();
    }

    private void handleGhostsCollisions() {
        for (Ghost ghost : ghosts) {
            if (ghost.hit(pacman)) {
                if (ghost.isFrightened()) {
                    handleGhostEated(ghost);
                    freezeGameEvent = true;
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            freezeGameEvent = false;
                        }
                    }, soundResources.ghostEated());
                } else if (!ghost.isNaked()) {
                    pacman.lose();
                    return;
                }
            }
            if (ghost.isFrightened()) {
                ghost.lowerFear();
                ghost.updateBlinkStatus();
            }
            if (ghost.isNaked() && ghost.hit(maze.getGhostHouseLocation())) {
                ghost.resurrect();
            }
        }
    }

    private void handleGhostEated(Ghost ghost) {
        ghost.lose();
        gameBoard.addGhostEatedEvent(ghost, round.ghostEatedCount, SMALL_FREEZE);
        game.scorePoints(getGhostEatedPoints(round.ghostEatedCount));
        round.ghostEatedCount++;
        if (round.ghostEatedCount >= ghosts.size()) {
            round.ghostEatedCount = 0;
        }
    }

    public void scoreEvent() {
        if (game.consumeExtraLife()) {
            pacman.addExtraLife();
            soundResources.extraLife();
        }
    }

    private void handlePacmanDotVoracity() {
        Food eated = maze.feed(pacman);
        if (eated != null) {
            game.scorePoints(eated.getValue());
            if (eated instanceof Energizer) {
                for (Ghost ghost : ghosts) {
                    ghost.frightened();
                }
            } else {
                round.dotEatedCount++;
            }
            soundResources.dot();
        }
        boolean isFearOver = true;
        for (Ghost ghost : ghosts) {
            if (ghost.isFrightened()) {
                isFearOver = false;
                break;
            }
        }
        if (isFearOver) {
            round.ghostEatedCount = 0;
        }
    }

    private void handleFruitCollision() {
        if (round.showFruit && pacman.getCenter().intersects(maze.getFruitSpawnPoint())) {
            round.showFruit = false;
            round.showPointsFruit = true;
            game.scorePoints(fruit.getValue());
            soundResources.fruitEated();
            fruit.nextFruit();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    round.showPointsFruit = false;
                }
            }, FRUIT_POINTS_DISPLAY_DURATION);
        }
    }

    private void handleMoves() {
        pacman.move(maze);
        for (Ghost ghost : ghosts) {
            ghost.move(maze);
        }
    }


    public void doRender(GameContainer container, StateBasedGame stateBasedGame, Graphics g) throws SlickException {
        maze.draw(g);
        gameBoard.draw(pacman.getLives(), game, round, fruit);

        if (pacman.isDead() || !game.over) {
            pacman.draw();
        }
        if (!pacman.isDead() && !game.over) {
            for (Ghost ghost : ghosts) {
                ghost.draw();
            }
        }
        if (round.showFruit) {
            fruit.draw();
        }
    }

    @Override
    protected boolean enableZoom() {
        return true;
    }
}
