package com.azathorpe.utils.struct.game;

import com.azathorpe.utils.struct.opengl.shader.Program;

public class Material implements Component {
    Program program;

    public Material() {
    }

    public Material(Program program) {
        this.program = program;
    }

    @Override
    public void load() {

    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

}
