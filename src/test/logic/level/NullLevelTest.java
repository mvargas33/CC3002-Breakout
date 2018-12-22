package logic.level;

import logic.brick.Brick;
import logic.brick.GlassBrick;
import logic.brick.MetalBrick;
import logic.brick.WoodenBrick;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

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
        nullLevel.sumToCurrentPoints(100);
        assertEquals(0, nullLevel.getCurrentPoints());
        nullLevel.sumToObtainablePoints(100);
        assertEquals(0, nullLevel.getPoints());
        ArrayList<Brick> bricks = new ArrayList<>();
        bricks.add(new GlassBrick());
        bricks.add(new WoodenBrick());
        bricks.add(new MetalBrick());
        nullLevel.setLevelBricks(bricks);
        assertEquals(0, nullLevel.getNumberOfBricks());
        assertEquals(0, nullLevel.getBricks().size());
        nullLevel.setName("Nombre de prueba");
        assertEquals("", nullLevel.getName());
        nullLevel.setLastBrickWasMetal(true);
        assertFalse(nullLevel.isLastBrickMetal());
    }

    @Test
    public void addPlayingLevelTest(){
        Level l = new RealLevel("Nivel 1", 100, 0.5, 0);
        assertEquals(l, nullLevel.addPlayingLevel(l));
    }
}