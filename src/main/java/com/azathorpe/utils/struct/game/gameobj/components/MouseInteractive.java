package com.azathorpe.utils.struct.game.gameobj.components;


import com.azathorpe.utils.struct.game.manage.game.Game;

/**
 * FIXME: 实际上这个接口不该出现在这个包下面
 * 鼠标交互的接口
 */
public interface MouseInteractive {
    default void onMouseEnter(Game game) {
    }

    default void onMouseExit(Game game) {
    }

    default void onMouseDown(Game game, int button) {
    }

    default void onMouseUp(Game game, int button) {
    }

    default void onClick(Game game, int button) {
    }
}