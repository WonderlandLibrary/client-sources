/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.Buffer;
import java.nio.IntBuffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class EXTFramebufferObject {
    public static final int GL_FRAMEBUFFER_EXT = 36160;
    public static final int GL_RENDERBUFFER_EXT = 36161;
    public static final int GL_STENCIL_INDEX1_EXT = 36166;
    public static final int GL_STENCIL_INDEX4_EXT = 36167;
    public static final int GL_STENCIL_INDEX8_EXT = 36168;
    public static final int GL_STENCIL_INDEX16_EXT = 36169;
    public static final int GL_RENDERBUFFER_WIDTH_EXT = 36162;
    public static final int GL_RENDERBUFFER_HEIGHT_EXT = 36163;
    public static final int GL_RENDERBUFFER_INTERNAL_FORMAT_EXT = 36164;
    public static final int GL_RENDERBUFFER_RED_SIZE_EXT = 36176;
    public static final int GL_RENDERBUFFER_GREEN_SIZE_EXT = 36177;
    public static final int GL_RENDERBUFFER_BLUE_SIZE_EXT = 36178;
    public static final int GL_RENDERBUFFER_ALPHA_SIZE_EXT = 36179;
    public static final int GL_RENDERBUFFER_DEPTH_SIZE_EXT = 36180;
    public static final int GL_RENDERBUFFER_STENCIL_SIZE_EXT = 36181;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_OBJECT_TYPE_EXT = 36048;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_OBJECT_NAME_EXT = 36049;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_LEVEL_EXT = 36050;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_CUBE_MAP_FACE_EXT = 36051;
    public static final int GL_FRAMEBUFFER_ATTACHMENT_TEXTURE_3D_ZOFFSET_EXT = 36052;
    public static final int GL_COLOR_ATTACHMENT0_EXT = 36064;
    public static final int GL_COLOR_ATTACHMENT1_EXT = 36065;
    public static final int GL_COLOR_ATTACHMENT2_EXT = 36066;
    public static final int GL_COLOR_ATTACHMENT3_EXT = 36067;
    public static final int GL_COLOR_ATTACHMENT4_EXT = 36068;
    public static final int GL_COLOR_ATTACHMENT5_EXT = 36069;
    public static final int GL_COLOR_ATTACHMENT6_EXT = 36070;
    public static final int GL_COLOR_ATTACHMENT7_EXT = 36071;
    public static final int GL_COLOR_ATTACHMENT8_EXT = 36072;
    public static final int GL_COLOR_ATTACHMENT9_EXT = 36073;
    public static final int GL_COLOR_ATTACHMENT10_EXT = 36074;
    public static final int GL_COLOR_ATTACHMENT11_EXT = 36075;
    public static final int GL_COLOR_ATTACHMENT12_EXT = 36076;
    public static final int GL_COLOR_ATTACHMENT13_EXT = 36077;
    public static final int GL_COLOR_ATTACHMENT14_EXT = 36078;
    public static final int GL_COLOR_ATTACHMENT15_EXT = 36079;
    public static final int GL_DEPTH_ATTACHMENT_EXT = 36096;
    public static final int GL_STENCIL_ATTACHMENT_EXT = 36128;
    public static final int GL_FRAMEBUFFER_COMPLETE_EXT = 36053;
    public static final int GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT_EXT = 36054;
    public static final int GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT_EXT = 36055;
    public static final int GL_FRAMEBUFFER_INCOMPLETE_DIMENSIONS_EXT = 36057;
    public static final int GL_FRAMEBUFFER_INCOMPLETE_FORMATS_EXT = 36058;
    public static final int GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER_EXT = 36059;
    public static final int GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER_EXT = 36060;
    public static final int GL_FRAMEBUFFER_UNSUPPORTED_EXT = 36061;
    public static final int GL_FRAMEBUFFER_BINDING_EXT = 36006;
    public static final int GL_RENDERBUFFER_BINDING_EXT = 36007;
    public static final int GL_MAX_COLOR_ATTACHMENTS_EXT = 36063;
    public static final int GL_MAX_RENDERBUFFER_SIZE_EXT = 34024;
    public static final int GL_INVALID_FRAMEBUFFER_OPERATION_EXT = 1286;

    protected EXTFramebufferObject() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glIsRenderbufferEXT, gLCapabilities.glBindRenderbufferEXT, gLCapabilities.glDeleteRenderbuffersEXT, gLCapabilities.glGenRenderbuffersEXT, gLCapabilities.glRenderbufferStorageEXT, gLCapabilities.glGetRenderbufferParameterivEXT, gLCapabilities.glIsFramebufferEXT, gLCapabilities.glBindFramebufferEXT, gLCapabilities.glDeleteFramebuffersEXT, gLCapabilities.glGenFramebuffersEXT, gLCapabilities.glCheckFramebufferStatusEXT, gLCapabilities.glFramebufferTexture1DEXT, gLCapabilities.glFramebufferTexture2DEXT, gLCapabilities.glFramebufferTexture3DEXT, gLCapabilities.glFramebufferRenderbufferEXT, gLCapabilities.glGetFramebufferAttachmentParameterivEXT, gLCapabilities.glGenerateMipmapEXT);
    }

    @NativeType(value="GLboolean")
    public static native boolean glIsRenderbufferEXT(@NativeType(value="GLuint") int var0);

    public static native void glBindRenderbufferEXT(@NativeType(value="GLenum") int var0, @NativeType(value="GLuint") int var1);

    public static native void nglDeleteRenderbuffersEXT(int var0, long var1);

    public static void glDeleteRenderbuffersEXT(@NativeType(value="GLuint const *") IntBuffer intBuffer) {
        EXTFramebufferObject.nglDeleteRenderbuffersEXT(intBuffer.remaining(), MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void glDeleteRenderbuffersEXT(@NativeType(value="GLuint const *") int n) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n2 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.ints(n);
            EXTFramebufferObject.nglDeleteRenderbuffersEXT(1, MemoryUtil.memAddress(intBuffer));
        } finally {
            memoryStack.setPointer(n2);
        }
    }

    public static native void nglGenRenderbuffersEXT(int var0, long var1);

    public static void glGenRenderbuffersEXT(@NativeType(value="GLuint *") IntBuffer intBuffer) {
        EXTFramebufferObject.nglGenRenderbuffersEXT(intBuffer.remaining(), MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glGenRenderbuffersEXT() {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            EXTFramebufferObject.nglGenRenderbuffersEXT(1, MemoryUtil.memAddress(intBuffer));
            int n2 = intBuffer.get(0);
            return n2;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    public static native void glRenderbufferStorageEXT(@NativeType(value="GLenum") int var0, @NativeType(value="GLenum") int var1, @NativeType(value="GLsizei") int var2, @NativeType(value="GLsizei") int var3);

    public static native void nglGetRenderbufferParameterivEXT(int var0, int var1, long var2);

    public static void glGetRenderbufferParameterivEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        EXTFramebufferObject.nglGetRenderbufferParameterivEXT(n, n2, MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glGetRenderbufferParameteriEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n3 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            EXTFramebufferObject.nglGetRenderbufferParameterivEXT(n, n2, MemoryUtil.memAddress(intBuffer));
            int n4 = intBuffer.get(0);
            return n4;
        } finally {
            memoryStack.setPointer(n3);
        }
    }

    @NativeType(value="GLboolean")
    public static native boolean glIsFramebufferEXT(@NativeType(value="GLuint") int var0);

    public static native void glBindFramebufferEXT(@NativeType(value="GLenum") int var0, @NativeType(value="GLuint") int var1);

    public static native void nglDeleteFramebuffersEXT(int var0, long var1);

    public static void glDeleteFramebuffersEXT(@NativeType(value="GLuint const *") IntBuffer intBuffer) {
        EXTFramebufferObject.nglDeleteFramebuffersEXT(intBuffer.remaining(), MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void glDeleteFramebuffersEXT(@NativeType(value="GLuint const *") int n) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n2 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.ints(n);
            EXTFramebufferObject.nglDeleteFramebuffersEXT(1, MemoryUtil.memAddress(intBuffer));
        } finally {
            memoryStack.setPointer(n2);
        }
    }

    public static native void nglGenFramebuffersEXT(int var0, long var1);

    public static void glGenFramebuffersEXT(@NativeType(value="GLuint *") IntBuffer intBuffer) {
        EXTFramebufferObject.nglGenFramebuffersEXT(intBuffer.remaining(), MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glGenFramebuffersEXT() {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            EXTFramebufferObject.nglGenFramebuffersEXT(1, MemoryUtil.memAddress(intBuffer));
            int n2 = intBuffer.get(0);
            return n2;
        } finally {
            memoryStack.setPointer(n);
        }
    }

    @NativeType(value="GLenum")
    public static native int glCheckFramebufferStatusEXT(@NativeType(value="GLenum") int var0);

    public static native void glFramebufferTexture1DEXT(@NativeType(value="GLenum") int var0, @NativeType(value="GLenum") int var1, @NativeType(value="GLenum") int var2, @NativeType(value="GLuint") int var3, @NativeType(value="GLint") int var4);

    public static native void glFramebufferTexture2DEXT(@NativeType(value="GLenum") int var0, @NativeType(value="GLenum") int var1, @NativeType(value="GLenum") int var2, @NativeType(value="GLuint") int var3, @NativeType(value="GLint") int var4);

    public static native void glFramebufferTexture3DEXT(@NativeType(value="GLenum") int var0, @NativeType(value="GLenum") int var1, @NativeType(value="GLenum") int var2, @NativeType(value="GLuint") int var3, @NativeType(value="GLint") int var4, @NativeType(value="GLint") int var5);

    public static native void glFramebufferRenderbufferEXT(@NativeType(value="GLenum") int var0, @NativeType(value="GLenum") int var1, @NativeType(value="GLenum") int var2, @NativeType(value="GLuint") int var3);

    public static native void nglGetFramebufferAttachmentParameterivEXT(int var0, int var1, int var2, long var3);

    public static void glGetFramebufferAttachmentParameterivEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLint *") IntBuffer intBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)intBuffer, 1);
        }
        EXTFramebufferObject.nglGetFramebufferAttachmentParameterivEXT(n, n2, n3, MemoryUtil.memAddress(intBuffer));
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @NativeType(value="void")
    public static int glGetFramebufferAttachmentParameteriEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3) {
        MemoryStack memoryStack = MemoryStack.stackGet();
        int n4 = memoryStack.getPointer();
        try {
            IntBuffer intBuffer = memoryStack.callocInt(1);
            EXTFramebufferObject.nglGetFramebufferAttachmentParameterivEXT(n, n2, n3, MemoryUtil.memAddress(intBuffer));
            int n5 = intBuffer.get(0);
            return n5;
        } finally {
            memoryStack.setPointer(n4);
        }
    }

    public static native void glGenerateMipmapEXT(@NativeType(value="GLenum") int var0);

    public static void glDeleteRenderbuffersEXT(@NativeType(value="GLuint const *") int[] nArray) {
        long l = GL.getICD().glDeleteRenderbuffersEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(nArray.length, nArray, l);
    }

    public static void glGenRenderbuffersEXT(@NativeType(value="GLuint *") int[] nArray) {
        long l = GL.getICD().glGenRenderbuffersEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(nArray.length, nArray, l);
    }

    public static void glGetRenderbufferParameterivEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLint *") int[] nArray) {
        long l = GL.getICD().glGetRenderbufferParameterivEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.callPV(n, n2, nArray, l);
    }

    public static void glDeleteFramebuffersEXT(@NativeType(value="GLuint const *") int[] nArray) {
        long l = GL.getICD().glDeleteFramebuffersEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(nArray.length, nArray, l);
    }

    public static void glGenFramebuffersEXT(@NativeType(value="GLuint *") int[] nArray) {
        long l = GL.getICD().glGenFramebuffersEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(nArray.length, nArray, l);
    }

    public static void glGetFramebufferAttachmentParameterivEXT(@NativeType(value="GLenum") int n, @NativeType(value="GLenum") int n2, @NativeType(value="GLenum") int n3, @NativeType(value="GLint *") int[] nArray) {
        long l = GL.getICD().glGetFramebufferAttachmentParameterivEXT;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(nArray, 1);
        }
        JNI.callPV(n, n2, n3, nArray, l);
    }

    static {
        GL.initialize();
    }
}

