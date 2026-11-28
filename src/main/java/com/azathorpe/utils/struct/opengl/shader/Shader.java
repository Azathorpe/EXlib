package com.azathorpe.utils.struct.opengl.shader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.lwjgl.opengl.GL20C.*;

/**
 * 着色器
 */
public abstract class Shader {
    private final int shaderId;
    private String shaderSource;
    private final int shaderType;


    protected Shader(int shaderType, String shaderCode) {
        this.shaderType = shaderType;
        shaderId = glCreateShader(this.shaderType);
        loadShaderCode(shaderCode);
    }

    protected Shader(int shaderType, Path shaderCodePath) {
        this.shaderType = shaderType;
        shaderId = glCreateShader(this.shaderType);
        loadShaderCode(shaderCodePath);
    }

    /**
     * 加载着色器代码
     *
     * @param content 着色器代码
     */
    protected void loadShaderCode(String content) {
        shaderSource = content;
    }

    /**
     * 加载着色器代码
     *
     * @param path 路径
     */
    protected void loadShaderCode(Path path) {
        try {
            shaderSource = Files.readString(path);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load shader: " + path, e);
        }
    }

    /**
     * 编译着色器
     */
    protected void compileShader() {
        glShaderSource(shaderId, shaderSource);
        glCompileShader(shaderId);

        checkShader();
    }

    /**
     * 检查着色器是否有编译异常
     */
    private void checkShader() {
        if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == GL_FALSE) {
            String log = glGetShaderInfoLog(shaderId);
            glDeleteShader(shaderId);   // 清理失败对象
            throw new RuntimeException("Shader compile failed:\n" + log);
        }
        // 成功时如有警告，可选打印
        String log = glGetShaderInfoLog(shaderId);
        if (!log.isEmpty()) {
            System.out.println("Shader compile log:\n" + log);
        }
    }

    /**
     * 移除着色器
     */
    protected void deleteShader() {
        glDeleteShader(shaderId);
    }

    public int getShaderType() {
        return shaderType;
    }

    public int getShaderId() {
        return shaderId;
    }

    public String getShaderSource() {
        return shaderSource;
    }
}
