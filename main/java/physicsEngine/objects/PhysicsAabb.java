package physicsEngine.objects;

import physicsEngine.Collision;
import physicsEngine.Material;
import physicsEngine.customMath.CustomMath;
import physicsEngine.customMath.RayCast;
import physicsEngine.customMath.Vec2;
import physicsEngine.customMath.shape.AABB;
import physicsEngine.customMath.shape.Line;

public class PhysicsAabb extends PhysicsObject
{
    AABB box;

    public PhysicsAabb(AABB aabb, Material material)
    {
        super(aabb.getPos(), aabb.getArea(), material, OType.AABB);
        box = aabb;
    }

    public float getWidth(){ return box.getWidth(); }
    public float getHeight(){ return box.getHeight(); }

    float getNextCollisionTime(PhysicsCircle circle, float time)
    {
        return circle.getNextCollisionTime(this, time);
    }

    float getNextCollisionTime(PhysicsAabb aabb, float time)
    {
        float closestDistance = Float.MAX_VALUE;
        Vec2 relativeVelocity = CustomMath.vecSub(velocity, aabb.getVelocity());

        // Cast from each of this AABB's points to each of the opposing's AABB's lines
        Vec2[] myPoints = box.getPoints();
        Vec2[] theirPoints = aabb.box.getPoints();
        for (int i=0; i<AABB.NUM_POINTS; i++) {

            for (int j = 0; j < AABB.NUM_POINTS; j++) {
                Vec2 p1 = theirPoints[j];
                Vec2 p2 = (j + 1 < AABB.NUM_POINTS) ?
                        theirPoints[j + 1] :
                        theirPoints[0];

                float dist = RayCast.distanceToLine(myPoints[i], relativeVelocity, new Line(p1, p2));
                if (dist < closestDistance) {
                    closestDistance = dist;
                }
            }
        }

        // Cast from each of the opposing AABB's points to each of this AABB's lines
        Vec2 reverseDirection = relativeVelocity.copy().mult(-1);
        for (int i=0; i<AABB.NUM_POINTS; i++) {

            for (int j = 0; j < AABB.NUM_POINTS; j++) {
                Vec2 p1 = myPoints[j];
                Vec2 p2 = (j + 1 < AABB.NUM_POINTS) ?
                        myPoints[j + 1] :
                        myPoints[0];

                float dist = RayCast.distanceToLine(theirPoints[i], reverseDirection, new Line(p1, p2));
                if (dist < closestDistance) {
                    closestDistance = dist;
                }
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

        // Cast from each of this AABB's points to each of the opposing's polygon's lines
        Vec2[] myPoints = box.getPoints();
        Vec2[] theirPoints = polygon.polygon.getPointsAbsolute();
        int numTheirPoints = polygon.polygon.getNumPoints();
        for (int i=0; i<AABB.NUM_POINTS; i++) {

            for (int j = 0; j < numTheirPoints; j++) {
                Vec2 p1 = theirPoints[j];
                Vec2 p2 = (j + 1 < numTheirPoints) ?
                        theirPoints[j + 1] :
                        theirPoints[0];

                float dist = RayCast.distanceToLine(myPoints[i], relativeVelocity, new Line(p1, p2));
                if (dist < closestDistance) {
                    closestDistance = dist;
                }
            }
        }

        // Cast from each of the opposing AABB's points to each of this AABB's lines
        Vec2 reverseDirection = relativeVelocity.copy().mult(-1);
        for (int i=0; i<numTheirPoints; i++) {

            for (int j = 0; j < AABB.NUM_POINTS; j++) {
                Vec2 p1 = myPoints[j];
                Vec2 p2 = (j + 1 < AABB.NUM_POINTS) ?
                        myPoints[j + 1] :
                        myPoints[0];

                float dist = RayCast.distanceToLine(theirPoints[i], reverseDirection, new Line(p1, p2));
                if (dist < closestDistance) {
                    closestDistance = dist;
                }
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

    }
    void checkCollision(PhysicsAabb aabb)
    {}

    void checkCollision(PhysicsPolygon polygon)
    {}
}
