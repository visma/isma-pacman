package org.isma.pacman;


import org.isma.pacman.manager.HighScoreManager;
import org.newdawn.slick.Input;

public class HighScoreInput {
    private static final char DEFAULT_CHAR = 'A';

    private final HighScoreManager manager;

    private Character[] name;
    private int charIndex;

    public HighScoreInput(HighScoreManager manager) {
        this.manager = manager;
        int length = manager.getNameSize();

        name = new Character[length];
        name[0] = DEFAULT_CHAR;
        for (int i = 1; i < name.length; i++) {
            name[i] = null;
        }
        charIndex = 0;
    }

    public void handleInput(Input input) {
        if (input.isKeyPressed(Input.KEY_LEFT)) {
            charIndex += charIndex == 0 ? 0 : -1;
        } else if (input.isKeyPressed(Input.KEY_RIGHT)) {
            charIndex += charIndex == name.length - 1 ? 0 : 1;
        } else if (input.isKeyPressed(Input.KEY_UP)) {
            name[charIndex] = manager.next(name[charIndex]);
        } else if (input.isKeyPressed(Input.KEY_DOWN)) {
            name[charIndex] = manager.previous(name[charIndex]);
        }
        if (name[charIndex] == null){
            name[charIndex] = DEFAULT_CHAR;
        }
    }

    public int getCharIndex() {
        return charIndex;
    }

    public char getCharAt(int index) {
        return name[index] == null ? ' ' : name[index];
    }

    public int getCharCount() {
        return manager.getNameSize();
    }

    public String getName() {
        String str = "";
        for (int i = 0; i < getCharCount();i++){
            str += getCharAt(i);
        }
        return str;
    }
}
