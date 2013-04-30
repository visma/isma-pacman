package org.isma.pacman.rules;

import static java.lang.Math.pow;

public class PacmanRules {
    private static final int EXTRALIFE_POINTS_TO_REACH = 10000;

    private PacmanRules() {
    }

    public static long getGhostEatedPoints(int ghostEatedCount){
        return (long) (pow(2, (ghostEatedCount + 1)) * 100);
    }

    public static boolean winAnExtraLife(long scoreBefore, long scoreAfter) {
        return scoreBefore < EXTRALIFE_POINTS_TO_REACH && scoreAfter >= EXTRALIFE_POINTS_TO_REACH;
    }

    public static boolean showFruit(int dotEatedCount) {
        return showFirstFruit(dotEatedCount) || showSecondFruit(dotEatedCount);
    }

    private static boolean showFirstFruit(int dotsEated) {
        return dotsEated == 70;
    }

    private static boolean showSecondFruit(int dotsEated) {
        return dotsEated == 170;
    }


}
