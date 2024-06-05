package client.metaware.api.shader.implementations;

import client.metaware.api.shader.Shader;
import client.metaware.api.shader.ShaderProgram;
import org.lwjgl.opengl.GL20;

public class AccountManagerShader extends Shader {

    public AccountManagerShader(int pass) {
        super(new ShaderProgram("fragment/altmanager.frag"));
        this.pass = pass;
    }

    @Override
    public void setUniforms() {
        GL20.glUniform1f(shaderProgram.getUniform("time"), pass / 100f);
        GL20.glUniform2f(shaderProgram.getUniform("resolution"), mc.displayWidth, mc.displayHeight);
    }

}
