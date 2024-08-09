/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http2.DecoratingHttp2ConnectionEncoder;
import io.netty.handler.codec.http2.Http2ConnectionAdapter;
import io.netty.handler.codec.http2.Http2ConnectionEncoder;
import io.netty.handler.codec.http2.Http2Error;
import io.netty.handler.codec.http2.Http2Exception;
import io.netty.handler.codec.http2.Http2Headers;
import io.netty.handler.codec.http2.Http2Settings;
import io.netty.handler.codec.http2.Http2Stream;
import io.netty.util.ReferenceCountUtil;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

public class StreamBufferingEncoder
extends DecoratingHttp2ConnectionEncoder {
    private final TreeMap<Integer, PendingStream> pendingStreams = new TreeMap();
    private int maxConcurrentStreams;
    private boolean closed;

    public StreamBufferingEncoder(Http2ConnectionEncoder http2ConnectionEncoder) {
        this(http2ConnectionEncoder, 100);
    }

    public StreamBufferingEncoder(Http2ConnectionEncoder http2ConnectionEncoder, int n) {
        super(http2ConnectionEncoder);
        this.maxConcurrentStreams = n;
        this.connection().addListener(new Http2ConnectionAdapter(this){
            final StreamBufferingEncoder this$0;
            {
                this.this$0 = streamBufferingEncoder;
            }

            @Override
            public void onGoAwayReceived(int n, long l, ByteBuf byteBuf) {
                StreamBufferingEncoder.access$000(this.this$0, n, l, byteBuf);
            }

            @Override
            public void onStreamClosed(Http2Stream http2Stream) {
                StreamBufferingEncoder.access$100(this.this$0);
            }
        });
    }

    public int numBufferedStreams() {
        return this.pendingStreams.size();
    }

    @Override
    public ChannelFuture writeHeaders(ChannelHandlerContext channelHandlerContext, int n, Http2Headers http2Headers, int n2, boolean bl, ChannelPromise channelPromise) {
        return this.writeHeaders(channelHandlerContext, n, http2Headers, 0, (short)16, false, n2, bl, channelPromise);
    }

    @Override
    public ChannelFuture writeHeaders(ChannelHandlerContext channelHandlerContext, int n, Http2Headers http2Headers, int n2, short s, boolean bl, int n3, boolean bl2, ChannelPromise channelPromise) {
        if (this.closed) {
            return channelPromise.setFailure(new Http2ChannelClosedException());
        }
        if (this.isExistingStream(n) || this.connection().goAwayReceived()) {
            return super.writeHeaders(channelHandlerContext, n, http2Headers, n2, s, bl, n3, bl2, channelPromise);
        }
        if (this.canCreateStream()) {
            return super.writeHeaders(channelHandlerContext, n, http2Headers, n2, s, bl, n3, bl2, channelPromise);
        }
        PendingStream pendingStream = this.pendingStreams.get(n);
        if (pendingStream == null) {
            pendingStream = new PendingStream(channelHandlerContext, n);
            this.pendingStreams.put(n, pendingStream);
        }
        pendingStream.frames.add(new HeadersFrame(this, http2Headers, n2, s, bl, n3, bl2, channelPromise));
        return channelPromise;
    }

    @Override
    public ChannelFuture writeRstStream(ChannelHandlerContext channelHandlerContext, int n, long l, ChannelPromise channelPromise) {
        if (this.isExistingStream(n)) {
            return super.writeRstStream(channelHandlerContext, n, l, channelPromise);
        }
        PendingStream pendingStream = this.pendingStreams.remove(n);
        if (pendingStream != null) {
            pendingStream.close(null);
            channelPromise.setSuccess();
        } else {
            channelPromise.setFailure(Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Stream does not exist %d", n));
        }
        return channelPromise;
    }

    @Override
    public ChannelFuture writeData(ChannelHandlerContext channelHandlerContext, int n, ByteBuf byteBuf, int n2, boolean bl, ChannelPromise channelPromise) {
        if (this.isExistingStream(n)) {
            return super.writeData(channelHandlerContext, n, byteBuf, n2, bl, channelPromise);
        }
        PendingStream pendingStream = this.pendingStreams.get(n);
        if (pendingStream != null) {
            pendingStream.frames.add(new DataFrame(this, byteBuf, n2, bl, channelPromise));
        } else {
            ReferenceCountUtil.safeRelease(byteBuf);
            channelPromise.setFailure(Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Stream does not exist %d", n));
        }
        return channelPromise;
    }

    @Override
    public void remoteSettings(Http2Settings http2Settings) throws Http2Exception {
        super.remoteSettings(http2Settings);
        this.maxConcurrentStreams = this.connection().local().maxActiveStreams();
        this.tryCreatePendingStreams();
    }

    @Override
    public void close() {
        try {
            if (!this.closed) {
                this.closed = true;
                Http2ChannelClosedException http2ChannelClosedException = new Http2ChannelClosedException();
                while (!this.pendingStreams.isEmpty()) {
                    PendingStream pendingStream = this.pendingStreams.pollFirstEntry().getValue();
                    pendingStream.close(http2ChannelClosedException);
                }
            }
        } finally {
            super.close();
        }
    }

    private void tryCreatePendingStreams() {
        while (!this.pendingStreams.isEmpty() && this.canCreateStream()) {
            Map.Entry<Integer, PendingStream> entry = this.pendingStreams.pollFirstEntry();
            PendingStream pendingStream = entry.getValue();
            try {
                pendingStream.sendFrames();
            } catch (Throwable throwable) {
                pendingStream.close(throwable);
            }
        }
    }

    private void cancelGoAwayStreams(int n, long l, ByteBuf byteBuf) {
        Iterator<PendingStream> iterator2 = this.pendingStreams.values().iterator();
        Http2GoAwayException http2GoAwayException = new Http2GoAwayException(n, l, ByteBufUtil.getBytes(byteBuf));
        while (iterator2.hasNext()) {
            PendingStream pendingStream = iterator2.next();
            if (pendingStream.streamId <= n) continue;
            iterator2.remove();
            pendingStream.close(http2GoAwayException);
        }
    }

    private boolean canCreateStream() {
        return this.connection().local().numActiveStreams() < this.maxConcurrentStreams;
    }

    private boolean isExistingStream(int n) {
        return n <= this.connection().local().lastStreamCreated();
    }

    static void access$000(StreamBufferingEncoder streamBufferingEncoder, int n, long l, ByteBuf byteBuf) {
        streamBufferingEncoder.cancelGoAwayStreams(n, l, byteBuf);
    }

    static void access$100(StreamBufferingEncoder streamBufferingEncoder) {
        streamBufferingEncoder.tryCreatePendingStreams();
    }

    private final class DataFrame
    extends Frame {
        final ByteBuf data;
        final int padding;
        final boolean endOfStream;
        final StreamBufferingEncoder this$0;

        DataFrame(StreamBufferingEncoder streamBufferingEncoder, ByteBuf byteBuf, int n, boolean bl, ChannelPromise channelPromise) {
            this.this$0 = streamBufferingEncoder;
            super(channelPromise);
            this.data = byteBuf;
            this.padding = n;
            this.endOfStream = bl;
        }

        @Override
        void release(Throwable throwable) {
            super.release(throwable);
            ReferenceCountUtil.safeRelease(this.data);
        }

        @Override
        void send(ChannelHandlerContext channelHandlerContext, int n) {
            this.this$0.writeData(channelHandlerContext, n, this.data, this.padding, this.endOfStream, this.promise);
        }
    }

    private final class HeadersFrame
    extends Frame {
        final Http2Headers headers;
        final int streamDependency;
        final short weight;
        final boolean exclusive;
        final int padding;
        final boolean endOfStream;
        final StreamBufferingEncoder this$0;

        HeadersFrame(StreamBufferingEncoder streamBufferingEncoder, Http2Headers http2Headers, int n, short s, boolean bl, int n2, boolean bl2, ChannelPromise channelPromise) {
            this.this$0 = streamBufferingEncoder;
            super(channelPromise);
            this.headers = http2Headers;
            this.streamDependency = n;
            this.weight = s;
            this.exclusive = bl;
            this.padding = n2;
            this.endOfStream = bl2;
        }

        @Override
        void send(ChannelHandlerContext channelHandlerContext, int n) {
            this.this$0.writeHeaders(channelHandlerContext, n, this.headers, this.streamDependency, this.weight, this.exclusive, this.padding, this.endOfStream, this.promise);
        }
    }

    private static abstract class Frame {
        final ChannelPromise promise;

        Frame(ChannelPromise channelPromise) {
            this.promise = channelPromise;
        }

        void release(Throwable throwable) {
            if (throwable == null) {
                this.promise.setSuccess();
            } else {
                this.promise.setFailure(throwable);
            }
        }

        abstract void send(ChannelHandlerContext var1, int var2);
    }

    private static final class PendingStream {
        final ChannelHandlerContext ctx;
        final int streamId;
        final Queue<Frame> frames = new ArrayDeque<Frame>(2);

        PendingStream(ChannelHandlerContext channelHandlerContext, int n) {
            this.ctx = channelHandlerContext;
            this.streamId = n;
        }

        void sendFrames() {
            for (Frame frame : this.frames) {
                frame.send(this.ctx, this.streamId);
            }
        }

        void close(Throwable throwable) {
            for (Frame frame : this.frames) {
                frame.release(throwable);
            }
        }
    }

    public static final class Http2GoAwayException
    extends Http2Exception {
        private static final long serialVersionUID = 1326785622777291198L;
        private final int lastStreamId;
        private final long errorCode;
        private final byte[] debugData;

        public Http2GoAwayException(int n, long l, byte[] byArray) {
            super(Http2Error.STREAM_CLOSED);
            this.lastStreamId = n;
            this.errorCode = l;
            this.debugData = byArray;
        }

        public int lastStreamId() {
            return this.lastStreamId;
        }

        public long errorCode() {
            return this.errorCode;
        }

        public byte[] debugData() {
            return this.debugData;
        }
    }

    public static final class Http2ChannelClosedException
    extends Http2Exception {
        private static final long serialVersionUID = 4768543442094476971L;

        public Http2ChannelClosedException() {
            super(Http2Error.REFUSED_STREAM, "Connection closed");
        }
    }
}

