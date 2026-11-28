package com.azathorpe.utils.struct.game.manage.game;

import com.azathorpe.utils.struct.game.gameobj.GameObject;
import com.azathorpe.utils.struct.game.gameobj.components.BoxCollider2D;
import com.azathorpe.utils.struct.game.manage.MousePicker;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Input
 * 采集鼠标动作, 通过Input注册按键回调
 */
public class Input {
    private final Map<Integer, List<KeyHandler>> keyHandlers = new HashMap<>();
    private Supplier<Game> getGame;
    // 采集鼠标动作
    MousePicker mousePicker = new MousePicker();

    public void init(Window window, Supplier<Game> game) {
        getGame = game;
        glfwSetKeyCallback(window.getHandle(), (win, key, scancode, action, mods) -> {
            List<KeyHandler> handlers = keyHandlers.get(key);
            if (handlers != null)
                for (KeyHandler h : handlers) h.handle(getGame.get(), key, action, mods);
        });

        glfwSetMouseButtonCallback(window.getHandle(), (win, button, action, mods) -> {
            boolean pressed = (action == GLFW_PRESS);
            mousePicker.onMouseButton(game.get(), button, pressed);
        });
    }

    /**
     * 更新input(暂时主要是关于鼠标的, 键盘部分交给GLFW的回调了)
     */
    public void update(Game game) {
        Scene currentScene = game.getCurrentScene();
        Window window = game.getWindow();
        // 1. 收集所有 BoxCollider2D
        List<BoxCollider2D> colliders = new ArrayList<>();
        for (GameObject go : currentScene.getGameObject()) {
            go.getComponentSafely(BoxCollider2D.class).ifPresent(colliders::add);
        }

        // 需要先知道当前鼠标世界坐标（用最新 cursor pos）
        double[] mx = new double[1], my = new double[1];
        glfwGetCursorPos(window.getHandle(), mx, my);
        // 屏幕坐标 → 世界坐标（取决于你的投影）
        float worldX = (float) mx[0];
        float worldY = (float) my[0];
        Vector3f vector3f = currentScene.camera.screenToWorld(worldX, worldY,
                window.WINDOW_WIDTH, window.WINDOW_HEIGHT,
                currentScene.camera.projection, currentScene.camera.view);
        worldX = vector3f.x;
        worldY = vector3f.y;

        // 更新 hover（保证 pressed 时 hovered 是最新的）
        mousePicker.update(game, colliders, worldX, worldY);
    }

    /**
     * 注册某个键的回调（可多个）
     */
    public void register(int key, KeyHandler handler) {
        keyHandlers.computeIfAbsent(key, k -> new ArrayList<>()).add(handler);
    }

    /**
     * 注销某个键的回调
     */
    public void unregister(int key, KeyHandler handler) {
        List<KeyHandler> list = keyHandlers.get(key);
        if (list != null) list.remove(handler);
    }

    /**
     * 清空所有（场景切换时用）
     */
    public void clear() {
        keyHandlers.clear();
    }

    @FunctionalInterface
    public interface KeyHandler {
        void handle(Game game, int key, int action, int mods);
    }
}