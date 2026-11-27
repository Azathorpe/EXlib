package org.impl.azathorpe.pong;

import com.azathorpe.utils.struct.game.gameobj.components.BoxCollider2D;
import com.azathorpe.utils.struct.game.manage.Input;
import com.azathorpe.utils.struct.game.manage.Scene;
import com.azathorpe.utils.struct.game.manage.Window;
import com.azathorpe.utils.struct.game.manage.game.Game;
import com.azathorpe.utils.struct.game.manage.game.GameContext;
import com.azathorpe.utils.struct.game.manage.game.GameStateMachine;
import org.impl.azathorpe.pong.entities.MenuBar;
import org.impl.azathorpe.pong.scene.GameScene;
import org.impl.azathorpe.pong.scene.MenuScene;
import org.impl.azathorpe.pong.state.MenuState;
import org.impl.azathorpe.pong.state.PongContext;
import org.impl.azathorpe.pong.state.RunState;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;


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
    private final Window window = new Window();

    Input input = new Input();
    Scene gameScene;
    Scene menuScene;
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
        // 设置视点
        try (MemoryStack stack = stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            glfwGetFramebufferSize(window.getHandle(), w, h);   // ← 帧缓冲尺寸，不是窗口尺寸
            glViewport(0, 0, w.get(0), h.get(0));
        }

        glfwSetFramebufferSizeCallback(window.getHandle(), (win, width, height) -> {
            glViewport(0, 0, width, height);
        });

        gameScene = new GameScene(window);
        menuScene = new MenuScene(window);

        GameContext ctx = new PongContext(menuScene, input, window, (s) -> {
            stateMachine.transitionTo(s);
            stateMachine.switchScene(gameScene);
        });
        stateMachine = new GameStateMachine(ctx, new MenuState());
    }

    @Override
    public void loop() {
        while (!glfwWindowShouldClose(window.getHandle())) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            stateMachine.render();
            stateMachine.update((float) glfwGetTime());

            glfwSwapBuffers(window.getHandle()); // swap the color buffers

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