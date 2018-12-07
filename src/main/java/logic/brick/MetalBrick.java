package logic.brick;

import logic.visitor.Visitor;

/**
 * Clase MetalBrick: Se identifica por el número de vidas y score. Puede ser visitado por un nivel
 *
 * @author Maximiliano Vargas
 */
public class MetalBrick extends AbstractBrick{

    /**
     * Constructor: Crea un MetalBrick con los parámetros dichos por el enunciado
     */
    public MetalBrick(){
        super(10, 0);
    }

    /**
     * Double Dispatch: El Brick es visitado por un Visitor (Nivel) y le dice
     * qué método usar en específico
     * @param v: Visitor (Nivel)
     */
    @Override
    public void accept(Visitor v) {
        v.visitMetalBrick(this);
    }

    /**
     * Retorna true
     * @return  true
     */
    @Override
    public boolean isMetalBrick(){
        return true;
    }
}
