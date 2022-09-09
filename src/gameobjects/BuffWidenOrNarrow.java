package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * widen or narrow object of the game, widen or narrow the paddle in collision.
 */
public class BuffWidenOrNarrow extends GameObject {
    private Vector2 windowDimensions;
    private GameObjectCollection gameObjectCollection;
    private int coin;

    /**
     * constructor
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions  Width and height in window coordinates.
     * @param renderable The renderable representing the object. Can be null, in which case
     * @param windowDimensions  pixel dimensions for game window height x width.
     * @param gameObjectCollection  global game object collection
     * @param coin 1 if narrow is needed, 0 if widen is needed
     */
    public BuffWidenOrNarrow(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                             Vector2 windowDimensions,
                             GameObjectCollection gameObjectCollection
                             ,int coin) {
        super(topLeftCorner, dimensions, renderable);
        this.windowDimensions = windowDimensions;
        this.gameObjectCollection = gameObjectCollection;
        this.coin = coin;
    }

    /**
     * Code in this function is run every frame update.
     * @param deltaTime time between updates. For internal use by game engine.
     *                 You do not need to call this method yourself.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if( this.getCenter().y() > windowDimensions.y() ){
            gameObjectCollection.removeGameObject(this);

        }


    }

    /**
     * Called on the first frame of a collision.
     * @param other The GameObject with which a collision occurred.
     * @param collision Information regarding this collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (shouldCollideWith(other)) {
            gameObjectCollection.removeGameObject(this);
            Vector2 paddleLength = other.getDimensions();
            if (coin == 1) {
                if (paddleLength.x() > 10) {
                    other.setDimensions(paddleLength.mult(0.5f));
                }
            }
            if (coin == 0) {
                if (paddleLength.x() < windowDimensions.x() / 2) {
                    other.setDimensions(paddleLength.mult(2));
                }
            }

        }
    }


    /**
     * Should this object be allowed to collide the  specified other object.
     * If both this object returns true for the other, and the other returns true for this one,
     * the collisions may occur when they overlap, meaning that their respective
     * onCollisionEnter/onCollisionStay/onCollisionExit will be called.
     * @param other  The other GameObject.
     * @return true if the objects should collide
     */

    @Override
    public boolean shouldCollideWith(GameObject other) {
        super.shouldCollideWith(other);
        return other instanceof Paddle;
    }
}
