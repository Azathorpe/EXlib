package org.impl.azathorpe.pong.scene;

import com.azathorpe.utils.struct.game.manage.Scene;
import com.azathorpe.utils.struct.game.manage.Window;
import org.impl.azathorpe.pong.entities.MenuBar;

public class MenuScene extends Scene {
    public MenuScene(Window window) {
        super(window);
    }

    @Override
    public void putGameObject(Window window) {
        MenuBar newGame = new MenuBar((float) 100 / 255, (float) 100 / 255, (float) 100 / 255);
        addGameObject(newGame);
    }
}
