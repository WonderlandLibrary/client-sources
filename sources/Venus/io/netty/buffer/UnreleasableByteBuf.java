/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.buffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.SwappedByteBuf;
import io.netty.buffer.WrappedByteBuf;
import io.netty.util.ReferenceCounted;
import java.nio.ByteOrder;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
final class UnreleasableByteBuf
extends WrappedByteBuf {
    private SwappedByteBuf swappedBuf;

    UnreleasableByteBuf(ByteBuf byteBuf) {
        super(byteBuf instanceof UnreleasableByteBuf ? byteBuf.unwrap() : byteBuf);
    }

    @Override
    public ByteBuf order(ByteOrder byteOrder) {
        if (byteOrder == null) {
            throw new NullPointerException("endianness");
        }
        if (byteOrder == this.order()) {
            return this;
        }
        SwappedByteBuf swappedByteBuf = this.swappedBuf;
        if (swappedByteBuf == null) {
            this.swappedBuf = swappedByteBuf = new SwappedByteBuf(this);
        }
        return swappedByteBuf;
    }

    @Override
    public ByteBuf asReadOnly() {
        return this.buf.isReadOnly() ? this : new UnreleasableByteBuf(this.buf.asReadOnly());
    }

    @Override
    public ByteBuf readSlice(int n) {
        return new UnreleasableByteBuf(this.buf.readSlice(n));
    }

    @Override
    public ByteBuf readRetainedSlice(int n) {
        return this.readSlice(n);
    }

    @Override
    public ByteBuf slice() {
        return new UnreleasableByteBuf(this.buf.slice());
    }

    @Override
    public ByteBuf retainedSlice() {
        return this.slice();
    }

    @Override
    public ByteBuf slice(int n, int n2) {
        return new UnreleasableByteBuf(this.buf.slice(n, n2));
    }

    @Override
    public ByteBuf retainedSlice(int n, int n2) {
        return this.slice(n, n2);
    }

    @Override
    public ByteBuf duplicate() {
        return new UnreleasableByteBuf(this.buf.duplicate());
    }

    @Override
    public ByteBuf retainedDuplicate() {
        return this.duplicate();
    }

    @Override
    public ByteBuf retain(int n) {
        return this;
    }

    @Override
    public ByteBuf retain() {
        return this;
    }

    @Override
    public ByteBuf touch() {
        return this;
    }

    @Override
    public ByteBuf touch(Object object) {
        return this;
    }

    @Override
    public boolean release() {
        return true;
    }

    @Override
    public boolean release(int n) {
        return true;
    }

    @Override
    public ReferenceCounted touch(Object object) {
        return this.touch(object);
    }

    @Override
    public ReferenceCounted touch() {
        return this.touch();
    }

    @Override
    public ReferenceCounted retain(int n) {
        return this.retain(n);
    }

    @Override
    public ReferenceCounted retain() {
        return this.retain();
    }
}

