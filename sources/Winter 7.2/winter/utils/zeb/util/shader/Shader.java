/*
 * Decompiled with CFR 0_122.
 */
package winter.utils.zeb.util.shader;

import net.minecraft.client.renderer.OpenGlHelper;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import winter.utils.zeb.Asset;
import winter.utils.zeb.ShaderHelper;
import winter.utils.zeb.util.shader.ShaderType;

public class Shader {
    private int combined;
    private int vertex;
    private int fragment;

    public Shader(Asset vertex, Asset fragment) {
        this.vertex = ShaderHelper.INSTANCE.create(vertex, ShaderType.VERTEX);
        this.fragment = ShaderHelper.INSTANCE.create(fragment, ShaderType.FRAGMENT);
        if (this.vertex == 0 || this.fragment == 0) {
            throw new RuntimeException(String.format("Invalid shader compile. Vertex: %s, Fragment: %s, error: (%s)\n (%s).", vertex, fragment, Shader.getLogInfo(this.vertex), Shader.getLogInfo(this.fragment)));
        }
        this.combined = ARBShaderObjects.glCreateProgramObjectARB();
        ARBShaderObjects.glAttachObjectARB(this.combined, this.vertex);
        ARBShaderObjects.glAttachObjectARB(this.combined, this.fragment);
        ARBShaderObjects.glLinkProgramARB(this.combined);
        ARBShaderObjects.glValidateProgramARB(this.combined);
        this.unbind();
    }

    public static String getLogInfo(int obj) {
        return ARBShaderObjects.glGetInfoLogARB(obj, ARBShaderObjects.glGetObjectParameteriARB(obj, 35716));
    }

    public void bind() {
        ARBShaderObjects.glUseProgramObjectARB(this.combined);
    }

    public void unbind() {
        ARBShaderObjects.glUseProgramObjectARB(0);
    }

    public int getUniform(String label) {
        return OpenGlHelper.glGetUniformLocation(this.combined, label);
    }

    public void setVector(String label, Vector2f value) {
        GL20.glUniform2f(this.getUniform(label), value.x, value.y);
    }

    public void setVector(String label, Vector3f value) {
        GL20.glUniform3f(this.getUniform(label), value.x, value.y, value.z);
    }

    public void setVector(String label, Vector4f value) {
        GL20.glUniform4f(this.getUniform(label), value.x, value.y, value.z, value.w);
    }

    public void setSampler2d(String label, int value) {
        int loc = this.getUniform(label);
        GL20.glUniform1i(loc, 0);
        GL13.glActiveTexture(33984);
        GL11.glEnable(3553);
        GL11.glBindTexture(3553, value);
    }

    public void setSampler2d(String label, int value, int activeTexture) {
        int loc = this.getUniform(label);
        GL20.glUniform1i(loc, 0);
        GL13.glActiveTexture(33984 + activeTexture);
        GL11.glEnable(3553);
        GL11.glBindTexture(3553, value);
    }

    public void setInteger(String label, int value) {
        GL20.glUniform1i(this.getUniform(label), value);
    }

    public void setFloat(String label, float value) {
        GL20.glUniform1f(this.getUniform(label), value);
    }

    public void setBoolean(String label, boolean value) {
        this.setInteger(label, value ? 1 : 0);
    }
}

