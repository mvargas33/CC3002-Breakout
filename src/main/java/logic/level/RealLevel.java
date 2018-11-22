package logic.level;

import logic.brick.Brick;

import java.util.ArrayList;
import java.util.List;

public class RealLevel implements Level {
    private ArrayList<Brick> levelBricks;

    public RealLevel(){
        this.levelBricks = new ArrayList<>();
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public int getNumberOfBricks() {
        return 0;
    }

    @Override
    public List<Brick> getBricks() {
        return null;
    }

    @Override
    public Level getNextLevel() {
        return null;
    }

    @Override
    public boolean isPlayableLevel() {
        return true;
    }

    @Override
    public boolean hasNextLevel() {
        return false;
    }

    @Override
    public int getPoints() {
        return 0;
    }

    @Override
    public Level addPlayingLevel(Level level) {
        return null;
    }

    @Override
    public void setNextLevel(Level level) {

    }
}
