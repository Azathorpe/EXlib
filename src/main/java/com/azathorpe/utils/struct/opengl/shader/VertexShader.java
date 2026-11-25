package com.azathorpe.utils.struct.opengl.shader;

import java.nio.file.Path;

import static org.lwjgl.opengl.GL20C.GL_VERTEX_SHADER;

/**
 * 顶点着色器
 */
public class VertexShader extends Shader {

    public VertexShader() {
        super(GL_VERTEX_SHADER);
    }

    public VertexShader(String shaderCode) {
        super(GL_VERTEX_SHADER, shaderCode);
    }

    public VertexShader(Path shaderCodePath) {
        super(GL_VERTEX_SHADER, shaderCodePath);
    }
}
