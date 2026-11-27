package com.azathorpe.utils.struct.game.manage;

import com.azathorpe.utils.struct.game.gameobj.Component;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class Camera extends Component {
    Matrix4f view = new Matrix4f();
    Matrix4f projection = new Matrix4f();

    public Camera() {
    }

    public Camera(Matrix4f view, Matrix4f projection) {
        this.view = view;
        this.projection = projection;
    }

    public Vector3f screenToWorld(double mouseX, double mouseY,
                                  int windowWidth, int windowHeight,
                                  Matrix4f projection, Matrix4f view) {
        // 1. 屏幕 → NDC
        float ndcX = (float) (mouseX / windowWidth) * 2f - 1f;
        float ndcY = 1f - (float) (mouseY / windowHeight) * 2f;   // y 翻转

        // 2. NDC → 世界（2D 用 z=0，w=1；正交投影 w 恒为 1）
        Vector4f ndc = new Vector4f(ndcX, ndcY, 0f, 1f);
        Matrix4f invPV = new Matrix4f(projection).mul(view).invert();
        Vector4f world = invPV.transform(ndc);
        world.div(world.w);   // 透视除法（正交时 w=1，无害）

        return new Vector3f(world.x, world.y, world.z);
    }

    public Matrix4f getMVP() {
        return getMVP(new Matrix4f());
    }

    public Matrix4f getMVP(Matrix4f model) {
        return new Matrix4f(projection).mul(view).mul(model);
    }

    public Matrix4f getView() {
        return view;
    }

    public void setView(Matrix4f view) {
        this.view = view;
    }

    public Matrix4f getProjection() {
        return projection;
    }

    public void setProjection(Matrix4f projection) {
        this.projection = projection;
    }
}
