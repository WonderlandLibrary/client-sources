/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.buffer;

import io.netty.buffer.AbstractByteBuf;
import io.netty.buffer.AbstractUnsafeSwappedByteBuf;
import io.netty.buffer.ByteBuf;
import io.netty.util.internal.PlatformDependent;

final class UnsafeHeapSwappedByteBuf
extends AbstractUnsafeSwappedByteBuf {
    UnsafeHeapSwappedByteBuf(AbstractByteBuf abstractByteBuf) {
        super(abstractByteBuf);
    }

    private static int idx(ByteBuf byteBuf, int n) {
        return byteBuf.arrayOffset() + n;
    }

    @Override
    protected long _getLong(AbstractByteBuf abstractByteBuf, int n) {
        return PlatformDependent.getLong(abstractByteBuf.array(), UnsafeHeapSwappedByteBuf.idx(abstractByteBuf, n));
    }

    @Override
    protected int _getInt(AbstractByteBuf abstractByteBuf, int n) {
        return PlatformDependent.getInt(abstractByteBuf.array(), UnsafeHeapSwappedByteBuf.idx(abstractByteBuf, n));
    }

    @Override
    protected short _getShort(AbstractByteBuf abstractByteBuf, int n) {
        return PlatformDependent.getShort(abstractByteBuf.array(), UnsafeHeapSwappedByteBuf.idx(abstractByteBuf, n));
    }

    @Override
    protected void _setShort(AbstractByteBuf abstractByteBuf, int n, short s) {
        PlatformDependent.putShort(abstractByteBuf.array(), UnsafeHeapSwappedByteBuf.idx(abstractByteBuf, n), s);
    }

    @Override
    protected void _setInt(AbstractByteBuf abstractByteBuf, int n, int n2) {
        PlatformDependent.putInt(abstractByteBuf.array(), UnsafeHeapSwappedByteBuf.idx(abstractByteBuf, n), n2);
    }

    @Override
    protected void _setLong(AbstractByteBuf abstractByteBuf, int n, long l) {
        PlatformDependent.putLong(abstractByteBuf.array(), UnsafeHeapSwappedByteBuf.idx(abstractByteBuf, n), l);
    }
}

