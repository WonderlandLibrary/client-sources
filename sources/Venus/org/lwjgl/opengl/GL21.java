/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.FloatBuffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL21C;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.NativeType;

public class GL21
extends GL20 {
    public static final int GL_CURRENT_RASTER_SECONDARY_COLOR = 33887;
    public static final int GL_FLOAT_MAT2x3 = 35685;
    public static final int GL_FLOAT_MAT2x4 = 35686;
    public static final int GL_FLOAT_MAT3x2 = 35687;
    public static final int GL_FLOAT_MAT3x4 = 35688;
    public static final int GL_FLOAT_MAT4x2 = 35689;
    public static final int GL_FLOAT_MAT4x3 = 35690;
    public static final int GL_PIXEL_PACK_BUFFER = 35051;
    public static final int GL_PIXEL_UNPACK_BUFFER = 35052;
    public static final int GL_PIXEL_PACK_BUFFER_BINDING = 35053;
    public static final int GL_PIXEL_UNPACK_BUFFER_BINDING = 35055;
    public static final int GL_SRGB = 35904;
    public static final int GL_SRGB8 = 35905;
    public static final int GL_SRGB_ALPHA = 35906;
    public static final int GL_SRGB8_ALPHA8 = 35907;
    public static final int GL_SLUMINANCE_ALPHA = 35908;
    public static final int GL_SLUMINANCE8_ALPHA8 = 35909;
    public static final int GL_SLUMINANCE = 35910;
    public static final int GL_SLUMINANCE8 = 35911;
    public static final int GL_COMPRESSED_SRGB = 35912;
    public static final int GL_COMPRESSED_SRGB_ALPHA = 35913;
    public static final int GL_COMPRESSED_SLUMINANCE = 35914;
    public static final int GL_COMPRESSED_SLUMINANCE_ALPHA = 35915;

    protected GL21() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glUniformMatrix2x3fv, gLCapabilities.glUniformMatrix3x2fv, gLCapabilities.glUniformMatrix2x4fv, gLCapabilities.glUniformMatrix4x2fv, gLCapabilities.glUniformMatrix3x4fv, gLCapabilities.glUniformMatrix4x3fv);
    }

    public static void nglUniformMatrix2x3fv(int n, int n2, boolean bl, long l) {
        GL21C.nglUniformMatrix2x3fv(n, n2, bl, l);
    }

    public static void glUniformMatrix2x3fv(@NativeType(value="GLint") int n, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        GL21C.glUniformMatrix2x3fv(n, bl, floatBuffer);
    }

    public static void nglUniformMatrix3x2fv(int n, int n2, boolean bl, long l) {
        GL21C.nglUniformMatrix3x2fv(n, n2, bl, l);
    }

    public static void glUniformMatrix3x2fv(@NativeType(value="GLint") int n, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        GL21C.glUniformMatrix3x2fv(n, bl, floatBuffer);
    }

    public static void nglUniformMatrix2x4fv(int n, int n2, boolean bl, long l) {
        GL21C.nglUniformMatrix2x4fv(n, n2, bl, l);
    }

    public static void glUniformMatrix2x4fv(@NativeType(value="GLint") int n, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        GL21C.glUniformMatrix2x4fv(n, bl, floatBuffer);
    }

    public static void nglUniformMatrix4x2fv(int n, int n2, boolean bl, long l) {
        GL21C.nglUniformMatrix4x2fv(n, n2, bl, l);
    }

    public static void glUniformMatrix4x2fv(@NativeType(value="GLint") int n, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        GL21C.glUniformMatrix4x2fv(n, bl, floatBuffer);
    }

    public static void nglUniformMatrix3x4fv(int n, int n2, boolean bl, long l) {
        GL21C.nglUniformMatrix3x4fv(n, n2, bl, l);
    }

    public static void glUniformMatrix3x4fv(@NativeType(value="GLint") int n, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        GL21C.glUniformMatrix3x4fv(n, bl, floatBuffer);
    }

    public static void nglUniformMatrix4x3fv(int n, int n2, boolean bl, long l) {
        GL21C.nglUniformMatrix4x3fv(n, n2, bl, l);
    }

    public static void glUniformMatrix4x3fv(@NativeType(value="GLint") int n, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") FloatBuffer floatBuffer) {
        GL21C.glUniformMatrix4x3fv(n, bl, floatBuffer);
    }

    public static void glUniformMatrix2x3fv(@NativeType(value="GLint") int n, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") float[] fArray) {
        GL21C.glUniformMatrix2x3fv(n, bl, fArray);
    }

    public static void glUniformMatrix3x2fv(@NativeType(value="GLint") int n, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") float[] fArray) {
        GL21C.glUniformMatrix3x2fv(n, bl, fArray);
    }

    public static void glUniformMatrix2x4fv(@NativeType(value="GLint") int n, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") float[] fArray) {
        GL21C.glUniformMatrix2x4fv(n, bl, fArray);
    }

    public static void glUniformMatrix4x2fv(@NativeType(value="GLint") int n, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") float[] fArray) {
        GL21C.glUniformMatrix4x2fv(n, bl, fArray);
    }

    public static void glUniformMatrix3x4fv(@NativeType(value="GLint") int n, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") float[] fArray) {
        GL21C.glUniformMatrix3x4fv(n, bl, fArray);
    }

    public static void glUniformMatrix4x3fv(@NativeType(value="GLint") int n, @NativeType(value="GLboolean") boolean bl, @NativeType(value="GLfloat const *") float[] fArray) {
        GL21C.glUniformMatrix4x3fv(n, bl, fArray);
    }

    static {
        GL.initialize();
    }
}

