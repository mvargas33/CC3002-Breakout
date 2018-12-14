package ui.GameStates;

import com.almasb.fxgl.app.GameApplication;
import ui.App;
import ui.EntityController;
import ui.GameFatory;

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

    }

    @Override
    public void looseGame() {

    }

    @Override
    public void stop() {
        playerController.stop();
    }

    @Override
    public void key_SPACE() {
        System.out.println("KEY SPACE BALL THROWN");
        ballController.throwAway();
    }

    @Override
    public void key_N() {

    }

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
    public void ballDrop(){

    }
}
