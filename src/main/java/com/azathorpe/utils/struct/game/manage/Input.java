package com.azathorpe.utils.struct.game.manage;

import com.azathorpe.utils.struct.game.manage.game.GameContext;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallbackI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static org.lwjgl.glfw.GLFW.*;

public class Input {
    private final Map<Integer, List<KeyHandler>> keyHandlers = new HashMap<>();
    private Supplier<GameContext> contextSupplier;   // 动态获取当前 context

    public void init(Window window, Supplier<GameContext> contextSupplier) {
        this.contextSupplier = contextSupplier;
        glfwSetKeyCallback(window.getHandle(), (win, key, scancode, action, mods) -> {
            GameContext ctx = contextSupplier.get();   // 每次拿最新 context
            List<KeyHandler> handlers = keyHandlers.get(key);
            if (handlers != null)
                for (KeyHandler h : handlers) h.handle(ctx, key, action, mods);
        });
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
        void handle(GameContext ctx, int key, int action, int mods);
    }
}