package com.azathorpe.utils.struct.opengl;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL15C.*;

/**
 * VBO 实例化时 自动创建VBOID
 */
public class VBO {
    private final int id;
    private final int target;


    /**
     * @param target <br>
     *               {@link org.lwjgl.opengl.GL15C#GL_ARRAY_BUFFER}
     *               存放顶点属性数据（位置、颜色、UV 等）。<br>
     *               {@link org.lwjgl.opengl.GL15C#GL_ELEMENT_ARRAY_BUFFER}
     *               存放索引数据（EBO）。<br>
     * @param vertex 数组
     * @param usage  <br>
     *               {@link org.lwjgl.opengl.GL15C#GL_STATIC_DRAW}
     *               静态数据，只上传一次。<br>
     *               {@link org.lwjgl.opengl.GL15C#GL_DYNAMIC_DRAW}
     *               动态数据，需要频繁更新。<br>
     */
    public VBO(int target, float[] vertex, int usage) {
        id = glGenBuffers();
        this.target = target;
        bindVBO();
        glBufferData(target, vertex, usage);
    }

    /**
     * @param target <br>
     *               {@link org.lwjgl.opengl.GL15C#GL_ARRAY_BUFFER}
     *               存放顶点属性数据（位置、颜色、UV 等）。<br>
     *               {@link org.lwjgl.opengl.GL15C#GL_ELEMENT_ARRAY_BUFFER}
     *               存放索引数据（EBO）。<br>
     * @param vertex 数组
     * @param usage  <br>
     *               {@link org.lwjgl.opengl.GL15C#GL_STATIC_DRAW}
     *               静态数据，只上传一次。<br>
     *               {@link org.lwjgl.opengl.GL15C#GL_DYNAMIC_DRAW}
     *               动态数据，需要频繁更新。<br>
     */
    public VBO(int target, FloatBuffer vertex, int usage) {
        id = glGenBuffers();
        this.target = target;
        bindVBO();
        glBufferData(target, vertex, usage);
    }


    /**
     * 绑定 VBO
     */
    public void bindVBO() {
        glBindBuffer(target, id);
    }

    /**
     * 解绑 VBO
     */
    public void unbindVBO() {
        glBindBuffer(target, 0);
    }

    /**
     * 删除VBO
     */
    public void deleteVBO() {
        glDeleteBuffers(id);
    }

    public int getId() {
        return id;
    }
}
