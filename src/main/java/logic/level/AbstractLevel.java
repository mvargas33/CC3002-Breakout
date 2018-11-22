package logic.level;

import logic.brick.Brick;
import java.util.ArrayList;
import java.util.List;

import logic.visitor.*;

public class AbstractLevel implements Level{
    private String name;
    private ArrayList<Brick> levelBricks;
    private Level nextLevel;
    private int goalPoints;         // Puntos minimos para pasar (destruir madera y glass)
    private int obtainablePoins;    // Puntos máximos obtenidobles (destuir madera, galass, metal)
    private int currentPonts;       // Puntos actuales

    public AbstractLevel(String name){
        this.name = name;
        this.currentPonts = 0;

    }
    public int setGoalPoints(){
        for(Brick b : this.levelBricks){

        }
    }
    public int setObtainablePoints(){

    }
    public int setLevelBricks(ArrayList<Brick> listaBricks){
        this.levelBricks = listaBricks;
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

        return 0;
    }

    /**
     * Adds a level to the list of levels. This adds the level in the last position of the list.
     *
     * @param level the level to be added
     */
    @Override
    public Level addPlayingLevel(Level level) {
        return null;
    }

    /**
     * Sets the reference for the next level of a level object.
     *
     * @param level the next level of a level object
     */
    @Override
    public void setNextLevel(Level level) {

    }
}
