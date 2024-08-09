/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.redis;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.handler.codec.redis.BulkStringRedisContent;
import io.netty.handler.codec.redis.DefaultBulkStringRedisContent;
import io.netty.handler.codec.redis.LastBulkStringRedisContent;
import io.netty.util.ReferenceCounted;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class DefaultLastBulkStringRedisContent
extends DefaultBulkStringRedisContent
implements LastBulkStringRedisContent {
    public DefaultLastBulkStringRedisContent(ByteBuf byteBuf) {
        super(byteBuf);
    }

    @Override
    public LastBulkStringRedisContent copy() {
        return (LastBulkStringRedisContent)super.copy();
    }

    @Override
    public LastBulkStringRedisContent duplicate() {
        return (LastBulkStringRedisContent)super.duplicate();
    }

    @Override
    public LastBulkStringRedisContent retainedDuplicate() {
        return (LastBulkStringRedisContent)super.retainedDuplicate();
    }

    @Override
    public LastBulkStringRedisContent replace(ByteBuf byteBuf) {
        return new DefaultLastBulkStringRedisContent(byteBuf);
    }

    @Override
    public LastBulkStringRedisContent retain() {
        super.retain();
        return this;
    }

    @Override
    public LastBulkStringRedisContent retain(int n) {
        super.retain(n);
        return this;
    }

    @Override
    public LastBulkStringRedisContent touch() {
        super.touch();
        return this;
    }

    @Override
    public LastBulkStringRedisContent touch(Object object) {
        super.touch(object);
        return this;
    }

    @Override
    public BulkStringRedisContent touch(Object object) {
        return this.touch(object);
    }

    @Override
    public BulkStringRedisContent touch() {
        return this.touch();
    }

    @Override
    public BulkStringRedisContent retain(int n) {
        return this.retain(n);
    }

    @Override
    public BulkStringRedisContent retain() {
        return this.retain();
    }

    @Override
    public BulkStringRedisContent replace(ByteBuf byteBuf) {
        return this.replace(byteBuf);
    }

    @Override
    public BulkStringRedisContent retainedDuplicate() {
        return this.retainedDuplicate();
    }

    @Override
    public BulkStringRedisContent duplicate() {
        return this.duplicate();
    }

    @Override
    public BulkStringRedisContent copy() {
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

