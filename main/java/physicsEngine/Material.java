package physicsEngine;

public class Material
{
    float density;
    float restitution;
    float staticFriction;
    float dynamicFriction;

    public Material(float density, float restitution, float staticFriction, float dynamicFriction)
    {
        this.density = density;
        this.restitution = restitution;
        this.staticFriction = staticFriction;
        this.dynamicFriction = dynamicFriction;
    }

    public float getDensity(){ return density; }
    public float getRestitution(){ return restitution; }
    public float getStaticFriction(){ return staticFriction; }
    public float getDynamicFriction() { return dynamicFriction; }

    public final static Material METAL = new Material(8f, 0.1f, 0.5f, 0.5f);
    public final static Material WOOD = new Material(1.5f, 0.5f, 0.5f, 0.5f);
    public final static Material RUBBER = new Material(1.2f, 0.8f, 0.5f, 0.5f);
}


