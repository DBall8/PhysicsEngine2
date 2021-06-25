package physicsEngine.customMath;

public class RotationMatrix
{
    private float angleRads;
    private float[][] matrix = new float[2][2];

    public RotationMatrix(float angleRads)
    {
        this.angleRads = angleRads;
        this.matrix[0][0] = (float)Math.cos(angleRads);
        this.matrix[0][1] = (float)-Math.sin(angleRads);
        this.matrix[1][0] = (float)Math.sin(angleRads);
        this.matrix[1][1] = (float)Math.cos(angleRads);
    }

    public float getAngleRads(){ return this.angleRads; }
    public float getAngleDegrees(){ return CustomMath.toDegrees(this.angleRads); }

    public Vec2 rotateVector(Vec2 vector)
    {
        return new Vec2((vector.getX() * this.matrix[0][0]) + (vector.getY() * this.matrix[0][1]),
                        (vector.getX() * this.matrix[1][0]) + (vector.getY() * this.matrix[1][1]));
    }
}
