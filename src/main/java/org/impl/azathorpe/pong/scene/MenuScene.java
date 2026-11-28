package org.impl.azathorpe.pong.scene;

import com.azathorpe.utils.struct.game.gameobj.components.base.Transform;
import com.azathorpe.utils.struct.game.gameobj.components.BoxCollider2D;
import com.azathorpe.utils.struct.game.manage.game.Game;
import com.azathorpe.utils.struct.game.manage.game.Scene;
import org.impl.azathorpe.pong.entities.MenuBar;
import org.lwjgl.glfw.GLFW;

import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;

public class MenuScene extends Scene {
    public MenuScene(Game game) {
        super(game);
    }

    @Override
    public void putGameObject(Game game) {
        MenuBar newGame = new MenuBar((float) 100 / 255, (float) 200 / 255, (float) 100 / 255);
        addGameObject(newGame);
        newGame.getComponent(Transform.class).position.add(0, 0.3F, 0);
        newGame.addComponent(new BoxCollider2D() {
            @Override
            public void onMouseDown(Game game, int button) {
                if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
                    System.out.println("RUNNING");
                }
            }
        });
        newGame.getComponent(BoxCollider2D.class)
                .halfHeight = 0.15F;
        newGame.getComponent(BoxCollider2D.class)
                .halfWidth = 0.3F;

        MenuBar exitGame = new MenuBar((float) 200 / 255, (float) 100 / 255, (float) 100 / 255);
        addGameObject(exitGame);
        exitGame.getComponent(Transform.class).position.add(0, -0.3F, 0);
        exitGame.addComponent(new BoxCollider2D() {
            @Override
            public void onMouseDown(Game game, int button) {
                System.out.println("MOUSE DOWN");
                if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
                    glfwSetWindowShouldClose(game.getWindowHandle(), true);
                    System.out.println("EXIT");
                }
            }
        });
        exitGame.getComponent(BoxCollider2D.class)
                .halfHeight = 0.15F;
        exitGame.getComponent(BoxCollider2D.class)
                .halfWidth = 0.3F;
    }
}
