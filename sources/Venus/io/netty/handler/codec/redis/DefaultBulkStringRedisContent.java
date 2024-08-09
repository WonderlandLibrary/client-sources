/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.redis;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.buffer.DefaultByteBufHolder;
import io.netty.handler.codec.redis.BulkStringRedisContent;
import io.netty.util.ReferenceCounted;
import io.netty.util.internal.StringUtil;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class DefaultBulkStringRedisContent
extends DefaultByteBufHolder
implements BulkStringRedisContent {
    public DefaultBulkStringRedisContent(ByteBuf byteBuf) {
        super(byteBuf);
    }

    @Override
    public BulkStringRedisContent copy() {
        return (BulkStringRedisContent)super.copy();
    }

    @Override
    public BulkStringRedisContent duplicate() {
        return (BulkStringRedisContent)super.duplicate();
    }

    @Override
    public BulkStringRedisContent retainedDuplicate() {
        return (BulkStringRedisContent)super.retainedDuplicate();
    }

    @Override
    public BulkStringRedisContent replace(ByteBuf byteBuf) {
        return new DefaultBulkStringRedisContent(byteBuf);
    }

    @Override
    public BulkStringRedisContent retain() {
        super.retain();
        return this;
    }

    @Override
    public BulkStringRedisContent retain(int n) {
        super.retain(n);
        return this;
    }

    @Override
    public BulkStringRedisContent touch() {
        super.touch();
        return this;
    }

    @Override
    public BulkStringRedisContent touch(Object object) {
        super.touch(object);
        return this;
    }

    @Override
    public String toString() {
        return StringUtil.simpleClassName(this) + '[' + "content=" + this.content() + ']';
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

