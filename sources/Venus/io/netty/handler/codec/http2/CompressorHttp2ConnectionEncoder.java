/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.compression.ZlibCodecFactory;
import io.netty.handler.codec.compression.ZlibWrapper;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http2.DecoratingHttp2ConnectionEncoder;
import io.netty.handler.codec.http2.Http2Connection;
import io.netty.handler.codec.http2.Http2ConnectionAdapter;
import io.netty.handler.codec.http2.Http2ConnectionEncoder;
import io.netty.handler.codec.http2.Http2Exception;
import io.netty.handler.codec.http2.Http2Headers;
import io.netty.handler.codec.http2.Http2Stream;
import io.netty.util.concurrent.PromiseCombiner;

public class CompressorHttp2ConnectionEncoder
extends DecoratingHttp2ConnectionEncoder {
    public static final int DEFAULT_COMPRESSION_LEVEL = 6;
    public static final int DEFAULT_WINDOW_BITS = 15;
    public static final int DEFAULT_MEM_LEVEL = 8;
    private final int compressionLevel;
    private final int windowBits;
    private final int memLevel;
    private final Http2Connection.PropertyKey propertyKey;

    public CompressorHttp2ConnectionEncoder(Http2ConnectionEncoder http2ConnectionEncoder) {
        this(http2ConnectionEncoder, 6, 15, 8);
    }

    public CompressorHttp2ConnectionEncoder(Http2ConnectionEncoder http2ConnectionEncoder, int n, int n2, int n3) {
        super(http2ConnectionEncoder);
        if (n < 0 || n > 9) {
            throw new IllegalArgumentException("compressionLevel: " + n + " (expected: 0-9)");
        }
        if (n2 < 9 || n2 > 15) {
            throw new IllegalArgumentException("windowBits: " + n2 + " (expected: 9-15)");
        }
        if (n3 < 1 || n3 > 9) {
            throw new IllegalArgumentException("memLevel: " + n3 + " (expected: 1-9)");
        }
        this.compressionLevel = n;
        this.windowBits = n2;
        this.memLevel = n3;
        this.propertyKey = this.connection().newKey();
        this.connection().addListener(new Http2ConnectionAdapter(this){
            final CompressorHttp2ConnectionEncoder this$0;
            {
                this.this$0 = compressorHttp2ConnectionEncoder;
            }

            @Override
            public void onStreamRemoved(Http2Stream http2Stream) {
                EmbeddedChannel embeddedChannel = (EmbeddedChannel)http2Stream.getProperty(CompressorHttp2ConnectionEncoder.access$000(this.this$0));
                if (embeddedChannel != null) {
                    this.this$0.cleanup(http2Stream, embeddedChannel);
                }
            }
        });
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public ChannelFuture writeData(ChannelHandlerContext channelHandlerContext, int n, ByteBuf byteBuf, int n2, boolean bl, ChannelPromise channelPromise) {
        EmbeddedChannel embeddedChannel;
        Http2Stream http2Stream = this.connection().stream(n);
        EmbeddedChannel embeddedChannel2 = embeddedChannel = http2Stream == null ? null : (EmbeddedChannel)http2Stream.getProperty(this.propertyKey);
        if (embeddedChannel == null) {
            return super.writeData(channelHandlerContext, n, byteBuf, n2, bl, channelPromise);
        }
        try {
            embeddedChannel.writeOutbound(byteBuf);
            ByteBuf byteBuf2 = CompressorHttp2ConnectionEncoder.nextReadableBuf(embeddedChannel);
            if (byteBuf2 == null) {
                if (bl) {
                    if (embeddedChannel.finish()) {
                        byteBuf2 = CompressorHttp2ConnectionEncoder.nextReadableBuf(embeddedChannel);
                    }
                    ChannelFuture channelFuture = super.writeData(channelHandlerContext, n, byteBuf2 == null ? Unpooled.EMPTY_BUFFER : byteBuf2, n2, true, channelPromise);
                    return channelFuture;
                }
                channelPromise.setSuccess();
                ChannelPromise channelPromise2 = channelPromise;
                return channelPromise2;
            }
            PromiseCombiner promiseCombiner = new PromiseCombiner();
            while (true) {
                ByteBuf byteBuf3;
                boolean bl2;
                boolean bl3 = bl2 = (byteBuf3 = CompressorHttp2ConnectionEncoder.nextReadableBuf(embeddedChannel)) == null && bl;
                if (bl2 && embeddedChannel.finish()) {
                    byteBuf3 = CompressorHttp2ConnectionEncoder.nextReadableBuf(embeddedChannel);
                    bl2 = byteBuf3 == null;
                }
                ChannelPromise channelPromise3 = channelHandlerContext.newPromise();
                promiseCombiner.add(channelPromise3);
                super.writeData(channelHandlerContext, n, byteBuf2, n2, bl2, channelPromise3);
                if (byteBuf3 == null) break;
                n2 = 0;
                byteBuf2 = byteBuf3;
            }
            promiseCombiner.finish(channelPromise);
        } catch (Throwable throwable) {
            channelPromise.tryFailure(throwable);
        } finally {
            if (bl) {
                this.cleanup(http2Stream, embeddedChannel);
            }
        }
        return channelPromise;
    }

    @Override
    public ChannelFuture writeHeaders(ChannelHandlerContext channelHandlerContext, int n, Http2Headers http2Headers, int n2, boolean bl, ChannelPromise channelPromise) {
        try {
            EmbeddedChannel embeddedChannel = this.newCompressor(channelHandlerContext, http2Headers, bl);
            ChannelFuture channelFuture = super.writeHeaders(channelHandlerContext, n, http2Headers, n2, bl, channelPromise);
            this.bindCompressorToStream(embeddedChannel, n);
            return channelFuture;
        } catch (Throwable throwable) {
            channelPromise.tryFailure(throwable);
            return channelPromise;
        }
    }

    @Override
    public ChannelFuture writeHeaders(ChannelHandlerContext channelHandlerContext, int n, Http2Headers http2Headers, int n2, short s, boolean bl, int n3, boolean bl2, ChannelPromise channelPromise) {
        try {
            EmbeddedChannel embeddedChannel = this.newCompressor(channelHandlerContext, http2Headers, bl2);
            ChannelFuture channelFuture = super.writeHeaders(channelHandlerContext, n, http2Headers, n2, s, bl, n3, bl2, channelPromise);
            this.bindCompressorToStream(embeddedChannel, n);
            return channelFuture;
        } catch (Throwable throwable) {
            channelPromise.tryFailure(throwable);
            return channelPromise;
        }
    }

    protected EmbeddedChannel newContentCompressor(ChannelHandlerContext channelHandlerContext, CharSequence charSequence) throws Http2Exception {
        if (HttpHeaderValues.GZIP.contentEqualsIgnoreCase(charSequence) || HttpHeaderValues.X_GZIP.contentEqualsIgnoreCase(charSequence)) {
            return this.newCompressionChannel(channelHandlerContext, ZlibWrapper.GZIP);
        }
        if (HttpHeaderValues.DEFLATE.contentEqualsIgnoreCase(charSequence) || HttpHeaderValues.X_DEFLATE.contentEqualsIgnoreCase(charSequence)) {
            return this.newCompressionChannel(channelHandlerContext, ZlibWrapper.ZLIB);
        }
        return null;
    }

    protected CharSequence getTargetContentEncoding(CharSequence charSequence) throws Http2Exception {
        return charSequence;
    }

    private EmbeddedChannel newCompressionChannel(ChannelHandlerContext channelHandlerContext, ZlibWrapper zlibWrapper) {
        return new EmbeddedChannel(channelHandlerContext.channel().id(), channelHandlerContext.channel().metadata().hasDisconnect(), channelHandlerContext.channel().config(), ZlibCodecFactory.newZlibEncoder(zlibWrapper, this.compressionLevel, this.windowBits, this.memLevel));
    }

    private EmbeddedChannel newCompressor(ChannelHandlerContext channelHandlerContext, Http2Headers http2Headers, boolean bl) throws Http2Exception {
        EmbeddedChannel embeddedChannel;
        if (bl) {
            return null;
        }
        CharSequence charSequence = (CharSequence)http2Headers.get(HttpHeaderNames.CONTENT_ENCODING);
        if (charSequence == null) {
            charSequence = HttpHeaderValues.IDENTITY;
        }
        if ((embeddedChannel = this.newContentCompressor(channelHandlerContext, charSequence)) != null) {
            CharSequence charSequence2 = this.getTargetContentEncoding(charSequence);
            if (HttpHeaderValues.IDENTITY.contentEqualsIgnoreCase(charSequence2)) {
                http2Headers.remove(HttpHeaderNames.CONTENT_ENCODING);
            } else {
                http2Headers.set(HttpHeaderNames.CONTENT_ENCODING, charSequence2);
            }
            http2Headers.remove(HttpHeaderNames.CONTENT_LENGTH);
        }
        return embeddedChannel;
    }

    private void bindCompressorToStream(EmbeddedChannel embeddedChannel, int n) {
        Http2Stream http2Stream;
        if (embeddedChannel != null && (http2Stream = this.connection().stream(n)) != null) {
            http2Stream.setProperty(this.propertyKey, embeddedChannel);
        }
    }

    void cleanup(Http2Stream http2Stream, EmbeddedChannel embeddedChannel) {
        if (embeddedChannel.finish()) {
            ByteBuf byteBuf;
            while ((byteBuf = (ByteBuf)embeddedChannel.readOutbound()) != null) {
                byteBuf.release();
            }
        }
        http2Stream.removeProperty(this.propertyKey);
    }

    private static ByteBuf nextReadableBuf(EmbeddedChannel embeddedChannel) {
        ByteBuf byteBuf;
        while (true) {
            if ((byteBuf = (ByteBuf)embeddedChannel.readOutbound()) == null) {
                return null;
            }
            if (byteBuf.isReadable()) break;
            byteBuf.release();
        }
        return byteBuf;
    }

    static Http2Connection.PropertyKey access$000(CompressorHttp2ConnectionEncoder compressorHttp2ConnectionEncoder) {
        return compressorHttp2ConnectionEncoder.propertyKey;
    }
}

