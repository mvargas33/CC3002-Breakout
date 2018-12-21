package ui;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;
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

import java.util.*;

import static ui.GameFatory.*;

public class App extends GameApplication {
    private int width = 1100;
    private int heigth = 700;
    private int nivelNumero = 1;
    private HashMap<Entity, Brick> actualLevelBricks;
    private HomeworkTwoFacade facade;
    private State gameState;

    public int getAppWidth(){
        return width;
    }

    public int getAppHeigth(){
        return heigth;
    }

    public void setGameState(State newstate){
        this.gameState = newstate;
    }

    public HomeworkTwoFacade getFacade(){
        return facade;
    }
    public void setFacade(HomeworkTwoFacade f){ facade = f;}

    public int getNivelNumero(){
        return nivelNumero;
    }

    public void setNivelNumero(int numero){
        this.nivelNumero = numero;
    }

    public void setActualLevelBricks(HashMap<Entity, Brick> linkedBricks){
        actualLevelBricks = linkedBricks;
    }

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(width);
        gameSettings.setHeight(heigth);
        gameSettings.setTitle("One piece Breakout");
        gameSettings.setVersion("");
    }

    public static void main(String... args){
        launch(args);
    }

    @Override
    protected void initGame(){
        Entity player = newPlayer(width/2.0 - 75,630, new EntityController());    // Platform 150*30
        Entity ball = newBall(player.getX() + 70,player.getY() - 17, new EntityController());// Symbolic ball
        Entity bg = newBackground(width, heigth);                       // Background
        Entity walls = newWalls();                                      // Screen collidable walls
        getGameWorld().addEntity(walls);
        facade = new HomeworkTwoFacade();
        gameState = new GameNotStarted(this);
        updateBalls(facade.getBallsLeft(), width);
    }

    @Override
    protected void initInput(){
        Input input = getInput();

        input.addAction(new UserAction("New Level") {
            @Override
            protected void onActionBegin() {
                gameState.key_N();
                getGameState().increment("levels to play", +1);
                //getGameState().setValue("number of levels", facade.numberOfLevels());
            }
        }, KeyCode.N);

        input.addAction(new UserAction("Start level") {
            @Override
            protected void onAction() {
                gameState.key_SPACE();
            }
        }, KeyCode.SPACE);

        input.addAction(new UserAction("Move Right"){
            @Override
            protected void onAction(){
                gameState.moveRight();
            }
            @Override
            protected void onActionEnd() {
                gameState.stop();
            }
        }, KeyCode.D);

        input.addAction(new UserAction("Move Left"){
            @Override
            protected void onAction(){
                gameState.moveLeft();
            }
            @Override
            protected void onActionEnd() {
                gameState.stop();
            }
        }, KeyCode.A);

        input.addAction(new UserAction("Restart game") {
            @Override
            protected void onActionBegin() {
                gameState.restartGame();
            }
        }, KeyCode.R);
    }

    @Override
    protected void initPhysics(){
        getPhysicsWorld().setGravity(0,0.1f);

        getPhysicsWorld().addCollisionHandler(
                new CollisionHandler(Types.BALL, Types.WALL) {
                    @Override
                    protected void onHitBoxTrigger(Entity ball, Entity wall, HitBox boxBall, HitBox boxWall) {
                        if (boxWall.getName().equals("BOT")){
                            facade.dropBall();
                            ArrayList<Entity> b = (ArrayList<Entity>) getGameWorld().getEntitiesByType(Types.SYMBOLIC_BALL);
                            for(Entity balll : b){
                                balll.removeFromWorld();
                            }
                            updateBalls(facade.getBallsLeft(), width);
                            if(facade.isGameOver()){
                                gameState.looseGame();return;
                            }
                            gameState.ballDrop(ball);   // And add a new one
                        }
                    }
                }
        );
        getPhysicsWorld().addCollisionHandler(
                new CollisionHandler(Types.PLAYER, Types.WALL) {
                    @Override
                    protected void onHitBoxTrigger(Entity player, Entity wall, HitBox boxPlayer, HitBox boxWall) {
                        if (boxWall.getName().equals("RIGHT")){
                            gameState.rightWall();
                        }else if (boxWall.getName().equals("LEFT")){
                            gameState.leftWall();
                        }
                    }
                }
        );
        getPhysicsWorld().addCollisionHandler(
                new CollisionHandler(Types.BALL, Types.BRICK) {
                    @Override
                    protected void onCollision(Entity ball, Entity UIBrick) {
                        Brick b = actualLevelBricks.get(UIBrick);
                        b.hit();
                        if(b.isDestroyed()){
                            UIBrick.removeFromWorld();
                            getGameState().setValue("level score", facade.getCurrentLevel().getCurrentPoints());
                            getGameState().setValue("total score", facade.getCurrentPoints());
                            if(facade.winner()){
                                getGameState().increment("won levels", +1);
                                getGameState().increment("levels to play", -1);
                                gameState.winGame();
                            }else if(facade.getCurrentLevel().getCurrentPoints() == 0){  // Pasamos a otro nivel
                                gameState.goToNextLevel();
                                getGameState().increment("won levels", +1);
                                getGameState().increment("levels to play", -1);
                                getGameState().setValue("actual level", facade.getLevelName());
                            }
                        }
                    }
                }
        );

    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("total score", 0);         // Puntaje total
        vars.put("level score", 0);         // Puntaje del nivel actual
        vars.put("actual level", "Level 0");// Nombre del nivel actual
        vars.put("won levels", 0);          // Niveles ganados
        vars.put("levels to play", 0);      // Niveles por jugar
        vars.put("popup", "");
    }

    @Override
    protected void initUI() {
        // TOTAL SCORE
        Text totalScoreWord = getUIFactory().newText("TOTAL SCORE", Color.WHITE,  20);
        totalScoreWord.setTranslateX(32);
        totalScoreWord.setTranslateY(20);   // Arriba
        getGameScene().addUINodes(totalScoreWord);

        Text totalScore = getUIFactory().newText("7", Color.WHITE, 20);
        totalScore.setTranslateX(32 + 150);
        totalScore.setTranslateY(20);  // Arriba
        totalScore.textProperty().bind(getGameState().intProperty("total score").asString());
        getGameScene().addUINodes(totalScore);

        // LEVEL SCORE
        Text levelSocreWord = getUIFactory().newText("LEVEL SCORE", Color.WHITE,  20);
        levelSocreWord.setTranslateX(32);
        levelSocreWord.setTranslateY(20 +20);   // Arriba
        getGameScene().addUINodes(levelSocreWord);

        Text levelScore = getUIFactory().newText("7", Color.WHITE, 20);
        levelScore.setTranslateX(32 + 150);
        levelScore.setTranslateY(20 + 20);  // Abajo
        levelScore.textProperty().bind(getGameState().intProperty("level score").asString());
        getGameScene().addUINodes(levelScore);

        // LEVEL NAME
        Text level = getUIFactory().newText("7", Color.WHITE, 20);
        level.setTranslateX(32 + 120 + 20 + 120 + 20);
        level.setTranslateY(20);
        level.textProperty().bind(getGameState().stringProperty("actual level"));
        getGameScene().addUINodes(level);

        // WON LEVELS
        Text wonLevelsWord = getUIFactory().newText("WON LEVELS", Color.WHITE,  20);
        wonLevelsWord.setTranslateX(32 + 120 + 20 + 120 + 20 + 120);
        wonLevelsWord.setTranslateY(20);   // Arriba
        getGameScene().addUINodes(wonLevelsWord);

        Text wonLevels = getUIFactory().newText("7", Color.WHITE, 20);
        wonLevels.setTranslateX(32 + 120 + 20 + 120 + 20 + 120 + 150);
        wonLevels.setTranslateY(20);  // Arriba
        wonLevels.textProperty().bind(getGameState().intProperty("won levels").asString());
        getGameScene().addUINodes(wonLevels);

        // LEVELS TO PLAY
        Text levelsLeftWord = getUIFactory().newText("LEVELS LEFT", Color.WHITE,  20);
        levelsLeftWord.setTranslateX(32 + 120 + 20 + 120 + 20 + 120);
        levelsLeftWord.setTranslateY(20 + 20);   // Arriba
        getGameScene().addUINodes(levelsLeftWord);

        Text levelsLeft = getUIFactory().newText("7", Color.WHITE, 20);
        levelsLeft.setTranslateX(32 + 120 + 20 + 120 + 20 + 120 + 150);
        levelsLeft.setTranslateY(20 + 20);  // Abajo
        levelsLeft.textProperty().bind(getGameState().intProperty("levels to play").asString());
        getGameScene().addUINodes(levelsLeft);

        // PUPUP MESSAGE
        Text popUpWord = getUIFactory().newText("GAME OVER", Color.WHITE,  100);
        popUpWord.setTranslateX(250);
        popUpWord.setTranslateY(350);
        popUpWord.textProperty().bind(getGameState().stringProperty("popup"));
        getGameScene().addUINodes(popUpWord);
    }

}
