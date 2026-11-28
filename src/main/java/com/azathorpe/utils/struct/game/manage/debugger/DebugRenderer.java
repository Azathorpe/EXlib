package com.azathorpe.utils.struct.game.manage.debugger;

import com.azathorpe.utils.struct.opengl.shader.FragmentShader;
import com.azathorpe.utils.struct.opengl.shader.Program;
import com.azathorpe.utils.struct.opengl.shader.VertexShader;
import org.joml.Matrix4f;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;
import java.nio.file.Path;

import static org.lwjgl.opengl.GL15C.*;
import static org.lwjgl.opengl.GL20C.*;
import static org.lwjgl.opengl.GL30C.*;

/**
 * Debug 渲染 现在主要是渲染碰撞箱
 * TODO: 把这里改成用VAO/VBO的形式
 */
public class DebugRenderer {
    private final Program program;
    private final int vao;
    private final int vbo;
    private final int mvpLoc;
    private final int colorLoc;
    private static final int MAX_VERTICES = 4;   // 一个矩形 4 个顶点

    public DebugRenderer() {
        program = new Program(
                new VertexShader(Path.of("/home/azathorpe/Projects/LWJGL/LWJGL_First/src/main/resources/VertexShader/debug.vert")),
                new FragmentShader(Path.of("/home/azathorpe/Projects/LWJGL/LWJGL_First/src/main/resources/FragmentShader/debug.frag"))
        );
        mvpLoc = glGetUniformLocation(program.getProgramId(), "MVP");
        colorLoc = glGetUniformLocation(program.getProgramId(), "uColor");

        vao = glGenVertexArrays();
        vbo = glGenBuffers();
        glBindVertexArray(vao);
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        // 预分配 4 顶点 * 3 float 的空间
        glBufferData(GL_ARRAY_BUFFER, (long) MAX_VERTICES * 3 * Float.BYTES,
                GL_DYNAMIC_DRAW);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
        glEnableVertexAttribArray(0);
        glBindVertexArray(0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    /** 画一个 AABB 线框 */
    public void drawBox(float cx, float cy, float hw, float hh,
                        Matrix4f mvp, float r, float g, float b, float a) {
        float[] verts = {
                cx - hw, cy - hh, 0,
                cx + hw, cy - hh, 0,
                cx + hw, cy + hh, 0,
                cx - hw, cy + hh, 0
        };
        glUseProgram(program.getProgramId());
        // 上传 MVP
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer buf = stack.mallocFloat(16);
            mvp.get(buf);
            glUniformMatrix4fv(mvpLoc, false, buf);
        }
        glUniform4f(colorLoc, r, g, b, a);

        glBindVertexArray(vao);
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferSubData(GL_ARRAY_BUFFER, 0, verts);   // 更新 4 个顶点
        glDrawArrays(GL_LINE_LOOP, 0, 4);
        glBindVertexArray(0);
    }

    public void close() {
        glDeleteBuffers(vbo);
        glDeleteVertexArrays(vao);
        glDeleteProgram(program.getProgramId());
    }
}