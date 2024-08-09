/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.buffer;

import io.netty.buffer.AbstractByteBuf;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.DuplicatedByteBuf;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
class UnpooledDuplicatedByteBuf
extends DuplicatedByteBuf {
    UnpooledDuplicatedByteBuf(AbstractByteBuf abstractByteBuf) {
        super(abstractByteBuf);
    }

    @Override
    public AbstractByteBuf unwrap() {
        return (AbstractByteBuf)super.unwrap();
    }

    @Override
    protected byte _getByte(int n) {
        return this.unwrap()._getByte(n);
    }

    @Override
    protected short _getShort(int n) {
        return this.unwrap()._getShort(n);
    }

    @Override
    protected short _getShortLE(int n) {
        return this.unwrap()._getShortLE(n);
    }

    @Override
    protected int _getUnsignedMedium(int n) {
        return this.unwrap()._getUnsignedMedium(n);
    }

    @Override
    protected int _getUnsignedMediumLE(int n) {
        return this.unwrap()._getUnsignedMediumLE(n);
    }

    @Override
    protected int _getInt(int n) {
        return this.unwrap()._getInt(n);
    }

    @Override
    protected int _getIntLE(int n) {
        return this.unwrap()._getIntLE(n);
    }

    @Override
    protected long _getLong(int n) {
        return this.unwrap()._getLong(n);
    }

    @Override
    protected long _getLongLE(int n) {
        return this.unwrap()._getLongLE(n);
    }

    @Override
    protected void _setByte(int n, int n2) {
        this.unwrap()._setByte(n, n2);
    }

    @Override
    protected void _setShort(int n, int n2) {
        this.unwrap()._setShort(n, n2);
    }

    @Override
    protected void _setShortLE(int n, int n2) {
        this.unwrap()._setShortLE(n, n2);
    }

    @Override
    protected void _setMedium(int n, int n2) {
        this.unwrap()._setMedium(n, n2);
    }

    @Override
    protected void _setMediumLE(int n, int n2) {
        this.unwrap()._setMediumLE(n, n2);
    }

    @Override
    protected void _setInt(int n, int n2) {
        this.unwrap()._setInt(n, n2);
    }

    @Override
    protected void _setIntLE(int n, int n2) {
        this.unwrap()._setIntLE(n, n2);
    }

    @Override
    protected void _setLong(int n, long l) {
        this.unwrap()._setLong(n, l);
    }

    @Override
    protected void _setLongLE(int n, long l) {
        this.unwrap()._setLongLE(n, l);
    }

    @Override
    public ByteBuf unwrap() {
        return this.unwrap();
    }
}

