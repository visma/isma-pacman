package org.isma.pacman.entity;

import org.isma.pacman.context.PacmanGameContext;
import org.isma.slick2d.BitmapObject;
import org.isma.slick2d.Direction;
import org.newdawn.slick.Animation;
import org.newdawn.slick.geom.Line;
import org.newdawn.slick.geom.Rectangle;

import static org.isma.slick2d.Direction.EAST;

public abstract class Character<C extends Character>
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

    public Character(PacmanGameContext context, String name, float x, float y) {
        super(context, x, y);
        this.name = name;
        this.xOriginalPosition = x;
        this.yOriginalPosition = y;
    }


    public float getSpeed() {
        return speed;
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


    public abstract void lose();

    public void resetOriginalState() {
        x = xOriginalPosition;
        y = yOriginalPosition;
        currentDirection = null;
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


    public boolean hit(Collisionable other) {
        return getHitBox().intersects(other.getHitBox());
    }

    public Rectangle getHitBox() {
        return getCenter();
    }

    @Override
    public boolean equals(Object o) {
        return name.equals(((Character) o).name);
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

}
