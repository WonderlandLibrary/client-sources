/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders.uniform;

import net.optifine.shaders.uniform.ShaderUniformBase;
import org.lwjgl.opengl.ARBShaderObjects;

public class ShaderUniform2i
extends ShaderUniformBase {
    private int[][] programValues;
    private static final int VALUE_UNKNOWN = Integer.MIN_VALUE;

    public ShaderUniform2i(String string) {
        super(string);
        this.resetValue();
    }

    public void setValue(int n, int n2) {
        int n3 = this.getProgram();
        int[] nArray = this.programValues[n3];
        if (nArray[0] != n || nArray[1] != n2) {
            nArray[0] = n;
            nArray[1] = n2;
            int n4 = this.getLocation();
            if (n4 >= 0) {
                ShaderUniform2i.flushRenderBuffers();
                ARBShaderObjects.glUniform2iARB(n4, n, n2);
                this.checkGLError();
            }
        }
    }

    public int[] getValue() {
        int n = this.getProgram();
        return this.programValues[n];
    }

    @Override
    protected void onProgramSet(int n) {
        if (n >= this.programValues.length) {
            int[][] nArray = this.programValues;
            int[][] nArrayArray = new int[n + 10][];
            System.arraycopy(nArray, 0, nArrayArray, 0, nArray.length);
            this.programValues = nArrayArray;
        }
        if (this.programValues[n] == null) {
            this.programValues[n] = new int[]{Integer.MIN_VALUE, Integer.MIN_VALUE};
        }
    }

    @Override
    protected void resetValue() {
        this.programValues = new int[][]{{Integer.MIN_VALUE, Integer.MIN_VALUE}};
    }
}

