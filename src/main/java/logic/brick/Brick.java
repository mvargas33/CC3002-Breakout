package logic.brick;

import logic.visitor.Visitable;

/**
 * Interfaz que representa todas las acciones a implementar por un Brick.
 *
 * @author Maximiliano Vargas
 */
public interface Brick extends Visitable{
    /**
     * Defines that a brick has been hit.
     * Implementations should consider the events that a hit to a brick can trigger.
     */
    void hit();

    /**
     * Gets whether the brick object is destroyed or not.
     *
     * @return true if the brick is destroyed, false otherwise
     */
    boolean isDestroyed();

    /**
     * Gets the points corresponding to the destroying of a brick object.
     *
     * @return the associated points of a brick object
     */
    int getScore();

    /**
     * Gets the remaining hits the brick has to receive before being destroyed.
     *
     * @return the remaining hits to destroy de brick
     */
    int remainingHits();

    /**
     * Retorna true si el brick es tipo glass
     * @return  true si es brick de galss
     */
    boolean isGlassBrick();

    /**
     * Retorna true si el brick es tipo wooden
     * @return  true si es brick de wooden
     */
    boolean isWoodenBrick();

    /**
     * Retorna true si el brick es tipo metal
     * @return  true si es brick de metal
     */
    boolean isMetalBrick();

}
