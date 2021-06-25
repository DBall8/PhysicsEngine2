package physicsEngine.customMath;

import physicsEngine.customMath.shape.Circle;
import physicsEngine.customMath.shape.Line;

public class RayCast
{
    public static float distanceToCircle(Vec2 point, Vec2 direction, Circle circle)
    {
        // Set to infinite intersection into an intersection is found
        float result = Float.MAX_VALUE;

        // Move relative space so that the point is at the origin
        circle.getPos().sub(point);

        // Rotate relative space so that the ray direction is 0 degrees
        Vec2 newCirclePos = rotatePoint(circle.getPos(), -direction.getAngle());
        circle.setPos(newCirclePos);

        // If the circle's y coordinate is negative, then the ray is facing towards the circle
        // If the circle's absolute of its x coordinate is less than its radius
        if ((Math.abs(circle.getX()) < circle.getRadius()) &&
            (circle.getY() < 0))
        {
            // Intersection has occurred

            // To find the point of intersection, draw a triangle with the circle's center, the point where the ray intersects the circle's radius.
            // and the line that is at the right angle to the vertical x axis
            float interiorDistance = (float)Math.sqrt((circle.getRadius() * circle.getRadius()) - (circle.getX() * circle.getX()));
            float intersectionPointDistance = Math.abs(circle.getY()) - interiorDistance;

            result = intersectionPointDistance;
        }

        return result;
    }

    public static float distanceToLine(Vec2 point, Vec2 direction, Line line)
    {
        // Translate the line and circle so that the circle is at the origin
        Vec2 linePoint1 = line.getPoint1();
        Vec2 linePoint2 = line.getPoint2();
        linePoint1.sub(point);
        linePoint2.sub(point);

        // Rotate relative space so that the ray direction is 0 degrees
        float directionAngle = direction.getAngle();
        RotationMatrix rm = new RotationMatrix(-directionAngle);
        Vec2 point1Relative = rm.rotateVector(linePoint1);
        Vec2 point2Relative = rm.rotateVector(linePoint2);

        if ((point1Relative.getY() > 0) &&
                (point2Relative.getY() > 0))
        {
            // Line is behind the circle
            return Float.MAX_VALUE;
        }

        if ((point1Relative.getX() < 0 && point2Relative.getX() > 0) ||
                (point2Relative.getX() < 0 && point1Relative.getX() > 0))
        {
            Line l2 = new Line(point1Relative, point2Relative);
            float closestDistance = Math.abs(l2.getY(0));
            return closestDistance;

        }

        return Float.MAX_VALUE;
    }

    public static float distanceFromCircleToLine(Circle c, Vec2 direction, Line line)
    {
        // Translate the line and circle so that the circle is at the origin
        Vec2 linePoint1 = line.getPoint1();
        Vec2 linePoint2 = line.getPoint2();
        linePoint1.sub(c.getPos());
        linePoint2.sub(c.getPos());

        // Rotate relative space so that the ray direction is 0 degrees
        float directionAngle = direction.getAngle();
        Vec2 point1Relative = rotatePoint(linePoint1, -directionAngle);
        Vec2 point2Relative = rotatePoint(linePoint2, -directionAngle);

        if ((point1Relative.getY() > 0) &&
            (point2Relative.getY() > 0))
        {
            // Line is behind the circle
            return Float.MAX_VALUE;
        }

        Vec2 lineTangent = CustomMath.vecSub(point2Relative, point1Relative).getTangentTowards(new Vec2(0, 1));
        lineTangent.normalize().mult(c.getRadius());
        Vec2 shiftedPoint1 = CustomMath.vecAdd(point1Relative, lineTangent);
        Vec2 shiftedPoint2 = CustomMath.vecAdd(point2Relative, lineTangent);

        if ((shiftedPoint1.getX() < 0 && shiftedPoint2.getX() > 0) ||
            (shiftedPoint2.getX() < 0 && shiftedPoint1.getX() > 0))
        {
            Line l2 = new Line(shiftedPoint1, shiftedPoint2);
            float closestDistance = Math.abs(l2.getY(0));
            return closestDistance;

        }
        else if ((Math.abs(point1Relative.getX()) < c.getRadius()) ||
                 (Math.abs(point2Relative.getX()) < c.getRadius()))
        {
            float p1Distance = distanceToCircle(new Vec2(0,0), new Vec2(0, -1), new Circle(point1Relative, c.getRadius()));
            float p2Distance = distanceToCircle(new Vec2(0,0), new Vec2(0, -1), new Circle(point2Relative, c.getRadius()));

            float closestDistance = Math.min(p1Distance, p2Distance);
            return closestDistance;
        }

        return Float.MAX_VALUE;
    }

    private static Vec2 rotatePoint(Vec2 point, float angleRads)
    {
        RotationMatrix rm = new RotationMatrix(angleRads);
        return rm.rotateVector(point);
    }
}
