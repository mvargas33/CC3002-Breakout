package ui;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.settings.GameSettings;
import javafx.scene.input.KeyCode;
import logic.brick.*;
import logic.level.RealLevel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static ui.GameFatory.*;

public class Aplicacion extends GameApplication {
    private Entity player;
    private PlayerControl playerControl;
    private HashMap<Brick, Entity> actualLevelBricks;

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(1000);
        gameSettings.setHeight(650);
        gameSettings.setTitle("One Piece Breakout");
        gameSettings.setVersion("0.1");
    }

    public static void main(String... args){
        launch(args);
    }

    public List<Entity> bricksToEntities(List<Brick> bricks){
        List<Entity> entities = new ArrayList<>();
        int i = 0;
        int j = 0;
        for(Brick brick : bricks){
            System.out.println(j);
            if (i%10 == 0){
                j++;
                i=0;
            }
            System.out.println(100*i + "," + 20*j);
            if(brick instanceof GlassBrick){
                Entity glassBrick = newGlassBrick(100*i, 30*j);
                entities.add(glassBrick);
            }else if(brick instanceof MetalBrick){
                Entity glassBrick = newMetalBrick(100*i, 30*j);
                entities.add(glassBrick);
            }else if(brick instanceof WoodenBrick){
                Entity glassBrick = newWoodenBrick(100*i, 30*j);
                entities.add(glassBrick);
            }
            i++;
        }
        return entities;
    }

    @Override
    protected void initGame(){
        player = newPlayer(0,600, playerControl);
        playerControl = new PlayerControl();
        Entity bg = newBackground();
        Entity ball = newBall(500,500);
        Entity walls = newWalls();  // Screen collidable walls
        List<Entity> bricks = bricksToEntities(new RealLevel("Level 1", 40, 0.5, 0).getBricks());
        for(Entity br : bricks){
            getGameWorld().addEntity(br);
        }
        getGameWorld().addEntities(bg, player, ball, walls);
    }

    @Override
    protected void initInput(){
        Input input = getInput();

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
        getPhysicsWorld().setGravity(0,0);

        getPhysicsWorld().addCollisionHandler(
                new CollisionHandler(Types.BALL, Types.WALL) {
                    @Override
                    protected void onHitBoxTrigger(Entity ball, Entity wall, HitBox boxBall, HitBox boxWall) {
                        if (boxWall.getName().equals("BOT")){
                            ball.removeFromWorld();
                            Entity new_ball = newBall(500,500);
                            getGameWorld().addEntities(new_ball);
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
