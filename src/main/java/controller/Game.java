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
    private int globalPoint;

    public Game(int balls) {
        this.balls = balls;
        this.winner = false;
        this.isGameOver = false;
        this.currentLevel = new NullLevel();
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
    }

    public int numberOfBalls(){
        return this.balls;
    }

    public int numberOfBricks(){
        return this.currentLevel.getNumberOfBricks();
    }
    @Override
    public void update(Observable observable, Object o) {

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
