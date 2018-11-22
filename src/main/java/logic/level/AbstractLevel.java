package logic.level;

import logic.brick.Brick;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import logic.brick.GlassBrick;
import logic.brick.MetalBrick;
import logic.brick.WoodenBrick;
import logic.visitor.*;

public abstract class AbstractLevel extends Observable implements Level{
    private int goalPoints;         // Puntos minimos para pasar (destruir madera y glass)
    private int obtainablePoins;    // Puntos máximos obtenidobles (destuir madera, galass, metal)
    private int currentPonts;       // Puntos actuales
    private String name;
    private ArrayList<Brick> levelBricks;
    private Level nextLevel;


    public AbstractLevel(){
        this.goalPoints = 0;
        this.obtainablePoins = 0;
        this.currentPonts = 0;
        this.name = "";
        this. levelBricks = new ArrayList<>();
    }

    public void sumToGoalPoints(int points){
        this.goalPoints += points;
    }
    public void sumToObtainablePoints(int points){
        this.obtainablePoins += points;
    }
    public void sumToCurrentPoints(int points){
        this.currentPonts += points;
    }
    public void setLevelBricks(ArrayList<Brick> listaBricks){
        this.levelBricks = listaBricks;
    }
    public void setName(String name){
        this.name = name;
    }

    /**
     * Gets the level's name. Each level must have a name.
     *
     * @return the table's name
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Gets the number of {@link Brick} in the level.
     *
     * @return the number of Bricks in the level
     */
    @Override
    public int getNumberOfBricks() {
        return this.levelBricks.size();
    }

    /**
     * Gets the {@link List} of {@link Brick}s in the level.
     *
     * @return the bricks in the level
     */
    @Override
    public List<Brick> getBricks() {
        return levelBricks;
    }

    /**
     * Gets the next level of a level object. Each level have a reference to the next level to play.
     *
     * @return the next level
     */
    @Override
    public Level getNextLevel() {
        return this.nextLevel;
    }

    /**
     * Gets the total number of points obtainable in level.
     *
     * @return the number of points in the current level
     */
    @Override
    public int getPoints() {
        return this.obtainablePoins;
    }

    /**
     * Adds a level to the list of levels. This adds the level in the last position of the list.
     *
     * @param level the level to be added
     */
    @Override
    public Level addPlayingLevel(Level level) {
        this.nextLevel = this.nextLevel.addPlayingLevel(level);
        return this;
    }

    /**
     * Sets the reference for the next level of a level object.
     *
     * @param level the next level of a level object
     */
    @Override
    public void setNextLevel(Level level) {
        this.nextLevel = level;
    }

    @Override
    public void accept(Visitor v) {

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
