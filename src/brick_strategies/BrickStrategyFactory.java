package src.brick_strategies;

import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.util.Vector2;
import src.BrickerGameManager;

import java.util.Random;

/**
 * Factory class for creating Collision strategies
 */
public class BrickStrategyFactory {


    private GameObjectCollection gameObjectCollection;
    private BrickerGameManager gameManager;
    private ImageReader imageReader;
    private SoundReader soundReader;
    private UserInputListener inputListener;
    private WindowController windowController;
    private Vector2 windowDimensions;

    /**
     * constructor
     * @param gameObjectCollection A container for accumulating instances of GameObject and for handling
     *      *                             their collisions.
     * @param gameManager an GameManager instance .the manager of the game
     * @param imageReader an ImageReader instance for reading images from files for rendering of objects.
     * @param soundReader a SoundReader instance for reading soundclips from files for rendering event sounds.
     * @param inputListener an InputListener instance for reading user input.
     * @param windowController controls visual rendering of the game window and object renderables.
     * @param windowDimensions pixel dimensions for game window height x width
     */
    public BrickStrategyFactory(GameObjectCollection gameObjectCollection, BrickerGameManager gameManager,
                                ImageReader imageReader, SoundReader soundReader,
                                UserInputListener inputListener,
                                WindowController windowController, Vector2 windowDimensions) {


        this.gameObjectCollection = gameObjectCollection;


        this.gameManager = gameManager;
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.inputListener = inputListener;
        this.windowController = windowController;
        this.windowDimensions = windowDimensions;
    }

    /**
     * method randomly selects between 5 strategies and returns one CollisionStrategy
     * object which is a RemoveBrickStrategy decorated by one of the decorator strategies,
     * or decorated by two randomly selected strategies, or decorated by one of the decorator strategies
     * and a pair of additional two decorator strategies.
     * @return CollisionStrategy object.
     */

    public CollisionStrategy getStrategy() {
        CollisionStrategy collisionStrategy = new RemoveBrickStrategy(gameObjectCollection);
        Random rand = new Random();
        int numOfStrategy = rand.nextInt(6);
        if(numOfStrategy != 4) {
            collisionStrategy = combineStrategies(numOfStrategy, collisionStrategy);



        }
        else {
            int doubleStrategy = rand.nextInt(5);

            if(doubleStrategy ==4){
                for(int i = 0; i<3 ; i++){
                        collisionStrategy = combineStrategies(rand.nextInt(4), collisionStrategy);}


            }
            else{
                    collisionStrategy = combineStrategies(doubleStrategy, collisionStrategy);
                    collisionStrategy =combineStrategies(rand.nextInt(4),collisionStrategy);

            }

        }

        return collisionStrategy;
    }

    /**
     * this method returns CollisionStrategy object according the random number(0-5) that it gets
     * @param strategiesToChoose random number between 0-5
     * @param collisionStrategy the strategy that needs to be decorated
     * @return CollisionStrategy object.
     */
    private CollisionStrategy combineStrategies(int strategiesToChoose, CollisionStrategy collisionStrategy) {
        if (strategiesToChoose == 0) {
            return new PuckStrategy(collisionStrategy, imageReader, soundReader);
        }
        if (strategiesToChoose == 1) {
            return new AddPaddleStrategy(collisionStrategy, imageReader, inputListener, windowDimensions);
        }
        if (strategiesToChoose == 2) {
            return new ChangeCameraStrategy(collisionStrategy, windowController, gameManager);
        }
        if (strategiesToChoose == 3) {
            return new WidenOrNarrowStrategy(collisionStrategy, imageReader, windowDimensions,
                    gameObjectCollection);

        }
        if (strategiesToChoose == 5) {
            return collisionStrategy;
        }
        return null;

    }

}






