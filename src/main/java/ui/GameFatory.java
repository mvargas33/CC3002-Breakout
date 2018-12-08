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
import facade.HomeworkTwoFacade;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import logic.brick.*;
import logic.level.Level;
import logic.level.RealLevel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public final class GameFatory {
    public enum Types{
        PLAYER,
        BALL,
        WALL,
        GLASS_BRICK,
        METAL_BRICK,
        WOODEN_BRICK
    }

    public static void addFullLevel(int number, HomeworkTwoFacade facade){
        Random randomObject = new Random();
        facade.addPlayingLevel(facade.newLevelWithBricksFull("Level " + String.valueOf(number), 80, randomObject.nextDouble(), randomObject.nextDouble(), 0));
    }

    public static List<Entity> bricksToEntities(List<Brick> bricks){
        List<Entity> entities = new ArrayList<>();
        int i = 0;
        int j = 0;
        for(Brick brick : bricks){
            //System.out.println(j);
            if (i%10 == 0){
                j++;
                i=0;
            }
            //System.out.println(100*i + "," + 20*j);
            if(brick instanceof GlassBrick){
                Entity glassBrick = newGlassBrick(100*i, 30*j);
                entities.add(glassBrick);
            }else if(brick instanceof MetalBrick){
                Entity glassBrick = newMetalBrick(100*i, 30*j);
                entities.add(glassBrick);
            }else if(brick instanceof WoodenBrick){
                Entity glassBrick = newWoodenBrick(100*i, 30*j);
                entities.add(glassBrick);
            }
            i++;
        }
        return entities;
    }

    public static HashMap<Entity, Brick> linkBricks(List<Brick> bricks){
        HashMap<Entity, Brick> map = new HashMap<>();
        int i = 0;
        int j = 30; // Empty row at top
        for(Brick brick : bricks){
            if (i%10 == 0){
                j++;i=0;
            }
            if(brick.isGlassBrick()){
                Entity glassEntity = newGlassBrick(100*i, 30*j);
                map.put(glassEntity, brick);
            }else if(brick.isMetalBrick()){
                Entity metalEntity = newMetalBrick(100*i, 30*j);
                map.put(metalEntity, brick);
            }else if(brick.isWoodenBrick()){
                Entity woodenEntity = newWoodenBrick(100*i, 30*j);
                map.put(woodenEntity, brick);
            }
            i++;
        }
        return map;
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
                .buildAndAttach();
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
                .buildAndAttach();
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
                .buildAndAttach();
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

    public static Entity newBackground(double width, double height){
        return Entities.builder()
                .viewFromNode(new Rectangle(width, height, Color.BLACK))
                .renderLayer(RenderLayer.BACKGROUND)
                .build();
    }

    public static Entity newBall(double x, double y, boolean withSpeed){
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);
        physics.setFixtureDef(
                new FixtureDef().restitution(1f).density(0.1f).friction(0f)
        );
        if(withSpeed){
            physics.setOnPhysicsInitialized(
                    () -> physics.setLinearVelocity(200*(new Random().nextDouble()-0.5), -200)
            );
        }

        return Entities.builder()
                .at(x,y)
                .type(Types.BALL)
                .bbox(new HitBox("Ball", BoundingShape.circle(10)))
                .viewFromNode(new Circle(8, Color.LIGHTCORAL))
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
