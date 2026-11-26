package com.azathorpe.utils.struct.opengl.shader;

import java.nio.file.Path;

import static org.lwjgl.opengl.GL20C.*;

public class FragmentShader extends Shader {
    public FragmentShader(String shaderCode) {
        super(GL_FRAGMENT_SHADER, shaderCode);
    }

    public FragmentShader(Path shaderCodePath) {
        super(GL_FRAGMENT_SHADER, shaderCodePath);
    }
}
