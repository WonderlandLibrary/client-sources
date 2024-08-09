/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.opengl.GLChecks;
import org.lwjgl.system.Checks;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class ARBDrawInstanced {
    protected ARBDrawInstanced() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glDrawArraysInstancedARB, gLCapabilities.glDrawElementsInstancedARB);
    }

    public static native void glDrawArraysInstancedARB(@NativeType(value="GLenum") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLsizei") int var2, @NativeType(value="GLsizei") int var3);

    public static native void nglDrawElementsInstancedARB(int var0, int var1, int var2, long var3, int var5);

    public static void glDrawElementsInstancedARB(@NativeType(value="GLenum") int n, @NativeType(value="GLsizei") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="void const *") long l, @NativeType(value="GLsizei") int n4) {
        ARBDrawInstanced.nglDrawElementsInstancedARB(n, n2, n3, l, n4);
    }

    public static void glDrawElementsInstancedARB(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="void const *") ByteBuffer byteBuffer, @NativeType(value="GLsizei") int n3) {
        ARBDrawInstanced.nglDrawElementsInstancedARB(n, byteBuffer.remaining() >> GLChecks.typeToByteShift(n2), n2, MemoryUtil.memAddress(byteBuffer), n3);
    }

    public static void glDrawElementsInstancedARB(@NativeType(value="GLenum") int n, @NativeType(value="void const *") ByteBuffer byteBuffer, @NativeType(value="GLsizei") int n2) {
        ARBDrawInstanced.nglDrawElementsInstancedARB(n, byteBuffer.remaining(), 5121, MemoryUtil.memAddress(byteBuffer), n2);
    }

    public static void glDrawElementsInstancedARB(@NativeType(value="GLenum") int n, @NativeType(value="void const *") ShortBuffer shortBuffer, @NativeType(value="GLsizei") int n2) {
        ARBDrawInstanced.nglDrawElementsInstancedARB(n, shortBuffer.remaining(), 5123, MemoryUtil.memAddress(shortBuffer), n2);
    }

    public static void glDrawElementsInstancedARB(@NativeType(value="GLenum") int n, @NativeType(value="void const *") IntBuffer intBuffer, @NativeType(value="GLsizei") int n2) {
        ARBDrawInstanced.nglDrawElementsInstancedARB(n, intBuffer.remaining(), 5125, MemoryUtil.memAddress(intBuffer), n2);
    }

    static {
        GL.initialize();
    }
}

