package logic.level;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NullLevelTest {
    private NullLevel nullLevel;

    @Before
    public void setUp(){
        this.nullLevel = new NullLevel();
    }

    @Test
    public void initialConditions(){
        assertFalse(nullLevel.hasNextLevel());
        assertFalse(nullLevel.isPlayableLevel());
        assertEquals(nullLevel, nullLevel.getNextLevel());
        assertEquals("", nullLevel.getName());
        assertEquals(0, nullLevel.getCurrentPoints());
        assertEquals(0, nullLevel.getPoints());
        assertEquals(0, nullLevel.getNumberOfBricks());
        assertFalse(nullLevel.isLastBrickMetal());
    }

    @Test
    public void addPlayingLevelTest(){
        Level l = new RealLevel("Nivel 1", 100, 0.5, 0);
        assertEquals(l, nullLevel.addPlayingLevel(l));
    }
}