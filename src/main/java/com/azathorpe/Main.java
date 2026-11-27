package com.azathorpe;

import com.azathorpe.utils.struct.game.gameobj.GameObject;
import com.azathorpe.utils.struct.game.gameobj.Material;
import com.azathorpe.utils.struct.game.gameobj.Mesh;
import com.azathorpe.utils.struct.game.gameobj.Transform;
import com.azathorpe.utils.struct.game.camera.Camera;
import com.azathorpe.utils.struct.game.camera.Scene;
import com.azathorpe.utils.struct.game.gameobj.components.UpdateComponent;
import com.azathorpe.utils.struct.opengl.VAO;
import com.azathorpe.utils.struct.opengl.VBO;
import com.azathorpe.utils.struct.opengl.VertexAttribPointer;
import com.azathorpe.utils.struct.opengl.shader.FragmentShader;
import com.azathorpe.utils.struct.opengl.shader.Program;
import com.azathorpe.utils.struct.opengl.shader.VertexShader;
import org.joml.Vector3f;
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
import static org.lwjgl.opengl.GL20C.glGetUniformLocation;
import static org.lwjgl.opengl.GL20C.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20C.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30C.glGenVertexArrays;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Main {

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

        float[] fragColor = {
                0.583f, 0.771f, 0.014f,
                0.609f, 0.115f, 0.436f,
                0.327f, 0.483f, 0.844f,
                0.822f, 0.569f, 0.201f,
                0.435f, 0.602f, 0.223f,
                0.310f, 0.747f, 0.185f,
                0.597f, 0.770f, 0.761f,
                0.559f, 0.436f, 0.730f,
                0.359f, 0.583f, 0.152f,
                0.483f, 0.596f, 0.789f,
                0.559f, 0.861f, 0.639f,
                0.195f, 0.548f, 0.859f,
                0.014f, 0.184f, 0.576f,
                0.771f, 0.328f, 0.970f,
                0.406f, 0.615f, 0.116f,
                0.676f, 0.977f, 0.133f,
                0.971f, 0.572f, 0.833f,
                0.140f, 0.616f, 0.489f,
                0.997f, 0.513f, 0.064f,
                0.945f, 0.719f, 0.592f,
                0.543f, 0.021f, 0.978f,
                0.279f, 0.317f, 0.505f,
                0.167f, 0.620f, 0.077f,
                0.347f, 0.857f, 0.137f,
                0.055f, 0.953f, 0.042f,
                0.714f, 0.505f, 0.345f,
                0.783f, 0.290f, 0.734f,
                0.722f, 0.645f, 0.174f,
                0.302f, 0.455f, 0.848f,
                0.225f, 0.587f, 0.040f,
                0.517f, 0.713f, 0.338f,
                0.053f, 0.959f, 0.120f,
                0.393f, 0.621f, 0.362f,
                0.673f, 0.211f, 0.457f,
                0.820f, 0.883f, 0.371f,
                0.982f, 0.099f, 0.879f
        };

        float[] vertex = {
                -1.0f, -1.0f, -1.0f, // triangle 1 : begin
                -1.0f, -1.0f, 1.0f,
                -1.0f, 1.0f, 1.0f, // triangle 1 : end
                1.0f, 1.0f, -1.0f, // triangle 2 : begin
                -1.0f, -1.0f, -1.0f,
                -1.0f, 1.0f, -1.0f, // triangle 2 : end
                1.0f, -1.0f, 1.0f,
                -1.0f, -1.0f, -1.0f,
                1.0f, -1.0f, -1.0f,
                1.0f, 1.0f, -1.0f,
                1.0f, -1.0f, -1.0f,
                -1.0f, -1.0f, -1.0f,
                -1.0f, -1.0f, -1.0f,
                -1.0f, 1.0f, 1.0f,
                -1.0f, 1.0f, -1.0f,
                1.0f, -1.0f, 1.0f,
                -1.0f, -1.0f, 1.0f,
                -1.0f, -1.0f, -1.0f,
                -1.0f, 1.0f, 1.0f,
                -1.0f, -1.0f, 1.0f,
                1.0f, -1.0f, 1.0f,
                1.0f, 1.0f, 1.0f,
                1.0f, -1.0f, -1.0f,
                1.0f, 1.0f, -1.0f,
                1.0f, -1.0f, -1.0f,
                1.0f, 1.0f, 1.0f,
                1.0f, -1.0f, 1.0f,
                1.0f, 1.0f, 1.0f,
                1.0f, 1.0f, -1.0f,
                -1.0f, 1.0f, -1.0f,
                1.0f, 1.0f, 1.0f,
                -1.0f, 1.0f, -1.0f,
                -1.0f, 1.0f, 1.0f,
                1.0f, 1.0f, 1.0f,
                -1.0f, 1.0f, 1.0f,
                1.0f, -1.0f, 1.0f
        };

        GameObject gameObject = new GameObject();
        gameObject.addComponent(new Mesh(
                new VAO(),
                vertex.length / 3,
                new VBO(
                        GL_ARRAY_BUFFER,
                        vertex,
                        GL_STATIC_DRAW,
                        new VertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0)),
                new VBO(
                        GL_ARRAY_BUFFER,
                        fragColor,
                        GL_STATIC_DRAW,
                        new VertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0)
                )
        ));
        gameObject.addComponent(new Material(
                new Program(
                        new VertexShader(Path.of("/home/azathorpe/Projects/LWJGL/LWJGL_First/src/main/resources/VertexShader/vert.GLSL")),
                        new FragmentShader(Path.of("/home/azathorpe/Projects/LWJGL/LWJGL_First/src/main/resources/FragmentShader/frag.GLSL"))
                )
        ));

        gameObject.addComponent(new Transform());

        gameObject.getComponent(Transform.class).position.z = 1;
        gameObject.getComponent(Transform.class).rotation.set(0, 1, 0);

        gameObject.addComponent(new UpdateComponent() {
            @Override
            public void input(long window) {
                GameObject object = getOwner();
                Transform transform = object.getComponent(Transform.class);

                if (glfwGetKey(window, GLFW_KEY_W) == GLFW_PRESS)
                    transform.position.add(0, 0.1f, 0);
                if (glfwGetKey(window, GLFW_KEY_S) == GLFW_PRESS)
                    transform.position.add(0, -0.1f, 0);
                if (glfwGetKey(window, GLFW_KEY_A) == GLFW_PRESS)
                    transform.position.add(-0.1f, 0, 0);
                if (glfwGetKey(window, GLFW_KEY_D) == GLFW_PRESS)
                    transform.position.add(0.1f, 0, 0);
                if (glfwGetKey(window, GLFW_KEY_Q) == GLFW_PRESS)
                    transform.rotation.rotateX((float) Math.toRadians(1.0f));
                if (glfwGetKey(window, GLFW_KEY_E) == GLFW_PRESS)
                    transform.rotation.rotateY((float) Math.toRadians(1.0f));
                if (glfwGetKey(window, GLFW_KEY_R) == GLFW_PRESS)
                    transform.rotation.rotateZ((float) Math.toRadians(1.0f));
            }
        });

        Scene scene = new Scene();
        scene.addGameObject(gameObject);

        scene.setCamera(new Camera());
        scene.getCamera()
                .setProjection(new Matrix4f()
                        .perspective(
                                (float) Math.toRadians(45.0f),
                                (float) WINDOW_WIDTH / (float) WINDOW_HEIGHT,
                                0.1f,
                                100.0f
                        ));
        scene.getCamera()
                .setView(new Matrix4f()
                        .lookAt(
                                new Vector3f(4, 3, 3),
                                new Vector3f(0, 0, 0),
                                new Vector3f(0, 1, 0)
                        ));

        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            scene.render();
            scene.update(window);

            glfwSwapBuffers(window); // swap the color buffers

            glfwPollEvents();
        }
    }

    public static void main(String[] args) {
        new Main().run();
    }

}