package org.isma.pacman.entity;

public class Round {
    public int index;
    public boolean starting;
    public int ghostEatedCount;
    public int dotEatedCount;
    public boolean showFruit;
    public boolean showPointsFruit;
    public boolean alreadyShowedFruit;

    public Round() {
        ghostEatedCount = 0;
        dotEatedCount = 0;
        index = 1;
        starting = true;
        showFruit = false;
        showPointsFruit = false;
        alreadyShowedFruit = false;
    }

    public void reset() {
        ghostEatedCount = 0;
        starting = true;
    }

    public void nextRound() {
        reset();
        index++;
        dotEatedCount = 0;
        showFruit = false;
        alreadyShowedFruit = false;
        showPointsFruit = false;
    }

    @Override
    public String toString() {
        return "Round{" +
                "index=" + index +
                ", starting=" + starting +
                ", ghostEatedCount=" + ghostEatedCount +
                ", dotEatedCount=" + dotEatedCount +
                ", showFruit=" + showFruit +
                ", showPointsFruit=" + showPointsFruit +
                ", alreadyShowedFruit=" + alreadyShowedFruit +
                '}';
    }
}
