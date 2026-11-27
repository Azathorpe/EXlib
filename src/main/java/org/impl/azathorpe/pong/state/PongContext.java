package org.impl.azathorpe.pong.state;

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
    public Window getWindow() {
        return window;
    }

    @Override
    public void transitionTo(GameState next) {
        next.enter(this);
    }

    @Override
    public long getHandle() {
        return window.getHandle();
    }
}
