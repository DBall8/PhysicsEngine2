import gameEngine.GameEngine;
import gameEngine.Settings;
import gameObjects.GameObject;
import gameObjects.Player;
import javafx.scene.paint.Color;
import physicsEngine.Material;
import physicsEngine.PhysicsWorld;
import physicsEngine.customMath.Vec2;
import physicsEngine.customMath.shape.Circle;
import physicsEngine.objects.PhysicsAabb;
import physicsEngine.objects.PhysicsCircle;
import physicsEngine.objects.PhysicsPolygon;

import java.util.ArrayList;

public class main extends GameEngine
{

    private final static int SCREEN_WIDTH = 1800;
    private final static int SCREEN_HEIGHT = 900;

    final static float FRAME_RATE = 120;
    final static float TIME_STEP = 1.0f / FRAME_RATE;

    private PhysicsWorld physicsWorld;
    private Player player;
    private ArrayList<GameObject> objects;

    @Override
    protected void onInitialize()
    {
        // Code here is called before anything is initialized
        setFramesPerSecond(FRAME_RATE);
        setWindowWidth(SCREEN_WIDTH);
        setWindowHeight(SCREEN_HEIGHT);
        physicsWorld = new PhysicsWorld();
    }

    @Override
    protected void onStart()
    {
        // Code here is called before the window is displayed.
        PhysicsCircle physicsObject = physicsWorld.addCircle(100, 100, 50, Material.WOOD);
//        PhysicsAabb physicsObject = physicsWorld.addAABB(100, 100, 100, 100, Material.WOOD);

//        double[] points = new double[]
//        {
//            0, -50,
//            25, 50,
//            -25, 50
//        };
//        PhysicsPolygon physicsObject = physicsWorld.addPolygon(100, 100, points, 3, Material.WOOD);


        player = new Player(physicsObject, getUserInputHandler());
        addEntity(player);
        addCircles(10, 5, 20);
//        addPolygons(10, 20, 100, 3, 8);
    }

    @Override
    protected void onUpdateStart()
    {
        // Code here is called on every frame of the game, before entities are moved and drawn
        physicsWorld.update(TIME_STEP);
    }

    @Override
    protected void onUpdateFinish()
    {
        // Code here is called on every frame of the game, after entities are moved and drawn
    }

    void addCircles(int numCircles, int minRadius, int maxRadius)
    {
        while (numCircles > 0)
        {
            float radius = (float)(Math.random() * (maxRadius - minRadius) + minRadius);
            float x = (float)Math.random() * SCREEN_WIDTH;
            float y = (float)Math.random() * SCREEN_HEIGHT;
            PhysicsCircle c = physicsWorld.addCircle(x, y, radius, Material.WOOD);
            GameObject obj = new GameObject(c, Color.BLUE);
            addEntity(obj);

            numCircles--;
        }
    }

    void addRects(int numRects, int minSize, int maxSize)
    {
        while (numRects > 0)
        {
            float width = (float)(Math.random() * (maxSize - minSize) + minSize);
            float height = (float)(Math.random() * (maxSize - minSize) + minSize);
            float x = (float)Math.random() * SCREEN_WIDTH;
            float y = (float)Math.random() * SCREEN_HEIGHT;
            PhysicsAabb r = physicsWorld.addAABB(x, y, width, height, Material.WOOD);
            GameObject obj = new GameObject(r, Color.BLUE);
            addEntity(obj);

            numRects--;
        }
    }

    void addTriangles(int numTriangles, int minSize, int maxSize)
    {
        while (numTriangles > 0)
        {
            float x = (float)Math.random() * SCREEN_WIDTH;
            float y = (float)Math.random() * SCREEN_HEIGHT;
            float dx1 = (float)(Math.random() * (maxSize - minSize) + minSize) * ((Math.random() > 0.5) ? 1.0f : -1.0f);
            float dy1 = (float)(Math.random() * (maxSize - minSize) + minSize) * ((Math.random() > 0.5) ? 1.0f : -1.0f);
            float dx2 = (float)(Math.random() * (maxSize - minSize) + minSize) * ((Math.random() > 0.5) ? 1.0f : -1.0f);
            float dy2 = (float)(Math.random() * (maxSize - minSize) + minSize) * ((Math.random() > 0.5) ? 1.0f : -1.0f);

            double[] points = new double[]
            {
                0, 0,
                dx1, dy1,
                dx2, dy2
            };

            PhysicsPolygon p = physicsWorld.addPolygon(x, y, points, 3, Material.WOOD);
            GameObject obj = new GameObject(p, Color.BLUE);
            addEntity(obj);

            numTriangles--;
        }
    }

    void addPolygons(int numPolygons, int minSize, int maxSize, int maxSides, int minSides)
    {
        while (numPolygons > 0)
        {
            int numSides = (int)(Math.random() * (maxSides - minSides)) + minSides;
            double[] polyPoints = new double[numSides*2];
            float x =(float)Math.random() * SCREEN_WIDTH;
            float y = (float)Math.random() * SCREEN_HEIGHT;

            float angle = 0;
            float anglePortion = (float)(2*Math.PI / (numSides));
            for (int i=0 ;i<numSides; i++)
            {
                float distance = (float)(Math.random() * (maxSize - minSize) + minSize);
                Vec2 delta = new Vec2((float)Math.sin(angle) * distance, (float)-Math.cos(angle) * distance);
                polyPoints[2*i] = delta.getX() + x;
                polyPoints[2*i + 1] = delta.getY() + y;

                angle += anglePortion;
            }

            PhysicsPolygon p = physicsWorld.addPolygon(x, y, polyPoints, numSides, Material.WOOD);
            GameObject obj = new GameObject(p, Color.BLUE);
            addEntity(obj);

            numPolygons--;
        }
    }
}
