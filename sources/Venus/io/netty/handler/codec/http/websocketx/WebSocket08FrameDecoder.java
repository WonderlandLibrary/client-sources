/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http.websocketx;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;
import io.netty.handler.codec.TooLongFrameException;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.ContinuationWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.Utf8Validator;
import io.netty.handler.codec.http.websocketx.WebSocketFrameDecoder;
import io.netty.util.ReferenceCounted;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.nio.ByteOrder;
import java.util.List;

public class WebSocket08FrameDecoder
extends ByteToMessageDecoder
implements WebSocketFrameDecoder {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(WebSocket08FrameDecoder.class);
    private static final byte OPCODE_CONT = 0;
    private static final byte OPCODE_TEXT = 1;
    private static final byte OPCODE_BINARY = 2;
    private static final byte OPCODE_CLOSE = 8;
    private static final byte OPCODE_PING = 9;
    private static final byte OPCODE_PONG = 10;
    private final long maxFramePayloadLength;
    private final boolean allowExtensions;
    private final boolean expectMaskedFrames;
    private final boolean allowMaskMismatch;
    private int fragmentedFramesCount;
    private boolean frameFinalFlag;
    private boolean frameMasked;
    private int frameRsv;
    private int frameOpcode;
    private long framePayloadLength;
    private byte[] maskingKey;
    private int framePayloadLen1;
    private boolean receivedClosingHandshake;
    private State state = State.READING_FIRST;

    public WebSocket08FrameDecoder(boolean bl, boolean bl2, int n) {
        this(bl, bl2, n, false);
    }

    public WebSocket08FrameDecoder(boolean bl, boolean bl2, int n, boolean bl3) {
        this.expectMaskedFrames = bl;
        this.allowMaskMismatch = bl3;
        this.allowExtensions = bl2;
        this.maxFramePayloadLength = n;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (this.receivedClosingHandshake) {
            byteBuf.skipBytes(this.actualReadableBytes());
            return;
        }
        switch (1.$SwitchMap$io$netty$handler$codec$http$websocketx$WebSocket08FrameDecoder$State[this.state.ordinal()]) {
            case 1: {
                if (!byteBuf.isReadable()) {
                    return;
                }
                this.framePayloadLength = 0L;
                byte by = byteBuf.readByte();
                this.frameFinalFlag = (by & 0x80) != 0;
                this.frameRsv = (by & 0x70) >> 4;
                this.frameOpcode = by & 0xF;
                if (logger.isDebugEnabled()) {
                    logger.debug("Decoding WebSocket Frame opCode={}", (Object)this.frameOpcode);
                }
                this.state = State.READING_SECOND;
            }
            case 2: {
                if (!byteBuf.isReadable()) {
                    return;
                }
                byte by = byteBuf.readByte();
                this.frameMasked = (by & 0x80) != 0;
                this.framePayloadLen1 = by & 0x7F;
                if (this.frameRsv != 0 && !this.allowExtensions) {
                    this.protocolViolation(channelHandlerContext, "RSV != 0 and no extension negotiated, RSV:" + this.frameRsv);
                    return;
                }
                if (!this.allowMaskMismatch && this.expectMaskedFrames != this.frameMasked) {
                    this.protocolViolation(channelHandlerContext, "received a frame that is not masked as expected");
                    return;
                }
                if (this.frameOpcode > 7) {
                    if (!this.frameFinalFlag) {
                        this.protocolViolation(channelHandlerContext, "fragmented control frame");
                        return;
                    }
                    if (this.framePayloadLen1 > 125) {
                        this.protocolViolation(channelHandlerContext, "control frame with payload length > 125 octets");
                        return;
                    }
                    if (this.frameOpcode != 8 && this.frameOpcode != 9 && this.frameOpcode != 10) {
                        this.protocolViolation(channelHandlerContext, "control frame using reserved opcode " + this.frameOpcode);
                        return;
                    }
                    if (this.frameOpcode == 8 && this.framePayloadLen1 == 1) {
                        this.protocolViolation(channelHandlerContext, "received close control frame with payload len 1");
                        return;
                    }
                } else {
                    if (this.frameOpcode != 0 && this.frameOpcode != 1 && this.frameOpcode != 2) {
                        this.protocolViolation(channelHandlerContext, "data frame using reserved opcode " + this.frameOpcode);
                        return;
                    }
                    if (this.fragmentedFramesCount == 0 && this.frameOpcode == 0) {
                        this.protocolViolation(channelHandlerContext, "received continuation data frame outside fragmented message");
                        return;
                    }
                    if (this.fragmentedFramesCount != 0 && this.frameOpcode != 0 && this.frameOpcode != 9) {
                        this.protocolViolation(channelHandlerContext, "received non-continuation data frame while inside fragmented message");
                        return;
                    }
                }
                this.state = State.READING_SIZE;
            }
            case 3: {
                if (this.framePayloadLen1 == 126) {
                    if (byteBuf.readableBytes() < 2) {
                        return;
                    }
                    this.framePayloadLength = byteBuf.readUnsignedShort();
                    if (this.framePayloadLength < 126L) {
                        this.protocolViolation(channelHandlerContext, "invalid data frame length (not using minimal length encoding)");
                        return;
                    }
                } else if (this.framePayloadLen1 == 127) {
                    if (byteBuf.readableBytes() < 8) {
                        return;
                    }
                    this.framePayloadLength = byteBuf.readLong();
                    if (this.framePayloadLength < 65536L) {
                        this.protocolViolation(channelHandlerContext, "invalid data frame length (not using minimal length encoding)");
                        return;
                    }
                } else {
                    this.framePayloadLength = this.framePayloadLen1;
                }
                if (this.framePayloadLength > this.maxFramePayloadLength) {
                    this.protocolViolation(channelHandlerContext, "Max frame length of " + this.maxFramePayloadLength + " has been exceeded.");
                    return;
                }
                if (logger.isDebugEnabled()) {
                    logger.debug("Decoding WebSocket Frame length={}", (Object)this.framePayloadLength);
                }
                this.state = State.MASKING_KEY;
            }
            case 4: {
                if (this.frameMasked) {
                    if (byteBuf.readableBytes() < 4) {
                        return;
                    }
                    if (this.maskingKey == null) {
                        this.maskingKey = new byte[4];
                    }
                    byteBuf.readBytes(this.maskingKey);
                }
                this.state = State.PAYLOAD;
            }
            case 5: {
                if ((long)byteBuf.readableBytes() < this.framePayloadLength) {
                    return;
                }
                ReferenceCounted referenceCounted = null;
                try {
                    referenceCounted = ByteBufUtil.readBytes(channelHandlerContext.alloc(), byteBuf, WebSocket08FrameDecoder.toFrameLength(this.framePayloadLength));
                    this.state = State.READING_FIRST;
                    if (this.frameMasked) {
                        this.unmask((ByteBuf)referenceCounted);
                    }
                    if (this.frameOpcode == 9) {
                        list.add(new PingWebSocketFrame(this.frameFinalFlag, this.frameRsv, (ByteBuf)referenceCounted));
                        referenceCounted = null;
                        return;
                    }
                    if (this.frameOpcode == 10) {
                        list.add(new PongWebSocketFrame(this.frameFinalFlag, this.frameRsv, (ByteBuf)referenceCounted));
                        referenceCounted = null;
                        return;
                    }
                    if (this.frameOpcode == 8) {
                        this.receivedClosingHandshake = true;
                        this.checkCloseFrameBody(channelHandlerContext, (ByteBuf)referenceCounted);
                        list.add(new CloseWebSocketFrame(this.frameFinalFlag, this.frameRsv, (ByteBuf)referenceCounted));
                        referenceCounted = null;
                        return;
                    }
                    if (this.frameFinalFlag) {
                        if (this.frameOpcode != 9) {
                            this.fragmentedFramesCount = 0;
                        }
                    } else {
                        ++this.fragmentedFramesCount;
                    }
                    if (this.frameOpcode == 1) {
                        list.add(new TextWebSocketFrame(this.frameFinalFlag, this.frameRsv, (ByteBuf)referenceCounted));
                        referenceCounted = null;
                        return;
                    }
                    if (this.frameOpcode == 2) {
                        list.add(new BinaryWebSocketFrame(this.frameFinalFlag, this.frameRsv, (ByteBuf)referenceCounted));
                        referenceCounted = null;
                        return;
                    }
                    if (this.frameOpcode == 0) {
                        list.add(new ContinuationWebSocketFrame(this.frameFinalFlag, this.frameRsv, (ByteBuf)referenceCounted));
                        referenceCounted = null;
                        return;
                    }
                    throw new UnsupportedOperationException("Cannot decode web socket frame with opcode: " + this.frameOpcode);
                } finally {
                    if (referenceCounted != null) {
                        referenceCounted.release();
                    }
                }
            }
            case 6: {
                if (byteBuf.isReadable()) {
                    byteBuf.readByte();
                }
                return;
            }
        }
        throw new Error("Shouldn't reach here.");
    }

    private void unmask(ByteBuf byteBuf) {
        int n = byteBuf.readerIndex();
        int n2 = byteBuf.writerIndex();
        ByteOrder byteOrder = byteBuf.order();
        int n3 = (this.maskingKey[0] & 0xFF) << 24 | (this.maskingKey[1] & 0xFF) << 16 | (this.maskingKey[2] & 0xFF) << 8 | this.maskingKey[3] & 0xFF;
        if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
            n3 = Integer.reverseBytes(n3);
        }
        while (n + 3 < n2) {
            int n4 = byteBuf.getInt(n) ^ n3;
            byteBuf.setInt(n, n4);
            n += 4;
        }
        while (n < n2) {
            byteBuf.setByte(n, byteBuf.getByte(n) ^ this.maskingKey[n % 4]);
            ++n;
        }
    }

    private void protocolViolation(ChannelHandlerContext channelHandlerContext, String string) {
        this.protocolViolation(channelHandlerContext, new CorruptedFrameException(string));
    }

    private void protocolViolation(ChannelHandlerContext channelHandlerContext, CorruptedFrameException corruptedFrameException) {
        this.state = State.CORRUPT;
        if (channelHandlerContext.channel().isActive()) {
            ReferenceCounted referenceCounted = this.receivedClosingHandshake ? Unpooled.EMPTY_BUFFER : new CloseWebSocketFrame(1002, null);
            channelHandlerContext.writeAndFlush(referenceCounted).addListener(ChannelFutureListener.CLOSE);
        }
        throw corruptedFrameException;
    }

    private static int toFrameLength(long l) {
        if (l > Integer.MAX_VALUE) {
            throw new TooLongFrameException("Length:" + l);
        }
        return (int)l;
    }

    protected void checkCloseFrameBody(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) {
        if (byteBuf == null || !byteBuf.isReadable()) {
            return;
        }
        if (byteBuf.readableBytes() == 1) {
            this.protocolViolation(channelHandlerContext, "Invalid close frame body");
        }
        int n = byteBuf.readerIndex();
        byteBuf.readerIndex(0);
        short s = byteBuf.readShort();
        if (s >= 0 && s <= 999 || s >= 1004 && s <= 1006 || s >= 1012 && s <= 2999) {
            this.protocolViolation(channelHandlerContext, "Invalid close frame getStatus code: " + s);
        }
        if (byteBuf.isReadable()) {
            try {
                new Utf8Validator().check(byteBuf);
            } catch (CorruptedFrameException corruptedFrameException) {
                this.protocolViolation(channelHandlerContext, corruptedFrameException);
            }
        }
        byteBuf.readerIndex(n);
    }

    static enum State {
        READING_FIRST,
        READING_SECOND,
        READING_SIZE,
        MASKING_KEY,
        PAYLOAD,
        CORRUPT;

    }
}

