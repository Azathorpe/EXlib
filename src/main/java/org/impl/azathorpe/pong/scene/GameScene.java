package org.impl.azathorpe.pong.scene;

import com.azathorpe.utils.struct.game.gameobj.GameObject;
import com.azathorpe.utils.struct.game.gameobj.components.base.Transform;
import com.azathorpe.utils.struct.game.gameobj.components.UpdateComponent;
import com.azathorpe.utils.struct.game.manage.game.Game;
import com.azathorpe.utils.struct.game.manage.game.Scene;
import org.impl.azathorpe.pong.entities.Ball;
import org.impl.azathorpe.pong.entities.Paddle;
import org.lwjgl.glfw.GLFW;

import java.awt.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_UP;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;

public class GameScene extends Scene {
    float playerSpeed = 0.5f;

    public GameScene(Game game) {
        super(game);
    }

    @Override
    public void putGameObject(Game game) {
        Paddle player1 = new Paddle(150, 0, 0);
        player1.addComponent(new UpdateComponent() {
            @Override
            public void input(long window) {
                GameObject parent = getOwner();
                Transform transform = parent.getComponent(Transform.class);
                if (GLFW.glfwGetKey(window, GLFW_KEY_W) == GLFW_PRESS)
                    if (transform.position.y <= 0.9)
                        transform.position.add(0, 0.1f * playerSpeed, 0);
                if (GLFW.glfwGetKey(window, GLFW_KEY_S) == GLFW_PRESS)
                    if (transform.position.y >= -0.9)
                        transform.position.add(0, -0.1f * playerSpeed, 0);
            }
        });
        player1.getComponent(Transform.class).position.set(-0.9f, 0, 0);

        Paddle player2 = new Paddle(0, 150, 0);
        player2.addComponent(new UpdateComponent() {
            @Override
            public void input(long window) {
                GameObject parent = getOwner();
                Transform transform = parent.getComponent(Transform.class);
                if (GLFW.glfwGetKey(window, GLFW_KEY_UP) == GLFW_PRESS)
                    if (transform.position.y <= 0.9)
                        transform.position.add(0, 0.1f * playerSpeed, 0);
                if (GLFW.glfwGetKey(window, GLFW_KEY_DOWN) == GLFW_PRESS)
                    if (transform.position.y >= -0.9)
                        transform.position.add(0, -0.1f * playerSpeed, 0);
            }
        });
        player2.getComponent(Transform.class).position.set(0.9f, 0, 0);

        Ball ball = new Ball(0.02f, 24, 24, new Color(10, 120, 150));

        float aspect = (float) game.getWindow().WINDOW_WIDTH / (float) game.getWindow().WINDOW_HEIGHT;
        this.getCamera()
                .getProjection().ortho(
                        -aspect, aspect,
                        -1, 1,
                        -1, 1
                );
        this.addGameObject(player1, player2, ball);
    }
}
