package org.impl.azathorpe.pong.state;

import com.azathorpe.utils.struct.game.manage.Input;
import com.azathorpe.utils.struct.game.manage.Scene;
import com.azathorpe.utils.struct.game.manage.Window;
import com.azathorpe.utils.struct.game.manage.game.GameContext;
import com.azathorpe.utils.struct.game.manage.game.GameState;

public class MenuContext implements GameContext {
    Scene scene = new Scene();
    Input input = new Input();
    Window window = new Window();

    @Override
    public Scene getScene() {
        return scene;
    }

    @Override
    public Input getInput() {
        return input;
    }

    @Override
    public void transitionTo(GameState next) {

    }

    @Override
    public long getWindow() {
        return window.getWindow();
    }
}
