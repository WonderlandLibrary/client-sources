/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders.uniform;

import net.optifine.shaders.uniform.ShaderUniformBase;
import org.lwjgl.opengl.ARBShaderObjects;

public class ShaderUniform1i
extends ShaderUniformBase {
    private int[] programValues;
    private static final int VALUE_UNKNOWN = Integer.MIN_VALUE;

    public ShaderUniform1i(String string) {
        super(string);
        this.resetValue();
    }

    public void setValue(int n) {
        int n2 = this.getProgram();
        int n3 = this.programValues[n2];
        if (n != n3) {
            this.programValues[n2] = n;
            int n4 = this.getLocation();
            if (n4 >= 0) {
                ShaderUniform1i.flushRenderBuffers();
                ARBShaderObjects.glUniform1iARB(n4, n);
                this.checkGLError();
            }
        }
    }

    public int getValue() {
        int n = this.getProgram();
        return this.programValues[n];
    }

    @Override
    protected void onProgramSet(int n) {
        if (n >= this.programValues.length) {
            int[] nArray = this.programValues;
            int[] nArray2 = new int[n + 10];
            System.arraycopy(nArray, 0, nArray2, 0, nArray.length);
            for (int i = nArray.length; i < nArray2.length; ++i) {
                nArray2[i] = Integer.MIN_VALUE;
            }
            this.programValues = nArray2;
        }
    }

    @Override
    protected void resetValue() {
        this.programValues = new int[]{Integer.MIN_VALUE};
    }
}

