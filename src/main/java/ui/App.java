package ui;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.settings.GameSettings;
import facade.HomeworkTwoFacade;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import logic.brick.*;
import ui.GameStates.*;

import java.util.*;

import static ui.GameFactory.*;

/**
 * Clase principal del juego. Se debe ejecutar el main de esta clase para iniciar el Juego.
 * Se encarga de incializar la visualización en ventana, colocar entidades gráficas y correr los textos y contadores del juego.
 * Además contiene todos los Handlers de las acciones introducidas por el jugador, que básicamente es el accionamiento de teclas.
 * Relaciona la lógica del juego con la interfaz gráfica implementada. Su funcionamiento implementa el Factory Pattern para la
 * creación de entidades gráficas. El Facade Pattern para la interacción con la lógica del juego. Y el State Pattern, para
 * la administración de los distintos estados del juego (basado en el accionamiento de teclas).
 *
 * @author Maximiliano Vargas
 */
public class App extends GameApplication {
    private int width = 1100;
    private int heigth = 700;
    private int nivelNumero = 1;
    private HashMap<Entity, Brick> actualLevelBricks;   // Bind de Bricks lógicos con Bricks gráficos
    private HomeworkTwoFacade facade;                   // Lógica del juego
    private State gameState;                            // Estado del juego

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

    /**
     * Recibe una lista Bricks lógicos y retorna un Bind con Bricks gráficos en forma de HashMap
     *
     * @param bricks Bricks lógicos
     * @return  HashMap de BricksGráficos,BricksLógicos
     */
    public static HashMap<Entity, Brick> linkBricks(List<Brick> bricks){
        Collections.shuffle(bricks);
        HashMap<Entity, Brick> map = new HashMap<>();
        int i = 0;
        int j = 1; // Empty row at top
        for(Brick brick : bricks){
            if (i%10 == 0){
                j++;i=0;
            }
            if(brick.isGlassBrick()){
                Entity glassEntity = newBrick(104*i + 32, 34*j, Color.RED, "ice.png");
                map.put(glassEntity, brick);
            }else if(brick.isMetalBrick()){
                Entity metalEntity = newBrick(104*i + 32, 34*j, Color.DARKGREY, "poneglyph.png");
                map.put(metalEntity, brick);
            }else if(brick.isWoodenBrick()){
                Entity woodenEntity = newBrick(104*i + 32, 34*j, Color.GREENYELLOW, "fire.png");
                map.put(woodenEntity, brick);
            }
            i++;
        }
        return map;
    }

    /**
     * Realiza todas las acciones lógicas y gráficas para golpear a un Brick
     *
     * @param UIBrick Brick gráfico a golpear
     */
    private void hitUIBrick(Entity UIBrick){
        Brick b = actualLevelBricks.get(UIBrick);
        b.hit();
        if(b.isDestroyed()){
            getAudioPlayer().playSound("destroy.wav");
            UIBrick.removeFromWorld();
            getGameState().setValue("level score", facade.getCurrentLevel().getCurrentPoints());
            getGameState().setValue("total score", facade.getCurrentPoints());
            updateBalls(facade.getBallsLeft(), width);
            if(facade.winner()){
                getGameState().increment("won levels", +1);
                getGameState().increment("levels to play", -1);
                gameState.winGame();
            }else if(facade.getCurrentLevel().getCurrentPoints() == 0){  // Pasamos a otro nivel
                gameState.goToNextLevel(getGameWorld().getEntitiesByType(GameFactory.Types.BALL).get(0));
                getGameState().increment("won levels", +1);
                getGameState().increment("levels to play", -1);
                getGameState().setValue("actual level", facade.getLevelName());
            }
            return;
        }
        /**
         * Feature mayor: Estado distinto del Brick al golpearse
         * Feature menor: Sonido al golpe de los Bricks
         */
        if(b.isMetalBrick()){
            if(b.remainingHits() == 7){
                UIBrick.setViewFromTexture("poneglyph-1.png");
            }else if(b.remainingHits() == 5){
                UIBrick.setViewFromTexture("poneglyph-2.png");
            }else if(b.remainingHits() == 3){
                UIBrick.setViewFromTexture("poneglyph-3.png");
            }
            getAudioPlayer().playSound("metal.wav");
        }else if(b.isWoodenBrick()){
            if(b.remainingHits() == 2){
                UIBrick.setViewFromTexture("fire-1.png");
            }else if(b.remainingHits() == 1){
                UIBrick.setViewFromTexture("fire-2.png");
            }
            getAudioPlayer().playSound("wood.wav");
        }else if(b.isGlassBrick()){
            getAudioPlayer().playSound("glass.wav");
        }
    }

    /**
     * Feature menor: Hace update de la visualización de vidas en forma gráfica.
     *
     * @param cuantity Cantidad de vidas a setear
     * @param width Ancho de la pantalla
     */
    public void updateBalls(int cuantity, int width){
        getGameWorld().getEntitiesByType(Types.SYMBOLIC_BALL).forEach(Entity::removeFromWorld);
        int i = 0;
        int j = -1;
        for(int q = 0; q < cuantity; q++){
            if(i%15 == 0){
                j++;i=0;
            }
            if (j == 2){    // Sólo se pueden dibujar hasta 60 balls
                break;
            }
            Entity symbolicBall = newSymbolicBall(width - 32 - 25 + 25*i, 5 + 25*j);
            i--;
        }
        getGameState().setValue("lives", facade.getBallsLeft());
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
        getGameState().setValue("lives", facade.getBallsLeft());
    }

    @Override
    protected void initInput(){
        Input input = getInput();
        /**
         * Feature mayor: Romper Bricks manteniendo presionado mouse para testear ui.
         */
        input.addAction(new UserAction("Delete brick") {
            @Override
            protected void onAction() {
                Entity e = getGameWorld().selectedEntityProperty().get();
                if (e != null && e.isType(Types.BRICK)) {
                    hitUIBrick(e);
                }
            }
        }, MouseButton.PRIMARY);

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
        getPhysicsWorld().setGravity(0.1f,0.1f);

        getPhysicsWorld().addCollisionHandler(
                new CollisionHandler(Types.BALL, Types.WALL) {
                    @Override
                    protected void onHitBoxTrigger(Entity ball, Entity wall, HitBox boxBall, HitBox boxWall) {
                        if (boxWall.getName().equals("BOT")){
                            facade.dropBall();
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
                            getPhysicsWorld().setGravity(-0.1f,0.1f);   // Para evitar bugs
                        }else if (boxWall.getName().equals("LEFT")){
                            gameState.leftWall();
                            getPhysicsWorld().setGravity(0.1f,0.1f);    // Para evitar bugs
                        }
                    }
                }
        );
        getPhysicsWorld().addCollisionHandler(
                new CollisionHandler(Types.PLAYER, Types.BALL) {
                    @Override
                    protected void onCollisionBegin(Entity a, Entity b) {
                        getAudioPlayer().playSound("bounce.wav");
                    }
                }
        );
        getPhysicsWorld().addCollisionHandler(
                new CollisionHandler(Types.BALL, Types.BRICK) {
                    @Override
                    protected void onCollisionBegin(Entity ball, Entity UIBrick) {
                        hitUIBrick(UIBrick);
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
        vars.put("popup", "");              // Popup message
        vars.put("restart","");             // Restart popup
        vars.put("lives", 0);               // Lives
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
        level.setTranslateX(32 + 120 + 20 + 100);
        level.setTranslateY(20);
        level.textProperty().bind(getGameState().stringProperty("actual level"));
        getGameScene().addUINodes(level);

        // WON LEVELS
        Text wonLevelsWord = getUIFactory().newText("WON LEVELS", Color.WHITE,  20);
        wonLevelsWord.setTranslateX(32 + 120 + 20 + 120 + 20 + 50);
        wonLevelsWord.setTranslateY(20);   // Arriba
        getGameScene().addUINodes(wonLevelsWord);

        Text wonLevels = getUIFactory().newText("7", Color.WHITE, 20);
        wonLevels.setTranslateX(32 + 120 + 20 + 120 + 20 + 190);
        wonLevels.setTranslateY(20);  // Arriba
        wonLevels.textProperty().bind(getGameState().intProperty("won levels").asString());
        getGameScene().addUINodes(wonLevels);

        // LEVELS TO PLAY
        Text levelsLeftWord = getUIFactory().newText("LEVELS LEFT", Color.WHITE,  20);
        levelsLeftWord.setTranslateX(32 + 120 + 20 + 120 + 20 + 50);
        levelsLeftWord.setTranslateY(20 + 20);   // Arriba
        getGameScene().addUINodes(levelsLeftWord);

        Text levelsLeft = getUIFactory().newText("7", Color.WHITE, 20);
        levelsLeft.setTranslateX(32 + 120 + 20 + 120 + 20 + 190);
        levelsLeft.setTranslateY(20 + 20);  // Abajo
        levelsLeft.textProperty().bind(getGameState().intProperty("levels to play").asString());
        getGameScene().addUINodes(levelsLeft);

        // PUPUP MESSAGES
        Text popUpWord = getUIFactory().newText("GAME OVER", Color.WHITE,  100);
        popUpWord.setTranslateX(250);
        popUpWord.setTranslateY(350);
        popUpWord.textProperty().bind(getGameState().stringProperty("popup"));
        getGameScene().addUINodes(popUpWord);

        Text popUpRestartWord = getUIFactory().newText("PRESS 'R' TO RESTART", Color.WHITE,  30);
        popUpRestartWord.setTranslateX(250 + 140);
        popUpRestartWord.setTranslateY(350 + 50);
        popUpRestartWord.textProperty().bind(getGameState().stringProperty("restart"));
        getGameScene().addUINodes(popUpRestartWord);

        // LIVES
        Text livesText = getUIFactory().newText("LIVES", Color.WHITE,  20);
        livesText.setTranslateX(32 + 120 + 20 + 120 + 20 + 250);
        livesText.setTranslateY(20);   // Arriba
        getGameScene().addUINodes(livesText);

        Text lives = getUIFactory().newText("7", Color.WHITE, 20);
        lives.setTranslateX(32 + 120 + 20 + 120 + 20 + 250 + 75);
        lives.setTranslateY(20);
        lives.textProperty().bind(getGameState().intProperty("lives").asString());
        getGameScene().addUINodes(lives);
    }

}
