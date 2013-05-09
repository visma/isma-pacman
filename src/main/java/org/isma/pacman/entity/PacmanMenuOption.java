package org.isma.pacman.entity;

import org.isma.pacman.PacmanGame;

public enum PacmanMenuOption {
    PLAY("play", PacmanGame.PLAY_STATE),
    ANTIPACMAN("play antipacman", PacmanGame.ANTIPACMANPLAY_STATE),
    DEMO("demo", PacmanGame.DEMO_STATE),
    HIGHSCORES("highscores", PacmanGame.HIGHSCORES_STATE),
    CREDITS("credits", PacmanGame.CREDITS_STATE),
    DRUNKEN_MODE("drunken mode", PacmanGame.DRUNKEN_PLAY_STATE);

    private static int highlightedIndex;
    private String label;
    private int gameState;

    private PacmanMenuOption(String label, int gameState) {
        this.label = label;
        this.gameState = gameState;
    }


    public boolean isHighlighted() {
        return ordinal() == highlightedIndex;
    }

    public String getLabel() {
        return label;
    }


    public static void up() {
        highlightedIndex--;
        if (-1 == highlightedIndex) {
            highlightedIndex = PacmanMenuOption.values().length - 1;
        }
    }

    public static void down() {
        highlightedIndex++;
        if (PacmanMenuOption.values().length == highlightedIndex) {
            highlightedIndex = 0;
        }
    }

    public static int getSelectedState() {
        return values()[highlightedIndex].gameState;
    }
    }
