package org.isma.pacman.manager;

import org.isma.pacman.entity.Character;
import org.isma.pacman.entity.Maze;
import org.isma.pacman.handlers.MoveHandler;

import java.util.HashMap;
import java.util.Map;

public class GameMovesManager {
    private final Map<Character, MoveHandler<Character>> moveHandlers = new HashMap<Character, MoveHandler<Character>>();


    public void put(Character character, MoveHandler moveHandler) {
        moveHandlers.put(character, moveHandler);
    }

    public void moveEverybody(Maze maze) {
        for (Character character : moveHandlers.keySet()) {
            MoveHandler<Character> characterMoveHandler = moveHandlers.get(character);
            characterMoveHandler.handleCharacterMove(character, maze);
        }
    }

    public void resetAllAI() {
        for (MoveHandler<Character> moveHandler : moveHandlers.values()) {
            moveHandler.reset();
        }
    }
}
