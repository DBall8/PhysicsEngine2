package physicsEngine.customMath;

public class CustomMath {

    public static float toDegrees(float angleRadians)
    {
        return angleRadians * 180 / (float)Math.PI;
    }
    public static float toRads(float angleDegrees)
    {
        return angleDegrees * (float)Math.PI / 180;
    }

    public static float getAngle(float x, float y)
    {
        float angle = (float)Math.atan(x/y);
        if (y < 0) angle += Math.PI;
        return angle;
    }

    public static float getDistSquared(Vec2 v1, Vec2 v2)
    {
        float dx = v1.getX() - v2.getX();
        float dy = v1.getY() - v2.getY();

        return (dx*dx) + (dy*dy);
    }

    public static float getDistSquared(float x1, float y1, float x2, float y2)
    {
        float dx = x1 - x2;
        float dy = y1 - y2;

        return (dx*dx) + (dy*dy);
    }

    public static float getDist(Vec2 v1, Vec2 v2)
    {
        return (float)Math.sqrt(getDistSquared(v1, v2));
    }

    public static float getDist(float x1, float y1, float x2, float y2)
    {
        return (float)Math.sqrt(getDistSquared(x1, y1, x2, y2));
    }

    public static float[] quadratic(float a, float b, float c)
    {
        float[] result = new float[2];

        float root = (float)Math.sqrt((b*b) - (4.0f * a * c));

        result[0] = ((-1.0f * b) +  root) / (2.0f * a);
        result[1] = ((-1.0f * b) -  root) / (2.0f * a);

        return result;
    }

    public static float clamp(float value, float clamp1, float clamp2)
    {
        float min, max;
        if (clamp1 < clamp2)
        {
            min = clamp1;
            max = clamp2;
        }
        else
        {
            min = clamp2;
            max = clamp1;
        }

        if (value < min) return min;
        else if (value > max) return max;
        return value;
    }

    public static Vec2 vecSub(Vec2 v1, Vec2 v2)
    {
        return new Vec2(v1.getX() - v2.getX(), v1.getY() - v2.getY());
    }
    public static Vec2 vecAdd(Vec2 v1, Vec2 v2)
    {
        return new Vec2(v1.getX() + v2.getX(), v1.getY() + v2.getY());
    }

    public static float cross(Vec2 v1, Vec2 v2)
    {
        return (v1.getX() * v2.getY()) - (v2.getX() * v1.getY());
    }
    public static Vec2 cross(float scalar, Vec2 v2)
    {
        return new Vec2(-scalar * v2.getY(), scalar * v2.getX());
    }

    public static float dotProduct(Vec2 vector1, Vec2 vector2)
    {
        return (vector1.getX() * vector2.getX()) + (vector1.getY() * vector2.getY());
    }

}
