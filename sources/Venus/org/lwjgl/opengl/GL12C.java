/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import javax.annotation.Nullable;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11C;
import org.lwjgl.opengl.GLChecks;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class GL12C
extends GL11C {
    public static final int GL_ALIASED_LINE_WIDTH_RANGE = 33902;
    public static final int GL_SMOOTH_POINT_SIZE_RANGE = 2834;
    public static final int GL_SMOOTH_POINT_SIZE_GRANULARITY = 2835;
    public static final int GL_SMOOTH_LINE_WIDTH_RANGE = 2850;
    public static final int GL_SMOOTH_LINE_WIDTH_GRANULARITY = 2851;
    public static final int GL_TEXTURE_BINDING_3D = 32874;
    public static final int GL_PACK_SKIP_IMAGES = 32875;
    public static final int GL_PACK_IMAGE_HEIGHT = 32876;
    public static final int GL_UNPACK_SKIP_IMAGES = 32877;
    public static final int GL_UNPACK_IMAGE_HEIGHT = 32878;
    public static final int GL_TEXTURE_3D = 32879;
    public static final int GL_PROXY_TEXTURE_3D = 32880;
    public static final int GL_TEXTURE_DEPTH = 32881;
    public static final int GL_TEXTURE_WRAP_R = 32882;
    public static final int GL_MAX_3D_TEXTURE_SIZE = 32883;
    public static final int GL_BGR = 32992;
    public static final int GL_BGRA = 32993;
    public static final int GL_UNSIGNED_BYTE_3_3_2 = 32818;
    public static final int GL_UNSIGNED_BYTE_2_3_3_REV = 33634;
    public static final int GL_UNSIGNED_SHORT_5_6_5 = 33635;
    public static final int GL_UNSIGNED_SHORT_5_6_5_REV = 33636;
    public static final int GL_UNSIGNED_SHORT_4_4_4_4 = 32819;
    public static final int GL_UNSIGNED_SHORT_4_4_4_4_REV = 33637;
    public static final int GL_UNSIGNED_SHORT_5_5_5_1 = 32820;
    public static final int GL_UNSIGNED_SHORT_1_5_5_5_REV = 33638;
    public static final int GL_UNSIGNED_INT_8_8_8_8 = 32821;
    public static final int GL_UNSIGNED_INT_8_8_8_8_REV = 33639;
    public static final int GL_UNSIGNED_INT_10_10_10_2 = 32822;
    public static final int GL_UNSIGNED_INT_2_10_10_10_REV = 33640;
    public static final int GL_CLAMP_TO_EDGE = 33071;
    public static final int GL_TEXTURE_MIN_LOD = 33082;
    public static final int GL_TEXTURE_MAX_LOD = 33083;
    public static final int GL_TEXTURE_BASE_LEVEL = 33084;
    public static final int GL_TEXTURE_MAX_LEVEL = 33085;
    public static final int GL_MAX_ELEMENTS_VERTICES = 33000;
    public static final int GL_MAX_ELEMENTS_INDICES = 33001;

    protected GL12C() {
        throw new UnsupportedOperationException();
    }

    public static native void nglTexImage3D(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, long var9);

    public static void glTexImage3D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLint") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="GLenum") int n9, @Nullable @NativeType(value="void const *") ByteBuffer byteBuffer) {
        GL12C.nglTexImage3D(n, n2, n3, n4, n5, n6, n7, n8, n9, MemoryUtil.memAddressSafe(byteBuffer));
    }

    public static void glTexImage3D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLint") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="GLenum") int n9, @Nullable @NativeType(value="void const *") long l) {
        GL12C.nglTexImage3D(n, n2, n3, n4, n5, n6, n7, n8, n9, l);
    }

    public static void glTexImage3D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLint") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="GLenum") int n9, @Nullable @NativeType(value="void const *") ShortBuffer shortBuffer) {
        GL12C.nglTexImage3D(n, n2, n3, n4, n5, n6, n7, n8, n9, MemoryUtil.memAddressSafe(shortBuffer));
    }

    public static void glTexImage3D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLint") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="GLenum") int n9, @Nullable @NativeType(value="void const *") IntBuffer intBuffer) {
        GL12C.nglTexImage3D(n, n2, n3, n4, n5, n6, n7, n8, n9, MemoryUtil.memAddressSafe(intBuffer));
    }

    public static void glTexImage3D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLint") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="GLenum") int n9, @Nullable @NativeType(value="void const *") FloatBuffer floatBuffer) {
        GL12C.nglTexImage3D(n, n2, n3, n4, n5, n6, n7, n8, n9, MemoryUtil.memAddressSafe(floatBuffer));
    }

    public static void glTexImage3D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLint") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="GLenum") int n9, @Nullable @NativeType(value="void const *") DoubleBuffer doubleBuffer) {
        GL12C.nglTexImage3D(n, n2, n3, n4, n5, n6, n7, n8, n9, MemoryUtil.memAddressSafe(doubleBuffer));
    }

    public static native void nglTexSubImage3D(int var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, long var10);

    public static void glTexSubImage3D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="GLenum") int n10, @NativeType(value="void const *") ByteBuffer byteBuffer) {
        GL12C.nglTexSubImage3D(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, MemoryUtil.memAddress(byteBuffer));
    }

    public static void glTexSubImage3D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="GLenum") int n10, @NativeType(value="void const *") long l) {
        GL12C.nglTexSubImage3D(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, l);
    }

    public static void glTexSubImage3D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="GLenum") int n10, @NativeType(value="void const *") ShortBuffer shortBuffer) {
        GL12C.nglTexSubImage3D(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, MemoryUtil.memAddress(shortBuffer));
    }

    public static void glTexSubImage3D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="GLenum") int n10, @NativeType(value="void const *") IntBuffer intBuffer) {
        GL12C.nglTexSubImage3D(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, MemoryUtil.memAddress(intBuffer));
    }

    public static void glTexSubImage3D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="GLenum") int n10, @NativeType(value="void const *") FloatBuffer floatBuffer) {
        GL12C.nglTexSubImage3D(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, MemoryUtil.memAddress(floatBuffer));
    }

    public static void glTexSubImage3D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="GLenum") int n10, @NativeType(value="void const *") DoubleBuffer doubleBuffer) {
        GL12C.nglTexSubImage3D(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void glCopyTexSubImage3D(@NativeType(value="GLenum") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLint") int var2, @NativeType(value="GLint") int var3, @NativeType(value="GLint") int var4, @NativeType(value="GLint") int var5, @NativeType(value="GLint") int var6, @NativeType(value="GLsizei") int var7, @NativeType(value="GLsizei") int var8);

    public static native void nglDrawRangeElements(int var0, int var1, int var2, int var3, int var4, long var5);

    public static void glDrawRangeElements(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="void const *") long l) {
        GL12C.nglDrawRangeElements(n, n2, n3, n4, n5, l);
    }

    public static void glDrawRangeElements(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="void const *") ByteBuffer byteBuffer) {
        GL12C.nglDrawRangeElements(n, n2, n3, byteBuffer.remaining() >> GLChecks.typeToByteShift(n4), n4, MemoryUtil.memAddress(byteBuffer));
    }

    public static void glDrawRangeElements(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="void const *") ByteBuffer byteBuffer) {
        GL12C.nglDrawRangeElements(n, n2, n3, byteBuffer.remaining(), 5121, MemoryUtil.memAddress(byteBuffer));
    }

    public static void glDrawRangeElements(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="void const *") ShortBuffer shortBuffer) {
        GL12C.nglDrawRangeElements(n, n2, n3, shortBuffer.remaining(), 5123, MemoryUtil.memAddress(shortBuffer));
    }

    public static void glDrawRangeElements(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="void const *") IntBuffer intBuffer) {
        GL12C.nglDrawRangeElements(n, n2, n3, intBuffer.remaining(), 5125, MemoryUtil.memAddress(intBuffer));
    }

    public static void glTexImage3D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLint") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="GLenum") int n9, @Nullable @NativeType(value="void const *") short[] sArray) {
        long l = GL.getICD().glTexImage3D;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, n7, n8, n9, sArray, l);
    }

    public static void glTexImage3D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLint") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="GLenum") int n9, @Nullable @NativeType(value="void const *") int[] nArray) {
        long l = GL.getICD().glTexImage3D;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, n7, n8, n9, nArray, l);
    }

    public static void glTexImage3D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLint") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="GLenum") int n9, @Nullable @NativeType(value="void const *") float[] fArray) {
        long l = GL.getICD().glTexImage3D;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, n7, n8, n9, fArray, l);
    }

    public static void glTexImage3D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLsizei") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLint") int n7, @NativeType(value="GLenum") int n8, @NativeType(value="GLenum") int n9, @Nullable @NativeType(value="void const *") double[] dArray) {
        long l = GL.getICD().glTexImage3D;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, n7, n8, n9, dArray, l);
    }

    public static void glTexSubImage3D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="GLenum") int n10, @NativeType(value="void const *") short[] sArray) {
        long l = GL.getICD().glTexSubImage3D;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, sArray, l);
    }

    public static void glTexSubImage3D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="GLenum") int n10, @NativeType(value="void const *") int[] nArray) {
        long l = GL.getICD().glTexSubImage3D;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, nArray, l);
    }

    public static void glTexSubImage3D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="GLenum") int n10, @NativeType(value="void const *") float[] fArray) {
        long l = GL.getICD().glTexSubImage3D;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, fArray, l);
    }

    public static void glTexSubImage3D(@NativeType(value="GLenum") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLsizei") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="GLsizei") int n8, @NativeType(value="GLenum") int n9, @NativeType(value="GLenum") int n10, @NativeType(value="void const *") double[] dArray) {
        long l = GL.getICD().glTexSubImage3D;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, n2, n3, n4, n5, n6, n7, n8, n9, n10, dArray, l);
    }

    static {
        GL.initialize();
    }
}

