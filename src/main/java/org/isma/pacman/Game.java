package org.isma.pacman;


import static org.isma.pacman.rules.PacmanRules.winAnExtraLife;

public class Game {
    private long score;
    public boolean pause;
    public boolean over;
    public boolean starting;
    public boolean playingIntro;

    private final ScoreListener scoreListener;

    public boolean extraLife;

    public Game(ScoreListener scoreListener) {
        score = 0;
        pause = false;
        over = false;
        starting = true;
        playingIntro = false;

        this.scoreListener = scoreListener;
    }

    public void scorePoints(long points) {
        long scoreBefore = score;
        score += points;
        if (winAnExtraLife(scoreBefore, score)) {
            extraLife = true;
        }
        scoreListener.scoreEvent();
    }

    public boolean consumeExtraLife() {
        if (!extraLife) {
            return false;
        }
        extraLife = false;
        return true;
    }

    @Override
    public String toString() {
        return "Game{" +
                "score=" + score +
                ", pause=" + pause +
                ", over=" + over +
                ", starting=" + starting +
                ", playingIntro=" + playingIntro +
                '}';
    }

    public long getScore() {
        return score;
    }
}
