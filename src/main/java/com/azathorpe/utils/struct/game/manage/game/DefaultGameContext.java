package com.azathorpe.utils.struct.game.manage.game;

import com.azathorpe.utils.struct.game.manage.Input;
import com.azathorpe.utils.struct.game.manage.Scene;
import com.azathorpe.utils.struct.game.manage.SceneManager;
import com.azathorpe.utils.struct.game.manage.Window;

import java.util.function.Consumer;

public class DefaultGameContext implements GameContext {
    private final SceneManager sceneManager;
    private final Input input;
    private final Window window;
    private final Consumer<GameState> transitionRequest;   // 注入的切换函数

    public DefaultGameContext(Scene scene, Input input, Window window,
                              Consumer<GameState> transitionRequest) {
        sceneManager = new SceneManager(scene);
        this.input = input;
        this.window = window;
        this.transitionRequest = transitionRequest;
    }

    @Override public SceneManager getSceneManager() { return sceneManager; }
    @Override public Scene getCurrentScene() { return sceneManager.getCurrentScene(); }
    @Override public Input getInput() { return input; }
    @Override public Window getWindow() { return window; }
    @Override public long getHandle() { return window.getHandle(); }

    @Override
    public void transitionTo(GameState next) {
        transitionRequest.accept(next);   // ← 转发给状态机
    }
}
