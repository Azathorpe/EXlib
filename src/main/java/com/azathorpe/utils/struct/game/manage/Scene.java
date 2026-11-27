package com.azathorpe.utils.struct.game.manage;

import com.azathorpe.utils.struct.game.gameobj.components.BoxCollider2D;
import com.azathorpe.utils.struct.game.gameobj.components.UpdateComponent;
import com.azathorpe.utils.struct.game.gameobj.GameObject;
import com.azathorpe.utils.struct.game.gameobj.Material;
import com.azathorpe.utils.struct.game.gameobj.Mesh;
import com.azathorpe.utils.struct.game.gameobj.Transform;
import com.azathorpe.utils.struct.game.manage.debugger.DebugRenderer;
import com.azathorpe.utils.struct.game.manage.game.GameContext;
import com.azathorpe.utils.struct.game.system.PhysicsSystem;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL20C.*;
import static org.lwjgl.opengl.GL30C.glBindVertexArray;

public class Scene {
    List<GameObject> gameObjects = new ArrayList<>();
    private final PhysicsSystem physics = new PhysicsSystem();
    Camera camera = new Camera();
    MousePicker mousePicker = new MousePicker();

    // Debug 模式
    boolean debugEnabled = true;
    DebugRenderer debugRenderer = new DebugRenderer();

    public Scene() {
        putGameObject();
    }

    public Scene(Window window) {
        putGameObject(window);
    }

    public void render() {
        for (GameObject gameObject : gameObjects) {
            Transform transform = gameObject.getComponent(Transform.class);
            Material material = gameObject.getComponent(Material.class);
            Mesh mesh = gameObject.getComponent(Mesh.class);

            Matrix4f mvp = camera.getMVP(transform.getModel());
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
            glDrawArrays(GL_TRIANGLES, 0, mesh.getVertexCount());
            glBindVertexArray(0);
        }


        // 2. 调试层：画所有 collider
        if (debugEnabled) {
            Matrix4f mvp = camera.getMVP(new Matrix4f());   // model = I
            for (GameObject go : gameObjects) {
                go.getComponentSafely(BoxCollider2D.class).ifPresent(c -> {
                    float r = c.isTrigger ? 0 : 1;   // 触发器绿色，实体红色
                    float g = c.isTrigger ? 1 : 0;
                    debugRenderer.drawBox(c.getCenterX(), c.getCenterY(),
                            c.halfWidth, c.halfHeight,
                            mvp, r, g, 0, 1);
                });
            }
        }
    }

    public void addGameObject(GameObject... gameObject) {
        gameObjects.addAll(List.of(gameObject));
    }

    public void removeGameObject(List<GameObject> gameObject) {
        for (GameObject go : gameObject)
            gameObjects.remove(go);
    }

    public void removeGameObject(GameObject... gameObject) {
        for (GameObject go : gameObject)
            gameObjects.remove(go);
    }


    public void init(GameContext context,Window window) {
        // 注册鼠标按键回调
        // TODO: 移动到Input里面去
        glfwSetMouseButtonCallback(window.getHandle(), (win, button, action, mods) -> {
            boolean pressed = (action == GLFW_PRESS);
            mousePicker.onMouseButton(context, button, pressed);
        });
    }

    public void update(GameContext context,Window window) {
        // 向所有GameObject广播操作
        for (GameObject gameObject : gameObjects)
            gameObject.getComponentSafely(UpdateComponent.class)
                    .ifPresent(ic -> ic.input(window.getHandle()));

        // 1. 收集所有 BoxCollider2D
        List<BoxCollider2D> colliders = new ArrayList<>();
        for (GameObject go : gameObjects) {
            go.getComponentSafely(BoxCollider2D.class).ifPresent(colliders::add);
        }
        // 2. 物理检测
        physics.update(colliders);

        // 需要先知道当前鼠标世界坐标（用最新 cursor pos）
        double[] mx = new double[1], my = new double[1];
        glfwGetCursorPos(window.getHandle(), mx, my);
        // 屏幕坐标 → 世界坐标（取决于你的投影）
        float worldX = (float) mx[0];
        float worldY = (float) my[0];
        Vector3f vector3f = camera.screenToWorld(worldX, worldY,
                window.WINDOW_WIDTH, window.WINDOW_HEIGHT,
                camera.projection, camera.view);
        worldX = vector3f.x;
        worldY = vector3f.y;

        // 更新 hover（保证 pressed 时 hovered 是最新的）
        mousePicker.update(context, colliders, worldX, worldY);
    }

    public void exit(Window window) {
        // 退出Scene
        removeGameObject(gameObjects);
    }

    /**
     * 放置物体调用这个方法
     */
    public void putGameObject(Window window) {

    }

    public void putGameObject() {

    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }
}
