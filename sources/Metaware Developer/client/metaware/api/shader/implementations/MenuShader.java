package client.metaware.api.shader.implementations;

import client.metaware.api.shader.Shader;
import client.metaware.api.shader.ShaderProgram;
import static org.lwjgl.opengl.GL20.*;

public class MenuShader extends Shader {

    public MenuShader(int pass) {
        super(new ShaderProgram("fragment/menu.frag"));
        this.pass = pass;
    }

    @Override
    public void render(float x, float y, float width, float height) {
        super.render(x, y, width, height);
    }

    @Override
    public void setUniforms() {
        glUniform1f(shaderProgram.getUniform("time"), pass / 200f);
        glUniform2f(shaderProgram.getUniform("resolution"), mc.displayWidth, mc.displayHeight);
    }

}
