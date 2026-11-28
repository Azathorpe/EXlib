package com.azathorpe.utils.struct.game.manage;

public class SceneManager {
    private Scene currentScene;

    public SceneManager(Scene currentScene) {
        this.currentScene = currentScene;
    }

    public void switchScene(Scene scene) {
        currentScene.exit();
        currentScene = scene;
    }

    public void setScene(Scene scene) {
        currentScene = scene;
    }

    public Scene getCurrentScene() {
        return currentScene;
    }
}
