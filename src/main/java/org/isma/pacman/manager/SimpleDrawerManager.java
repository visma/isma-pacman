package org.isma.pacman.manager;

import org.isma.pacman.PacmanGameContext;
import org.isma.pacman.drawer.GhostDrawer;
import org.isma.pacman.drawer.MazeDrawer;
import org.isma.pacman.drawer.PacmanDrawer;
import org.isma.pacman.entity.Ghost;
import org.isma.slick2d.drawer.RenderableDrawer;
import org.isma.slick2d.drawer.TextDrawer;
import org.newdawn.slick.SlickException;

import java.util.List;

public class SimpleDrawerManager {
    private final PacmanGameContext context;

    private final TextDrawer textDrawer;
    private final RenderableDrawer renderableDrawer;
    private final MazeDrawer mazeDrawer;
    private final RenderableDrawer ghostDrawer;

    public SimpleDrawerManager(PacmanGameContext context) throws SlickException {
        this.context = context;
        textDrawer = new TextDrawer(context);
        renderableDrawer = new RenderableDrawer(context);
        mazeDrawer = new MazeDrawer(context);
        ghostDrawer = new GhostDrawer(context);
    }

    public TextDrawer getTextDrawer() {
        return textDrawer;
    }

    public RenderableDrawer getRenderableDrawer() {
        return renderableDrawer;
    }

    public PacmanDrawer getPacmanDrawer(List<Ghost> ghosts) throws SlickException {
        return new PacmanDrawer(context, ghosts);
    }

    public MazeDrawer getMazeDrawer() {
        return mazeDrawer;
    }

    public RenderableDrawer getGhostDrawer() {
        return ghostDrawer;
    }

}
