package ui.GameStates;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entity;
import ui.App;
import ui.EntityController;
import ui.GameFatory;

import java.util.Random;

import static ui.GameFatory.linkBricks;
import static ui.GameFatory.newBall;

public class Playing implements State{
    private App game;
    private EntityController playerController;
    private EntityController ballController;

    protected Playing(App g){
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
    public void goToNextLevel() {
        getGame().getGameWorld().getEntitiesByType(GameFatory.Types.BRICK).forEach(e -> e.removeFromWorld());   // Remove metal bricks
        getGame().setActualLevelBricks(linkBricks(getGame().getFacade().getBricks()));
        game.getGameState().setValue("actual level", game.getFacade().getLevelName());
        getGame().setGameState(new StandBy(getGame())); // Go to StandBy State
    }

    @Override
    public void winGame() {
        playerController.stop();
        playerController.blockLeft();
        playerController.blockRight();
        ballController.stop();
        getGame().setGameState(new GameOver(getGame(), true));
    }

    @Override
    public void looseGame() {
        winGame();
        getGame().setGameState(new GameOver(getGame(), false));
    }

    @Override
    public void stop() {
        playerController.stop();
    }

    @Override
    public void key_SPACE(){}

    @Override
    public void key_N(){
        double nBricks = genNumberOfBricks();
        getGame().setNivelNumero(getGame().getNivelNumero() + 1);
        getGame().getFacade().addPlayingLevel(getGame().getFacade().newLevelWithBricksFull("Level " + getGame().getNivelNumero(), (int)nBricks, new Random().nextDouble(), nBricks/100, 0));
    }
    @Override
    public void rightWall(){
        playerController.stop();
        playerController.blockRight();
        playerController.unblockLeft();
    }

    @Override
    public void leftWall(){
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

    @Override
    public void restartGame() {

    }

}
