package ui.GameStates;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.settings.GameSettings;
import facade.HomeworkTwoFacade;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import logic.brick.*;
import ui.GameStates.*;

public interface State {

    void moveRight();

    void moveLeft();

    void stop();

    void shootBall();

    void addLevel();

    void goToNextLevel();

    void winGame();

    void looseGame();

    void key_SPACE();

    void key_N();

    void rightWall();

    void leftWall();

    void ballDrop(Entity deadBall);

    double genNumberOfBricks();
}
