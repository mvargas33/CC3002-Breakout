package ui.GameStates;

import facade.HomeworkTwoFacade;
import logic.brick.Brick;
import ui.App;

import java.util.List;
import java.util.Random;

import static ui.GameFatory.linkBricks;

public class StandBy extends AbstractState {

    public StandBy(App game){
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
    public void key_SPACE(){
        getBallController().throwAway();
        getGame().setGameState(new Playing(getGame()));
    }


}
