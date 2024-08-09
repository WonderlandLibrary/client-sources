/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class NVBindlessMultiDrawIndirectCount {
    protected NVBindlessMultiDrawIndirectCount() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glMultiDrawArraysIndirectBindlessCountNV, gLCapabilities.glMultiDrawElementsIndirectBindlessCountNV);
    }

    public static native void nglMultiDrawArraysIndirectBindlessCountNV(int var0, long var1, long var3, int var5, int var6, int var7);

    public static void glMultiDrawArraysIndirectBindlessCountNV(@NativeType(value="GLenum") int n, @NativeType(value="void const *") ByteBuffer byteBuffer, @NativeType(value="GLintptr") long l, @NativeType(value="GLsizei") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="GLint") int n4) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)byteBuffer, n2 * (n3 == 0 ? 16 + n4 * 24 : n3));
        }
        NVBindlessMultiDrawIndirectCount.nglMultiDrawArraysIndirectBindlessCountNV(n, MemoryUtil.memAddress(byteBuffer), l, n2, n3, n4);
    }

    public static void glMultiDrawArraysIndirectBindlessCountNV(@NativeType(value="GLenum") int n, @NativeType(value="void const *") long l, @NativeType(value="GLintptr") long l2, @NativeType(value="GLsizei") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="GLint") int n4) {
        NVBindlessMultiDrawIndirectCount.nglMultiDrawArraysIndirectBindlessCountNV(n, l, l2, n2, n3, n4);
    }

    public static native void nglMultiDrawElementsIndirectBindlessCountNV(int var0, int var1, long var2, long var4, int var6, int var7, int var8);

    public static void glMultiDrawElementsIndirectBindlessCountNV(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="void const *") ByteBuffer byteBuffer, @NativeType(value="GLintptr") long l, @NativeType(value="GLsizei") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLint") int n5) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)byteBuffer, n3 * (n4 == 0 ? (n5 + 2) * 24 : n4));
        }
        NVBindlessMultiDrawIndirectCount.nglMultiDrawElementsIndirectBindlessCountNV(n, n2, MemoryUtil.memAddress(byteBuffer), l, n3, n4, n5);
    }

    public static void glMultiDrawElementsIndirectBindlessCountNV(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="void const *") long l, @NativeType(value="GLintptr") long l2, @NativeType(value="GLsizei") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLint") int n5) {
        NVBindlessMultiDrawIndirectCount.nglMultiDrawElementsIndirectBindlessCountNV(n, n2, l, l2, n3, n4, n5);
    }

    static {
        GL.initialize();
    }
}

