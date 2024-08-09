/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.redis;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.MessageAggregator;
import io.netty.handler.codec.redis.BulkStringHeaderRedisMessage;
import io.netty.handler.codec.redis.BulkStringRedisContent;
import io.netty.handler.codec.redis.FullBulkStringRedisMessage;
import io.netty.handler.codec.redis.LastBulkStringRedisContent;
import io.netty.handler.codec.redis.RedisMessage;

public final class RedisBulkStringAggregator
extends MessageAggregator<RedisMessage, BulkStringHeaderRedisMessage, BulkStringRedisContent, FullBulkStringRedisMessage> {
    public RedisBulkStringAggregator() {
        super(0x20000000);
    }

    @Override
    protected boolean isStartMessage(RedisMessage redisMessage) throws Exception {
        return redisMessage instanceof BulkStringHeaderRedisMessage && !this.isAggregated(redisMessage);
    }

    @Override
    protected boolean isContentMessage(RedisMessage redisMessage) throws Exception {
        return redisMessage instanceof BulkStringRedisContent;
    }

    @Override
    protected boolean isLastContentMessage(BulkStringRedisContent bulkStringRedisContent) throws Exception {
        return bulkStringRedisContent instanceof LastBulkStringRedisContent;
    }

    @Override
    protected boolean isAggregated(RedisMessage redisMessage) throws Exception {
        return redisMessage instanceof FullBulkStringRedisMessage;
    }

    @Override
    protected boolean isContentLengthInvalid(BulkStringHeaderRedisMessage bulkStringHeaderRedisMessage, int n) throws Exception {
        return bulkStringHeaderRedisMessage.bulkStringLength() > n;
    }

    @Override
    protected Object newContinueResponse(BulkStringHeaderRedisMessage bulkStringHeaderRedisMessage, int n, ChannelPipeline channelPipeline) throws Exception {
        return null;
    }

    @Override
    protected boolean closeAfterContinueResponse(Object object) throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override
    protected boolean ignoreContentAfterContinueResponse(Object object) throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override
    protected FullBulkStringRedisMessage beginAggregation(BulkStringHeaderRedisMessage bulkStringHeaderRedisMessage, ByteBuf byteBuf) throws Exception {
        return new FullBulkStringRedisMessage(byteBuf);
    }

    @Override
    protected ByteBufHolder beginAggregation(Object object, ByteBuf byteBuf) throws Exception {
        return this.beginAggregation((BulkStringHeaderRedisMessage)object, byteBuf);
    }

    @Override
    protected Object newContinueResponse(Object object, int n, ChannelPipeline channelPipeline) throws Exception {
        return this.newContinueResponse((BulkStringHeaderRedisMessage)object, n, channelPipeline);
    }

    @Override
    protected boolean isContentLengthInvalid(Object object, int n) throws Exception {
        return this.isContentLengthInvalid((BulkStringHeaderRedisMessage)object, n);
    }

    @Override
    protected boolean isAggregated(Object object) throws Exception {
        return this.isAggregated((RedisMessage)object);
    }

    @Override
    protected boolean isLastContentMessage(ByteBufHolder byteBufHolder) throws Exception {
        return this.isLastContentMessage((BulkStringRedisContent)byteBufHolder);
    }

    @Override
    protected boolean isContentMessage(Object object) throws Exception {
        return this.isContentMessage((RedisMessage)object);
    }

    @Override
    protected boolean isStartMessage(Object object) throws Exception {
        return this.isStartMessage((RedisMessage)object);
    }
}

