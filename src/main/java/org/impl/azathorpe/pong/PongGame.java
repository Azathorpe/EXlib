package org.impl.azathorpe.pong;

import com.azathorpe.utils.struct.game.gameobj.GameObject;
import com.azathorpe.utils.struct.game.gameobj.Transform;
import com.azathorpe.utils.struct.game.manage.Scene;
import com.azathorpe.utils.struct.game.gameobj.components.UpdateComponent;
import com.azathorpe.utils.struct.game.manage.Window;
import com.azathorpe.utils.struct.game.manage.game.Game;
import com.azathorpe.utils.struct.game.manage.game.GameContext;
import com.azathorpe.utils.struct.game.manage.game.GameStateMachine;
import org.impl.azathorpe.pong.entities.Ball;
import org.impl.azathorpe.pong.entities.Paddle;
import org.impl.azathorpe.pong.state.MenuState;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;


import java.awt.*;
import java.nio.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15C.glBufferData;
import static org.lwjgl.opengl.GL15C.glGenBuffers;
import static org.lwjgl.opengl.GL20C.glGetUniformLocation;
import static org.lwjgl.opengl.GL20C.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20C.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30C.glGenVertexArrays;
import static org.lwjgl.system.MemoryStack.*;

public class PongGame implements Game {
    private int WINDOW_WIDTH = 800;
    private int WINDOW_HEIGHT = 600;

    private final Window window = new Window();

    float playerSpeed = 0.5f;
    private Scene scene;
    private GameStateMachine stateMachine;

    public void run() {
        init();
        loop();
        window.quit();
    }

    @Override
    public void init() {
        window.run();
        GL.createCapabilities();

        scene = new Scene();

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

        float aspect = (float) WINDOW_WIDTH / (float) WINDOW_HEIGHT;
        scene.getCamera()
                .getProjection().ortho(
                        -aspect, aspect,
                        -1, 1,
                        -1, 1
                );
        scene.addGameObject(player1, player2, ball);

        try (MemoryStack stack = stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            glfwGetFramebufferSize(window.getWindow(), w, h);   // ← 帧缓冲尺寸，不是窗口尺寸
            glViewport(0, 0, w.get(0), h.get(0));
        }

        glfwSetFramebufferSizeCallback(window.getWindow(), (win, width, height) -> {
            glViewport(0, 0, width, height);
        });


        GameContext ctx = new PongContext(scene, window, this::setState);
        stateMachine = new GameStateMachine(ctx, new MenuState());
    }

    @Override
    public void loop() {
        while (!glfwWindowShouldClose(window.getWindow())) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            stateMachine.render();
            stateMachine.update((float) glfwGetTime());

//            scene.render();
//            scene.update(window.getWindow());

            glfwSwapBuffers(window.getWindow()); // swap the color buffers

            glfwPollEvents();
        }
    }

    @Override
    public void setState() {

    }

    public static void main(String[] args) {
        new PongGame().run();
    }

}