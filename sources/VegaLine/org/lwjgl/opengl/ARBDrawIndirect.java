/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.lwjgl.opengl;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import org.lwjgl.opengl.GL40;

public final class ARBDrawIndirect {
    public static final int GL_DRAW_INDIRECT_BUFFER = 36671;
    public static final int GL_DRAW_INDIRECT_BUFFER_BINDING = 36675;

    private ARBDrawIndirect() {
    }

    public static void glDrawArraysIndirect(int mode, ByteBuffer indirect) {
        GL40.glDrawArraysIndirect(mode, indirect);
    }

    public static void glDrawArraysIndirect(int mode, long indirect_buffer_offset) {
        GL40.glDrawArraysIndirect(mode, indirect_buffer_offset);
    }

    public static void glDrawArraysIndirect(int mode, IntBuffer indirect) {
        GL40.glDrawArraysIndirect(mode, indirect);
    }

    public static void glDrawElementsIndirect(int mode, int type2, ByteBuffer indirect) {
        GL40.glDrawElementsIndirect(mode, type2, indirect);
    }

    public static void glDrawElementsIndirect(int mode, int type2, long indirect_buffer_offset) {
        GL40.glDrawElementsIndirect(mode, type2, indirect_buffer_offset);
    }

    public static void glDrawElementsIndirect(int mode, int type2, IntBuffer indirect) {
        GL40.glDrawElementsIndirect(mode, type2, indirect);
    }
}

