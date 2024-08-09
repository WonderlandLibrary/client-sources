/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders.uniform;

import net.optifine.shaders.uniform.ShaderUniformBase;
import org.lwjgl.opengl.ARBShaderObjects;

public class ShaderUniform2f
extends ShaderUniformBase {
    private float[][] programValues;
    private static final float VALUE_UNKNOWN = -3.4028235E38f;

    public ShaderUniform2f(String string) {
        super(string);
        this.resetValue();
    }

    public void setValue(float f, float f2) {
        int n = this.getProgram();
        float[] fArray = this.programValues[n];
        if (fArray[0] != f || fArray[1] != f2) {
            fArray[0] = f;
            fArray[1] = f2;
            int n2 = this.getLocation();
            if (n2 >= 0) {
                ShaderUniform2f.flushRenderBuffers();
                ARBShaderObjects.glUniform2fARB(n2, f, f2);
                this.checkGLError();
            }
        }
    }

    public float[] getValue() {
        int n = this.getProgram();
        return this.programValues[n];
    }

    @Override
    protected void onProgramSet(int n) {
        if (n >= this.programValues.length) {
            float[][] fArray = this.programValues;
            float[][] fArrayArray = new float[n + 10][];
            System.arraycopy(fArray, 0, fArrayArray, 0, fArray.length);
            this.programValues = fArrayArray;
        }
        if (this.programValues[n] == null) {
            this.programValues[n] = new float[]{-3.4028235E38f, -3.4028235E38f};
        }
    }

    @Override
    protected void resetValue() {
        this.programValues = new float[][]{{-3.4028235E38f, -3.4028235E38f}};
    }
}

