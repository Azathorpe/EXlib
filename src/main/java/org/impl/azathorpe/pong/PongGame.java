package org.impl.azathorpe.pong;

import com.azathorpe.utils.struct.game.manage.game.Game;
import org.impl.azathorpe.pong.scene.GameScene;
import org.impl.azathorpe.pong.scene.MenuScene;
import org.lwjgl.glfw.GLFW;

public class PongGame extends Game {
    @Override
    public void init() {
        getInput().register(GLFW.GLFW_KEY_ESCAPE, (game, key, action, mods) -> {
                    if (action == GLFW.GLFW_PRESS)
                        if (game.getCurrentScene().getClass().isAssignableFrom(MenuScene.class))
                            game.getSceneManager().switchScene(new GameScene(this));
                        else
                            game.getSceneManager().switchScene(new MenuScene(this));

                }
        );
        getSceneManager().setScene(new GameScene(this));
    }

    public static void main(String[] args) {
        new PongGame().initGame();
    }

}