package logic.brick;

import java.util.Observable;

/**
 * Clase abstracta de Bricks, contiene métodos comunes entre los distintos tipos de Bricks
 * y un constructor abstracto en comnún. Implementa los métodos declarados en la clase abstracta Brick.
 * Los bricks extienden de Observable, son observados por niveles, utilizan los métodos de Observable.
 * Esto corresponde al 'Observer Pattern' visto en clases.
 *
 * @author Maximiliano Vargas
 */
public abstract class AbstractBrick extends Observable implements Brick {
    private int lives;          // Vidas
    private int score;          // Score
    private boolean isAlive;    // Está vivo?

    /**
     * Constructor: Crea un brick abstracto con una cantidad de vida y puntos entregados
     *
     * @param lives: Vidas del Brick
     * @param points: Score que da el Brcik al ser destruido
     */
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

    /**
     * Retorna true si el brick es tipo glass
     * @return  true si es brick de galss
     */
    @Override
    public boolean isGlassBrick(){
        return false;
    }

    /**
     * Retorna true si el brick es tipo wooden
     * @return  true si es brick de wooden
     */
    @Override
    public boolean isWoodenBrick(){
        return false;
    }

    /**
     * Retorna true si el brick es tipo metal
     * @return  true si es brick de metal
     */
    @Override
    public boolean isMetalBrick(){
        return false;
    }
}
