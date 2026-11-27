package org.impl.azathorpe.pong.state;

import com.azathorpe.utils.struct.game.manage.Input;
import com.azathorpe.utils.struct.game.manage.Scene;
import com.azathorpe.utils.struct.game.manage.Window;
import com.azathorpe.utils.struct.game.manage.game.DefaultGameContext;
import com.azathorpe.utils.struct.game.manage.game.GameContext;
import com.azathorpe.utils.struct.game.manage.game.GameState;

import java.util.function.Consumer;

public class PongContext extends DefaultGameContext {
    public PongContext(Scene scene, Input input, Window window, Consumer<GameState> transitionRequest) {
        super(scene, input, window, transitionRequest);
    }
}
