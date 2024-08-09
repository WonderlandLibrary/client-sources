/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http2.DefaultHttp2HeadersEncoder;
import io.netty.handler.codec.http2.Http2CodecUtil;
import io.netty.handler.codec.http2.Http2Error;
import io.netty.handler.codec.http2.Http2Exception;
import io.netty.handler.codec.http2.Http2Flags;
import io.netty.handler.codec.http2.Http2FrameSizePolicy;
import io.netty.handler.codec.http2.Http2FrameWriter;
import io.netty.handler.codec.http2.Http2Headers;
import io.netty.handler.codec.http2.Http2HeadersEncoder;
import io.netty.handler.codec.http2.Http2Settings;
import io.netty.util.ReferenceCounted;
import io.netty.util.collection.CharObjectMap;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;

public class DefaultHttp2FrameWriter
implements Http2FrameWriter,
Http2FrameSizePolicy,
Http2FrameWriter.Configuration {
    private static final String STREAM_ID = "Stream ID";
    private static final String STREAM_DEPENDENCY = "Stream Dependency";
    private static final ByteBuf ZERO_BUFFER = Unpooled.unreleasableBuffer(Unpooled.directBuffer(255).writeZero(255)).asReadOnly();
    private final Http2HeadersEncoder headersEncoder;
    private int maxFrameSize;

    public DefaultHttp2FrameWriter() {
        this(new DefaultHttp2HeadersEncoder());
    }

    public DefaultHttp2FrameWriter(Http2HeadersEncoder.SensitivityDetector sensitivityDetector) {
        this(new DefaultHttp2HeadersEncoder(sensitivityDetector));
    }

    public DefaultHttp2FrameWriter(Http2HeadersEncoder.SensitivityDetector sensitivityDetector, boolean bl) {
        this(new DefaultHttp2HeadersEncoder(sensitivityDetector, bl));
    }

    public DefaultHttp2FrameWriter(Http2HeadersEncoder http2HeadersEncoder) {
        this.headersEncoder = http2HeadersEncoder;
        this.maxFrameSize = 16384;
    }

    @Override
    public Http2FrameWriter.Configuration configuration() {
        return this;
    }

    @Override
    public Http2HeadersEncoder.Configuration headersConfiguration() {
        return this.headersEncoder.configuration();
    }

    @Override
    public Http2FrameSizePolicy frameSizePolicy() {
        return this;
    }

    @Override
    public void maxFrameSize(int n) throws Http2Exception {
        if (!Http2CodecUtil.isMaxFrameSizeValid(n)) {
            throw Http2Exception.connectionError(Http2Error.FRAME_SIZE_ERROR, "Invalid MAX_FRAME_SIZE specified in sent settings: %d", n);
        }
        this.maxFrameSize = n;
    }

    @Override
    public int maxFrameSize() {
        return this.maxFrameSize;
    }

    @Override
    public void close() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public ChannelFuture writeData(ChannelHandlerContext channelHandlerContext, int n, ByteBuf byteBuf, int n2, boolean bl, ChannelPromise channelPromise) {
        Http2CodecUtil.SimpleChannelPromiseAggregator simpleChannelPromiseAggregator = new Http2CodecUtil.SimpleChannelPromiseAggregator(channelPromise, channelHandlerContext.channel(), channelHandlerContext.executor());
        ReferenceCounted referenceCounted = null;
        try {
            DefaultHttp2FrameWriter.verifyStreamId(n, STREAM_ID);
            Http2CodecUtil.verifyPadding(n2);
            int n3 = byteBuf.readableBytes();
            Http2Flags http2Flags = new Http2Flags();
            http2Flags.endOfStream(true);
            http2Flags.paddingPresent(true);
            if (n3 > this.maxFrameSize) {
                referenceCounted = channelHandlerContext.alloc().buffer(9);
                Http2CodecUtil.writeFrameHeaderInternal((ByteBuf)referenceCounted, this.maxFrameSize, (byte)0, http2Flags, n);
                do {
                    channelHandlerContext.write(((ByteBuf)referenceCounted).retainedSlice(), simpleChannelPromiseAggregator.newPromise());
                    channelHandlerContext.write(byteBuf.readRetainedSlice(this.maxFrameSize), simpleChannelPromiseAggregator.newPromise());
                } while ((n3 -= this.maxFrameSize) > this.maxFrameSize);
            }
            if (n2 == 0) {
                if (referenceCounted != null) {
                    referenceCounted.release();
                    referenceCounted = null;
                }
                ByteBuf byteBuf2 = channelHandlerContext.alloc().buffer(9);
                http2Flags.endOfStream(bl);
                Http2CodecUtil.writeFrameHeaderInternal(byteBuf2, n3, (byte)0, http2Flags, n);
                channelHandlerContext.write(byteBuf2, simpleChannelPromiseAggregator.newPromise());
                ByteBuf byteBuf3 = byteBuf.readSlice(n3);
                byteBuf = null;
                channelHandlerContext.write(byteBuf3, simpleChannelPromiseAggregator.newPromise());
            } else {
                if (n3 != this.maxFrameSize) {
                    if (referenceCounted != null) {
                        referenceCounted.release();
                        referenceCounted = null;
                    }
                } else {
                    ByteBuf byteBuf4;
                    n3 -= this.maxFrameSize;
                    if (referenceCounted == null) {
                        byteBuf4 = channelHandlerContext.alloc().buffer(9);
                        Http2CodecUtil.writeFrameHeaderInternal(byteBuf4, this.maxFrameSize, (byte)0, http2Flags, n);
                    } else {
                        byteBuf4 = ((ByteBuf)referenceCounted).slice();
                        referenceCounted = null;
                    }
                    channelHandlerContext.write(byteBuf4, simpleChannelPromiseAggregator.newPromise());
                    byteBuf4 = byteBuf.readSlice(this.maxFrameSize);
                    byteBuf = null;
                    channelHandlerContext.write(byteBuf4, simpleChannelPromiseAggregator.newPromise());
                }
                do {
                    int n4 = Math.min(n3, this.maxFrameSize);
                    int n5 = Math.min(n2, Math.max(0, this.maxFrameSize - 1 - n4));
                    ByteBuf byteBuf5 = channelHandlerContext.alloc().buffer(10);
                    http2Flags.endOfStream(bl && (n3 -= n4) == 0 && (n2 -= n5) == 0);
                    http2Flags.paddingPresent(n5 > 0);
                    Http2CodecUtil.writeFrameHeaderInternal(byteBuf5, n5 + n4, (byte)0, http2Flags, n);
                    DefaultHttp2FrameWriter.writePaddingLength(byteBuf5, n5);
                    channelHandlerContext.write(byteBuf5, simpleChannelPromiseAggregator.newPromise());
                    if (n4 != 0) {
                        if (n3 == 0) {
                            ByteBuf byteBuf6 = byteBuf.readSlice(n4);
                            byteBuf = null;
                            channelHandlerContext.write(byteBuf6, simpleChannelPromiseAggregator.newPromise());
                        } else {
                            channelHandlerContext.write(byteBuf.readRetainedSlice(n4), simpleChannelPromiseAggregator.newPromise());
                        }
                    }
                    if (DefaultHttp2FrameWriter.paddingBytes(n5) <= 0) continue;
                    channelHandlerContext.write(ZERO_BUFFER.slice(0, DefaultHttp2FrameWriter.paddingBytes(n5)), simpleChannelPromiseAggregator.newPromise());
                } while (n3 != 0 || n2 != 0);
            }
        } catch (Throwable throwable) {
            if (referenceCounted != null) {
                referenceCounted.release();
            }
            try {
                if (byteBuf != null) {
                    byteBuf.release();
                }
            } finally {
                simpleChannelPromiseAggregator.setFailure(throwable);
                simpleChannelPromiseAggregator.doneAllocatingPromises();
            }
            return simpleChannelPromiseAggregator;
        }
        return simpleChannelPromiseAggregator.doneAllocatingPromises();
    }

    @Override
    public ChannelFuture writeHeaders(ChannelHandlerContext channelHandlerContext, int n, Http2Headers http2Headers, int n2, boolean bl, ChannelPromise channelPromise) {
        return this.writeHeadersInternal(channelHandlerContext, n, http2Headers, n2, bl, false, 0, (short)0, false, channelPromise);
    }

    @Override
    public ChannelFuture writeHeaders(ChannelHandlerContext channelHandlerContext, int n, Http2Headers http2Headers, int n2, short s, boolean bl, int n3, boolean bl2, ChannelPromise channelPromise) {
        return this.writeHeadersInternal(channelHandlerContext, n, http2Headers, n3, bl2, true, n2, s, bl, channelPromise);
    }

    @Override
    public ChannelFuture writePriority(ChannelHandlerContext channelHandlerContext, int n, int n2, short s, boolean bl, ChannelPromise channelPromise) {
        try {
            DefaultHttp2FrameWriter.verifyStreamId(n, STREAM_ID);
            DefaultHttp2FrameWriter.verifyStreamId(n2, STREAM_DEPENDENCY);
            DefaultHttp2FrameWriter.verifyWeight(s);
            ByteBuf byteBuf = channelHandlerContext.alloc().buffer(14);
            Http2CodecUtil.writeFrameHeaderInternal(byteBuf, 5, (byte)2, new Http2Flags(), n);
            byteBuf.writeInt(bl ? (int)(0x80000000L | (long)n2) : n2);
            byteBuf.writeByte(s - 1);
            return channelHandlerContext.write(byteBuf, channelPromise);
        } catch (Throwable throwable) {
            return channelPromise.setFailure(throwable);
        }
    }

    @Override
    public ChannelFuture writeRstStream(ChannelHandlerContext channelHandlerContext, int n, long l, ChannelPromise channelPromise) {
        try {
            DefaultHttp2FrameWriter.verifyStreamId(n, STREAM_ID);
            DefaultHttp2FrameWriter.verifyErrorCode(l);
            ByteBuf byteBuf = channelHandlerContext.alloc().buffer(13);
            Http2CodecUtil.writeFrameHeaderInternal(byteBuf, 4, (byte)3, new Http2Flags(), n);
            byteBuf.writeInt((int)l);
            return channelHandlerContext.write(byteBuf, channelPromise);
        } catch (Throwable throwable) {
            return channelPromise.setFailure(throwable);
        }
    }

    @Override
    public ChannelFuture writeSettings(ChannelHandlerContext channelHandlerContext, Http2Settings http2Settings, ChannelPromise channelPromise) {
        try {
            ObjectUtil.checkNotNull(http2Settings, "settings");
            int n = 6 * http2Settings.size();
            ByteBuf byteBuf = channelHandlerContext.alloc().buffer(9 + http2Settings.size() * 6);
            Http2CodecUtil.writeFrameHeaderInternal(byteBuf, n, (byte)4, new Http2Flags(), 0);
            for (CharObjectMap.PrimitiveEntry primitiveEntry : http2Settings.entries()) {
                byteBuf.writeChar(primitiveEntry.key());
                byteBuf.writeInt(((Long)primitiveEntry.value()).intValue());
            }
            return channelHandlerContext.write(byteBuf, channelPromise);
        } catch (Throwable throwable) {
            return channelPromise.setFailure(throwable);
        }
    }

    @Override
    public ChannelFuture writeSettingsAck(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) {
        try {
            ByteBuf byteBuf = channelHandlerContext.alloc().buffer(9);
            Http2CodecUtil.writeFrameHeaderInternal(byteBuf, 0, (byte)4, new Http2Flags().ack(false), 0);
            return channelHandlerContext.write(byteBuf, channelPromise);
        } catch (Throwable throwable) {
            return channelPromise.setFailure(throwable);
        }
    }

    @Override
    public ChannelFuture writePing(ChannelHandlerContext channelHandlerContext, boolean bl, long l, ChannelPromise channelPromise) {
        Http2Flags http2Flags = bl ? new Http2Flags().ack(false) : new Http2Flags();
        ByteBuf byteBuf = channelHandlerContext.alloc().buffer(17);
        Http2CodecUtil.writeFrameHeaderInternal(byteBuf, 8, (byte)6, http2Flags, 0);
        byteBuf.writeLong(l);
        return channelHandlerContext.write(byteBuf, channelPromise);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public ChannelFuture writePushPromise(ChannelHandlerContext channelHandlerContext, int n, int n2, Http2Headers http2Headers, int n3, ChannelPromise channelPromise) {
        ReferenceCounted referenceCounted = null;
        Http2CodecUtil.SimpleChannelPromiseAggregator simpleChannelPromiseAggregator = new Http2CodecUtil.SimpleChannelPromiseAggregator(channelPromise, channelHandlerContext.channel(), channelHandlerContext.executor());
        try {
            DefaultHttp2FrameWriter.verifyStreamId(n, STREAM_ID);
            DefaultHttp2FrameWriter.verifyStreamId(n2, "Promised Stream ID");
            Http2CodecUtil.verifyPadding(n3);
            referenceCounted = channelHandlerContext.alloc().buffer();
            this.headersEncoder.encodeHeaders(n, http2Headers, (ByteBuf)referenceCounted);
            Http2Flags http2Flags = new Http2Flags().paddingPresent(n3 > 0);
            int n4 = 4 + n3;
            int n5 = this.maxFrameSize - n4;
            ByteBuf byteBuf = ((ByteBuf)referenceCounted).readRetainedSlice(Math.min(((ByteBuf)referenceCounted).readableBytes(), n5));
            http2Flags.endOfHeaders(!((ByteBuf)referenceCounted).isReadable());
            int n6 = byteBuf.readableBytes() + n4;
            ByteBuf byteBuf2 = channelHandlerContext.alloc().buffer(14);
            Http2CodecUtil.writeFrameHeaderInternal(byteBuf2, n6, (byte)5, http2Flags, n);
            DefaultHttp2FrameWriter.writePaddingLength(byteBuf2, n3);
            byteBuf2.writeInt(n2);
            channelHandlerContext.write(byteBuf2, simpleChannelPromiseAggregator.newPromise());
            channelHandlerContext.write(byteBuf, simpleChannelPromiseAggregator.newPromise());
            if (DefaultHttp2FrameWriter.paddingBytes(n3) > 0) {
                channelHandlerContext.write(ZERO_BUFFER.slice(0, DefaultHttp2FrameWriter.paddingBytes(n3)), simpleChannelPromiseAggregator.newPromise());
            }
            if (!http2Flags.endOfHeaders()) {
                this.writeContinuationFrames(channelHandlerContext, n, (ByteBuf)referenceCounted, n3, simpleChannelPromiseAggregator);
            }
        } catch (Http2Exception http2Exception) {
            simpleChannelPromiseAggregator.setFailure(http2Exception);
        } catch (Throwable throwable) {
            simpleChannelPromiseAggregator.setFailure(throwable);
            simpleChannelPromiseAggregator.doneAllocatingPromises();
            PlatformDependent.throwException(throwable);
        } finally {
            if (referenceCounted != null) {
                referenceCounted.release();
            }
        }
        return simpleChannelPromiseAggregator.doneAllocatingPromises();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public ChannelFuture writeGoAway(ChannelHandlerContext channelHandlerContext, int n, long l, ByteBuf byteBuf, ChannelPromise channelPromise) {
        Http2CodecUtil.SimpleChannelPromiseAggregator simpleChannelPromiseAggregator = new Http2CodecUtil.SimpleChannelPromiseAggregator(channelPromise, channelHandlerContext.channel(), channelHandlerContext.executor());
        try {
            DefaultHttp2FrameWriter.verifyStreamOrConnectionId(n, "Last Stream ID");
            DefaultHttp2FrameWriter.verifyErrorCode(l);
            int n2 = 8 + byteBuf.readableBytes();
            ByteBuf byteBuf2 = channelHandlerContext.alloc().buffer(17);
            Http2CodecUtil.writeFrameHeaderInternal(byteBuf2, n2, (byte)7, new Http2Flags(), 0);
            byteBuf2.writeInt(n);
            byteBuf2.writeInt((int)l);
            channelHandlerContext.write(byteBuf2, simpleChannelPromiseAggregator.newPromise());
        } catch (Throwable throwable) {
            try {
                byteBuf.release();
            } finally {
                simpleChannelPromiseAggregator.setFailure(throwable);
                simpleChannelPromiseAggregator.doneAllocatingPromises();
            }
            return simpleChannelPromiseAggregator;
        }
        try {
            channelHandlerContext.write(byteBuf, simpleChannelPromiseAggregator.newPromise());
        } catch (Throwable throwable) {
            simpleChannelPromiseAggregator.setFailure(throwable);
        }
        return simpleChannelPromiseAggregator.doneAllocatingPromises();
    }

    @Override
    public ChannelFuture writeWindowUpdate(ChannelHandlerContext channelHandlerContext, int n, int n2, ChannelPromise channelPromise) {
        try {
            DefaultHttp2FrameWriter.verifyStreamOrConnectionId(n, STREAM_ID);
            DefaultHttp2FrameWriter.verifyWindowSizeIncrement(n2);
            ByteBuf byteBuf = channelHandlerContext.alloc().buffer(13);
            Http2CodecUtil.writeFrameHeaderInternal(byteBuf, 4, (byte)8, new Http2Flags(), n);
            byteBuf.writeInt(n2);
            return channelHandlerContext.write(byteBuf, channelPromise);
        } catch (Throwable throwable) {
            return channelPromise.setFailure(throwable);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public ChannelFuture writeFrame(ChannelHandlerContext channelHandlerContext, byte by, int n, Http2Flags http2Flags, ByteBuf byteBuf, ChannelPromise channelPromise) {
        Http2CodecUtil.SimpleChannelPromiseAggregator simpleChannelPromiseAggregator = new Http2CodecUtil.SimpleChannelPromiseAggregator(channelPromise, channelHandlerContext.channel(), channelHandlerContext.executor());
        try {
            DefaultHttp2FrameWriter.verifyStreamOrConnectionId(n, STREAM_ID);
            ByteBuf byteBuf2 = channelHandlerContext.alloc().buffer(9);
            Http2CodecUtil.writeFrameHeaderInternal(byteBuf2, byteBuf.readableBytes(), by, http2Flags, n);
            channelHandlerContext.write(byteBuf2, simpleChannelPromiseAggregator.newPromise());
        } catch (Throwable throwable) {
            try {
                byteBuf.release();
            } finally {
                simpleChannelPromiseAggregator.setFailure(throwable);
                simpleChannelPromiseAggregator.doneAllocatingPromises();
            }
            return simpleChannelPromiseAggregator;
        }
        try {
            channelHandlerContext.write(byteBuf, simpleChannelPromiseAggregator.newPromise());
        } catch (Throwable throwable) {
            simpleChannelPromiseAggregator.setFailure(throwable);
        }
        return simpleChannelPromiseAggregator.doneAllocatingPromises();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private ChannelFuture writeHeadersInternal(ChannelHandlerContext channelHandlerContext, int n, Http2Headers http2Headers, int n2, boolean bl, boolean bl2, int n3, short s, boolean bl3, ChannelPromise channelPromise) {
        ReferenceCounted referenceCounted = null;
        Http2CodecUtil.SimpleChannelPromiseAggregator simpleChannelPromiseAggregator = new Http2CodecUtil.SimpleChannelPromiseAggregator(channelPromise, channelHandlerContext.channel(), channelHandlerContext.executor());
        try {
            DefaultHttp2FrameWriter.verifyStreamId(n, STREAM_ID);
            if (bl2) {
                DefaultHttp2FrameWriter.verifyStreamOrConnectionId(n3, STREAM_DEPENDENCY);
                Http2CodecUtil.verifyPadding(n2);
                DefaultHttp2FrameWriter.verifyWeight(s);
            }
            referenceCounted = channelHandlerContext.alloc().buffer();
            this.headersEncoder.encodeHeaders(n, http2Headers, (ByteBuf)referenceCounted);
            Http2Flags http2Flags = new Http2Flags().endOfStream(bl).priorityPresent(bl2).paddingPresent(n2 > 0);
            int n4 = n2 + http2Flags.getNumPriorityBytes();
            int n5 = this.maxFrameSize - n4;
            ByteBuf byteBuf = ((ByteBuf)referenceCounted).readRetainedSlice(Math.min(((ByteBuf)referenceCounted).readableBytes(), n5));
            http2Flags.endOfHeaders(!((ByteBuf)referenceCounted).isReadable());
            int n6 = byteBuf.readableBytes() + n4;
            ByteBuf byteBuf2 = channelHandlerContext.alloc().buffer(15);
            Http2CodecUtil.writeFrameHeaderInternal(byteBuf2, n6, (byte)1, http2Flags, n);
            DefaultHttp2FrameWriter.writePaddingLength(byteBuf2, n2);
            if (bl2) {
                byteBuf2.writeInt(bl3 ? (int)(0x80000000L | (long)n3) : n3);
                byteBuf2.writeByte(s - 1);
            }
            channelHandlerContext.write(byteBuf2, simpleChannelPromiseAggregator.newPromise());
            channelHandlerContext.write(byteBuf, simpleChannelPromiseAggregator.newPromise());
            if (DefaultHttp2FrameWriter.paddingBytes(n2) > 0) {
                channelHandlerContext.write(ZERO_BUFFER.slice(0, DefaultHttp2FrameWriter.paddingBytes(n2)), simpleChannelPromiseAggregator.newPromise());
            }
            if (!http2Flags.endOfHeaders()) {
                this.writeContinuationFrames(channelHandlerContext, n, (ByteBuf)referenceCounted, n2, simpleChannelPromiseAggregator);
            }
        } catch (Http2Exception http2Exception) {
            simpleChannelPromiseAggregator.setFailure(http2Exception);
        } catch (Throwable throwable) {
            simpleChannelPromiseAggregator.setFailure(throwable);
            simpleChannelPromiseAggregator.doneAllocatingPromises();
            PlatformDependent.throwException(throwable);
        } finally {
            if (referenceCounted != null) {
                referenceCounted.release();
            }
        }
        return simpleChannelPromiseAggregator.doneAllocatingPromises();
    }

    private ChannelFuture writeContinuationFrames(ChannelHandlerContext channelHandlerContext, int n, ByteBuf byteBuf, int n2, Http2CodecUtil.SimpleChannelPromiseAggregator simpleChannelPromiseAggregator) {
        Http2Flags http2Flags = new Http2Flags().paddingPresent(n2 > 0);
        int n3 = this.maxFrameSize - n2;
        if (n3 <= 0) {
            return simpleChannelPromiseAggregator.setFailure(new IllegalArgumentException("Padding [" + n2 + "] is too large for max frame size [" + this.maxFrameSize + "]"));
        }
        if (byteBuf.isReadable()) {
            int n4 = Math.min(byteBuf.readableBytes(), n3);
            int n5 = n4 + n2;
            ByteBuf byteBuf2 = channelHandlerContext.alloc().buffer(10);
            Http2CodecUtil.writeFrameHeaderInternal(byteBuf2, n5, (byte)9, http2Flags, n);
            DefaultHttp2FrameWriter.writePaddingLength(byteBuf2, n2);
            do {
                n4 = Math.min(byteBuf.readableBytes(), n3);
                ByteBuf byteBuf3 = byteBuf.readRetainedSlice(n4);
                n5 = n4 + n2;
                if (byteBuf.isReadable()) {
                    channelHandlerContext.write(byteBuf2.retain(), simpleChannelPromiseAggregator.newPromise());
                } else {
                    http2Flags = http2Flags.endOfHeaders(false);
                    byteBuf2.release();
                    byteBuf2 = channelHandlerContext.alloc().buffer(10);
                    Http2CodecUtil.writeFrameHeaderInternal(byteBuf2, n5, (byte)9, http2Flags, n);
                    DefaultHttp2FrameWriter.writePaddingLength(byteBuf2, n2);
                    channelHandlerContext.write(byteBuf2, simpleChannelPromiseAggregator.newPromise());
                }
                channelHandlerContext.write(byteBuf3, simpleChannelPromiseAggregator.newPromise());
                if (DefaultHttp2FrameWriter.paddingBytes(n2) <= 0) continue;
                channelHandlerContext.write(ZERO_BUFFER.slice(0, DefaultHttp2FrameWriter.paddingBytes(n2)), simpleChannelPromiseAggregator.newPromise());
            } while (byteBuf.isReadable());
        }
        return simpleChannelPromiseAggregator;
    }

    private static int paddingBytes(int n) {
        return n - 1;
    }

    private static void writePaddingLength(ByteBuf byteBuf, int n) {
        if (n > 0) {
            byteBuf.writeByte(n - 1);
        }
    }

    private static void verifyStreamId(int n, String string) {
        if (n <= 0) {
            throw new IllegalArgumentException(string + " must be > 0");
        }
    }

    private static void verifyStreamOrConnectionId(int n, String string) {
        if (n < 0) {
            throw new IllegalArgumentException(string + " must be >= 0");
        }
    }

    private static void verifyWeight(short s) {
        if (s < 1 || s > 256) {
            throw new IllegalArgumentException("Invalid weight: " + s);
        }
    }

    private static void verifyErrorCode(long l) {
        if (l < 0L || l > 0xFFFFFFFFL) {
            throw new IllegalArgumentException("Invalid errorCode: " + l);
        }
    }

    private static void verifyWindowSizeIncrement(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("WindowSizeIncrement must be >= 0");
        }
    }

    private static void verifyPingPayload(ByteBuf byteBuf) {
        if (byteBuf == null || byteBuf.readableBytes() != 8) {
            throw new IllegalArgumentException("Opaque data must be 8 bytes");
        }
    }
}

