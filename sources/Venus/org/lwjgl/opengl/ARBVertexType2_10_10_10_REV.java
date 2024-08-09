/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.IntBuffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL33;
import org.lwjgl.opengl.GL33C;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class ARBVertexType2_10_10_10_REV {
    public static final int GL_INT_2_10_10_10_REV = 36255;

    protected ARBVertexType2_10_10_10_REV() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities, boolean bl) {
        return (bl || Checks.checkFunctions(gLCapabilities.glVertexP2ui, gLCapabilities.glVertexP3ui, gLCapabilities.glVertexP4ui, gLCapabilities.glVertexP2uiv, gLCapabilities.glVertexP3uiv, gLCapabilities.glVertexP4uiv, gLCapabilities.glTexCoordP1ui, gLCapabilities.glTexCoordP2ui, gLCapabilities.glTexCoordP3ui, gLCapabilities.glTexCoordP4ui, gLCapabilities.glTexCoordP1uiv, gLCapabilities.glTexCoordP2uiv, gLCapabilities.glTexCoordP3uiv, gLCapabilities.glTexCoordP4uiv, gLCapabilities.glMultiTexCoordP1ui, gLCapabilities.glMultiTexCoordP2ui, gLCapabilities.glMultiTexCoordP3ui, gLCapabilities.glMultiTexCoordP4ui, gLCapabilities.glMultiTexCoordP1uiv, gLCapabilities.glMultiTexCoordP2uiv, gLCapabilities.glMultiTexCoordP3uiv, gLCapabilities.glMultiTexCoordP4uiv, gLCapabilities.glNormalP3ui, gLCapabilities.glNormalP3uiv, gLCapabilities.glColorP3ui, gLCapabilities.glColorP4ui, gLCapabilities.glColorP3uiv, gLCapabilities.glColorP4uiv, gLCapabilities.glSecondaryColorP3ui, gLCapabilities.glSecondaryColorP3uiv)) && Checks.checkFunctions(gLCapabilities.glVertexAttribP1ui, gLCapabilities.glVertexAttribP2ui, gLCapabilities.glVertexAttribP3ui, gLCapabilities.glVertexAttribP4ui, gLCapabilities.glVertexAttribP1uiv, gLCapabilities.glVertexAttribP2uiv, gLCapabilities.glVertexAttribP3uiv, gLCapabilities.glVertexAttribP4uiv);
    }

    public static void glVertexP2ui(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2) {
        GL33.glVertexP2ui(n, n2);
    }

    public static void glVertexP3ui(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2) {
        GL33.glVertexP3ui(n, n2);
    }

    public static void glVertexP4ui(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2) {
        GL33.glVertexP4ui(n, n2);
    }

    public static void nglVertexP2uiv(int n, long l) {
        GL33.nglVertexP2uiv(n, l);
    }

    public static void glVertexP2uiv(@NativeType(value="GLenum") int n, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        GL33.glVertexP2uiv(n, intBuffer);
    }

    public static void nglVertexP3uiv(int n, long l) {
        GL33.nglVertexP3uiv(n, l);
    }

    public static void glVertexP3uiv(@NativeType(value="GLenum") int n, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        GL33.glVertexP3uiv(n, intBuffer);
    }

    public static void nglVertexP4uiv(int n, long l) {
        GL33.nglVertexP4uiv(n, l);
    }

    public static void glVertexP4uiv(@NativeType(value="GLenum") int n, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        GL33.glVertexP4uiv(n, intBuffer);
    }

    public static void glTexCoordP1ui(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2) {
        GL33.glTexCoordP1ui(n, n2);
    }

    public static void glTexCoordP2ui(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2) {
        GL33.glTexCoordP2ui(n, n2);
    }

    public static void glTexCoordP3ui(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2) {
        GL33.glTexCoordP3ui(n, n2);
    }

    public static void glTexCoordP4ui(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2) {
        GL33.glTexCoordP4ui(n, n2);
    }

    public static void nglTexCoordP1uiv(int n, long l) {
        GL33.nglTexCoordP1uiv(n, l);
    }

    public static void glTexCoordP1uiv(@NativeType(value="GLenum") int n, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        GL33.glTexCoordP1uiv(n, intBuffer);
    }

    public static void nglTexCoordP2uiv(int n, long l) {
        GL33.nglTexCoordP2uiv(n, l);
    }

    public static void glTexCoordP2uiv(@NativeType(value="GLenum") int n, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        GL33.glTexCoordP2uiv(n, intBuffer);
    }

    public static void nglTexCoordP3uiv(int n, long l) {
        GL33.nglTexCoordP3uiv(n, l);
    }

    public static void glTexCoordP3uiv(@NativeType(value="GLenum") int n, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        GL33.glTexCoordP3uiv(n, intBuffer);
    }

    public static void nglTexCoordP4uiv(int n, long l) {
        GL33.nglTexCoordP4uiv(n, l);
    }

    public static void glTexCoordP4uiv(@NativeType(value="GLenum") int n, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        GL33.glTexCoordP4uiv(n, intBuffer);
    }

    public static void glMultiTexCoordP1ui(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint") int n3) {
        GL33.glMultiTexCoordP1ui(n, n2, n3);
    }

    public static void glMultiTexCoordP2ui(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint") int n3) {
        GL33.glMultiTexCoordP2ui(n, n2, n3);
    }

    public static void glMultiTexCoordP3ui(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint") int n3) {
        GL33.glMultiTexCoordP3ui(n, n2, n3);
    }

    public static void glMultiTexCoordP4ui(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint") int n3) {
        GL33.glMultiTexCoordP4ui(n, n2, n3);
    }

    public static void nglMultiTexCoordP1uiv(int n, int n2, long l) {
        GL33.nglMultiTexCoordP1uiv(n, n2, l);
    }

    public static void glMultiTexCoordP1uiv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        GL33.glMultiTexCoordP1uiv(n, n2, intBuffer);
    }

    public static void nglMultiTexCoordP2uiv(int n, int n2, long l) {
        GL33.nglMultiTexCoordP2uiv(n, n2, l);
    }

    public static void glMultiTexCoordP2uiv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        GL33.glMultiTexCoordP2uiv(n, n2, intBuffer);
    }

    public static void nglMultiTexCoordP3uiv(int n, int n2, long l) {
        GL33.nglMultiTexCoordP3uiv(n, n2, l);
    }

    public static void glMultiTexCoordP3uiv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        GL33.glMultiTexCoordP3uiv(n, n2, intBuffer);
    }

    public static void nglMultiTexCoordP4uiv(int n, int n2, long l) {
        GL33.nglMultiTexCoordP4uiv(n, n2, l);
    }

    public static void glMultiTexCoordP4uiv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        GL33.glMultiTexCoordP4uiv(n, n2, intBuffer);
    }

    public static void glNormalP3ui(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2) {
        GL33.glNormalP3ui(n, n2);
    }

    public static void nglNormalP3uiv(int n, long l) {
        GL33.nglNormalP3uiv(n, l);
    }

    public static void glNormalP3uiv(@NativeType(value="GLenum") int n, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        GL33.glNormalP3uiv(n, intBuffer);
    }

    public static void glColorP3ui(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2) {
        GL33.glColorP3ui(n, n2);
    }

    public static void glColorP4ui(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2) {
        GL33.glColorP4ui(n, n2);
    }

    public static void nglColorP3uiv(int n, long l) {
        GL33.nglColorP3uiv(n, l);
    }

    public static void glColorP3uiv(@NativeType(value="GLenum") int n, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        GL33.glColorP3uiv(n, intBuffer);
    }

    public static void nglColorP4uiv(int n, long l) {
        GL33.nglColorP4uiv(n, l);
    }

    public static void glColorP4uiv(@NativeType(value="GLenum") int n, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        GL33.glColorP4uiv(n, intBuffer);
    }

    public static void glSecondaryColorP3ui(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2) {
        GL33.glSecondaryColorP3ui(n, n2);
    }

    public static void nglSecondaryColorP3uiv(int n, long l) {
        GL33.nglSecondaryColorP3uiv(n, l);
    }

    public static void glSecondaryColorP3uiv(@NativeType(value="GLenum") int n, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        GL33.glSecondaryColorP3uiv(n, intBuffer);
    }

    public static void glVertexAttribP1ui(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLuint") int n3) {
        GL33C.glVertexAttribP1ui(n, n2, bl, n3);
    }

    public static void glVertexAttribP2ui(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLuint") int n3) {
        GL33C.glVertexAttribP2ui(n, n2, bl, n3);
    }

    public static void glVertexAttribP3ui(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLuint") int n3) {
        GL33C.glVertexAttribP3ui(n, n2, bl, n3);
    }

    public static void glVertexAttribP4ui(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLuint") int n3) {
        GL33C.glVertexAttribP4ui(n, n2, bl, n3);
    }

    public static void nglVertexAttribP1uiv(int n, int n2, boolean bl, long l) {
        GL33C.nglVertexAttribP1uiv(n, n2, bl, l);
    }

    public static void glVertexAttribP1uiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        GL33C.glVertexAttribP1uiv(n, n2, bl, intBuffer);
    }

    public static void nglVertexAttribP2uiv(int n, int n2, boolean bl, long l) {
        GL33C.nglVertexAttribP2uiv(n, n2, bl, l);
    }

    public static void glVertexAttribP2uiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        GL33C.glVertexAttribP2uiv(n, n2, bl, intBuffer);
    }

    public static void nglVertexAttribP3uiv(int n, int n2, boolean bl, long l) {
        GL33C.nglVertexAttribP3uiv(n, n2, bl, l);
    }

    public static void glVertexAttribP3uiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        GL33C.glVertexAttribP3uiv(n, n2, bl, intBuffer);
    }

    public static void nglVertexAttribP4uiv(int n, int n2, boolean bl, long l) {
        GL33C.nglVertexAttribP4uiv(n, n2, bl, l);
    }

    public static void glVertexAttribP4uiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLuint const *") IntBuffer intBuffer) {
        GL33C.glVertexAttribP4uiv(n, n2, bl, intBuffer);
    }

    public static void glVertexP2uiv(@NativeType(value="GLenum") int n, @NativeType(value="GLuint const *") int[] nArray) {
        GL33.glVertexP2uiv(n, nArray);
    }

    public static void glVertexP3uiv(@NativeType(value="GLenum") int n, @NativeType(value="GLuint const *") int[] nArray) {
        GL33.glVertexP3uiv(n, nArray);
    }

    public static void glVertexP4uiv(@NativeType(value="GLenum") int n, @NativeType(value="GLuint const *") int[] nArray) {
        GL33.glVertexP4uiv(n, nArray);
    }

    public static void glTexCoordP1uiv(@NativeType(value="GLenum") int n, @NativeType(value="GLuint const *") int[] nArray) {
        GL33.glTexCoordP1uiv(n, nArray);
    }

    public static void glTexCoordP2uiv(@NativeType(value="GLenum") int n, @NativeType(value="GLuint const *") int[] nArray) {
        GL33.glTexCoordP2uiv(n, nArray);
    }

    public static void glTexCoordP3uiv(@NativeType(value="GLenum") int n, @NativeType(value="GLuint const *") int[] nArray) {
        GL33.glTexCoordP3uiv(n, nArray);
    }

    public static void glTexCoordP4uiv(@NativeType(value="GLenum") int n, @NativeType(value="GLuint const *") int[] nArray) {
        GL33.glTexCoordP4uiv(n, nArray);
    }

    public static void glMultiTexCoordP1uiv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint const *") int[] nArray) {
        GL33.glMultiTexCoordP1uiv(n, n2, nArray);
    }

    public static void glMultiTexCoordP2uiv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint const *") int[] nArray) {
        GL33.glMultiTexCoordP2uiv(n, n2, nArray);
    }

    public static void glMultiTexCoordP3uiv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint const *") int[] nArray) {
        GL33.glMultiTexCoordP3uiv(n, n2, nArray);
    }

    public static void glMultiTexCoordP4uiv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint const *") int[] nArray) {
        GL33.glMultiTexCoordP4uiv(n, n2, nArray);
    }

    public static void glNormalP3uiv(@NativeType(value="GLenum") int n, @NativeType(value="GLuint const *") int[] nArray) {
        GL33.glNormalP3uiv(n, nArray);
    }

    public static void glColorP3uiv(@NativeType(value="GLenum") int n, @NativeType(value="GLuint const *") int[] nArray) {
        GL33.glColorP3uiv(n, nArray);
    }

    public static void glColorP4uiv(@NativeType(value="GLenum") int n, @NativeType(value="GLuint const *") int[] nArray) {
        GL33.glColorP4uiv(n, nArray);
    }

    public static void glSecondaryColorP3uiv(@NativeType(value="GLenum") int n, @NativeType(value="GLuint const *") int[] nArray) {
        GL33.glSecondaryColorP3uiv(n, nArray);
    }

    public static void glVertexAttribP1uiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLuint const *") int[] nArray) {
        GL33C.glVertexAttribP1uiv(n, n2, bl, nArray);
    }

    public static void glVertexAttribP2uiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLuint const *") int[] nArray) {
        GL33C.glVertexAttribP2uiv(n, n2, bl, nArray);
    }

    public static void glVertexAttribP3uiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLuint const *") int[] nArray) {
        GL33C.glVertexAttribP3uiv(n, n2, bl, nArray);
    }

    public static void glVertexAttribP4uiv(@NativeType(value="GLuint") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLuint const *") int[] nArray) {
        GL33C.glVertexAttribP4uiv(n, n2, bl, nArray);
    }

    static {
        GL.initialize();
    }
}

