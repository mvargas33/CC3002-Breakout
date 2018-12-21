package ui;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.PositionComponent;
import com.almasb.fxgl.entity.components.ViewComponent;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import javafx.geometry.Point2D;

import java.util.Random;

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

    public void translateTo(double x, double y){
        System.out.println("TRANSLATED TO  (" + x + "," + y + ")");
        //stop();
        //physics.reposition(new Point2D(x,y));
        position.translate(x, y);

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

    public void throwAway(){
        physics.setLinearVelocity(200*(new Random().nextDouble()+0.5), -200);
    }
}
