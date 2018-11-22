package logic.brick;

import logic.level.AbstractLevel;

public class GlassBrick extends AbstractBrick {

    public GlassBrick(){
        super(1, 10);
    }

    @Override
    public void hit() {

    }

    @Override
    public boolean isDestroyed() {
        return false;
    }

    @Override
    public int getScore() {
        return 0;
    }

    @Override
    public int remainingHits() {
        return 0;
    }
}
