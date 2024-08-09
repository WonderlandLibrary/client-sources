/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http.websocketx;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.TooLongFrameException;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.ContinuationWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrameEncoder;
import io.netty.util.ReferenceCounted;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;

public class WebSocket08FrameEncoder
extends MessageToMessageEncoder<WebSocketFrame>
implements WebSocketFrameEncoder {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(WebSocket08FrameEncoder.class);
    private static final byte OPCODE_CONT = 0;
    private static final byte OPCODE_TEXT = 1;
    private static final byte OPCODE_BINARY = 2;
    private static final byte OPCODE_CLOSE = 8;
    private static final byte OPCODE_PING = 9;
    private static final byte OPCODE_PONG = 10;
    private static final int GATHERING_WRITE_THRESHOLD = 1024;
    private final boolean maskPayload;

    public WebSocket08FrameEncoder(boolean bl) {
        this.maskPayload = bl;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, WebSocketFrame webSocketFrame, List<Object> list) throws Exception {
        int n;
        ByteBuf byteBuf = webSocketFrame.content();
        if (webSocketFrame instanceof TextWebSocketFrame) {
            n = 1;
        } else if (webSocketFrame instanceof PingWebSocketFrame) {
            n = 9;
        } else if (webSocketFrame instanceof PongWebSocketFrame) {
            n = 10;
        } else if (webSocketFrame instanceof CloseWebSocketFrame) {
            n = 8;
        } else if (webSocketFrame instanceof BinaryWebSocketFrame) {
            n = 2;
        } else if (webSocketFrame instanceof ContinuationWebSocketFrame) {
            n = 0;
        } else {
            throw new UnsupportedOperationException("Cannot encode frame of type: " + webSocketFrame.getClass().getName());
        }
        int n2 = byteBuf.readableBytes();
        if (logger.isDebugEnabled()) {
            logger.debug("Encoding WebSocket Frame opCode=" + n + " length=" + n2);
        }
        int n3 = 0;
        if (webSocketFrame.isFinalFragment()) {
            n3 |= 0x80;
        }
        n3 |= webSocketFrame.rsv() % 8 << 4;
        n3 |= n % 128;
        if (n == 9 && n2 > 125) {
            throw new TooLongFrameException("invalid payload for PING (payload length must be <= 125, was " + n2);
        }
        boolean bl = true;
        ReferenceCounted referenceCounted = null;
        try {
            int n4;
            int n5;
            int n6 = n5 = this.maskPayload ? 4 : 0;
            if (n2 <= 125) {
                n4 = 2 + n5;
                if (this.maskPayload || n2 <= 1024) {
                    n4 += n2;
                }
                referenceCounted = channelHandlerContext.alloc().buffer(n4);
                ((ByteBuf)referenceCounted).writeByte(n3);
                byte by = this.maskPayload ? (byte)(0x80 | (byte)n2) : (byte)n2;
                ((ByteBuf)referenceCounted).writeByte(by);
            } else if (n2 <= 65535) {
                n4 = 4 + n5;
                if (this.maskPayload || n2 <= 1024) {
                    n4 += n2;
                }
                referenceCounted = channelHandlerContext.alloc().buffer(n4);
                ((ByteBuf)referenceCounted).writeByte(n3);
                ((ByteBuf)referenceCounted).writeByte(this.maskPayload ? 254 : 126);
                ((ByteBuf)referenceCounted).writeByte(n2 >>> 8 & 0xFF);
                ((ByteBuf)referenceCounted).writeByte(n2 & 0xFF);
            } else {
                n4 = 10 + n5;
                if (this.maskPayload || n2 <= 1024) {
                    n4 += n2;
                }
                referenceCounted = channelHandlerContext.alloc().buffer(n4);
                ((ByteBuf)referenceCounted).writeByte(n3);
                ((ByteBuf)referenceCounted).writeByte(this.maskPayload ? 255 : 127);
                ((ByteBuf)referenceCounted).writeLong(n2);
            }
            if (this.maskPayload) {
                int n7;
                n4 = (int)(Math.random() * 2.147483647E9);
                byte[] byArray = ByteBuffer.allocate(4).putInt(n4).array();
                ((ByteBuf)referenceCounted).writeBytes(byArray);
                ByteOrder byteOrder = byteBuf.order();
                ByteOrder byteOrder2 = ((ByteBuf)referenceCounted).order();
                int n8 = 0;
                int n9 = byteBuf.readerIndex();
                int n10 = byteBuf.writerIndex();
                if (byteOrder == byteOrder2) {
                    n7 = (byArray[0] & 0xFF) << 24 | (byArray[1] & 0xFF) << 16 | (byArray[2] & 0xFF) << 8 | byArray[3] & 0xFF;
                    if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
                        n7 = Integer.reverseBytes(n7);
                    }
                    while (n9 + 3 < n10) {
                        int n11 = byteBuf.getInt(n9);
                        ((ByteBuf)referenceCounted).writeInt(n11 ^ n7);
                        n9 += 4;
                    }
                }
                while (n9 < n10) {
                    n7 = byteBuf.getByte(n9);
                    ((ByteBuf)referenceCounted).writeByte(n7 ^ byArray[n8++ % 4]);
                    ++n9;
                }
                list.add(referenceCounted);
            } else if (((ByteBuf)referenceCounted).writableBytes() >= byteBuf.readableBytes()) {
                ((ByteBuf)referenceCounted).writeBytes(byteBuf);
                list.add(referenceCounted);
            } else {
                list.add(referenceCounted);
                list.add(byteBuf.retain());
            }
            bl = false;
        } finally {
            if (bl && referenceCounted != null) {
                referenceCounted.release();
            }
        }
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object object, List list) throws Exception {
        this.encode(channelHandlerContext, (WebSocketFrame)object, (List<Object>)list);
    }
}

