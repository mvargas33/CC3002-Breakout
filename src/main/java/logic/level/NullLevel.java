package logic.level;

import logic.brick.AbstractBrick;
import logic.brick.Brick;

import java.util.ArrayList;
import java.util.List;

public class NullLevel extends AbstractLevel{

    public NullLevel(){
        super("");
        super.setLevelBricks(new ArrayList<>());
    }

    /**
     * Gets whether the level is playable or not.
     *
     * @return true if the level is playable, false otherwise
     */
    @Override
    public boolean isPlayableLevel() {
        return false;
    }

    /**
     * Whether the level's next level is playable or not.
     *
     * @return true if the level's next level is playable, false otherwise
     */
    @Override
    public boolean hasNextLevel() {
        return false;
    }

}
