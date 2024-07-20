/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.buffer;

import io.netty.buffer.AbstractUnpooledSlicedByteBuf;
import io.netty.buffer.ByteBuf;

@Deprecated
public class SlicedByteBuf
extends AbstractUnpooledSlicedByteBuf {
    private int length;

    public SlicedByteBuf(ByteBuf buffer, int index, int length) {
        super(buffer, index, length);
    }

    @Override
    final void initLength(int length) {
        this.length = length;
    }

    @Override
    final int length() {
        return this.length;
    }

    @Override
    public int capacity() {
        return this.length;
    }
}

