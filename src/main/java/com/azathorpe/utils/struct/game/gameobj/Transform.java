package com.azathorpe.utils.struct.game.gameobj;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Transform extends Component {
    public final Vector3f position = new Vector3f();   // 世界坐标，逻辑直接用
    public final Vector3f rotation = new Vector3f();   // 欧拉角/弧度
    public final Vector3f scale    = new Vector3f(1, 1, 1);

    public Matrix4f getModel() {
        return new Matrix4f()
                .translate(position)
                .rotateXYZ(rotation)
                .scale(scale);
    }
}
