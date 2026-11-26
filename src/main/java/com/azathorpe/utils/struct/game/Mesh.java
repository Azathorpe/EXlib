package com.azathorpe.utils.struct.game;

import com.azathorpe.utils.struct.opengl.VAO;
import com.azathorpe.utils.struct.opengl.VBO;
import com.azathorpe.utils.struct.opengl.VertexAttribPointer;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.lwjgl.opengl.GL20C.glEnableVertexAttribArray;

/**
 * 定义物体的位置
 */
public class Mesh {
    private final VAO vao;
    private final List<VBO> vbos = new ArrayList<>();

    /**
     * look at:
     * {@link VBO#VBO}
     */
    public Mesh(int target, float[] vertex, int usage, VertexAttribPointer vap) {
        this(new VAO(), new VBO(target, vertex, usage, vap));
    }

    /**
     * look at:
     * {@link VBO#VBO}
     */
    public Mesh(int target, FloatBuffer vertex, int usage, VertexAttribPointer vap) {
        this(new VAO(), new VBO(target, vertex, usage, vap));
    }

    public Mesh(VAO vao, VBO... vbos) {
        this.vao = vao;
        this.vbos.addAll(List.of(vbos));

        // VAO VBO创建完之后 我们就可以加载顶点数组了
        loadVertex();
    }

    private void loadVertex() {
        // 保证vbo按照index排序
        vbos.sort(Comparator.comparingInt(o -> o.getVap().getIndex()));

        // 绑定VAO
        this.vao.bindVAO();

        // 启用顶点数组
        for (VBO vbo : vbos) {
            // 启用顶点数组
            glEnableVertexAttribArray(vbo.getVap().getIndex());
            // 加载VertexAttribArray
            vbo.setVAP();
        }
    }

    public int getVaoId() {
        return vao.getId();
    }

    public List<Integer> getVboIds() {
        List<Integer> ids = new ArrayList<>();
        for (VBO vbo : vbos)
            ids.add(vbo.getId());
        return ids;
    }

    public VAO getVao() {
        return vao;
    }

    public List<VBO> getVbos() {
        return vbos;
    }
}
