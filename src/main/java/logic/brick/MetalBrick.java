package logic.brick;

public class MetalBrick implements Brick{
    private int lives;
    private int points;

    public MetalBrick(){
        this.lives = 10;
        this.points = 0;
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
