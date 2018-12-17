package controller;

import logic.level.*;

import java.util.Observable;
import java.util.Observer;


/**
 * Clase Game. Es el controlador del juego y administrador de bolas. Decide cuando pasar de nivel,
 * cuando el juego está ganado o perdido. También administra el puntaje entre otras cosas.
 * Game implementa Observer ya que Observa el currentLevel quien notifica a Game.
 * Esto corresponde al 'Observer Pattern' visto en clases.
 *
 * @author Maximiliano Vargas
 */
public class Game implements Observer{
    private int balls;          // Número de bolas
    private boolean winner;     // true si hay ganador
    private boolean isGameOver; // true si el juego está terminado
    private Level currentLevel; // Nivel actual
    private int globalPoints;   // Puntos globales hasta antes del nivel actual

    /**
     * Constructor: Se crea un juego con el número de bolas específicado
     * y los parámetros por default de un juego.
     * @param balls número de bolas iniciales
     */
    public Game(int balls) {
        this.balls = balls;
        this.winner = false;
        this.isGameOver = false;
        this.currentLevel = new NullLevel();
        this.globalPoints = 0;
    }

    /**
     * Retorna true si el juego está ganado
     * @return true si el juego está ganado
     */
    public boolean winner() {
        return this.winner;
    }

    /**
     * Añade una bola al juego
     */
    public void addBall(){
        this.balls += 1;
    }

    /**
     * Borra una bola del juego.
     * También verifica si quedan bolas o no y si el juego está perdido.
     */
    public void deleteBall(){
        if(balls > 0){
            this.balls -= 1;
            if(balls == 0)
                this.isGameOver = true;
        }
    }

    /**
     * Retorna el número de bolas del juego
     * @return número de bolas
     */
    public int numberOfBalls(){
        return this.balls;
    }

    /**
     * Retorna el nivel actual del juego
     * @return el nivel actual
     */
    public Level getCurrentLevel(){
        return this.currentLevel;
    }

    /**
     * Pasa al siguiente nivel independiente si es jugable o no
     */
    public void goToNextLevel(){
        setCurrentLevel(currentLevel.getNextLevel());
    }

    /**
     * Retorna los puntos globales del juego, los acumulados + los del nivel actual
     * @return puntos totales
     */
    public int getGlobalPoints(){
        return this.globalPoints + this.getCurrentLevel().getCurrentPoints();
    }

    /**
     * Retorna true si el juego está terminado
     * @return true si el juego está terminado
     */
    public boolean isGameOver(){
        return this.isGameOver;
    }

    /**
     * Setea el nivel actual por otro. Antes de hacerlo rescata los puntos del nivel actual.
     * @param level nivel a pasar
     */
    public void setCurrentLevel(Level level){
        this.globalPoints += currentLevel.getCurrentPoints();
        this.currentLevel = level;
        ((RealLevel) level).addObserver(this);
    }

    /**
     * Se ejecuta cuando currentLevel notifica a Game. Puede ser por varios motivos:
     * 1- El nivel está ganado por puntaje
     * 2- Se destruyó un bloque de metal y hay que restar una bola
     *
     * @param observable quien notifica
     * @param o objeto que pasa el observable (null para efectos del juego)
     */
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
