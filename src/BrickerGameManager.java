package src;

import src.brick_strategies.BrickStrategyFactory;
import src.brick_strategies.CollisionStrategy;
import src.gameobjects.*;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.awt.*;
import java.util.Random;

/**
 * thus class is responsible for game initialization. holding references for game objects and calling update methods
 * for every update iteration
 */
public class BrickerGameManager  extends  GameManager{
    public static final  int BORDER_WIDTH = 20;

    private   static  final float BALL_SPEED =250;
    private static  final  int MIN_DISTANCE_FROM_SCREEN_EDGE = 20;
    private static final int LIFE_COUNTER = 4;
    private static final float NUMERIC_LIFE_COUNTER_SIZE =25;
    private static final float HEART_SIZE = 30;
    private static final float PADDLE_X_SIZE = 100;
    private static final float PADDLE_Y_SIZE = 15;
    private static final float BALL_SIZE = 20;
    private  static final float NUM_OF_ROWS = 5;
    private  static final float NUM_OF_COLS = 8;
    private static final float WINDOW_WIDTH = 700;
    private static final float WINDOW_HEIGHT = 500;
    private static final float BRICK_HEIGHT = 15;


    private  Ball ball;
    private Vector2 windowDimensions;
    private WindowController windowController;
    private Counter lifeCounter;
    private  int numOfLives;
    private Counter numOfBricks;


    /**
     * constructor, initialize all th fields
     * @param windowTitle  name of the game
     * @param windowDimensions  pixel dimensions for game window height x width
     */
    public BrickerGameManager(String windowTitle, Vector2 windowDimensions){
        super(windowTitle,windowDimensions);
    }

    /**
     * Calling this function should initialize the game window.
     * @param imageReader an ImageReader instance for reading images from files for rendering of objects.
     * @param soundReader  a SoundReader instance for reading soundclips from files for rendering event sounds.
     * @param inputListener an InputListener instance for reading user input.
     * @param windowController  controls visual rendering of the game window and object renderables.
     */
    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        this.numOfLives =4;
        this.lifeCounter = new Counter(LIFE_COUNTER);
        this.windowController = windowController;
        this.numOfBricks = new Counter(0);

        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        windowController.setTargetFramerate(80);
        //creating ball
        cratingBall(imageReader, soundReader, windowController);
        //create paddle
        creatingPaddle(imageReader, inputListener);
       //creating walls
        craeatingWalls();
        //background
        creatingBackground(imageReader, windowController);
        //brick
        creatingBricks(imageReader,soundReader,inputListener);
        //heart
        creatingGrapicLifeCounter(imageReader);
        //string
        creatingNumericLifeCounter();

    }

    /**
     * creates the string that counts down the left num of lives
     */
    private void creatingNumericLifeCounter() {
        GameObject numericLifeCounter = new NumericLifeCounter(lifeCounter,
                new Vector2(BORDER_WIDTH +5, windowDimensions.y()-70),
                new Vector2(NUMERIC_LIFE_COUNTER_SIZE,NUMERIC_LIFE_COUNTER_SIZE),this.gameObjects());
        gameObjects().addGameObject(numericLifeCounter,Layer.BACKGROUND);
    }

    /**
     * creates 4 hearts that represent the left num of lives
     * @param imageReader an ImageReader instance for reading images from files for rendering of objects.
     */
    private void creatingGrapicLifeCounter(ImageReader imageReader) {
        Renderable heartImage = imageReader.readImage("assets/heart.png",true);
        GraphicLifeCounter graphicLifeCounter = new GraphicLifeCounter(
                new Vector2(BORDER_WIDTH +5 ,windowDimensions.y()-40),
                new Vector2( HEART_SIZE, HEART_SIZE),lifeCounter,heartImage,this.gameObjects(),numOfLives);
        gameObjects().addGameObject(graphicLifeCounter,Layer.BACKGROUND);
    }

    /**
     * creates the bricks of the game, 5 rows,8 cols. Calculates the correct dimensions
     * th matches the screen dimensions
     * @param imageReader an ImageReader instance for reading images from files for rendering of objects.
     */
    private void creatingBricks(ImageReader imageReader,SoundReader soundReader,UserInputListener inputListener) {
        float brickWidth = (windowDimensions.x()-2*(BORDER_WIDTH+5)-(NUM_OF_COLS-1))/NUM_OF_COLS;
        Renderable brickImage = imageReader.readImage("assets/brick.png",false);
        BrickStrategyFactory brickStrategyFactory = new BrickStrategyFactory(gameObjects(),
                this,imageReader,soundReader,inputListener, windowController,windowDimensions);
        for (int i = 0; i < NUM_OF_ROWS; i++) {
            for(int j=0; j<NUM_OF_COLS ; j++){
                CollisionStrategy collisionStrategy = brickStrategyFactory.getStrategy();
                GameObject brick= new Brick(new Vector2((BORDER_WIDTH+5)+(j*(brickWidth)) +j ,
                        (BORDER_WIDTH+5)+(i*BRICK_HEIGHT) +i), new Vector2((brickWidth) ,BRICK_HEIGHT),
                        brickImage,
                        collisionStrategy, numOfBricks );
                gameObjects().addGameObject(brick,Layer.STATIC_OBJECTS);
                numOfBricks.increment();
            }
        }
    }

    /**
     * puts the background of the game
     * @param imageReader an ImageReader instance for reading images from files for rendering of objects.
     * @param windowController controls visual rendering of the game window and object renderables
     */
    private void creatingBackground(ImageReader imageReader, WindowController windowController) {
        GameObject background = new GameObject(Vector2.ZERO, windowController.getWindowDimensions(),
                imageReader.readImage("assets/DARK_BG2_small.jpeg",false));
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects().addGameObject(background, Layer.BACKGROUND);
    }

    /**
     * creates the walls of the game- leftwall,rightwall,upwall
     */
    private void craeatingWalls() {
        RectangleRenderable rectangleRenderable1 =new RectangleRenderable(Color.BLACK);
        GameObject leftWall = new GameObject(Vector2.ZERO, new Vector2(BORDER_WIDTH, windowDimensions.y()),
                rectangleRenderable1);
        gameObjects().addGameObject(leftWall);

        RectangleRenderable rectangleRenderable2 =new RectangleRenderable(Color.BLACK);
        GameObject rightWall = new GameObject(new Vector2(windowDimensions.x()-BORDER_WIDTH,0),
                new Vector2(BORDER_WIDTH, windowDimensions.y()),rectangleRenderable2);
        gameObjects().addGameObject(rightWall);

        RectangleRenderable rectangleRenderable3 =new RectangleRenderable(Color.BLACK);
        GameObject upWall = new GameObject(Vector2.ZERO, new Vector2(windowDimensions.x(),
                (float)BORDER_WIDTH),rectangleRenderable3);
        gameObjects().addGameObject(upWall);
    }

    /**
     * creates the paddle of the game
     * @param imageReader an ImageReader instance for reading images from files for rendering of objects.
     * @param inputListener an InputListener instance for reading user input.
     */
    private void creatingPaddle(ImageReader imageReader, UserInputListener inputListener) {
        Renderable paddleImage = imageReader.readImage("assets/paddle.png",true);
        GameObject  Paddle =new Paddle(Vector2.ZERO, new Vector2(PADDLE_X_SIZE,PADDLE_Y_SIZE), paddleImage,
                inputListener,windowDimensions,MIN_DISTANCE_FROM_SCREEN_EDGE);
        Paddle.setCenter( new Vector2(windowDimensions.x()/2,(int)windowDimensions.y()-30));
        gameObjects().addGameObject(Paddle);
    }

    /**
     * creates the ball of the game
     * @param imageReader  an ImageReader instance for reading images from files for rendering of objects.
     * @param soundReader  a SoundReader instance for reading soundclips from files for rendering event
     *                     sounds.
     * @param windowController controls visual rendering of the game window and object renderables.
     */

    private void cratingBall(ImageReader imageReader, SoundReader soundReader,
                             WindowController windowController) {
        Renderable ballImage = imageReader.readImage("assets/ball.png",true);
        Sound collisionSound = soundReader.readSound("assets/blop_cut_silenced.wav");
        ball = new Ball(Vector2.ZERO, new Vector2(BALL_SIZE,BALL_SIZE),ballImage,collisionSound);
        windowDimensions = windowController.getWindowDimensions();
        gameObjects().addGameObject(ball);
        repositionBall(ball);
    }


    /**
     * Code in this function is run every frame update.
     * @param deltaTime time between updates. For internal use by game engine.
     *                 You do not need to call this method yourself.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        for (GameObject gameObject: gameObjects()) {
            if(gameObject instanceof Puck && gameObject.getCenter().y()> windowDimensions.y()){
                gameObjects().removeGameObject(gameObject);
            }

        }
        checkForGameEnd();


    }
    /**
     * checks if the game ended,show at the screen if the player won or lost,
     * if the ball crossed the borders, num of lives are decreases
     */
    private void checkForGameEnd() {
        float ballHeight = ball.getCenter().y();
        String prompt = "";
        if (ballHeight > windowDimensions.y() && lifeCounter.value() > 0 && lifeCounter.value() -1 !=0){
            lifeCounter.decrement();
            repositionBall(ball);
        }
        else
            if(ballHeight > windowDimensions.y() && lifeCounter.value()-1 ==0){
            prompt = "You Lose!";}

        else
            if(numOfBricks.value()<=0 && lifeCounter.value() >0 ){
            prompt = "You win!";
        }

        if(!prompt.isEmpty() ){
            prompt += " Play again?";
            if(windowController.openYesNoDialog(prompt)){
                windowController.resetGame();
            }
            else
                windowController.closeWindow();
        }
    }


    /**
     * this function resets the ball position and velocity
     * @param ball the ball object
     */
    public void repositionBall(GameObject ball){
        ball.setCenter(windowDimensions.mult(0.5f));
        float ballVelX = BALL_SPEED;
        float ballVelY = BALL_SPEED;
        Random rand = new Random();
        if (rand.nextBoolean()) {
            ballVelX *= -1;
        }
        if (rand.nextBoolean()) {
            ballVelY *= -1;
        }
        ball.setVelocity(new Vector2(ballVelX, ballVelY));

    }

    /**
     * Entry point for game. Should contain:
     * 1. An instantiation call to BrickerGameManager constructor.
     * 2. A call to run() method of instance of BrickerGameManager.
     * Should initialize game window of dimensions (x,y) = (700,500).
     * @param args command line args
     */
    public static void main(String[] args) {
        BrickerGameManager brickerGameManager =new BrickerGameManager("Bricker",
                new Vector2(WINDOW_WIDTH,WINDOW_HEIGHT));
        brickerGameManager.run();



    }
}

