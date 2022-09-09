package src.brick_strategies;

import danogl.GameObject;
import danogl.gui.WindowController;
import danogl.gui.rendering.Camera;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.BrickerGameManager;
import src.gameobjects.Ball;
import src.gameobjects.BallCollisionCountdownAgent;
import src.gameobjects.Puck;

/**
 * Concrete class extending abstract RemoveBrickStrategyDecorator.
 * Changes camera focus from ground to ball until ball collides NUM_BALL_COLLISIONS_TO_TURN_OFF times.
 */
public class ChangeCameraStrategy extends RemoveBrickStrategyDecorator{

    private WindowController windowController;
    private BrickerGameManager gameManager;
    private static int NUM_BALL_COLLISIONS_TO_TURN_OFF = 4;

    /**
     * constructor
     * @param toBeDecorated Collision strategy object to be decorated.
     * @param windowController controls visual rendering of the game window and object renderables.
     * @param gameManager an GameManager instance .the manager of the game
     *
     */
    public ChangeCameraStrategy(CollisionStrategy toBeDecorated, WindowController windowController,
                                BrickerGameManager gameManager) {
        super(toBeDecorated);
        this.windowController = windowController;
        this.gameManager = gameManager;
    }

    /**
     * Change camere position on collision and delegate to held CollisionStrategy.
     * @param thisObj the object that collides
     * @param otherObj the objects that thisObj collide with
     * @param counter global brick counter.
     */

    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        super.onCollision(thisObj, otherObj, counter);
        if (!(otherObj instanceof Puck) && gameManager.getCamera() == null) {
            gameManager.setCamera(
                    new Camera(
                            otherObj,            //object to follow
                            Vector2.ZERO,    //follow the center of the object
                            windowController.getWindowDimensions().mult(1.2f),  //widen the frame a bit
                            windowController.getWindowDimensions()   //share the window dimensions
                    )
            );
            BallCollisionCountdownAgent ballCollisionCountdownAgent =
                    new BallCollisionCountdownAgent((Ball)otherObj,
                    this, ((Ball) otherObj).getCollisionCount() +
                            NUM_BALL_COLLISIONS_TO_TURN_OFF);
            getGameObjectCollection().addGameObject(ballCollisionCountdownAgent);

        }
    }

    /**
     *
     * Return camera to normal ground position.
     */
    public void turnOffCameraChange(){
        gameManager.setCamera(null);}

}
