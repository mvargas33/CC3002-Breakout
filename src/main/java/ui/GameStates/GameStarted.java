package ui.GameStates;

import com.almasb.fxgl.app.GameApplication;
import ui.App;
import ui.EntityController;
import ui.GameFatory;

public class GameStarted extends AbstractState{

    public GameStarted(App game){
        super(game);
    }

    @Override
    public void moveRight() {
        super.moveRight();
        getBallController().right();
    }

    @Override
    public void moveLeft() {
        super.moveLeft();
        getBallController().left();
    }

    @Override
    public void stop() {
        super.stop();
        getBallController().stop();
    }

    @Override
    public void rightWall(){
        super.rightWall();
        getBallController().stop();
        getBallController().blockRight();
        getBallController().unblockLeft();
    }

    @Override
    public void leftWall(){
        super.leftWall();
        getBallController().stop();
        getBallController().blockLeft();
        getBallController().unblockRight();
    }

    @Override
    public void key_SPACE(){}

    @Override
    public void key_N(){
        getGame().setGameState(new StandBy(getGame()));
    }
}
