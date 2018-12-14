package ui.GameStates;

import com.almasb.fxgl.app.GameApplication;
import ui.App;

public class StandBy extends GameStarted{

    public StandBy(App game){
        super(game);
    }

    @Override
    public void key_SPACE(){
        super.key_SPACE();
        getGame().setGameState(new Playing(getGame()));
    }
}
