package me.dinozoid.strife.shader.implementations;

import me.dinozoid.strife.shader.Shader;
import me.dinozoid.strife.shader.ShaderProgram;
import org.lwjgl.opengl.GL20;

public class MenuShader extends Shader {

    public MenuShader(int pass) {
        super(new ShaderProgram("fragment/menu.frag"));
        this.pass = pass;
    }

    @Override
    public void setUniforms() {
        GL20.glUniform1f(shaderProgram.getUniform("time"), pass / 100f);
        GL20.glUniform2f(shaderProgram.getUniform("resolution"), mc.displayWidth, mc.displayHeight);
    }

}
