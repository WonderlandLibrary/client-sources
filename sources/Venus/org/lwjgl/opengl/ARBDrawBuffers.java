/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.IntBuffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class ARBDrawBuffers {
    public static final int GL_MAX_DRAW_BUFFERS_ARB = 34852;
    public static final int GL_DRAW_BUFFER0_ARB = 34853;
    public static final int GL_DRAW_BUFFER1_ARB = 34854;
    public static final int GL_DRAW_BUFFER2_ARB = 34855;
    public static final int GL_DRAW_BUFFER3_ARB = 34856;
    public static final int GL_DRAW_BUFFER4_ARB = 34857;
    public static final int GL_DRAW_BUFFER5_ARB = 34858;
    public static final int GL_DRAW_BUFFER6_ARB = 34859;
    public static final int GL_DRAW_BUFFER7_ARB = 34860;
    public static final int GL_DRAW_BUFFER8_ARB = 34861;
    public static final int GL_DRAW_BUFFER9_ARB = 34862;
    public static final int GL_DRAW_BUFFER10_ARB = 34863;
    public static final int GL_DRAW_BUFFER11_ARB = 34864;
    public static final int GL_DRAW_BUFFER12_ARB = 34865;
    public static final int GL_DRAW_BUFFER13_ARB = 34866;
    public static final int GL_DRAW_BUFFER14_ARB = 34867;
    public static final int GL_DRAW_BUFFER15_ARB = 34868;

    protected ARBDrawBuffers() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glDrawBuffersARB);
    }

    public static native void nglDrawBuffersARB(int var0, long var1);

    public static void glDrawBuffersARB(@NativeType(value="GLenum const *") IntBuffer intBuffer) {
        ARBDrawBuffers.nglDrawBuffersARB(intBuffer.remaining(), MemoryUtil.memAddress(intBuffer));
    }

    public static void glDrawBuffersARB(@NativeType(value="GLenum const *") int[] nArray) {
        long l = GL.getICD().glDrawBuffersARB;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(nArray.length, nArray, l);
    }

    static {
        GL.initialize();
    }
}

