package ui.GameStates;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entity;
import facade.HomeworkTwoFacade;
import logic.brick.Brick;
import ui.App;
import ui.EntityController;
import ui.GameFatory;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static ui.GameFatory.linkBricks;

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
        getGame().setGameState(new StandBy(getGame())); // Go to StandBy State
    }
}
