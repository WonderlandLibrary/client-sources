/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.buffer;

import io.netty.buffer.AbstractByteBuf;
import io.netty.buffer.ByteBuf;
import io.netty.util.ReferenceCounted;
import java.nio.ByteBuffer;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@Deprecated
public abstract class AbstractDerivedByteBuf
extends AbstractByteBuf {
    protected AbstractDerivedByteBuf(int n) {
        super(n);
    }

    @Override
    public final int refCnt() {
        return this.refCnt0();
    }

    int refCnt0() {
        return this.unwrap().refCnt();
    }

    @Override
    public final ByteBuf retain() {
        return this.retain0();
    }

    ByteBuf retain0() {
        this.unwrap().retain();
        return this;
    }

    @Override
    public final ByteBuf retain(int n) {
        return this.retain0(n);
    }

    ByteBuf retain0(int n) {
        this.unwrap().retain(n);
        return this;
    }

    @Override
    public final ByteBuf touch() {
        return this.touch0();
    }

    ByteBuf touch0() {
        this.unwrap().touch();
        return this;
    }

    @Override
    public final ByteBuf touch(Object object) {
        return this.touch0(object);
    }

    ByteBuf touch0(Object object) {
        this.unwrap().touch(object);
        return this;
    }

    @Override
    public final boolean release() {
        return this.release0();
    }

    boolean release0() {
        return this.unwrap().release();
    }

    @Override
    public final boolean release(int n) {
        return this.release0(n);
    }

    boolean release0(int n) {
        return this.unwrap().release(n);
    }

    @Override
    public boolean isReadOnly() {
        return this.unwrap().isReadOnly();
    }

    @Override
    public ByteBuffer internalNioBuffer(int n, int n2) {
        return this.nioBuffer(n, n2);
    }

    @Override
    public ByteBuffer nioBuffer(int n, int n2) {
        return this.unwrap().nioBuffer(n, n2);
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

