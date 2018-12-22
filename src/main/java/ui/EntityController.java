package ui;

import java.util.Random;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.PositionComponent;
import com.almasb.fxgl.entity.components.ViewComponent;
import com.almasb.fxgl.physics.PhysicsComponent;

/**
 * Controlador del movimiento gráfico de las Entidades (Bola y Plataforma(Player)).
 * Cambia las velocidades de los objetos físicos e impide que salgan fuera de los límites de la pantalla
 *
 * @author Maximiliano Vargas
 */
public class EntityController extends Component {
    private ViewComponent view;
    private PhysicsComponent physics;
    private PositionComponent position;
    private boolean moveLeft;
    private boolean moveRight;

    public EntityController(){
        moveLeft = true;
        moveRight = true;
    }

    public void left(){
        if(moveLeft) {
            unblockRight();
            view.getView().setScaleX(1);
            physics.setVelocityX(-300);
        }
    }

    public void right(){
        if(moveRight) {
            unblockLeft();
            view.getView().setScaleX(1);
            physics.setVelocityX(300);
        }
    }

    public void stop(){
        physics.setVelocityX(0);
        physics.setVelocityY(0);
    }

    public void blockRight(){
        moveRight = false;
    }

    public void blockLeft(){
        moveLeft = false;
    }

    public void unblockRight(){
        moveRight = true;
    }

    public void unblockLeft(){
        moveLeft = true;
    }

    /**
     * Sólo para el uso sobre bolas. Lanza una bola al cambiar su velcidad.
     * Puede ser tanto ahacia la derecha como la izquierda
     */
    public void throwAway(){
        physics.setLinearVelocity(185*(new Random().nextDouble()-0.5), -185);
    }
}
