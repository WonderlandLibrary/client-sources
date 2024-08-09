/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.buffer;

import io.netty.buffer.AbstractByteBuf;
import io.netty.buffer.AbstractUnpooledSlicedByteBuf;
import io.netty.buffer.ByteBuf;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
class UnpooledSlicedByteBuf
extends AbstractUnpooledSlicedByteBuf {
    UnpooledSlicedByteBuf(AbstractByteBuf abstractByteBuf, int n, int n2) {
        super(abstractByteBuf, n, n2);
    }

    @Override
    public int capacity() {
        return this.maxCapacity();
    }

    @Override
    public AbstractByteBuf unwrap() {
        return (AbstractByteBuf)super.unwrap();
    }

    @Override
    protected byte _getByte(int n) {
        return this.unwrap()._getByte(this.idx(n));
    }

    @Override
    protected short _getShort(int n) {
        return this.unwrap()._getShort(this.idx(n));
    }

    @Override
    protected short _getShortLE(int n) {
        return this.unwrap()._getShortLE(this.idx(n));
    }

    @Override
    protected int _getUnsignedMedium(int n) {
        return this.unwrap()._getUnsignedMedium(this.idx(n));
    }

    @Override
    protected int _getUnsignedMediumLE(int n) {
        return this.unwrap()._getUnsignedMediumLE(this.idx(n));
    }

    @Override
    protected int _getInt(int n) {
        return this.unwrap()._getInt(this.idx(n));
    }

    @Override
    protected int _getIntLE(int n) {
        return this.unwrap()._getIntLE(this.idx(n));
    }

    @Override
    protected long _getLong(int n) {
        return this.unwrap()._getLong(this.idx(n));
    }

    @Override
    protected long _getLongLE(int n) {
        return this.unwrap()._getLongLE(this.idx(n));
    }

    @Override
    protected void _setByte(int n, int n2) {
        this.unwrap()._setByte(this.idx(n), n2);
    }

    @Override
    protected void _setShort(int n, int n2) {
        this.unwrap()._setShort(this.idx(n), n2);
    }

    @Override
    protected void _setShortLE(int n, int n2) {
        this.unwrap()._setShortLE(this.idx(n), n2);
    }

    @Override
    protected void _setMedium(int n, int n2) {
        this.unwrap()._setMedium(this.idx(n), n2);
    }

    @Override
    protected void _setMediumLE(int n, int n2) {
        this.unwrap()._setMediumLE(this.idx(n), n2);
    }

    @Override
    protected void _setInt(int n, int n2) {
        this.unwrap()._setInt(this.idx(n), n2);
    }

    @Override
    protected void _setIntLE(int n, int n2) {
        this.unwrap()._setIntLE(this.idx(n), n2);
    }

    @Override
    protected void _setLong(int n, long l) {
        this.unwrap()._setLong(this.idx(n), l);
    }

    @Override
    protected void _setLongLE(int n, long l) {
        this.unwrap()._setLongLE(this.idx(n), l);
    }

    @Override
    public ByteBuf unwrap() {
        return this.unwrap();
    }
}

