package com.azathorpe.utils.struct.opengl.shader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.lwjgl.opengl.GL20C.*;

public abstract class Shader {
    private final int shaderId;
    private String shaderSource;
    private final int shaderType;

    protected Shader(int shaderType) {
        this.shaderType = shaderType;
        shaderId = glCreateShader(this.shaderType);
    }

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
    public void loadShaderCode(String content) {
        shaderSource = content;
    }

    /**
     * 加载着色器代码
     *
     * @param path 路径
     */
    public void loadShaderCode(Path path) {
        try {
            shaderSource = Files.readString(path);
        } catch (IOException e) {
            //TODO: log info
            throw new RuntimeException(e);
        }
    }

    /**
     * 编译着色器
     */
    public void compileShader() {
        glShaderSource(shaderId, shaderSource);
        glCompileShader(shaderId);

        checkShader();
    }

    /**
     * 检查着色器是否有编译异常
     */
    public void checkShader() {
        int[] result = {0}, infoLogLength = {0};
        glGetShaderiv(shaderId, GL_COMPILE_STATUS, result);
        glGetShaderiv(shaderId, GL_INFO_LOG_LENGTH, infoLogLength);
        if (infoLogLength[0] > 0) {
            String info = glGetShaderInfoLog(shaderId, infoLogLength[0]);
            //TODO: 更改为log.info
            System.out.println(info);
        }
    }

    /**
     * 移除着色器
     */
    public void deleteShader() {
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
