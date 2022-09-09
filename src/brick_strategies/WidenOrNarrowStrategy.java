package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.BuffWidenOrNarrow;

import java.util.Random;

/**
 * Concrete class extending abstract RemoveBrickStrategyDecorator. Introduces buffWiden or
 * buffNarrow, that widen or narrow the paddle
 */

public class WidenOrNarrowStrategy extends  RemoveBrickStrategyDecorator{

    private static final float BUFF_SPEED = 300;
    private ImageReader imageReader;
    private Vector2 windowDimensions;
    private GameObjectCollection gameObjectCollection;

    private BuffWidenOrNarrow buffWidenOrNarrow;

    /**
     * constructor
     * @param toBeDecorated Collision strategy object to be decorated.
     * @param imageReader  the image  renderable representing the object.
     * @param windowDimensions pixel dimensions for game window height x width.
     * @param gameObjectCollection  global game object collection
     */
    public WidenOrNarrowStrategy(CollisionStrategy toBeDecorated, ImageReader imageReader,
                                 Vector2 windowDimensions,
                                 GameObjectCollection gameObjectCollection) {
        super(toBeDecorated);
        this.imageReader = imageReader;

        this.windowDimensions = windowDimensions;
        this.gameObjectCollection = gameObjectCollection;

    }

    /**
     * Add buffWiden or buffNarrow to game on collision and delegate to held CollisionStrategy.
     * @param thisObj the object that collides
     * @param otherObj the objects that thisObj collides with
     * @param counter global brick counter.
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        super.onCollision(thisObj, otherObj, counter);
        Renderable widenImage = imageReader.readImage("assets/buffWiden.png",true);
        Renderable narrowImage = imageReader.readImage("assets/buffNarrow.png",true);

        Random rand = new Random();
        int randomNum =rand.nextInt(2);
        if( randomNum == 0){
            buffWidenOrNarrow = new BuffWidenOrNarrow(Vector2.ZERO,thisObj.getDimensions(),widenImage,
                    windowDimensions,
                    gameObjectCollection,0);
        }
        if(randomNum == 1){
            buffWidenOrNarrow = new BuffWidenOrNarrow(Vector2.ZERO,thisObj.getDimensions(),narrowImage,
                    windowDimensions,
                    gameObjectCollection,1);
        }
        buffWidenOrNarrow.setCenter(thisObj.getCenter());
        buffWidenOrNarrow.setVelocity(Vector2.DOWN.mult(BUFF_SPEED));
        getGameObjectCollection().addGameObject(buffWidenOrNarrow);







    }
}
