package com.azathorpe.utils.struct.game.manage.game;

import java.util.function.Supplier;

/**
 * 场景管理器
 */
public final class SceneManager {
    // 当前场景
    private Scene currentScene;

    // 是否切换
    boolean pendingSwitch = false;
    // 下一个场景
    Scene nxtScene = null;

    public SceneManager(Scene currentScene) {
        this.currentScene = currentScene;
    }

    /**
     * 确保一帧结束之后, 才进行场景的切换
     */
    public void nxtFrame(){
        if(pendingSwitch) {
            pendingSwitch = false;
            currentScene = nxtScene;
            nxtScene = null;
        }
    }

    public void switchScene(Scene scene) {
        nxtScene = scene;
        pendingSwitch = true;
    }

    public void setScene(Scene scene) {
        currentScene = scene;
    }

    public Scene getCurrentScene() {
        return currentScene;
    }
}
