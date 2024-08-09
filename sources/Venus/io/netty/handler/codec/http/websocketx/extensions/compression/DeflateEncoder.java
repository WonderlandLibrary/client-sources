/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http.websocketx.extensions.compression;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.CodecException;
import io.netty.handler.codec.compression.ZlibCodecFactory;
import io.netty.handler.codec.compression.ZlibWrapper;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.ContinuationWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketExtensionEncoder;
import io.netty.handler.codec.http.websocketx.extensions.compression.PerMessageDeflateDecoder;
import java.util.List;

abstract class DeflateEncoder
extends WebSocketExtensionEncoder {
    private final int compressionLevel;
    private final int windowSize;
    private final boolean noContext;
    private EmbeddedChannel encoder;

    public DeflateEncoder(int n, int n2, boolean bl) {
        this.compressionLevel = n;
        this.windowSize = n2;
        this.noContext = bl;
    }

    protected abstract int rsv(WebSocketFrame var1);

    protected abstract boolean removeFrameTail(WebSocketFrame var1);

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, WebSocketFrame webSocketFrame, List<Object> list) throws Exception {
        WebSocketFrame webSocketFrame2;
        ByteBuf byteBuf;
        if (this.encoder == null) {
            this.encoder = new EmbeddedChannel(ZlibCodecFactory.newZlibEncoder(ZlibWrapper.NONE, this.compressionLevel, this.windowSize, 8));
        }
        this.encoder.writeOutbound(webSocketFrame.content().retain());
        CompositeByteBuf compositeByteBuf = channelHandlerContext.alloc().compositeBuffer();
        while ((byteBuf = (ByteBuf)this.encoder.readOutbound()) != null) {
            if (!byteBuf.isReadable()) {
                byteBuf.release();
                continue;
            }
            compositeByteBuf.addComponent(true, byteBuf);
        }
        if (compositeByteBuf.numComponents() <= 0) {
            compositeByteBuf.release();
            throw new CodecException("cannot read compressed buffer");
        }
        if (webSocketFrame.isFinalFragment() && this.noContext) {
            this.cleanup();
        }
        if (this.removeFrameTail(webSocketFrame)) {
            int n = compositeByteBuf.readableBytes() - PerMessageDeflateDecoder.FRAME_TAIL.length;
            byteBuf = compositeByteBuf.slice(0, n);
        } else {
            byteBuf = compositeByteBuf;
        }
        if (webSocketFrame instanceof TextWebSocketFrame) {
            webSocketFrame2 = new TextWebSocketFrame(webSocketFrame.isFinalFragment(), this.rsv(webSocketFrame), byteBuf);
        } else if (webSocketFrame instanceof BinaryWebSocketFrame) {
            webSocketFrame2 = new BinaryWebSocketFrame(webSocketFrame.isFinalFragment(), this.rsv(webSocketFrame), byteBuf);
        } else if (webSocketFrame instanceof ContinuationWebSocketFrame) {
            webSocketFrame2 = new ContinuationWebSocketFrame(webSocketFrame.isFinalFragment(), this.rsv(webSocketFrame), byteBuf);
        } else {
            throw new CodecException("unexpected frame type: " + webSocketFrame.getClass().getName());
        }
        list.add(webSocketFrame2);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.cleanup();
        super.handlerRemoved(channelHandlerContext);
    }

    private void cleanup() {
        if (this.encoder != null) {
            if (this.encoder.finish()) {
                ByteBuf byteBuf;
                while ((byteBuf = (ByteBuf)this.encoder.readOutbound()) != null) {
                    byteBuf.release();
                }
            }
            this.encoder = null;
        }
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object object, List list) throws Exception {
        this.encode(channelHandlerContext, (WebSocketFrame)object, (List<Object>)list);
    }
}

