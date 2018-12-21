package logic.level;

import logic.brick.*;
import logic.visitor.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

/**
 * Interface that represents the basics of a level to be played on.
 *
 * @author Juan-Pablo Silva
 */
public interface Level extends Visitor,Observer {
    /**
     * Retorna los puntos ganados en un nivel y sólo los de ese nivel.
     * Siempre menor o igual a los obtenibles. Los que calcula getPoints()
     *
     * @return los puntos ganados hasta el momento en un nivel.
     */
    int getCurrentPoints();

    /**
     * Gets the level's name. Each level must have a name.
     *
     * @return the table's name
     */
    String getName();

    /**
     * Gets the number of {@link Brick} in the level.
     *
     * @return the number of Bricks in the level
     */
    int getNumberOfBricks();

    /**
     * Gets the {@link List} of {@link Brick}s in the level.
     *
     * @return the bricks in the level
     */
    List<Brick> getBricks();

    /**
     * Gets the next level of a level object. Each level have a reference to the next level to play.
     *
     * @return the next level
     */
    Level getNextLevel();

    /**
     * Gets whether the level is playable or not.
     *
     * @return true if the level is playable, false otherwise
     */
    boolean isPlayableLevel();

    /**
     * Whether the level's next level is playable or not.
     *
     * @return true if the level's next level is playable, false otherwise
     */
    boolean hasNextLevel();

    /**
     * Gets the total number of points obtainable in level.
     *
     * @return the number of points in the current level
     */
    int getPoints();

    /**
     * Adds a level to the list of levels. This adds the level in the last position of the list.
     *
     * @param level the level to be added
     */
    Level addPlayingLevel(Level level);

    /**
     * Sets the reference for the next level of a level object.
     *
     * @param level the next level of a level object
     */
    void setNextLevel(Level level);

    /* Metodos adicionales */

    /**
     * Añade un obbserver a la lista de obserevers del nivel
     * @param o : Observer a agregar
     */
    void addRealObserver(Observer o);

    /**
     * Suma a 'obtainablePoins' los puntos específicados.
     * Utilizado en el constructor de RealLevel.
     * @param points: puntos a sumar
     */
    void sumToObtainablePoints(int points);

    /**
     * Suma a 'currentPonts' los puntos específicados.
     * Utilizado al destruirse un bloque en las visitas a los bloques.
     * @param points: puntos a sumar
     */
    void sumToCurrentPoints(int points);

    /**
     * Impone la lista de Bricks sobre el nivel
     * @param listaBricks: lista de Bricks a imponer
     */
    void setLevelBricks(ArrayList<Brick> listaBricks);

    /**
     * Impone el nombre a utilizar por un Nivel
     * @param name: nombre a imponer
     */
    void setName(String name);

    /**
     * Retorna true si el último Brick destruido en el nivel fue de metal.
     * Utilizado en la notificación de Level a Game para ver condición de bolas extra
     * @return variable 'lastBrickWasMetal' del nivel
     */
    boolean isLastBrickMetal();

    /**
     * Declara con 'valor' la variable 'lastBrickWasMetal' que guarda si el último birck destruido fue de metal.
     * @param valor: valor a setear 'lastBrickWasMetal'
     */
    void setLastBrickWasMetal(boolean valor);

}
