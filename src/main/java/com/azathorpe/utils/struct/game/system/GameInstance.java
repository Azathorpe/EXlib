package com.azathorpe.utils.struct.game.system;

import com.azathorpe.utils.struct.game.manage.game.Game;

public final class GameInstance {
    private static Game instance;

    public static void setInstance(Game instance) {
        GameInstance.instance = instance;
    }

    public static Game getInstance() {
        return instance;
    }
}
