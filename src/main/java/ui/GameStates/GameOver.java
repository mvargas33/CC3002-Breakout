package ui.GameStates;

import com.almasb.fxgl.app.GameApplication;
import ui.App;

public class GameOver extends Playing{
    private boolean gameIsWon;

    public GameOver(App game, boolean gameIsWon){
        super(game);
        this.gameIsWon = gameIsWon;
    }

    @Override
    public void key_N(){}

    @Override
    public void key_SPACE(){}
}
