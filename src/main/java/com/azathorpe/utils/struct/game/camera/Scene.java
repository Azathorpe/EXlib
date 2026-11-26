package com.azathorpe.utils.struct.game.camera;

import com.azathorpe.utils.struct.game.GameObject;
import com.azathorpe.utils.struct.game.Material;
import com.azathorpe.utils.struct.game.Mesh;
import com.azathorpe.utils.struct.game.Transform;
import com.azathorpe.utils.struct.game.components.InputComponent;
import org.joml.Matrix4f;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL20C.*;
import static org.lwjgl.opengl.GL30C.glBindVertexArray;

public class Scene {
    List<GameObject> gameObjects = new ArrayList<>();
    Camera camera;

    public void render() {
        for (GameObject gameObject : gameObjects) {
            Transform transform = gameObject.getComponent(Transform.class);
            Material material = gameObject.getComponent(Material.class);
            Mesh mesh = gameObject.getComponent(Mesh.class);

            Matrix4f mvp = camera.getMVP(transform.transform);
            int mvpLocation = glGetUniformLocation(gameObject.getComponent(Material.class).getProgram().getProgramId(), "MVP");

            glUseProgram(material.getProgram().getProgramId());

            try (MemoryStack stack = MemoryStack.stackPush()) {
                FloatBuffer buffer = stack.mallocFloat(16);
                // 将 JOML 的 Matrix4f 数据写入缓冲区
                // JOML 默认也是列主序，与 OpenGL 兼容
                mvp.get(buffer);
                // 上传到 GPU
                // 参数: 位置, 是否转置(false), 数据缓冲区
                glUniformMatrix4fv(mvpLocation, false, buffer);
            }

            glBindVertexArray(mesh.getVao().getId());
            // FIXME: FIX HERE
            glDrawArrays(GL_TRIANGLES, 0, mesh.getVertexCount());
            glBindVertexArray(0);
        }
    }

    public void addGameObject(GameObject... gameObject) {
        gameObjects.addAll(List.of(gameObject));
    }

    public void removeGameObject(GameObject... gameObject) {
        for (GameObject go : gameObject)
            gameObjects.remove(go);
    }

    public void input(long window) {
        // 向所有GameObject广播操作
        for (GameObject gameObject : gameObjects)
            gameObject.getComponentSafely(InputComponent.class)
                    .ifPresent(ic -> ic.input(window));
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }
}
