package ui.GameStates;

import java.util.Random;

import com.almasb.fxgl.entity.Entity;
import ui.*;

import static ui.App.linkBricks;
import static ui.GameFactory.newBall;

/**
 * Estado principal y base del juego: Se implementan las funciones por default de las acciones de las teclas presionadas,
 * y de las acciones a realizar por los controladores de las entidades del juego.
 * Corresponde al estado de estar jugado con una bola en juego. Al perder una bola, ganar o perder el juego, se pasa a nuevo estado.
 *
 * @author Maximiliano Vargas
 */
public class Playing implements State{
    private App game;                           // Game al que pertenece
    private EntityController playerController;  // Player Controller
    private EntityController ballController;    // Ball Controller

    protected Playing(App g){
        game = g;
        playerController = getGame().getGameWorld().getEntitiesByType(GameFactory.Types.PLAYER).get(0).getComponent(EntityController.class);
        ballController = getGame().getGameWorld().getEntitiesByType(GameFactory.Types.BALL).get(0).getComponent(EntityController.class);
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
    public void setBallController(EntityController ec){ ballController = ec;}

    @Override
    public void moveRight() {
        playerController.right();
    }

    @Override
    public void moveLeft() {
        playerController.left();
    }

    @Override
    public void goToNextLevel(Entity deadBall) {
        getGame().getGameWorld().getEntitiesByType(GameFactory.Types.BRICK).forEach(Entity::removeFromWorld);   // Remove metal bricks
        getGame().setActualLevelBricks(linkBricks(getGame().getFacade().getBricks()));
        game.getGameState().setValue("actual level", game.getFacade().getLevelName());
        ballDrop(deadBall);
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
        Entity player = getGame().getGameWorld().getEntitiesByType(GameFactory.Types.PLAYER).get(0);
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
