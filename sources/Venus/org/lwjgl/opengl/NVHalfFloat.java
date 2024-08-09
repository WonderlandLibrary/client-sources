/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.Buffer;
import java.nio.ShortBuffer;
import java.util.Set;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.JNI;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class NVHalfFloat {
    public static final int GL_HALF_FLOAT_NV = 5131;

    protected NVHalfFloat() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities, Set<String> set) {
        return Checks.checkFunctions(gLCapabilities.glVertex2hNV, gLCapabilities.glVertex2hvNV, gLCapabilities.glVertex3hNV, gLCapabilities.glVertex3hvNV, gLCapabilities.glVertex4hNV, gLCapabilities.glVertex4hvNV, gLCapabilities.glNormal3hNV, gLCapabilities.glNormal3hvNV, gLCapabilities.glColor3hNV, gLCapabilities.glColor3hvNV, gLCapabilities.glColor4hNV, gLCapabilities.glColor4hvNV, gLCapabilities.glTexCoord1hNV, gLCapabilities.glTexCoord1hvNV, gLCapabilities.glTexCoord2hNV, gLCapabilities.glTexCoord2hvNV, gLCapabilities.glTexCoord3hNV, gLCapabilities.glTexCoord3hvNV, gLCapabilities.glTexCoord4hNV, gLCapabilities.glTexCoord4hvNV, gLCapabilities.glMultiTexCoord1hNV, gLCapabilities.glMultiTexCoord1hvNV, gLCapabilities.glMultiTexCoord2hNV, gLCapabilities.glMultiTexCoord2hvNV, gLCapabilities.glMultiTexCoord3hNV, gLCapabilities.glMultiTexCoord3hvNV, gLCapabilities.glMultiTexCoord4hNV, gLCapabilities.glMultiTexCoord4hvNV, set.contains("GL_EXT_fog_coord") ? gLCapabilities.glFogCoordhNV : -1L, set.contains("GL_EXT_fog_coord") ? gLCapabilities.glFogCoordhvNV : -1L, set.contains("GL_EXT_secondary_color") ? gLCapabilities.glSecondaryColor3hNV : -1L, set.contains("GL_EXT_secondary_color") ? gLCapabilities.glSecondaryColor3hvNV : -1L, set.contains("GL_EXT_vertex_weighting") ? gLCapabilities.glVertexWeighthNV : -1L, set.contains("GL_EXT_vertex_weighting") ? gLCapabilities.glVertexWeighthvNV : -1L, set.contains("GL_NV_vertex_program") ? gLCapabilities.glVertexAttrib1hNV : -1L, set.contains("GL_NV_vertex_program") ? gLCapabilities.glVertexAttrib1hvNV : -1L, set.contains("GL_NV_vertex_program") ? gLCapabilities.glVertexAttrib2hNV : -1L, set.contains("GL_NV_vertex_program") ? gLCapabilities.glVertexAttrib2hvNV : -1L, set.contains("GL_NV_vertex_program") ? gLCapabilities.glVertexAttrib3hNV : -1L, set.contains("GL_NV_vertex_program") ? gLCapabilities.glVertexAttrib3hvNV : -1L, set.contains("GL_NV_vertex_program") ? gLCapabilities.glVertexAttrib4hNV : -1L, set.contains("GL_NV_vertex_program") ? gLCapabilities.glVertexAttrib4hvNV : -1L, set.contains("GL_NV_vertex_program") ? gLCapabilities.glVertexAttribs1hvNV : -1L, set.contains("GL_NV_vertex_program") ? gLCapabilities.glVertexAttribs2hvNV : -1L, set.contains("GL_NV_vertex_program") ? gLCapabilities.glVertexAttribs3hvNV : -1L, set.contains("GL_NV_vertex_program") ? gLCapabilities.glVertexAttribs4hvNV : -1L);
    }

    public static native void glVertex2hNV(@NativeType(value="GLhalfNV") short var0, @NativeType(value="GLhalfNV") short var1);

    public static native void nglVertex2hvNV(long var0);

    public static void glVertex2hvNV(@NativeType(value="GLhalfNV const *") ShortBuffer shortBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)shortBuffer, 2);
        }
        NVHalfFloat.nglVertex2hvNV(MemoryUtil.memAddress(shortBuffer));
    }

    public static native void glVertex3hNV(@NativeType(value="GLhalfNV") short var0, @NativeType(value="GLhalfNV") short var1, @NativeType(value="GLhalfNV") short var2);

    public static native void nglVertex3hvNV(long var0);

    public static void glVertex3hvNV(@NativeType(value="GLhalfNV const *") ShortBuffer shortBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)shortBuffer, 3);
        }
        NVHalfFloat.nglVertex3hvNV(MemoryUtil.memAddress(shortBuffer));
    }

    public static native void glVertex4hNV(@NativeType(value="GLhalfNV") short var0, @NativeType(value="GLhalfNV") short var1, @NativeType(value="GLhalfNV") short var2, @NativeType(value="GLhalfNV") short var3);

    public static native void nglVertex4hvNV(long var0);

    public static void glVertex4hvNV(@NativeType(value="GLhalfNV const *") ShortBuffer shortBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)shortBuffer, 4);
        }
        NVHalfFloat.nglVertex4hvNV(MemoryUtil.memAddress(shortBuffer));
    }

    public static native void glNormal3hNV(@NativeType(value="GLhalfNV") short var0, @NativeType(value="GLhalfNV") short var1, @NativeType(value="GLhalfNV") short var2);

    public static native void nglNormal3hvNV(long var0);

    public static void glNormal3hvNV(@NativeType(value="GLhalfNV const *") ShortBuffer shortBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)shortBuffer, 3);
        }
        NVHalfFloat.nglNormal3hvNV(MemoryUtil.memAddress(shortBuffer));
    }

    public static native void glColor3hNV(@NativeType(value="GLhalfNV") short var0, @NativeType(value="GLhalfNV") short var1, @NativeType(value="GLhalfNV") short var2);

    public static native void nglColor3hvNV(long var0);

    public static void glColor3hvNV(@NativeType(value="GLhalfNV const *") ShortBuffer shortBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)shortBuffer, 3);
        }
        NVHalfFloat.nglColor3hvNV(MemoryUtil.memAddress(shortBuffer));
    }

    public static native void glColor4hNV(@NativeType(value="GLhalfNV") short var0, @NativeType(value="GLhalfNV") short var1, @NativeType(value="GLhalfNV") short var2, @NativeType(value="GLhalfNV") short var3);

    public static native void nglColor4hvNV(long var0);

    public static void glColor4hvNV(@NativeType(value="GLhalfNV const *") ShortBuffer shortBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)shortBuffer, 4);
        }
        NVHalfFloat.nglColor4hvNV(MemoryUtil.memAddress(shortBuffer));
    }

    public static native void glTexCoord1hNV(@NativeType(value="GLhalfNV") short var0);

    public static native void nglTexCoord1hvNV(long var0);

    public static void glTexCoord1hvNV(@NativeType(value="GLhalfNV const *") ShortBuffer shortBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)shortBuffer, 1);
        }
        NVHalfFloat.nglTexCoord1hvNV(MemoryUtil.memAddress(shortBuffer));
    }

    public static native void glTexCoord2hNV(@NativeType(value="GLhalfNV") short var0, @NativeType(value="GLhalfNV") short var1);

    public static native void nglTexCoord2hvNV(long var0);

    public static void glTexCoord2hvNV(@NativeType(value="GLhalfNV const *") ShortBuffer shortBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)shortBuffer, 2);
        }
        NVHalfFloat.nglTexCoord2hvNV(MemoryUtil.memAddress(shortBuffer));
    }

    public static native void glTexCoord3hNV(@NativeType(value="GLhalfNV") short var0, @NativeType(value="GLhalfNV") short var1, @NativeType(value="GLhalfNV") short var2);

    public static native void nglTexCoord3hvNV(long var0);

    public static void glTexCoord3hvNV(@NativeType(value="GLhalfNV const *") ShortBuffer shortBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)shortBuffer, 3);
        }
        NVHalfFloat.nglTexCoord3hvNV(MemoryUtil.memAddress(shortBuffer));
    }

    public static native void glTexCoord4hNV(@NativeType(value="GLhalfNV") short var0, @NativeType(value="GLhalfNV") short var1, @NativeType(value="GLhalfNV") short var2, @NativeType(value="GLhalfNV") short var3);

    public static native void nglTexCoord4hvNV(long var0);

    public static void glTexCoord4hvNV(@NativeType(value="GLhalfNV const *") ShortBuffer shortBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)shortBuffer, 4);
        }
        NVHalfFloat.nglTexCoord4hvNV(MemoryUtil.memAddress(shortBuffer));
    }

    public static native void glMultiTexCoord1hNV(@NativeType(value="GLenum") int var0, @NativeType(value="GLhalfNV") short var1);

    public static native void nglMultiTexCoord1hvNV(int var0, long var1);

    public static void glMultiTexCoord1hvNV(@NativeType(value="GLenum") int n, @NativeType(value="GLhalfNV const *") ShortBuffer shortBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)shortBuffer, 1);
        }
        NVHalfFloat.nglMultiTexCoord1hvNV(n, MemoryUtil.memAddress(shortBuffer));
    }

    public static native void glMultiTexCoord2hNV(@NativeType(value="GLenum") int var0, @NativeType(value="GLhalfNV") short var1, @NativeType(value="GLhalfNV") short var2);

    public static native void nglMultiTexCoord2hvNV(int var0, long var1);

    public static void glMultiTexCoord2hvNV(@NativeType(value="GLenum") int n, @NativeType(value="GLhalfNV const *") ShortBuffer shortBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)shortBuffer, 2);
        }
        NVHalfFloat.nglMultiTexCoord2hvNV(n, MemoryUtil.memAddress(shortBuffer));
    }

    public static native void glMultiTexCoord3hNV(@NativeType(value="GLenum") int var0, @NativeType(value="GLhalfNV") short var1, @NativeType(value="GLhalfNV") short var2, @NativeType(value="GLhalfNV") short var3);

    public static native void nglMultiTexCoord3hvNV(int var0, long var1);

    public static void glMultiTexCoord3hvNV(@NativeType(value="GLenum") int n, @NativeType(value="GLhalfNV const *") ShortBuffer shortBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)shortBuffer, 3);
        }
        NVHalfFloat.nglMultiTexCoord3hvNV(n, MemoryUtil.memAddress(shortBuffer));
    }

    public static native void glMultiTexCoord4hNV(@NativeType(value="GLenum") int var0, @NativeType(value="GLhalfNV") short var1, @NativeType(value="GLhalfNV") short var2, @NativeType(value="GLhalfNV") short var3, @NativeType(value="GLhalfNV") short var4);

    public static native void nglMultiTexCoord4hvNV(int var0, long var1);

    public static void glMultiTexCoord4hvNV(@NativeType(value="GLenum") int n, @NativeType(value="GLhalfNV const *") ShortBuffer shortBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)shortBuffer, 4);
        }
        NVHalfFloat.nglMultiTexCoord4hvNV(n, MemoryUtil.memAddress(shortBuffer));
    }

    public static native void glFogCoordhNV(@NativeType(value="GLhalfNV") short var0);

    public static native void nglFogCoordhvNV(long var0);

    public static void glFogCoordhvNV(@NativeType(value="GLhalfNV const *") ShortBuffer shortBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)shortBuffer, 1);
        }
        NVHalfFloat.nglFogCoordhvNV(MemoryUtil.memAddress(shortBuffer));
    }

    public static native void glSecondaryColor3hNV(@NativeType(value="GLhalfNV") short var0, @NativeType(value="GLhalfNV") short var1, @NativeType(value="GLhalfNV") short var2);

    public static native void nglSecondaryColor3hvNV(long var0);

    public static void glSecondaryColor3hvNV(@NativeType(value="GLhalfNV const *") ShortBuffer shortBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)shortBuffer, 3);
        }
        NVHalfFloat.nglSecondaryColor3hvNV(MemoryUtil.memAddress(shortBuffer));
    }

    public static native void glVertexWeighthNV(@NativeType(value="GLhalfNV") short var0);

    public static native void nglVertexWeighthvNV(long var0);

    public static void glVertexWeighthvNV(@NativeType(value="GLhalfNV const *") ShortBuffer shortBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)shortBuffer, 1);
        }
        NVHalfFloat.nglVertexWeighthvNV(MemoryUtil.memAddress(shortBuffer));
    }

    public static native void glVertexAttrib1hNV(@NativeType(value="GLuint") int var0, @NativeType(value="GLhalfNV") short var1);

    public static native void nglVertexAttrib1hvNV(int var0, long var1);

    public static void glVertexAttrib1hvNV(@NativeType(value="GLuint") int n, @NativeType(value="GLhalfNV const *") ShortBuffer shortBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)shortBuffer, 1);
        }
        NVHalfFloat.nglVertexAttrib1hvNV(n, MemoryUtil.memAddress(shortBuffer));
    }

    public static native void glVertexAttrib2hNV(@NativeType(value="GLuint") int var0, @NativeType(value="GLhalfNV") short var1, @NativeType(value="GLhalfNV") short var2);

    public static native void nglVertexAttrib2hvNV(int var0, long var1);

    public static void glVertexAttrib2hvNV(@NativeType(value="GLuint") int n, @NativeType(value="GLhalfNV const *") ShortBuffer shortBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)shortBuffer, 2);
        }
        NVHalfFloat.nglVertexAttrib2hvNV(n, MemoryUtil.memAddress(shortBuffer));
    }

    public static native void glVertexAttrib3hNV(@NativeType(value="GLuint") int var0, @NativeType(value="GLhalfNV") short var1, @NativeType(value="GLhalfNV") short var2, @NativeType(value="GLhalfNV") short var3);

    public static native void nglVertexAttrib3hvNV(int var0, long var1);

    public static void glVertexAttrib3hvNV(@NativeType(value="GLuint") int n, @NativeType(value="GLhalfNV const *") ShortBuffer shortBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)shortBuffer, 3);
        }
        NVHalfFloat.nglVertexAttrib3hvNV(n, MemoryUtil.memAddress(shortBuffer));
    }

    public static native void glVertexAttrib4hNV(@NativeType(value="GLuint") int var0, @NativeType(value="GLhalfNV") short var1, @NativeType(value="GLhalfNV") short var2, @NativeType(value="GLhalfNV") short var3, @NativeType(value="GLhalfNV") short var4);

    public static native void nglVertexAttrib4hvNV(int var0, long var1);

    public static void glVertexAttrib4hvNV(@NativeType(value="GLuint") int n, @NativeType(value="GLhalfNV const *") ShortBuffer shortBuffer) {
        if (Checks.CHECKS) {
            Checks.check((Buffer)shortBuffer, 4);
        }
        NVHalfFloat.nglVertexAttrib4hvNV(n, MemoryUtil.memAddress(shortBuffer));
    }

    public static native void nglVertexAttribs1hvNV(int var0, int var1, long var2);

    public static void glVertexAttribs1hvNV(@NativeType(value="GLuint") int n, @NativeType(value="GLhalfNV const *") ShortBuffer shortBuffer) {
        NVHalfFloat.nglVertexAttribs1hvNV(n, shortBuffer.remaining(), MemoryUtil.memAddress(shortBuffer));
    }

    public static native void nglVertexAttribs2hvNV(int var0, int var1, long var2);

    public static void glVertexAttribs2hvNV(@NativeType(value="GLuint") int n, @NativeType(value="GLhalfNV const *") ShortBuffer shortBuffer) {
        NVHalfFloat.nglVertexAttribs2hvNV(n, shortBuffer.remaining() >> 1, MemoryUtil.memAddress(shortBuffer));
    }

    public static native void nglVertexAttribs3hvNV(int var0, int var1, long var2);

    public static void glVertexAttribs3hvNV(@NativeType(value="GLuint") int n, @NativeType(value="GLhalfNV const *") ShortBuffer shortBuffer) {
        NVHalfFloat.nglVertexAttribs3hvNV(n, shortBuffer.remaining() / 3, MemoryUtil.memAddress(shortBuffer));
    }

    public static native void nglVertexAttribs4hvNV(int var0, int var1, long var2);

    public static void glVertexAttribs4hvNV(@NativeType(value="GLuint") int n, @NativeType(value="GLhalfNV const *") ShortBuffer shortBuffer) {
        NVHalfFloat.nglVertexAttribs4hvNV(n, shortBuffer.remaining() >> 2, MemoryUtil.memAddress(shortBuffer));
    }

    public static void glVertex2hvNV(@NativeType(value="GLhalfNV const *") short[] sArray) {
        long l = GL.getICD().glVertex2hvNV;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(sArray, 2);
        }
        JNI.callPV(sArray, l);
    }

    public static void glVertex3hvNV(@NativeType(value="GLhalfNV const *") short[] sArray) {
        long l = GL.getICD().glVertex3hvNV;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(sArray, 3);
        }
        JNI.callPV(sArray, l);
    }

    public static void glVertex4hvNV(@NativeType(value="GLhalfNV const *") short[] sArray) {
        long l = GL.getICD().glVertex4hvNV;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(sArray, 4);
        }
        JNI.callPV(sArray, l);
    }

    public static void glNormal3hvNV(@NativeType(value="GLhalfNV const *") short[] sArray) {
        long l = GL.getICD().glNormal3hvNV;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(sArray, 3);
        }
        JNI.callPV(sArray, l);
    }

    public static void glColor3hvNV(@NativeType(value="GLhalfNV const *") short[] sArray) {
        long l = GL.getICD().glColor3hvNV;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(sArray, 3);
        }
        JNI.callPV(sArray, l);
    }

    public static void glColor4hvNV(@NativeType(value="GLhalfNV const *") short[] sArray) {
        long l = GL.getICD().glColor4hvNV;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(sArray, 4);
        }
        JNI.callPV(sArray, l);
    }

    public static void glTexCoord1hvNV(@NativeType(value="GLhalfNV const *") short[] sArray) {
        long l = GL.getICD().glTexCoord1hvNV;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(sArray, 1);
        }
        JNI.callPV(sArray, l);
    }

    public static void glTexCoord2hvNV(@NativeType(value="GLhalfNV const *") short[] sArray) {
        long l = GL.getICD().glTexCoord2hvNV;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(sArray, 2);
        }
        JNI.callPV(sArray, l);
    }

    public static void glTexCoord3hvNV(@NativeType(value="GLhalfNV const *") short[] sArray) {
        long l = GL.getICD().glTexCoord3hvNV;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(sArray, 3);
        }
        JNI.callPV(sArray, l);
    }

    public static void glTexCoord4hvNV(@NativeType(value="GLhalfNV const *") short[] sArray) {
        long l = GL.getICD().glTexCoord4hvNV;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(sArray, 4);
        }
        JNI.callPV(sArray, l);
    }

    public static void glMultiTexCoord1hvNV(@NativeType(value="GLenum") int n, @NativeType(value="GLhalfNV const *") short[] sArray) {
        long l = GL.getICD().glMultiTexCoord1hvNV;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(sArray, 1);
        }
        JNI.callPV(n, sArray, l);
    }

    public static void glMultiTexCoord2hvNV(@NativeType(value="GLenum") int n, @NativeType(value="GLhalfNV const *") short[] sArray) {
        long l = GL.getICD().glMultiTexCoord2hvNV;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(sArray, 2);
        }
        JNI.callPV(n, sArray, l);
    }

    public static void glMultiTexCoord3hvNV(@NativeType(value="GLenum") int n, @NativeType(value="GLhalfNV const *") short[] sArray) {
        long l = GL.getICD().glMultiTexCoord3hvNV;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(sArray, 3);
        }
        JNI.callPV(n, sArray, l);
    }

    public static void glMultiTexCoord4hvNV(@NativeType(value="GLenum") int n, @NativeType(value="GLhalfNV const *") short[] sArray) {
        long l = GL.getICD().glMultiTexCoord4hvNV;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(sArray, 4);
        }
        JNI.callPV(n, sArray, l);
    }

    public static void glFogCoordhvNV(@NativeType(value="GLhalfNV const *") short[] sArray) {
        long l = GL.getICD().glFogCoordhvNV;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(sArray, 1);
        }
        JNI.callPV(sArray, l);
    }

    public static void glSecondaryColor3hvNV(@NativeType(value="GLhalfNV const *") short[] sArray) {
        long l = GL.getICD().glSecondaryColor3hvNV;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(sArray, 3);
        }
        JNI.callPV(sArray, l);
    }

    public static void glVertexWeighthvNV(@NativeType(value="GLhalfNV const *") short[] sArray) {
        long l = GL.getICD().glVertexWeighthvNV;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(sArray, 1);
        }
        JNI.callPV(sArray, l);
    }

    public static void glVertexAttrib1hvNV(@NativeType(value="GLuint") int n, @NativeType(value="GLhalfNV const *") short[] sArray) {
        long l = GL.getICD().glVertexAttrib1hvNV;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(sArray, 1);
        }
        JNI.callPV(n, sArray, l);
    }

    public static void glVertexAttrib2hvNV(@NativeType(value="GLuint") int n, @NativeType(value="GLhalfNV const *") short[] sArray) {
        long l = GL.getICD().glVertexAttrib2hvNV;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(sArray, 2);
        }
        JNI.callPV(n, sArray, l);
    }

    public static void glVertexAttrib3hvNV(@NativeType(value="GLuint") int n, @NativeType(value="GLhalfNV const *") short[] sArray) {
        long l = GL.getICD().glVertexAttrib3hvNV;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(sArray, 3);
        }
        JNI.callPV(n, sArray, l);
    }

    public static void glVertexAttrib4hvNV(@NativeType(value="GLuint") int n, @NativeType(value="GLhalfNV const *") short[] sArray) {
        long l = GL.getICD().glVertexAttrib4hvNV;
        if (Checks.CHECKS) {
            Checks.check(l);
            Checks.check(sArray, 4);
        }
        JNI.callPV(n, sArray, l);
    }

    public static void glVertexAttribs1hvNV(@NativeType(value="GLuint") int n, @NativeType(value="GLhalfNV const *") short[] sArray) {
        long l = GL.getICD().glVertexAttribs1hvNV;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, sArray.length, sArray, l);
    }

    public static void glVertexAttribs2hvNV(@NativeType(value="GLuint") int n, @NativeType(value="GLhalfNV const *") short[] sArray) {
        long l = GL.getICD().glVertexAttribs2hvNV;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, sArray.length >> 1, sArray, l);
    }

    public static void glVertexAttribs3hvNV(@NativeType(value="GLuint") int n, @NativeType(value="GLhalfNV const *") short[] sArray) {
        long l = GL.getICD().glVertexAttribs3hvNV;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, sArray.length / 3, sArray, l);
    }

    public static void glVertexAttribs4hvNV(@NativeType(value="GLuint") int n, @NativeType(value="GLhalfNV const *") short[] sArray) {
        long l = GL.getICD().glVertexAttribs4hvNV;
        if (Checks.CHECKS) {
            Checks.check(l);
        }
        JNI.callPV(n, sArray.length >> 2, sArray, l);
    }

    static {
        GL.initialize();
    }
}

