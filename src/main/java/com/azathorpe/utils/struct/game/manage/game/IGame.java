package com.azathorpe.utils.struct.game.manage.game;

/**
 * 定义Game
 * 你可以自己实现IGame
 */
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
