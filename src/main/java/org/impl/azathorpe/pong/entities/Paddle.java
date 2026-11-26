package org.impl.azathorpe.pong.entities;

import com.azathorpe.utils.struct.game.GameObject;
import com.azathorpe.utils.struct.game.Material;
import com.azathorpe.utils.struct.game.Mesh;
import com.azathorpe.utils.struct.game.Transform;
import com.azathorpe.utils.struct.opengl.VAO;
import com.azathorpe.utils.struct.opengl.VBO;
import com.azathorpe.utils.struct.opengl.VertexAttribPointer;
import com.azathorpe.utils.struct.opengl.shader.FragmentShader;
import com.azathorpe.utils.struct.opengl.shader.Program;
import com.azathorpe.utils.struct.opengl.shader.VertexShader;
import org.joml.Matrix4f;

import java.nio.FloatBuffer;
import java.nio.file.Path;
import java.util.Arrays;

import static org.lwjgl.opengl.GL11C.GL_FLOAT;
import static org.lwjgl.opengl.GL15C.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15C.GL_STATIC_DRAW;

public class Paddle extends GameObject {
    float[] vertex = new float[]{
            // Upper Right
            0.01f, 0.1f, 0f,
            -0.01f, 0.1f, 0f,
            0.01f, -0.1f, 0f,
            // Down Left
            -0.01f, -0.1f, 0f,
            0.01f, -0.1f, 0f,
            -0.01f, 0.1f, 0f,
    };


    float[] color = new float[]{
            0.583f, 0.771f, 0.014f,
            0.609f, 0.115f, 0.436f,
            0.327f, 0.483f, 0.844f,
            0.822f, 0.569f, 0.201f,
            0.435f, 0.602f, 0.223f,
            0.310f, 0.747f, 0.185f
    };

    public Paddle() {
        this(0, 0, 0);
    }

    public Paddle(float r, float g, float b) {
        for (int i = 0; i < color.length; i += 3) {
            color[i] = r;
            color[i + 1] = g;
            color[i + 2] = b;
        }

        this.addComponent(new Mesh(
                vertex.length / 3,
                new VBO(
                        GL_ARRAY_BUFFER, vertex, GL_STATIC_DRAW,
                        new VertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0)
                ),
                new VBO(
                        GL_ARRAY_BUFFER, color, GL_STATIC_DRAW,
                        new VertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0)
                )
        ));

        this.addComponent(new Material(
                new Program(
                        new VertexShader(Path.of("/home/azathorpe/Projects/LWJGL/LWJGL_First/src/main/java/org/impl/azathorpe/pong/resources/vertex.GLSL")),
                        new FragmentShader(Path.of("/home/azathorpe/Projects/LWJGL/LWJGL_First/src/main/java/org/impl/azathorpe/pong/resources/frag.GLSL"))
                )
        ));

        this.addComponent(new Transform());
    }
}
