package com.azathorpe.utils.struct.game.manage.game;

import com.azathorpe.utils.struct.game.manage.Scene;

public interface GameContext {
    Scene getScene();
    Input getInput();
    void transitionTo(GameState next);   // 请求切换
    long getWindow();
}