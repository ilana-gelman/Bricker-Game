package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.Sound;
import danogl.gui.SoundReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.Puck;
import java.util.Random;


/**
 * Concrete class extending abstract RemoveBrickStrategyDecorator.
 * Introduces several pucks instead of brick once removed.
 */
public class PuckStrategy extends  RemoveBrickStrategyDecorator{
    private static final float PUCK_VELOCITY = 300;
    private ImageReader imageReader;
    private SoundReader soundReader;
    private Puck puck;

    /**
     * constructor
     * @param toBeDecorated Collision strategy object t
     *                      o be decorated.
     * @param imageReader  the image  renderable representing the object.
     * @param soundReader a SoundReader instance for reading soundclips from files for rendering event sounds.
     */
    public PuckStrategy(CollisionStrategy toBeDecorated, ImageReader imageReader, SoundReader soundReader) {
        super(toBeDecorated);
        this.imageReader = imageReader;
        this.soundReader = soundReader;

    }

    /**
     * Add pucks to game on collision and delegate to held CollisionStrategy.
     * @param thisObj the object that collides
     * @param otherObj the objects that thisObj collide with
     * @param counter global brick counter.
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        super.onCollision(thisObj, otherObj, counter);
        Renderable puckImage = this.imageReader.readImage("assets/mockBall.png",true);
        Sound collisionSound = this.soundReader.readSound("assets/blop_cut_silenced.wav");
        for(int i=0 ; i<3 ; i++){
            puck = new Puck(Vector2.ZERO
                    , new Vector2((thisObj.getDimensions().x()/3),(thisObj.getDimensions().y())),
                    puckImage, collisionSound);
            puck.setCenter(thisObj.getCenter());
            Random rand = new Random();
            float puckVelX = PUCK_VELOCITY;
            float puckVelY = PUCK_VELOCITY;
            if (rand.nextBoolean()) {
                puckVelX *= -1;
            }
            if (rand.nextBoolean()) {
                puckVelY *= -1;
            }
            puck.setVelocity(new Vector2( (puckVelX), puckVelY));
            this.getGameObjectCollection().addGameObject(puck);



        }


    }
}
