package com.azathorpe.utils.struct.game;

import com.azathorpe.utils.struct.opengl.shader.Program;

public class Material extends Component {
    Program program;

    public Material() {
    }

    public Material(Program program) {
        this.program = program;
    }

    public Program getProgram() {
        return program;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

}
