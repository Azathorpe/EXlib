package com.azathorpe.utils.struct.game.manage.game;

import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;

/**
 * 我们使用Game类来管理所有的输入 输出 渲染...
 */
public abstract class Game implements IGame {
    SceneManager sceneManager = new SceneManager(null);
    Window window = new Window();
    Input input = new Input();

    @Override
    public final void initGame() {
        // 系统级初始化
        window.run();
        GL.createCapabilities();
        // 设置视点
        try (MemoryStack stack = stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            glfwGetFramebufferSize(getWindowHandle(), w, h);   // ← 帧缓冲尺寸，不是窗口尺寸
            glViewport(0, 0, w.get(0), h.get(0));
        }

        glfwSetFramebufferSizeCallback(getWindowHandle(), (win, width, height) -> {
            glViewport(0, 0, width, height);
        });

        input.init(window, () -> this);
        // 用户级初始化, 用于执行用户自己的逻辑什么的
        init();

        // 之后进入游戏主循环
        loop();
    }

    protected abstract void init();

    @Override
    public void loop() {
        while (!glfwWindowShouldClose(getWindow().getHandle())) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            getCurrentScene().render();
            getCurrentScene().update(getWindow());

            getSceneManager().nxtFrame();

            glfwSwapBuffers(getWindow().getHandle()); // swap the color buffers

            glfwPollEvents();
        }

        // 退出游戏 清理资源
        quit();
    }

    @Override
    public void quit() {

    }

    @Override
    public SceneManager getSceneManager() {
        return sceneManager;
    }

    @Override
    public Scene getCurrentScene() {
        return sceneManager.getCurrentScene();
    }

    @Override
    public Input getInput() {
        return input;
    }

    @Override
    public Window getWindow() {
        return window;
    }

    @Override
    public long getWindowHandle() {
        return window.getHandle();
    }
}
