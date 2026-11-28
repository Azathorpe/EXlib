package com.azathorpe.utils.struct.game.manage.game;

public interface IGame {
    void initGame();

    void loop();

    void quit();

    SceneManager getSceneManager();

    Scene getCurrentScene();

    Input getInput();

    Window getWindow();

    long getWindowHandle();
}
