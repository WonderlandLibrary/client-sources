/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class GLAllocation {
    public static synchronized ByteBuffer createDirectByteBuffer(int n) {
        return ByteBuffer.allocateDirect(n).order(ByteOrder.nativeOrder());
    }

    public static FloatBuffer createDirectFloatBuffer(int n) {
        return GLAllocation.createDirectByteBuffer(n << 2).asFloatBuffer();
    }

    public static IntBuffer createDirectIntBuffer(int n) {
        return GLAllocation.createDirectByteBuffer(n << 2).asIntBuffer();
    }
}

