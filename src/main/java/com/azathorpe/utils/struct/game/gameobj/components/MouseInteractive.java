package com.azathorpe.utils.struct.game.gameobj.components;

import com.azathorpe.utils.struct.game.manage.game.GameContext;

public interface MouseInteractive {
    default void onMouseEnter(GameContext context) {
    }

    default void onMouseExit(GameContext context) {
    }

    default void onMouseDown(GameContext context, int button) {
    }

    default void onMouseUp(GameContext context, int button) {
    }

    default void onClick(GameContext context, int button) {
    }
}