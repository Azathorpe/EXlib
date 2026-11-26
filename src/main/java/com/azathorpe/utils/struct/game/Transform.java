package com.azathorpe.utils.struct.game;

import org.joml.Matrix4f;

public class Transform {
    Matrix4f model;

    public Transform() {
        model = new Matrix4f();
    }

    public Transform(Matrix4f model) {
        this.model = model;
    }

    public void transform(final float x, final float y, final float z) {
        model.translate(x, y, z);
    }

    public Matrix4f getModel() {
        return model;
    }

    public void setModel(Matrix4f model) {
        this.model = model;
    }
}
