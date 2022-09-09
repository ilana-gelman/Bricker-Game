package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;


/**
 * Concrete brick strategy implmenting CollisionStrategy interface. Removes holding brick on collision.
 */
public class RemoveBrickStrategy implements  CollisionStrategy{
    private GameObjectCollection gameObjectCollection;
    private boolean isDisappeared;

    /**
     * constructor
     * @param gameObjectCollection global game object collection
     */
    public RemoveBrickStrategy(GameObjectCollection gameObjectCollection){

        this.gameObjectCollection = gameObjectCollection;
        this.isDisappeared =false;
    }

    /**
     * Removes brick from game object collection on collision.
     * @param thisObj the object that collides
     * @param otherObj the objects that thisObj collide with
     * @param counter global brick counter.
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        if(!isDisappeared){
            this.gameObjectCollection.removeGameObject(thisObj, Layer.STATIC_OBJECTS);
            counter.decrement();
            isDisappeared =true;
        }
    }

    /**
     * All collision strategy objects should hold a reference to the global game object collection and be able
     * to return it.
     * @return global game object collection whose reference is held in object.
     */
    @Override
    public GameObjectCollection getGameObjectCollection() {
        return this.gameObjectCollection;
    }
}
