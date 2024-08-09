/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.DefaultChannelPromise;
import io.netty.handler.codec.http2.Http2Error;
import io.netty.handler.codec.http2.Http2Exception;
import io.netty.handler.codec.http2.Http2Flags;
import io.netty.handler.codec.http2.StreamByteDistributor;
import io.netty.util.AsciiString;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Promise;
import java.util.concurrent.TimeUnit;

public final class Http2CodecUtil {
    public static final int CONNECTION_STREAM_ID = 0;
    public static final int HTTP_UPGRADE_STREAM_ID = 1;
    public static final CharSequence HTTP_UPGRADE_SETTINGS_HEADER = AsciiString.cached("HTTP2-Settings");
    public static final CharSequence HTTP_UPGRADE_PROTOCOL_NAME = "h2c";
    public static final CharSequence TLS_UPGRADE_PROTOCOL_NAME = "h2";
    public static final int PING_FRAME_PAYLOAD_LENGTH = 8;
    public static final short MAX_UNSIGNED_BYTE = 255;
    public static final int MAX_PADDING = 256;
    public static final long MAX_UNSIGNED_INT = 0xFFFFFFFFL;
    public static final int FRAME_HEADER_LENGTH = 9;
    public static final int SETTING_ENTRY_LENGTH = 6;
    public static final int PRIORITY_ENTRY_LENGTH = 5;
    public static final int INT_FIELD_LENGTH = 4;
    public static final short MAX_WEIGHT = 256;
    public static final short MIN_WEIGHT = 1;
    private static final ByteBuf CONNECTION_PREFACE = Unpooled.unreleasableBuffer(Unpooled.directBuffer(24).writeBytes("PRI * HTTP/2.0\r\n\r\nSM\r\n\r\n".getBytes(CharsetUtil.UTF_8))).asReadOnly();
    private static final ByteBuf EMPTY_PING = Unpooled.unreleasableBuffer(Unpooled.directBuffer(8).writeZero(8)).asReadOnly();
    private static final int MAX_PADDING_LENGTH_LENGTH = 1;
    public static final int DATA_FRAME_HEADER_LENGTH = 10;
    public static final int HEADERS_FRAME_HEADER_LENGTH = 15;
    public static final int PRIORITY_FRAME_LENGTH = 14;
    public static final int RST_STREAM_FRAME_LENGTH = 13;
    public static final int PUSH_PROMISE_FRAME_HEADER_LENGTH = 14;
    public static final int GO_AWAY_FRAME_HEADER_LENGTH = 17;
    public static final int WINDOW_UPDATE_FRAME_LENGTH = 13;
    public static final int CONTINUATION_FRAME_HEADER_LENGTH = 10;
    public static final char SETTINGS_HEADER_TABLE_SIZE = '\u0001';
    public static final char SETTINGS_ENABLE_PUSH = '\u0002';
    public static final char SETTINGS_MAX_CONCURRENT_STREAMS = '\u0003';
    public static final char SETTINGS_INITIAL_WINDOW_SIZE = '\u0004';
    public static final char SETTINGS_MAX_FRAME_SIZE = '\u0005';
    public static final char SETTINGS_MAX_HEADER_LIST_SIZE = '\u0006';
    public static final int NUM_STANDARD_SETTINGS = 6;
    public static final long MAX_HEADER_TABLE_SIZE = 0xFFFFFFFFL;
    public static final long MAX_CONCURRENT_STREAMS = 0xFFFFFFFFL;
    public static final int MAX_INITIAL_WINDOW_SIZE = Integer.MAX_VALUE;
    public static final int MAX_FRAME_SIZE_LOWER_BOUND = 16384;
    public static final int MAX_FRAME_SIZE_UPPER_BOUND = 0xFFFFFF;
    public static final long MAX_HEADER_LIST_SIZE = 0xFFFFFFFFL;
    public static final long MIN_HEADER_TABLE_SIZE = 0L;
    public static final long MIN_CONCURRENT_STREAMS = 0L;
    public static final int MIN_INITIAL_WINDOW_SIZE = 0;
    public static final long MIN_HEADER_LIST_SIZE = 0L;
    public static final int DEFAULT_WINDOW_SIZE = 65535;
    public static final short DEFAULT_PRIORITY_WEIGHT = 16;
    public static final int DEFAULT_HEADER_TABLE_SIZE = 4096;
    public static final long DEFAULT_HEADER_LIST_SIZE = 8192L;
    public static final int DEFAULT_MAX_FRAME_SIZE = 16384;
    public static final int SMALLEST_MAX_CONCURRENT_STREAMS = 100;
    static final int DEFAULT_MAX_RESERVED_STREAMS = 100;
    static final int DEFAULT_MIN_ALLOCATION_CHUNK = 1024;
    static final int DEFAULT_INITIAL_HUFFMAN_DECODE_CAPACITY = 32;
    public static final long DEFAULT_GRACEFUL_SHUTDOWN_TIMEOUT_MILLIS = TimeUnit.MILLISECONDS.convert(30L, TimeUnit.SECONDS);

    public static long calculateMaxHeaderListSizeGoAway(long l) {
        return l + (l >>> 2);
    }

    public static boolean isOutboundStream(boolean bl, int n) {
        boolean bl2 = (n & 1) == 0;
        return n > 0 && bl == bl2;
    }

    public static boolean isStreamIdValid(int n) {
        return n >= 0;
    }

    public static boolean isMaxFrameSizeValid(int n) {
        return n >= 16384 && n <= 0xFFFFFF;
    }

    public static ByteBuf connectionPrefaceBuf() {
        return CONNECTION_PREFACE.retainedDuplicate();
    }

    public static ByteBuf emptyPingBuf() {
        return EMPTY_PING.retainedDuplicate();
    }

    public static Http2Exception getEmbeddedHttp2Exception(Throwable throwable) {
        while (throwable != null) {
            if (throwable instanceof Http2Exception) {
                return (Http2Exception)throwable;
            }
            throwable = throwable.getCause();
        }
        return null;
    }

    public static ByteBuf toByteBuf(ChannelHandlerContext channelHandlerContext, Throwable throwable) {
        if (throwable == null || throwable.getMessage() == null) {
            return Unpooled.EMPTY_BUFFER;
        }
        return ByteBufUtil.writeUtf8(channelHandlerContext.alloc(), (CharSequence)throwable.getMessage());
    }

    public static int readUnsignedInt(ByteBuf byteBuf) {
        return byteBuf.readInt() & Integer.MAX_VALUE;
    }

    public static void writeFrameHeader(ByteBuf byteBuf, int n, byte by, Http2Flags http2Flags, int n2) {
        byteBuf.ensureWritable(9 + n);
        Http2CodecUtil.writeFrameHeaderInternal(byteBuf, n, by, http2Flags, n2);
    }

    public static int streamableBytes(StreamByteDistributor.StreamState streamState) {
        return Math.max(0, (int)Math.min(streamState.pendingBytes(), (long)streamState.windowSize()));
    }

    public static void headerListSizeExceeded(int n, long l, boolean bl) throws Http2Exception {
        throw Http2Exception.headerListSizeError(n, Http2Error.PROTOCOL_ERROR, bl, "Header size exceeded max allowed size (%d)", l);
    }

    public static void headerListSizeExceeded(long l) throws Http2Exception {
        throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Header size exceeded max allowed size (%d)", l);
    }

    static void writeFrameHeaderInternal(ByteBuf byteBuf, int n, byte by, Http2Flags http2Flags, int n2) {
        byteBuf.writeMedium(n);
        byteBuf.writeByte(by);
        byteBuf.writeByte(http2Flags.value());
        byteBuf.writeInt(n2);
    }

    public static void verifyPadding(int n) {
        if (n < 0 || n > 256) {
            throw new IllegalArgumentException(String.format("Invalid padding '%d'. Padding must be between 0 and %d (inclusive).", n, 256));
        }
    }

    private Http2CodecUtil() {
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    static final class SimpleChannelPromiseAggregator
    extends DefaultChannelPromise {
        private final ChannelPromise promise;
        private int expectedCount;
        private int doneCount;
        private Throwable lastFailure;
        private boolean doneAllocating;
        static final boolean $assertionsDisabled = !Http2CodecUtil.class.desiredAssertionStatus();

        SimpleChannelPromiseAggregator(ChannelPromise channelPromise, Channel channel, EventExecutor eventExecutor) {
            super(channel, eventExecutor);
            if (!$assertionsDisabled && (channelPromise == null || channelPromise.isDone())) {
                throw new AssertionError();
            }
            this.promise = channelPromise;
        }

        public ChannelPromise newPromise() {
            if (!$assertionsDisabled && this.doneAllocating) {
                throw new AssertionError((Object)"Done allocating. No more promises can be allocated.");
            }
            ++this.expectedCount;
            return this;
        }

        public ChannelPromise doneAllocatingPromises() {
            if (!this.doneAllocating) {
                this.doneAllocating = true;
                if (this.doneCount == this.expectedCount || this.expectedCount == 0) {
                    return this.setPromise();
                }
            }
            return this;
        }

        @Override
        public boolean tryFailure(Throwable throwable) {
            if (this.allowFailure()) {
                ++this.doneCount;
                this.lastFailure = throwable;
                if (this.allPromisesDone()) {
                    return this.tryPromise();
                }
                return false;
            }
            return true;
        }

        @Override
        public ChannelPromise setFailure(Throwable throwable) {
            if (this.allowFailure()) {
                ++this.doneCount;
                this.lastFailure = throwable;
                if (this.allPromisesDone()) {
                    return this.setPromise();
                }
            }
            return this;
        }

        @Override
        public ChannelPromise setSuccess(Void void_) {
            if (this.awaitingPromises()) {
                ++this.doneCount;
                if (this.allPromisesDone()) {
                    this.setPromise();
                }
            }
            return this;
        }

        @Override
        public boolean trySuccess(Void void_) {
            if (this.awaitingPromises()) {
                ++this.doneCount;
                if (this.allPromisesDone()) {
                    return this.tryPromise();
                }
                return false;
            }
            return true;
        }

        private boolean allowFailure() {
            return this.awaitingPromises() || this.expectedCount == 0;
        }

        private boolean awaitingPromises() {
            return this.doneCount < this.expectedCount;
        }

        private boolean allPromisesDone() {
            return this.doneCount == this.expectedCount && this.doneAllocating;
        }

        private ChannelPromise setPromise() {
            if (this.lastFailure == null) {
                this.promise.setSuccess();
                return super.setSuccess(null);
            }
            this.promise.setFailure(this.lastFailure);
            return super.setFailure(this.lastFailure);
        }

        private boolean tryPromise() {
            if (this.lastFailure == null) {
                this.promise.trySuccess();
                return super.trySuccess(null);
            }
            this.promise.tryFailure(this.lastFailure);
            return super.tryFailure(this.lastFailure);
        }

        @Override
        public Promise setFailure(Throwable throwable) {
            return this.setFailure(throwable);
        }

        @Override
        public boolean trySuccess(Object object) {
            return this.trySuccess((Void)object);
        }

        @Override
        public Promise setSuccess(Object object) {
            return this.setSuccess((Void)object);
        }
    }
}

