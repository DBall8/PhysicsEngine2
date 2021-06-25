package physicsEngine.customMath.shape;

import physicsEngine.customMath.CustomMath;
import physicsEngine.customMath.Vec2;

public class Line
{
    Vec2 point1;
    Vec2 point2;

    public Line(float x1, float y1, float x2, float y2)
    {
        this.point1 = new Vec2(x1, y1);
        this.point2 = new Vec2(x2, y2);
    }

    public Line(Vec2 point1, Vec2 point2)
    {
        this.point1 = point1;
        this.point2 = point2;
    }

    public Vec2 getPoint1(){ return point1.copy(); }
    public Vec2 getPoint2() { return point2.copy(); }

    public float getX(float y)
    {
        float slope = (point2.getY() - point1.getY()) / (point2.getX() - point1.getX());
        return ((y - point1.getY()) / slope) + point1.getX();
    }

    public float getY(float x)
    {
        float slope = (point2.getY() - point1.getY()) / (point2.getX() - point1.getX());
        return (slope * (x - point1.getX())) + point1.getY();
    }

    public Vec2 getVector()
    {
        return CustomMath.vecSub(point2, point1);
    }
}
