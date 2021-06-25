package gameObjects;

import gameEngine.GameEngine;
import gameEngine.userInput.KeyBinding;
import gameEngine.userInput.UserInputHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import physicsEngine.customMath.Vec2;
import physicsEngine.objects.PhysicsAabb;
import physicsEngine.objects.PhysicsCircle;
import physicsEngine.objects.PhysicsObject;

public class Player extends GameObject
{
    final static float MOVE_FORCE = 100;

    private KeyBinding up;
    private KeyBinding down;
    private KeyBinding left;
    private KeyBinding right;

    Shape shape;

    public Player(PhysicsObject object, UserInputHandler inputHandler) {
        super(object, Color.GREEN);

        up = inputHandler.createKeyBinding(KeyCode.W);
        down = inputHandler.createKeyBinding(KeyCode.S);
        left = inputHandler.createKeyBinding(KeyCode.A);
        right = inputHandler.createKeyBinding(KeyCode.D);
    }

    @Override
    public void update()
    {
        super.update();

        Vec2 force = new Vec2(0,0);
        if (up.isPressed())
        {
            force.addY(-MOVE_FORCE);
        }
        if (down.isPressed())
        {
            force.addY(MOVE_FORCE);
        }
        if (left.isPressed())
        {
            force.addX(-MOVE_FORCE);
        }
        if (right.isPressed())
        {
            force.addX(MOVE_FORCE);
        }

        object.applyForce(force);
    }
}
