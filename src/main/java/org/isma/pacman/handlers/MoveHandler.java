package org.isma.pacman.handlers;

import org.isma.pacman.entity.Maze;

import org.isma.pacman.entity.Character;

public interface MoveHandler<C extends Character> {
    public void handleCharacterMove(C character, Maze maze);
}
