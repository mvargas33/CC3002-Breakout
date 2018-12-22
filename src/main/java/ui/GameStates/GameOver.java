package ui.GameStates;

import facade.HomeworkTwoFacade;

import com.almasb.fxgl.entity.Entity;
import ui.*;
import static ui.GameFactory.*;

/**
 * Estado de juego terminado. Evita hacer algunas acciones y muestra un mensaje popUp si el juego está ganado o perdido.
 * Informa al jugador que con la tecla R se reinicia el juego. El método más importante d esta clase es reiniciar el juego.
 *
 * @author Maximiliano Vargas
 */
public class GameOver extends Playing{

    public GameOver(App game, boolean gameIsWon){
        super(game);
        if(gameIsWon){
            getGame().getGameState().setValue("popup", "   YOU WIN!");
            getGame().getGameState().setValue("restart", "PRESS 'R' TO RESTART");
        }else{
            getGame().getGameState().setValue("popup", "GAME OVER");
            getGame().getGameState().setValue("restart", "PRESS 'R' TO RESTART");
        }
    }

    @Override
    public void key_N(){}

    @Override
    public void key_SPACE(){}

    @Override
    public void restartGame() {
        // Delete Old Entities
        getGame().getGameWorld().getEntitiesByType(GameFactory.Types.SYMBOLIC_BALL).forEach(Entity::removeFromWorld);
        getGame().getGameWorld().getEntitiesByType(GameFactory.Types.BALL).forEach(Entity::removeFromWorld);
        getGame().getGameWorld().getEntitiesByType(GameFactory.Types.BRICK).forEach(Entity::removeFromWorld);
        getGame().getGameWorld().getEntitiesByType(GameFactory.Types.WALL).forEach(Entity::removeFromWorld);
        getGame().getGameWorld().getEntitiesByType(GameFactory.Types.PLAYER).forEach(Entity::removeFromWorld);
        // Reset counters and messages
        getGame().getGameState().setValue("total score",getGame().getFacade().getCurrentPoints());
        getGame().getGameState().setValue("level score",getGame().getFacade().getCurrentLevel().getCurrentPoints());
        getGame().getGameState().setValue("actual level","Level 0");
        getGame().getGameState().setValue("won levels",0);
        getGame().getGameState().setValue("levels to play",0);
        getGame().getGameState().setValue("popup", "");
        getGame().getGameState().setValue("restart", "");
        // Reset logic
        getGame().setFacade(new HomeworkTwoFacade());
        // Add new Entities, update and go to GameNotStarted State
        Entity player = newPlayer(getGame().getWidth()/2.0 - 75,630, new EntityController());// Platform 150*30
        Entity ball = newBall(player.getX() + 70,player.getY() - 17, new EntityController());// Symbolic ball
        Entity walls = newWalls();
        getGame().getGameWorld().addEntity(walls);
        getGame().updateBalls(getGame().getFacade().getBallsLeft(), getGame().getAppWidth());
        getGame().setGameState(new GameNotStarted(getGame()));
    }
}
