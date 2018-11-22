package logic.brick;

public class WoodenBrick implements Brick {
    private int lives;
    private int points;

    public WoodenBrick(){
        this.lives = 3;
        this.points = 200;
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
