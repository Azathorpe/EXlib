package org.impl.azathorpe.pong;

import com.azathorpe.utils.struct.game.manage.Scene;
import com.azathorpe.utils.struct.game.manage.game.Game;
import org.impl.azathorpe.pong.scene.GameScene;
import org.impl.azathorpe.pong.scene.MenuScene;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;


import java.nio.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15C.glBufferData;
import static org.lwjgl.opengl.GL15C.glGenBuffers;
import static org.lwjgl.opengl.GL20C.glGetUniformLocation;
import static org.lwjgl.opengl.GL20C.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20C.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30C.glGenVertexArrays;
import static org.lwjgl.system.MemoryStack.*;

public class PongIGame extends Game {
    Scene gameScene;
    Scene menuScene;

    @Override
    public void init() {
        gameScene = new GameScene(getWindow());
        menuScene = new MenuScene(getWindow());

        getSceneManager().setScene(gameScene);
    }

    public static void main(String[] args) {
        new PongIGame().initGame();
    }

}