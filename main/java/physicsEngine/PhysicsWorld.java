package physicsEngine;

import physicsEngine.customMath.Vec2;
import physicsEngine.customMath.shape.AABB;
import physicsEngine.customMath.shape.Circle;
import physicsEngine.customMath.shape.Polygon;
import physicsEngine.objects.PhysicsAabb;
import physicsEngine.objects.PhysicsCircle;
import physicsEngine.objects.PhysicsObject;
import physicsEngine.objects.PhysicsPolygon;

import java.util.ArrayList;

public class PhysicsWorld
{
    private ArrayList<PhysicsObject> objects = new ArrayList<>();

    public PhysicsWorld()
    {
        PhysicsSettings.setDrag(0.05f);
    }

    public void update(float timestep)
    {
        for (PhysicsObject o: objects)
        {
            o.update(timestep);
        }

        float timeBank = timestep;
        while (timeBank > 0.001f)
        {
            float earliestCollisionTime = timeBank;
            for (int i=0; i<objects.size(); i++)
            {
                for (int j=i+1; j<objects.size(); j++)
                {
                    float t = objects.get(i).getNextCollisionTime(objects.get(j), timeBank);
                    if (t < earliestCollisionTime)
                    {
//                        earliestCollisionTime = (t < 0) ? 0 : earliestCollisionTime;
                        earliestCollisionTime = t;
                    }
                }
            }

            for (PhysicsObject o: objects)
            {
                o.move(earliestCollisionTime);
            }

            for (int i=0; i<objects.size(); i++)
            {
                for (int j=i+1; j<objects.size(); j++)
                {
                    objects.get(i).checkCollision(objects.get(j));
                }
            }

            timeBank -= earliestCollisionTime;
        }
    }

    public PhysicsCircle addCircle(float x, float y, float radius, Material material)
    {
        Circle circle = new Circle(x, y, radius);
        PhysicsCircle c = new PhysicsCircle(circle, material);
        objects.add(c);
        return c;
    }

    public PhysicsAabb addAABB(float x, float y, float width, float height, Material material)
    {
        AABB aabb = new AABB(new Vec2(x, y), width, height);
        PhysicsAabb a = new PhysicsAabb(aabb, material);
        objects.add(a);
        return a;
    }

    public PhysicsPolygon addPolygon(float x, float y, double[] points, int numPoints, Material material)
    {
        Polygon polygon = new Polygon(points, numPoints);
        PhysicsPolygon p = new PhysicsPolygon(polygon, material);
        objects.add(p);
        return p;
    }
}
