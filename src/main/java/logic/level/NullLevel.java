package logic.level;

import logic.brick.*;

import java.util.*;

/**
 * Clase NullLevel. Corresponde con los valores default 'nulos' de un Nivel vacío.
 * Correspondiente con el 'Null Pattern' visto en clases.
 *
 * @author Maximiliano Vargas
 */
public class NullLevel implements Level{
    private Level nextLevel;                // Nivel siguiente

    /**
     * Constructor: Crea un nivel nulo con los valores default del padre.
     * Setea el siguiente nivel como sí mismo, ya que es la cola de la lista de niveles.
     */
    public NullLevel(){
        this.setNextLevel(this);
    }

    /**
     * Retorna false, un NullLevel no es jugable
     *
     * @return false
     */
    @Override
    public boolean isPlayableLevel() {
        return false;
    }

    /**
     * Retorna false, un NullLevel no tiene un viel siguiente
     *
     * @return false
     */
    @Override
    public boolean hasNextLevel() {
        return false;
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
     * Retorna el nivel a añadir, ya que NullLevel es el último en la lista.
     *
     * @param level nivel a añadir
     */
    @Override
    public Level addPlayingLevel(Level level) {
        return level;
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

    /**
     * Añade un obbserver a la lista de obserevers del nivel
     *
     * @param o : Observer a agregar
     */
    @Override
    public void addRealObserver(Observer o) {

    }

    /**
     * Suma a 'obtainablePoins' los puntos específicados.
     * Utilizado en el constructor de RealLevel.
     *
     * @param points : puntos a sumar
     */
    @Override
    public void sumToObtainablePoints(int points) {}

    /**
     * Suma a 'currentPonts' los puntos específicados.
     * Utilizado al destruirse un bloque en las visitas a los bloques.
     *
     * @param points : puntos a sumar
     */
    @Override
    public void sumToCurrentPoints(int points) {}

    /**
     * Impone la lista de Bricks sobre el nivel
     *
     * @param listaBricks : lista de Bricks a imponer
     */
    @Override
    public void setLevelBricks(ArrayList<Brick> listaBricks) {}

    /**
     * Impone el nombre a utilizar por un Nivel
     *
     * @param name : nombre a imponer
     */
    @Override
    public void setName(String name){}

    /**
     * Retorna true si el último Brick destruido en el nivel fue de metal.
     * Utilizado en la notificación de Level a Game para ver condición de bolas extra
     *
     * @return variable 'lastBrickWasMetal' del nivel
     */
    @Override
    public boolean isLastBrickMetal() {
        return false;
    }

    /**
     * Declara con 'valor' la variable 'lastBrickWasMetal' que guarda si el último birck destruido fue de metal.
     *
     * @param valor : valor a setear 'lastBrickWasMetal'
     */
    @Override
    public void setLastBrickWasMetal(boolean valor) {}

    /**
     * Retorna los puntos ganados en un nivel y sólo los de ese nivel.
     * Siempre menor o igual a los obtenibles. Los que calcula getPoints()
     *
     * @return los puntos ganados hasta el momento en un nivel.
     */
    @Override
    public int getCurrentPoints() {
        return 0;
    }

    /**
     * Gets the level's name. Each level must have a name.
     *
     * @return the table's name
     */
    @Override
    public String getName() {
        return "";
    }

    /**
     * Gets the number of {@link Brick} in the level.
     *
     * @return the number of Bricks in the level
     */
    @Override
    public int getNumberOfBricks() {
        return 0;
    }

    /**
     * Gets the {@link List} of {@link Brick}s in the level.
     *
     * @return the bricks in the level
     */
    @Override
    public List<Brick> getBricks() {
        return new ArrayList<>();
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
     * This method is called whenever the observed object is changed. An
     * application calls an <tt>Observable</tt> object's
     * <code>notifyObservers</code> method to have all the object's
     * observers notified of the change.
     *
     * @param o   the observable object.
     * @param arg an argument passed to the <code>notifyObservers</code>
     */
    @Override
    public void update(Observable o, Object arg) {}

    /**
     * Visita a un Brick tipo Glass
     *
     * @param b GlassBrick a visitar
     */
    @Override
    public void visitGlassBrick(GlassBrick b) {}

    /**
     * Visita a un Brick tipo Metal
     *
     * @param b MetalBrick a visitar
     */
    @Override
    public void visitMetalBrick(MetalBrick b) {}

    /**
     * Visita a un Brick tipo Wooden
     *
     * @param b WoodenBrick a visitar
     */
    @Override
    public void visitWoodenBrick(WoodenBrick b) {}
}
