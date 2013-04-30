package org.isma.pacman;

import org.isma.pacman.manager.HighScoreManager;
import org.isma.pacman.resources.PacmanResourcesManager;
import org.isma.slick2d.GameContext;

public interface PacmanGameContext extends GameContext<PacmanResourcesManager> {

    boolean enableParticles();

    HighScoreManager getHighScoreManager();

}
