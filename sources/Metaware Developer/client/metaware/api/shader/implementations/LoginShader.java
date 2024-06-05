package client.metaware.api.shader.implementations;

import client.metaware.api.shader.Shader;
import client.metaware.api.shader.ShaderProgram;
import org.lwjgl.opengl.GL20;

public class LoginShader extends Shader {

    public LoginShader(int pass){
        super(new ShaderProgram("fragment/login.frag"));
        this.pass = pass;
    }

    @Override
    public void render(float x, float y, float width, float height) {
        super.render(x, y, width, height);
    }

    @Override
    public void setUniforms() {
        GL20.glUniform1f(shaderProgram.getUniform("time"), pass / 200f);
        GL20.glUniform2f(shaderProgram.getUniform("resolution"), mc.displayWidth, mc.displayHeight);
    }


}
