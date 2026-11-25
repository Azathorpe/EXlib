package com.azathorpe.utils.struct.opengl.shader;

import static org.lwjgl.opengl.GL20C.*;

public class Program {
    private final int programId;

    public Program() {
        this.programId = glCreateProgram();
    }

    /**
     * 添加一个着色器
     *
     * @param shader 着色器
     */
    public void attachShader(Shader shader) {
        glAttachShader(programId, shader.getShaderId());
    }

    /**
     * 添加多个着色器
     *
     * @param shaders 着色器s
     */
    public void attachShader(Shader... shaders) {
        for (Shader shader : shaders) {
            attachShader(shader);
        }
    }

    /**
     * 连接着色器
     */
    public void linkShader() {
        glLinkProgram(programId);

        checkProgram();
    }

    /**
     * 检查Program是否出错
     */
    public void checkProgram() {
        int[] result = {0}, infoLogLength = {0};
        glGetShaderiv(programId, GL_COMPILE_STATUS, result);
        glGetShaderiv(programId, GL_INFO_LOG_LENGTH, infoLogLength);
        if (infoLogLength[0] > 0) {
            String info = glGetShaderInfoLog(programId, infoLogLength[0]);
            //TODO: 更改为log.info
            System.out.println(info);
        }
    }


    /**
     * 移除着色器
     * @param shader 着色器
     */
    public void detachShader(Shader shader) {
        glDetachShader(programId, shader.getShaderId());
    }

    /**
     * 移除着色器s
     * @param shaders 着色器s
     */
    public void detachShader(Shader... shaders) {
        for (Shader shader : shaders) {
            detachShader(shader);
        }
    }

    public int getProgramId() {
        return programId;
    }
}
