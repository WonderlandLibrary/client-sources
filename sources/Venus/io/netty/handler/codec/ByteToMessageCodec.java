/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.internal.TypeParameterMatcher;
import java.util.List;

public abstract class ByteToMessageCodec<I>
extends ChannelDuplexHandler {
    private final TypeParameterMatcher outboundMsgMatcher;
    private final MessageToByteEncoder<I> encoder;
    private final ByteToMessageDecoder decoder = new ByteToMessageDecoder(this){
        final ByteToMessageCodec this$0;
        {
            this.this$0 = byteToMessageCodec;
        }

        @Override
        public void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
            this.this$0.decode(channelHandlerContext, byteBuf, list);
        }

        @Override
        protected void decodeLast(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
            this.this$0.decodeLast(channelHandlerContext, byteBuf, list);
        }
    };

    protected ByteToMessageCodec() {
        this(true);
    }

    protected ByteToMessageCodec(Class<? extends I> clazz) {
        this(clazz, true);
    }

    protected ByteToMessageCodec(boolean bl) {
        this.ensureNotSharable();
        this.outboundMsgMatcher = TypeParameterMatcher.find(this, ByteToMessageCodec.class, "I");
        this.encoder = new Encoder(this, bl);
    }

    protected ByteToMessageCodec(Class<? extends I> clazz, boolean bl) {
        this.ensureNotSharable();
        this.outboundMsgMatcher = TypeParameterMatcher.get(clazz);
        this.encoder = new Encoder(this, bl);
    }

    public boolean acceptOutboundMessage(Object object) throws Exception {
        return this.outboundMsgMatcher.match(object);
    }

    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object object) throws Exception {
        this.decoder.channelRead(channelHandlerContext, object);
    }

    @Override
    public void write(ChannelHandlerContext channelHandlerContext, Object object, ChannelPromise channelPromise) throws Exception {
        this.encoder.write(channelHandlerContext, object, channelPromise);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.decoder.channelReadComplete(channelHandlerContext);
    }

    @Override
    public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.decoder.channelInactive(channelHandlerContext);
    }

    @Override
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
        try {
            this.decoder.handlerAdded(channelHandlerContext);
        } finally {
            this.encoder.handlerAdded(channelHandlerContext);
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception {
        try {
            this.decoder.handlerRemoved(channelHandlerContext);
        } finally {
            this.encoder.handlerRemoved(channelHandlerContext);
        }
    }

    protected abstract void encode(ChannelHandlerContext var1, I var2, ByteBuf var3) throws Exception;

    protected abstract void decode(ChannelHandlerContext var1, ByteBuf var2, List<Object> var3) throws Exception;

    protected void decodeLast(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.isReadable()) {
            this.decode(channelHandlerContext, byteBuf, list);
        }
    }

    private final class Encoder
    extends MessageToByteEncoder<I> {
        final ByteToMessageCodec this$0;

        Encoder(ByteToMessageCodec byteToMessageCodec, boolean bl) {
            this.this$0 = byteToMessageCodec;
            super(bl);
        }

        @Override
        public boolean acceptOutboundMessage(Object object) throws Exception {
            return this.this$0.acceptOutboundMessage(object);
        }

        @Override
        protected void encode(ChannelHandlerContext channelHandlerContext, I i, ByteBuf byteBuf) throws Exception {
            this.this$0.encode(channelHandlerContext, i, byteBuf);
        }
    }
}

