package com.azathorpe.utils.struct.opengl;

import static org.lwjgl.opengl.GL11C.GL_FLOAT;
import static org.lwjgl.opengl.GL20C.glVertexAttribPointer;

public class VertexAttribPointer {
    int index = 0;
    int size = 3;
    int type = GL_FLOAT;
    boolean normalized = false;
    int stride = 0;
    int offset = 0;

    public VertexAttribPointer() {
    }

    public VertexAttribPointer(int index, int size, int type, boolean normalized, int stride, int offset) {
        this.index = index;
        this.size = size;
        this.type = type;
        this.normalized = normalized;
        this.stride = stride;
        this.offset = offset;
    }

    public void set(VBO vbo) {
        vbo.bindVBO();
        glVertexAttribPointer(index, size, type, normalized, stride, offset);
        vbo.unbindVBO();
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isNormalized() {
        return normalized;
    }

    public void setNormalized(boolean normalized) {
        this.normalized = normalized;
    }

    public int getStride() {
        return stride;
    }

    public void setStride(int stride) {
        this.stride = stride;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}