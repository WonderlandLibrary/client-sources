/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders.uniform;

import net.optifine.shaders.uniform.ShaderUniformBase;
import org.lwjgl.opengl.ARBShaderObjects;

public class ShaderUniform1f
extends ShaderUniformBase {
    private float[] programValues;
    private static final float VALUE_UNKNOWN = -3.4028235E38f;

    public ShaderUniform1f(String string) {
        super(string);
        this.resetValue();
    }

    public void setValue(float f) {
        int n = this.getProgram();
        float f2 = this.programValues[n];
        if (f != f2) {
            this.programValues[n] = f;
            int n2 = this.getLocation();
            if (n2 >= 0) {
                ShaderUniform1f.flushRenderBuffers();
                ARBShaderObjects.glUniform1fARB(n2, f);
                this.checkGLError();
            }
        }
    }

    public float getValue() {
        int n = this.getProgram();
        return this.programValues[n];
    }

    @Override
    protected void onProgramSet(int n) {
        if (n >= this.programValues.length) {
            float[] fArray = this.programValues;
            float[] fArray2 = new float[n + 10];
            System.arraycopy(fArray, 0, fArray2, 0, fArray.length);
            for (int i = fArray.length; i < fArray2.length; ++i) {
                fArray2[i] = -3.4028235E38f;
            }
            this.programValues = fArray2;
        }
    }

    @Override
    protected void resetValue() {
        this.programValues = new float[]{-3.4028235E38f};
    }
}

