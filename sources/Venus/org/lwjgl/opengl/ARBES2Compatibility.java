/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL41C;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class ARBES2Compatibility {
    public static final int GL_SHADER_COMPILER = 36346;
    public static final int GL_SHADER_BINARY_FORMATS = 36344;
    public static final int GL_NUM_SHADER_BINARY_FORMATS = 36345;
    public static final int GL_MAX_VERTEX_UNIFORM_VECTORS = 36347;
    public static final int GL_MAX_VARYING_VECTORS = 36348;
    public static final int GL_MAX_FRAGMENT_UNIFORM_VECTORS = 36349;
    public static final int GL_IMPLEMENTATION_COLOR_READ_TYPE = 35738;
    public static final int GL_IMPLEMENTATION_COLOR_READ_FORMAT = 35739;
    public static final int GL_FIXED = 5132;
    public static final int GL_LOW_FLOAT = 36336;
    public static final int GL_MEDIUM_FLOAT = 36337;
    public static final int GL_HIGH_FLOAT = 36338;
    public static final int GL_LOW_INT = 36339;
    public static final int GL_MEDIUM_INT = 36340;
    public static final int GL_HIGH_INT = 36341;
    public static final int GL_RGB565 = 36194;

    protected ARBES2Compatibility() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glReleaseShaderCompiler, gLCapabilities.glShaderBinary, gLCapabilities.glGetShaderPrecisionFormat, gLCapabilities.glDepthRangef, gLCapabilities.glClearDepthf);
    }

    public static void glReleaseShaderCompiler() {
        GL41C.glReleaseShaderCompiler();
    }

    public static void nglShaderBinary(int n, long l, int n2, long l2, int n3) {
        GL41C.nglShaderBinary(n, l, n2, l2, n3);
    }

    public static void glShaderBinary(@NativeType(value="GLuint const *") IntBuffer intBuffer, @NativeType(value="GLenum") int n, @NativeType(value="void const *") ByteBuffer byteBuffer) {
        GL41C.glShaderBinary(intBuffer, n, byteBuffer);
    }

    public static void nglGetShaderPrecisionFormat(int n, int n2, long l, long l2) {
        GL41C.nglGetShaderPrecisionFormat(n, n2, l, l2);
    }

    public static void glGetShaderPrecisionFormat(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") IntBuffer intBuffer, @NativeType(value="GLint *") IntBuffer intBuffer2) {
        GL41C.glGetShaderPrecisionFormat(n, n2, intBuffer, intBuffer2);
    }

    @NativeType(value="void")
    public static int glGetShaderPrecisionFormat(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") IntBuffer intBuffer) {
        return GL41C.glGetShaderPrecisionFormat(n, n2, intBuffer);
    }

    public static void glDepthRangef(@NativeType(value="GLfloat") float f, @NativeType(value="GLfloat") float f2) {
        GL41C.glDepthRangef(f, f2);
    }

    public static void glClearDepthf(@NativeType(value="GLfloat") float f) {
        GL41C.glClearDepthf(f);
    }

    public static void glShaderBinary(@NativeType(value="GLuint const *") int[] nArray, @NativeType(value="GLenum") int n, @NativeType(value="void const *") ByteBuffer byteBuffer) {
        GL41C.glShaderBinary(nArray, n, byteBuffer);
    }

    public static void glGetShaderPrecisionFormat(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") int[] nArray, @NativeType(value="GLint *") int[] nArray2) {
        GL41C.glGetShaderPrecisionFormat(n, n2, nArray, nArray2);
    }

    static {
        GL.initialize();
    }
}

