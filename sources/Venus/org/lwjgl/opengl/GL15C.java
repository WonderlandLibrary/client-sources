/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import javax.annotation.Nullable;
import org.lwjgl.PointerBuffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL14C;
import org.lwjgl.system.APIUtil;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class GL15C
extends GL14C {
    public static final int GL_SRC1_ALPHA = 34185;
    public static final int GL_ARRAY_BUFFER = 34962;
    public static final int GL_ELEMENT_ARRAY_BUFFER = 34963;
    public static final int GL_ARRAY_BUFFER_BINDING = 34964;
    public static final int GL_ELEMENT_ARRAY_BUFFER_BINDING = 34965;
    public static final int GL_VERTEX_ATTRIB_ARRAY_BUFFER_BINDING = 34975;
    public static final int GL_STREAM_DRAW = 35040;
    public static final int GL_STREAM_READ = 35041;
    public static final int GL_STREAM_COPY = 35042;
    public static final int GL_STATIC_DRAW = 35044;
    public static final int GL_STATIC_READ = 35045;
    public static final int GL_STATIC_COPY = 35046;
    public static final int GL_DYNAMIC_DRAW = 35048;
    public static final int GL_DYNAMIC_READ = 35049;
    public static final int GL_DYNAMIC_COPY = 35050;
    public static final int GL_READ_ONLY = 35000;
    public static final int GL_WRITE_ONLY = 35001;
    public static final int GL_READ_WRITE = 35002;
    public static final int GL_BUFFER_SIZE = 34660;
    public static final int GL_BUFFER_USAGE = 34661;
    public static final int GL_BUFFER_ACCESS = 35003;
    public static final int GL_BUFFER_MAPPED = 35004;
    public static final int GL_BUFFER_MAP_POINTER = 35005;
    public static final int GL_SAMPLES_PASSED = 35092;
    public static final int GL_QUERY_COUNTER_BITS = 34916;
    public static final int GL_CURRENT_QUERY = 34917;
    public static final int GL_QUERY_RESULT = 34918;
    public static final int GL_QUERY_RESULT_AVAILABLE = 34919;

    protected GL15C() {
        throw new UnsupportedOperationException();
    }

    public static native void glBindBuffer(@NativeType(value="GLenum") int var0, @NativeType(value="GLuint") int var1);

    public static native void nglDeleteBuffers(int var0, long var1);

    public static void glDeleteBuffers(@NativeType(value="GLuint const *") IntBuffer intBuffer) {
        GL15C.nglDeleteBuffers(intBuffer.remaining(), MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void glDeleteBuffers(@NativeType(value="GLuint const *") int n) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n2 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.ints(n);
            GL15C.nglDeleteBuffers(1, MemoryUtil.memAddress(intBuffer));
        } finally {
            memoryStack.setPointer(n2);
        }
    }

    public static native void nglGenBuffers(int var0, long var1);

    public static void glGenBuffers(@NativeType(value="GLuint *") IntBuffer intBuffer) {
        GL15C.nglGenBuffers(intBuffer.remaining(), MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glGenBuffers() {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            GL15C.nglGenBuffers(1, MemoryUtil.memAddress(intBuffer));
            int n2 = intBuffer.get(0);
            return n2;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    @NativeType(value="GLboolean")
    public static native boolean glIsBuffer(@NativeType(value="GLuint") int var0);

    public static native void nglBufferData(int var0, long var1, long var3, int var5);

    public static void glBufferData(@NativeType(value="GLenum") int n, @NativeType(value="GLsizeiptr") long l, @NativeType(value="GLenum") int n2) {
        GL15C.nglBufferData(n, l, 0L, n2);
    }

    public static void glBufferData(@NativeType(value="GLenum") int n, @NativeType(value="void const *") ByteBuffer byteBuffer, @NativeType(value="GLenum") int n2) {
        GL15C.nglBufferData(n, byteBuffer.remaining(), MemoryUtil.memAddress(byteBuffer), n2);
    }

    public static void glBufferData(@NativeType(value="GLenum") int n, @NativeType(value="void const *") ShortBuffer shortBuffer, @NativeType(value="GLenum") int n2) {
        GL15C.nglBufferData(n, Integer.toUnsignedLong(shortBuffer.remaining()) << 1, MemoryUtil.memAddress(shortBuffer), n2);
    }

    public static void glBufferData(@NativeType(value="GLenum") int n, @NativeType(value="void const *") IntBuffer intBuffer, @NativeType(value="GLenum") int n2) {
        GL15C.nglBufferData(n, Integer.toUnsignedLong(intBuffer.remaining()) << 2, MemoryUtil.memAddress(intBuffer), n2);
    }

    public static void glBufferData(@NativeType(value="GLenum") int n, @NativeType(value="void const *") LongBuffer longBuffer, @NativeType(value="GLenum") int n2) {
        GL15C.nglBufferData(n, Integer.toUnsignedLong(longBuffer.remaining()) << 3, MemoryUtil.memAddress(longBuffer), n2);
    }

    public static void glBufferData(@NativeType(value="GLenum") int n, @NativeType(value="void const *") FloatBuffer floatBuffer, @NativeType(value="GLenum") int n2) {
        GL15C.nglBufferData(n, Integer.toUnsignedLong(floatBuffer.remaining()) << 2, MemoryUtil.memAddress(floatBuffer), n2);
    }

    public static void glBufferData(@NativeType(value="GLenum") int n, @NativeType(value="void const *") DoubleBuffer doubleBuffer, @NativeType(value="GLenum") int n2) {
        GL15C.nglBufferData(n, Integer.toUnsignedLong(doubleBuffer.remaining()) << 3, MemoryUtil.memAddress(doubleBuffer), n2);
    }

    public static native void nglBufferSubData(int var0, long var1, long var3, long var5);

    public static void glBufferSubData(@NativeType(value="GLenum") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void const *") ByteBuffer byteBuffer) {
        GL15C.nglBufferSubData(n, l, byteBuffer.remaining(), MemoryUtil.memAddress(byteBuffer));
    }

    public static void glBufferSubData(@NativeType(value="GLenum") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void const *") ShortBuffer shortBuffer) {
        GL15C.nglBufferSubData(n, l, Integer.toUnsignedLong(shortBuffer.remaining()) << 1, MemoryUtil.memAddress(shortBuffer));
    }

    public static void glBufferSubData(@NativeType(value="GLenum") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void const *") IntBuffer intBuffer) {
        GL15C.nglBufferSubData(n, l, Integer.toUnsignedLong(intBuffer.remaining()) << 2, MemoryUtil.memAddress(intBuffer));
    }

    public static void glBufferSubData(@NativeType(value="GLenum") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void const *") LongBuffer longBuffer) {
        GL15C.nglBufferSubData(n, l, Integer.toUnsignedLong(longBuffer.remaining()) << 3, MemoryUtil.memAddress(longBuffer));
    }

    public static void glBufferSubData(@NativeType(value="GLenum") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void const *") FloatBuffer floatBuffer) {
        GL15C.nglBufferSubData(n, l, Integer.toUnsignedLong(floatBuffer.remaining()) << 2, MemoryUtil.memAddress(floatBuffer));
    }

    public static void glBufferSubData(@NativeType(value="GLenum") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void const *") DoubleBuffer doubleBuffer) {
        GL15C.nglBufferSubData(n, l, Integer.toUnsignedLong(doubleBuffer.remaining()) << 3, MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void nglGetBufferSubData(int var0, long var1, long var3, long var5);

    public static void glGetBufferSubData(@NativeType(value="GLenum") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void *") ByteBuffer byteBuffer) {
        GL15C.nglGetBufferSubData(n, l, byteBuffer.remaining(), MemoryUtil.memAddress(byteBuffer));
    }

    public static void glGetBufferSubData(@NativeType(value="GLenum") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void *") ShortBuffer shortBuffer) {
        GL15C.nglGetBufferSubData(n, l, Integer.toUnsignedLong(shortBuffer.remaining()) << 1, MemoryUtil.memAddress(shortBuffer));
    }

    public static void glGetBufferSubData(@NativeType(value="GLenum") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void *") IntBuffer intBuffer) {
        GL15C.nglGetBufferSubData(n, l, Integer.toUnsignedLong(intBuffer.remaining()) << 2, MemoryUtil.memAddress(intBuffer));
    }

    public static void glGetBufferSubData(@NativeType(value="GLenum") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void *") LongBuffer longBuffer) {
        GL15C.nglGetBufferSubData(n, l, Integer.toUnsignedLong(longBuffer.remaining()) << 3, MemoryUtil.memAddress(longBuffer));
    }

    public static void glGetBufferSubData(@NativeType(value="GLenum") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void *") FloatBuffer floatBuffer) {
        GL15C.nglGetBufferSubData(n, l, Integer.toUnsignedLong(floatBuffer.remaining()) << 2, MemoryUtil.memAddress(floatBuffer));
    }

    public static void glGetBufferSubData(@NativeType(value="GLenum") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void *") DoubleBuffer doubleBuffer) {
        GL15C.nglGetBufferSubData(n, l, Integer.toUnsignedLong(doubleBuffer.remaining()) << 3, MemoryUtil.memAddress(doubleBuffer));
    }

    public static native long nglMapBuffer(int var0, int var1);

    @Nullable
    @NativeType(value="void *")
    public static ByteBuffer glMapBuffer(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2) {
        long l = GL15C.nglMapBuffer(n, n2);
        return MemoryUtil.memByteBufferSafe(l, GL15C.glGetBufferParameteri(n, 34660));
    }

    @Nullable
    @NativeType(value="void *")
    public static ByteBuffer glMapBuffer(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @Nullable ByteBuffer byteBuffer) {
        long l = GL15C.nglMapBuffer(n, n2);
        int n3 = GL15C.glGetBufferParameteri(n, 34660);
        return APIUtil.apiGetMappedBuffer(byteBuffer, l, n3);
    }

    @Nullable
    @NativeType(value="void *")
    public static ByteBuffer glMapBuffer(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, long l, @Nullable ByteBuffer byteBuffer) {
        long l2 = GL15C.nglMapBuffer(n, n2);
        return APIUtil.apiGetMappedBuffer(byteBuffer, l2, (int)l);
    }

    @NativeType(value="GLboolean")
    public static native boolean glUnmapBuffer(@NativeType(value="GLenum") int var0);

    public static native void nglGetBufferParameteriv(int var0, int var1, long var2);

    public static void glGetBufferParameteriv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        GL15C.nglGetBufferParameteriv(n, n2, MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glGetBufferParameteri(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            GL15C.nglGetBufferParameteriv(n, n2, MemoryUtil.memAddress(intBuffer));
            int n4 = intBuffer.get(0);
            return n4;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native void nglGetBufferPointerv(int var0, int var1, long var2);

    public static void glGetBufferPointerv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="void **") PointerBuffer pointerBuffer) {
        if (Checks.CHECKS) {
            Checks.check(pointerBuffer, 1);
        }
        GL15C.nglGetBufferPointerv(n, n2, MemoryUtil.memAddress(pointerBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static long glGetBufferPointer(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            PointerBuffer pointerBuffer = memoryStack.callocPointer(1);
            GL15C.nglGetBufferPointerv(n, n2, MemoryUtil.memAddress(pointerBuffer));
            long l = pointerBuffer.get(0);
            return l;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native void nglGenQueries(int var0, long var1);

    public static void glGenQueries(@NativeType(value="GLuint *") IntBuffer intBuffer) {
        GL15C.nglGenQueries(intBuffer.remaining(), MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glGenQueries() {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            GL15C.nglGenQueries(1, MemoryUtil.memAddress(intBuffer));
            int n2 = intBuffer.get(0);
            return n2;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static native void nglDeleteQueries(int var0, long var1);

    public static void glDeleteQueries(@NativeType(value="GLuint const *") IntBuffer intBuffer) {
        GL15C.nglDeleteQueries(intBuffer.remaining(), MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void glDeleteQueries(@NativeType(value="GLuint const *") int n) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n2 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.ints(n);
            GL15C.nglDeleteQueries(1, MemoryUtil.memAddress(intBuffer));
        } finally {
            memoryStack.setPointer(n2);
        }
    }

    @NativeType(value="GLboolean")
    public static native boolean glIsQuery(@NativeType(value="GLuint") int var0);

    public static native void glBeginQuery(@NativeType(value="GLenum") int var0, @NativeType(value="GLuint") int var1);

    public static native void glEndQuery(@NativeType(value="GLenum") int var0);

    public static native void nglGetQueryiv(int var0, int var1, long var2);

    public static void glGetQueryiv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        GL15C.nglGetQueryiv(n, n2, MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glGetQueryi(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            GL15C.nglGetQueryiv(n, n2, MemoryUtil.memAddress(intBuffer));
            int n4 = intBuffer.get(0);
            return n4;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native void nglGetQueryObjectiv(int var0, int var1, long var2);

    public static void glGetQueryObjectiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        GL15C.nglGetQueryObjectiv(n, n2, MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glGetQueryObjecti(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            GL15C.nglGetQueryObjectiv(n, n2, MemoryUtil.memAddress(intBuffer));
            int n4 = intBuffer.get(0);
            return n4;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native void nglGetQueryObjectuiv(int var0, int var1, long var2);

    public static void glGetQueryObjectuiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        GL15C.nglGetQueryObjectuiv(n, n2, MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glGetQueryObjectui(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            GL15C.nglGetQueryObjectuiv(n, n2, MemoryUtil.memAddress(intBuffer));
            int n4 = intBuffer.get(0);
            return n4;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static void glDeleteBuffers(@NativeType(value="GLuint const *") int[] nArray) {
        long l = GL.getICD().glDeleteBuffers;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(nArray.length, nArray, l);
    }

    public static void glGenBuffers(@NativeType(value="GLuint *") int[] nArray) {
        long l = GL.getICD().glGenBuffers;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(nArray.length, nArray, l);
    }

    public static void glBufferData(@NativeType(value="GLenum") int n, @NativeType(value="void const *") short[] sArray, @NativeType(value="GLenum") int n2) {
        long l = GL.getICD().glBufferData;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPPV(n, Integer.toUnsignedLong(sArray.length) << 1, sArray, n2, l);
    }

    public static void glBufferData(@NativeType(value="GLenum") int n, @NativeType(value="void const *") int[] nArray, @NativeType(value="GLenum") int n2) {
        long l = GL.getICD().glBufferData;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPPV(n, Integer.toUnsignedLong(nArray.length) << 2, nArray, n2, l);
    }

    public static void glBufferData(@NativeType(value="GLenum") int n, @NativeType(value="void const *") long[] lArray, @NativeType(value="GLenum") int n2) {
        long l = GL.getICD().glBufferData;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPPV(n, Integer.toUnsignedLong(lArray.length) << 3, lArray, n2, l);
    }

    public static void glBufferData(@NativeType(value="GLenum") int n, @NativeType(value="void const *") float[] fArray, @NativeType(value="GLenum") int n2) {
        long l = GL.getICD().glBufferData;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPPV(n, Integer.toUnsignedLong(fArray.length) << 2, fArray, n2, l);
    }

    public static void glBufferData(@NativeType(value="GLenum") int n, @NativeType(value="void const *") double[] dArray, @NativeType(value="GLenum") int n2) {
        long l = GL.getICD().glBufferData;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPPV(n, Integer.toUnsignedLong(dArray.length) << 3, dArray, n2, l);
    }

    public static void glBufferSubData(@NativeType(value="GLenum") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void const *") short[] sArray) {
        long l2 = GL.getICD().glBufferSubData;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        JNI.callPPPV(n, l, Integer.toUnsignedLong(sArray.length) << 1, sArray, l2);
    }

    public static void glBufferSubData(@NativeType(value="GLenum") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void const *") int[] nArray) {
        long l2 = GL.getICD().glBufferSubData;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        JNI.callPPPV(n, l, Integer.toUnsignedLong(nArray.length) << 2, nArray, l2);
    }

    public static void glBufferSubData(@NativeType(value="GLenum") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void const *") long[] lArray) {
        long l2 = GL.getICD().glBufferSubData;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        JNI.callPPPV(n, l, Integer.toUnsignedLong(lArray.length) << 3, lArray, l2);
    }

    public static void glBufferSubData(@NativeType(value="GLenum") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void const *") float[] fArray) {
        long l2 = GL.getICD().glBufferSubData;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        JNI.callPPPV(n, l, Integer.toUnsignedLong(fArray.length) << 2, fArray, l2);
    }

    public static void glBufferSubData(@NativeType(value="GLenum") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void const *") double[] dArray) {
        long l2 = GL.getICD().glBufferSubData;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        JNI.callPPPV(n, l, Integer.toUnsignedLong(dArray.length) << 3, dArray, l2);
    }

    public static void glGetBufferSubData(@NativeType(value="GLenum") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void *") short[] sArray) {
        long l2 = GL.getICD().glGetBufferSubData;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        JNI.callPPPV(n, l, Integer.toUnsignedLong(sArray.length) << 1, sArray, l2);
    }

    public static void glGetBufferSubData(@NativeType(value="GLenum") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void *") int[] nArray) {
        long l2 = GL.getICD().glGetBufferSubData;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        JNI.callPPPV(n, l, Integer.toUnsignedLong(nArray.length) << 2, nArray, l2);
    }

    public static void glGetBufferSubData(@NativeType(value="GLenum") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void *") long[] lArray) {
        long l2 = GL.getICD().glGetBufferSubData;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        JNI.callPPPV(n, l, Integer.toUnsignedLong(lArray.length) << 3, lArray, l2);
    }

    public static void glGetBufferSubData(@NativeType(value="GLenum") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void *") float[] fArray) {
        long l2 = GL.getICD().glGetBufferSubData;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        JNI.callPPPV(n, l, Integer.toUnsignedLong(fArray.length) << 2, fArray, l2);
    }

    public static void glGetBufferSubData(@NativeType(value="GLenum") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void *") double[] dArray) {
        long l2 = GL.getICD().glGetBufferSubData;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        JNI.callPPPV(n, l, Integer.toUnsignedLong(dArray.length) << 3, dArray, l2);
    }

    public static void glGetBufferParameteriv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") int[] nArray) {
        long l = GL.getICD().glGetBufferParameteriv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.callPV(n, n2, nArray, l);
    }

    public static void glGenQueries(@NativeType(value="GLuint *") int[] nArray) {
        long l = GL.getICD().glGenQueries;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(nArray.length, nArray, l);
    }

    public static void glDeleteQueries(@NativeType(value="GLuint const *") int[] nArray) {
        long l = GL.getICD().glDeleteQueries;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(nArray.length, nArray, l);
    }

    public static void glGetQueryiv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") int[] nArray) {
        long l = GL.getICD().glGetQueryiv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.callPV(n, n2, nArray, l);
    }

    public static void glGetQueryObjectiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") int[] nArray) {
        long l = GL.getICD().glGetQueryObjectiv;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.callPV(n, n2, nArray, l);
    }

    public static void glGetQueryObjectuiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint *") int[] nArray) {
        long l = GL.getICD().glGetQueryObjectuiv;
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

