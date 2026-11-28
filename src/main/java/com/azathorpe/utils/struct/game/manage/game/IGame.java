package com.azathorpe.utils.struct.game.manage.game;

import com.azathorpe.utils.struct.game.manage.Input;
import com.azathorpe.utils.struct.game.manage.Scene;
import com.azathorpe.utils.struct.game.manage.SceneManager;
import com.azathorpe.utils.struct.game.manage.Window;

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
