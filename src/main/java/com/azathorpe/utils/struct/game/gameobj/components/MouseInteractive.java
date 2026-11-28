package com.azathorpe.utils.struct.game.gameobj.components;


import com.azathorpe.utils.struct.game.manage.game.Game;

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