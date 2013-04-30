package org.isma.pacman.handlers;

import org.isma.slick2d.Direction;
import org.isma.pacman.entity.Maze;
import org.isma.pacman.entity.Character;
import org.newdawn.slick.Input;

import java.util.Arrays;
import java.util.List;

import static org.isma.slick2d.Direction.*;
import static org.isma.slick2d.PositionHelper.convertInputToDirection;
import static org.newdawn.slick.Input.*;

public class CharacterInputMoveHandler<C extends Character> implements MoveHandler<C> {
    private static final List<Integer> AUTHORIZED_KEYS = Arrays.asList(KEY_DOWN, KEY_UP, KEY_RIGHT, KEY_LEFT);
    private final Input input;

    public CharacterInputMoveHandler(Input input) {
        this.input = input;
    }

    public void handleCharacterMove(Character pacman, Maze maze) {
        updateKeyPressed(pacman, input);
        updateCharacterPosition(pacman, maze);
    }

    private void updateKeyPressed(Character pacman, Input input) {
        for (Integer authorizedKey : AUTHORIZED_KEYS) {
            if (input.isKeyPressed(authorizedKey)) {
                pacman.setLastInput(convertInputToDirection(authorizedKey));
                return;
            }
        }
    }

    private void updateCharacterPosition(Character character, Maze level) {
        if (!updateCharacterPosition(character.getLastInput(), character, level)) {
            if (!updateCharacterPosition(character.getCurrentDirection(), character, level)){
                character.setStopped(true);
                return;
            }
        }
        character.setStopped(false);
    }

    private boolean updateCharacterPosition(Direction direction, Character character, Maze maze) {
        if (direction != null) {
            if ((direction == WEST) && maze.canMoveLeft(character)) {
                character.move(direction, maze);
                return true;
            } else if (direction == EAST && maze.canMoveRight(character)) {
                character.move(direction, maze);
                return true;
            } else if (direction == NORTH && maze.canMoveTop(character)) {
                character.move(direction, maze);
                return true;
            } else if (direction == SOUTH && maze.canMoveBottom(character)) {
                character.move(direction, maze);
                return true;
            }
        }
        return false;
    }


}
