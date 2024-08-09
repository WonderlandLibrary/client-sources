/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.Buffer;
import java.nio.LongBuffer;
import java.util.Set;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class NVVertexAttribInteger64bit {
    public static final int GL_INT64_NV = 5134;
    public static final int GL_UNSIGNED_INT64_NV = 5135;

    protected NVVertexAttribInteger64bit() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities, Set<String> set) {
        return Checks.checkFunctions(gLCapabilities.glVertexAttribL1i64NV, gLCapabilities.glVertexAttribL2i64NV, gLCapabilities.glVertexAttribL3i64NV, gLCapabilities.glVertexAttribL4i64NV, gLCapabilities.glVertexAttribL1i64vNV, gLCapabilities.glVertexAttribL2i64vNV, gLCapabilities.glVertexAttribL3i64vNV, gLCapabilities.glVertexAttribL4i64vNV, gLCapabilities.glVertexAttribL1ui64NV, gLCapabilities.glVertexAttribL2ui64NV, gLCapabilities.glVertexAttribL3ui64NV, gLCapabilities.glVertexAttribL4ui64NV, gLCapabilities.glVertexAttribL1ui64vNV, gLCapabilities.glVertexAttribL2ui64vNV, gLCapabilities.glVertexAttribL3ui64vNV, gLCapabilities.glVertexAttribL4ui64vNV, gLCapabilities.glGetVertexAttribLi64vNV, gLCapabilities.glGetVertexAttribLui64vNV, set.contains("GL_NV_vertex_buffer_unified_memory") ? gLCapabilities.glVertexAttribLFormatNV : -1L);
    }

    public static native void glVertexAttribL1i64NV(@NativeType(value="GLuint") int var0, @NativeType(value="GLint64EXT") long var1);

    public static native void glVertexAttribL2i64NV(@NativeType(value="GLuint") int var0, @NativeType(value="GLint64EXT") long var1, @NativeType(value="GLint64EXT") long var3);

    public static native void glVertexAttribL3i64NV(@NativeType(value="GLuint") int var0, @NativeType(value="GLint64EXT") long var1, @NativeType(value="GLint64EXT") long var3, @NativeType(value="GLint64EXT") long var5);

    public static native void glVertexAttribL4i64NV(@NativeType(value="GLuint") int var0, @NativeType(value="GLint64EXT") long var1, @NativeType(value="GLint64EXT") long var3, @NativeType(value="GLint64EXT") long var5, @NativeType(value="GLint64EXT") long var7);

    public static native void nglVertexAttribL1i64vNV(int var0, long var1);

    public static void glVertexAttribL1i64vNV(@NativeType(value="GLuint") int n, @NativeType(value="GLint64EXT const *") LongBuffer longBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)longBuffer, 1);
        }
        NVVertexAttribInteger64bit.nglVertexAttribL1i64vNV(n, MemoryUtil.memAddress(longBuffer));
    }

    public static native void nglVertexAttribL2i64vNV(int var0, long var1);

    public static void glVertexAttribL2i64vNV(@NativeType(value="GLuint") int n, @NativeType(value="GLint64EXT const *") LongBuffer longBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)longBuffer, 2);
        }
        NVVertexAttribInteger64bit.nglVertexAttribL2i64vNV(n, MemoryUtil.memAddress(longBuffer));
    }

    public static native void nglVertexAttribL3i64vNV(int var0, long var1);

    public static void glVertexAttribL3i64vNV(@NativeType(value="GLuint") int n, @NativeType(value="GLint64EXT const *") LongBuffer longBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)longBuffer, 3);
        }
        NVVertexAttribInteger64bit.nglVertexAttribL3i64vNV(n, MemoryUtil.memAddress(longBuffer));
    }

    public static native void nglVertexAttribL4i64vNV(int var0, long var1);

    public static void glVertexAttribL4i64vNV(@NativeType(value="GLuint") int n, @NativeType(value="GLint64EXT const *") LongBuffer longBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)longBuffer, 4);
        }
        NVVertexAttribInteger64bit.nglVertexAttribL4i64vNV(n, MemoryUtil.memAddress(longBuffer));
    }

    public static native void glVertexAttribL1ui64NV(@NativeType(value="GLuint") int var0, @NativeType(value="GLuint64EXT") long var1);

    public static native void glVertexAttribL2ui64NV(@NativeType(value="GLuint") int var0, @NativeType(value="GLuint64EXT") long var1, @NativeType(value="GLuint64EXT") long var3);

    public static native void glVertexAttribL3ui64NV(@NativeType(value="GLuint") int var0, @NativeType(value="GLuint64EXT") long var1, @NativeType(value="GLuint64EXT") long var3, @NativeType(value="GLuint64EXT") long var5);

    public static native void glVertexAttribL4ui64NV(@NativeType(value="GLuint") int var0, @NativeType(value="GLuint64EXT") long var1, @NativeType(value="GLuint64EXT") long var3, @NativeType(value="GLuint64EXT") long var5, @NativeType(value="GLuint64EXT") long var7);

    public static native void nglVertexAttribL1ui64vNV(int var0, long var1);

    public static void glVertexAttribL1ui64vNV(@NativeType(value="GLuint") int n, @NativeType(value="GLuint64EXT const *") LongBuffer longBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)longBuffer, 1);
        }
        NVVertexAttribInteger64bit.nglVertexAttribL1ui64vNV(n, MemoryUtil.memAddress(longBuffer));
    }

    public static native void nglVertexAttribL2ui64vNV(int var0, long var1);

    public static void glVertexAttribL2ui64vNV(@NativeType(value="GLuint") int n, @NativeType(value="GLuint64EXT const *") LongBuffer longBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)longBuffer, 2);
        }
        NVVertexAttribInteger64bit.nglVertexAttribL2ui64vNV(n, MemoryUtil.memAddress(longBuffer));
    }

    public static native void nglVertexAttribL3ui64vNV(int var0, long var1);

    public static void glVertexAttribL3ui64vNV(@NativeType(value="GLuint") int n, @NativeType(value="GLuint64EXT const *") LongBuffer longBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)longBuffer, 3);
        }
        NVVertexAttribInteger64bit.nglVertexAttribL3ui64vNV(n, MemoryUtil.memAddress(longBuffer));
    }

    public static native void nglVertexAttribL4ui64vNV(int var0, long var1);

    public static void glVertexAttribL4ui64vNV(@NativeType(value="GLuint") int n, @NativeType(value="GLuint64EXT const *") LongBuffer longBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)longBuffer, 4);
        }
        NVVertexAttribInteger64bit.nglVertexAttribL4ui64vNV(n, MemoryUtil.memAddress(longBuffer));
    }

    public static native void nglGetVertexAttribLi64vNV(int var0, int var1, long var2);

    public static void glGetVertexAttribLi64vNV(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint64EXT *") LongBuffer longBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)longBuffer, 1);
        }
        NVVertexAttribInteger64bit.nglGetVertexAttribLi64vNV(n, n2, MemoryUtil.memAddress(longBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static long glGetVertexAttribLi64NV(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            LongBuffer longBuffer = memoryStack.callocLong(1);
            NVVertexAttribInteger64bit.nglGetVertexAttribLi64vNV(n, n2, MemoryUtil.memAddress(longBuffer));
            long l = longBuffer.get(0);
            return l;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native void nglGetVertexAttribLui64vNV(int var0, int var1, long var2);

    public static void glGetVertexAttribLui64vNV(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint64EXT *") LongBuffer longBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)longBuffer, 1);
        }
        NVVertexAttribInteger64bit.nglGetVertexAttribLui64vNV(n, n2, MemoryUtil.memAddress(longBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static long glGetVertexAttribLui64NV(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            LongBuffer longBuffer = memoryStack.callocLong(1);
            NVVertexAttribInteger64bit.nglGetVertexAttribLui64vNV(n, n2, MemoryUtil.memAddress(longBuffer));
            long l = longBuffer.get(0);
            return l;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native void glVertexAttribLFormatNV(@NativeType(value="GLuint") int var0, @NativeType(value="GLint") int var1, @NativeType(value="GLenum") int var2, @NativeType(value="GLsizei") int var3);

    public static void glVertexAttribL1i64vNV(@NativeType(value="GLuint") int n, @NativeType(value="GLint64EXT const *") long[] lArray) {
        long l = GL.getICD().glVertexAttribL1i64vNV;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(lArray, 1);
        }
        JNI.callPV(n, lArray, l);
    }

    public static void glVertexAttribL2i64vNV(@NativeType(value="GLuint") int n, @NativeType(value="GLint64EXT const *") long[] lArray) {
        long l = GL.getICD().glVertexAttribL2i64vNV;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(lArray, 2);
        }
        JNI.callPV(n, lArray, l);
    }

    public static void glVertexAttribL3i64vNV(@NativeType(value="GLuint") int n, @NativeType(value="GLint64EXT const *") long[] lArray) {
        long l = GL.getICD().glVertexAttribL3i64vNV;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(lArray, 3);
        }
        JNI.callPV(n, lArray, l);
    }

    public static void glVertexAttribL4i64vNV(@NativeType(value="GLuint") int n, @NativeType(value="GLint64EXT const *") long[] lArray) {
        long l = GL.getICD().glVertexAttribL4i64vNV;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(lArray, 4);
        }
        JNI.callPV(n, lArray, l);
    }

    public static void glVertexAttribL1ui64vNV(@NativeType(value="GLuint") int n, @NativeType(value="GLuint64EXT const *") long[] lArray) {
        long l = GL.getICD().glVertexAttribL1ui64vNV;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(lArray, 1);
        }
        JNI.callPV(n, lArray, l);
    }

    public static void glVertexAttribL2ui64vNV(@NativeType(value="GLuint") int n, @NativeType(value="GLuint64EXT const *") long[] lArray) {
        long l = GL.getICD().glVertexAttribL2ui64vNV;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(lArray, 2);
        }
        JNI.callPV(n, lArray, l);
    }

    public static void glVertexAttribL3ui64vNV(@NativeType(value="GLuint") int n, @NativeType(value="GLuint64EXT const *") long[] lArray) {
        long l = GL.getICD().glVertexAttribL3ui64vNV;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(lArray, 3);
        }
        JNI.callPV(n, lArray, l);
    }

    public static void glVertexAttribL4ui64vNV(@NativeType(value="GLuint") int n, @NativeType(value="GLuint64EXT const *") long[] lArray) {
        long l = GL.getICD().glVertexAttribL4ui64vNV;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(lArray, 4);
        }
        JNI.callPV(n, lArray, l);
    }

    public static void glGetVertexAttribLi64vNV(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint64EXT *") long[] lArray) {
        long l = GL.getICD().glGetVertexAttribLi64vNV;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(lArray, 1);
        }
        JNI.callPV(n, n2, lArray, l);
    }

    public static void glGetVertexAttribLui64vNV(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint64EXT *") long[] lArray) {
        long l = GL.getICD().glGetVertexAttribLui64vNV;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(lArray, 1);
        }
        JNI.callPV(n, n2, lArray, l);
    }

    static {
        GL.initialize();
    }
}

