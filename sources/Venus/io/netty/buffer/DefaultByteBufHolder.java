/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.buffer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.util.IllegalReferenceCountException;
import io.netty.util.ReferenceCounted;
import io.netty.util.internal.StringUtil;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class DefaultByteBufHolder
implements ByteBufHolder {
    private final ByteBuf data;

    public DefaultByteBufHolder(ByteBuf byteBuf) {
        if (byteBuf == null) {
            throw new NullPointerException("data");
        }
        this.data = byteBuf;
    }

    @Override
    public ByteBuf content() {
        if (this.data.refCnt() <= 0) {
            throw new IllegalReferenceCountException(this.data.refCnt());
        }
        return this.data;
    }

    @Override
    public ByteBufHolder copy() {
        return this.replace(this.data.copy());
    }

    @Override
    public ByteBufHolder duplicate() {
        return this.replace(this.data.duplicate());
    }

    @Override
    public ByteBufHolder retainedDuplicate() {
        return this.replace(this.data.retainedDuplicate());
    }

    @Override
    public ByteBufHolder replace(ByteBuf byteBuf) {
        return new DefaultByteBufHolder(byteBuf);
    }

    @Override
    public int refCnt() {
        return this.data.refCnt();
    }

    @Override
    public ByteBufHolder retain() {
        this.data.retain();
        return this;
    }

    @Override
    public ByteBufHolder retain(int n) {
        this.data.retain(n);
        return this;
    }

    @Override
    public ByteBufHolder touch() {
        this.data.touch();
        return this;
    }

    @Override
    public ByteBufHolder touch(Object object) {
        this.data.touch(object);
        return this;
    }

    @Override
    public boolean release() {
        return this.data.release();
    }

    @Override
    public boolean release(int n) {
        return this.data.release(n);
    }

    protected final String contentToString() {
        return this.data.toString();
    }

    public String toString() {
        return StringUtil.simpleClassName(this) + '(' + this.contentToString() + ')';
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object instanceof ByteBufHolder) {
            return this.data.equals(((ByteBufHolder)object).content());
        }
        return true;
    }

    public int hashCode() {
        return this.data.hashCode();
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

