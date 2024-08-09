/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.stomp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.MessageAggregator;
import io.netty.handler.codec.stomp.DefaultStompFrame;
import io.netty.handler.codec.stomp.LastStompContentSubframe;
import io.netty.handler.codec.stomp.StompContentSubframe;
import io.netty.handler.codec.stomp.StompFrame;
import io.netty.handler.codec.stomp.StompHeaders;
import io.netty.handler.codec.stomp.StompHeadersSubframe;
import io.netty.handler.codec.stomp.StompSubframe;

public class StompSubframeAggregator
extends MessageAggregator<StompSubframe, StompHeadersSubframe, StompContentSubframe, StompFrame> {
    public StompSubframeAggregator(int n) {
        super(n);
    }

    @Override
    protected boolean isStartMessage(StompSubframe stompSubframe) throws Exception {
        return stompSubframe instanceof StompHeadersSubframe;
    }

    @Override
    protected boolean isContentMessage(StompSubframe stompSubframe) throws Exception {
        return stompSubframe instanceof StompContentSubframe;
    }

    @Override
    protected boolean isLastContentMessage(StompContentSubframe stompContentSubframe) throws Exception {
        return stompContentSubframe instanceof LastStompContentSubframe;
    }

    @Override
    protected boolean isAggregated(StompSubframe stompSubframe) throws Exception {
        return stompSubframe instanceof StompFrame;
    }

    @Override
    protected boolean isContentLengthInvalid(StompHeadersSubframe stompHeadersSubframe, int n) {
        return (int)Math.min(Integer.MAX_VALUE, stompHeadersSubframe.headers().getLong(StompHeaders.CONTENT_LENGTH, -1L)) > n;
    }

    @Override
    protected Object newContinueResponse(StompHeadersSubframe stompHeadersSubframe, int n, ChannelPipeline channelPipeline) {
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
    protected StompFrame beginAggregation(StompHeadersSubframe stompHeadersSubframe, ByteBuf byteBuf) throws Exception {
        DefaultStompFrame defaultStompFrame = new DefaultStompFrame(stompHeadersSubframe.command(), byteBuf);
        defaultStompFrame.headers().set(stompHeadersSubframe.headers());
        return defaultStompFrame;
    }

    @Override
    protected ByteBufHolder beginAggregation(Object object, ByteBuf byteBuf) throws Exception {
        return this.beginAggregation((StompHeadersSubframe)object, byteBuf);
    }

    @Override
    protected Object newContinueResponse(Object object, int n, ChannelPipeline channelPipeline) throws Exception {
        return this.newContinueResponse((StompHeadersSubframe)object, n, channelPipeline);
    }

    @Override
    protected boolean isContentLengthInvalid(Object object, int n) throws Exception {
        return this.isContentLengthInvalid((StompHeadersSubframe)object, n);
    }

    @Override
    protected boolean isAggregated(Object object) throws Exception {
        return this.isAggregated((StompSubframe)object);
    }

    @Override
    protected boolean isLastContentMessage(ByteBufHolder byteBufHolder) throws Exception {
        return this.isLastContentMessage((StompContentSubframe)byteBufHolder);
    }

    @Override
    protected boolean isContentMessage(Object object) throws Exception {
        return this.isContentMessage((StompSubframe)object);
    }

    @Override
    protected boolean isStartMessage(Object object) throws Exception {
        return this.isStartMessage((StompSubframe)object);
    }
}

