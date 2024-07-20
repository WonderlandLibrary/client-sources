/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import org.lwjgl.opengl.GL43;

public final class ARBMultiDrawIndirect {
    private ARBMultiDrawIndirect() {
    }

    public static void glMultiDrawArraysIndirect(int mode, ByteBuffer indirect, int primcount, int stride) {
        GL43.glMultiDrawArraysIndirect(mode, indirect, primcount, stride);
    }

    public static void glMultiDrawArraysIndirect(int mode, long indirect_buffer_offset, int primcount, int stride) {
        GL43.glMultiDrawArraysIndirect(mode, indirect_buffer_offset, primcount, stride);
    }

    public static void glMultiDrawArraysIndirect(int mode, IntBuffer indirect, int primcount, int stride) {
        GL43.glMultiDrawArraysIndirect(mode, indirect, primcount, stride);
    }

    public static void glMultiDrawElementsIndirect(int mode, int type2, ByteBuffer indirect, int primcount, int stride) {
        GL43.glMultiDrawElementsIndirect(mode, type2, indirect, primcount, stride);
    }

    public static void glMultiDrawElementsIndirect(int mode, int type2, long indirect_buffer_offset, int primcount, int stride) {
        GL43.glMultiDrawElementsIndirect(mode, type2, indirect_buffer_offset, primcount, stride);
    }

    public static void glMultiDrawElementsIndirect(int mode, int type2, IntBuffer indirect, int primcount, int stride) {
        GL43.glMultiDrawElementsIndirect(mode, type2, indirect, primcount, stride);
    }
}

