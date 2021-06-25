package physicsEngine.customMath.shape;

import physicsEngine.customMath.CustomMath;
import physicsEngine.customMath.Vec2;

public class Triangle extends Shape
{
    private Vec2[] points = new Vec2[3];

    public Triangle(Vec2 p1, Vec2 p2, Vec2 p3)
    {
        points[0] = p1;
        points[1] = p2;
        points[2] = p3;

        this.position = getCenter();

        for (int i=0; i<3; i++)
        {
            points[i].sub(this.position);
        }
    }

    private Vec2 getCenter()
    {
        // Draw a line from the center of any side of a triangle to the third point (the point not on the side)
        // The midpoint of the triangle will be 1/3 of the distance on this line from the side (or 2/3 from the point)
        float midX = (points[0].getX() + points[1].getX()) / 2.0f;
        float midY = (points[0].getY() + points[1].getY()) / 2.0f;

        float dx = (points[2].getX() - midX) / 3;
        float dy = (points[2].getY() - midY) / 3;

        return new Vec2(midX + dx, midY + dy);
    }

    public float getArea()
    {
        Vec2 base = CustomMath.vecSub(points[1], points[0]);
        float baseLen = base.getMagnitude();

        // Get the distance from the base to the third point DOT the tangent of the base
        Vec2 inwardsDir = new Vec2(points[2].getX() - points[0].getX(), points[2].getY() - points[0].getY());
        Vec2 inwardsTangent = base.getTangentTowards(inwardsDir);
        inwardsTangent.normalize();

        float height = inwardsTangent.dot(inwardsDir);

        return 0.5f * baseLen * height;
    }
}
