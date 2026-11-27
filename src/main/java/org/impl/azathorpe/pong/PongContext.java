package org.impl.azathorpe.pong;

import com.azathorpe.utils.struct.game.manage.Input;
import com.azathorpe.utils.struct.game.manage.Scene;
import com.azathorpe.utils.struct.game.manage.Window;
import com.azathorpe.utils.struct.game.manage.game.GameContext;
import com.azathorpe.utils.struct.game.manage.game.GameState;

public class PongContext implements GameContext {
    private Scene scene;
    private Window window;

    public PongContext(Scene scene, Window window, Runnable gs) {
        this.scene = scene;
        this.window = window;
        gs.run();
    }

    @Override
    public Scene getScene() {
        return scene;
    }

    @Override
    public Input getInput() {
        return null;
    }

    @Override
    public void transitionTo(GameState next) {

    }

    @Override
    public long getWindow() {
        return window.getWindow();
    }
}
