/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufHolder;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.DecoderResultProvider;
import io.netty.handler.codec.MessageAggregationException;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.TooLongFrameException;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import java.util.List;

public abstract class MessageAggregator<I, S, C extends ByteBufHolder, O extends ByteBufHolder>
extends MessageToMessageDecoder<I> {
    private static final int DEFAULT_MAX_COMPOSITEBUFFER_COMPONENTS = 1024;
    private final int maxContentLength;
    private O currentMessage;
    private boolean handlingOversizedMessage;
    private int maxCumulationBufferComponents = 1024;
    private ChannelHandlerContext ctx;
    private ChannelFutureListener continueResponseWriteListener;

    protected MessageAggregator(int n) {
        MessageAggregator.validateMaxContentLength(n);
        this.maxContentLength = n;
    }

    protected MessageAggregator(int n, Class<? extends I> clazz) {
        super(clazz);
        MessageAggregator.validateMaxContentLength(n);
        this.maxContentLength = n;
    }

    private static void validateMaxContentLength(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("maxContentLength: " + n + " (expected: >= 0)");
        }
    }

    @Override
    public boolean acceptInboundMessage(Object object) throws Exception {
        if (!super.acceptInboundMessage(object)) {
            return true;
        }
        Object object2 = object;
        return (this.isContentMessage(object2) || this.isStartMessage(object2)) && !this.isAggregated(object2);
    }

    protected abstract boolean isStartMessage(I var1) throws Exception;

    protected abstract boolean isContentMessage(I var1) throws Exception;

    protected abstract boolean isLastContentMessage(C var1) throws Exception;

    protected abstract boolean isAggregated(I var1) throws Exception;

    public final int maxContentLength() {
        return this.maxContentLength;
    }

    public final int maxCumulationBufferComponents() {
        return this.maxCumulationBufferComponents;
    }

    public final void setMaxCumulationBufferComponents(int n) {
        if (n < 2) {
            throw new IllegalArgumentException("maxCumulationBufferComponents: " + n + " (expected: >= 2)");
        }
        if (this.ctx != null) {
            throw new IllegalStateException("decoder properties cannot be changed once the decoder is added to a pipeline.");
        }
        this.maxCumulationBufferComponents = n;
    }

    @Deprecated
    public final boolean isHandlingOversizedMessage() {
        return this.handlingOversizedMessage;
    }

    protected final ChannelHandlerContext ctx() {
        if (this.ctx == null) {
            throw new IllegalStateException("not added to a pipeline yet");
        }
        return this.ctx;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, I i, List<Object> list) throws Exception {
        if (this.isStartMessage(i)) {
            Object object;
            this.handlingOversizedMessage = false;
            if (this.currentMessage != null) {
                this.currentMessage.release();
                this.currentMessage = null;
                throw new MessageAggregationException();
            }
            I i2 = i;
            Object object2 = this.newContinueResponse(i2, this.maxContentLength, channelHandlerContext.pipeline());
            if (object2 != null) {
                object = this.continueResponseWriteListener;
                if (object == null) {
                    object = new ChannelFutureListener(this, channelHandlerContext){
                        final ChannelHandlerContext val$ctx;
                        final MessageAggregator this$0;
                        {
                            this.this$0 = messageAggregator;
                            this.val$ctx = channelHandlerContext;
                        }

                        @Override
                        public void operationComplete(ChannelFuture channelFuture) throws Exception {
                            if (!channelFuture.isSuccess()) {
                                this.val$ctx.fireExceptionCaught(channelFuture.cause());
                            }
                        }

                        @Override
                        public void operationComplete(Future future) throws Exception {
                            this.operationComplete((ChannelFuture)future);
                        }
                    };
                    this.continueResponseWriteListener = object;
                }
                boolean bl = this.closeAfterContinueResponse(object2);
                this.handlingOversizedMessage = this.ignoreContentAfterContinueResponse(object2);
                ChannelFuture channelFuture = channelHandlerContext.writeAndFlush(object2).addListener((GenericFutureListener<? extends Future<? super Void>>)object);
                if (bl) {
                    channelFuture.addListener(ChannelFutureListener.CLOSE);
                    return;
                }
                if (this.handlingOversizedMessage) {
                    return;
                }
            } else if (this.isContentLengthInvalid(i2, this.maxContentLength)) {
                this.invokeHandleOversizedMessage(channelHandlerContext, i2);
                return;
            }
            if (i2 instanceof DecoderResultProvider && !((DecoderResultProvider)i2).decoderResult().isSuccess()) {
                object = i2 instanceof ByteBufHolder ? this.beginAggregation(i2, ((ByteBufHolder)i2).content().retain()) : this.beginAggregation(i2, Unpooled.EMPTY_BUFFER);
                this.finishAggregation(object);
                list.add(object);
                return;
            }
            object = channelHandlerContext.alloc().compositeBuffer(this.maxCumulationBufferComponents);
            if (i2 instanceof ByteBufHolder) {
                MessageAggregator.appendPartialContent((CompositeByteBuf)object, ((ByteBufHolder)i2).content());
            }
            this.currentMessage = this.beginAggregation(i2, (ByteBuf)object);
        } else if (this.isContentMessage(i)) {
            boolean bl;
            if (this.currentMessage == null) {
                return;
            }
            CompositeByteBuf compositeByteBuf = (CompositeByteBuf)this.currentMessage.content();
            ByteBufHolder byteBufHolder = (ByteBufHolder)i;
            if (compositeByteBuf.readableBytes() > this.maxContentLength - byteBufHolder.content().readableBytes()) {
                O o = this.currentMessage;
                this.invokeHandleOversizedMessage(channelHandlerContext, o);
                return;
            }
            MessageAggregator.appendPartialContent(compositeByteBuf, byteBufHolder.content());
            this.aggregate(this.currentMessage, byteBufHolder);
            if (byteBufHolder instanceof DecoderResultProvider) {
                DecoderResult decoderResult = ((DecoderResultProvider)((Object)byteBufHolder)).decoderResult();
                if (!decoderResult.isSuccess()) {
                    if (this.currentMessage instanceof DecoderResultProvider) {
                        ((DecoderResultProvider)this.currentMessage).setDecoderResult(DecoderResult.failure(decoderResult.cause()));
                    }
                    bl = true;
                } else {
                    bl = this.isLastContentMessage(byteBufHolder);
                }
            } else {
                bl = this.isLastContentMessage(byteBufHolder);
            }
            if (bl) {
                this.finishAggregation(this.currentMessage);
                list.add(this.currentMessage);
                this.currentMessage = null;
            }
        } else {
            throw new MessageAggregationException();
        }
    }

    private static void appendPartialContent(CompositeByteBuf compositeByteBuf, ByteBuf byteBuf) {
        if (byteBuf.isReadable()) {
            compositeByteBuf.addComponent(true, byteBuf.retain());
        }
    }

    protected abstract boolean isContentLengthInvalid(S var1, int var2) throws Exception;

    protected abstract Object newContinueResponse(S var1, int var2, ChannelPipeline var3) throws Exception;

    protected abstract boolean closeAfterContinueResponse(Object var1) throws Exception;

    protected abstract boolean ignoreContentAfterContinueResponse(Object var1) throws Exception;

    protected abstract O beginAggregation(S var1, ByteBuf var2) throws Exception;

    protected void aggregate(O o, C c) throws Exception {
    }

    protected void finishAggregation(O o) throws Exception {
    }

    private void invokeHandleOversizedMessage(ChannelHandlerContext channelHandlerContext, S s) throws Exception {
        this.handlingOversizedMessage = true;
        this.currentMessage = null;
        try {
            this.handleOversizedMessage(channelHandlerContext, s);
        } finally {
            ReferenceCountUtil.release(s);
        }
    }

    protected void handleOversizedMessage(ChannelHandlerContext channelHandlerContext, S s) throws Exception {
        channelHandlerContext.fireExceptionCaught(new TooLongFrameException("content length exceeded " + this.maxContentLength() + " bytes."));
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (this.currentMessage != null && !channelHandlerContext.channel().config().isAutoRead()) {
            channelHandlerContext.read();
        }
        channelHandlerContext.fireChannelReadComplete();
    }

    @Override
    public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
        try {
            super.channelInactive(channelHandlerContext);
        } finally {
            this.releaseCurrentMessage();
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.ctx = channelHandlerContext;
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception {
        try {
            super.handlerRemoved(channelHandlerContext);
        } finally {
            this.releaseCurrentMessage();
        }
    }

    private void releaseCurrentMessage() {
        if (this.currentMessage != null) {
            this.currentMessage.release();
            this.currentMessage = null;
            this.handlingOversizedMessage = false;
        }
    }
}

