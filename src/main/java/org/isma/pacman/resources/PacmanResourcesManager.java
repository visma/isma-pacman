package org.isma.pacman.resources;

import org.isma.slick2d.resources.GameResourcesLoader;
import org.isma.slick2d.resources.ResourcesManager;

import static java.lang.Boolean.parseBoolean;

public class PacmanResourcesManager implements ResourcesManager<PacmanImageResources, PacmanSoundResources, PacmanFontResources, PacmanConfiguration> {

    private final GameResourcesLoader resourcesLoader;

    private final PacmanImageResources imageResources;
    private final PacmanSoundResources soundResources;
    private final PacmanFontResources fontResources;
    private final PacmanConfiguration configuration;

    public PacmanResourcesManager(GameResourcesLoader resourcesLoader) {
        this.resourcesLoader = resourcesLoader;
        configuration = new PacmanConfiguration(resourcesLoader);
        imageResources = new PacmanImageResources(resourcesLoader);
        soundResources = new PacmanSoundResources(resourcesLoader);
        fontResources = new PacmanFontResources(resourcesLoader);

    }

    public PacmanImageResources getImageResources() {
        return imageResources;
    }

    public PacmanSoundResources getSoundResources() {
        return soundResources;
    }

    public PacmanFontResources getFontResources() {
        return fontResources;
    }

    public PacmanConfiguration getConfiguration() {
        return configuration;
    }

    public boolean isDebugMode() {
        return resourcesLoader.isDebugMode();
    }

    public boolean enableParticles() {
        return parseBoolean(resourcesLoader.get("particles.enable"));
    }
}
