package ui;

import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.GameWorld;
import com.almasb.fxgl.entity.RenderLayer;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import logic.brick.*;

import java.util.*;

public final class GameFatory {
    public enum Types{
        PLAYER,
        BALL,
        WALL,
        BRICK,
        SYMBOLIC_BALL
    }
/*
    public static void addFullLevel(int number, HomeworkTwoFacade facade){
        Random randomObject = new Random();
        facade.addPlayingLevel(facade.newLevelWithBricksFull("Level " + number, 80, randomObject.nextDouble(), randomObject.nextDouble(), 0));
    }
*/
    public static void updateBalls(int cuantity, int width){
        int i = 0;
        int j = -1;
        for(int q = 0; q < cuantity; q++){
            if(i%15 == 0){
                j++;i=0;
            }
            if (j == 2){    // Sólo se pueden dibujar hasta 60 balls
                break;
            }
            Entity symbolicBall = newSymbolicBall(width - 32 - 25 + 25*i, 5 + 25*j);
            i--;
        }
    }

    public static HashMap<Entity, Brick> linkBricks(List<Brick> bricks){
        Collections.shuffle(bricks);
        HashMap<Entity, Brick> map = new HashMap<>();
        int i = 0;
        int j = 1; // Empty row at top
        for(Brick brick : bricks){
            if (i%10 == 0){
                j++;i=0;
            }
            if(brick.isGlassBrick()){
                Entity glassEntity = newBrick(104*i + 32, 34*j, Color.RED, "iceiceice.png");
                map.put(glassEntity, brick);
            }else if(brick.isMetalBrick()){
                Entity metalEntity = newBrick(104*i + 32, 34*j, Color.DARKGREY, "poneglyph-v5.png");
                map.put(metalEntity, brick);
            }else if(brick.isWoodenBrick()){
                Entity woodenEntity = newBrick(104*i + 32, 34*j, Color.GREENYELLOW, "fire-v4.png");
                map.put(woodenEntity, brick);
            }
            i++;
        }
        return map;
    }

    public static Entity newBrick(double x, double y, Color c, String textura){
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.STATIC);
        return Entities.builder()
                .at(x, y)
                .type(Types.BRICK)
                .bbox(new HitBox("Brick",BoundingShape.box(100, 30)))
                .viewFromNode(new Rectangle(100, 30, c))
                .viewFromTexture(textura)
                .with(physics, new CollidableComponent(true))
                .buildAndAttach();
    }

    public static Entity newPlayer(double x, double y, EntityController entityController){
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.KINEMATIC);
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
                new FixtureDef().restitution(1f).density(0f).friction(0f)
        );
        /*if(withSpeed){
            physics.setOnPhysicsInitialized(
                    () -> physics.setLinearVelocity(200*(new Random().nextDouble()-0.5), -200)
            );
        }*/

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
        walls.setType(Types.WALL);
        walls.addComponent(new CollidableComponent(true));
        return walls;
    }
}
