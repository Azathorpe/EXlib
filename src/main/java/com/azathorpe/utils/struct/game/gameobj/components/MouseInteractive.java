package com.azathorpe.utils.struct.game.gameobj.components;


public interface MouseInteractive {
    default void onMouseEnter() {
    }

    default void onMouseExit() {
    }

    default void onMouseDown(int button) {
    }

    default void onMouseUp(int button) {
    }

    default void onClick(int button) {
    }
}