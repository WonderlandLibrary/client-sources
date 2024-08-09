/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class ARBIndirectParameters {
    public static final int GL_PARAMETER_BUFFER_ARB = 33006;
    public static final int GL_PARAMETER_BUFFER_BINDING_ARB = 33007;

    protected ARBIndirectParameters() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glMultiDrawArraysIndirectCountARB, gLCapabilities.glMultiDrawElementsIndirectCountARB);
    }

    public static native void nglMultiDrawArraysIndirectCountARB(int var0, long var1, long var3, int var5, int var6);

    public static void glMultiDrawArraysIndirectCountARB(@NativeType(value="GLenum") int n, @NativeType(value="void const *") ByteBuffer byteBuffer, @NativeType(value="GLintptr") long l, @NativeType(value="GLsizei") int n2, @NativeType(value="GLsizei") int n3) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)byteBuffer, n2 * (n3 == 0 ? 16 : n3));
        }
        ARBIndirectParameters.nglMultiDrawArraysIndirectCountARB(n, MemoryUtil.memAddress(byteBuffer), l, n2, n3);
    }

    public static void glMultiDrawArraysIndirectCountARB(@NativeType(value="GLenum") int n, @NativeType(value="void const *") long l, @NativeType(value="GLintptr") long l2, @NativeType(value="GLsizei") int n2, @NativeType(value="GLsizei") int n3) {
        ARBIndirectParameters.nglMultiDrawArraysIndirectCountARB(n, l, l2, n2, n3);
    }

    public static void glMultiDrawArraysIndirectCountARB(@NativeType(value="GLenum") int n, @NativeType(value="void const *") IntBuffer intBuffer, @NativeType(value="GLintptr") long l, @NativeType(value="GLsizei") int n2, @NativeType(value="GLsizei") int n3) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, n2 * (n3 == 0 ? 16 : n3) >> 2);
        }
        ARBIndirectParameters.nglMultiDrawArraysIndirectCountARB(n, MemoryUtil.memAddress(intBuffer), l, n2, n3);
    }

    public static native void nglMultiDrawElementsIndirectCountARB(int var0, int var1, long var2, long var4, int var6, int var7);

    public static void glMultiDrawElementsIndirectCountARB(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="void const *") ByteBuffer byteBuffer, @NativeType(value="GLintptr") long l, @NativeType(value="GLsizei") int n3, @NativeType(value="GLsizei") int n4) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)byteBuffer, n3 * (n4 == 0 ? 20 : n4));
        }
        ARBIndirectParameters.nglMultiDrawElementsIndirectCountARB(n, n2, MemoryUtil.memAddress(byteBuffer), l, n3, n4);
    }

    public static void glMultiDrawElementsIndirectCountARB(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="void const *") long l, @NativeType(value="GLintptr") long l2, @NativeType(value="GLsizei") int n3, @NativeType(value="GLsizei") int n4) {
        ARBIndirectParameters.nglMultiDrawElementsIndirectCountARB(n, n2, l, l2, n3, n4);
    }

    public static void glMultiDrawElementsIndirectCountARB(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="void const *") IntBuffer intBuffer, @NativeType(value="GLintptr") long l, @NativeType(value="GLsizei") int n3, @NativeType(value="GLsizei") int n4) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, n3 * (n4 == 0 ? 20 : n4) >> 2);
        }
        ARBIndirectParameters.nglMultiDrawElementsIndirectCountARB(n, n2, MemoryUtil.memAddress(intBuffer), l, n3, n4);
    }

    public static void glMultiDrawArraysIndirectCountARB(@NativeType(value="GLenum") int n, @NativeType(value="void const *") int[] nArray, @NativeType(value="GLintptr") long l, @NativeType(value="GLsizei") int n2, @NativeType(value="GLsizei") int n3) {
        long l2 = GL.getICD().glMultiDrawArraysIndirectCountARB;
        if (Checks.CHECKS) {
            Checks.check(l2);
            Checks.check(nArray, n2 * (n3 == 0 ? 16 : n3) >> 2);
        }
        JNI.callPPV(n, nArray, l, n2, n3, l2);
    }

    public static void glMultiDrawElementsIndirectCountARB(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="void const *") int[] nArray, @NativeType(value="GLintptr") long l, @NativeType(value="GLsizei") int n3, @NativeType(value="GLsizei") int n4) {
        long l2 = GL.getICD().glMultiDrawElementsIndirectCountARB;
        if (Checks.CHECKS) {
            Checks.check(l2);
            Checks.check(nArray, n3 * (n4 == 0 ? 20 : n4) >> 2);
        }
        JNI.callPPV(n, n2, nArray, l, n3, n4, l2);
    }

    static {
        GL.initialize();
    }
}

