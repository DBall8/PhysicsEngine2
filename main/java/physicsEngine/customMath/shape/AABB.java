package physicsEngine.customMath.shape;

import physicsEngine.customMath.Vec2;

public class AABB extends Shape
{
    public final static int NUM_POINTS = 4;
    private float width;
    private float height;
    public AABB(Vec2 center, float width, float height)
    {
        this.position = center;
        this.width = width;
        this.height = height;
    }

    public float getWidth() { return width; }
    public float getHeight() { return height; }
    public Vec2[] getPoints()
    {
        return new Vec2[]
                {
                        new Vec2(position.getX() - width/2, position.getY() - height/2),
                        new Vec2(position.getX() + width/2, position.getY() - height/2),
                        new Vec2(position.getX() + width/2, position.getY() + height/2),
                        new Vec2(position.getX() - width/2, position.getY() + height/2)
                };
    }

    public float getArea(){ return width*height; }
}
