/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.Buffer;
import java.nio.IntBuffer;
import java.util.Set;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL43C;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class ARBFramebufferNoAttachments {
    public static final int GL_FRAMEBUFFER_DEFAULT_WIDTH = 37648;
    public static final int GL_FRAMEBUFFER_DEFAULT_HEIGHT = 37649;
    public static final int GL_FRAMEBUFFER_DEFAULT_LAYERS = 37650;
    public static final int GL_FRAMEBUFFER_DEFAULT_SAMPLES = 37651;
    public static final int GL_FRAMEBUFFER_DEFAULT_FIXED_SAMPLE_LOCATIONS = 37652;
    public static final int GL_MAX_FRAMEBUFFER_WIDTH = 37653;
    public static final int GL_MAX_FRAMEBUFFER_HEIGHT = 37654;
    public static final int GL_MAX_FRAMEBUFFER_LAYERS = 37655;
    public static final int GL_MAX_FRAMEBUFFER_SAMPLES = 37656;

    protected ARBFramebufferNoAttachments() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities, Set<String> set) {
        return Checks.checkFunctions(gLCapabilities.glFramebufferParameteri, gLCapabilities.glGetFramebufferParameteriv, set.contains("GL_EXT_direct_state_access") ? gLCapabilities.glNamedFramebufferParameteriEXT : -1L, set.contains("GL_EXT_direct_state_access") ? gLCapabilities.glGetNamedFramebufferParameterivEXT : -1L);
    }

    public static void glFramebufferParameteri(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint") int n3) {
        GL43C.glFramebufferParameteri(n, n2, n3);
    }

    public static void nglGetFramebufferParameteriv(int n, int n2, long l) {
        GL43C.nglGetFramebufferParameteriv(n, n2, l);
    }

    public static void glGetFramebufferParameteriv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") IntBuffer intBuffer) {
        GL43C.glGetFramebufferParameteriv(n, n2, intBuffer);
    }

    @NativeType(value="void")
    public static int glGetFramebufferParameteri(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2) {
        return GL43C.glGetFramebufferParameteri(n, n2);
    }

    public static native void glNamedFramebufferParameteriEXT(@NativeType(value="GLuint") int var0, @NativeType(value="GLenum") int var1, @NativeType(value="GLint") int var2);

    public static native void nglGetNamedFramebufferParameterivEXT(int var0, int var1, long var2);

    public static void glGetNamedFramebufferParameterivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        ARBFramebufferNoAttachments.nglGetNamedFramebufferParameterivEXT(n, n2, MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glGetNamedFramebufferParameteriEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            ARBFramebufferNoAttachments.nglGetNamedFramebufferParameterivEXT(n, n2, MemoryUtil.memAddress(intBuffer));
            int n4 = intBuffer.get(0);
            return n4;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static void glGetFramebufferParameteriv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") int[] nArray) {
        GL43C.glGetFramebufferParameteriv(n, n2, nArray);
    }

    public static void glGetNamedFramebufferParameterivEXT(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") int[] nArray) {
        long l = GL.getICD().glGetNamedFramebufferParameterivEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.callPV(n, n2, nArray, l);
    }

    static {
        GL.initialize();
    }
}

