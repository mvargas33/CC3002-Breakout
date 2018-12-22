package ui.GameStates;

import java.util.Random;

import ui.App;

import static ui.App.linkBricks;

/**
 * Estado base del juego, con el nivel vacío. Sólo se puede pasar al estado de StandBy si se preciona la tecla N.
 * SPACE no hace lanzar la bola, debe haber al menos un nivel antes.
 *
 * @author Maximiliano Vargas
 */
public class GameNotStarted extends StandBy{

    public GameNotStarted(App game){
        super(game);
    }

    @Override
    public void key_SPACE(){}

    @Override
    public void key_N(){
        double nBricks = super.genNumberOfBricks();
        getGame().getFacade().setCurrentLevel(getGame().getFacade().newLevelWithBricksFull("Level " + getGame().getNivelNumero(), (int)nBricks, new Random().nextDouble(), nBricks/100, 0));
        getGame().setActualLevelBricks(linkBricks(getGame().getFacade().getBricks()));
        getGame().getGameState().setValue("actual level", getGame().getFacade().getLevelName());
        getGame().setGameState(new StandBy(getGame())); // Go to StandBy State
    }
}
