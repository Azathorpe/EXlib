package com.azathorpe.utils.struct.game;

import com.azathorpe.utils.struct.opengl.VAO;
import com.azathorpe.utils.struct.opengl.VBO;

import java.nio.FloatBuffer;

/**
 * 定义物体的位置
 */
public class Mesh {
    private final VAO vao;
    private final VBO vbo;

    /**
     * look at:
     * {@link VBO#VBO(int, float[], int)}
     */
    public Mesh(int target, float[] vertex, int usage) {
        vao = new VAO();
        vbo = new VBO(target, vertex, usage);
    }

    /**
     * look at:
     * {@link VBO#VBO(int, float[], int)}
     */
    public Mesh(int target, FloatBuffer vertex, int usage) {
        vao = new VAO();
        vbo = new VBO(target, vertex, usage);
    }

    public Mesh(VAO vao, VBO vbo) {
        this.vao = vao;
        this.vbo = vbo;
    }

    public int getVaoId() {
        return vao.getId();
    }

    public int getVboId() {
        return vbo.getId();
    }

    public VAO getVao() {
        return vao;
    }

    public VBO getVbo() {
        return vbo;
    }
}
