package net.optifine.shaders.uniform;

import net.optifine.shaders.client.Shaders;
import org.lwjgl.opengl.ARBShaderObjects;

public class ShaderUniformFloat extends ShaderUniformBase {
    private float value = -1.0F;

    public ShaderUniformFloat(String name) {
        super(name);
    }

    protected void onProgramChanged() {
        this.value = -1.0F;
    }

    public void setValue(float value) {
        if (this.getLocation() >= 0) {
            if (this.value != value) {
                ARBShaderObjects.glUniform1fARB(this.getLocation(), value);
                Shaders.checkGLError(this.getName());
                this.value = value;
            }
        }
    }

    public float getValue() {
        return this.value;
    }
}
