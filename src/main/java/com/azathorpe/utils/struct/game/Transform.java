package com.azathorpe.utils.struct.game;

import org.joml.Matrix4f;

public class Transform {
    Matrix4f transform = new Matrix4f();

    public void transform(final float x, final float y, final float z) {
        transform.translate(x, y, z);
    }
}
