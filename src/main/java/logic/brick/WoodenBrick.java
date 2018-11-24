package logic.brick;

import logic.visitor.Visitor;

/**
 * Clase Wooden: Se identifica por el número de vidas y score. Puede ser visitado por un nivel
 *
 * @author Maximiliano Vargas
 */
public class WoodenBrick extends AbstractBrick {

    /**
     * Constructor: Crea un WoodenBrick con los parámetros dichos por el enunciado
     */
    public WoodenBrick(){
        super(3, 200);
    }

    /**
     * Double Dispatch: El Brick es visitado por un Visitor (Nivel) y le dice
     * qué método usar en específico
     * @param v: Visitor (Nivel)
     */
    @Override
    public void accept(Visitor v) {
        v.visitWoodenBrick(this);
    }

}
