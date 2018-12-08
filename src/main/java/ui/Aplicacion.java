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
import logic.level.Level;
import logic.level.RealLevel;

import java.util.*;

import static ui.GameFatory.*;

public class Aplicacion extends GameApplication {
    private int width = 1100;
    private int heigth = 700;
    private int nivelNumero = 1;
    private boolean levelStarted = false;
    private boolean isThisFirstLevel = true;
    private Entity player;
    private PlayerControl playerControl;
    private HashMap<Entity, Brick> actualLevelBricks;
    private HomeworkTwoFacade facade;

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(width);
        gameSettings.setHeight(heigth);
        gameSettings.setTitle("|");
        gameSettings.setVersion("");
    }

    public static void main(String... args){
        launch(args);
    }

    @Override
    protected void initGame(){
        /* EMPTY GAME */
        facade = new HomeworkTwoFacade();
        playerControl = new PlayerControl();
        playerControl.blockLeft();playerControl.blockRight();
        player = newPlayer(width/2.0 - 75,630, playerControl);    // Platform 150*30
        Entity bg = newBackground(width, heigth);                       // Background
        Entity ball = newBall(player.getX() + 70,player.getY() - 17, false);// Symbolic ball
        Entity walls = newWalls();                                      // Screen collidable walls
        getGameWorld().addEntities(bg, player, walls);
        updateBalls(facade.getBallsLeft(), width);
        /* EMPTY GAME */
/*
        List<Entity> bricks = bricksToEntities(new RealLevel("Level 1", 40, 0.5, 0).getBricks());
        for(Entity br : bricks){
            getGameWorld().addEntity(br);
        }
*/
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
                if(!levelStarted){
                    getGameWorld().getEntitiesByType(Types.BALL).forEach(Entity::removeFromWorld);
                    List<Entity> p = getGameWorld().getEntitiesByType(Types.PLAYER);
                    Entity ball = newBall(p.get(0).getX() + 70,p.get(0).getY() - 17, true);
                    levelStarted = true;
                    playerControl.unblockLeft();
                    playerControl.unblockRight();
                }
            }
        }, KeyCode.SPACE);

        input.addAction(new UserAction("Move Right"){
            @Override
            protected void onAction(){
                playerControl.right();
            }
            @Override
            protected void onActionEnd() {
                playerControl.stop();
            }
        }, KeyCode.D);

        input.addAction(new UserAction("Move Left"){
            @Override
            protected void onAction(){
                playerControl.left();
            }
            @Override
            protected void onActionEnd() {
                playerControl.stop();
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
                                Entity s_ball = newBall(player.getX() + 70, player.getY() - 17, false);// Symbolic ball
                                playerControl.stop();
                                playerControl.blockRight();
                                playerControl.blockLeft();
                            }else{
                                // GAME OVER
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
                            playerControl.blockRight();
                            playerControl.stop();
                        }else if (boxWall.getName().equals("LEFT")){
                            playerControl.blockLeft();
                            playerControl.stop();
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
        Text textScore1 = getUIFactory().newText("", Color.WHITE, 25);
        textScore1.setTranslateX(32);
        textScore1.setTranslateY(25);
        textScore1.textProperty().bind(getGameState().intProperty("score").asString());
        getGameScene().addUINodes(textScore1);
    }

    @Override
    protected void onUpdate(double tpf){

 /*d       System.out.println(player.getX());
        double currentPositionX = player.getX();
        if (currentPositionX <= 0.0) playerControl.blockLeft();
        if (currentPositionX >= getWidth()) playerControl.blockRight();
*/
    }
}
