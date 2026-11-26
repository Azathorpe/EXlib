package com.azathorpe.utils.struct.opengl;

import static org.lwjgl.opengl.GL30C.*;

/**
 * VAO 实例化自动创建VAOID
 * 不提供自动绑定, 使用前请调用{@link #bindVAO()}
 */
public class VAO {
    private final int id;

    public VAO() {
        id = glGenVertexArrays();
    }

    public int getId() {
        return id;
    }

    /**
     * 绑定 VAO
     */
    public void bindVAO() {
        glBindVertexArray(id);
    }

    /**
     * 解绑 VAO
     */
    public void unbindVAO() {
        glBindVertexArray(0);
    }

    /**
     * 删除 VAO
     */
    public void deleteVAO(){
        glDeleteVertexArrays(id);
    }
}
