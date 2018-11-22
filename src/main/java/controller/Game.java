package controller;

import logic.brick.*;
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
public class Game implements Observer,Visitor{
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
        this.balls -= 1;
        if(balls == 0){
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
        this.globalPoints += currentLevel.getPoints();
        currentLevel = currentLevel.getNextLevel();
    }
    public int getGlobalPoints(){
        return this.globalPoints + this.getCurrentLevel().getPoints();
    }
    public boolean isGameOver(){
        return this.isGameOver;
    }
    public void setCurrentLevel(Level level){
        this.currentLevel = level;
    }
    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof RealLevel){
            this.balls += 1;
        }

    }

    @Override
    public void visitNullLevel(NullLevel l) {

    }

    @Override
    public void visitRealLevel(RealLevel l) {

    }

    @Override
    public void visitGlassBrick(GlassBrick b) {

    }

    @Override
    public void visitMetalBrick(MetalBrick b) {

    }

    @Override
    public void visitWoodenBrick(WoodenBrick b) {

    }
}
