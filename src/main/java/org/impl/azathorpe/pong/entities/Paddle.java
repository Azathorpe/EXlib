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

import java.nio.file.Path;

import static org.lwjgl.opengl.GL11C.GL_FLOAT;
import static org.lwjgl.opengl.GL15C.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15C.GL_STATIC_DRAW;

public class Paddle extends GameObject {
    float[] vertex = new float[]{
            // Upper Right
            0.2f, 0.2f, 0f,
            -0.2f, 0.2f, 0f,
            0.2f, -0.2f, 0f,
            // Down Left
            -0.2f, -0.2f, 0f,
            0.2f, -0.2f, 0f,
            -0.2f, 0.2f, 0f,
    };

    public Paddle() {
        this.addComponent(new Mesh(
                vertex.length / 3,
                new VBO(
                        GL_ARRAY_BUFFER, vertex, GL_STATIC_DRAW,
                        new VertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0)
                )
        ));

        this.addComponent(new Material(
                new Program(
                        new VertexShader(Path.of("/home/azathorpe/Projects/LWJGL/LWJGL_First/src/main/java/org/impl/azathorpe/pong/resources/vertex.GLSL")),
                        new FragmentShader(Path.of("/home/azathorpe/Projects/LWJGL/LWJGL_First/src/main/java/org/impl/azathorpe/pong/resources/frag.GLSL"))
                )
        ));
    }
}
