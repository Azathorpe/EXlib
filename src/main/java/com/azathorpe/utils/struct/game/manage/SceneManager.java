package com.azathorpe.utils.struct.game.manage;

public class SceneManager {
    private final Scene currentScene;
    private int sceneTag = -1;

    public SceneManager(Scene currentScene) {
        this.currentScene = currentScene;
    }

    public void switchScene(Scene scene) {
        if (sceneTag == scene.getSceneTag())
            return;
        currentScene.exit();
        currentScene.addGameObject(scene.getGameObject());
    }

    public Scene getCurrentScene() {
        return currentScene;
    }
}
