package ui.GameStates;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import javafx.geometry.Point2D;
import ui.App;
import ui.EntityController;
import ui.GameFatory;

import java.util.Random;

import static ui.GameFatory.newBall;

public abstract class AbstractState implements State{
    private App game;
    private EntityController playerController;
    private EntityController ballController;

    protected AbstractState(App g){
        game = g;
        playerController = getGame().getGameWorld().getEntitiesByType(GameFatory.Types.PLAYER).get(0).getComponent(EntityController.class);
        ballController = getGame().getGameWorld().getEntitiesByType(GameFatory.Types.BALL).get(0).getComponent(EntityController.class);
    }

    protected App getGame(){
        return game;
    }

    protected EntityController getPlayerController(){
        return playerController;
    }

    protected EntityController getBallController(){
        return ballController;
    }

    @Override
    public void moveRight() {
        playerController.right();
    }

    @Override
    public void moveLeft() {
        playerController.left();
    }

    @Override
    public void shootBall() {
        ballController.throwAway();
    }

    @Override
    public void addLevel() {

    }

    @Override
    public void goToNextLevel() {

    }

    @Override
    public void winGame() {
        getGame().setGameState(new GameOver(getGame(), true));
    }

    @Override
    public void looseGame() {
        getGame().setGameState(new GameOver(getGame(), false));
    }

    @Override
    public void stop() {
        playerController.stop();
    }

    @Override
    public void key_SPACE(){}

    @Override
    public void key_N() {}

    @Override
    public void rightWall(){
        System.out.println("RIGHT WALL");
        playerController.stop();
        playerController.blockRight();
        playerController.unblockLeft();
    }

    @Override
    public void leftWall(){
        System.out.println("LEFT WALL");
        playerController.stop();
        playerController.blockLeft();
        playerController.unblockRight();
    }

    @Override
    public void ballDrop(Entity deadBall){
        Entity player = getGame().getGameWorld().getEntitiesByType(GameFatory.Types.PLAYER).get(0);
        Entity ball = newBall(player.getX() + 70, player.getY() - 17, new EntityController());
        ballController = ball.getComponent(EntityController.class);
        deadBall.removeFromWorld();
        getGame().setGameState(new StandBy(getGame()));
    }

    @Override
    public double genNumberOfBricks(){
        Random randomObject = new Random();
        double nBricks = 0;
        while(!(nBricks >= 30 && nBricks < 50)){
            nBricks = randomObject.nextDouble()*100;
        }
        return nBricks;
    }
}
