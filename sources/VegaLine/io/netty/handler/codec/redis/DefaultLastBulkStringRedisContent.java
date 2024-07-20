/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.redis;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.redis.DefaultBulkStringRedisContent;
import io.netty.handler.codec.redis.LastBulkStringRedisContent;

public final class DefaultLastBulkStringRedisContent
extends DefaultBulkStringRedisContent
implements LastBulkStringRedisContent {
    public DefaultLastBulkStringRedisContent(ByteBuf content) {
        super(content);
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
    public LastBulkStringRedisContent replace(ByteBuf content) {
        return new DefaultLastBulkStringRedisContent(content);
    }

    @Override
    public LastBulkStringRedisContent retain() {
        super.retain();
        return this;
    }

    @Override
    public LastBulkStringRedisContent retain(int increment) {
        super.retain(increment);
        return this;
    }

    @Override
    public LastBulkStringRedisContent touch() {
        super.touch();
        return this;
    }

    @Override
    public LastBulkStringRedisContent touch(Object hint) {
        super.touch(hint);
        return this;
    }
}

