package logic.level;

import java.util.ArrayList;
import java.util.Random;

public class LevelController {
    private ArrayList<Level> allGameLevels;

    LevelController(){
        this.allGameLevels = new ArrayList<>();
    }

    public Level newLevelWithBricksNoMetal(String name, int numberOfBricks, double probOfGlass, int seed){
        Level newLevel = new RealLevel();
        Random randomObject = new Random(seed);
        double random;
        return null;
    }



}
