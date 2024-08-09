/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.FloatBuffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL20C;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class GL21C
extends GL20C {
    public static final int GL_FLOAT_MAT2x3 = 35685;
    public static final int GL_FLOAT_MAT2x4 = 35686;
    public static final int GL_FLOAT_MAT3x2 = 35687;
    public static final int GL_FLOAT_MAT3x4 = 35688;
    public static final int GL_FLOAT_MAT4x2 = 35689;
    public static final int GL_FLOAT_MAT4x3 = 35690;
    public static final int GL_PIXEL_PACK_BUFFER = 35051;
    public static final int GL_PIXEL_UNPACK_BUFFER = 35052;
    public static final int GL_PIXEL_PACK_BUFFER_BINDING = 35053;
    public static final int GL_PIXEL_UNPACK_BUFFER_BINDING = 35055;
    public static final int GL_SRGB = 35904;
    public static final int GL_SRGB8 = 35905;
    public static final int GL_SRGB_ALPHA = 35906;
    public static final int GL_SRGB8_ALPHA8 = 35907;
    public static final int GL_COMPRESSED_SRGB = 35912;
    public static final int GL_COMPRESSED_SRGB_ALPHA = 35913;

    protected GL21C() {
        throw new UnsupportedOperationException();
    }

    public static native void nglUniformMatrix2x3fv(int var0, int var1, boolean var2, long var3);

    public static void glUniformMatrix2x3fv(@NativeType(value="GLint") int n, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        GL21C.nglUniformMatrix2x3fv(n, floatBuffer.remaining() / 6, bl, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglUniformMatrix3x2fv(int var0, int var1, boolean var2, long var3);

    public static void glUniformMatrix3x2fv(@NativeType(value="GLint") int n, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        GL21C.nglUniformMatrix3x2fv(n, floatBuffer.remaining() / 6, bl, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglUniformMatrix2x4fv(int var0, int var1, boolean var2, long var3);

    public static void glUniformMatrix2x4fv(@NativeType(value="GLint") int n, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        GL21C.nglUniformMatrix2x4fv(n, floatBuffer.remaining() >> 3, bl, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglUniformMatrix4x2fv(int var0, int var1, boolean var2, long var3);

    public static void glUniformMatrix4x2fv(@NativeType(value="GLint") int n, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        GL21C.nglUniformMatrix4x2fv(n, floatBuffer.remaining() >> 3, bl, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglUniformMatrix3x4fv(int var0, int var1, boolean var2, long var3);

    public static void glUniformMatrix3x4fv(@NativeType(value="GLint") int n, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        GL21C.nglUniformMatrix3x4fv(n, floatBuffer.remaining() / 12, bl, MemoryUtil.memAddress(floatBuffer));
    }

    public static native void nglUniformMatrix4x3fv(int var0, int var1, boolean var2, long var3);

    public static void glUniformMatrix4x3fv(@NativeType(value="GLint") int n, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        GL21C.nglUniformMatrix4x3fv(n, floatBuffer.remaining() / 12, bl, MemoryUtil.memAddress(floatBuffer));
    }

    public static void glUniformMatrix2x3fv(@NativeType(value="GLint") int n, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glUniformMatrix2x3fv;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, fArray.length / 6, bl, fArray, l);
    }

    public static void glUniformMatrix3x2fv(@NativeType(value="GLint") int n, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glUniformMatrix3x2fv;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, fArray.length / 6, bl, fArray, l);
    }

    public static void glUniformMatrix2x4fv(@NativeType(value="GLint") int n, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glUniformMatrix2x4fv;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, fArray.length >> 3, bl, fArray, l);
    }

    public static void glUniformMatrix4x2fv(@NativeType(value="GLint") int n, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glUniformMatrix4x2fv;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, fArray.length >> 3, bl, fArray, l);
    }

    public static void glUniformMatrix3x4fv(@NativeType(value="GLint") int n, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glUniformMatrix3x4fv;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, fArray.length / 12, bl, fArray, l);
    }

    public static void glUniformMatrix4x3fv(@NativeType(value="GLint") int n, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") float[] fArray) {
        long l = GL.getICD().glUniformMatrix4x3fv;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, fArray.length / 12, bl, fArray, l);
    }

    static {
        GL.initialize();
    }
}

