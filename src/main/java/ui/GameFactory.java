package ui;

import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.RenderLayer;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.entity.components.SelectableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;


/**
 * Clase que fabrica Entidades de un tipo específico. Esa es su única función. Podría clasificarse dentro del Factory Pattern,
 * sin embargo todos sus métodos son estáticos para ser llamados desde cualquier lado, principalmente de App.
 *
 * @author Maximiliano Vargas
 */
public final class GameFactory {
    public enum Types{
        PLAYER,
        BALL,
        WALL,
        BRICK,
        SYMBOLIC_BALL
    }

    public static Entity newBrick(double x, double y, Color c, String textura){
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.STATIC);
        physics.setFixtureDef(
                new FixtureDef().restitution(0f).density(0f).friction(0f)
        );
        return Entities.builder()
                .at(x, y)
                .type(Types.BRICK)
                .bbox(new HitBox("Brick",BoundingShape.box(100, 30)))
                .viewFromNode(new Rectangle(100, 30, c))
                .viewFromTexture(textura)
                .with(physics, new CollidableComponent(true), new SelectableComponent(true))
                .buildAndAttach();
    }

    public static Entity newPlayer(double x, double y, EntityController entityController){
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.KINEMATIC);
        physics.setFixtureDef(
                new FixtureDef().restitution(0f).density(0f).friction(1f)
        );
        return Entities.builder()
                .at(x, y)
                .type(Types.PLAYER)
                .bbox(new HitBox("Player",BoundingShape.box(150, 30)))
                .viewFromNode(new Rectangle(150, 57, Color.BLUE))
                .viewFromTexture("strawhat.png")
                .with(physics, new CollidableComponent(true), entityController)
                .buildAndAttach();
    }

    public static Entity newBackground(double width, double height){
        return Entities.builder()
                .viewFromNode(new Rectangle(width, height, Color.BLACK))
                .viewFromTexture("sunny-1100x700.png")
                .renderLayer(RenderLayer.BACKGROUND)
                .buildAndAttach();
    }

    public static Entity newBall(double x, double y, EntityController entityController){
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);
        physics.setFixtureDef(
                new FixtureDef().restitution(1f).density(1f).friction(1f)
        );
        return Entities.builder()
                .at(x,y)
                .type(Types.BALL)
                .bbox(new HitBox("Ball", BoundingShape.circle(10)))
                .viewFromNode(new Circle(8, Color.LIGHTCORAL))
                .viewFromTexture("logpose.png")
                .with(physics, new CollidableComponent(true), entityController)
                .buildAndAttach();
    }

    public static Entity newSymbolicBall(double x, double y){
        return Entities.builder()
                .at(x,y)
                .type(Types.SYMBOLIC_BALL)
                .viewFromNode(new Circle(8, Color.LIGHTCORAL))
                .viewFromTexture("logpose.png")
                .with(new CollidableComponent(false))
                .buildAndAttach();
    }

    public static Entity newWalls() {
        Entity walls = Entities.makeScreenBounds(100);
        walls.getComponent(PhysicsComponent.class).setFixtureDef(
            new FixtureDef().restitution(0f).density(0f).friction(0f)
        );
        walls.setType(Types.WALL);
        walls.addComponent(new CollidableComponent(true));
        return walls;
    }
}
