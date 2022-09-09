package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.awt.*;


/**
 * Display a graphic object on the game window showing a numeric count of lives left.
 */
public class NumericLifeCounter  extends GameObject {
    private  static  TextRenderable textRenderable = new TextRenderable("");
    private Counter liveCounter;
    private GameObjectCollection gameObjectCollection;


    /**
     * Construct a new GameObject instance.
     * @param  liveCounter global lives counter of game.
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param  gameObjectCollection global game object collection
     *
     */
    public NumericLifeCounter(Counter liveCounter, Vector2 topLeftCorner, Vector2 dimensions,
                              GameObjectCollection gameObjectCollection) {
        super(topLeftCorner, dimensions, textRenderable);

        this.liveCounter = liveCounter;
        this.gameObjectCollection = gameObjectCollection;
        textRenderable.setString(String.format("num of lives : %d",this.liveCounter.value()));
        textRenderable.setColor(Color.BLACK);
        GameObject gameObj = new GameObject(topLeftCorner, dimensions,textRenderable);
        this.gameObjectCollection.addGameObject(gameObj, Layer.BACKGROUND);

    }

    /**
     * Code in this function is run every frame update.
     * @param deltaTime time between updates. For internal use by game engine.
     *                 You do not need to call this method yourself.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        textRenderable.setString(String.format("num of lives: %d",this.liveCounter.value()));
    }
}
