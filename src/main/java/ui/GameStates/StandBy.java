package ui.GameStates;

import ui.App;

/**
 * Corresponde al estado secundario de juego: Al haber perdido una bola, se pone una nueva bola encima y se dispara con SPACE
 * Básicamente se implementa para que la bola siga a la plataforma. Al lanzar la bola se pasa a un nuevo estado.
 *
 * @author Maximiliano Vargas
 */
public class StandBy extends Playing {

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
