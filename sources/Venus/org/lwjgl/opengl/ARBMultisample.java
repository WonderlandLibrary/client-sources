/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class ARBMultisample {
    public static final int GL_MULTISAMPLE_ARB = 32925;
    public static final int GL_SAMPLE_ALPHA_TO_COVERAGE_ARB = 32926;
    public static final int GL_SAMPLE_ALPHA_TO_ONE_ARB = 32927;
    public static final int GL_SAMPLE_COVERAGE_ARB = 32928;
    public static final int GL_MULTISAMPLE_BIT_ARB = 0x20000000;
    public static final int GL_SAMPLE_BUFFERS_ARB = 32936;
    public static final int GL_SAMPLES_ARB = 32937;
    public static final int GL_SAMPLE_COVERAGE_VALUE_ARB = 32938;
    public static final int GL_SAMPLE_COVERAGE_INVERT_ARB = 32939;

    protected ARBMultisample() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glSampleCoverageARB);
    }

    public static native void glSampleCoverageARB(@NativeType(value="GLfloat") float var0, @NativeType(value="GLboolean") boolean var1);

    static {
        GL.initialize();
    }
}

