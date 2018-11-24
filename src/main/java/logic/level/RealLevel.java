package logic.level;

import logic.brick.*;

import java.util.ArrayList;
import java.util.Random;

public class RealLevel extends AbstractLevel {
    /**
     * Constructor de un RealLevel. Crea un nivl con GlassBrick, MetalBrick y WoodenBrick basado en probabilidades.
     * 'numberOfBricks' especifica los bricks entre wood y glass. Los bricks de metal se agregan como extra.
     * Parte como base en el constructor padre. Luego cambia parámetros.
     *
     * @param name Nombre del nivel a crear
     * @param numberOfBricks número de Bricks totales entre glass y wooden
     * @param probOfGlass probabilidad de general bricks de glass
     * @param probOfMetal probabilidad de general bricks de metal
     * @param seed parámetro para utilizar correctamente la clase Random
     */
    public RealLevel(String name, int numberOfBricks, double probOfGlass, double probOfMetal, int seed) {
        super();
        ArrayList<Brick> bricks = new ArrayList<>();
        Random randomObject = new Random(seed);
        for(int i = 0;i < numberOfBricks;i++){
            double randomNumber = randomObject.nextDouble();
            if(randomNumber < probOfGlass){
                GlassBrick glassBrick = new GlassBrick();
                bricks.add(glassBrick);
                super.sumToObtainablePoints(glassBrick.getScore());
                glassBrick.addObserver(this);
            }else{
                WoodenBrick woodenBrick = new WoodenBrick();
                bricks.add(woodenBrick);
                super.sumToObtainablePoints(woodenBrick.getScore());
                woodenBrick.addObserver(this);
            }
        }
        for(int j = 0; j < numberOfBricks;j++){
            double randomNumber = randomObject.nextDouble();
            if(randomNumber < probOfMetal){
                MetalBrick metalBrick = new MetalBrick();
                bricks.add(metalBrick);
                super.sumToObtainablePoints(metalBrick.getScore()); // Inútil ahora, pero útil para escalabilidad
                metalBrick.addObserver(this);
            }
        }
        super.setName(name);
        super.setLevelBricks(bricks);
        super.setNextLevel(new NullLevel());
    }

    /**
     * Constructor de un RealLevel. Crea un nivl con GlassBrick y WoodenBrick basado en probabilidades.
     * 'numberOfBricks' especifica los bricks entre wood y glass.
     * Parte como base en el constructor padre. Luego cambia parámetros.
     *
     * @param name Nombre del nivel a crear
     * @param numberOfBricks número de Bricks totales entre glass y wooden
     * @param probOfGlass probabilidad de general bricks de glass
     * @param seed parámetro para utilizar correctamente la clase Random
     */
    public RealLevel(String name, int numberOfBricks, double probOfGlass, int seed){
        super();
        ArrayList<Brick> bricks = new ArrayList<>();
        Random randomObject = new Random(seed);
        for(int i = 0;i < numberOfBricks;i++){
            double randomNumber = randomObject.nextDouble();
            if(randomNumber < probOfGlass){
                GlassBrick glassBrick = new GlassBrick();
                bricks.add(glassBrick);
                super.sumToObtainablePoints(glassBrick.getScore());
                glassBrick.addObserver(this);
            }else{
                WoodenBrick woodenBrick = new WoodenBrick();
                bricks.add(woodenBrick);
                super.sumToObtainablePoints(woodenBrick.getScore());
                woodenBrick.addObserver(this);
            }
        }
        super.setName(name);
        super.setLevelBricks(bricks);
        super.setNextLevel(new NullLevel());
    }

    /**
     * Un RealLevel es siempre jugable
     *
     * @return true
     */
    @Override
    public boolean isPlayableLevel() {
        return true;
    }

    /**
     * Retorna true si el nivel siguiente nivel es jugable
     *
     * @return true si el nivel siguiente es jugable
     */
    @Override
    public boolean hasNextLevel() {
        return super.getNextLevel().isPlayableLevel();
    }

}
