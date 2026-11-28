package com.azathorpe.utils.struct.game.gameobj.components.base;

import com.azathorpe.utils.struct.game.gameobj.Component;
import com.azathorpe.utils.struct.opengl.shader.Program;

/**
 * 物体的材质
 */
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
