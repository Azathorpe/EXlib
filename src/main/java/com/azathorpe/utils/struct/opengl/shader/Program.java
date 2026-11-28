package com.azathorpe.utils.struct.opengl.shader;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL20C.*;

/**
 * Program
 */
public class Program {
    private ArrayList<Shader> shaderRecord = new ArrayList<>();
    private final int programId;

    public Program() {
        this.programId = glCreateProgram();
    }

    public Program(Shader... shaders) {
        this.programId = glCreateProgram();

        try {
            for (Shader shader : shaders) {
                // 编译着色器
                shader.compileShader();
                // 添加着色器
                attachShader(shader);
                // 添加着色器到记录表
                this.shaderRecord.add(shader);
            }

            // 连接所有着色器
            linkProgram();
        } finally {
            // 移除所有着色器
            detachShader(shaders);

            // 删除所有着色器
            for (Shader shader : shaderRecord)
                shader.deleteShader();
        }
    }

    /**
     * 添加一个着色器
     *
     * @param shader 着色器
     */
    private void attachShader(Shader shader) {
        glAttachShader(programId, shader.getShaderId());
    }

    /**
     * 添加多个着色器
     *
     * @param shaders 着色器s
     */
    private void attachShader(Shader... shaders) {
        for (Shader shader : shaders) {
            attachShader(shader);
        }
    }

    /**
     * 连接着色器
     */
    private void linkProgram() {
        glLinkProgram(programId);

        checkProgram();
    }

    /**
     * 检查Program是否出错
     */
    private void checkProgram() {
        if (glGetProgrami(programId, GL_LINK_STATUS) == GL_FALSE) {
            String log = glGetProgramInfoLog(programId);
            throw new RuntimeException("Program link failed:\n" + log);
        }
        // 可选：即使成功也打印警告信息
        String log = glGetProgramInfoLog(programId);
        if (!log.isEmpty()) {
            System.out.println("Program link log:\n" + log);
        }
    }


    /**
     * 移除着色器
     *
     * @param shader 着色器
     */
    private void detachShader(Shader shader) {
        glDetachShader(programId, shader.getShaderId());
    }

    /**
     * 移除着色器s
     *
     * @param shaders 着色器s
     */
    private void detachShader(Shader... shaders) {
        for (Shader shader : shaders) {
            detachShader(shader);
        }
    }

    public void deleteProgram() {
        glDeleteProgram(programId);
    }

    public int getProgramId() {
        return programId;
    }
}
