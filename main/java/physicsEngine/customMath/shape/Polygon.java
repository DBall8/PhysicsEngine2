package physicsEngine.customMath.shape;

import physicsEngine.customMath.CustomMath;
import physicsEngine.customMath.RotationMatrix;
import physicsEngine.customMath.Vec2;

public class Polygon extends Shape
{
    private Vec2[] points;
    private int numPoints;
    private float angle; // degrees

    public Polygon(Vec2[] points, int numPoints)
    {
        this.points = points;
        this.numPoints = numPoints;

        this.position = getCenter();
        this.angle = 0;

        for (int i=0; i<numPoints; i++)
        {
            points[i].sub(position);
        }
    }

    public Polygon(double[] points, int numPoints)
    {
        this.points = new Vec2[numPoints];
        this.numPoints = numPoints;

        for (int i=0; i<numPoints; i++)
        {
            this.points[i] = new Vec2((float)points[2*i], (float)points[2*i+1]);
        }

        this.position = getCenter();
        this.angle = 0;

        for (int i=0; i<numPoints; i++)
        {
            this.points[i].sub(position);
        }
    }

    private Vec2 getCenter() {
        // The number of distinct triangles that the polygon can be split into
        int numTriangles = numPoints - 2;

        // Calculate the average midpoint of each composite triangle
        Vec2 averageMidpoint = new Vec2(0, 0);
        float area = 0;
        for (int i = 0; i < numTriangles; i++) {
            Triangle t = new Triangle(points[0].copy(), points[i + 1].copy(), points[i + 2].copy());
            Vec2 triangleMidpoint = t.getPos();
            float triangleArea = t.getArea();

            averageMidpoint.addX(triangleMidpoint.getX() * triangleArea);
            averageMidpoint.addY(triangleMidpoint.getY() * triangleArea);

            area += triangleArea;
        }

        averageMidpoint.div(area);
        return averageMidpoint;
    }

    public void rotate(float angleDegrees)
    {
        RotationMatrix rm = new RotationMatrix(CustomMath.toRads(angleDegrees));
        for (Vec2 p: points)
        {
            p.rotate(rm);
        }

        this.angle += angleDegrees;
    }

    public void rotateTo(float angleDegrees)
    {
        float rotation = angleDegrees - this.angle;
        rotate(rotation);
    }

    public int getNumPoints(){ return numPoints; }
    public Vec2[] getPointsRelative(){ return points; }
    public double[] getPointsRelativeDouble()
    {
        double[] pVals = new double[numPoints * 2];

        for (int i=0; i<numPoints; i++)
        {
            pVals[i*2] = points[i].getX();
            pVals[i*2 + 1] = points[i].getY();
        }

        return pVals;
    }

    public Vec2[] getPointsAbsolute()
    {
        Vec2[] pointsAbsolute = new Vec2[numPoints];
        for (int i=0; i<numPoints; i++)
        {
            pointsAbsolute[i] = new Vec2(points[i].getX() + getX(), points[i].getY() + getY());
        }
        return pointsAbsolute;
    }

    public double[] getPointsAbsoluteDouble()
    {
        double[] pVals = new double[numPoints * 2];

        for (int i=0; i<numPoints; i++)
        {
            pVals[i*2] = points[i].getX() + getX();
            pVals[i*2 + 1] = points[i].getY() + getY();
        }

        return pVals;
    }

    public float getArea()
    {
        int numTriangles = numPoints - 2;

        // Calculate the area of the polygon by adding the area of each triangle that constructs it
        float area = 0;
        for (int i = 0; i < numTriangles; i++) {
            Triangle t = new Triangle(points[0].copy(), points[i + 1].copy(), points[i + 2].copy());

            float triangleArea = t.getArea();
            area += triangleArea;
        }

        return area;
    }
}
