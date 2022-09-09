package src.brick_strategies;

import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.MockPaddle;

/**
 * Concrete class extending abstract RemoveBrickStrategyDecorator.
 * Introduces extra paddle to game window which remains until colliding
 * NUM_COLLISIONS_FOR_MOCK_PADDLE_DISAPPEARANCE with other game objects.
 */
public class AddPaddleStrategy extends  RemoveBrickStrategyDecorator{

    private static final float PADDLE_X_SIZE = 100;
    private static final float PADDLE_Y_SIZE = 15;
    private static  final  int MIN_DISTANCE_FROM_SCREEN_EDGE = 20;
    private static  int NUM_COLLISIONS_FOR_MOCK_PADDLE_DISAPPEARANCE = 3;


    private ImageReader imageReader;
    private UserInputListener inputListener;
    private Vector2 windowDimensions;
    private MockPaddle mockPaddle;

    /**
     * constructor
     * @param toBeDecorated Collision strategy object to be decorated.
     * @param imageReader  the image  renderable representing the object.
     * @param inputListener an inputListener instance for reading user input.
     * @param windowDimensions pixel dimensions for game window height x width.
     */

    public AddPaddleStrategy(CollisionStrategy toBeDecorated,
                             ImageReader imageReader,
                             UserInputListener inputListener,
                             Vector2 windowDimensions) {
        super(toBeDecorated);
        this.imageReader = imageReader;
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;


    }

    /**
     * Adds additional paddle to game and delegates to held object.
     * @param thisObj the object that collides
     * @param otherObj the objects that thisObj collide with
     * @param counter global brick counter.
     */

    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        super.onCollision(thisObj, otherObj, counter);
        Renderable mockPaddleImage = imageReader.readImage("assets/paddle.png",true);
        if(!MockPaddle.isInstantiated ){
            mockPaddle=new MockPaddle(Vector2.ZERO,new Vector2(PADDLE_X_SIZE,PADDLE_Y_SIZE),
                    mockPaddleImage,inputListener
                    ,windowDimensions,this.getGameObjectCollection(),MIN_DISTANCE_FROM_SCREEN_EDGE,
                    NUM_COLLISIONS_FOR_MOCK_PADDLE_DISAPPEARANCE);
            int mockPaddleHeight = (int)windowDimensions.y()/2;
            mockPaddle.setCenter( new Vector2(windowDimensions.x()/2,mockPaddleHeight));
            getGameObjectCollection().addGameObject(mockPaddle);}


    }
}
