/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL45C;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class KHRRobustness {
    public static final int GL_NO_ERROR = 0;
    public static final int GL_GUILTY_CONTEXT_RESET = 33363;
    public static final int GL_INNOCENT_CONTEXT_RESET = 33364;
    public static final int GL_UNKNOWN_CONTEXT_RESET = 33365;
    public static final int GL_CONTEXT_ROBUST_ACCESS = 37107;
    public static final int GL_RESET_NOTIFICATION_STRATEGY = 33366;
    public static final int GL_LOSE_CONTEXT_ON_RESET = 33362;
    public static final int GL_NO_RESET_NOTIFICATION = 33377;
    public static final int GL_CONTEXT_LOST = 1287;

    protected KHRRobustness() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glGetGraphicsResetStatus, gLCapabilities.glReadnPixels, gLCapabilities.glGetnUniformfv, gLCapabilities.glGetnUniformiv, gLCapabilities.glGetnUniformuiv);
    }

    @NativeType(value="GLenum")
    public static int glGetGraphicsResetStatus() {
        return GL45C.glGetGraphicsResetStatus();
    }

    public static void nglReadnPixels(int n, int n2, int n3, int n4, int n5, int n6, int n7, long l) {
        GL45C.nglReadnPixels(n, n2, n3, n4, n5, n6, n7, l);
    }

    public static void glReadnPixels(@NativeType(value="GLint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="GLsizei") int n7, @NativeType(value="void *") long l) {
        GL45C.glReadnPixels(n, n2, n3, n4, n5, n6, n7, l);
    }

    public static void glReadnPixels(@NativeType(value="GLint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="void *") ByteBuffer byteBuffer) {
        GL45C.glReadnPixels(n, n2, n3, n4, n5, n6, byteBuffer);
    }

    public static void glReadnPixels(@NativeType(value="GLint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="void *") ShortBuffer shortBuffer) {
        GL45C.glReadnPixels(n, n2, n3, n4, n5, n6, shortBuffer);
    }

    public static void glReadnPixels(@NativeType(value="GLint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="void *") IntBuffer intBuffer) {
        GL45C.glReadnPixels(n, n2, n3, n4, n5, n6, intBuffer);
    }

    public static void glReadnPixels(@NativeType(value="GLint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="void *") FloatBuffer floatBuffer) {
        GL45C.glReadnPixels(n, n2, n3, n4, n5, n6, floatBuffer);
    }

    public static void nglGetnUniformfv(int n, int n2, int n3, long l) {
        GL45C.nglGetnUniformfv(n, n2, n3, l);
    }

    public static void glGetnUniformfv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLfloat *") FloatBuffer floatBuffer) {
        GL45C.glGetnUniformfv(n, n2, floatBuffer);
    }

    @NativeType(value="void")
    public static float glGetnUniformf(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2) {
        return GL45C.glGetnUniformf(n, n2);
    }

    public static void nglGetnUniformiv(int n, int n2, int n3, long l) {
        GL45C.nglGetnUniformiv(n, n2, n3, l);
    }

    public static void glGetnUniformiv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLfloat *") FloatBuffer floatBuffer) {
        GL45C.glGetnUniformiv(n, n2, floatBuffer);
    }

    @NativeType(value="void")
    public static float glGetnUniformi(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2) {
        return GL45C.glGetnUniformi(n, n2);
    }

    public static void nglGetnUniformuiv(int n, int n2, int n3, long l) {
        GL45C.nglGetnUniformuiv(n, n2, n3, l);
    }

    public static void glGetnUniformuiv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLfloat *") FloatBuffer floatBuffer) {
        GL45C.glGetnUniformuiv(n, n2, floatBuffer);
    }

    @NativeType(value="void")
    public static float glGetnUniformui(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2) {
        return GL45C.glGetnUniformui(n, n2);
    }

    public static void glReadnPixels(@NativeType(value="GLint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="void *") short[] sArray) {
        GL45C.glReadnPixels(n, n2, n3, n4, n5, n6, sArray);
    }

    public static void glReadnPixels(@NativeType(value="GLint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="void *") int[] nArray) {
        GL45C.glReadnPixels(n, n2, n3, n4, n5, n6, nArray);
    }

    public static void glReadnPixels(@NativeType(value="GLint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLenum") int n5, @NativeType(value="GLenum") int n6, @NativeType(value="void *") float[] fArray) {
        GL45C.glReadnPixels(n, n2, n3, n4, n5, n6, fArray);
    }

    public static void glGetnUniformfv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLfloat *") float[] fArray) {
        GL45C.glGetnUniformfv(n, n2, fArray);
    }

    public static void glGetnUniformiv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLfloat *") float[] fArray) {
        GL45C.glGetnUniformiv(n, n2, fArray);
    }

    public static void glGetnUniformuiv(@NativeType(value="GLuint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLfloat *") float[] fArray) {
        GL45C.glGetnUniformuiv(n, n2, fArray);
    }

    static {
        GL.initialize();
    }
}

