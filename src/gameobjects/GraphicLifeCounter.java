package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * Display a graphic object on the game window showing as many widgets as lives left.
 */
public class GraphicLifeCounter extends GameObject {

     private Counter liveCounter;
     private Renderable widgetRenderable;
     private GameObjectCollection gameObjectCollection;
     private int numOfLives;
     private GameObject[] liveArray;

     /**
      * Construct a new GameObject instance.
      *
      * @param widgetTopLeftCorner Position of the object, in window coordinates (pixels).
      *                      Note that (0,0) is the top-left corner of the window.
      * @param widgetDimensions    Width and height in window coordinates.
      * @param widgetRenderable    The renderable representing the object. Can be null, in which case
      * @param liveCounter  global lives counter of game.
      * @param gameObjectCollection global game object collection managed by game manager.
      * @param numOfLives global setting of number of lives a player will have in a game.
      */
     public GraphicLifeCounter(Vector2 widgetTopLeftCorner, Vector2 widgetDimensions, Counter liveCounter,
                               Renderable widgetRenderable,
                               GameObjectCollection gameObjectCollection,
                               int numOfLives) {
          super(widgetTopLeftCorner, widgetDimensions, widgetRenderable);
          this.liveCounter = liveCounter;
          this.widgetRenderable = widgetRenderable;
          this.gameObjectCollection = gameObjectCollection;
          this.numOfLives = numOfLives;
          this.liveArray = new GameObject[numOfLives];
          for(int i=0 ; i<numOfLives ; i++){
               GameObject heart = new GameObject(
                       new Vector2(widgetTopLeftCorner.x() + i*(widgetDimensions.x()+5),
                               widgetTopLeftCorner.y()),
                       widgetDimensions,widgetRenderable);
               liveArray[i]= heart;
               this.gameObjectCollection.addGameObject(liveArray[i], Layer.BACKGROUND);
          }
     }

     /**
      * Code in this function is run every frame update.
      * @param deltaTime time between updates. For internal use by game engine.
      */
     @Override
     public void update(float deltaTime) {
          super.update(deltaTime);
          if(numOfLives > liveCounter.value()){
               numOfLives --;
               this.gameObjectCollection.removeGameObject(liveArray[numOfLives],Layer.BACKGROUND);

          }

     }
}
