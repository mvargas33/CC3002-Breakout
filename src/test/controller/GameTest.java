package controller;

import org.junit.Before;
import org.junit.Test;
import logic.level.*;

import static org.junit.Assert.*;

public class GameTest {
    private Game game;
    @Before
    public void create(){
        this.game = new Game(3);
    }

    @Test
    public void newGameTest(){
        assertEquals(game.numberOfBalls(), 3);
        game.addBall();
        assertEquals(game.numberOfBalls(), 4);
        game.deleteBall();
        assertEquals(game.numberOfBalls(), 3);
        assertFalse(game.isGameOver());
        assertFalse(game.winner());
        assertFalse(game.getCurrentLevel().isPlayableLevel());
        assertEquals(game.getGlobalPoints(), 0);
        assertFalse(game.getCurrentLevel().hasNextLevel());
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
}