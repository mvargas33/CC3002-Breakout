package logic.visitor;

import logic.brick.*;

/**
 * Clase Visitor: Se definen los métodos a implementar por parte de todos los Visitors
 * Para efectos del juego, sólo los Bricks son Visitables.
 * Correspondiente con el patrón de diseño 'Visitor Pattern' visto en clases.
 *
 * @author Maximiliano Vargas
 */
public interface Visitor {
    /**
     * Visita a un Brick tipo Glass
     * @param b GlassBrick a visitar
     */
    void visitGlassBrick(GlassBrick b);

    /**
     * Visita a un Brick tipo Metal
     * @param b MetalBrick a visitar
     */
    void visitMetalBrick(MetalBrick b);

    /**
     * Visita a un Brick tipo Wooden
     * @param b WoodenBrick a visitar
     */
    void visitWoodenBrick(WoodenBrick b);
}
