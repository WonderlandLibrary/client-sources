package net.optifine.shaders.uniform;

public class CustomUniforms {
    private final CustomUniform[] uniforms;

    public CustomUniforms(CustomUniform[] uniforms) {
        this.uniforms = uniforms;
    }

    public void setProgram(int program) {
        for (int i = 0; i < this.uniforms.length; ++i) {
            CustomUniform customuniform = this.uniforms[i];
            customuniform.setProgram(program);
        }
    }

    public void update() {
        for (int i = 0; i < this.uniforms.length; ++i) {
            CustomUniform customuniform = this.uniforms[i];
            customuniform.update();
        }
    }
}
