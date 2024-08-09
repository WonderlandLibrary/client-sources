/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import javax.annotation.Nullable;
import org.lwjgl.PointerBuffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL31C;
import org.lwjgl.opengl.GLChecks;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class GL32C
extends GL31C {
    public static final int GL_CONTEXT_PROFILE_MASK = 37158;
    public static final int GL_CONTEXT_CORE_PROFILE_BIT = 1;
    public static final int GL_CONTEXT_COMPATIBILITY_PROFILE_BIT = 2;
    public static final int GL_MAX_VERTEX_OUTPUT_COMPONENTS = 37154;
    public static final int GL_MAX_GEOMETRY_INPUT_COMPONENTS = 37155;
    public static final int GL_MAX_GEOMETRY_OUTPUT_COMPONENTS = 37156;
    public static final int GL_MAX_FRAGMENT_INPUT_COMPONENTS = 37157;
    public static final int GL_FIRST_VERTEX_CONVENTION = 36429;
    public static final int GL_LAST_VERTEX_CONVENTION = 36430;
    public static final int GL_PROVOKING_VERTEX = 36431;
    public static final int GL_QUADS_FOLLOW_PROVOKING_VERTEX_CONVENTION = 36428;
    public static final int GL_TEXTURE_CUBE_MAP_SEAMLESS = 34895;
    public static final int GL_SAMPLE_POSITION = 36432;
    public static final int GL_SAMPLE_MASK = 36433;
    public static final int GL_SAMPLE_MASK_VALUE = 36434;
    public static final int GL_TEXTURE_2D_MULTISAMPLE = 37120;
    public static final int GL_PROXY_TEXTURE_2D_MULTISAMPLE = 37121;
    public static final int GL_TEXTURE_2D_MULTISAMPLE_ARRAY = 37122;
    public static final int GL_PROXY_TEXTURE_2D_MULTISAMPLE_ARRAY = 37123;
    public static final int GL_MAX_SAMPLE_MASK_WORDS = 36441;
    public static final int GL_MAX_COLOR_TEXTURE_SAMPLES = 37134;
    public static final int GL_MAX_DEPTH_TEXTURE_SAMPLES = 37135;
    public static final int GL_MAX_INTEGER_SAMPLES = 37136;
    public static final int GL_TEXTURE_BINDING_2D_MULTISAMPLE = 37124;
    public static final int GL_TEXTURE_BINDING_2D_MULTISAMPLE_ARRAY = 37125;
    public static final int GL_TEXTURE_SAMPLES = 37126;
    public static final int GL_TEXTURE_FIXED_SAMPLE_LOCATIONS = 37127;
    public static final int GL_SAMPLER_2D_MULTISAMPLE = 37128;
    public static final int GL_INT_SAMPLER_2D_MULTISAMPLE = 37129;
    public static final int GL_UNSIGNED_INT_SAMPLER_2D_MULTISAMPLE = 37130;
    public static final int GL_SAMPLER_2D_MULTISAMPLE_ARRAY = 37131;
    public static final int GL_INT_SAMPLER_2D_MULTISAMPLE_ARRAY = 37132;
    public static final int GL_UNSIGNED_INT_SAMPLER_2D_MULTISAMPLE_ARRAY = 37133;
    public static final int GL_DEPTH_CLAMP = 34383;
    public static final int GL_GEOMETRY_SHADER = 36313;
    public static final int GL_GEOMETRY_VERTICES_OUT = 36314;
    public static final int GL_GEOMETRY_INPUT_TYPE = 36315;
    public static final int GL_GEOMETRY_OUTPUT_TYPE = 36316;
    public static final int GL_MAX_GEOMETRY_TEXTURE_IMAGE_UNITS = 35881;
    public static final int GL_MAX_GEOMETRY_UNIFORM_COMPONENTS = 36319;
    public static final int GL_MAX_GEOMETRY_OUTPUT_VERTICES = 36320;
    public static final int GL_MAX_GEOMETRY_TOTAL_OUTPUT_COMPONENTS = 36321;
    public static final int GL_LINES_ADJACENCY = 10;
    public static final int GL_LINE_STRIP_ADJACENCY = 11;
    public static final int GL_TRIANGLES_ADJACENCY = 12;
    public static final int GL_TRIANGLE_STRIP_ADJACENCY = 13;
    public static final int GL_FRAMEBUFFER_INCOMPLETE_LAYER_TARGETS = 36264;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_LAYERED = 36263;
    public static final int GL_PROGRAM_POINT_SIZE = 34370;
    public static final int GL_MAX_SERVER_WAIT_TIMEOUT = 37137;
    public static final int GL_OBJECT_TYPE = 37138;
    public static final int GL_SYNC_CONDITION = 37139;
    public static final int GL_SYNC_STATUS = 37140;
    public static final int GL_SYNC_FLAGS = 37141;
    public static final int GL_SYNC_FENCE = 37142;
    public static final int GL_SYNC_GPU_COMMANDS_COMPLETE = 37143;
    public static final int GL_UNSIGNALED = 37144;
    public static final int GL_SIGNALED = 37145;
    public static final int GL_SYNC_FLUSH_COMMANDS_BIT = 1;
    public static final long GL_TIMEOUT_IGNORED = -1L;
    public static final int GL_ALREADY_SIGNALED = 37146;
    public static final int GL_TIMEOUT_EXPIRED = 37147;
    public static final int GL_CONDITION_SATISFIED = 37148;
    public static final int GL_WAIT_FAILED = 37149;

    protected GL32C() {
        throw new UnsupportedOperationException();
    }

    public static native void nglGetBufferParameteri64v(int var0, int var1, long var2);

    public static void glGetBufferParameteri64v(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint64 *") LongBuffer longBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)longBuffer, 1);
        }
        GL32C.nglGetBufferParameteri64v(n, n2, MemoryUtil.memAddress(longBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static long glGetBufferParameteri64(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            LongBuffer longBuffer = memoryStack.callocLong(1);
            GL32C.nglGetBufferParameteri64v(n, n2, MemoryUtil.memAddress(longBuffer));
            long l = longBuffer.get(0);
            return l;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native void nglDrawElementsBaseVertex(int var0, int var1, int var2, long var3, int var5);

    public static void glDrawElementsBaseVertex(@NativeType(value="GLenum") int n, @NativeType(value="GLsizei") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="void const *") long l, @NativeType(value="GLint") int n4) {
        GL32C.nglDrawElementsBaseVertex(n, n2, n3, l, n4);
    }

    public static void glDrawElementsBaseVertex(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="void const *") ByteBuffer byteBuffer, @NativeType(value="GLint") int n3) {
        GL32C.nglDrawElementsBaseVertex(n, byteBuffer.remaining() >> GLChecks.typeToByteShift(n2), n2, MemoryUtil.memAddress(byteBuffer), n3);
    }

    public static void glDrawElementsBaseVertex(@NativeType(value="GLenum") int n, @NativeType(value="void const *") ByteBuffer byteBuffer, @NativeType(value="GLint") int n2) {
        GL32C.nglDrawElementsBaseVertex(n, byteBuffer.remaining(), 5121, MemoryUtil.memAddress(byteBuffer), n2);
    }

    public static void glDrawElementsBaseVertex(@NativeType(value="GLenum") int n, @NativeType(value="void const *") ShortBuffer shortBuffer, @NativeType(value="GLint") int n2) {
        GL32C.nglDrawElementsBaseVertex(n, shortBuffer.remaining(), 5123, MemoryUtil.memAddress(shortBuffer), n2);
    }

    public static void glDrawElementsBaseVertex(@NativeType(value="GLenum") int n, @NativeType(value="void const *") IntBuffer intBuffer, @NativeType(value="GLint") int n2) {
        GL32C.nglDrawElementsBaseVertex(n, intBuffer.remaining(), 5125, MemoryUtil.memAddress(intBuffer), n2);
    }

    public static native void nglDrawRangeElementsBaseVertex(int var0, int var1, int var2, int var3, int var4, long var5, int var7);

    public static void glDrawRangeElementsBaseVertex(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="void const *") long l, @NativeType(value="GLint") int n6) {
        GL32C.nglDrawRangeElementsBaseVertex(n, n2, n3, n4, n5, l, n6);
    }

    public static void glDrawRangeElementsBaseVertex(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="GLenum") int n4, @NativeType(value="void const *") ByteBuffer byteBuffer, @NativeType(value="GLint") int n5) {
        GL32C.nglDrawRangeElementsBaseVertex(n, n2, n3, byteBuffer.remaining() >> GLChecks.typeToByteShift(n4), n4, MemoryUtil.memAddress(byteBuffer), n5);
    }

    public static void glDrawRangeElementsBaseVertex(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="void const *") ByteBuffer byteBuffer, @NativeType(value="GLint") int n4) {
        GL32C.nglDrawRangeElementsBaseVertex(n, n2, n3, byteBuffer.remaining(), 5121, MemoryUtil.memAddress(byteBuffer), n4);
    }

    public static void glDrawRangeElementsBaseVertex(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="void const *") ShortBuffer shortBuffer, @NativeType(value="GLint") int n4) {
        GL32C.nglDrawRangeElementsBaseVertex(n, n2, n3, shortBuffer.remaining(), 5123, MemoryUtil.memAddress(shortBuffer), n4);
    }

    public static void glDrawRangeElementsBaseVertex(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="void const *") IntBuffer intBuffer, @NativeType(value="GLint") int n4) {
        GL32C.nglDrawRangeElementsBaseVertex(n, n2, n3, intBuffer.remaining(), 5125, MemoryUtil.memAddress(intBuffer), n4);
    }

    public static native void nglDrawElementsInstancedBaseVertex(int var0, int var1, int var2, long var3, int var5, int var6);

    public static void glDrawElementsInstancedBaseVertex(@NativeType(value="GLenum") int n, @NativeType(value="GLsizei") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="void const *") long l, @NativeType(value="GLsizei") int n4, @NativeType(value="GLint") int n5) {
        GL32C.nglDrawElementsInstancedBaseVertex(n, n2, n3, l, n4, n5);
    }

    public static void glDrawElementsInstancedBaseVertex(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="void const *") ByteBuffer byteBuffer, @NativeType(value="GLsizei") int n3, @NativeType(value="GLint") int n4) {
        GL32C.nglDrawElementsInstancedBaseVertex(n, byteBuffer.remaining() >> GLChecks.typeToByteShift(n2), n2, MemoryUtil.memAddress(byteBuffer), n3, n4);
    }

    public static void glDrawElementsInstancedBaseVertex(@NativeType(value="GLenum") int n, @NativeType(value="void const *") ByteBuffer byteBuffer, @NativeType(value="GLsizei") int n2, @NativeType(value="GLint") int n3) {
        GL32C.nglDrawElementsInstancedBaseVertex(n, byteBuffer.remaining(), 5121, MemoryUtil.memAddress(byteBuffer), n2, n3);
    }

    public static void glDrawElementsInstancedBaseVertex(@NativeType(value="GLenum") int n, @NativeType(value="void const *") ShortBuffer shortBuffer, @NativeType(value="GLsizei") int n2, @NativeType(value="GLint") int n3) {
        GL32C.nglDrawElementsInstancedBaseVertex(n, shortBuffer.remaining(), 5123, MemoryUtil.memAddress(shortBuffer), n2, n3);
    }

    public static void glDrawElementsInstancedBaseVertex(@NativeType(value="GLenum") int n, @NativeType(value="void const *") IntBuffer intBuffer, @NativeType(value="GLsizei") int n2, @NativeType(value="GLint") int n3) {
        GL32C.nglDrawElementsInstancedBaseVertex(n, intBuffer.remaining(), 5125, MemoryUtil.memAddress(intBuffer), n2, n3);
    }

    public static native void nglMultiDrawElementsBaseVertex(int var0, long var1, int var3, long var4, int var6, long var7);

    public static void glMultiDrawElementsBaseVertex(@NativeType(value="GLenum") int n, @NativeType(value="GLsizei const *") IntBuffer intBuffer, @NativeType(value="GLenum") int n2, @NativeType(value="void const **") PointerBuffer pointerBuffer, @NativeType(value="GLint *") IntBuffer intBuffer2) {
        if (Checks.CHECKS) {
            Checks.check(pointerBuffer, intBuffer.remaining());
            Checks.check((Buffer)intBuffer2, intBuffer.remaining());
        }
        GL32C.nglMultiDrawElementsBaseVertex(n, MemoryUtil.memAddress(intBuffer), n2, MemoryUtil.memAddress(pointerBuffer), intBuffer.remaining(), MemoryUtil.memAddress(intBuffer2));
    }

    public static native void glProvokingVertex(@NativeType(value="GLenum") int var0);

    public static native void glTexImage2DMultisample(@NativeType(value="GLenum") int var0, @NativeType(value="GLsizei") int var1, @NativeType(value="GLint") int var2, @NativeType(value="GLsizei") int var3, @NativeType(value="GLsizei") int var4, @NativeType(value="GLboolean") boolean var5);

    public static native void glTexImage3DMultisample(@NativeType(value="GLenum") int var0, @NativeType(value="GLsizei") int var1, @NativeType(value="GLint") int var2, @NativeType(value="GLsizei") int var3, @NativeType(value="GLsizei") int var4, @NativeType(value="GLsizei") int var5, @NativeType(value="GLboolean") boolean var6);

    public static native void nglGetMultisamplefv(int var0, int var1, long var2);

    public static void glGetMultisamplefv(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLfloat *") FloatBuffer floatBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)floatBuffer, 1);
        }
        GL32C.nglGetMultisamplefv(n, n2, MemoryUtil.memAddress(floatBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static float glGetMultisamplef(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            FloatBuffer floatBuffer = memoryStack.callocFloat(1);
            GL32C.nglGetMultisamplefv(n, n2, MemoryUtil.memAddress(floatBuffer));
            float f = floatBuffer.get(0);
            return f;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native void glSampleMaski(@NativeType(value="GLuint") int var0, @NativeType(value="GLbitfield") int var1);

    public static native void glFramebufferTexture(@NativeType(value="GLenum") int var0, @NativeType(value="GLenum") int var1, @NativeType(value="GLuint") int var2, @NativeType(value="GLint") int var3);

    @NativeType(value="GLsync")
    public static native long glFenceSync(@NativeType(value="GLenum") int var0, @NativeType(value="GLbitfield") int var1);

    public static native boolean nglIsSync(long var0);

    @NativeType(value="GLboolean")
    public static boolean glIsSync(@NativeType(value="GLsync") long l) {
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return GL32C.nglIsSync(l);
    }

    public static native void nglDeleteSync(long var0);

    public static void glDeleteSync(@NativeType(value="GLsync") long l) {
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        GL32C.nglDeleteSync(l);
    }

    public static native int nglClientWaitSync(long var0, int var2, long var3);

    @NativeType(value="GLenum")
    public static int glClientWaitSync(@NativeType(value="GLsync") long l, @NativeType(value="GLbitfield") int n, @NativeType(value="GLuint64") long l2) {
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        return GL32C.nglClientWaitSync(l, n, l2);
    }

    public static native void nglWaitSync(long var0, int var2, long var3);

    public static void glWaitSync(@NativeType(value="GLsync") long l, @NativeType(value="GLbitfield") int n, @NativeType(value="GLuint64") long l2) {
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        GL32C.nglWaitSync(l, n, l2);
    }

    public static native void nglGetInteger64v(int var0, long var1);

    public static void glGetInteger64v(@NativeType(value="GLenum") int n, @NativeType(value="GLint64 *") LongBuffer longBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)longBuffer, 1);
        }
        GL32C.nglGetInteger64v(n, MemoryUtil.memAddress(longBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static long glGetInteger64(@NativeType(value="GLenum") int n) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n2 = memoryStack.getPointer();
        try {
            LongBuffer longBuffer = memoryStack.callocLong(1);
            GL32C.nglGetInteger64v(n, MemoryUtil.memAddress(longBuffer));
            long l = longBuffer.get(0);
            return l;
        } finally {
            memoryStack.setPointer(n2);
        }
    }

    public static native void nglGetInteger64i_v(int var0, int var1, long var2);

    public static void glGetInteger64i_v(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLint64 *") LongBuffer longBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)longBuffer, 1);
        }
        GL32C.nglGetInteger64i_v(n, n2, MemoryUtil.memAddress(longBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static long glGetInteger64i(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            LongBuffer longBuffer = memoryStack.callocLong(1);
            GL32C.nglGetInteger64i_v(n, n2, MemoryUtil.memAddress(longBuffer));
            long l = longBuffer.get(0);
            return l;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native void nglGetSynciv(long var0, int var2, int var3, long var4, long var6);

    public static void glGetSynciv(@NativeType(value="GLsync") long l, @NativeType(value="GLenum") int n, @Nullable @NativeType(value="GLsizei *") IntBuffer intBuffer, @NativeType(value="GLint *") IntBuffer intBuffer2) {
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.checkSafe((Buffer)intBuffer, 1);
        }
        GL32C.nglGetSynciv(l, n, intBuffer2.remaining(), MemoryUtil.memAddressSafe(intBuffer), MemoryUtil.memAddress(intBuffer2));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glGetSynci(@NativeType(value="GLsync") long l, @NativeType(value="GLenum") int n, @Nullable @NativeType(value="GLsizei *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.checkSafe((Buffer)intBuffer, 1);
        }
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n2 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer2 = memoryStack.callocInt(1);
            GL32C.nglGetSynciv(l, n, 1, MemoryUtil.memAddressSafe(intBuffer), MemoryUtil.memAddress(intBuffer2));
            int n3 = intBuffer2.get(0);
            return n3;
        } finally {
            memoryStack.setPointer(n2);
        }
    }

    public static void glGetBufferParameteri64v(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint64 *") long[] lArray) {
        long l = GL.getICD().glGetBufferParameteri64v;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(lArray, 1);
        }
        JNI.callPV(n, n2, lArray, l);
    }

    public static void glMultiDrawElementsBaseVertex(@NativeType(value="GLenum") int n, @NativeType(value="GLsizei const *") int[] nArray, @NativeType(value="GLenum") int n2, @NativeType(value="void const **") PointerBuffer pointerBuffer, @NativeType(value="GLint *") int[] nArray2) {
        long l = GL.getICD().glMultiDrawElementsBaseVertex;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(pointerBuffer, nArray.length);
            Checks.check(nArray2, nArray.length);
        }
        JNI.callPPPV(n, nArray, n2, MemoryUtil.memAddress(pointerBuffer), nArray.length, nArray2, l);
    }

    public static void glGetMultisamplefv(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLfloat *") float[] fArray) {
        long l = GL.getICD().glGetMultisamplefv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(fArray, 1);
        }
        JNI.callPV(n, n2, fArray, l);
    }

    public static void glGetInteger64v(@NativeType(value="GLenum") int n, @NativeType(value="GLint64 *") long[] lArray) {
        long l = GL.getICD().glGetInteger64v;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(lArray, 1);
        }
        JNI.callPV(n, lArray, l);
    }

    public static void glGetInteger64i_v(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2, @NativeType(value="GLint64 *") long[] lArray) {
        long l = GL.getICD().glGetInteger64i_v;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(lArray, 1);
        }
        JNI.callPV(n, n2, lArray, l);
    }

    public static void glGetSynciv(@NativeType(value="GLsync") long l, @NativeType(value="GLenum") int n, @Nullable @NativeType(value="GLsizei *") int[] nArray, @NativeType(value="GLint *") int[] nArray2) {
        long l2 = GL.getICD().glGetSynciv;
        if (Checks.CHECKS) {
            Checks.check(l2);
            Checks.check(l);
            Checks.checkSafe(nArray, 1);
        }
        JNI.callPPPV(l, n, nArray2.length, nArray, nArray2, l2);
    }

    static {
        GL.initialize();
    }
}

