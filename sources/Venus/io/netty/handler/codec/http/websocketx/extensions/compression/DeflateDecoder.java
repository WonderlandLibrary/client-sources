/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http.websocketx.extensions.compression;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.CodecException;
import io.netty.handler.codec.compression.ZlibCodecFactory;
import io.netty.handler.codec.compression.ZlibWrapper;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.ContinuationWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.extensions.WebSocketExtensionDecoder;
import io.netty.util.ReferenceCounted;
import java.util.List;

abstract class DeflateDecoder
extends WebSocketExtensionDecoder {
    static final byte[] FRAME_TAIL = new byte[]{0, 0, -1, -1};
    private final boolean noContext;
    private EmbeddedChannel decoder;

    public DeflateDecoder(boolean bl) {
        this.noContext = bl;
    }

    protected abstract boolean appendFrameTail(WebSocketFrame var1);

    protected abstract int newRsv(WebSocketFrame var1);

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, WebSocketFrame webSocketFrame, List<Object> list) throws Exception {
        ReferenceCounted referenceCounted;
        if (this.decoder == null) {
            if (!(webSocketFrame instanceof TextWebSocketFrame) && !(webSocketFrame instanceof BinaryWebSocketFrame)) {
                throw new CodecException("unexpected initial frame type: " + webSocketFrame.getClass().getName());
            }
            this.decoder = new EmbeddedChannel(ZlibCodecFactory.newZlibDecoder(ZlibWrapper.NONE));
        }
        boolean bl = webSocketFrame.content().isReadable();
        this.decoder.writeInbound(webSocketFrame.content().retain());
        if (this.appendFrameTail(webSocketFrame)) {
            this.decoder.writeInbound(Unpooled.wrappedBuffer(FRAME_TAIL));
        }
        CompositeByteBuf compositeByteBuf = channelHandlerContext.alloc().compositeBuffer();
        while ((referenceCounted = (ByteBuf)this.decoder.readInbound()) != null) {
            if (!referenceCounted.isReadable()) {
                referenceCounted.release();
                continue;
            }
            compositeByteBuf.addComponent(true, (ByteBuf)referenceCounted);
        }
        if (bl && compositeByteBuf.numComponents() <= 0) {
            compositeByteBuf.release();
            throw new CodecException("cannot read uncompressed buffer");
        }
        if (webSocketFrame.isFinalFragment() && this.noContext) {
            this.cleanup();
        }
        if (webSocketFrame instanceof TextWebSocketFrame) {
            referenceCounted = new TextWebSocketFrame(webSocketFrame.isFinalFragment(), this.newRsv(webSocketFrame), compositeByteBuf);
        } else if (webSocketFrame instanceof BinaryWebSocketFrame) {
            referenceCounted = new BinaryWebSocketFrame(webSocketFrame.isFinalFragment(), this.newRsv(webSocketFrame), compositeByteBuf);
        } else if (webSocketFrame instanceof ContinuationWebSocketFrame) {
            referenceCounted = new ContinuationWebSocketFrame(webSocketFrame.isFinalFragment(), this.newRsv(webSocketFrame), compositeByteBuf);
        } else {
            throw new CodecException("unexpected frame type: " + webSocketFrame.getClass().getName());
        }
        list.add(referenceCounted);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.cleanup();
        super.handlerRemoved(channelHandlerContext);
    }

    @Override
    public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.cleanup();
        super.channelInactive(channelHandlerContext);
    }

    private void cleanup() {
        if (this.decoder != null) {
            if (this.decoder.finish()) {
                ByteBuf byteBuf;
                while ((byteBuf = (ByteBuf)this.decoder.readOutbound()) != null) {
                    byteBuf.release();
                }
            }
            this.decoder = null;
        }
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, Object object, List list) throws Exception {
        this.decode(channelHandlerContext, (WebSocketFrame)object, (List<Object>)list);
    }
}

