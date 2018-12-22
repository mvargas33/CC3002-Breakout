package ui.GameStates;

import com.almasb.fxgl.entity.Entity;

/**
 * Interfaz base para el State Pattern implemntado para las acciones del jugador. Para evitar cometer acciones bajo
 * distintos estados de juego, por ejemplo, evitar añadir niveles si el juego está perdido/ganado, o evitar lanzar
 * una bola si ya hay una en juego.
 *
 * @author Maximiliano Vargas
 */
public interface State {

    void moveRight();

    void moveLeft();

    void stop();

    void goToNextLevel(Entity deadBall);

    void winGame();

    void looseGame();

    void key_SPACE();

    void key_N();

    void rightWall();

    void leftWall();

    void ballDrop(Entity deadBall);

    double genNumberOfBricks();

    void restartGame();
}
