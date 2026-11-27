package com.azathorpe.utils.struct.game.manage;

import com.azathorpe.utils.struct.game.gameobj.Component;
import org.joml.Matrix4f;

public class Camera extends Component {
    Matrix4f view = new Matrix4f();
    Matrix4f projection = new Matrix4f();

    public Camera() {
    }

    public Camera(Matrix4f view, Matrix4f projection) {
        this.view = view;
        this.projection = projection;
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
