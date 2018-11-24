package controller;

import logic.brick.*;
import logic.level.AbstractLevel;
import logic.level.Level;
import logic.level.NullLevel;
import logic.level.RealLevel;
import logic.visitor.*;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

/**
 * Game logic controller class.
 *
 * @author Maximiliano Vargas
 */
public class Game implements Observer{
    private int balls;
    private boolean winner;
    private boolean isGameOver;
    private Level currentLevel;
    private int globalPoints;

    public Game(int balls) {
        this.balls = balls;
        this.winner = false;
        this.isGameOver = false;
        this.currentLevel = new NullLevel();
        this.globalPoints = 0;
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
        this.balls += 1;
    }

    public void deleteBall(){
        if(balls > 0){
            this.balls -= 1;
            if(balls == 0)
                this.isGameOver = true;
        }
    }

    public int numberOfBalls(){
        return this.balls;
    }

    public Level getCurrentLevel(){
        return this.currentLevel;
    }
    public void goToNextLevel(){
        setCurrentLevel(currentLevel.getNextLevel());
    }
    public int getGlobalPoints(){
        return this.globalPoints + this.getCurrentLevel().getCurrentPoints();
    }
    public boolean isGameOver(){
        return this.isGameOver;
    }
    public void setCurrentLevel(Level level){
        this.globalPoints += currentLevel.getCurrentPoints();
        this.currentLevel = level;
        ((AbstractLevel) level).addObserver(this);
    }
    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof RealLevel){
            if(((RealLevel) observable).getCurrentPoints() == ((RealLevel) observable).getPoints()){
                if(!currentLevel.getNextLevel().isPlayableLevel()) {
                    this.winner = true;     // Hay ganador
                    this.isGameOver = true; // Termina el juego
                }
                this.goToNextLevel();
                return;
            }
            if(((RealLevel) observable).isLastBrickMetal()) {
                ((RealLevel) observable).setLastBrickWasMetal(false);
                this.addBall();
            }
        }

    }

}
