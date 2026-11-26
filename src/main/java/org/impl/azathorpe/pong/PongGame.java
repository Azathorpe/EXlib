package org.impl.azathorpe.pong;

import com.azathorpe.utils.struct.game.GameObject;
import com.azathorpe.utils.struct.game.Material;
import com.azathorpe.utils.struct.game.Mesh;
import com.azathorpe.utils.struct.game.Transform;
import com.azathorpe.utils.struct.game.camera.Scene;
import com.azathorpe.utils.struct.game.components.InputComponent;
import com.azathorpe.utils.struct.opengl.VAO;
import com.azathorpe.utils.struct.opengl.VBO;
import com.azathorpe.utils.struct.opengl.VertexAttribPointer;
import com.azathorpe.utils.struct.opengl.shader.FragmentShader;
import com.azathorpe.utils.struct.opengl.shader.Program;
import com.azathorpe.utils.struct.opengl.shader.VertexShader;
import org.impl.azathorpe.pong.entities.Paddle;
import org.joml.Matrix3f;
import org.joml.Vector3f;
import org.joml.Vector3fc;
import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;
import org.joml.Matrix4f;


import java.nio.*;
import java.nio.file.Path;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15C.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15C.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15C.glBufferData;
import static org.lwjgl.opengl.GL15C.glGenBuffers;
import static org.lwjgl.opengl.GL20C.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20C.glGetUniformLocation;
import static org.lwjgl.opengl.GL20C.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20C.glUseProgram;
import static org.lwjgl.opengl.GL20C.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30C.glBindVertexArray;
import static org.lwjgl.opengl.GL30C.glGenVertexArrays;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class PongGame {

    // The window handle
    private long window;

    private int WINDOW_WIDTH = 800;
    private int WINDOW_HEIGHT = 600;

    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        init();
        loop();

        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void init() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

        // Create the window
        window = glfwCreateWindow(WINDOW_WIDTH, WINDOW_HEIGHT, "Hello World!", NULL, NULL);
        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
        });

        // Get the thread stack and push a new frame
        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            if (glfwGetPlatform() != GLFW_PLATFORM_WAYLAND)
                glfwGetWindowSize(window, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            if (glfwGetPlatform() != GLFW_PLATFORM_WAYLAND)
                glfwSetWindowPos(
                        window,
                        (vidmode.width() - pWidth.get(0)) / 2,
                        (vidmode.height() - pHeight.get(0)) / 2
                );
        } // the stack frame is popped automatically

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);
    }

    private void loop() {
        GL.createCapabilities();

        float playerSpeed = 0.5f;

        Paddle player1 = new Paddle(150, 0, 0);
        player1.addComponent(new InputComponent() {
            @Override
            public void input(long window) {
                GameObject parent = getOwner();
                Transform transform = parent.getComponent(Transform.class);
                if (GLFW.glfwGetKey(window, GLFW_KEY_W) == GLFW_PRESS)
                    transform.position.add(0, 0.1f * playerSpeed, 0);
                if (GLFW.glfwGetKey(window, GLFW_KEY_S) == GLFW_PRESS)
                    transform.position.add(0, -0.1f * playerSpeed, 0);
            }
        });
        player1.getComponent(Transform.class).position.set(0.9f, 0, 0);

        Paddle player2 = new Paddle(0, 150, 0);
        player2.addComponent(new InputComponent() {
            @Override
            public void input(long window) {
                GameObject parent = getOwner();
                Transform transform = parent.getComponent(Transform.class);
                if (GLFW.glfwGetKey(window, GLFW_KEY_UP) == GLFW_PRESS)
                    transform.position.add(0, 0.1f * playerSpeed, 0);
                if (GLFW.glfwGetKey(window, GLFW_KEY_DOWN) == GLFW_PRESS)
                    transform.position.add(0, -0.1f * playerSpeed, 0);
            }
        });
        player2.getComponent(Transform.class).position.set(-0.9f, 0, 0);

        Scene scene = new Scene();
        scene.addGameObject(player1, player2);

        // Enable depth test
        glEnable(GL_DEPTH_TEST);
        // Accept fragment if it closer to the camera than the former one
        glDepthFunc(GL_LESS);

        try (MemoryStack stack = stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            glfwGetFramebufferSize(window, w, h);   // ← 帧缓冲尺寸，不是窗口尺寸
            glViewport(0, 0, w.get(0), h.get(0));
        }

        glfwSetFramebufferSizeCallback(window, (win, width, height) -> {
            glViewport(0, 0, width, height);
        });

        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            scene.render();
            scene.input(window);

            glfwSwapBuffers(window); // swap the color buffers

            glfwPollEvents();
        }
    }

    public static void main(String[] args) {
        new PongGame().run();
    }

}