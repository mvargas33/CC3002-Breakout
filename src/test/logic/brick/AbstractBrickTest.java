package logic.brick;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AbstractBrickTest {
    private Brick glassBrick;
    private Brick woodenBrick;
    private Brick metalBrick;

    @Before
    public void setUp(){
        this.glassBrick = new GlassBrick();
        this.woodenBrick = new WoodenBrick();
        this.metalBrick = new MetalBrick();
    }

    @Test
    public void intialConditionTest(){
        assertFalse(glassBrick.isDestroyed());
        assertFalse(woodenBrick.isDestroyed());
        assertFalse(metalBrick.isDestroyed());
        assertEquals(glassBrick.getScore(), 50);
        assertEquals(woodenBrick.getScore(), 200);
        assertEquals(metalBrick.getScore(), 0);
        assertEquals(1, glassBrick.remainingHits());
        assertEquals(3, woodenBrick.remainingHits());
        assertEquals(10, metalBrick.remainingHits());
    }

    @Test
    public void destroyGlassBrickTest(){
        assertEquals(1, glassBrick.remainingHits());
        assertFalse(glassBrick.isDestroyed());
        glassBrick.hit();
        assertEquals(0, glassBrick.remainingHits());
        assertTrue(glassBrick.isDestroyed());
        glassBrick.hit();
        assertEquals(0, glassBrick.remainingHits());    // After dead
        assertTrue(glassBrick.isDestroyed());
    }

    @Test
    public void destroyWoodenBrickTest(){
        assertEquals(3, woodenBrick.remainingHits());
        assertFalse(woodenBrick.isDestroyed());
        woodenBrick.hit();
        assertEquals(2, woodenBrick.remainingHits());
        assertFalse(woodenBrick.isDestroyed());
        woodenBrick.hit();
        assertEquals(1, woodenBrick.remainingHits());
        assertFalse(woodenBrick.isDestroyed());
        woodenBrick.hit();
        assertEquals(0, woodenBrick.remainingHits());
        assertTrue(woodenBrick.isDestroyed());
        woodenBrick.hit();
        assertEquals(0, woodenBrick.remainingHits());   // After dead
        assertTrue(woodenBrick.isDestroyed());
    }

    @Test
    public void destroyMetalBrickTest(){
        assertEquals(10, metalBrick.remainingHits());
        assertFalse(metalBrick.isDestroyed());
        metalBrick.hit();
        assertEquals(9, metalBrick.remainingHits());
        assertFalse(metalBrick.isDestroyed());
        metalBrick.hit();
        assertEquals(8, metalBrick.remainingHits());
        assertFalse(metalBrick.isDestroyed());
        metalBrick.hit();
        assertEquals(7, metalBrick.remainingHits());
        assertFalse(metalBrick.isDestroyed());
        metalBrick.hit();
        assertEquals(6, metalBrick.remainingHits());
        assertFalse(metalBrick.isDestroyed());
        metalBrick.hit();
        assertEquals(5, metalBrick.remainingHits());
        assertFalse(metalBrick.isDestroyed());
        metalBrick.hit();
        assertEquals(4, metalBrick.remainingHits());
        assertFalse(metalBrick.isDestroyed());
        metalBrick.hit();
        assertEquals(3, metalBrick.remainingHits());
        assertFalse(metalBrick.isDestroyed());
        metalBrick.hit();
        assertEquals(2, metalBrick.remainingHits());
        assertFalse(metalBrick.isDestroyed());
        metalBrick.hit();
        assertEquals(1, metalBrick.remainingHits());
        assertFalse(metalBrick.isDestroyed());
        metalBrick.hit();
        assertEquals(0, metalBrick.remainingHits());
        assertTrue(metalBrick.isDestroyed());
        metalBrick.hit();
        assertEquals(0, metalBrick.remainingHits());    // After dead
        assertTrue(metalBrick.isDestroyed());
    }
}