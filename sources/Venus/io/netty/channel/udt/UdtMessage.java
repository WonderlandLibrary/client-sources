/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.channel.udt;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.buffer.DefaultByteBufHolder;
import io.netty.util.ReferenceCounted;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@Deprecated
public final class UdtMessage
extends DefaultByteBufHolder {
    public UdtMessage(ByteBuf byteBuf) {
        super(byteBuf);
    }

    @Override
    public UdtMessage copy() {
        return (UdtMessage)super.copy();
    }

    @Override
    public UdtMessage duplicate() {
        return (UdtMessage)super.duplicate();
    }

    @Override
    public UdtMessage retainedDuplicate() {
        return (UdtMessage)super.retainedDuplicate();
    }

    @Override
    public UdtMessage replace(ByteBuf byteBuf) {
        return new UdtMessage(byteBuf);
    }

    @Override
    public UdtMessage retain() {
        super.retain();
        return this;
    }

    @Override
    public UdtMessage retain(int n) {
        super.retain(n);
        return this;
    }

    @Override
    public UdtMessage touch() {
        super.touch();
        return this;
    }

    @Override
    public UdtMessage touch(Object object) {
        super.touch(object);
        return this;
    }

    @Override
    public ByteBufHolder touch(Object object) {
        return this.touch(object);
    }

    @Override
    public ByteBufHolder touch() {
        return this.touch();
    }

    @Override
    public ByteBufHolder retain(int n) {
        return this.retain(n);
    }

    @Override
    public ByteBufHolder retain() {
        return this.retain();
    }

    @Override
    public ByteBufHolder replace(ByteBuf byteBuf) {
        return this.replace(byteBuf);
    }

    @Override
    public ByteBufHolder retainedDuplicate() {
        return this.retainedDuplicate();
    }

    @Override
    public ByteBufHolder duplicate() {
        return this.duplicate();
    }

    @Override
    public ByteBufHolder copy() {
        return this.copy();
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

