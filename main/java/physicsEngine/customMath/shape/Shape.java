package physicsEngine.customMath.shape;

import physicsEngine.customMath.Vec2;

public abstract class Shape
{
    protected Vec2 position = new Vec2(0,0);

    public Vec2 getPos(){ return position; }
    public float getX() { return position.getX(); }
    public float getY() { return position.getY(); }
    public void setPos(Vec2 newPosition) { this.position = newPosition; }
    public void move(Vec2 movement) { this.position.add(movement); }
}
