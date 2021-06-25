package physicsEngine;

public class PhysicsSettings
{
    private static float drag = 0;

    public static void setDrag(float newDrag){ drag = newDrag; }
    public static float getDrag(){ return drag; }
}
