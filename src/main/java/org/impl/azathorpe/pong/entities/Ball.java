package org.impl.azathorpe.pong.entities;

import com.azathorpe.utils.struct.game.gameobj.GameObject;
import com.azathorpe.utils.struct.game.gameobj.Material;
import com.azathorpe.utils.struct.game.gameobj.Mesh;
import com.azathorpe.utils.struct.game.gameobj.Transform;
import com.azathorpe.utils.struct.game.gameobj.components.BoxCollider2D;
import com.azathorpe.utils.struct.game.gameobj.components.UpdateComponent;
import com.azathorpe.utils.struct.game.manage.game.Game;
import com.azathorpe.utils.struct.game.system.GameInstance;
import com.azathorpe.utils.struct.opengl.VBO;
import com.azathorpe.utils.struct.opengl.VertexAttribPointer;
import com.azathorpe.utils.struct.opengl.shader.FragmentShader;
import com.azathorpe.utils.struct.opengl.shader.Program;
import com.azathorpe.utils.struct.opengl.shader.VertexShader;
import org.impl.azathorpe.pong.scene.GameScene;
import org.joml.Vector3f;

import java.awt.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11C.GL_FLOAT;
import static org.lwjgl.opengl.GL15C.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15C.GL_STATIC_DRAW;

public class Ball extends GameObject {
    Vector3f direction = new Vector3f(-1, 0, 0);
    float ballSpeed = 0.01f;

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

        this.addComponent(new UpdateComponent() {
            @Override
            public void input(long window) {
                GameObject owner = getOwner();

                Vector3f delta = new Vector3f(direction).mul(ballSpeed);
                owner.getComponent(Transform.class).position.add(delta);

                Vector3f pos = owner.getComponent(Transform.class).position;
                // 上下边界反弹（可视区 y ∈ [-1, 1]，球半径 0.2 已烘焙进顶点）
                float bound = 1.0f - radius;   // 需要知道球的半径
                if (pos.y > bound) {
                    pos.y = bound;
                    direction.y = -direction.y;
                }
                if (pos.y < -bound) {
                    pos.y = -bound;
                    direction.y = -direction.y;
                }
                // 左右边界得分/重置（或反弹）
                if (pos.x > 1.0f || pos.x < -1.0f) {
                    /* 重置球到中心 */
                    Game instance = GameInstance.getInstance();
                    instance.getSceneManager().switchScene(new GameScene(instance));
                }
            }
        });

        this.addComponent(new BoxCollider2D() {
            @Override
            public void onEnterCollider2D(BoxCollider2D other) {
                if (other.getOwner() instanceof Paddle) {
                    System.out.println("Collider!!");
                    direction.x = -direction.x;
                }
            }
        });

        this.getComponent(BoxCollider2D.class).halfWidth = 0.01f;
        this.getComponent(BoxCollider2D.class).halfHeight = 0.01f;

    }
}
