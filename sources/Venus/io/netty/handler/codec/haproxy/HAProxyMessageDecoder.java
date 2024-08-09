/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.haproxy;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.ProtocolDetectionResult;
import io.netty.handler.codec.haproxy.HAProxyMessage;
import io.netty.handler.codec.haproxy.HAProxyProtocolException;
import io.netty.handler.codec.haproxy.HAProxyProtocolVersion;
import io.netty.util.CharsetUtil;
import java.util.List;

public class HAProxyMessageDecoder
extends ByteToMessageDecoder {
    private static final int V1_MAX_LENGTH = 108;
    private static final int V2_MAX_LENGTH = 65551;
    private static final int V2_MIN_LENGTH = 232;
    private static final int V2_MAX_TLV = 65319;
    private static final int DELIMITER_LENGTH = 2;
    private static final byte[] BINARY_PREFIX = new byte[]{13, 10, 13, 10, 0, 13, 10, 81, 85, 73, 84, 10};
    private static final byte[] TEXT_PREFIX = new byte[]{80, 82, 79, 88, 89};
    private static final int BINARY_PREFIX_LENGTH = BINARY_PREFIX.length;
    private static final ProtocolDetectionResult<HAProxyProtocolVersion> DETECTION_RESULT_V1 = ProtocolDetectionResult.detected(HAProxyProtocolVersion.V1);
    private static final ProtocolDetectionResult<HAProxyProtocolVersion> DETECTION_RESULT_V2 = ProtocolDetectionResult.detected(HAProxyProtocolVersion.V2);
    private boolean discarding;
    private int discardedBytes;
    private boolean finished;
    private int version = -1;
    private final int v2MaxHeaderSize;

    public HAProxyMessageDecoder() {
        this.v2MaxHeaderSize = 65551;
    }

    public HAProxyMessageDecoder(int n) {
        int n2;
        this.v2MaxHeaderSize = n < 1 ? 232 : (n > 65319 ? 65551 : ((n2 = n + 232) > 65551 ? 65551 : n2));
    }

    private static int findVersion(ByteBuf byteBuf) {
        int n = byteBuf.readableBytes();
        if (n < 13) {
            return 1;
        }
        int n2 = byteBuf.readerIndex();
        return HAProxyMessageDecoder.match(BINARY_PREFIX, byteBuf, n2) ? (int)byteBuf.getByte(n2 + BINARY_PREFIX_LENGTH) : 1;
    }

    private static int findEndOfHeader(ByteBuf byteBuf) {
        int n = byteBuf.readableBytes();
        if (n < 16) {
            return 1;
        }
        int n2 = byteBuf.readerIndex() + 14;
        int n3 = 16 + byteBuf.getUnsignedShort(n2);
        if (n >= n3) {
            return n3;
        }
        return 1;
    }

    private static int findEndOfLine(ByteBuf byteBuf) {
        int n = byteBuf.writerIndex();
        for (int i = byteBuf.readerIndex(); i < n; ++i) {
            byte by = byteBuf.getByte(i);
            if (by != 13 || i >= n - 1 || byteBuf.getByte(i + 1) != 10) continue;
            return i;
        }
        return 1;
    }

    @Override
    public boolean isSingleDecode() {
        return false;
    }

    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object object) throws Exception {
        super.channelRead(channelHandlerContext, object);
        if (this.finished) {
            channelHandlerContext.pipeline().remove(this);
        }
    }

    @Override
    protected final void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (this.version == -1 && (this.version = HAProxyMessageDecoder.findVersion(byteBuf)) == -1) {
            return;
        }
        ByteBuf byteBuf2 = this.version == 1 ? this.decodeLine(channelHandlerContext, byteBuf) : this.decodeStruct(channelHandlerContext, byteBuf);
        if (byteBuf2 != null) {
            this.finished = true;
            try {
                if (this.version == 1) {
                    list.add(HAProxyMessage.decodeHeader(byteBuf2.toString(CharsetUtil.US_ASCII)));
                } else {
                    list.add(HAProxyMessage.decodeHeader(byteBuf2));
                }
            } catch (HAProxyProtocolException hAProxyProtocolException) {
                this.fail(channelHandlerContext, null, hAProxyProtocolException);
            }
        }
    }

    private ByteBuf decodeStruct(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        int n = HAProxyMessageDecoder.findEndOfHeader(byteBuf);
        if (!this.discarding) {
            if (n >= 0) {
                int n2 = n - byteBuf.readerIndex();
                if (n2 > this.v2MaxHeaderSize) {
                    byteBuf.readerIndex(n);
                    this.failOverLimit(channelHandlerContext, n2);
                    return null;
                }
                return byteBuf.readSlice(n2);
            }
            int n3 = byteBuf.readableBytes();
            if (n3 > this.v2MaxHeaderSize) {
                this.discardedBytes = n3;
                byteBuf.skipBytes(n3);
                this.discarding = true;
                this.failOverLimit(channelHandlerContext, "over " + this.discardedBytes);
            }
            return null;
        }
        if (n >= 0) {
            byteBuf.readerIndex(n);
            this.discardedBytes = 0;
            this.discarding = false;
        } else {
            this.discardedBytes = byteBuf.readableBytes();
            byteBuf.skipBytes(this.discardedBytes);
        }
        return null;
    }

    private ByteBuf decodeLine(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        int n = HAProxyMessageDecoder.findEndOfLine(byteBuf);
        if (!this.discarding) {
            if (n >= 0) {
                int n2 = n - byteBuf.readerIndex();
                if (n2 > 108) {
                    byteBuf.readerIndex(n + 2);
                    this.failOverLimit(channelHandlerContext, n2);
                    return null;
                }
                ByteBuf byteBuf2 = byteBuf.readSlice(n2);
                byteBuf.skipBytes(2);
                return byteBuf2;
            }
            int n3 = byteBuf.readableBytes();
            if (n3 > 108) {
                this.discardedBytes = n3;
                byteBuf.skipBytes(n3);
                this.discarding = true;
                this.failOverLimit(channelHandlerContext, "over " + this.discardedBytes);
            }
            return null;
        }
        if (n >= 0) {
            int n4 = byteBuf.getByte(n) == 13 ? 2 : 1;
            byteBuf.readerIndex(n + n4);
            this.discardedBytes = 0;
            this.discarding = false;
        } else {
            this.discardedBytes = byteBuf.readableBytes();
            byteBuf.skipBytes(this.discardedBytes);
        }
        return null;
    }

    private void failOverLimit(ChannelHandlerContext channelHandlerContext, int n) {
        this.failOverLimit(channelHandlerContext, String.valueOf(n));
    }

    private void failOverLimit(ChannelHandlerContext channelHandlerContext, String string) {
        int n = this.version == 1 ? 108 : this.v2MaxHeaderSize;
        this.fail(channelHandlerContext, "header length (" + string + ") exceeds the allowed maximum (" + n + ')', null);
    }

    private void fail(ChannelHandlerContext channelHandlerContext, String string, Exception exception) {
        this.finished = true;
        channelHandlerContext.close();
        HAProxyProtocolException hAProxyProtocolException = string != null && exception != null ? new HAProxyProtocolException(string, exception) : (string != null ? new HAProxyProtocolException(string) : (exception != null ? new HAProxyProtocolException(exception) : new HAProxyProtocolException()));
        throw hAProxyProtocolException;
    }

    public static ProtocolDetectionResult<HAProxyProtocolVersion> detectProtocol(ByteBuf byteBuf) {
        if (byteBuf.readableBytes() < 12) {
            return ProtocolDetectionResult.needsMoreData();
        }
        int n = byteBuf.readerIndex();
        if (HAProxyMessageDecoder.match(BINARY_PREFIX, byteBuf, n)) {
            return DETECTION_RESULT_V2;
        }
        if (HAProxyMessageDecoder.match(TEXT_PREFIX, byteBuf, n)) {
            return DETECTION_RESULT_V1;
        }
        return ProtocolDetectionResult.invalid();
    }

    private static boolean match(byte[] byArray, ByteBuf byteBuf, int n) {
        for (int i = 0; i < byArray.length; ++i) {
            byte by = byteBuf.getByte(n + i);
            if (by == byArray[i]) continue;
            return true;
        }
        return false;
    }
}

