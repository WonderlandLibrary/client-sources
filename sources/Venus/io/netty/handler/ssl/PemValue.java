/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.ssl;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.handler.ssl.PemEncoded;
import io.netty.handler.ssl.SslUtils;
import io.netty.util.AbstractReferenceCounted;
import io.netty.util.IllegalReferenceCountException;
import io.netty.util.ReferenceCounted;
import io.netty.util.internal.ObjectUtil;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
class PemValue
extends AbstractReferenceCounted
implements PemEncoded {
    private final ByteBuf content;
    private final boolean sensitive;

    public PemValue(ByteBuf byteBuf, boolean bl) {
        this.content = ObjectUtil.checkNotNull(byteBuf, "content");
        this.sensitive = bl;
    }

    @Override
    public boolean isSensitive() {
        return this.sensitive;
    }

    @Override
    public ByteBuf content() {
        int n = this.refCnt();
        if (n <= 0) {
            throw new IllegalReferenceCountException(n);
        }
        return this.content;
    }

    @Override
    public PemValue copy() {
        return this.replace(this.content.copy());
    }

    @Override
    public PemValue duplicate() {
        return this.replace(this.content.duplicate());
    }

    @Override
    public PemValue retainedDuplicate() {
        return this.replace(this.content.retainedDuplicate());
    }

    @Override
    public PemValue replace(ByteBuf byteBuf) {
        return new PemValue(byteBuf, this.sensitive);
    }

    @Override
    public PemValue touch() {
        return (PemValue)super.touch();
    }

    @Override
    public PemValue touch(Object object) {
        this.content.touch(object);
        return this;
    }

    @Override
    public PemValue retain() {
        return (PemValue)super.retain();
    }

    @Override
    public PemValue retain(int n) {
        return (PemValue)super.retain(n);
    }

    @Override
    protected void deallocate() {
        if (this.sensitive) {
            SslUtils.zeroout(this.content);
        }
        this.content.release();
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

    @Override
    public ReferenceCounted touch(Object object) {
        return this.touch(object);
    }

    @Override
    public PemEncoded touch(Object object) {
        return this.touch(object);
    }

    @Override
    public PemEncoded touch() {
        return this.touch();
    }

    @Override
    public PemEncoded retain(int n) {
        return this.retain(n);
    }

    @Override
    public PemEncoded retain() {
        return this.retain();
    }

    @Override
    public PemEncoded replace(ByteBuf byteBuf) {
        return this.replace(byteBuf);
    }

    @Override
    public PemEncoded retainedDuplicate() {
        return this.retainedDuplicate();
    }

    @Override
    public PemEncoded duplicate() {
        return this.duplicate();
    }

    @Override
    public PemEncoded copy() {
        return this.copy();
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
}

