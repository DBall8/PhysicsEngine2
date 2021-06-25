package physicsEngine.customMath;

public class Vec2 {
    private float x;
    private float y;

    public Vec2(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getMagnitude()
    {
        return (float)Math.sqrt((x*x) + (y*y));
    }

    public float getMagnitudeSquared()
    {
        return (x*x) + (y*y);
    }

    public Vec2 addX(float x)
    {
        this.x += x;
        return this;
    }

    public Vec2 addY(float y)
    {
        this.y += y;
        return this;
    }

    public Vec2 mult(float mult)
    {
        this.x *= mult;
        this.y *= mult;
        return this;
    }

    public Vec2 add(Vec2 v)
    {
        this.x += v.x;
        this.y += v.y;
        return this;
    }

    public Vec2 sub(Vec2 v)
    {
        this.x -= v.x;
        this.y -= v.y;
        return this;
    }

    public Vec2 div(float div)
    {
        this.x /= div;
        this.y /= div;
        return this;
    }

    public float getAngle()
    {
        float angle = (float)Math.atan(x/-y);
        if (y >= 0) angle += Math.PI;
        return angle;
    }

    public float dot(Vec2 v2)
    {
        return (x * v2.x) + (y * v2.y);
    }

    public Vec2 getTangent()
    {
        Vec2 tangent = new Vec2(-y, x);
        return tangent;
    }

    public Vec2 getTangentTowards(Vec2 dir)
    {
        Vec2 tangent = new Vec2(-y, x);

        // If the tangent is opposite the direction given, flip it
        if (tangent.dot(dir) < 0)
        {
            tangent.mult(-1);
        }

        return tangent;
    }

    public Vec2 normalize()
    {
        div(getMagnitude());
        return this;
    }

    public Vec2 copy(){ return new Vec2(x, y); }

    public void rotate(float degreesRadians)
    {
        RotationMatrix rm = new RotationMatrix(degreesRadians);
        Vec2 rotated = rm.rotateVector(this);
        this.x = rotated.x;
        this.y = rotated.y;
    }

    public void rotate(RotationMatrix rotationMatrix)
    {
        Vec2 rotated = rotationMatrix.rotateVector(this);
        this.x = rotated.x;
        this.y = rotated.y;
    }
}
