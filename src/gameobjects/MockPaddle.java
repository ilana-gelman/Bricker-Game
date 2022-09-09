package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;


/**
 * in collision another paddle is added to the game, and disappears after 3 collisions with the ball
 */
public class MockPaddle extends Paddle{
    public  static  boolean isInstantiated;
    private GameObjectCollection gameObjectCollection;
    private int numCollisionsToDisappear;

    /**
     * Construct a new GameObject instance.
     * @param topLeftCorner  Position of the object, in window coordinates (pixels). Note that (0,0)
     *                       is the top-left corner of the window.
     * @param dimensions  Width and height in window coordinates.
     * @param renderable The renderable representing the object. Can be null, in which case
     * @param inputListener  listener object for user input.
     * @param windowDimensions dimensions of game window.
     * @param gameObjectCollection  global game object collection
     * @param minDistanceFromEdge border for paddle movement.
     * @param numCollisionsToDisappear num of collisions needed until the mockPaddle disappears
     */
    public MockPaddle(Vector2 topLeftCorner, Vector2 dimensions,
                      Renderable renderable, UserInputListener inputListener,
                      Vector2 windowDimensions, GameObjectCollection gameObjectCollection,
                      int minDistanceFromEdge, int numCollisionsToDisappear){
        super(topLeftCorner, dimensions, renderable, inputListener, windowDimensions, minDistanceFromEdge);
        isInstantiated = true;
        this.gameObjectCollection = gameObjectCollection;
        this.numCollisionsToDisappear = numCollisionsToDisappear;

    }

    /**
     * Called on the first frame of a collision.
     * @param other The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     */

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if(other instanceof Ball) {
            if (this.numCollisionsToDisappear - 1 <= 0) {
                isInstantiated = false;
                this.gameObjectCollection.removeGameObject(this);
            } else {
                numCollisionsToDisappear--;
            }
        }

    }

    /**
     * Code in this function is run every frame update.
     * @param deltaTime time between updates. For internal use by game engine.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

    }
}
