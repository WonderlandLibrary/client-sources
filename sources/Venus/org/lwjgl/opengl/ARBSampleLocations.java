/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.FloatBuffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class ARBSampleLocations {
    public static final int GL_SAMPLE_LOCATION_SUBPIXEL_BITS_ARB = 37693;
    public static final int GL_SAMPLE_LOCATION_PIXEL_GRID_WIDTH_ARB = 37694;
    public static final int GL_SAMPLE_LOCATION_PIXEL_GRID_HEIGHT_ARB = 37695;
    public static final int GL_PROGRAMMABLE_SAMPLE_LOCATION_TABLE_SIZE_ARB = 37696;
    public static final int GL_FRAMEBUFFER_PROGRAMMABLE_SAMPLE_LOCATIONS_ARB = 37698;
    public static final int GL_FRAMEBUFFER_SAMPLE_LOCATION_PIXEL_GRID_ARB = 37699;

    protected ARBSampleLocations() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glFramebufferSampleLocationsfvARB, gLCapabilities.glNamedFramebufferSampleLocationsfvARB, gLCapabilities.glEvaluateDepthValuesARB);
    }

    public static native void nglFramebufferSampleLocationsfvARB(int var0, int var1, int var2, long var3);

    public static void glFramebufferSampleLocationsfvARB(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        ARBSampleLocations.nglFramebufferSampleLocationsfvARB(n, n2, floatBuffer.remaining() >> 1, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglNamedFramebufferSampleLocationsfvARB(int var0, int var1, int var2, long var3);

    public static void glNamedFramebufferSampleLocationsfvARB(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        ARBSampleLocations.nglNamedFramebufferSampleLocationsfvARB(n, n2, floatBuffer.remaining() >> 1, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void glEvaluateDepthValuesARB();

    public static void glFramebufferSampleLocationsfvARB(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glFramebufferSampleLocationsfvARB;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, fArray.length >> 1, fArray, l);
    }

    public static void glNamedFramebufferSampleLocationsfvARB(@NativeType(value="GLuint") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glNamedFramebufferSampleLocationsfvARB;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, fArray.length >> 1, fArray, l);
    }

    static {
        GL.initialize();
    }
}

