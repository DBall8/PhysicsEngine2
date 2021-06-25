package physicsEngine.objects;

import physicsEngine.Collision;
import physicsEngine.Material;
import physicsEngine.customMath.CustomMath;
import physicsEngine.customMath.RayCast;
import physicsEngine.customMath.Vec2;
import physicsEngine.customMath.shape.Line;
import physicsEngine.customMath.shape.Polygon;

public class PhysicsPolygon extends PhysicsObject
{
    Polygon polygon;

    public PhysicsPolygon(Polygon polygon, Material material)
    {
        super(polygon.getPos(), polygon.getArea(), material, OType.POLYGON);
        this.polygon = polygon;
    }

    public double[] getPoints(){ return polygon.getPointsRelativeDouble(); }

    float getNextCollisionTime(PhysicsCircle circle, float time)
    {
        return circle.getNextCollisionTime(this, time);
    }

    float getNextCollisionTime(PhysicsAabb aabb, float time)
    {
        return aabb.getNextCollisionTime(this, time);
    }

    float getNextCollisionTime(PhysicsPolygon otherPolygon, float time)
    {
        float closestDistance = Float.MAX_VALUE;
        Vec2 relativeVelocity = CustomMath.vecSub(velocity, otherPolygon.getVelocity());

        // Cast from each of this AABB's points to each of the opposing's polygon's lines
        Vec2[] myPoints = this.polygon.getPointsAbsolute();
        Vec2[] theirPoints = otherPolygon.polygon.getPointsAbsolute();
        int numMyPoints = polygon.getNumPoints();
        int numTheirPoints = otherPolygon.polygon.getNumPoints();
        for (int i=0; i<numMyPoints; i++) {

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

            for (int j = 0; j < numMyPoints; j++) {
                Vec2 p1 = myPoints[j];
                Vec2 p2 = (j + 1 < numMyPoints) ?
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
                otherPolygon.intakeCollisionTime(collisionTime);

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
