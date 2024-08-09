/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import javax.annotation.Nullable;
import org.lwjgl.PointerBuffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.APIUtil;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class ARBVertexBufferObject {
    public static final int GL_ARRAY_BUFFER_ARB = 34962;
    public static final int GL_ELEMENT_ARRAY_BUFFER_ARB = 34963;
    public static final int GL_ARRAY_BUFFER_BINDING_ARB = 34964;
    public static final int GL_ELEMENT_ARRAY_BUFFER_BINDING_ARB = 34965;
    public static final int GL_VERTEX_ARRAY_BUFFER_BINDING_ARB = 34966;
    public static final int GL_NORMAL_ARRAY_BUFFER_BINDING_ARB = 34967;
    public static final int GL_COLOR_ARRAY_BUFFER_BINDING_ARB = 34968;
    public static final int GL_INDEX_ARRAY_BUFFER_BINDING_ARB = 34969;
    public static final int GL_TEXTURE_COORD_ARRAY_BUFFER_BINDING_ARB = 34970;
    public static final int GL_EDGE_FLAG_ARRAY_BUFFER_BINDING_ARB = 34971;
    public static final int GL_SECONDARY_COLOR_ARRAY_BUFFER_BINDING_ARB = 34972;
    public static final int GL_FOG_COORDINATE_ARRAY_BUFFER_BINDING_ARB = 34973;
    public static final int GL_WEIGHT_ARRAY_BUFFER_BINDING_ARB = 34974;
    public static final int GL_VERTEX_ATTRIB_ARRAY_BUFFER_BINDING_ARB = 34975;
    public static final int GL_STREAM_DRAW_ARB = 35040;
    public static final int GL_STREAM_READ_ARB = 35041;
    public static final int GL_STREAM_COPY_ARB = 35042;
    public static final int GL_STATIC_DRAW_ARB = 35044;
    public static final int GL_STATIC_READ_ARB = 35045;
    public static final int GL_STATIC_COPY_ARB = 35046;
    public static final int GL_DYNAMIC_DRAW_ARB = 35048;
    public static final int GL_DYNAMIC_READ_ARB = 35049;
    public static final int GL_DYNAMIC_COPY_ARB = 35050;
    public static final int GL_READ_ONLY_ARB = 35000;
    public static final int GL_WRITE_ONLY_ARB = 35001;
    public static final int GL_READ_WRITE_ARB = 35002;
    public static final int GL_BUFFER_SIZE_ARB = 34660;
    public static final int GL_BUFFER_USAGE_ARB = 34661;
    public static final int GL_BUFFER_ACCESS_ARB = 35003;
    public static final int GL_BUFFER_MAPPED_ARB = 35004;
    public static final int GL_BUFFER_MAP_POINTER_ARB = 35005;

    protected ARBVertexBufferObject() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glBindBufferARB, gLCapabilities.glDeleteBuffersARB, gLCapabilities.glGenBuffersARB, gLCapabilities.glIsBufferARB, gLCapabilities.glBufferDataARB, gLCapabilities.glBufferSubDataARB, gLCapabilities.glGetBufferSubDataARB, gLCapabilities.glMapBufferARB, gLCapabilities.glUnmapBufferARB, gLCapabilities.glGetBufferParameterivARB, gLCapabilities.glGetBufferPointervARB);
    }

    public static native void glBindBufferARB(@NativeType(value="GLenum") int var0, @NativeType(value="GLuint") int var1);

    public static native void nglDeleteBuffersARB(int var0, long var1);

    public static void glDeleteBuffersARB(@NativeType(value="GLuint const *") IntBuffer intBuffer) {
        ARBVertexBufferObject.nglDeleteBuffersARB(intBuffer.remaining(), MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void glDeleteBuffersARB(@NativeType(value="GLuint const *") int n) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n2 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.ints(n);
            ARBVertexBufferObject.nglDeleteBuffersARB(1, MemoryUtil.memAddress(intBuffer));
        } finally {
            memoryStack.setPointer(n2);
        }
    }

    public static native void nglGenBuffersARB(int var0, long var1);

    public static void glGenBuffersARB(@NativeType(value="GLuint *") IntBuffer intBuffer) {
        ARBVertexBufferObject.nglGenBuffersARB(intBuffer.remaining(), MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glGenBuffersARB() {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            ARBVertexBufferObject.nglGenBuffersARB(1, MemoryUtil.memAddress(intBuffer));
            int n2 = intBuffer.get(0);
            return n2;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    @NativeType(value="GLboolean")
    public static native boolean glIsBufferARB(@NativeType(value="GLuint") int var0);

    public static native void nglBufferDataARB(int var0, long var1, long var3, int var5);

    public static void glBufferDataARB(@NativeType(value="GLenum") int n, @NativeType(value="GLsizeiptrARB") long l, @NativeType(value="GLenum") int n2) {
        ARBVertexBufferObject.nglBufferDataARB(n, l, 0L, n2);
    }

    public static void glBufferDataARB(@NativeType(value="GLenum") int n, @NativeType(value="void const *") ByteBuffer byteBuffer, @NativeType(value="GLenum") int n2) {
        ARBVertexBufferObject.nglBufferDataARB(n, byteBuffer.remaining(), MemoryUtil.memAddress(byteBuffer), n2);
    }

    public static void glBufferDataARB(@NativeType(value="GLenum") int n, @NativeType(value="void const *") ShortBuffer shortBuffer, @NativeType(value="GLenum") int n2) {
        ARBVertexBufferObject.nglBufferDataARB(n, Integer.toUnsignedLong(shortBuffer.remaining()) << 1, MemoryUtil.memAddress(shortBuffer), n2);
    }

    public static void glBufferDataARB(@NativeType(value="GLenum") int n, @NativeType(value="void const *") IntBuffer intBuffer, @NativeType(value="GLenum") int n2) {
        ARBVertexBufferObject.nglBufferDataARB(n, Integer.toUnsignedLong(intBuffer.remaining()) << 2, MemoryUtil.memAddress(intBuffer), n2);
    }

    public static void glBufferDataARB(@NativeType(value="GLenum") int n, @NativeType(value="void const *") FloatBuffer floatBuffer, @NativeType(value="GLenum") int n2) {
        ARBVertexBufferObject.nglBufferDataARB(n, Integer.toUnsignedLong(floatBuffer.remaining()) << 2, MemoryUtil.memAddress(floatBuffer), n2);
    }

    public static void glBufferDataARB(@NativeType(value="GLenum") int n, @NativeType(value="void const *") DoubleBuffer doubleBuffer, @NativeType(value="GLenum") int n2) {
        ARBVertexBufferObject.nglBufferDataARB(n, Integer.toUnsignedLong(doubleBuffer.remaining()) << 3, MemoryUtil.memAddress(doubleBuffer), n2);
    }

    public static native void nglBufferSubDataARB(int var0, long var1, long var3, long var5);

    public static void glBufferSubDataARB(@NativeType(value="GLenum") int n, @NativeType(value="GLintptrARB") long l, @NativeType(value="void const *") ByteBuffer byteBuffer) {
        ARBVertexBufferObject.nglBufferSubDataARB(n, l, byteBuffer.remaining(), MemoryUtil.memAddress(byteBuffer));
    }

    public static void glBufferSubDataARB(@NativeType(value="GLenum") int n, @NativeType(value="GLintptrARB") long l, @NativeType(value="void const *") ShortBuffer shortBuffer) {
        ARBVertexBufferObject.nglBufferSubDataARB(n, l, Integer.toUnsignedLong(shortBuffer.remaining()) << 1, MemoryUtil.memAddress(shortBuffer));
    }

    public static void glBufferSubDataARB(@NativeType(value="GLenum") int n, @NativeType(value="GLintptrARB") long l, @NativeType(value="void const *") IntBuffer intBuffer) {
        ARBVertexBufferObject.nglBufferSubDataARB(n, l, Integer.toUnsignedLong(intBuffer.remaining()) << 2, MemoryUtil.memAddress(intBuffer));
    }

    public static void glBufferSubDataARB(@NativeType(value="GLenum") int n, @NativeType(value="GLintptrARB") long l, @NativeType(value="void const *") FloatBuffer floatBuffer) {
        ARBVertexBufferObject.nglBufferSubDataARB(n, l, Integer.toUnsignedLong(floatBuffer.remaining()) << 2, MemoryUtil.memAddress(floatBuffer));
    }

    public static void glBufferSubDataARB(@NativeType(value="GLenum") int n, @NativeType(value="GLintptrARB") long l, @NativeType(value="void const *") DoubleBuffer doubleBuffer) {
        ARBVertexBufferObject.nglBufferSubDataARB(n, l, Integer.toUnsignedLong(doubleBuffer.remaining()) << 3, MemoryUtil.memAddress(doubleBuffer));
    }

    public static native void nglGetBufferSubDataARB(int var0, long var1, long var3, long var5);

    public static void glGetBufferSubDataARB(@NativeType(value="GLenum") int n, @NativeType(value="GLintptrARB") long l, @NativeType(value="void *") ByteBuffer byteBuffer) {
        ARBVertexBufferObject.nglGetBufferSubDataARB(n, l, byteBuffer.remaining(), MemoryUtil.memAddress(byteBuffer));
    }

    public static void glGetBufferSubDataARB(@NativeType(value="GLenum") int n, @NativeType(value="GLintptrARB") long l, @NativeType(value="void *") ShortBuffer shortBuffer) {
        ARBVertexBufferObject.nglGetBufferSubDataARB(n, l, Integer.toUnsignedLong(shortBuffer.remaining()) << 1, MemoryUtil.memAddress(shortBuffer));
    }

    public static void glGetBufferSubDataARB(@NativeType(value="GLenum") int n, @NativeType(value="GLintptrARB") long l, @NativeType(value="void *") IntBuffer intBuffer) {
        ARBVertexBufferObject.nglGetBufferSubDataARB(n, l, Integer.toUnsignedLong(intBuffer.remaining()) << 2, MemoryUtil.memAddress(intBuffer));
    }

    public static void glGetBufferSubDataARB(@NativeType(value="GLenum") int n, @NativeType(value="GLintptrARB") long l, @NativeType(value="void *") FloatBuffer floatBuffer) {
        ARBVertexBufferObject.nglGetBufferSubDataARB(n, l, Integer.toUnsignedLong(floatBuffer.remaining()) << 2, MemoryUtil.memAddress(floatBuffer));
    }

    public static void glGetBufferSubDataARB(@NativeType(value="GLenum") int n, @NativeType(value="GLintptrARB") long l, @NativeType(value="void *") DoubleBuffer doubleBuffer) {
        ARBVertexBufferObject.nglGetBufferSubDataARB(n, l, Integer.toUnsignedLong(doubleBuffer.remaining()) << 3, MemoryUtil.memAddress(doubleBuffer));
    }

    public static native long nglMapBufferARB(int var0, int var1);

    @Nullable
    @NativeType(value="void *")
    public static ByteBuffer glMapBufferARB(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2) {
        long l = ARBVertexBufferObject.nglMapBufferARB(n, n2);
        return MemoryUtil.memByteBufferSafe(l, ARBVertexBufferObject.glGetBufferParameteriARB(n, 34660));
    }

    @Nullable
    @NativeType(value="void *")
    public static ByteBuffer glMapBufferARB(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @Nullable ByteBuffer byteBuffer) {
        long l = ARBVertexBufferObject.nglMapBufferARB(n, n2);
        int n3 = ARBVertexBufferObject.glGetBufferParameteriARB(n, 34660);
        return APIUtil.apiGetMappedBuffer(byteBuffer, l, n3);
    }

    @Nullable
    @NativeType(value="void *")
    public static ByteBuffer glMapBufferARB(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, long l, @Nullable ByteBuffer byteBuffer) {
        long l2 = ARBVertexBufferObject.nglMapBufferARB(n, n2);
        return APIUtil.apiGetMappedBuffer(byteBuffer, l2, (int)l);
    }

    @NativeType(value="GLboolean")
    public static native boolean glUnmapBufferARB(@NativeType(value="GLenum") int var0);

    public static native void nglGetBufferParameterivARB(int var0, int var1, long var2);

    public static void glGetBufferParameterivARB(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        ARBVertexBufferObject.nglGetBufferParameterivARB(n, n2, MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glGetBufferParameteriARB(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            ARBVertexBufferObject.nglGetBufferParameterivARB(n, n2, MemoryUtil.memAddress(intBuffer));
            int n4 = intBuffer.get(0);
            return n4;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static native void nglGetBufferPointervARB(int var0, int var1, long var2);

    public static void glGetBufferPointervARB(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="void **") PointerBuffer pointerBuffer) {
        if (Checks.CHECKS) {
            Checks.check(pointerBuffer, 1);
        }
        ARBVertexBufferObject.nglGetBufferPointervARB(n, n2, MemoryUtil.memAddress(pointerBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static long glGetBufferPointerARB(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            PointerBuffer pointerBuffer = memoryStack.callocPointer(1);
            ARBVertexBufferObject.nglGetBufferPointervARB(n, n2, MemoryUtil.memAddress(pointerBuffer));
            long l = pointerBuffer.get(0);
            return l;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    public static void glDeleteBuffersARB(@NativeType(value="GLuint const *") int[] nArray) {
        long l = GL.getICD().glDeleteBuffersARB;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(nArray.length, nArray, l);
    }

    public static void glGenBuffersARB(@NativeType(value="GLuint *") int[] nArray) {
        long l = GL.getICD().glGenBuffersARB;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(nArray.length, nArray, l);
    }

    public static void glBufferDataARB(@NativeType(value="GLenum") int n, @NativeType(value="void const *") short[] sArray, @NativeType(value="GLenum") int n2) {
        long l = GL.getICD().glBufferDataARB;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPPV(n, Integer.toUnsignedLong(sArray.length) << 1, sArray, n2, l);
    }

    public static void glBufferDataARB(@NativeType(value="GLenum") int n, @NativeType(value="void const *") int[] nArray, @NativeType(value="GLenum") int n2) {
        long l = GL.getICD().glBufferDataARB;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPPV(n, Integer.toUnsignedLong(nArray.length) << 2, nArray, n2, l);
    }

    public static void glBufferDataARB(@NativeType(value="GLenum") int n, @NativeType(value="void const *") float[] fArray, @NativeType(value="GLenum") int n2) {
        long l = GL.getICD().glBufferDataARB;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPPV(n, Integer.toUnsignedLong(fArray.length) << 2, fArray, n2, l);
    }

    public static void glBufferDataARB(@NativeType(value="GLenum") int n, @NativeType(value="void const *") double[] dArray, @NativeType(value="GLenum") int n2) {
        long l = GL.getICD().glBufferDataARB;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPPV(n, Integer.toUnsignedLong(dArray.length) << 3, dArray, n2, l);
    }

    public static void glBufferSubDataARB(@NativeType(value="GLenum") int n, @NativeType(value="GLintptrARB") long l, @NativeType(value="void const *") short[] sArray) {
        long l2 = GL.getICD().glBufferSubDataARB;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        JNI.callPPPV(n, l, Integer.toUnsignedLong(sArray.length) << 1, sArray, l2);
    }

    public static void glBufferSubDataARB(@NativeType(value="GLenum") int n, @NativeType(value="GLintptrARB") long l, @NativeType(value="void const *") int[] nArray) {
        long l2 = GL.getICD().glBufferSubDataARB;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        JNI.callPPPV(n, l, Integer.toUnsignedLong(nArray.length) << 2, nArray, l2);
    }

    public static void glBufferSubDataARB(@NativeType(value="GLenum") int n, @NativeType(value="GLintptrARB") long l, @NativeType(value="void const *") float[] fArray) {
        long l2 = GL.getICD().glBufferSubDataARB;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        JNI.callPPPV(n, l, Integer.toUnsignedLong(fArray.length) << 2, fArray, l2);
    }

    public static void glBufferSubDataARB(@NativeType(value="GLenum") int n, @NativeType(value="GLintptrARB") long l, @NativeType(value="void const *") double[] dArray) {
        long l2 = GL.getICD().glBufferSubDataARB;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        JNI.callPPPV(n, l, Integer.toUnsignedLong(dArray.length) << 3, dArray, l2);
    }

    public static void glGetBufferSubDataARB(@NativeType(value="GLenum") int n, @NativeType(value="GLintptrARB") long l, @NativeType(value="void *") short[] sArray) {
        long l2 = GL.getICD().glGetBufferSubDataARB;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        JNI.callPPPV(n, l, Integer.toUnsignedLong(sArray.length) << 1, sArray, l2);
    }

    public static void glGetBufferSubDataARB(@NativeType(value="GLenum") int n, @NativeType(value="GLintptrARB") long l, @NativeType(value="void *") int[] nArray) {
        long l2 = GL.getICD().glGetBufferSubDataARB;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        JNI.callPPPV(n, l, Integer.toUnsignedLong(nArray.length) << 2, nArray, l2);
    }

    public static void glGetBufferSubDataARB(@NativeType(value="GLenum") int n, @NativeType(value="GLintptrARB") long l, @NativeType(value="void *") float[] fArray) {
        long l2 = GL.getICD().glGetBufferSubDataARB;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        JNI.callPPPV(n, l, Integer.toUnsignedLong(fArray.length) << 2, fArray, l2);
    }

    public static void glGetBufferSubDataARB(@NativeType(value="GLenum") int n, @NativeType(value="GLintptrARB") long l, @NativeType(value="void *") double[] dArray) {
        long l2 = GL.getICD().glGetBufferSubDataARB;
        if (Checks.CHECKS) {
            Checks.check(l2);
        }
        JNI.callPPPV(n, l, Integer.toUnsignedLong(dArray.length) << 3, dArray, l2);
    }

    public static void glGetBufferParameterivARB(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") int[] nArray) {
        long l = GL.getICD().glGetBufferParameterivARB;
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

