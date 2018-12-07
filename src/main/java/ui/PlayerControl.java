package ui;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.ViewComponent;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;

public class PlayerControl extends Component {
    private ViewComponent view;
    private PhysicsComponent physics;
    private boolean moveLeft;
    private boolean moveRight;

    public PlayerControl(){
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
}
