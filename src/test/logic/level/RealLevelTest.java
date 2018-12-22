package logic.level;

import logic.brick.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class RealLevelTest {
    private Level glassLevel;
    private Level woodenLevel;
    private Level glassMetalLevel;
    private Level woodenMetalLevel;
    private Level glassWoodenMetalLevel;

    public void destroyBrick(Brick brick){
        for(int i = 0; i < 10; i++){
            brick.hit();
        }
    }

    public void destroyAllBricks(Level level){
        List<Brick> bricks = level.getBricks();
        for(Brick b : bricks){
            destroyBrick(b);
        }
    }

    @Before
    public void setUp(){
        glassLevel = new RealLevel("Glass Level",100,1,0);
        woodenLevel = new RealLevel("Wooden Level",100,0,0);
        glassMetalLevel = new RealLevel("Glass-Metal Level", 100, 1, 1,0);
        woodenMetalLevel = new RealLevel("Wooden-Metal Level", 100,0,1,0);
        glassWoodenMetalLevel = new RealLevel("Glass-Wooden_Metal Level", 100,0.5,1, 0);
    }

    @Test
    public void initialConditions(){
        ArrayList<Level> levels = new ArrayList<>();
        levels.add(glassLevel);
        levels.add(woodenLevel);
        levels.add(glassMetalLevel);
        levels.add(woodenMetalLevel);
        levels.add(glassWoodenMetalLevel);
        for(Level l : levels){
            assertTrue(l.getNumberOfBricks() >= 100);
            assertFalse(l.hasNextLevel());
            assertTrue(l.isPlayableLevel());
            assertTrue(l.getPoints() >= 50*100); // All glass
            assertEquals(0, l.getCurrentPoints());
        }
    }

    @Test
    public void glassLevelTest(){
        assertEquals(100, glassLevel.getNumberOfBricks());
        glassLevel.getBricks().forEach(b -> assertTrue(b.isGlassBrick()));
        assertEquals(50*100, (new GlassBrick().getScore())*glassLevel.getNumberOfBricks());
        assertEquals( (new GlassBrick().getScore())*glassLevel.getNumberOfBricks(), glassLevel.getPoints());
        destroyBrick(glassLevel.getBricks().get(0));
        assertEquals(50, glassLevel.getCurrentPoints());
        assertEquals(99, glassLevel.getNumberOfBricks());
        destroyAllBricks(glassLevel);
        assertEquals(50*100, glassLevel.getCurrentPoints());
        assertEquals(0, glassLevel.getNumberOfBricks());
    }

    @Test
    public void woodenLevelTest(){
        assertEquals(100, woodenLevel.getNumberOfBricks());
        woodenLevel.getBricks().forEach(b -> assertTrue(b.isWoodenBrick()));
        assertEquals(200*100, (new WoodenBrick().getScore())*woodenLevel.getNumberOfBricks());
        assertEquals( (new WoodenBrick().getScore())*woodenLevel.getNumberOfBricks(), woodenLevel.getPoints());
        destroyBrick(woodenLevel.getBricks().get(0));
        assertEquals(200, woodenLevel.getCurrentPoints());
        assertEquals(99, woodenLevel.getNumberOfBricks());
        destroyAllBricks(woodenLevel);
        assertEquals(200*100, woodenLevel.getCurrentPoints());
        assertEquals(0, woodenLevel.getNumberOfBricks());
    }

    @Test
    public void glassMetalLevelTest(){
        assertEquals(200, glassMetalLevel.getNumberOfBricks());
        glassMetalLevel.getBricks().forEach(b -> assertTrue(b.isGlassBrick() || b.isMetalBrick()));
        assertEquals(50*100, 50*glassMetalLevel.getNumberOfBricks()/2);
        assertEquals(50*100, glassMetalLevel.getPoints());
        destroyBrick(glassMetalLevel.getBricks().get(0));
        assertEquals(50, glassMetalLevel.getCurrentPoints());
        assertEquals(199, glassMetalLevel.getNumberOfBricks());
        destroyAllBricks(glassMetalLevel);
        assertEquals(50*100, glassMetalLevel.getCurrentPoints());
        assertEquals(0, glassMetalLevel.getNumberOfBricks());
    }

    @Test
    public void woodenMetalLevelTest(){
        assertEquals(200, woodenMetalLevel.getNumberOfBricks());
        woodenMetalLevel.getBricks().forEach(b -> assertTrue(b.isWoodenBrick() || b.isMetalBrick()));
        assertEquals(200*100, 200*woodenMetalLevel.getNumberOfBricks()/2);
        assertEquals(200*100, woodenMetalLevel.getPoints());
        destroyBrick(woodenMetalLevel.getBricks().get(0));
        assertEquals(200, woodenMetalLevel.getCurrentPoints());
        assertEquals(199, woodenMetalLevel.getNumberOfBricks());
        destroyAllBricks(woodenMetalLevel);
        assertEquals(200*100, woodenMetalLevel.getCurrentPoints());
        assertEquals(0, woodenMetalLevel.getNumberOfBricks());
    }

    @Test
    public void glassWoodenMetalLevelTest(){
        assertEquals(200, glassWoodenMetalLevel.getNumberOfBricks());
        glassWoodenMetalLevel.getBricks().forEach(b -> assertTrue(b.isGlassBrick() || b.isMetalBrick()|| b.isWoodenBrick()));
        assertTrue(glassWoodenMetalLevel.getPoints() >= 200*50);
        assertTrue(glassWoodenMetalLevel.getPoints() <= 200*100);
        destroyBrick(glassWoodenMetalLevel.getBricks().get(0));
        assertTrue(glassWoodenMetalLevel.getCurrentPoints() > 0);
        assertEquals(199, glassWoodenMetalLevel.getNumberOfBricks());
        destroyAllBricks(glassWoodenMetalLevel);
        assertTrue(glassWoodenMetalLevel.getCurrentPoints() >= 50*100);     // Todos son glass
        assertTrue(glassWoodenMetalLevel.getCurrentPoints() <= 200*100);    // Todos son wooden
        assertEquals(0, glassWoodenMetalLevel.getNumberOfBricks());
    }


}