package ui.GameStates;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;
import facade.HomeworkTwoFacade;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import ui.App;
import ui.EntityController;
import ui.GameFatory;

import javax.lang.model.type.TypeKind;
import java.util.ArrayList;
import java.util.List;

import static ui.GameFatory.*;

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
        GameWorld gameWorld = getGame().getGameWorld();
        ArrayList<Entity> symbolicBalls = (ArrayList<Entity>) gameWorld.getEntitiesByType(GameFatory.Types.SYMBOLIC_BALL);
        for(Entity symbolicBall : symbolicBalls){
            symbolicBall.removeFromWorld();
        }
        ArrayList<Entity> balls = (ArrayList<Entity>) gameWorld.getEntitiesByType(GameFatory.Types.BALL);
        for(Entity ball : balls){
            ball.removeFromWorld();
        }
        ArrayList<Entity> bricks = (ArrayList<Entity>) gameWorld.getEntitiesByType(GameFatory.Types.BRICK);
        for(Entity brick : bricks){
            brick.removeFromWorld();
        }
        ArrayList<Entity> wallss = (ArrayList<Entity>) gameWorld.getEntitiesByType(GameFatory.Types.WALL);
        for(Entity wall : wallss){
            wall.removeFromWorld();
        }
        gameWorld.getEntitiesByType(GameFatory.Types.PLAYER).get(0).removeFromWorld();

        getGame().setFacade(new HomeworkTwoFacade());   // Reset logic
        getGame().getGameState().setValue("total score",getGame().getFacade().getCurrentPoints());
        getGame().getGameState().setValue("level score",getGame().getFacade().getCurrentLevel().getCurrentPoints());
        getGame().getGameState().setValue("actual level","Level 0");
        getGame().getGameState().setValue("won levels",0);
        getGame().getGameState().setValue("levels to play",0);
        getGame().getGameState().setValue("popup", "");
        getGame().getGameState().setValue("restart", "");


        Entity player = newPlayer(getGame().getWidth()/2.0 - 75,630, new EntityController());// Platform 150*30
        Entity ball = newBall(player.getX() + 70,player.getY() - 17, new EntityController());// Symbolic ball
        Entity walls = newWalls();
        getGame().getGameWorld().addEntity(walls);
        getGame().updateBalls(getGame().getFacade().getBallsLeft(), getGame().getAppWidth());
        getGame().setGameState(new GameNotStarted(getGame()));
    }
}
