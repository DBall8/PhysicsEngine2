package physicsEngine.objects;

import physicsEngine.Collision;
import physicsEngine.Material;
import physicsEngine.PhysicsSettings;
import physicsEngine.customMath.Vec2;

public abstract class PhysicsObject
{
    Vec2 position;
    Vec2 velocity;
    Vec2 force;

    protected float mass;
    protected float invertedMass;
    protected Material material;

    protected OType objectType;

    protected float nextCollisionTime;

    public PhysicsObject(Vec2 initialPos, float volume, Material material, OType type)
    {
        this.position = initialPos;
        this.velocity = new Vec2(0,0);
        this.force = new Vec2(0,0);
        this.material = material;
        this.mass = volume * material.getDensity() / 100000;
        this.invertedMass = (this.mass == 0) ? 0 : 1/this.mass;
        this.objectType = type;
    }

    /* Internal abstract methods */
    abstract float getNextCollisionTime(PhysicsCircle circle, float time);
    abstract float getNextCollisionTime(PhysicsAabb aabb, float time);
    abstract float getNextCollisionTime(PhysicsPolygon polygon, float time);

    public float getNextCollisionTime(PhysicsObject object, float time)
    {
        switch (object.getObjectType())
        {
            case CIRCLE:
            {
                return getNextCollisionTime((PhysicsCircle)object, time);
            }

            case AABB:
            {
                return getNextCollisionTime((PhysicsAabb) object, time);
            }

            case POLYGON:
            {
                return getNextCollisionTime((PhysicsPolygon) object, time);
            }

            default:
            {
                return time;
            }
        }
    }

    abstract void checkCollision(PhysicsCircle circle);
    abstract void checkCollision(PhysicsAabb aabb);
    abstract void checkCollision(PhysicsPolygon polygon);

    public void checkCollision(PhysicsObject object)
    {
//        switch (object.getObjectType())
//        {
//            case CIRCLE:
//            {
//                checkCollision((PhysicsCircle)object);
//            }
//
//            case AABB:
//            {
//                checkCollision((PhysicsAabb) object);
//            }
//
//            case POLYGON:
//            {
//                checkCollision((PhysicsPolygon) object);
//            }
//
//            default:
//            {
//
//            }
//        }
        checkCollision((PhysicsCircle)object);
    }

    public void applyImpulse(Vec2 impulse)
    { ;
        if (mass != 0) {
            velocity.add(impulse);
        }
    }

    /* Internal common methods */
    public void update(float time)
    {
        if (mass != 0) {

            applyDrag();
            Vec2 acceleration = force.div(mass);
            velocity.add(acceleration.mult(time));
            force = new Vec2(0,0);
        }
    }

    public void move(float time)
    {
        Vec2 displacement = velocity.copy().mult(time);
        position.add(displacement);

        nextCollisionTime = nextCollisionTime - time;
    }

    public void applyForce(float xForce, float yForce)
    {
        force.addX(xForce);
        force.addY(yForce);
    }

    public void applyForce(Vec2 force)
    {
        this.force.add(force);
    }

    /* private functions */
    private void applyDrag()
    {
        // TODO Drag = coeff * (density * velocity^2)/2 * Cross sectional area
        force.add(velocity.copy().mult(-PhysicsSettings.getDrag()));
    }

    void intakeCollisionTime(float collisionTime)
    {
        if (nextCollisionTime > collisionTime)
        {
            nextCollisionTime = collisionTime;
        }
    }


    /* Public getters */
    public Vec2 getPosition(){ return position.copy(); }
    public float getX(){ return position.getX(); }
    public float getY(){ return position.getY(); }
    public Vec2 getVelocity(){ return velocity.copy(); }
    public float getXVelocity(){ return velocity.getX(); }
    public float getYVelocity(){ return velocity.getY(); }
    public float getMass(){ return mass; }
    public Material getMaterial(){ return material; }
    public float getInvertedMass()
    {
        if (mass == 0) return 0;
        return 1.0f / mass;
    }
    public OType getObjectType(){ return objectType; }

    public void setPos(Vec2 newPos){ this.position = newPos; }



    public enum OType
    {
        AABB,
        CIRCLE,
        POLYGON
    }
}
