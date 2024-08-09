/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import javax.annotation.Nullable;
import org.lwjgl.PointerBuffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL15C;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class GL15
extends GL14 {
    public static final int GL_FOG_COORD_SRC = 33872;
    public static final int GL_FOG_COORD = 33873;
    public static final int GL_CURRENT_FOG_COORD = 33875;
    public static final int GL_FOG_COORD_ARRAY_TYPE = 33876;
    public static final int GL_FOG_COORD_ARRAY_STRIDE = 33877;
    public static final int GL_FOG_COORD_ARRAY_POINTER = 33878;
    public static final int GL_FOG_COORD_ARRAY = 33879;
    public static final int GL_FOG_COORD_ARRAY_BUFFER_BINDING = 34973;
    public static final int GL_SRC0_RGB = 34176;
    public static final int GL_SRC1_RGB = 34177;
    public static final int GL_SRC2_RGB = 34178;
    public static final int GL_SRC0_ALPHA = 34184;
    public static final int GL_SRC1_ALPHA = 34185;
    public static final int GL_SRC2_ALPHA = 34186;
    public static final int GL_ARRAY_BUFFER = 34962;
    public static final int GL_ELEMENT_ARRAY_BUFFER = 34963;
    public static final int GL_ARRAY_BUFFER_BINDING = 34964;
    public static final int GL_ELEMENT_ARRAY_BUFFER_BINDING = 34965;
    public static final int GL_VERTEX_ARRAY_BUFFER_BINDING = 34966;
    public static final int GL_NORMAL_ARRAY_BUFFER_BINDING = 34967;
    public static final int GL_COLOR_ARRAY_BUFFER_BINDING = 34968;
    public static final int GL_INDEX_ARRAY_BUFFER_BINDING = 34969;
    public static final int GL_TEXTURE_COORD_ARRAY_BUFFER_BINDING = 34970;
    public static final int GL_EDGE_FLAG_ARRAY_BUFFER_BINDING = 34971;
    public static final int GL_SECONDARY_COLOR_ARRAY_BUFFER_BINDING = 34972;
    public static final int GL_FOG_COORDINATE_ARRAY_BUFFER_BINDING = 34973;
    public static final int GL_WEIGHT_ARRAY_BUFFER_BINDING = 34974;
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

    protected GL15() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glBindBuffer, gLCapabilities.glDeleteBuffers, gLCapabilities.glGenBuffers, gLCapabilities.glIsBuffer, gLCapabilities.glBufferData, gLCapabilities.glBufferSubData, gLCapabilities.glGetBufferSubData, gLCapabilities.glMapBuffer, gLCapabilities.glUnmapBuffer, gLCapabilities.glGetBufferParameteriv, gLCapabilities.glGetBufferPointerv, gLCapabilities.glGenQueries, gLCapabilities.glDeleteQueries, gLCapabilities.glIsQuery, gLCapabilities.glBeginQuery, gLCapabilities.glEndQuery, gLCapabilities.glGetQueryiv, gLCapabilities.glGetQueryObjectiv, gLCapabilities.glGetQueryObjectuiv);
    }

    public static void glBindBuffer(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2) {
        GL15C.glBindBuffer(n, n2);
    }

    public static void nglDeleteBuffers(int n, long l) {
        GL15C.nglDeleteBuffers(n, l);
    }

    public static void glDeleteBuffers(@NativeType(value="GLuint const *") IntBuffer intBuffer) {
        GL15C.glDeleteBuffers(intBuffer);
    }

    public static void glDeleteBuffers(@NativeType(value="GLuint const *") int n) {
        GL15C.glDeleteBuffers(n);
    }

    public static void nglGenBuffers(int n, long l) {
        GL15C.nglGenBuffers(n, l);
    }

    public static void glGenBuffers(@NativeType(value="GLuint *") IntBuffer intBuffer) {
        GL15C.glGenBuffers(intBuffer);
    }

    @NativeType(value="void")
    public static int glGenBuffers() {
        return GL15C.glGenBuffers();
    }

    @NativeType(value="GLboolean")
    public static boolean glIsBuffer(@NativeType(value="GLuint") int n) {
        return GL15C.glIsBuffer(n);
    }

    public static void nglBufferData(int n, long l, long l2, int n2) {
        GL15C.nglBufferData(n, l, l2, n2);
    }

    public static void glBufferData(@NativeType(value="GLenum") int n, @NativeType(value="GLsizeiptr") long l, @NativeType(value="GLenum") int n2) {
        GL15C.glBufferData(n, l, n2);
    }

    public static void glBufferData(@NativeType(value="GLenum") int n, @NativeType(value="void const *") ByteBuffer byteBuffer, @NativeType(value="GLenum") int n2) {
        GL15C.glBufferData(n, byteBuffer, n2);
    }

    public static void glBufferData(@NativeType(value="GLenum") int n, @NativeType(value="void const *") ShortBuffer shortBuffer, @NativeType(value="GLenum") int n2) {
        GL15C.glBufferData(n, shortBuffer, n2);
    }

    public static void glBufferData(@NativeType(value="GLenum") int n, @NativeType(value="void const *") IntBuffer intBuffer, @NativeType(value="GLenum") int n2) {
        GL15C.glBufferData(n, intBuffer, n2);
    }

    public static void glBufferData(@NativeType(value="GLenum") int n, @NativeType(value="void const *") LongBuffer longBuffer, @NativeType(value="GLenum") int n2) {
        GL15C.glBufferData(n, longBuffer, n2);
    }

    public static void glBufferData(@NativeType(value="GLenum") int n, @NativeType(value="void const *") FloatBuffer floatBuffer, @NativeType(value="GLenum") int n2) {
        GL15C.glBufferData(n, floatBuffer, n2);
    }

    public static void glBufferData(@NativeType(value="GLenum") int n, @NativeType(value="void const *") DoubleBuffer doubleBuffer, @NativeType(value="GLenum") int n2) {
        GL15C.glBufferData(n, doubleBuffer, n2);
    }

    public static void nglBufferSubData(int n, long l, long l2, long l3) {
        GL15C.nglBufferSubData(n, l, l2, l3);
    }

    public static void glBufferSubData(@NativeType(value="GLenum") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void const *") ByteBuffer byteBuffer) {
        GL15C.glBufferSubData(n, l, byteBuffer);
    }

    public static void glBufferSubData(@NativeType(value="GLenum") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void const *") ShortBuffer shortBuffer) {
        GL15C.glBufferSubData(n, l, shortBuffer);
    }

    public static void glBufferSubData(@NativeType(value="GLenum") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void const *") IntBuffer intBuffer) {
        GL15C.glBufferSubData(n, l, intBuffer);
    }

    public static void glBufferSubData(@NativeType(value="GLenum") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void const *") LongBuffer longBuffer) {
        GL15C.glBufferSubData(n, l, longBuffer);
    }

    public static void glBufferSubData(@NativeType(value="GLenum") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void const *") FloatBuffer floatBuffer) {
        GL15C.glBufferSubData(n, l, floatBuffer);
    }

    public static void glBufferSubData(@NativeType(value="GLenum") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void const *") DoubleBuffer doubleBuffer) {
        GL15C.glBufferSubData(n, l, doubleBuffer);
    }

    public static void nglGetBufferSubData(int n, long l, long l2, long l3) {
        GL15C.nglGetBufferSubData(n, l, l2, l3);
    }

    public static void glGetBufferSubData(@NativeType(value="GLenum") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void *") ByteBuffer byteBuffer) {
        GL15C.glGetBufferSubData(n, l, byteBuffer);
    }

    public static void glGetBufferSubData(@NativeType(value="GLenum") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void *") ShortBuffer shortBuffer) {
        GL15C.glGetBufferSubData(n, l, shortBuffer);
    }

    public static void glGetBufferSubData(@NativeType(value="GLenum") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void *") IntBuffer intBuffer) {
        GL15C.glGetBufferSubData(n, l, intBuffer);
    }

    public static void glGetBufferSubData(@NativeType(value="GLenum") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void *") LongBuffer longBuffer) {
        GL15C.glGetBufferSubData(n, l, longBuffer);
    }

    public static void glGetBufferSubData(@NativeType(value="GLenum") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void *") FloatBuffer floatBuffer) {
        GL15C.glGetBufferSubData(n, l, floatBuffer);
    }

    public static void glGetBufferSubData(@NativeType(value="GLenum") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void *") DoubleBuffer doubleBuffer) {
        GL15C.glGetBufferSubData(n, l, doubleBuffer);
    }

    public static long nglMapBuffer(int n, int n2) {
        return GL15C.nglMapBuffer(n, n2);
    }

    @Nullable
    @NativeType(value="void *")
    public static ByteBuffer glMapBuffer(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2) {
        return GL15C.glMapBuffer(n, n2);
    }

    @Nullable
    @NativeType(value="void *")
    public static ByteBuffer glMapBuffer(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @Nullable ByteBuffer byteBuffer) {
        return GL15C.glMapBuffer(n, n2);
    }

    @Nullable
    @NativeType(value="void *")
    public static ByteBuffer glMapBuffer(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, long l, @Nullable ByteBuffer byteBuffer) {
        return GL15C.glMapBuffer(n, n2);
    }

    @NativeType(value="GLboolean")
    public static boolean glUnmapBuffer(@NativeType(value="GLenum") int n) {
        return GL15C.glUnmapBuffer(n);
    }

    public static void nglGetBufferParameteriv(int n, int n2, long l) {
        GL15C.nglGetBufferParameteriv(n, n2, l);
    }

    public static void glGetBufferParameteriv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") IntBuffer intBuffer) {
        GL15C.glGetBufferParameteriv(n, n2, intBuffer);
    }

    @NativeType(value="void")
    public static int glGetBufferParameteri(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2) {
        return GL15C.glGetBufferParameteri(n, n2);
    }

    public static void nglGetBufferPointerv(int n, int n2, long l) {
        GL15C.nglGetBufferPointerv(n, n2, l);
    }

    public static void glGetBufferPointerv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="void **") PointerBuffer pointerBuffer) {
        GL15C.glGetBufferPointerv(n, n2, pointerBuffer);
    }

    @NativeType(value="void")
    public static long glGetBufferPointer(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2) {
        return GL15C.glGetBufferPointer(n, n2);
    }

    public static void nglGenQueries(int n, long l) {
        GL15C.nglGenQueries(n, l);
    }

    public static void glGenQueries(@NativeType(value="GLuint *") IntBuffer intBuffer) {
        GL15C.glGenQueries(intBuffer);
    }

    @NativeType(value="void")
    public static int glGenQueries() {
        return GL15C.glGenQueries();
    }

    public static void nglDeleteQueries(int n, long l) {
        GL15C.nglDeleteQueries(n, l);
    }

    public static void glDeleteQueries(@NativeType(value="GLuint const *") IntBuffer intBuffer) {
        GL15C.glDeleteQueries(intBuffer);
    }

    public static void glDeleteQueries(@NativeType(value="GLuint const *") int n) {
        GL15C.glDeleteQueries(n);
    }

    @NativeType(value="GLboolean")
    public static boolean glIsQuery(@NativeType(value="GLuint") int n) {
        return GL15C.glIsQuery(n);
    }

    public static void glBeginQuery(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2) {
        GL15C.glBeginQuery(n, n2);
    }

    public static void glEndQuery(@NativeType(value="GLenum") int n) {
        GL15C.glEndQuery(n);
    }

    public static void nglGetQueryiv(int n, int n2, long l) {
        GL15C.nglGetQueryiv(n, n2, l);
    }

    public static void glGetQueryiv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") IntBuffer intBuffer) {
        GL15C.glGetQueryiv(n, n2, intBuffer);
    }

    @NativeType(value="void")
    public static int glGetQueryi(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2) {
        return GL15C.glGetQueryi(n, n2);
    }

    public static void nglGetQueryObjectiv(int n, int n2, long l) {
        GL15C.nglGetQueryObjectiv(n, n2, l);
    }

    public static void glGetQueryObjectiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") IntBuffer intBuffer) {
        GL15C.glGetQueryObjectiv(n, n2, intBuffer);
    }

    @NativeType(value="void")
    public static int glGetQueryObjecti(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2) {
        return GL15C.glGetQueryObjecti(n, n2);
    }

    public static void nglGetQueryObjectuiv(int n, int n2, long l) {
        GL15C.nglGetQueryObjectuiv(n, n2, l);
    }

    public static void glGetQueryObjectuiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint *") IntBuffer intBuffer) {
        GL15C.glGetQueryObjectuiv(n, n2, intBuffer);
    }

    @NativeType(value="void")
    public static int glGetQueryObjectui(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2) {
        return GL15C.glGetQueryObjectui(n, n2);
    }

    public static void glDeleteBuffers(@NativeType(value="GLuint const *") int[] nArray) {
        GL15C.glDeleteBuffers(nArray);
    }

    public static void glGenBuffers(@NativeType(value="GLuint *") int[] nArray) {
        GL15C.glGenBuffers(nArray);
    }

    public static void glBufferData(@NativeType(value="GLenum") int n, @NativeType(value="void const *") short[] sArray, @NativeType(value="GLenum") int n2) {
        GL15C.glBufferData(n, sArray, n2);
    }

    public static void glBufferData(@NativeType(value="GLenum") int n, @NativeType(value="void const *") int[] nArray, @NativeType(value="GLenum") int n2) {
        GL15C.glBufferData(n, nArray, n2);
    }

    public static void glBufferData(@NativeType(value="GLenum") int n, @NativeType(value="void const *") long[] lArray, @NativeType(value="GLenum") int n2) {
        GL15C.glBufferData(n, lArray, n2);
    }

    public static void glBufferData(@NativeType(value="GLenum") int n, @NativeType(value="void const *") float[] fArray, @NativeType(value="GLenum") int n2) {
        GL15C.glBufferData(n, fArray, n2);
    }

    public static void glBufferData(@NativeType(value="GLenum") int n, @NativeType(value="void const *") double[] dArray, @NativeType(value="GLenum") int n2) {
        GL15C.glBufferData(n, dArray, n2);
    }

    public static void glBufferSubData(@NativeType(value="GLenum") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void const *") short[] sArray) {
        GL15C.glBufferSubData(n, l, sArray);
    }

    public static void glBufferSubData(@NativeType(value="GLenum") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void const *") int[] nArray) {
        GL15C.glBufferSubData(n, l, nArray);
    }

    public static void glBufferSubData(@NativeType(value="GLenum") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void const *") long[] lArray) {
        GL15C.glBufferSubData(n, l, lArray);
    }

    public static void glBufferSubData(@NativeType(value="GLenum") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void const *") float[] fArray) {
        GL15C.glBufferSubData(n, l, fArray);
    }

    public static void glBufferSubData(@NativeType(value="GLenum") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void const *") double[] dArray) {
        GL15C.glBufferSubData(n, l, dArray);
    }

    public static void glGetBufferSubData(@NativeType(value="GLenum") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void *") short[] sArray) {
        GL15C.glGetBufferSubData(n, l, sArray);
    }

    public static void glGetBufferSubData(@NativeType(value="GLenum") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void *") int[] nArray) {
        GL15C.glGetBufferSubData(n, l, nArray);
    }

    public static void glGetBufferSubData(@NativeType(value="GLenum") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void *") long[] lArray) {
        GL15C.glGetBufferSubData(n, l, lArray);
    }

    public static void glGetBufferSubData(@NativeType(value="GLenum") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void *") float[] fArray) {
        GL15C.glGetBufferSubData(n, l, fArray);
    }

    public static void glGetBufferSubData(@NativeType(value="GLenum") int n, @NativeType(value="GLintptr") long l, @NativeType(value="void *") double[] dArray) {
        GL15C.glGetBufferSubData(n, l, dArray);
    }

    public static void glGetBufferParameteriv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") int[] nArray) {
        GL15C.glGetBufferParameteriv(n, n2, nArray);
    }

    public static void glGenQueries(@NativeType(value="GLuint *") int[] nArray) {
        GL15C.glGenQueries(nArray);
    }

    public static void glDeleteQueries(@NativeType(value="GLuint const *") int[] nArray) {
        GL15C.glDeleteQueries(nArray);
    }

    public static void glGetQueryiv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") int[] nArray) {
        GL15C.glGetQueryiv(n, n2, nArray);
    }

    public static void glGetQueryObjectiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") int[] nArray) {
        GL15C.glGetQueryObjectiv(n, n2, nArray);
    }

    public static void glGetQueryObjectuiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint *") int[] nArray) {
        GL15C.glGetQueryObjectuiv(n, n2, nArray);
    }

    static {
        GL.initialize();
    }
}

