/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL20
 */
package cc.diablo.render.implementations;

import cc.diablo.render.Shader;
import cc.diablo.render.ShaderProgram;
import org.lwjgl.opengl.GL20;

public class NewMenuShader
extends Shader {
    public NewMenuShader(int pass) {
        super(new ShaderProgram("fragment/blue.frag"));
        this.pass = pass;
    }

    @Override
    public void setUniforms() {
        GL20.glUniform1f((int)this.shaderProgram.getUniform("time"), (float)((float)this.pass / 100.0f));
        GL20.glUniform2f((int)this.shaderProgram.getUniform("resolution"), (float)this.mc.displayWidth, (float)this.mc.displayHeight);
    }
}

