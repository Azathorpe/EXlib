package com.azathorpe.utils.struct.game.manage.game;

import com.azathorpe.utils.struct.game.manage.Input;
import com.azathorpe.utils.struct.game.manage.Scene;
import com.azathorpe.utils.struct.game.manage.Window;

public interface GameContext {
    Scene getScene();

    Input getInput();

    Window getWindow();

    void transitionTo(GameState next);   // 请求切换

    long getHandle();
}