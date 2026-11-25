package com.azathorpe.utils.struct.opengl;

import static org.lwjgl.opengl.GL15C.*;

/**
 * VBO 实例化时 自动创建VBOID
 */
public class VBO {
    private final int id;
    private final int target;


    public VBO(int target, float[] vertex, int usage) {
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
