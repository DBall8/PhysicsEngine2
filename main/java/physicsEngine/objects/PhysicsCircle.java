package physicsEngine.objects;

import physicsEngine.Collision;
import physicsEngine.Material;
import physicsEngine.customMath.CustomMath;
import physicsEngine.customMath.RayCast;
import physicsEngine.customMath.Vec2;
import physicsEngine.customMath.shape.AABB;
import physicsEngine.customMath.shape.Circle;
import physicsEngine.customMath.shape.Line;

public class PhysicsCircle extends PhysicsObject
{
    Circle circle;

    public PhysicsCircle(Circle circle, Material material)
    {
        super(circle.getPos(), circle.getArea(), material, OType.CIRCLE);
        this.circle = circle;
    }

    public float getRadius(){ return circle.getRadius(); }

    float getNextCollisionTime(PhysicsCircle c, float time)
    {
        Vec2 relativeVelocity = CustomMath.vecSub(velocity, c.getVelocity());
        Circle testCircle = new Circle(c.getPosition(), c.getRadius() + circle.getRadius());
        float distance = RayCast.distanceToCircle(circle.getPos(), relativeVelocity, testCircle);
        float collisionTime = distance / relativeVelocity.getMagnitude();

        if (collisionTime <= time)
        {
            intakeCollisionTime(collisionTime);
            c.intakeCollisionTime(collisionTime);

            return collisionTime;
        }

        return time;
    }

    float getNextCollisionTime(PhysicsAabb aabb, float time)
    {
        float closestDistance = Float.MAX_VALUE;
        Vec2 relativeVelocity = CustomMath.vecSub(velocity, aabb.getVelocity());

        Vec2[] points = aabb.box.getPoints();
        for (int i = 0; i< AABB.NUM_POINTS; i++)
        {
            Vec2 p1 = points[i];
            Vec2 p2 = (i+1 < AABB.NUM_POINTS) ?
                    points[i+1] :
                    points[0];

            float dist = RayCast.distanceFromCircleToLine(circle, relativeVelocity, new Line(p1, p2));
            if (dist < closestDistance)
            {
                closestDistance = dist;
            }
        }

        if (closestDistance < Float.MAX_VALUE)
        {
            float collisionTime = closestDistance / relativeVelocity.getMagnitude();
            if (collisionTime <= time)
            {
                intakeCollisionTime(collisionTime);
                aabb.intakeCollisionTime(collisionTime);

                return collisionTime;
            }
        }

        return time;
    }

    float getNextCollisionTime(PhysicsPolygon polygon, float time)
    {
        float closestDistance = Float.MAX_VALUE;
        Vec2 relativeVelocity = CustomMath.vecSub(velocity, polygon.getVelocity());

        Vec2[] points = polygon.polygon.getPointsAbsolute();
        int numPoints = polygon.polygon.getNumPoints();
        for (int i=0; i<numPoints; i++)
        {
            Vec2 p1 = points[i];
            Vec2 p2 = (i+1 < numPoints) ?
                    points[i+1] :
                    points[0];

            float dist = RayCast.distanceFromCircleToLine(circle, relativeVelocity, new Line(p1, p2));
            if (dist < closestDistance)
            {
                closestDistance = dist;
            }
        }

        if (closestDistance < Float.MAX_VALUE)
        {
            float collisionTime = closestDistance / relativeVelocity.getMagnitude();
            if (collisionTime <= time)
            {
                intakeCollisionTime(collisionTime);
                polygon.intakeCollisionTime(collisionTime);

                return collisionTime;
            }
        }

        return time;
    }

    void checkCollision(PhysicsCircle circle)
    {
        Vec2 normal = CustomMath.vecSub(circle.getPosition(), getPosition());
        float radiusSum = circle.getRadius() + getRadius();

        if ((radiusSum * radiusSum) > normal.getMagnitudeSquared())
        {
            // Collision!
            float penetration = radiusSum - normal.getMagnitude();
            Collision c = new Collision(this, circle, normal, penetration);
            c.resolve();
        }
    }
    void checkCollision(PhysicsAabb aabb)
    {}

    void checkCollision(PhysicsPolygon polygon)
    {}
}
