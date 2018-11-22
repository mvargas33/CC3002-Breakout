package logic.level;

import logic.brick.Brick;
import logic.brick.GlassBrick;
import logic.brick.MetalBrick;
import logic.brick.WoodenBrick;
import logic.visitor.Visitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RealLevel extends AbstractLevel {

    public RealLevel(String name, int numberOfBricks, double probOfGlass, double probOfMetal, int seed) {
        super();
        ArrayList<Brick> bricks = new ArrayList<>();
        Random randomObject = new Random(seed);
        for(int i = 0;i < numberOfBricks;i++){
            double randomNumber = randomObject.nextDouble();
            if(randomNumber < probOfGlass){
                GlassBrick glassBrick = new GlassBrick();
                bricks.add(glassBrick);
                super.sumToGoalPoints(glassBrick.getScore());
                super.sumToObtainablePoints(glassBrick.getScore());
            }else{
                WoodenBrick woodenBrick = new WoodenBrick();
                bricks.add(woodenBrick);
                super.sumToGoalPoints(woodenBrick.getScore());
                super.sumToObtainablePoints(woodenBrick.getScore());
            }
        }
        for(int j = 0; j < numberOfBricks;j++){
            double randomNumber = randomObject.nextDouble();
            if(randomNumber < probOfMetal){
                MetalBrick metalBrick = new MetalBrick();
                bricks.add(metalBrick);
                super.sumToObtainablePoints(metalBrick.getScore());
            }
        }
        super.setName(name);
        super.setLevelBricks(bricks);
    }


    public RealLevel(String name, int numberOfBricks, double probOfGlass, int seed){
        super();
        ArrayList<Brick> bricks = new ArrayList<>();
        Random randomObject = new Random(seed);
        for(int i = 0;i < numberOfBricks;i++){
            double randomNumber = randomObject.nextDouble();
            if(randomNumber < probOfGlass){
                GlassBrick glassBrick = new GlassBrick();
                bricks.add(glassBrick);
                super.sumToGoalPoints(glassBrick.getScore());
                super.sumToObtainablePoints(glassBrick.getScore());
            }else{
                WoodenBrick woodenBrick = new WoodenBrick();
                bricks.add(woodenBrick);
                super.sumToGoalPoints(woodenBrick.getScore());
                super.sumToObtainablePoints(woodenBrick.getScore());
            }
        }
        super.setName(name);
        super.setLevelBricks(bricks);
    }

    /**
     * Gets whether the level is playable or not.
     *
     * @return true if the level is playable, false otherwise
     */
    @Override
    public boolean isPlayableLevel() {
        return true;
    }

    /**
     * Whether the level's next level is playable or not.
     *
     * @return true if the level's next level is playable, false otherwise
     */
    @Override
    public boolean hasNextLevel() {
        return super.getNextLevel().isPlayableLevel();
    }



    @Override
    public void accept(Visitor v) {
        v.visitRealLevel(this);
    }
}
