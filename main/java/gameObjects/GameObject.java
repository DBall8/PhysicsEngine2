package gameObjects;

import gameEngine.Entity;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import physicsEngine.Collision;
import physicsEngine.objects.PhysicsAabb;
import physicsEngine.objects.PhysicsCircle;
import physicsEngine.objects.PhysicsObject;
import physicsEngine.objects.PhysicsPolygon;

public class GameObject extends Entity
{
    PhysicsObject object;

    public GameObject(PhysicsObject object, Color color)
    {
        this.object = object;

        switch (object.getObjectType())
        {
            default:
            case CIRCLE:
            {
                PhysicsCircle c = (PhysicsCircle)object;
                Circle visuals = new Circle(c.getRadius());
                visuals.setFill(color);
                addVisual(visuals);
                break;
            }

            case AABB:
            {
                PhysicsAabb aabb = (PhysicsAabb) object;
                Rectangle visuals = new Rectangle(-aabb.getWidth()/2, -aabb.getHeight()/2, aabb.getWidth(), aabb.getHeight());
                visuals.setFill(color);
                addVisual(visuals);
                break;
            }

            case POLYGON:
            {
                PhysicsPolygon p = (PhysicsPolygon) object;
                Polygon visuals = new Polygon(p.getPoints());
                visuals.setFill(color);
                addVisual(visuals);
            }
        }
    }

    @Override
    public void update()
    {
        setLocation(object.getX(), object.getY());
    }
}
