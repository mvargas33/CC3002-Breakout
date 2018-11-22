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
    private int currentLevel;
    private ArrayList<Level> levels;

    public Game(int balls) {
        this.balls = balls;
        this.winner = false;
        this.isGameOver = false;
        this.levels = new ArrayList<>();this.levels.add(new NullLevel());
        this.currentLevel = 0;
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

    @Override
    public void update(Observable observable, Object o) {

    }
    public int numberOfBricks(){
        Level currentLevel = levels.get(this.currentLevel);
        return currentLevel.getNumberOfBricks();
    }

    public Level newLevelWithBricksNoMetal(String name, int numberOfBricks, double probOfGlass, int seed){
        ArrayList<Brick> bricks = new ArrayList<Brick>();
        Random randomObject = new Random(seed);
        for(int i = 0;i < numberOfBricks;i++){
            double randomNumber = randomObject.nextDouble();
            if(randomNumber < probOfGlass){
                bricks.add(new GlassBrick());
            }else{
                bricks.add(new WoodenBrick());
            }
        }
        return new RealLevel(name, bricks);
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
