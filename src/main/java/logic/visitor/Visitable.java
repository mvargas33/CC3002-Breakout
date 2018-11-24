package logic.visitor;

/**
 * Clase abstracta que impone la implementación del método accept() a las clases que la implementen.
 * Correspondiente con el patrón de diseño 'Visitor Pattern' visto en clases.
 *
 * @author Maximiliano Vargas
 */
public interface Visitable {
    /**
     * Método que acepta un visitor y le dice qué método usar en específico
     * @param v: Visitor que visita a una clase tipo Visitable
     */
    void accept(Visitor v);
}
