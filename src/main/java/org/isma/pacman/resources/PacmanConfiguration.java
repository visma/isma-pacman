package org.isma.pacman.resources;

import org.isma.slick2d.resources.GameResourcesLoader;
import org.isma.slick2d.resources.Configuration;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

public class PacmanConfiguration implements Configuration {
    private GameResourcesLoader resourcesManager;
    private int energizerDuration;
    private int minBlinkFrequency;
    private int maxBlinkFrequency;
    private int lives;

    public void loadConfiguration() {
        System.out.println("loading configuration...");
        energizerDuration = parseInt(resourcesManager.get("energizer.duration"));
        minBlinkFrequency = parseInt(resourcesManager.get("blink.frequency.max"));
        maxBlinkFrequency = parseInt(resourcesManager.get("blink.frequency.min"));
        lives = parseInt(resourcesManager.get("pacman.lives"));

    }

    public PacmanConfiguration(GameResourcesLoader resourcesManager) {
        this.resourcesManager = resourcesManager;
    }

    public int getEnergizerDuration() {
        return energizerDuration;
    }


    public int getMaxBlinkFrequency() {
        return maxBlinkFrequency;
    }

    public int getMinBlinkFrequency() {
        return minBlinkFrequency;
    }

    public int getLives() {
        return lives;
    }


    public float getSpeed(String name) {
        return parseFloat(resourcesManager.get(name + ".speed"));
    }

    public int getDemoTargetFrameRate() {
        return parseInt(resourcesManager.get("demo.targetedFps"));
    }

    public int getTargetFrameRate() {
        return parseInt(resourcesManager.get("game.targetedFps"));
    }

    public float getZoomRatio() {
        return parseFloat(resourcesManager.get("display.zoom.ratio"));
    }
}
