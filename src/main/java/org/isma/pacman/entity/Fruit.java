package org.isma.pacman.entity;

import org.isma.pacman.PacmanGameContext;
import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import static org.isma.slick2d.RenderableFactory.buildAnimation;

public class Fruit extends Food {


    public enum FruitEnum {
        CHERRY(100),
        STRAWBERRY(300),
        ORANGE(500),
        APPLE(700),
        MELON(1000),
        GALAXIAN(2000),
        BELL(3000),
        KEY(5000);

        private final int points;

        private FruitEnum(int points) {
            this.points = points;
        }
    }

    private FruitEnum fruitEnum;


    public Fruit(PacmanGameContext context, float x, float y, FruitEnum fruitEnum) throws SlickException {
        super(context, x, y);
        this.fruitEnum = fruitEnum;
    }

    @Override
    protected void buildRenderables() {
        Animation animation = buildAnimation(sprite, FruitEnum.values().length, getAnimationRate(), 0, 0, 0, false);
        for (FruitEnum fruit : FruitEnum.values()) {
            renderables.put(fruit, buildAnimation(animation, fruit.ordinal()));
        }
    }

    @Override
    protected Object getRenderableKey() {
        return fruitEnum;
    }

    @Override
    public int getValue() {
        return fruitEnum.points;
    }

    @Override
    protected SpriteSheet getSpriteSheet() {
        return getContext().getResourcesManager().getImageResources().getFruitsSprites();
    }

    public void nextFruit() {
        FruitEnum[] fruits = FruitEnum.values();
        fruitEnum = fruits[(fruitEnum.ordinal() + 1) % fruits.length];
    }

    public FruitEnum getPreviousFruit() {
        int previousFruit = fruitEnum.ordinal() - 1;
        if (previousFruit == -1) {
            previousFruit = FruitEnum.values().length - 1;
        }
        return FruitEnum.values()[previousFruit];
    }


}
