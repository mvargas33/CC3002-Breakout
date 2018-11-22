package logic.brick;
import logic.visitor.Visitable;
import logic.visitor.Visitor;

import java.util.Observer;
import java.util.Observable;

public abstract class AbstractBrick extends Observable implements Brick {
    private int lives;
    private int score;
    private boolean isAlive;

    public AbstractBrick(int lives, int points){
        this.lives = lives;
        this.score = points;
        this.isAlive = true;
    }

    /**
     * Defines that a brick has been hit.
     * Implementations should consider the events that a hit to a brick can trigger.
     */
    @Override
    public void hit() {
        if(lives > 0) {
            this.lives -= 1;
            if(lives == 0) {
                this.isAlive = false;
                setChanged();
                notifyObservers();
            }
        }
    }

    /**
     * Gets whether the brick object is destroyed or not.
     *
     * @return true if the brick is destroyed, false otherwise
     */
    @Override
    public boolean isDestroyed() {
        return !this.isAlive;
    }

    /**
     * Gets the points corresponding to the destroying of a brick object.
     *
     * @return the associated points of a brick object
     */
    @Override
    public int getScore() {
        return this.score;
    }

    /**
     * Gets the remaining hits the brick has to receive before being destroyed.
     *
     * @return the remaining hits to destroy de brick
     */
    @Override
    public int remainingHits() {
        return this.lives;
    }

}
