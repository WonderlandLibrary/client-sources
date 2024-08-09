/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.IntBuffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL30C;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class ARBFramebufferObject {
    public static final int GL_FRAMEBUFFER = 36160;
    public static final int GL_READ_FRAMEBUFFER = 36008;
    public static final int GL_DRAW_FRAMEBUFFER = 36009;
    public static final int GL_RENDERBUFFER = 36161;
    public static final int GL_STENCIL_INDEX1 = 36166;
    public static final int GL_STENCIL_INDEX4 = 36167;
    public static final int GL_STENCIL_INDEX8 = 36168;
    public static final int GL_STENCIL_INDEX16 = 36169;
    public static final int GL_RENDERBUFFER_WIDTH = 36162;
    public static final int GL_RENDERBUFFER_HEIGHT = 36163;
    public static final int GL_RENDERBUFFER_INTERNAL_FORMAT = 36164;
    public static final int GL_RENDERBUFFER_RED_SIZE = 36176;
    public static final int GL_RENDERBUFFER_GREEN_SIZE = 36177;
    public static final int GL_RENDERBUFFER_BLUE_SIZE = 36178;
    public static final int GL_RENDERBUFFER_ALPHA_SIZE = 36179;
    public static final int GL_RENDERBUFFER_DEPTH_SIZE = 36180;
    public static final int GL_RENDERBUFFER_STENCIL_SIZE = 36181;
    public static final int GL_RENDERBUFFER_SAMPLES = 36011;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_OBJECT_TYPE = 36048;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_OBJECT_NAME = 36049;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_LEVEL = 36050;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_CUBE_MAP_FACE = 36051;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_LAYER = 36052;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_COLOR_ENCODING = 33296;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_COMPONENT_TYPE = 33297;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_RED_SIZE = 33298;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_GREEN_SIZE = 33299;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_BLUE_SIZE = 33300;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_ALPHA_SIZE = 33301;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_DEPTH_SIZE = 33302;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_STENCIL_SIZE = 33303;
    public static final int GL_UNSIGNED_NORMALIZED = 35863;
    public static final int GL_FRAMEBUFFER_DEFAULT = 33304;
    public static final int GL_INDEX = 33314;
    public static final int GL_COLOR_ATTACHMENT0 = 36064;
    public static final int GL_COLOR_ATTACHMENT1 = 36065;
    public static final int GL_COLOR_ATTACHMENT2 = 36066;
    public static final int GL_COLOR_ATTACHMENT3 = 36067;
    public static final int GL_COLOR_ATTACHMENT4 = 36068;
    public static final int GL_COLOR_ATTACHMENT5 = 36069;
    public static final int GL_COLOR_ATTACHMENT6 = 36070;
    public static final int GL_COLOR_ATTACHMENT7 = 36071;
    public static final int GL_COLOR_ATTACHMENT8 = 36072;
    public static final int GL_COLOR_ATTACHMENT9 = 36073;
    public static final int GL_COLOR_ATTACHMENT10 = 36074;
    public static final int GL_COLOR_ATTACHMENT11 = 36075;
    public static final int GL_COLOR_ATTACHMENT12 = 36076;
    public static final int GL_COLOR_ATTACHMENT13 = 36077;
    public static final int GL_COLOR_ATTACHMENT14 = 36078;
    public static final int GL_COLOR_ATTACHMENT15 = 36079;
    public static final int GL_DEPTH_ATTACHMENT = 36096;
    public static final int GL_STENCIL_ATTACHMENT = 36128;
    public static final int GL_DEPTH_STENCIL_ATTACHMENT = 33306;
    public static final int GL_MAX_SAMPLES = 36183;
    public static final int GL_FRAMEBUFFER_COMPLETE = 36053;
    public static final int GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT = 36054;
    public static final int GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT = 36055;
    public static final int GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER = 36059;
    public static final int GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER = 36060;
    public static final int GL_FRAMEBUFFER_UNSUPPORTED = 36061;
    public static final int GL_FRAMEBUFFER_INCOMPLETE_MULTISAMPLE = 36182;
    public static final int GL_FRAMEBUFFER_UNDEFINED = 33305;
    public static final int GL_FRAMEBUFFER_BINDING = 36006;
    public static final int GL_DRAW_FRAMEBUFFER_BINDING = 36006;
    public static final int GL_READ_FRAMEBUFFER_BINDING = 36010;
    public static final int GL_RENDERBUFFER_BINDING = 36007;
    public static final int GL_MAX_COLOR_ATTACHMENTS = 36063;
    public static final int GL_MAX_RENDERBUFFER_SIZE = 34024;
    public static final int GL_INVALID_FRAMEBUFFER_OPERATION = 1286;
    public static final int GL_DEPTH_STENCIL = 34041;
    public static final int GL_UNSIGNED_INT_24_8 = 34042;
    public static final int GL_DEPTH24_STENCIL8 = 35056;
    public static final int GL_TEXTURE_STENCIL_SIZE = 35057;

    protected ARBFramebufferObject() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glIsRenderbuffer, gLCapabilities.glBindRenderbuffer, gLCapabilities.glDeleteRenderbuffers, gLCapabilities.glGenRenderbuffers, gLCapabilities.glRenderbufferStorage, gLCapabilities.glRenderbufferStorageMultisample, gLCapabilities.glGetRenderbufferParameteriv, gLCapabilities.glIsFramebuffer, gLCapabilities.glBindFramebuffer, gLCapabilities.glDeleteFramebuffers, gLCapabilities.glGenFramebuffers, gLCapabilities.glCheckFramebufferStatus, gLCapabilities.glFramebufferTexture1D, gLCapabilities.glFramebufferTexture2D, gLCapabilities.glFramebufferTexture3D, gLCapabilities.glFramebufferTextureLayer, gLCapabilities.glFramebufferRenderbuffer, gLCapabilities.glGetFramebufferAttachmentParameteriv, gLCapabilities.glBlitFramebuffer, gLCapabilities.glGenerateMipmap);
    }

    @NativeType(value="GLboolean")
    public static boolean glIsRenderbuffer(@NativeType(value="GLuint") int n) {
        return GL30C.glIsRenderbuffer(n);
    }

    public static void glBindRenderbuffer(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2) {
        GL30C.glBindRenderbuffer(n, n2);
    }

    public static void nglDeleteRenderbuffers(int n, long l) {
        GL30C.nglDeleteRenderbuffers(n, l);
    }

    public static void glDeleteRenderbuffers(@NativeType(value="GLuint const *") IntBuffer intBuffer) {
        GL30C.glDeleteRenderbuffers(intBuffer);
    }

    public static void glDeleteRenderbuffers(@NativeType(value="GLuint const *") int n) {
        GL30C.glDeleteRenderbuffers(n);
    }

    public static void nglGenRenderbuffers(int n, long l) {
        GL30C.nglGenRenderbuffers(n, l);
    }

    public static void glGenRenderbuffers(@NativeType(value="GLuint *") IntBuffer intBuffer) {
        GL30C.glGenRenderbuffers(intBuffer);
    }

    @NativeType(value="void")
    public static int glGenRenderbuffers() {
        return GL30C.glGenRenderbuffers();
    }

    public static void glRenderbufferStorage(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLsizei") int n3, @NativeType(value="GLsizei") int n4) {
        GL30C.glRenderbufferStorage(n, n2, n3, n4);
    }

    public static void glRenderbufferStorageMultisample(@NativeType(value="GLenum") int n, @NativeType(value="GLsizei") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLsizei") int n4, @NativeType(value="GLsizei") int n5) {
        GL30C.glRenderbufferStorageMultisample(n, n2, n3, n4, n5);
    }

    public static void nglGetRenderbufferParameteriv(int n, int n2, long l) {
        GL30C.nglGetRenderbufferParameteriv(n, n2, l);
    }

    public static void glGetRenderbufferParameteriv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") IntBuffer intBuffer) {
        GL30C.glGetRenderbufferParameteriv(n, n2, intBuffer);
    }

    @NativeType(value="void")
    public static int glGetRenderbufferParameteri(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2) {
        return GL30C.glGetRenderbufferParameteri(n, n2);
    }

    @NativeType(value="GLboolean")
    public static boolean glIsFramebuffer(@NativeType(value="GLuint") int n) {
        return GL30C.glIsFramebuffer(n);
    }

    public static void glBindFramebuffer(@NativeType(value="GLenum") int n, @NativeType(value="GLuint") int n2) {
        GL30C.glBindFramebuffer(n, n2);
    }

    public static void nglDeleteFramebuffers(int n, long l) {
        GL30C.nglDeleteFramebuffers(n, l);
    }

    public static void glDeleteFramebuffers(@NativeType(value="GLuint const *") IntBuffer intBuffer) {
        GL30C.glDeleteFramebuffers(intBuffer);
    }

    public static void glDeleteFramebuffers(@NativeType(value="GLuint const *") int n) {
        GL30C.glDeleteFramebuffers(n);
    }

    public static void nglGenFramebuffers(int n, long l) {
        GL30C.nglGenFramebuffers(n, l);
    }

    public static void glGenFramebuffers(@NativeType(value="GLuint *") IntBuffer intBuffer) {
        GL30C.glGenFramebuffers(intBuffer);
    }

    @NativeType(value="void")
    public static int glGenFramebuffers() {
        return GL30C.glGenFramebuffers();
    }

    @NativeType(value="GLenum")
    public static int glCheckFramebufferStatus(@NativeType(value="GLenum") int n) {
        return GL30C.glCheckFramebufferStatus(n);
    }

    public static void glFramebufferTexture1D(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLuint") int n4, @NativeType(value="GLint") int n5) {
        GL30C.glFramebufferTexture1D(n, n2, n3, n4, n5);
    }

    public static void glFramebufferTexture2D(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLuint") int n4, @NativeType(value="GLint") int n5) {
        GL30C.glFramebufferTexture2D(n, n2, n3, n4, n5);
    }

    public static void glFramebufferTexture3D(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLuint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLint") int n6) {
        GL30C.glFramebufferTexture3D(n, n2, n3, n4, n5, n6);
    }

    public static void glFramebufferTextureLayer(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLuint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5) {
        GL30C.glFramebufferTextureLayer(n, n2, n3, n4, n5);
    }

    public static void glFramebufferRenderbuffer(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLuint") int n4) {
        GL30C.glFramebufferRenderbuffer(n, n2, n3, n4);
    }

    public static void nglGetFramebufferAttachmentParameteriv(int n, int n2, int n3, long l) {
        GL30C.nglGetFramebufferAttachmentParameteriv(n, n2, n3, l);
    }

    public static void glGetFramebufferAttachmentParameteriv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLint *") IntBuffer intBuffer) {
        GL30C.glGetFramebufferAttachmentParameteriv(n, n2, n3, intBuffer);
    }

    @NativeType(value="void")
    public static int glGetFramebufferAttachmentParameteri(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3) {
        return GL30C.glGetFramebufferAttachmentParameteri(n, n2, n3);
    }

    public static void glBlitFramebuffer(@NativeType(value="GLint") int n, @NativeType(value="GLint") int n2, @NativeType(value="GLint") int n3, @NativeType(value="GLint") int n4, @NativeType(value="GLint") int n5, @NativeType(value="GLint") int n6, @NativeType(value="GLint") int n7, @NativeType(value="GLint") int n8, @NativeType(value="GLbitfield") int n9, @NativeType(value="GLenum") int n10) {
        GL30C.glBlitFramebuffer(n, n2, n3, n4, n5, n6, n7, n8, n9, n10);
    }

    public static void glGenerateMipmap(@NativeType(value="GLenum") int n) {
        GL30C.glGenerateMipmap(n);
    }

    public static void glDeleteRenderbuffers(@NativeType(value="GLuint const *") int[] nArray) {
        GL30C.glDeleteRenderbuffers(nArray);
    }

    public static void glGenRenderbuffers(@NativeType(value="GLuint *") int[] nArray) {
        GL30C.glGenRenderbuffers(nArray);
    }

    public static void glGetRenderbufferParameteriv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") int[] nArray) {
        GL30C.glGetRenderbufferParameteriv(n, n2, nArray);
    }

    public static void glDeleteFramebuffers(@NativeType(value="GLuint const *") int[] nArray) {
        GL30C.glDeleteFramebuffers(nArray);
    }

    public static void glGenFramebuffers(@NativeType(value="GLuint *") int[] nArray) {
        GL30C.glGenFramebuffers(nArray);
    }

    public static void glGetFramebufferAttachmentParameteriv(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLint *") int[] nArray) {
        GL30C.glGetFramebufferAttachmentParameteriv(n, n2, n3, nArray);
    }

    static {
        GL.initialize();
    }
}

