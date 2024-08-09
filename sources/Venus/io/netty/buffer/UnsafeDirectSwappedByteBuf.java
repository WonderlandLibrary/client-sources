/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.buffer;

import io.netty.buffer.AbstractByteBuf;
import io.netty.buffer.AbstractUnsafeSwappedByteBuf;
import io.netty.util.internal.PlatformDependent;

final class UnsafeDirectSwappedByteBuf
extends AbstractUnsafeSwappedByteBuf {
    UnsafeDirectSwappedByteBuf(AbstractByteBuf abstractByteBuf) {
        super(abstractByteBuf);
    }

    private static long addr(AbstractByteBuf abstractByteBuf, int n) {
        return abstractByteBuf.memoryAddress() + (long)n;
    }

    @Override
    protected long _getLong(AbstractByteBuf abstractByteBuf, int n) {
        return PlatformDependent.getLong(UnsafeDirectSwappedByteBuf.addr(abstractByteBuf, n));
    }

    @Override
    protected int _getInt(AbstractByteBuf abstractByteBuf, int n) {
        return PlatformDependent.getInt(UnsafeDirectSwappedByteBuf.addr(abstractByteBuf, n));
    }

    @Override
    protected short _getShort(AbstractByteBuf abstractByteBuf, int n) {
        return PlatformDependent.getShort(UnsafeDirectSwappedByteBuf.addr(abstractByteBuf, n));
    }

    @Override
    protected void _setShort(AbstractByteBuf abstractByteBuf, int n, short s) {
        PlatformDependent.putShort(UnsafeDirectSwappedByteBuf.addr(abstractByteBuf, n), s);
    }

    @Override
    protected void _setInt(AbstractByteBuf abstractByteBuf, int n, int n2) {
        PlatformDependent.putInt(UnsafeDirectSwappedByteBuf.addr(abstractByteBuf, n), n2);
    }

    @Override
    protected void _setLong(AbstractByteBuf abstractByteBuf, int n, long l) {
        PlatformDependent.putLong(UnsafeDirectSwappedByteBuf.addr(abstractByteBuf, n), l);
    }
}

