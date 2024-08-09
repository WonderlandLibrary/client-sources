/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http.websocketx;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.CorruptedFrameException;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.Utf8Validator;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

public class Utf8FrameValidator
extends ChannelInboundHandlerAdapter {
    private int fragmentedFramesCount;
    private Utf8Validator utf8Validator;

    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object object) throws Exception {
        if (object instanceof WebSocketFrame) {
            WebSocketFrame webSocketFrame = (WebSocketFrame)object;
            if (((WebSocketFrame)object).isFinalFragment()) {
                if (!(webSocketFrame instanceof PingWebSocketFrame)) {
                    this.fragmentedFramesCount = 0;
                    if (webSocketFrame instanceof TextWebSocketFrame || this.utf8Validator != null && this.utf8Validator.isChecking()) {
                        this.checkUTF8String(channelHandlerContext, webSocketFrame.content());
                        this.utf8Validator.finish();
                    }
                }
            } else {
                if (this.fragmentedFramesCount == 0) {
                    if (webSocketFrame instanceof TextWebSocketFrame) {
                        this.checkUTF8String(channelHandlerContext, webSocketFrame.content());
                    }
                } else if (this.utf8Validator != null && this.utf8Validator.isChecking()) {
                    this.checkUTF8String(channelHandlerContext, webSocketFrame.content());
                }
                ++this.fragmentedFramesCount;
            }
        }
        super.channelRead(channelHandlerContext, object);
    }

    private void checkUTF8String(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) {
        block3: {
            try {
                if (this.utf8Validator == null) {
                    this.utf8Validator = new Utf8Validator();
                }
                this.utf8Validator.check(byteBuf);
            } catch (CorruptedFrameException corruptedFrameException) {
                if (!channelHandlerContext.channel().isActive()) break block3;
                channelHandlerContext.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
            }
        }
    }
}

