package logic.level;

import logic.brick.AbstractBrick;
import logic.brick.Brick;
import logic.visitor.Visitor;

import java.util.ArrayList;
import java.util.List;

public class NullLevel extends AbstractLevel{

    public NullLevel(){
        super();
        super.setNextLevel(this);
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

    /**
     * Adds a level to the list of levels. This adds the level in the last position of the list.
     *
     * @param level the level to be added
     */
    @Override
    public Level addPlayingLevel(Level level) {
        return level;
    }

    @Override
    public void accept(Visitor v) {
        v.visitNullLevel(this);
    }
}
