package org.isma.pacman.manager;

import org.isma.pacman.entity.RankedPlayer;
import org.isma.pacman.repository.HighScoreRepository;

import java.util.ArrayList;
import java.util.List;

public class HighScoreManager {
    private final HighScoreRepository repository;
    private RankedPlayer currentRankedPlayer;

    private static final int NAME_SIZE = 10;
    private final List<Character> chars;

    public HighScoreManager(HighScoreRepository repository) {
        this.repository = repository;
        chars = buildChars();

    }

    private List<Character> buildChars() {
        List<Character> list = new ArrayList<Character>();

        for (char c = 'A'; c <= 'Z'; c++) {
            list.add(c);
        }
        for (char c = '0'; c <= '9'; c++) {
            list.add(c);
        }
        list.add('_');
        list.add('.');
        list.add(' ');
        return list;
    }

    public Character next(Character current) {
        int index = chars.indexOf(current);
        if (index == chars.size() - 1) {
            return chars.get(0);
        }
        return chars.get(index + 1);
    }

    public Character previous(Character current) {
        int index = chars.indexOf(current);
        if (index == 0) {
            return chars.get(chars.size() - 1);
        }
        return chars.get(index - 1);
    }

    public boolean isRanked(long scored) {
        return repository.isRanked(scored);
    }

    public void push(long scored) {
        currentRankedPlayer = repository.push(scored);
    }

    public RankedPlayer getCurrentRankedPlayer() {
        return currentRankedPlayer;
    }

    public void clearCurrentRankedPlayer() {
        currentRankedPlayer = null;
    }

    public List<RankedPlayer> getRankedPlayers() {
        return repository.getRankedPlayers();
    }

    public int getNameSize() {
        return NAME_SIZE;
    }

    public void save() {
        try {
            repository.save();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
