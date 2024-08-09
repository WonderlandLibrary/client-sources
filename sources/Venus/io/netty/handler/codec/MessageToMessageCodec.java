/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.internal.TypeParameterMatcher;
import java.util.List;

public abstract class MessageToMessageCodec<INBOUND_IN, OUTBOUND_IN>
extends ChannelDuplexHandler {
    private final MessageToMessageEncoder<Object> encoder = new MessageToMessageEncoder<Object>(this){
        final MessageToMessageCodec this$0;
        {
            this.this$0 = messageToMessageCodec;
        }

        @Override
        public boolean acceptOutboundMessage(Object object) throws Exception {
            return this.this$0.acceptOutboundMessage(object);
        }

        @Override
        protected void encode(ChannelHandlerContext channelHandlerContext, Object object, List<Object> list) throws Exception {
            this.this$0.encode(channelHandlerContext, object, list);
        }
    };
    private final MessageToMessageDecoder<Object> decoder = new MessageToMessageDecoder<Object>(this){
        final MessageToMessageCodec this$0;
        {
            this.this$0 = messageToMessageCodec;
        }

        @Override
        public boolean acceptInboundMessage(Object object) throws Exception {
            return this.this$0.acceptInboundMessage(object);
        }

        @Override
        protected void decode(ChannelHandlerContext channelHandlerContext, Object object, List<Object> list) throws Exception {
            this.this$0.decode(channelHandlerContext, object, list);
        }
    };
    private final TypeParameterMatcher inboundMsgMatcher;
    private final TypeParameterMatcher outboundMsgMatcher;

    protected MessageToMessageCodec() {
        this.inboundMsgMatcher = TypeParameterMatcher.find(this, MessageToMessageCodec.class, "INBOUND_IN");
        this.outboundMsgMatcher = TypeParameterMatcher.find(this, MessageToMessageCodec.class, "OUTBOUND_IN");
    }

    protected MessageToMessageCodec(Class<? extends INBOUND_IN> clazz, Class<? extends OUTBOUND_IN> clazz2) {
        this.inboundMsgMatcher = TypeParameterMatcher.get(clazz);
        this.outboundMsgMatcher = TypeParameterMatcher.get(clazz2);
    }

    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object object) throws Exception {
        this.decoder.channelRead(channelHandlerContext, object);
    }

    @Override
    public void write(ChannelHandlerContext channelHandlerContext, Object object, ChannelPromise channelPromise) throws Exception {
        this.encoder.write(channelHandlerContext, object, channelPromise);
    }

    public boolean acceptInboundMessage(Object object) throws Exception {
        return this.inboundMsgMatcher.match(object);
    }

    public boolean acceptOutboundMessage(Object object) throws Exception {
        return this.outboundMsgMatcher.match(object);
    }

    protected abstract void encode(ChannelHandlerContext var1, OUTBOUND_IN var2, List<Object> var3) throws Exception;

    protected abstract void decode(ChannelHandlerContext var1, INBOUND_IN var2, List<Object> var3) throws Exception;
}

