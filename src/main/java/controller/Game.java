package controller;

import logic.ball.BallController;
import logic.visitor.EmptyBallVisitor;

import java.util.Observable;
import java.util.Observer;
/**
 * Game logic controller class.
 *
 * @author Juan-Pablo Silva
 */
public class Game implements Observer{
    private boolean winner = false;
    private boolean isGameOver = false;
    private BallController ballController;
    private EmptyBallVisitor emptyBallVisitor;

    public Game(int balls) {
        this.ballController = new BallController();
        ballController.addObserver(this);
        this.emptyBallVisitor = new EmptyBallVisitor();
        ballController.addBall(3);


    }

    /**
     * This method is just an example. Change it or delete it at wish.
     * <p>
     * Checks whether the game has a winner or not
     *
     * @return true if the game has a winner, false otherwise
     */
    public boolean winner() {
        return this.winner;
    }

    public void addBall(){
        this.ballController.addBall(1);
    }

    public void deleteBall(){
        this.ballController.deleteBall();
    }

    public int numberOfBalls(){
        return this.ballController.getNumberOfBalls();
    }

    @Override
    public void update(Observable observable, Object o) {
        if( o instanceof BallController){
            boolean isEmpty = ((BallController) o).accept(this.emptyBallVisitor);
            if(isEmpty) {
                this.winner = false;
            }
        }
    }
}
