package ui;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.settings.GameSettings;
import facade.HomeworkTwoFacade;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import logic.brick.*;
import ui.GameStates.*;

import java.util.*;

import static ui.GameFatory.*;

public class App extends GameApplication {
    private int width = 1100;
    private int heigth = 700;
    private int nivelNumero = 1;
    private HashMap<Entity, Brick> actualLevelBricks;
    private HomeworkTwoFacade facade;
    private State gameState;

    public void setGameState(State newstate){
        this.gameState = newstate;
    }

    public HomeworkTwoFacade getFacade(){
        return facade;
    }

    public int getNivelNumero(){
        return nivelNumero;
    }

    public void setNivelNumero(int numero){
        this.nivelNumero = numero;
    }

    public void setActualLevelBricks(HashMap<Entity, Brick> linkedBricks){
        actualLevelBricks = linkedBricks;
    }

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(width);
        gameSettings.setHeight(heigth);
        gameSettings.setTitle("One piece Breakout");
        gameSettings.setVersion("");
    }

    public static void main(String... args){
        launch(args);
    }

    @Override
    protected void initGame(){
        Entity player = newPlayer(width/2.0 - 75,630, new EntityController());    // Platform 150*30
        Entity ball = newBall(player.getX() + 70,player.getY() - 17, new EntityController());// Symbolic ball
        Entity bg = newBackground(width, heigth);                       // Background
        Entity walls = newWalls();                                      // Screen collidable walls
        getGameWorld().addEntity(walls);
        //updateBalls(facade.getBallsLeft(), width);
        facade = new HomeworkTwoFacade();
        gameState = new GameNotStarted(this);
    }

    @Override
    protected void initInput(){
        Input input = getInput();

        input.addAction(new UserAction("New Level") {
            @Override
            protected void onActionBegin() {
                gameState.key_N();
                getGameState().setValue("number of levels", facade.numberOfLevels());
            }
        }, KeyCode.N);

        input.addAction(new UserAction("Start level") {
            @Override
            protected void onAction() {
                gameState.key_SPACE();
            }
        }, KeyCode.SPACE);

        input.addAction(new UserAction("Move Right"){
            @Override
            protected void onAction(){
                gameState.moveRight();
            }
            @Override
            protected void onActionEnd() {
                gameState.stop();
            }
        }, KeyCode.D);

        input.addAction(new UserAction("Move Left"){
            @Override
            protected void onAction(){
                gameState.moveLeft();
            }
            @Override
            protected void onActionEnd() {
                gameState.stop();
            }
        }, KeyCode.A);
    }

    @Override
    protected void initPhysics(){
        getPhysicsWorld().setGravity(0.3,0.3);

        getPhysicsWorld().addCollisionHandler(
                new CollisionHandler(Types.BALL, Types.WALL) {
                    @Override
                    protected void onHitBoxTrigger(Entity ball, Entity wall, HitBox boxBall, HitBox boxWall) {
                        if (boxWall.getName().equals("BOT")){
                            facade.dropBall();
                            if(facade.isGameOver()){
                                gameState.looseGame();return;
                            }
                            gameState.ballDrop(ball);   // And add a new one
                        }
                    }
                }
        );
        getPhysicsWorld().addCollisionHandler(
                new CollisionHandler(Types.PLAYER, Types.WALL) {
                    @Override
                    protected void onHitBoxTrigger(Entity player, Entity wall, HitBox boxPlayer, HitBox boxWall) {
                        if (boxWall.getName().equals("RIGHT")){
                            gameState.rightWall();
                        }else if (boxWall.getName().equals("LEFT")){
                            gameState.leftWall();
                        }
                    }
                }
        );
        getPhysicsWorld().addCollisionHandler(
                new CollisionHandler(Types.BALL, Types.BRICK) {
                    @Override
                    protected void onCollision(Entity ball, Entity UIBrick) {
                        Brick b = actualLevelBricks.get(UIBrick);
                        b.hit();
                        if(b.isDestroyed()){
                            UIBrick.removeFromWorld();
                            getGameState().increment("score", +(b.getScore()));
                            if(facade.winner()){
                                gameState.winGame();
                            }else if(facade.getCurrentLevel().getCurrentPoints() == 0){  // Pasamos a otro nivel
                                gameState.goToNextLevel();
                                getGameState().setValue("actual level", facade.getLevelName());
                            }
                        }

                    }
                }
        );

    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("score", 0);
        vars.put("actual level", "Level 1");
        vars.put("number of levels", 0);
    }

    @Override
    protected void initUI() {
        Text textScore1 = getUIFactory().newText("7", Color.WHITE, 25);
        textScore1.setTranslateX(32);
        textScore1.setTranslateY(25);
        textScore1.textProperty().bind(getGameState().intProperty("score").asString());
        getGameScene().addUINodes(textScore1);

        Text level = getUIFactory().newText("7", Color.WHITE, 25);
        level.setTranslateX(232);
        level.setTranslateY(25);
        level.textProperty().bind(getGameState().stringProperty("actual level"));
        getGameScene().addUINodes(level);

        Text numberLevels = getUIFactory().newText("7", Color.WHITE, 25);
        numberLevels.setTranslateX(432);
        numberLevels.setTranslateY(25);
        numberLevels.textProperty().bind(getGameState().intProperty("number of levels").asString());
        getGameScene().addUINodes(numberLevels);
    }

}
