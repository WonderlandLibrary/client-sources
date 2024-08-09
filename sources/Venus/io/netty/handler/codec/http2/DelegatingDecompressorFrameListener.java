/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.compression.ZlibCodecFactory;
import io.netty.handler.codec.compression.ZlibWrapper;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http2.Http2Connection;
import io.netty.handler.codec.http2.Http2ConnectionAdapter;
import io.netty.handler.codec.http2.Http2Error;
import io.netty.handler.codec.http2.Http2Exception;
import io.netty.handler.codec.http2.Http2FrameListener;
import io.netty.handler.codec.http2.Http2FrameListenerDecorator;
import io.netty.handler.codec.http2.Http2FrameWriter;
import io.netty.handler.codec.http2.Http2Headers;
import io.netty.handler.codec.http2.Http2LocalFlowController;
import io.netty.handler.codec.http2.Http2Stream;
import io.netty.util.internal.ObjectUtil;

public class DelegatingDecompressorFrameListener
extends Http2FrameListenerDecorator {
    private final Http2Connection connection;
    private final boolean strict;
    private boolean flowControllerInitialized;
    private final Http2Connection.PropertyKey propertyKey;

    public DelegatingDecompressorFrameListener(Http2Connection http2Connection, Http2FrameListener http2FrameListener) {
        this(http2Connection, http2FrameListener, true);
    }

    public DelegatingDecompressorFrameListener(Http2Connection http2Connection, Http2FrameListener http2FrameListener, boolean bl) {
        super(http2FrameListener);
        this.connection = http2Connection;
        this.strict = bl;
        this.propertyKey = http2Connection.newKey();
        http2Connection.addListener(new Http2ConnectionAdapter(this){
            final DelegatingDecompressorFrameListener this$0;
            {
                this.this$0 = delegatingDecompressorFrameListener;
            }

            @Override
            public void onStreamRemoved(Http2Stream http2Stream) {
                Http2Decompressor http2Decompressor = this.this$0.decompressor(http2Stream);
                if (http2Decompressor != null) {
                    DelegatingDecompressorFrameListener.access$000(http2Decompressor);
                }
            }
        });
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public int onDataRead(ChannelHandlerContext channelHandlerContext, int n, ByteBuf byteBuf, int n2, boolean bl) throws Http2Exception {
        int n3;
        Http2Stream http2Stream = this.connection.stream(n);
        Http2Decompressor http2Decompressor = this.decompressor(http2Stream);
        if (http2Decompressor == null) {
            return this.listener.onDataRead(channelHandlerContext, n, byteBuf, n2, bl);
        }
        EmbeddedChannel embeddedChannel = http2Decompressor.decompressor();
        int n4 = byteBuf.readableBytes() + n2;
        http2Decompressor.incrementCompressedBytes(n4);
        embeddedChannel.writeInbound(byteBuf.retain());
        ByteBuf byteBuf2 = DelegatingDecompressorFrameListener.nextReadableBuf(embeddedChannel);
        if (byteBuf2 == null && bl && embeddedChannel.finish()) {
            byteBuf2 = DelegatingDecompressorFrameListener.nextReadableBuf(embeddedChannel);
        }
        if (byteBuf2 == null) {
            if (bl) {
                this.listener.onDataRead(channelHandlerContext, n, Unpooled.EMPTY_BUFFER, n2, true);
            }
            http2Decompressor.incrementDecompressedBytes(n4);
            return n4;
        }
        try {
            Http2LocalFlowController http2LocalFlowController = this.connection.local().flowController();
            http2Decompressor.incrementDecompressedBytes(n2);
            while (true) {
                ByteBuf byteBuf3;
                boolean bl2;
                boolean bl3 = bl2 = (byteBuf3 = DelegatingDecompressorFrameListener.nextReadableBuf(embeddedChannel)) == null && bl;
                if (bl2 && embeddedChannel.finish()) {
                    byteBuf3 = DelegatingDecompressorFrameListener.nextReadableBuf(embeddedChannel);
                    bl2 = byteBuf3 == null;
                }
                http2Decompressor.incrementDecompressedBytes(byteBuf2.readableBytes());
                http2LocalFlowController.consumeBytes(http2Stream, this.listener.onDataRead(channelHandlerContext, n, byteBuf2, n2, bl2));
                if (byteBuf3 == null) break;
                n2 = 0;
                byteBuf2.release();
                byteBuf2 = byteBuf3;
            }
            n3 = 0;
        } catch (Throwable throwable) {
            try {
                byteBuf2.release();
                throw throwable;
            } catch (Http2Exception http2Exception) {
                throw http2Exception;
            } catch (Throwable throwable2) {
                throw Http2Exception.streamError(http2Stream.id(), Http2Error.INTERNAL_ERROR, throwable2, "Decompressor error detected while delegating data read on streamId %d", http2Stream.id());
            }
        }
        byteBuf2.release();
        return n3;
    }

    @Override
    public void onHeadersRead(ChannelHandlerContext channelHandlerContext, int n, Http2Headers http2Headers, int n2, boolean bl) throws Http2Exception {
        this.initDecompressor(channelHandlerContext, n, http2Headers, bl);
        this.listener.onHeadersRead(channelHandlerContext, n, http2Headers, n2, bl);
    }

    @Override
    public void onHeadersRead(ChannelHandlerContext channelHandlerContext, int n, Http2Headers http2Headers, int n2, short s, boolean bl, int n3, boolean bl2) throws Http2Exception {
        this.initDecompressor(channelHandlerContext, n, http2Headers, bl2);
        this.listener.onHeadersRead(channelHandlerContext, n, http2Headers, n2, s, bl, n3, bl2);
    }

    protected EmbeddedChannel newContentDecompressor(ChannelHandlerContext channelHandlerContext, CharSequence charSequence) throws Http2Exception {
        if (HttpHeaderValues.GZIP.contentEqualsIgnoreCase(charSequence) || HttpHeaderValues.X_GZIP.contentEqualsIgnoreCase(charSequence)) {
            return new EmbeddedChannel(channelHandlerContext.channel().id(), channelHandlerContext.channel().metadata().hasDisconnect(), channelHandlerContext.channel().config(), ZlibCodecFactory.newZlibDecoder(ZlibWrapper.GZIP));
        }
        if (HttpHeaderValues.DEFLATE.contentEqualsIgnoreCase(charSequence) || HttpHeaderValues.X_DEFLATE.contentEqualsIgnoreCase(charSequence)) {
            ZlibWrapper zlibWrapper = this.strict ? ZlibWrapper.ZLIB : ZlibWrapper.ZLIB_OR_NONE;
            return new EmbeddedChannel(channelHandlerContext.channel().id(), channelHandlerContext.channel().metadata().hasDisconnect(), channelHandlerContext.channel().config(), ZlibCodecFactory.newZlibDecoder(zlibWrapper));
        }
        return null;
    }

    protected CharSequence getTargetContentEncoding(CharSequence charSequence) throws Http2Exception {
        return HttpHeaderValues.IDENTITY;
    }

    private void initDecompressor(ChannelHandlerContext channelHandlerContext, int n, Http2Headers http2Headers, boolean bl) throws Http2Exception {
        Http2Stream http2Stream = this.connection.stream(n);
        if (http2Stream == null) {
            return;
        }
        Http2Decompressor http2Decompressor = this.decompressor(http2Stream);
        if (http2Decompressor == null && !bl) {
            EmbeddedChannel embeddedChannel;
            CharSequence charSequence = (CharSequence)http2Headers.get(HttpHeaderNames.CONTENT_ENCODING);
            if (charSequence == null) {
                charSequence = HttpHeaderValues.IDENTITY;
            }
            if ((embeddedChannel = this.newContentDecompressor(channelHandlerContext, charSequence)) != null) {
                http2Decompressor = new Http2Decompressor(embeddedChannel);
                http2Stream.setProperty(this.propertyKey, http2Decompressor);
                CharSequence charSequence2 = this.getTargetContentEncoding(charSequence);
                if (HttpHeaderValues.IDENTITY.contentEqualsIgnoreCase(charSequence2)) {
                    http2Headers.remove(HttpHeaderNames.CONTENT_ENCODING);
                } else {
                    http2Headers.set(HttpHeaderNames.CONTENT_ENCODING, charSequence2);
                }
            }
        }
        if (http2Decompressor != null) {
            http2Headers.remove(HttpHeaderNames.CONTENT_LENGTH);
            if (!this.flowControllerInitialized) {
                this.flowControllerInitialized = true;
                this.connection.local().flowController(new ConsumedBytesConverter(this, this.connection.local().flowController()));
            }
        }
    }

    Http2Decompressor decompressor(Http2Stream http2Stream) {
        return http2Stream == null ? null : (Http2Decompressor)http2Stream.getProperty(this.propertyKey);
    }

    private static void cleanup(Http2Decompressor http2Decompressor) {
        http2Decompressor.decompressor().finishAndReleaseAll();
    }

    private static ByteBuf nextReadableBuf(EmbeddedChannel embeddedChannel) {
        ByteBuf byteBuf;
        while (true) {
            if ((byteBuf = (ByteBuf)embeddedChannel.readInbound()) == null) {
                return null;
            }
            if (byteBuf.isReadable()) break;
            byteBuf.release();
        }
        return byteBuf;
    }

    static void access$000(Http2Decompressor http2Decompressor) {
        DelegatingDecompressorFrameListener.cleanup(http2Decompressor);
    }

    private static final class Http2Decompressor {
        private final EmbeddedChannel decompressor;
        private int compressed;
        private int decompressed;
        static final boolean $assertionsDisabled = !DelegatingDecompressorFrameListener.class.desiredAssertionStatus();

        Http2Decompressor(EmbeddedChannel embeddedChannel) {
            this.decompressor = embeddedChannel;
        }

        EmbeddedChannel decompressor() {
            return this.decompressor;
        }

        void incrementCompressedBytes(int n) {
            if (!$assertionsDisabled && n < 0) {
                throw new AssertionError();
            }
            this.compressed += n;
        }

        void incrementDecompressedBytes(int n) {
            if (!$assertionsDisabled && n < 0) {
                throw new AssertionError();
            }
            this.decompressed += n;
        }

        int consumeBytes(int n, int n2) throws Http2Exception {
            if (n2 < 0) {
                throw new IllegalArgumentException("decompressedBytes must not be negative: " + n2);
            }
            if (this.decompressed - n2 < 0) {
                throw Http2Exception.streamError(n, Http2Error.INTERNAL_ERROR, "Attempting to return too many bytes for stream %d. decompressed: %d decompressedBytes: %d", n, this.decompressed, n2);
            }
            double d = (double)n2 / (double)this.decompressed;
            int n3 = Math.min(this.compressed, (int)Math.ceil((double)this.compressed * d));
            if (this.compressed - n3 < 0) {
                throw Http2Exception.streamError(n, Http2Error.INTERNAL_ERROR, "overflow when converting decompressed bytes to compressed bytes for stream %d.decompressedBytes: %d decompressed: %d compressed: %d consumedCompressed: %d", n, n2, this.decompressed, this.compressed, n3);
            }
            this.decompressed -= n2;
            this.compressed -= n3;
            return n3;
        }
    }

    private final class ConsumedBytesConverter
    implements Http2LocalFlowController {
        private final Http2LocalFlowController flowController;
        final DelegatingDecompressorFrameListener this$0;

        ConsumedBytesConverter(DelegatingDecompressorFrameListener delegatingDecompressorFrameListener, Http2LocalFlowController http2LocalFlowController) {
            this.this$0 = delegatingDecompressorFrameListener;
            this.flowController = ObjectUtil.checkNotNull(http2LocalFlowController, "flowController");
        }

        @Override
        public Http2LocalFlowController frameWriter(Http2FrameWriter http2FrameWriter) {
            return this.flowController.frameWriter(http2FrameWriter);
        }

        @Override
        public void channelHandlerContext(ChannelHandlerContext channelHandlerContext) throws Http2Exception {
            this.flowController.channelHandlerContext(channelHandlerContext);
        }

        @Override
        public void initialWindowSize(int n) throws Http2Exception {
            this.flowController.initialWindowSize(n);
        }

        @Override
        public int initialWindowSize() {
            return this.flowController.initialWindowSize();
        }

        @Override
        public int windowSize(Http2Stream http2Stream) {
            return this.flowController.windowSize(http2Stream);
        }

        @Override
        public void incrementWindowSize(Http2Stream http2Stream, int n) throws Http2Exception {
            this.flowController.incrementWindowSize(http2Stream, n);
        }

        @Override
        public void receiveFlowControlledFrame(Http2Stream http2Stream, ByteBuf byteBuf, int n, boolean bl) throws Http2Exception {
            this.flowController.receiveFlowControlledFrame(http2Stream, byteBuf, n, bl);
        }

        @Override
        public boolean consumeBytes(Http2Stream http2Stream, int n) throws Http2Exception {
            Http2Decompressor http2Decompressor = this.this$0.decompressor(http2Stream);
            if (http2Decompressor != null) {
                n = http2Decompressor.consumeBytes(http2Stream.id(), n);
            }
            try {
                return this.flowController.consumeBytes(http2Stream, n);
            } catch (Http2Exception http2Exception) {
                throw http2Exception;
            } catch (Throwable throwable) {
                throw Http2Exception.streamError(http2Stream.id(), Http2Error.INTERNAL_ERROR, throwable, "Error while returning bytes to flow control window", new Object[0]);
            }
        }

        @Override
        public int unconsumedBytes(Http2Stream http2Stream) {
            return this.flowController.unconsumedBytes(http2Stream);
        }

        @Override
        public int initialWindowSize(Http2Stream http2Stream) {
            return this.flowController.initialWindowSize(http2Stream);
        }
    }
}

