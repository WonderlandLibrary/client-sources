/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders.uniform;

import java.util.Arrays;
import net.optifine.shaders.Shaders;
import org.lwjgl.opengl.ARBShaderObjects;

public abstract class ShaderUniformBase {
    private String name;
    private int program = 0;
    private int[] locations = new int[]{-1};
    private static final int LOCATION_UNDEFINED = -1;
    private static final int LOCATION_UNKNOWN = Integer.MIN_VALUE;

    public ShaderUniformBase(String string) {
        this.name = string;
    }

    public void setProgram(int n) {
        if (this.program != n) {
            this.program = n;
            this.expandLocations();
            this.onProgramSet(n);
        }
    }

    private void expandLocations() {
        if (this.program >= this.locations.length) {
            int[] nArray = new int[this.program * 2];
            Arrays.fill(nArray, Integer.MIN_VALUE);
            System.arraycopy(this.locations, 0, nArray, 0, this.locations.length);
            this.locations = nArray;
        }
    }

    protected abstract void onProgramSet(int var1);

    public String getName() {
        return this.name;
    }

    public int getProgram() {
        return this.program;
    }

    public int getLocation() {
        if (this.program <= 0) {
            return 1;
        }
        int n = this.locations[this.program];
        if (n == Integer.MIN_VALUE) {
            this.locations[this.program] = n = ARBShaderObjects.glGetUniformLocationARB(this.program, this.name);
        }
        return n;
    }

    public boolean isDefined() {
        return this.getLocation() >= 0;
    }

    public void disable() {
        this.locations[this.program] = -1;
    }

    public void reset() {
        this.program = 0;
        this.locations = new int[]{-1};
        this.resetValue();
    }

    protected abstract void resetValue();

    protected void checkGLError() {
        if (Shaders.checkGLError(this.name) != 0) {
            this.disable();
        }
    }

    protected static final void flushRenderBuffers() {
        Shaders.flushRenderBuffers();
    }

    public String toString() {
        return this.name;
    }
}

