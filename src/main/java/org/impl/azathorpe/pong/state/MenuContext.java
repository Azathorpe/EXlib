package org.impl.azathorpe.pong.state;

import com.azathorpe.utils.struct.game.manage.Input;
import com.azathorpe.utils.struct.game.manage.Scene;
import com.azathorpe.utils.struct.game.manage.Window;
import com.azathorpe.utils.struct.game.manage.game.GameContext;
import com.azathorpe.utils.struct.game.manage.game.GameState;

public class MenuContext implements GameContext {
    Scene scene;
    Input input;
    Window window;

    public MenuContext(Scene scene, Input input, Window window) {
        this.scene = scene;
        this.input = input;
        this.window = window;
    }

    @Override
    public Scene getScene() {
        return scene;
    }

    @Override
    public Input getInput() {
        return input;
    }

    @Override
    public Window getWindow() {
        return window;
    }

    @Override
    public void transitionTo(GameState next) {

    }

    @Override
    public long getHandle() {
        return window.getHandle();
    }
}
