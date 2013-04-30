package org.isma.pacman;

import org.isma.pacman.entity.RankedPlayer;
import org.isma.slick2d.font.ColoredText;
import org.newdawn.slick.Color;

public class HighScoreHelper {
    private HighScoreHelper() {
    }

    public static String toString(RankedPlayer rankedPlayer, int rank, int maxNameLen, int maxScoreLen) {
        String name = rankedPlayer.name;
        long score = rankedPlayer.score;

        final String minimalSpace = "  ";
        String nameSpace = "";
        String scoreSpace = "";

        for (int i = 0; i < maxNameLen - name.length(); i++) {
            nameSpace += " ";
        }
        for (int i = 0; i < maxScoreLen - Long.toString(score).length(); i++) {
            scoreSpace += " ";
        }
        return String.format("%s. %s%s%s%s%s pts", rank, name, nameSpace, minimalSpace, scoreSpace, score);
    }

    public static ColoredText toColoredText(RankedPlayer rankedPlayer, int rank, int maxNameLen, int maxScoreLen, ColoredText coloredName, Color defautColor) {
        ColoredText coloredText = new ColoredText();

        String name = rankedPlayer.name;
        long score = rankedPlayer.score;

        final String minimalSpace = "  ";
        String nameSpace = "";
        String scoreSpace = "";

        for (int i = 0; i < maxNameLen - name.length(); i++) {
            nameSpace += " ";
        }
        for (int i = 0; i < maxScoreLen - Long.toString(score).length(); i++) {
            scoreSpace += " ";
        }
        //=> String.format("%s. %s%s%s%s%s pts", rank, name, nameSpace, minimalSpace, scoreSpace, score);
        coloredText.concat(rank, defautColor);
        coloredText.concat(". ", defautColor);
        coloredText.concat(coloredName);
        coloredText.concat(nameSpace, defautColor);
        coloredText.concat(minimalSpace, defautColor);
        coloredText.concat(scoreSpace, defautColor);
        coloredText.concat(score, defautColor);
        coloredText.concat(" pts", defautColor);
        return coloredText;
    }
}
