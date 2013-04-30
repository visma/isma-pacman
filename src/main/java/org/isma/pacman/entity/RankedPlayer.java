package org.isma.pacman.entity;

public class RankedPlayer {
    public String name;
    public long score;

    public RankedPlayer(String name, long score) {
        this.name = name;
        this.score = score;
    }

//    public String toString(int rank, int maxNameLen, int maxScoreLen) {
//        final String minimalSpace = "  ";
//        String nameSpace = "";
//        String scoreSpace = "";
//
//        for (int i = 0; i < maxNameLen - name.length(); i++) {
//            nameSpace += " ";
//        }
//        for (int i = 0; i < maxScoreLen - Long.toString(score).length(); i++) {
//            scoreSpace += " ";
//        }
//        return String.format("%s. %s%s%s%s%s pts", rank, name, nameSpace, minimalSpace, scoreSpace, score);
//    }


}
