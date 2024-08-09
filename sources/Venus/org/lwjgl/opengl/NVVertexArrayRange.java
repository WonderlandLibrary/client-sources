/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.Checks;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.NativeType;

public class NVVertexArrayRange {
    public static final int GL_VERTEX_ARRAY_RANGE_NV = 34077;
    public static final int GL_VERTEX_ARRAY_RANGE_LENGTH_NV = 34078;
    public static final int GL_VERTEX_ARRAY_RANGE_VALID_NV = 34079;
    public static final int GL_MAX_VERTEX_ARRAY_RANGE_ELEMENT_NV = 34080;
    public static final int GL_VERTEX_ARRAY_RANGE_POINTER_NV = 34081;

    protected NVVertexArrayRange() {
        throw new UnsupportedOperationException();
    }

    static boolean isAvailable(GLCapabilities gLCapabilities) {
        return Checks.checkFunctions(gLCapabilities.glVertexArrayRangeNV, gLCapabilities.glFlushVertexArrayRangeNV);
    }

    public static native void nglVertexArrayRangeNV(int var0, long var1);

    public static void glVertexArrayRangeNV(@NativeType(value="void *") ByteBuffer byteBuffer) {
        NVVertexArrayRange.nglVertexArrayRangeNV(byteBuffer.remaining(), MemoryUtil.memAddress(byteBuffer));
    }

    public static native void glFlushVertexArrayRangeNV();

    static {
        GL.initialize();
    }
}

