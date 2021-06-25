package physicsEngine.customMath.shape;

import physicsEngine.customMath.Vec2;

public class Circle extends Shape
{
    private float radius;

    public Circle(Vec2 center, float radius)
    {
        this.position = center;
        this.radius = radius;
    }

    public Circle(float x, float y, float radius)
    {
        this.position = new Vec2(x, y);
        this.radius = radius;
    }

    public float getRadius() { return radius; }

    public float getArea() { return (float)(Math.PI * radius * radius); }

    public Circle copy(){ return new Circle(position, radius); }
}
