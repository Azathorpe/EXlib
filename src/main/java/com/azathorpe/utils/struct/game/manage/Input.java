package com.azathorpe.utils.struct.game.manage;

import com.azathorpe.utils.struct.game.manage.game.Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static org.lwjgl.glfw.GLFW.*;

public class Input {
    private final Map<Integer, List<KeyHandler>> keyHandlers = new HashMap<>();
    private Supplier<Game> getGame;

    public void init(Window window, Supplier<Game> game) {
        getGame = game;
        glfwSetKeyCallback(window.getHandle(), (win, key, scancode, action, mods) -> {
            List<KeyHandler> handlers = keyHandlers.get(key);
            if (handlers != null)
                for (KeyHandler h : handlers) h.handle(getGame.get(), key, action, mods);
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
        void handle(Game game, int key, int action, int mods);
    }
}