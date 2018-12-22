package logic.brick;

import logic.visitor.Visitor;

/**
 * Clase GlassBrick: Se identifica por el número de vidas y score. Puede ser visitado por un nivel
 *
 * @author Maximiliano Vargas
 */
public class GlassBrick extends AbstractBrick {

    /**
     * Constructor: Crea un GlassBrick con los parámetros dichos por el enunciado
     */
    public GlassBrick(){
        super(1, 50);
    }

    /**
     * Double Dispatch: El Brick es visitado por un Visitor (Nivel) y le dice
     * qué método usar en específico
     * @param v: Visitor (Nivel)
     */
    @Override
    public void accept(Visitor v) {
        v.visitGlassBrick(this);
    }

    /**
     * Retorna true, de utilidad para interfaz gráfica
     * @return  true
     */
    @Override
    public boolean isGlassBrick(){
        return true;
    }
}
