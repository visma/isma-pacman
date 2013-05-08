package org.isma.pacman.handlers;

import org.isma.pacman.entity.Character;
import org.isma.pacman.entity.Maze;

public interface MoveHandler<C extends Character> {
    void handleCharacterMove(C character, Maze maze);
}
