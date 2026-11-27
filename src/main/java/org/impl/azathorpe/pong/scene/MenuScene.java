package org.impl.azathorpe.pong.scene;

import com.azathorpe.utils.struct.game.gameobj.Transform;
import com.azathorpe.utils.struct.game.gameobj.components.BoxCollider2D;
import com.azathorpe.utils.struct.game.manage.Scene;
import com.azathorpe.utils.struct.game.manage.Window;
import com.azathorpe.utils.struct.game.manage.game.GameContext;
import org.impl.azathorpe.pong.entities.MenuBar;
import org.impl.azathorpe.pong.state.RunState;
import org.lwjgl.glfw.GLFW;

import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;

public class MenuScene extends Scene {
    public MenuScene(Window window) {
        super(window);
    }

    @Override
    public void putGameObject(Window window) {
        MenuBar newGame = new MenuBar((float) 100 / 255, (float) 200 / 255, (float) 100 / 255);
        addGameObject(newGame);
        newGame.getComponent(Transform.class).position.add(0, 0.3F, 0);
        newGame.addComponent(new BoxCollider2D() {
            @Override
            public void onMouseDown(GameContext context, int button) {
                if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
                    context.transitionTo(new RunState());
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
            public void onMouseDown(GameContext context, int button) {
                if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
                    glfwSetWindowShouldClose(window.getHandle(), true);
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
