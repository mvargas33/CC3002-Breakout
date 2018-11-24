package controller;

import logic.brick.Brick;
import org.junit.Before;
import org.junit.Test;
import logic.level.*;

import java.util.List;

import static org.junit.Assert.*;

public class GameTest {
    private Game game;
    private Level glassMetalLevel;
    private Level woodenLevel;


    public void destroyBrick(Brick brick){
        for(int i = 0; i < 10; i++){
            brick.hit();
        }
    }
    // Destruye del final al inicio, para destruir metales primero
    public void destroyAllBricks(Level level, int hastaIncluido){
        List<Brick> bricks = level.getBricks();
        for(int i = hastaIncluido; i >= 0; i--){
            destroyBrick(bricks.get(i));
        }
    }

    @Before
    public void setUp(){
        this.game = new Game(3);
        glassMetalLevel = new RealLevel("Glass-Metal Level",100,1,1, 0);
        woodenLevel = new RealLevel("Wooden Level", 100,0,0,0);
    }

    @Test
    public void newGameTest(){
        assertFalse(game.isGameOver());
        assertFalse(game.winner());
        assertFalse(game.getCurrentLevel().isPlayableLevel());
        assertEquals(game.getGlobalPoints(), 0);
        assertFalse(game.getCurrentLevel().hasNextLevel());
    }

    @Test
    public void ballsDynamics(){
        assertEquals(game.numberOfBalls(), 3);
        game.addBall();
        assertEquals(game.numberOfBalls(), 4);
        game.deleteBall();
        assertEquals(game.numberOfBalls(), 3);
        game.deleteBall();
        game.deleteBall();
        game.deleteBall();
        assertEquals(game.numberOfBalls(), 0);
        assertTrue(game.isGameOver());
        assertFalse(game.winner());
        game.deleteBall();
        assertEquals(game.numberOfBalls(), 0);
    }

    @Test
    public void oneLevelTest(){
        Level current = game.getCurrentLevel();
        game.goToNextLevel();
        assertEquals(game.getCurrentLevel(), current);
        // Insert level
        Level newLevel = new RealLevel("Nivel 1", 100, 0.5, 0.5, 0);
        game.setCurrentLevel(newLevel);
        assertNotEquals(game.getCurrentLevel(), current);
        assertEquals(game.getCurrentLevel(), newLevel);
        assertTrue(game.getCurrentLevel().isPlayableLevel());
        assertFalse(game.getCurrentLevel().hasNextLevel());
        game.goToNextLevel();
        assertFalse(game.getCurrentLevel().isPlayableLevel());
        assertFalse(game.getCurrentLevel().hasNextLevel());
    }

    @Test
    public void concatenationLevelTest(){
        Level level1 = new RealLevel("Nivel 1", 100, 0.5, 0.5, 0);
        Level level2 = new RealLevel("Nivel 2", 100, 0.5, 0.5, 0);
        Level level3 = new RealLevel("Nivel 3", 100, 0.5, 0.5, 0);
        level1.addPlayingLevel(level2);
        level1.addPlayingLevel(level3);

        game.setCurrentLevel(level1);
        assertEquals(game.getCurrentLevel().getName(),"Nivel 1");
        assertTrue(game.getCurrentLevel().isPlayableLevel());
        assertTrue(game.getCurrentLevel().hasNextLevel());
        assertEquals(game.getCurrentLevel().getNextLevel().getName(), "Nivel 2");
        assertEquals(game.getGlobalPoints(), 0);

        game.goToNextLevel();
        assertEquals(game.getCurrentLevel().getName(),"Nivel 2");
        assertTrue(game.getCurrentLevel().isPlayableLevel());
        assertTrue(game.getCurrentLevel().hasNextLevel());
        assertEquals(game.getCurrentLevel().getNextLevel().getName(), "Nivel 3");
        assertEquals(game.getGlobalPoints(), 0);

        game.goToNextLevel();
        assertEquals(game.getCurrentLevel().getName(),"Nivel 3");
        assertTrue(game.getCurrentLevel().isPlayableLevel());
        assertFalse(game.getCurrentLevel().hasNextLevel());
        assertEquals(game.getCurrentLevel().getNextLevel().getName(), "");
        assertEquals(game.getGlobalPoints(), 0);
    }

    @Test
    public void extraLiveTest(){
        assertEquals(3, game.numberOfBalls());
        game.setCurrentLevel(glassMetalLevel);
        assertEquals(glassMetalLevel, game.getCurrentLevel());
        assertEquals(0, game.getGlobalPoints());
        assertFalse(game.winner());
        assertFalse(game.isGameOver());
        int prevPoints = game.getGlobalPoints();
        destroyBrick(glassMetalLevel.getBricks().get(100));   // A metal Brick
        assertEquals(4, game.numberOfBalls());
        assertEquals(prevPoints, game.getGlobalPoints());
        assertEquals(glassMetalLevel, game.getCurrentLevel());
        assertFalse(game.winner());
        assertFalse(game.isGameOver());
    }

    @Test
    public void winWithoutTouchingMetalTest(){
        assertEquals(3, game.numberOfBalls());
        game.setCurrentLevel(glassMetalLevel);
        assertEquals(glassMetalLevel, game.getCurrentLevel());
        assertTrue(game.getCurrentLevel().isPlayableLevel());
        assertEquals(0, game.getGlobalPoints());
        destroyAllBricks(game.getCurrentLevel(), 99);   // Destruir todos los glass
        assertEquals(50*100, game.getGlobalPoints());
        assertNotEquals(game.getCurrentLevel(), glassMetalLevel);   // Pasa al nivel nulo
        assertFalse(game.getCurrentLevel().isPlayableLevel());
        assertFalse(game.getCurrentLevel().hasNextLevel());
        assertTrue(game.winner());                                  // Hay ganador
        assertTrue(game.isGameOver());                              // Terminó el juego
        assertEquals(3, game.numberOfBalls());              // No se destruyeron metales
    }

    @Test
    public void winTouchingAllMetalTest(){
        assertEquals(3, game.numberOfBalls());
        game.setCurrentLevel(glassMetalLevel);
        assertEquals(glassMetalLevel, game.getCurrentLevel());
        assertTrue(game.getCurrentLevel().isPlayableLevel());
        assertEquals(0, game.getGlobalPoints());
        destroyAllBricks(game.getCurrentLevel(), 199);   // Destruir todos los glass
        assertEquals(50*100, game.getGlobalPoints());
        assertNotEquals(game.getCurrentLevel(), glassMetalLevel);   // Pasa al nivel nulo
        assertFalse(game.getCurrentLevel().isPlayableLevel());
        assertFalse(game.getCurrentLevel().hasNextLevel());
        assertTrue(game.winner());                                  // Hay ganador
        assertTrue(game.isGameOver());                              // Terminó el juego
        assertEquals(3 + 100, game.numberOfBalls());        // Se destruyeron 100 metales
    }

    @Test
    public void winPassingTwoLevels(){
        assertEquals(3, game.numberOfBalls());
        game.setCurrentLevel(glassMetalLevel);
        assertEquals(glassMetalLevel, game.getCurrentLevel());
        assertTrue(game.getCurrentLevel().isPlayableLevel());
        assertEquals(0, game.getGlobalPoints());
        game.getCurrentLevel().addPlayingLevel(woodenLevel);
        assertTrue(game.getCurrentLevel().hasNextLevel());
        // Win level 1
        destroyAllBricks(game.getCurrentLevel(), 99);   // Destruir todos los glass
        assertEquals(50*100, game.getGlobalPoints());
        assertEquals(woodenLevel, game.getCurrentLevel());          // Pasa al siguiente nivel
        assertTrue(game.getCurrentLevel().isPlayableLevel());
        assertEquals(3, game.numberOfBalls());              // No se ganaron bolas extra

        // Attack one wooden
        destroyBrick(game.getCurrentLevel().getBricks().get(99));
        assertEquals(50*100 + 200, game.getGlobalPoints());
        assertEquals(3, game.numberOfBalls());

        // Win level 2
        destroyAllBricks(game.getCurrentLevel(), 98);   // Destruir todos los glass
        assertEquals(50*100 + 200*100, game.getGlobalPoints()); // Se suman ahora los wooden
        assertNotEquals(game.getCurrentLevel(), woodenLevel);       // Pasa al nivel nulo
        assertFalse(game.getCurrentLevel().isPlayableLevel());
        assertFalse(game.getCurrentLevel().hasNextLevel());
        assertTrue(game.winner());                                  // Hay ganador
        assertTrue(game.isGameOver());                              // Terminó el juego
    }


}