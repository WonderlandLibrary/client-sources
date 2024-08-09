/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.memcache;

import io.netty.buffer.ByteBufHolder;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.MessageAggregator;
import io.netty.handler.codec.memcache.FullMemcacheMessage;
import io.netty.handler.codec.memcache.LastMemcacheContent;
import io.netty.handler.codec.memcache.MemcacheContent;
import io.netty.handler.codec.memcache.MemcacheMessage;
import io.netty.handler.codec.memcache.MemcacheObject;

public abstract class AbstractMemcacheObjectAggregator<H extends MemcacheMessage>
extends MessageAggregator<MemcacheObject, H, MemcacheContent, FullMemcacheMessage> {
    protected AbstractMemcacheObjectAggregator(int n) {
        super(n);
    }

    @Override
    protected boolean isContentMessage(MemcacheObject memcacheObject) throws Exception {
        return memcacheObject instanceof MemcacheContent;
    }

    @Override
    protected boolean isLastContentMessage(MemcacheContent memcacheContent) throws Exception {
        return memcacheContent instanceof LastMemcacheContent;
    }

    @Override
    protected boolean isAggregated(MemcacheObject memcacheObject) throws Exception {
        return memcacheObject instanceof FullMemcacheMessage;
    }

    @Override
    protected boolean isContentLengthInvalid(H h, int n) {
        return true;
    }

    @Override
    protected Object newContinueResponse(H h, int n, ChannelPipeline channelPipeline) {
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
    protected Object newContinueResponse(Object object, int n, ChannelPipeline channelPipeline) throws Exception {
        return this.newContinueResponse((H)((MemcacheMessage)object), n, channelPipeline);
    }

    @Override
    protected boolean isContentLengthInvalid(Object object, int n) throws Exception {
        return this.isContentLengthInvalid((H)((MemcacheMessage)object), n);
    }

    @Override
    protected boolean isAggregated(Object object) throws Exception {
        return this.isAggregated((MemcacheObject)object);
    }

    @Override
    protected boolean isLastContentMessage(ByteBufHolder byteBufHolder) throws Exception {
        return this.isLastContentMessage((MemcacheContent)byteBufHolder);
    }

    @Override
    protected boolean isContentMessage(Object object) throws Exception {
        return this.isContentMessage((MemcacheObject)object);
    }
}

