package org.impl.azathorpe.pong.entities;

import com.azathorpe.utils.struct.game.GameObject;
import com.azathorpe.utils.struct.game.Material;
import com.azathorpe.utils.struct.game.Mesh;
import com.azathorpe.utils.struct.game.Transform;
import com.azathorpe.utils.struct.game.components.InputComponent;
import com.azathorpe.utils.struct.opengl.VBO;
import com.azathorpe.utils.struct.opengl.VertexAttribPointer;
import com.azathorpe.utils.struct.opengl.shader.FragmentShader;
import com.azathorpe.utils.struct.opengl.shader.Program;
import com.azathorpe.utils.struct.opengl.shader.VertexShader;

import java.awt.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11C.GL_FLOAT;
import static org.lwjgl.opengl.GL15C.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15C.GL_STATIC_DRAW;

public class Ball extends GameObject {
    /**
     * 生成球体顶点位置数组（每顶点 3 float）
     */
    public static float[] generatePositions(float radius, int stacks, int slices) {
        List<Float> out = new ArrayList<>();
        for (int i = 0; i <= stacks; i++) {
            float phi = (float) (-Math.PI / 2 + Math.PI * i / stacks);
            float y = (float) Math.sin(phi);
            float rCosPhi = (float) Math.cos(phi);
            for (int j = 0; j <= slices; j++) {
                float theta = (float) (2 * Math.PI * j / slices);
                float x = rCosPhi * (float) Math.cos(theta);
                float z = rCosPhi * (float) Math.sin(theta);
                // 位置
                out.add(x * radius);
                out.add(y * radius);
                out.add(z * radius);
            }
        }
        // 索引展开成三角形列表（非索引绘制）
        float[] grid = toArray(out);
        List<Float> tris = new ArrayList<>();
        int row = slices + 1;
        for (int i = 0; i < stacks; i++) {
            for (int j = 0; j < slices; j++) {
                int a = (i * row + j) * 3;
                int b = (i * row + j + 1) * 3;
                int c = ((i + 1) * row + j) * 3;
                int d = ((i + 1) * row + j + 1) * 3;
                // 三角形 1: a, c, b
                addVec(tris, grid, a);
                addVec(tris, grid, c);
                addVec(tris, grid, b);
                // 三角形 2: b, c, d
                addVec(tris, grid, b);
                addVec(tris, grid, c);
                addVec(tris, grid, d);
            }
        }
        return toArray(tris);
    }

    private static void addVec(List<Float> list, float[] src, int idx) {
        list.add(src[idx]);
        list.add(src[idx + 1]);
        list.add(src[idx + 2]);
    }

    private static float[] toArray(List<Float> l) {
        float[] a = new float[l.size()];
        for (int i = 0; i < a.length; i++) a[i] = l.get(i);
        return a;
    }

    public Ball() {
        this(0, 0, 0, Color.BLUE);
    }

    public Ball(float radius, int stacks, int slices, Color color) {
        float[] vertex = generatePositions(radius, stacks, slices);
        // gen colors
        float[] colorVertex = new float[vertex.length];

        for (int i = 0; i < colorVertex.length; i += 3) {
            colorVertex[i] = (float) color.getRed() / 255;
            colorVertex[i + 1] = (float) color.getGreen() / 255;
            colorVertex[i + 2] = (float) color.getBlue() / 255;
        }

        this.addComponent(new Mesh(
                vertex.length / 3,
                new VBO(GL_ARRAY_BUFFER, vertex, GL_STATIC_DRAW,
                        new VertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0)
                ),
                new VBO(GL_ARRAY_BUFFER, colorVertex, GL_STATIC_DRAW,
                        new VertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0))
        ));

        this.addComponent(new Material(
                new Program(
                        new VertexShader(Path.of("/home/azathorpe/Projects/LWJGL/LWJGL_First/src/main/java/org/impl/azathorpe/pong/resources/vertex.GLSL")),
                        new FragmentShader(Path.of("/home/azathorpe/Projects/LWJGL/LWJGL_First/src/main/java/org/impl/azathorpe/pong/resources/frag.GLSL"))
                )));

        this.addComponent(new Transform());

        this.addComponent(new InputComponent() {
            @Override
            public void input(long window) {

            }
        });

    }
}
