package logic.level;

import logic.brick.Brick;
import java.util.ArrayList;
import logic.visitor.*;

public abstract class AbstractLevel implements Level{
    private String name;
    private ArrayList<Brick> levelBricks;

    public AbstractLevel(String name, ArrayList<Brick> bricks){
        this.name = name;
        this.levelBricks = bricks;
    }

    public int numberOfBricks(){
        return this.levelBricks.size();
    }
}
