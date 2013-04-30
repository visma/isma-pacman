package org.isma.pacman.repository;

import org.isma.pacman.entity.RankedPlayer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.util.Collections.sort;

public abstract class HighScoreRepository {
    private static final int CAPACITY = 10;

    private List<RankedPlayer> rankedPlayers = new ArrayList<RankedPlayer>();


    protected HighScoreRepository() throws Exception {
        load();
        while (CAPACITY > rankedPlayers.size()) {
            String emptyName = "";
            while (emptyName.length() < CAPACITY) {
                emptyName += "-";
            }
            rankedPlayers.add(new RankedPlayer(emptyName, 0));
        }
    }

    protected abstract void load() throws Exception;

    class BigScoreComparator implements Comparator<RankedPlayer> {

        public int compare(RankedPlayer a, RankedPlayer b) {
            long diff = a.score - b.score;
            return diff == 0 ? 0 : (diff > 0 ? -1 : 1);
        }
    }

    public List<RankedPlayer> getRankedPlayers() {
        sort(rankedPlayers, new BigScoreComparator());
        return rankedPlayers;
    }

    public boolean isRanked(long score) {
        if (score == 0) {
            return false;
        }
        if (rankedPlayers.size() < CAPACITY) {
            return true;
        }
        List<RankedPlayer> sorted = getRankedPlayers();
        return sorted.get(sorted.size() - 1).score < score;
    }

    public RankedPlayer push(long score) {
        if (!isRanked(score)) {
            throw new RuntimeException("!!");
        }
        if (rankedPlayers.size() == CAPACITY) {
            rankedPlayers.remove(CAPACITY - 1);
        }
        RankedPlayer rankedPlayer = new RankedPlayer("AAA", score);
        rankedPlayers.add(rankedPlayer);
        sort(rankedPlayers, new BigScoreComparator());
        return rankedPlayer;
    }

    public abstract void save() throws Exception;

}
