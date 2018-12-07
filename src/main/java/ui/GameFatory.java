package ui;

import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.RenderLayer;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public final class GameFatory {
    public enum Types{
        PLAYER,
        BALL,
        WALL,
        GLASS_BRICK,
        METAL_BRICK,
        WOODEN_BRICK
    }

    public static Entity newGlassBrick(double x, double y){
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.STATIC);
        return Entities.builder()
                .at(x, y)
                .type(Types.GLASS_BRICK)
                .bbox(new HitBox("Brick",BoundingShape.box(100, 30)))
                .viewFromNode(new Rectangle(100, 30, Color.RED))
                .with(physics, new CollidableComponent(true))
                .build();
    }

    public static Entity newMetalBrick(double x, double y){
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.STATIC);
        return Entities.builder()
                .at(x, y)
                .type(Types.METAL_BRICK)
                .bbox(new HitBox("Brick",BoundingShape.box(100, 30)))
                .viewFromNode(new Rectangle(100, 30, Color.GREEN))
                .with(physics, new CollidableComponent(true))
                .build();
    }

    public static Entity newWoodenBrick(double x, double y){
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.STATIC);
        return Entities.builder()
                .at(x, y)
                .type(Types.WOODEN_BRICK)
                .bbox(new HitBox("Brick",BoundingShape.box(100, 30)))
                .viewFromNode(new Rectangle(100, 30, Color.YELLOW))
                .with(physics, new CollidableComponent(true))
                .build();
    }

    public static Entity newPlayer(double x, double y, PlayerControl playerControl){
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.KINEMATIC);
        return Entities.builder()
                .at(x, y)
                .type(Types.PLAYER)
                .bbox(new HitBox("Player",BoundingShape.box(150, 30)))
                .viewFromNode(new Rectangle(150, 30, Color.BLUE))
                .with(physics, new CollidableComponent(true), playerControl)
                .build();
    }

    public static Entity newBackground(){
        return Entities.builder()
                .viewFromNode(new Rectangle(1000, 650, Color.BLACK))
                .renderLayer(RenderLayer.BACKGROUND)
                .build();
    }

    public static Entity newBall(double x, double y){
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);
        physics.setFixtureDef(
                new FixtureDef().restitution(1f).density(0.1f).friction(0f)
        );
        physics.setOnPhysicsInitialized(
                () -> physics.setLinearVelocity(5*40, -5*30)
        );
        return Entities.builder()
                .at(x,y)
                .type(Types.BALL)
                .bbox(new HitBox("Ball", BoundingShape.circle(10)))
                .viewFromNode(new Circle(10, Color.LIGHTCORAL))
                .with(physics, new CollidableComponent(true))
                .build();
    }

    public static Entity newWalls() {
        Entity walls = Entities.makeScreenBounds(100);
        walls.setType(Types.WALL);
        walls.addComponent(new CollidableComponent(true));
        return walls;
    }
}
