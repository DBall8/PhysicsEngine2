package physicsEngine;

import physicsEngine.customMath.CustomMath;
import physicsEngine.customMath.Vec2;
import physicsEngine.objects.PhysicsObject;

public class Collision
{
    private final static float POSITION_CORRECTION_PERCENT = 0.2f; // the percent to use when correcting the position
    // of overlapped objects
    private final static float MIN_POSITION_CORRECTION = 0.1f; // Minimum position correction to use
    private final static short MAX_CONTACT_POINTS = 2;

    private final static boolean ENABLE_ROTATION = false;
    private final static boolean ENABLE_FRICTION = false;

    PhysicsObject object1;
    PhysicsObject object2;
    Vec2 normal;
    float penetration;

    Vec2[] contactPoints = new Vec2[MAX_CONTACT_POINTS];
    int numContactPoints = 0;

    public Collision(PhysicsObject o1, PhysicsObject o2, Vec2 normal, float penetration)
    {
        this.object1 = o1;
        this.object2 = o2;
        this.normal = normal;
        this.penetration = penetration;
    }

    public PhysicsObject getObject1() {
        return object1;
    }

    public PhysicsObject getObject2() {
        return object2;
    }

    public void addContactPoint(Vec2 point)
    {
        if (numContactPoints >= MAX_CONTACT_POINTS) return;

        contactPoints[numContactPoints] = point;
        numContactPoints++;
    }

    public void resolve()
    {
        // Two infinite mass objects cannot move
        if (object1.getInvertedMass() + object2.getInvertedMass() == 0) return;

        // Remove any overlap
        correctPosition();

        applyImpulse();

//        object1.resolve();
//        object2.resolve();
    }

    void applyImpulse() {

        Material o1Material = object1.getMaterial();
        Material o2Material = object2.getMaterial();

        // Use the average restitution
        float e = o1Material.getRestitution() + o2Material.getRestitution() / 2.0f;
        // get the coefficient of friction to use for this collision
        float muStatic = (o1Material.getStaticFriction() + o2Material.getStaticFriction()) / 2.0f;
        float muDynamic = (o1Material.getDynamicFriction() + o2Material.getDynamicFriction()) / 2.0f;

            // Get the vector formed by the two circles' velocities
            Vec2 relativeVelocity = CustomMath.vecSub(object2.getVelocity(), object1.getVelocity());
            // Get the relative velocity along the normal vector
            float normalVelocity = CustomMath.dotProduct(normal, relativeVelocity);

            // If the relative velocity along the normal is positive, the objects are already moving apart, do nothing
            if (normalVelocity >= 0) return;

            // Get the impulse scalar from the normal velocity, the restitution, and the inverted masses
            // j times normal is normal force
            float j = -(1.0f + e) * normalVelocity;
            float inverseMassSum = object1.getInvertedMass() + object2.getInvertedMass();

            j /= inverseMassSum;
            //j /= numContactPoints;

            // Distribute the impulse across the normal of the collision
            Vec2 resolutionVec = normal.copy().mult(j);

            Vec2 oppositeImpulse = resolutionVec.copy().mult(-1.0f);
            if(!ENABLE_FRICTION) {
                object1.applyImpulse(oppositeImpulse);
                object2.applyImpulse(resolutionVec);
                return;
            }
//
//            Vec2 tangent = Formulas.vecMult(normal, -1.0f * Formulas.dotProduct(relativeVelocity, normal));
//            tangent = Formulas.vecAdd(relativeVelocity, tangent);
//            tangent.normalize();
//
//            // Recalculate j but using the tangent of the collision normal
//            float jF = -1.0f * Formulas.dotProduct(relativeVelocity, tangent);
//            jF /= inverseMassSum;
//            //jF /= numContactPoints;
//
//            // If the the absolute value of jF is less than the static coefficient times j
//            Vec2 frictionVec;
//            if (Math.abs(jF) < muStatic * j) {
//                frictionVec = tangent.mult(jF);
//            }
//            // Otherwise use j and dynamic friction
//            else {
//                frictionVec = tangent.mult(-1.0f * j * muDynamic);
//            }
//
//            oppositeImpulse.add(Formulas.vecMult(frictionVec, -1.0f));
//            resolutionVec.add(frictionVec);
//
//            if (ENABLE_ROTATION) {
//                o1.applyImpulse(oppositeImpulse, contactA);
//                o2.applyImpulse(resolutionVec, contactB);
//            } else {
//                o1.applyImpulse(oppositeImpulse, new Vec2(0, 0));
//                o2.applyImpulse(resolutionVec, new Vec2(0, 0));
//            }

    }

    /**
     * Move two overlapping objects apart a bit to correct the overlapping
     */
    private void correctPosition()
    {
        // If the overlap is small enough, ignore it
        // TODO time scale this
        float minPositionCorrection = MIN_POSITION_CORRECTION;
        if(penetration <= minPositionCorrection) return;

        float correctionAmount;
        correctionAmount = (penetration / (object1.getInvertedMass() + object2.getInvertedMass())) * POSITION_CORRECTION_PERCENT;

        Vec2 correctionVector = normal.copy();
        correctionVector.mult(correctionAmount);

        Vec2 o1NewPos = object1.getPosition().sub(new Vec2(object1.getInvertedMass() * correctionVector.getX(),
                                                           object1.getInvertedMass() * correctionVector.getY()));
        object1.setPos(o1NewPos);

        Vec2 o2NewPos = object2.getPosition().add(new Vec2(object2.getInvertedMass() * correctionVector.getX(),
                                                            object2.getInvertedMass() * correctionVector.getY()));
        object2.setPos(o2NewPos);
    }

    public void reset()
    {
        this.object1 = null;
        this.object2 = null;
    }
}
