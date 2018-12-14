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
    private boolean levelStarted = false;
    private boolean isThisFirstLevel = true;
    private Entity player;
    private HashMap<Entity, Brick> actualLevelBricks;
    private HomeworkTwoFacade facade;
    private State gameState;

    public void setGameState(State newstate){
        this.gameState = newstate;
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
        gameState = new GameStarted(this);
    }

    @Override
    protected void initInput(){
        Input input = getInput();

        input.addAction(new UserAction("New Level") {
            @Override
            protected void onAction() {
                Random randomObject = new Random();double nBricks = 0;
                while(!(nBricks >= 30 && nBricks < 50)){
                    nBricks = randomObject.nextDouble()*100;
                }
                if(isThisFirstLevel){
                    facade.setCurrentLevel(facade.newLevelWithBricksFull("Level " + nivelNumero, (int)nBricks, randomObject.nextDouble(), nBricks/100, 0));
                    List<Brick> levelBricks = facade.getBricks();
                    System.out.println(levelBricks.size());
                    actualLevelBricks = linkBricks(levelBricks);
                    isThisFirstLevel = false;
                    gameState.key_N();
                }else{
                    facade.addPlayingLevel(facade.newLevelWithBricksFull("Level " + nivelNumero, (int)nBricks, randomObject.nextDouble(), nBricks/100, 0));
                }
                nivelNumero++;
            }
        }, KeyCode.N);

        input.addAction(new UserAction("Start level") {
            @Override
            protected void onAction() {
                //System.out.println("Barra espaciadora!!");
                /*if(!levelStarted){
                    getGameWorld().getEntitiesByType(Types.BALL).forEach(Entity::removeFromWorld);
                    List<Entity> p = getGameWorld().getEntitiesByType(Types.PLAYER);
                    Entity ball = newBall(p.get(0).getX() + 70,p.get(0).getY() - 17, false);

                    levelStarted = true;
                    //entityController.unblockLeft();
                    //entityController.unblockRight();

                }*/
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
                            ball.removeFromWorld();
                            facade.dropBall();
                            if(facade.getBallsLeft() > 0) {
                                levelStarted = false;
                                gameState.ballDrop();
                                Entity s_ball = newBall(player.getX() + 70, player.getY() - 17, new EntityController());// Symbolic ball
                                //entityController.stop();
                                //entityController.blockRight();
                                //entityController.blockLeft();
                            }else{
                                gameState.looseGame();
                            }
                        }
                    }
                }
        );
        getPhysicsWorld().addCollisionHandler(
                new CollisionHandler(Types.BALL, Types.PLAYER) {
                    @Override
                    protected void onCollisionBegin(Entity ball, Entity player){
                    }
                }
        );
        getPhysicsWorld().addCollisionHandler(
                new CollisionHandler(Types.PLAYER, Types.WALL) {
                    @Override
                    protected void onHitBoxTrigger(Entity player, Entity wall, HitBox boxPlayer, HitBox boxWall) {
                        if (boxWall.getName().equals("RIGHT")){
                            gameState.rightWall();
                            //entityController.blockRight();
                            //entityController.stop();
                        }else if (boxWall.getName().equals("LEFT")){
                            //System.out.println("LEFT WALL IN CONTRLLES");
                            gameState.leftWall();
                            //entityController.blockLeft();
                            //entityController.stop();
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
                        }
                    }
                }
        );

    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("score", 0);
    }

    @Override
    protected void initUI() {
        Text textScore1 = getUIFactory().newText("7", Color.WHITE, 25);
        textScore1.setTranslateX(32);
        textScore1.setTranslateY(25);
        textScore1.textProperty().bind(getGameState().intProperty("score").asString());
        getGameScene().addUINodes(textScore1);
    }

    @Override
    protected void onUpdate(double tpf){

 /*d       System.out.println(player.getX());
        double currentPositionX = player.getX();
        if (currentPositionX <= 0.0) entityController.blockLeft();
        if (currentPositionX >= getWidth()) entityController.blockRight();
*/
    }
}
