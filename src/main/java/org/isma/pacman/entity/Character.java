package org.isma.pacman.entity;

import org.isma.pacman.Collisionable;
import org.isma.pacman.PacmanGameContext;
import org.isma.pacman.ai.CharacterAI;
import org.isma.pacman.handlers.MoveHandler;
import org.isma.slick2d.BitmapObject;
import org.isma.slick2d.Direction;
import org.isma.slick2d.PositionHelper;
import org.newdawn.slick.Animation;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Rectangle;

import static org.isma.slick2d.Direction.*;

public abstract class Character<C extends Character, A extends CharacterAI>
        extends BitmapObject<Animation, PacmanGameContext> implements Collisionable {
    private final String name;

    public static final int ANIMATION_RATE = 80;

    //Dimensions
    private static final int WIDTH = 16;
    private static final int HEIGHT = 16;
    private static final int WIDTH_CENTER = WIDTH / 2;
    private static final int HEIGHT_CENTER = HEIGHT / 2;

    //
    private float speed;
    protected Direction currentDirection;
    private Direction lastInput;
    protected boolean stopped = true;

    private final float xOriginalPosition;
    private final float yOriginalPosition;

    //
    private MoveHandler<C> moveHandler;
    private A ai;

    public Character(PacmanGameContext context, String name, float x, float y) {
        super(context, x, y);
        this.name = name;
        this.xOriginalPosition = x;
        this.yOriginalPosition = y;
    }


    protected float getSpeed() {
        return speed;
    }

    public void move(Direction direction, Maze level) {
        if (direction == Direction.WEST) {
            x -= getSpeed();
        } else if (direction == EAST) {
            x += getSpeed();
        } else if (direction == NORTH) {
            y -= getSpeed();
        } else if (direction == SOUTH) {
            y += getSpeed();
        } else {
            throw new RuntimeException("unexpected input move");
        }
        Rectangle center = getCenter();
        if (PositionHelper.isOutOfBounds(level.getBounds(), center)) {
            Direction boundOut = PositionHelper.getBoundOut(level.getBounds(), center);
            if (boundOut == EAST) {
                x = level.getBounds().getMinX();
            }
            if (boundOut == Direction.WEST) {
                x = level.getBounds().getMaxX() - getWidth();
            }
            if (boundOut == Direction.NORTH) {
                y = level.getBounds().getMaxY() - getHeight();
            }
            if (boundOut == Direction.SOUTH) {
                y = level.getBounds().getMinY();
            }
        }
        setCurrentDirection(direction);
    }


    //------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------


    /**
     * TODO gérer les cas ou la vitesse n'est pas de '1'
     */
    public Line getRightLine() {
        Rectangle rectangle = getCenter();
        Line line = new Line(
                rectangle.getMaxX() + 1,
                rectangle.getMinY(),
                rectangle.getMaxX() + 1,
                rectangle.getMaxY()
        );
        return line;
    }

    public Line getLeftLine() {
        Rectangle rectangle = getCenter();
        Line line = new Line(
                rectangle.getMinX() - 1,
                rectangle.getMinY(),
                rectangle.getMinX() - 1,
                rectangle.getMaxY()
        );
        return line;
    }

    public Line getTopLine() {
        Rectangle rectangle = getCenter();
        Line line = new Line(
                rectangle.getMinX(),
                rectangle.getMinY() - 1,
                rectangle.getMaxX(),
                rectangle.getMinY() - 1
        );
        return line;
    }

    public Line getBottomLine() {
        Rectangle rectangle = getCenter();
        Line line = new Line(
                rectangle.getMinX(),
                rectangle.getMaxY() + 1,
                rectangle.getMaxX(),
                rectangle.getMaxY() + 1
        );
        return line;
    }

    public abstract int getMoveLevel();

    public void move(Maze maze) {
        getMoveHandler().handleCharacterMove((C) this, maze);
    }


    //------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------
    //------------------------------------------------------------------------------------

    protected Object getRenderableKey() {
        return currentDirection;
    }

    protected Object getDefaultRenderableKey() {
        return EAST;
    }


    @Override
    public int getWidth() {
        return WIDTH;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }

    protected int getWidthCenter() {
        return WIDTH_CENTER;
    }

    protected int getHeightCenter() {
        return HEIGHT_CENTER;
    }

    public Rectangle getCenter() {
        int xCenter = (int) (x + ((getWidth() - getWidthCenter()) / 2));
        int yCenter = (int) (y + ((getHeight() - getHeightCenter()) / 2));
        return new Rectangle(xCenter, yCenter, getWidthCenter(), getHeightCenter());
    }

    public void setCurrentDirection(Direction currentDirection) {
        this.currentDirection = currentDirection;
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }

    public MoveHandler<C> getMoveHandler() {
        return moveHandler;
    }

    public void setMoveHandler(MoveHandler<C> moveHandler) {
        this.moveHandler = moveHandler;
    }

    public abstract void lose();

    public void resetOriginalState() {
        x = xOriginalPosition;
        y = yOriginalPosition;
        currentDirection = null;
        if (ai != null) {
            ai.reinitialize();
        }
    }

    public String getName() {
        return name;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setStopped(boolean stopped) {
        this.stopped = stopped;
    }

    public void setLastInput(Direction lastInput) {
        this.lastInput = lastInput;
    }

    public Direction getLastInput() {
        return lastInput;
    }

    public A getAi() {
        return ai;
    }

    public void setAi(A ai) {
        this.ai = ai;
    }

    public boolean hit(Collisionable other) {
        return getHitBox().intersects(other.getHitBox());
    }

    public Rectangle getHitBox() {
        return getCenter();
    }
}
