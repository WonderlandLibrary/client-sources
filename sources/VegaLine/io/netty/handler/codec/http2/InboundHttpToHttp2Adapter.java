/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http2;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpMessage;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpScheme;
import io.netty.handler.codec.http2.Http2Connection;
import io.netty.handler.codec.http2.Http2FrameListener;
import io.netty.handler.codec.http2.Http2Headers;
import io.netty.handler.codec.http2.Http2Stream;
import io.netty.handler.codec.http2.HttpConversionUtil;

public class InboundHttpToHttp2Adapter
extends ChannelInboundHandlerAdapter {
    private final Http2Connection connection;
    private final Http2FrameListener listener;

    public InboundHttpToHttp2Adapter(Http2Connection connection, Http2FrameListener listener) {
        this.connection = connection;
        this.listener = listener;
    }

    private int getStreamId(HttpHeaders httpHeaders) {
        return httpHeaders.getInt(HttpConversionUtil.ExtensionHeaderNames.STREAM_ID.text(), this.connection.remote().incrementAndGetNextStreamId());
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpMessage) {
            FullHttpMessage message = (FullHttpMessage)msg;
            try {
                int streamId = this.getStreamId(message.headers());
                Http2Stream stream = this.connection.stream(streamId);
                if (stream == null) {
                    stream = this.connection.remote().createStream(streamId, false);
                }
                message.headers().set((CharSequence)HttpConversionUtil.ExtensionHeaderNames.SCHEME.text(), (Object)HttpScheme.HTTP.name());
                Http2Headers messageHeaders = HttpConversionUtil.toHttp2Headers(message, true);
                boolean hasContent = message.content().isReadable();
                boolean hasTrailers = !message.trailingHeaders().isEmpty();
                this.listener.onHeadersRead(ctx, streamId, messageHeaders, 0, !hasContent && !hasTrailers);
                if (hasContent) {
                    this.listener.onDataRead(ctx, streamId, message.content(), 0, !hasTrailers);
                }
                if (hasTrailers) {
                    Http2Headers headers = HttpConversionUtil.toHttp2Headers(message.trailingHeaders(), true);
                    this.listener.onHeadersRead(ctx, streamId, headers, 0, true);
                }
                stream.closeRemoteSide();
            } finally {
                message.release();
            }
        } else {
            super.channelRead(ctx, msg);
        }
    }
}

