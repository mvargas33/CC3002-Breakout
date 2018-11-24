package logic.level;

/**
 * Clase NullLevel. Corresponde con los valores default 'nulos' de un Nivel vacío.
 * Correspondiente con el 'Null Pattern' visto en clases.
 *
 * @author Maximiliano Vargas
 */
public class NullLevel extends AbstractLevel{

    /**
     * Constructor: Crea un nivel nulo con los valores default del padre.
     * Setea el siguiente nivel como sí mismo, ya que es la cola de la lista de niveles.
     */
    public NullLevel(){
        super();
        super.setNextLevel(this);
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
     * Retorna el nivel a añadir, ya que NullLevel es el último en la lista.
     *
     * @param level nivel a añadir
     */
    @Override
    public Level addPlayingLevel(Level level) {
        return level;
    }

    
}
