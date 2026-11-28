package com.azathorpe.utils.struct.game.system;

import com.azathorpe.utils.struct.game.manage.game.Game;

/**
 * 用于获取Game实例
 * (主要是还没想好Game实例用什么传)
 */
public final class GameInstance {
    private static Game instance;

    public static void setInstance(Game instance) {
        GameInstance.instance = instance;
    }

    public static Game getInstance() {
        return instance;
    }
}
